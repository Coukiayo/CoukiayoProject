package com.neuedu.controller.protal;


import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user/")
public class UserController {
    @Autowired
    IUserService userService;
    /*
    实现登录功能的controller
     */

    @RequestMapping(value = "login.do")
    public ServerResponse login(HttpSession httpSession,String username, String password){

        ServerResponse login = userService.login(username, password);

        //验证是否登录成功即status=0
        if (login.isSuccess()){
            UserInfo userInfo = (UserInfo) login.getData();
            if (userInfo.getRole()==Const.RoleEnum.ROLE_CUSTOMER.getCode()){
                return ServerResponse.createServerResponseByError("无权限登录");
            }
            httpSession.setAttribute(Const.CURRENTUSER,userInfo);
        }
        return login;
    }

    /*
    实现注册功能的接口
     */
    @RequestMapping(value = "register.do")
    public ServerResponse register(UserInfo userInfo){

        ServerResponse register = userService.register(userInfo);
        return register;

    }

    /**
     * 实现根据用户名查询密保问题的接口
     */
    @RequestMapping(value = "forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        ServerResponse questionByUsername = userService.getQuestionByUsername(username);
        return questionByUsername;
    }

    /**
     * 根据用户名、密保问题以及密保问题查询token
     */
    @RequestMapping(value = "forget_check_answer.do")
    public ServerResponse forget_check_answer(String username,String question,String answer){

        ServerResponse countByUsernameAndPasswordAndAnswer = userService.getCountByUsernameAndPasswordAndAnswer(username, question, answer);
        System.out.println(countByUsernameAndPasswordAndAnswer);
        return countByUsernameAndPasswordAndAnswer;
    }


    /*
    忘记密码的重置密码（在未登录状态下）
     */
    @RequestMapping(value = "forget_reset_password.do")
    public ServerResponse forget_reset_password(String username,String password,String forgetToken){

        ServerResponse serverResponse = userService.resetPasswordByUsernameAndToken(username, password, forgetToken);
        return serverResponse;

    }


    /**
     * 检测用户名或者邮箱是否有效
     */
    @RequestMapping(value = "check_valid.do")
    public ServerResponse check_valid(String str,String type){
        ServerResponse serverResponse = userService.checkValid(str, type);
        return serverResponse;
    }


    /**
     * 获取用户的登录信息
     */

    @RequestMapping(value = "get_user_info.do")
    public ServerResponse get_user_info(HttpSession session){
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError("用户未登录");
        }
        attribute.setPassword("");
        attribute.setQuestion("");
        attribute.setAnswer("");
        attribute.setRole(null);
        return ServerResponse.createServerResponseBySuccess(null,attribute);
    }

    /*
    登录状态下重置密码
     */
    @RequestMapping(value = "reset_password.do")
    public ServerResponse reset_password(HttpSession session,String passwordOld,String passwordNew){
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError("用户未登录");
        }
        return userService.resetPassword(attribute.getUsername(),passwordOld,passwordNew);

    }

    /*
    登录状态下更新个人信息
     */
    @RequestMapping(value = "update_information.do")
    public ServerResponse update_information(HttpSession session,UserInfo user){

        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError("用户未登录");
        }
        user.setId(attribute.getId());
        ServerResponse serverResponse = userService.update_information(user);
        if (serverResponse.isSuccess()){
            UserInfo userinfoById = userService.findUserinfoById(attribute.getId());
            session.setAttribute(Const.CURRENTUSER,userinfoById);
        }
        return serverResponse;
    }

    /*
    登录状态下获取当前用户的详细信息
     */
    @RequestMapping(value = "get_information.do")
    public ServerResponse get_information(HttpSession session){
        UserInfo attribute = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (attribute==null){
            return ServerResponse.createServerResponseByError("用户未登录");
        }
        attribute.setPassword("");
        return ServerResponse.createServerResponseBySuccess(null,attribute);
    }

    /*
    退出登录
     */

    @RequestMapping(value = "logout.do")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENTUSER);
        return ServerResponse.createServerResponseBySuccess("移除用户成功");

    }



}
