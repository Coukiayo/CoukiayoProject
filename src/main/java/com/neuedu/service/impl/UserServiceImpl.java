package com.neuedu.service.impl;

import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.utils.MD5Utils;
import com.neuedu.utils.TokenCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    /*
    登录方法的实现类
     */
    @Override
    public ServerResponse login(String username, String password) {

        //ster1.参数的非空校验
        if (username==null||username.equals("")){
            return ServerResponse.createServerResponseByError("用户名不能为空");

        }
        if (password==null||password.equals("")){
            return ServerResponse.createServerResponseByError("密码不能为空");
        }

        //step2.检查用户名是否存在

        int i = userInfoMapper.checkUsername(username);
        if (i==0){
            return ServerResponse.createServerResponseByError("用户名不存在");
        }

        //step3.根据用户名和密码查找信息

        UserInfo userInfo = userInfoMapper.selectUserinfoByUsernameAndPassword(username, MD5Utils.getMD5Code(password));
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("密码错误");
        }
        //step4.返回结果
        userInfo.setPassword("");
        return ServerResponse.createServerResponseBySuccess(null,userInfo);
    }

    /*
    注册方法的实现类
     */
    @Override
    public ServerResponse register(UserInfo userInfo) {
        //ster1.参数的非空校验
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }

        //ster2.校验用户名
        int i = userInfoMapper.checkUsername(userInfo.getUsername());
        if (i>0){
            return ServerResponse.createServerResponseByError("用户名已存在");
        }
        //ster3.校验邮箱

        int i1 = userInfoMapper.checkEmail(userInfo.getEmail());
        if (i1>0){
            return ServerResponse.createServerResponseByError("邮箱已存在");
        }
        //ster4.注册
        userInfo.setRole(Const.RoleEnum.ROLE_CUSTOMER.getCode());
        userInfo.setPassword(MD5Utils.getMD5Code(userInfo.getPassword()));
        int insert = userInfoMapper.insert(userInfo);
        if (insert>0){
            return ServerResponse.createServerResponseBySuccess("注册成功");
        }

        //ster5.返回
        return ServerResponse.createServerResponseByError("注册失败");

    }

    /*
    根据用户名查询到用户密保问题的实现类
     */
    @Override
    public ServerResponse getQuestionByUsername(String username) {
        //step1:参数检验
        if (username==null||username.equals("")){
            return ServerResponse.createServerResponseByError("用户名不能为空");
        }

        //step2:检验username
        int i = userInfoMapper.checkUsername(username);
        if (i==0){
            return ServerResponse.createServerResponseByError("当前用户名不存在，请重新输入");
        }

        //step3:查找密保问题
        String s = userInfoMapper.forgetGetQuestionByUsername(username);
        if (s==null||s.equals("")){
            return ServerResponse.createServerResponseByError("密保问题为空");
        }
        //step4:返回密保问题
        return ServerResponse.createServerResponseBySuccess(null,s);
    }


    /*
    通过用户名、密保问题以及问题答案来查询数据
    并返回token
     */
    @Override
    public ServerResponse getCountByUsernameAndPasswordAndAnswer(String username, String question, String answer) {
        //step1:检验参数
        if (username==null||username.equals("")){
            return ServerResponse.createServerResponseByError("用户名不能为空");
        }
        if (question==null||question.equals("")){
            return ServerResponse.createServerResponseByError("问题不能为空");
        }
        if (answer==null||answer.equals("")){
            return ServerResponse.createServerResponseByError("密保问题答案不能为空");
        }

        //step2:根据username、password以及answer查询
        int i = userInfoMapper.selectByUsernameAndPasswordAndAnswer(username, question, answer);
        if (i==0){

            return ServerResponse.createServerResponseByError("答案错误");
        }

        //step3:服务端生成一个token并将token返回给客户端
        String forgenToken = UUID.randomUUID().toString();
        TokenCache.set(username,forgenToken);
        return ServerResponse.createServerResponseBySuccess(null,forgenToken);

    }


            /*
        根据用户名以及用户名对应的token来更改密码
        注意在方法中参数名为password的值时更改之后的密码
        */
    @Override
    public ServerResponse resetPasswordByUsernameAndToken(String username, String password, String forgetToken) {
        //step1: 参数校验
        if (username==null||username.equals("")){
            return ServerResponse.createServerResponseByError("用户名不能为空");
        }
        if (password==null||password.equals("")){
            return ServerResponse.createServerResponseByError("密码不能为空");
        }
        if (forgetToken==null||forgetToken.equals("")){
            return ServerResponse.createServerResponseByError("token不能为空");
        }

        //step2:token校验

        String token = TokenCache.get(username);
        if (token==null){
            return ServerResponse.createServerResponseByError("token过期");
        }

        if (!token.equals(forgetToken)){
            return ServerResponse.createServerResponseByError("无效的token");
        }

        //step3:更改密码
        int i = userInfoMapper.resetByUsernameAndToken(username, MD5Utils.getMD5Code(password));
        if (i>0){

            return ServerResponse.createServerResponseBySuccess("修改成功");
        }
        return ServerResponse.createServerResponseByError("修改失败");
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        //step1:参数的非空校验
        if (str==null||str.equals("")){
            return ServerResponse.createServerResponseByError("用户名或者邮箱不能为空");
        }
        if (type==null||type.equals("")){
            return ServerResponse.createServerResponseByError("校验类型不能为空");
        }

        /*
        step2:根据type的值确定要检验的是哪种字段
        如果type：username校验的就是用户名
        如果type：email校验的就是邮箱
         */

        //step3:返回数据结果

        if (type.equals("username")){
            int result = userInfoMapper.checkUsername(str);
            if (result>0){
                //表示用户名已经存在
                return ServerResponse.createServerResponseByError("用户名已存在");
            }else {
                return ServerResponse.createServerResponseBySuccess("用户名不存在");
            }
        }else if (type.equals("email")){
            int result = userInfoMapper.checkEmail(str);
            if (result>0){
                //表示邮箱已经存在
                return ServerResponse.createServerResponseByError("邮箱已存在");
            }else {
                return ServerResponse.createServerResponseBySuccess("邮箱不存在");
            }
        }else {
            return ServerResponse.createServerResponseByError("参数类型错误");
        }
    }

    /*
    在登录状态下修改密码
     */
    @Override
    public ServerResponse resetPassword(String username, String passwordOld, String passworldNew) {

        //setp1:参数的非空校验
        if (username==null||username.equals("")){
            return ServerResponse.createServerResponseByError("用户名不能为空");
        }
        if (passwordOld==null||passwordOld.equals("")){
            return ServerResponse.createServerResponseByError("旧密码不能为空");
        }
        if (passworldNew==null||passworldNew.equals("")){
            return ServerResponse.createServerResponseByError("新密码不能为空");
        }

        //setp2:根据用户名以及旧密码来获取数据信息
        //setp3:在登录状态下修改密码
        UserInfo userInfo = userInfoMapper.selectUserinfoByUsernameAndPassword(username, MD5Utils.getMD5Code(passwordOld));
        if (userInfo==null){
            return ServerResponse.createServerResponseByError("旧密码错误");
        }
        userInfo.setPassword(MD5Utils.getMD5Code(passworldNew));
        int i = userInfoMapper.updateByPrimaryKey(userInfo);
        if (i>0){
            return ServerResponse.createServerResponseBySuccess("修改成功 ");
        }
        return ServerResponse.createServerResponseByError("修改失败");

    }

    /*
    登录状态下更新个人信息
     */
    @Override
    public ServerResponse update_information(UserInfo user) {
        //setp1:参数校验
        if (user==null){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }

        //setp2：更新用户信息
        int i = userInfoMapper.updateUserBySelectActive(user);
        //setp3:返回结果
        if (i>0){
            return ServerResponse.createServerResponseBySuccess("更新信息成功");
        }
        return ServerResponse.createServerResponseByError("更新个人信息失败");
    }

    @Override
    public UserInfo findUserinfoById(Integer userid) {

        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        return userInfo;
    }

}
