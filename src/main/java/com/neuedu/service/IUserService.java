package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    /*
    登录接口
     */
    public ServerResponse login(String username,String password);

    /*
    注册接口
     */
    public ServerResponse register(UserInfo userInfo);

    /**
     * 根据用户名查询用户的密保问题
     */
    public ServerResponse getQuestionByUsername(String username);


    /**
     * 根据用户名、密码以及密保问题答案查询数据
     */
    public ServerResponse getCountByUsernameAndPasswordAndAnswer(String username,String question,String answer);


    /*
    根据用户名以及用户名对应的token来更改密码
    注意在方法中参数名为password的值时更改之后的密码
     */
    public ServerResponse resetPasswordByUsernameAndToken(String username,
                                                          String password,
                                                          String token);

    /*
    在注册的过程中检查用户名以及邮箱是否存在
     */

    public ServerResponse checkValid(String str,String type);


    /*
    在登录状态下重置密码
     */
    public ServerResponse resetPassword(String username,String passwordOld,String passworldNew);


    /*
    登录状态下更个人信息
     */
    public ServerResponse update_information(UserInfo user);

    /*
    根据userID查询用户信息
     */
    public UserInfo findUserinfoById(Integer userid);


}
