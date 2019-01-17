package com.neuedu.controller;

import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserInfoMapper;
import com.neuedu.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {

    @Autowired
    UserInfoMapper userInfoMapper;

    @RequestMapping(value = "/user/{userid}")
    @ResponseBody
    public ServerResponse<UserInfo> findUser(@PathVariable Integer userid){
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userid);
        if (userInfo !=  null){
            return ServerResponse.createServerResponseBySuccess(null,userInfo);
        }else {
            return ServerResponse.createServerResponseByError("fail");
        }
    }

    @RequestMapping(value = "/hello/{userid}")
    @ResponseBody
    public UserInfo hello(@PathVariable Integer userid){
            return userInfoMapper.selectByPrimaryKey(userid);
    }




}
