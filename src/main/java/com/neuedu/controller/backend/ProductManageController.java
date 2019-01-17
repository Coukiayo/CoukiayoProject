package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/product/")
public class ProductManageController {

    @Autowired
    IProductService productService;

    /*
    添加或者更新商品
     */
    @RequestMapping(value = "insert_or_update.do")
    public ServerResponse insert_or_update(HttpSession session,Product product){

        //step1:判断登录状态
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        //step2：判断用户权限
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        //step3：返回结果
        return productService.insertOrUpdate(product);
    }

    /*
    更新产品上下架状态
     */

    @RequestMapping(value = "set_sale_status.do")
    public ServerResponse set_sale_status(HttpSession session,Integer productId,Integer status){

        //step1:判断登录状态
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        //step2：判断用户权限
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return productService.setSaleStatus(productId,status);
    }


    /*
    通过商品ID查看商品详细信息
     */
    @RequestMapping(value = "detail.do")
    public ServerResponse detail(HttpSession session, Integer productId){

        //step1:判断登录状态
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        //step2：判断用户权限
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        return productService.detailByProductId(productId);

    }

    /*
    通过分页在前台显示商品数据
     */

    @RequestMapping(value = "list.do")
    public ServerResponse checkInfoByPage(HttpSession session,@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "10") Integer pageSize){

        //step1:判断登录状态
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        //step2：判断用户权限
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        return productService.checkInfoByPage(pageNum,pageSize);
    }



    /*
   通过商品名称以及商品ID进行查询，但也是可以省略不写的，如果
   只有商品ID就是进行精准查询，如果是写上商品名称则是通过模糊查询
   如果两个都写上就是根据两者进行查询
     */

    @RequestMapping(value = "search.do")
    public ServerResponse search(HttpSession session,@RequestParam(value = "productId",required = false) Integer productId,
                                 @RequestParam(value = "productName",required = false) String productName,
                                 @RequestParam(defaultValue = "1") Integer pageNum,
                                 @RequestParam(defaultValue = "10") Integer pageSize){

        //step1:判断登录状态
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        //step2：判断用户权限
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return productService.findProductByProductIdOrProductName(productId,productName,pageNum,pageSize);
    }




}
