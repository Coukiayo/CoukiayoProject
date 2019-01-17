package com.neuedu.service;


import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import org.springframework.stereotype.Service;

@Service
public interface IAdressService {

    /*
    添加地址方法的接口
     */
    public ServerResponse addArdess(Integer userId, Shipping shipping);

    /*
    删除地址方法的接口
     */
    public ServerResponse deleteAdress(Integer userId,Integer shippingId);

    /*
    登录状态下更新地址的方法接口
     */
    public ServerResponse updateArdess(Shipping shipping);


    /*
    登录状态下选中查看具体的地址
     */
    public ServerResponse selectAdress(Integer userId,Integer shippingId);

    /*
    登录状态下查看某个用户地址的详细信息的方法接口，此时要通过userId进行查询
     */
    public ServerResponse selectOneUserAdress(Integer userId,Integer pageNum,Integer pageSize);

}
