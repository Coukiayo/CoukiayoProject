package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IOrderService {

    /*
    创建订单对应方法的接口
     */
    public ServerResponse creatOrderByUserIdAndShippingId(Integer userId,Integer shippingdId);


    /*
    取消订单对应方法的接口
    参数中包含了订单对应的用户id以及订单编号
    设置用户Id是为了最大程度上的防止横向越权
     */
    public ServerResponse cancleOrderNo(Integer userId,Long orderNo);

    /*
    在登录状态下获取订单的商品信息
     */
    public ServerResponse getOrderInfoList(Integer userId);

    /*
    在后台查看订单信息
     */
    public ServerResponse getOrderInfoAtBackEnd(Integer userId,Integer pageNum,Integer pageSize);

    /*
    在后台根据订单号查询订单的详细信息
     */
    public ServerResponse getOrderDetailByOrderNo(Long orderNo);

    /*
    支付宝支付接口
     */
    public ServerResponse payByUserIdAndOrderNo(Integer userId,Long orderNo);

    /*
    支付宝支付回调接口
     */
    public ServerResponse alipayCallBack(Map<String,String>map);


    /*
    查询订单支付状态
     */
    public ServerResponse queryOrderPayStatus(Long orderNo);




}
