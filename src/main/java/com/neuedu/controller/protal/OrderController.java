package com.neuedu.controller.protal;


import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayAccount;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/order/")
public class OrderController {
    @Autowired
    IOrderService orderService;

    /*
    创建订单
     */
    @RequestMapping(value = "create.do")
    public ServerResponse create(HttpSession session, Integer shippingId){
        //进行一个登陆判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return orderService.creatOrderByUserIdAndShippingId(attribute.getId(),shippingId);
    }

    /*
    取消订单
     */
    @RequestMapping(value = "cancel.do")
    public ServerResponse cancel(HttpSession session,Long orderNo){
        //进行一个登陆判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return orderService.cancleOrderNo(attribute.getId(),orderNo);
    }

    /*
    获取订单的商品信息
     */
    @RequestMapping(value = "get_order_cart_product.do")
    public ServerResponse get_order_cart_product(HttpSession session){
        //进行一个登陆判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return orderService.getOrderInfoList(attribute.getId());
    }

    /*
    在后台查看订单信息，与前台不同的是，前台查看的时候是只能查看当前普通用户（买家）对应的订单信息
    而在后台查看的是管理员，因此能看到所有的订单信息或者某一用户的，这时在实现类的的代码中
    要进行userID判断，如果userID存在则是查询某一用户的订单信息，如果不错存在则是
    查询所有的订单信息，这一点需要注意
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(defaultValue = "1")Integer pageNum,
                               @RequestParam(defaultValue = "10")Integer pageSize){

        //进行一个登陆判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return orderService.getOrderInfoAtBackEnd(attribute.getId(),pageNum,pageSize);
    }

    /*
    在后台根据订单号查询订单的详细信息
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession session,Long orderNo){
        //进行一个登陆判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return orderService.getOrderDetailByOrderNo(orderNo);
    }



    /*
    支付宝支付接口
     */
    @RequestMapping(value = "pay.do")
    public ServerResponse pay(HttpSession session,Long orderNo){
        //进行一个登陆判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return orderService.payByUserIdAndOrderNo(attribute.getId(),orderNo);
    }

    /*
    支付宝服务器回调应用服务器接口，当在支付宝支付的过程中，当用户完成支付之后
    这时支付宝服务器就会回调应用服务器接口（向应用服务器接口回传给支付信息），而当
    步骤成功之后，应用服务器只需要给支付宝服务器响应“success”即可，否则支付宝服务器
    接口就会一直回调应用服务器接口，所以返回值类型为String即可
    一定保证该方法是被支付宝调用的
     */
    @RequestMapping(value = "alipay_callback.do")
    public ServerResponse alipay_callback(HttpServletRequest request){

        System.out.println("====支付宝服务器回调应用服务器方法====");
        //拿到支付宝回传的信息，然后将支付宝服务器回调返回的信息封装到一个Map集合中，并用“，”隔开
        //获取request返回的参数，value是一个字符串数组
        Map<String, String[]> parameter = request.getParameterMap();

        //创建一个map集合用来接收获取的参数
        Map<String,String> requestMap = Maps.newHashMap();
        //使用迭代循环遍历value
        Iterator<String> iterator = parameter.keySet().iterator();
        while (iterator.hasNext()){
            //先循环遍历出key的值，然后通过key的值找打对应的value
            String key = iterator.next();
            String[] strings = parameter.get(key);
            String value = "";
            //使用for循环将数组中集合遍历，然后用“，”隔开
            System.out.println(strings.length);
            for (int i=0;i<strings.length;i++){
                //使用三目表达式进行判断，如果是数组中的最后一个元素那么不需要添加“，”
                //如果不是，则需要添加“，”
                value=(i==strings.length-1)?value+strings[i]:value+strings[i]+",";
                System.out.println(key+":"+value);
            }
            requestMap.put(key,value);
        }

        //进行一个支付宝的验证，因为该方法只能允许支付宝进行访问回调，如果不设置安全校验
        //那么就有可能其他的用户也会通过路径直接访问到该方法\
        //该方法中的四个参数：1：从request获取的参数信息（Map类型）；2：公钥；3：编码格式(默认为utf-8)；4：签名类型:默认为RSA2
        try {
            //需要先将sign_type从request获取的参数集合移除，否则在验签的过程中是不通过的
            System.out.println("准备验签中。。。");
            requestMap.remove("sign_type");
            System.out.println("验签");
            boolean result = AlipaySignature.rsaCheckV2(requestMap, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
            if (!result){
                //表示访问该路径不是通过支付宝来访问的
                return ServerResponse.createServerResponseByError("非法请求方式");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }


        return orderService.alipayCallBack(requestMap);
    }

    /*
    查看订单的支付状态
     */
    @RequestMapping(value = "query_order_pay_status.do")
    public ServerResponse query_order_pay_status(HttpSession session,Long orderNo){
        //进行一个登陆判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return orderService.queryOrderPayStatus(orderNo);
    }



}
