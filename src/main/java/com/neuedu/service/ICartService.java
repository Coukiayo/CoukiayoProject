package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import org.springframework.stereotype.Service;

@Service
public interface ICartService {

    /*
    执行购物车中添加商品数据或者更新商品数据的方法接口
     */
    public ServerResponse addOrUpdateCartProduct(Integer userId,Integer productId,Integer count);

    /*
    查看某个用户的购物车列表的方法接口
     */
    public ServerResponse showCartProductInfo(Integer userId);

    /*
    更新某个用户购物车下的某个商品的数量方法的接口
     */
    public ServerResponse updateCartProductCout(Integer userId,Integer productId,Integer count);


    /*
    在购物车中移除一个或者多个商品
     */
    public ServerResponse deleteProductsByProductIds(Integer userId,String productIds);

    /*
    购物车选中或者取消某个商品
    或者对购物车中所有商品进行全选或者取消全选
    在购物车中checked字段中1：选中；0：未选中
     */
    public ServerResponse selectCheckerByUserIdAndProductId(Integer userId,Integer paoductId,Integer checked);


    /*
    查询在购物车里的产品数量
     */
    public ServerResponse getCartProductCount(Integer userId);

}
