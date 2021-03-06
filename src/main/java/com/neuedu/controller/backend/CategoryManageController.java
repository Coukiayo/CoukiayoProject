package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICategoryService;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/category/")
public class CategoryManageController {
    @Autowired
    ICategoryService categoryService;

    /*
    获取品类的子节点
     */
    @RequestMapping(value = "get_category.do")
    public ServerResponse get_category(HttpSession session,Integer categoryId){

        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return categoryService.getCategory(categoryId);
    }


    /*
    添加新的节点
     */
    @RequestMapping(value = "add_category.do")
    public ServerResponse add_category(HttpSession session,
                                       @RequestParam(required = false,defaultValue = "0") Integer parentId,
                                       String categoryName){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return categoryService.addCategory(parentId,categoryName);
    }

    /*
    修改节点名称
     */
    @RequestMapping(value = "set_category_name.do")
    public ServerResponse set_category_name(HttpSession session,Integer
            categoryId,String categoryName){
        //判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        //判断用户权限是否为管理员
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        return categoryService.setCategoryName(categoryId,categoryName);
    }

    /*
    获取当前分类ID以及递归子节点categoryID
     */
    @RequestMapping(value = "get_deep_category.do")
    public ServerResponse get_deep_category(HttpSession session,Integer
            categoryId){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }
        return categoryService.getDeepCategory(categoryId);

    }


}
