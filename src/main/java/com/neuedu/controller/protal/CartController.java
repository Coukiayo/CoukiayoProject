package com.neuedu.controller.protal;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 购物车的controller
 */

@RestController
@RequestMapping(value = "/cart/")
public class CartController {
    @Autowired
    ICartService cartService;
    /*
    添加购物车中商品信息或者更新商品数量
    如果添加的商品ID是不存在数据库中的话，那么就将商品信息添加到数据库中即可
     如果添加的商品id是已经在数据库中存在的话，那么就只更新商品的购买数量即可
     */
    @RequestMapping(value = "add.do")
    public ServerResponse add_or_update_cart_product(HttpSession session,Integer productId,Integer count){
        //step1：由于购物车是与用户绑定的，所以要进行登录判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return cartService.addOrUpdateCartProduct(attribute.getId(),productId,count);
    }


    /*
    查看某用户购物车中的商品列表
    注意该方法无需参数，但是需要登录状态，因为购物车和用户是绑定在一起的
     */

    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session){
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return cartService.showCartProductInfo(attribute.getId());
    }


    /*
    更新购物车中的商品数量
    需要登录状态以及注意的是，在更新某件商品数量的时候，是根据用户ID以及商品ID
    查询到该商品，然后在执行更新操作
     */
    @RequestMapping(value = "update.do")
    public ServerResponse update_cart_product_cout(HttpSession session,Integer productId,Integer count){
        //进行一个登录判断
        UserInfo attribute = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return cartService.updateCartProductCout(attribute.getId(),productId,count);
    }

    /*
      移除购物车一个或多个产品，当该方法可以移除多个商品通过每个商品的productId
      因此可以将多个ID用字符串的形式表现出来，用“，”分开，并且在方法中可以将他们转换
      成Interage形式
     */

    @RequestMapping(value = "delete_product.do")
    public ServerResponse delete_product(HttpSession session,String productIds){
        //进行一个登录判断
        UserInfo attribute = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return cartService.deleteProductsByProductIds(attribute.getId(),productIds);
    }

    /*
    购物车选中某个商品
    在购物车中checked字段中1：选中；0：未选中
     */
    @RequestMapping(value = "select.do")
    public ServerResponse select(HttpSession session,Integer productId){
        //进行一个登录判断
        UserInfo attribute = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return cartService.selectCheckerByUserIdAndProductId(attribute.getId(),productId,Const.cartCheckStatus.PRODUCT_CHECKED.getCode());
    }

    /*
    购物车取消选中某个商品
     */
    @RequestMapping(value = "un_select.do")
    public ServerResponse un_select(HttpSession session,Integer productId){
        //进行一个登录判断
        UserInfo attribute = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return cartService.selectCheckerByUserIdAndProductId(attribute.getId(),productId,Const.cartCheckStatus.PRODUCT_UNCHECKED.getCode());
    }

    /*
    购物车全选,注意当全选的时候，就不应该添加productId
    并且项目中的mapper.xml文件上已经对没有productId进行了逻辑判断
     */
    @RequestMapping(value = "select_all.do")
    public ServerResponse select_all(HttpSession session){
        //进行一个登录判断
        UserInfo attribute = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return cartService.selectCheckerByUserIdAndProductId(attribute.getId(),null,Const.cartCheckStatus.PRODUCT_CHECKED.getCode());
    }

    /*
    .购物车取消全选
     */
    @RequestMapping(value = "un_select_all.do")
    public ServerResponse un_select_all(HttpSession session){
        //进行一个登录判断
        UserInfo attribute = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return cartService.selectCheckerByUserIdAndProductId(attribute.getId(),null,Const.cartCheckStatus.PRODUCT_UNCHECKED.getCode());
    }

    /*
    .查询在购物车里的所有商品数量之和
     */
    @RequestMapping(value = "get_cart_product_count.do")
    public ServerResponse get_cart_product_count(HttpSession session){
        //进行一个登录判断
        UserInfo attribute = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return cartService.getCartProductCount(attribute.getId());

    }










}
