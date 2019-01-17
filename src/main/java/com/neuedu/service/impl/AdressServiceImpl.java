package com.neuedu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IAdressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdressServiceImpl implements IAdressService {

    @Autowired
    ShippingMapper shippingMapper;

    /*
    添加地址的方法接口对应的实现类
     */
    @Override
    public ServerResponse addArdess(Integer userId, Shipping shipping) {
        //step1：参数的非空校验
        if (shipping==null){
            return ServerResponse.createServerResponseByError("参数错误");
        }

        //step2：调用dao层，执行添加地址的sql语句
        shipping.setUserId(userId);
        shippingMapper.insert(shipping);

        //step3：返回结果
        Map<String,Integer>map = Maps.newHashMap();
        map.put("shippingId",shipping.getId());
        return ServerResponse.createServerResponseBySuccess(null,map);
    }



    /*
    删除地址方法的接口
     */
    @Override
    public ServerResponse deleteAdress(Integer userId, Integer shippingId) {
        //step1：参数的非空校验
        if (shippingId==null){
            return ServerResponse.createServerResponseByError("地址ID不能为空");
        }

        //step2：调用dao层，执行添加地址的sql语句
        int i = shippingMapper.deleteAdressByUserIdAndShippingId(userId, shippingId);

        //step3：返回结果
        if (i>0){
            return ServerResponse.createServerResponseBySuccess("删除成功");
        }
        return ServerResponse.createServerResponseByError("删除失败");
    }

    @Override
    public ServerResponse updateArdess(Shipping shipping) {
        //step1：参数的非空校验
        if (shipping==null){
            return ServerResponse.createServerResponseByError("参数错误");
        }

        /*
        step2：调用dao层，执行sql语句，并且需要注意的是如果在更新过程中为添加某个字段的
        值，更新过后，就能将原有的地址数据覆盖为空，所以在mapper文件中也要进行一个非空
        判断
         */
        int i = shippingMapper.updateAdressByIdAndUserId(shipping);

        //返回结果
        if (i>0){
            return ServerResponse.createServerResponseBySuccess("更新成功");
        }
        return ServerResponse.createServerResponseByError("更新失败");
    }



    /*
    登录状态下选中查看具体的地址
     */

    @Override
    public ServerResponse selectAdress(Integer userId, Integer shippingId) {
        //step1：参数的非空校验
        if (shippingId==null){
            return ServerResponse.createServerResponseByError("地址ID不能为空");
        }
        //step2：调用dao层，执行sql语句
        Shipping shipping = shippingMapper.selectAdressByUserIdAndShippingId(userId, shippingId);

        //step3：返回结果
        if (shipping!=null){
            return ServerResponse.createServerResponseBySuccess(null,shipping);
        }
        return ServerResponse.createServerResponseByError("查询的地址不存在");
    }

    /*
    登录状态下查看某个用户地址的详细信息的方法接口，此时要通过userId进行查询
    并且pageNum和pageSize在controller层中设定了默认值，此时就不需要进行非空判断
     */
    @Override
    public ServerResponse selectOneUserAdress(Integer userId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippings = shippingMapper.selctAdressInfoByUserId(userId);
        if (shippings==null){
            return ServerResponse.createServerResponseByError("查询的用户不存在或当前用户没有设置地址信息");
        }
        PageInfo pageInfo = new PageInfo(shippings);
        return ServerResponse.createServerResponseBySuccess(null,pageInfo);
    }
}
