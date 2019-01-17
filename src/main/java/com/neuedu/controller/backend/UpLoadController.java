package com.neuedu.controller.backend;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/manage/product/")
public class UpLoadController {

    @Autowired
    IProductService productService;

    /*
    表明该结果返回的是jsp页面，并且规定了请求方式为get提交
    用于从服务端中获取数据
     */
    @RequestMapping(value = "upload",method = RequestMethod.GET)
    public String uploadPicture(){
        return "upload";
    }

    /*
    表明该方法是返回的json字符串，并且规定了是post的提交方式，用于向
    服务端传送数据
    从而与上面的方法区别（因为两者有着相同的value值）
     */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse uploadPicture1(HttpSession session, @RequestParam(value = "file",required = false) MultipartFile file){

        //step1:判断登录状态
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NEED_LOGIN.getDesc());
        }

        //step2：判断用户权限
        if (userInfo.getRole()!=Const.RoleEnum.ROLE_ADMIN.getCode()){
            return ServerResponse.createServerResponseByError(Const.ReponseCodeEnum.NO_PRIVILEGE.getDesc());
        }

        //String path = "D:\\hello";
        String path = "/project/img";

        return productService.uploadPicture(file,path);
    }
}
