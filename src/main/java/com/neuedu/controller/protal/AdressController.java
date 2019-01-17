package com.neuedu.controller.protal;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IAdressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/shipping/")
public class AdressController {
    @Autowired
    IAdressService adressService;

    /*
    添加地址，注意：最终在页面上返回的结果是地址表中添加地址对应的id值（主键）
     */
    @RequestMapping(value = "add.do")
    public ServerResponse add(HttpSession session ,Shipping shipping ){
        //进行一个登录判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return adressService.addArdess(attribute.getId(),shipping);
    }


    /*
    删除地址。注意：在页面上最终返回的只需要是是否删除成功的信息即可
     */
    @RequestMapping(value = "del.do")
    public ServerResponse del(HttpSession session,Integer shippingId){
        //进行一个登录判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        return adressService.deleteAdress(attribute.getId(),shippingId);
    }

    /*
    登录状态下更新地址信息。并且需要注意的是如果在更新过程中为添加某个字段的
    值，更新过后，就能将原有的地址数据覆盖为空，所以在mapper文件中也要进行一个非空
    判断
     */
    @RequestMapping(value = "update.do")
    public ServerResponse update(HttpSession session,Shipping shipping){
        //进行一个登录判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        //用户id放入到更新的地址表中的user_Id中，确保更新的一致性
        shipping.setUserId(attribute.getId());
        return adressService.updateArdess(shipping);
    }

    /*
    登录状态下选中查看具体的地址
     */
    @RequestMapping(value = "select.do")
    public ServerResponse select(HttpSession session,Integer shippingId){
        //进行一个登录判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return adressService.selectAdress(attribute.getId(),shippingId);
    }

    /*
    登录状态下查看某个用户地址的详细信息，此时要通过userId进行查询
     */
    @RequestMapping(value = "list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10")Integer pageSize){
        //进行一个登录判断
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        return adressService.selectOneUserAdress(attribute.getId(),pageNum,pageSize);
    }




}
