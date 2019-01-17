package com.neuedu.dao;

import com.neuedu.pojo.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    List<Cart> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Cart record);

    /*
    通过用户ID以及商品ID查询购物车信息的映射文件接口
    （因为购物车和用户的绑定关系的）
     */
    Cart selectCartByUserIdAndProductId(@Param(value = "userId") Integer userId,
                                        @Param(value = "productId") Integer productId);


    /*
    通过用户ID查询到购物车的具体信息的映射文件接口，注意返回的值应该的有可能存在多个
    因为一个用户的购物车中可能有多件商品，所以返回值是List<Cart>
     */
    List<Cart> findCartByUser(Integer userId);

    /*
    统计购物车表中有几个未勾选的商品
    如果返回值>0表示是未全选
     */

    int isNotCheckQuantity(Integer userId);


    /*
    删除购物车中的某些商品，通过userId以及productIdList确定
    其中productIdList为多个productId的集合
     */
    int deleteProductsByUserIdAndProductIds(@Param(value = "userId") Integer userId,
                                            @Param(value = "productIdList") List<Integer> productIdList);


    /*
    对购物车商品的选中状态进行一个操作，可以用一个接口表示四种状态：
    购物车某个商品的选中或为选中（通过productId）
    购物车所有商品的选中或为选中（此时就不应该传productId这个参数）
     */
    int updateProductCheckedByUserIdAndProductId(@Param(value = "userId") Integer userId,
                                                 @Param(value = "productId") Integer productId,
                                                 @Param(value = "checked") Integer checked);


    /*
    查询在购物车里的产品数量
    并且注意，返回值类型最好是Interage，因为如果使用int作为返回值类型时
    当查询的用户id不存在时，那么查询的商品数量也是不存在的即为null
    这时就会产生类型不匹配的问题，从而产生错误
    但是也可以使用int作为返回值类型，但是要在sql语句中使用ifnull即
    ifnull（sum（quantity））
     */
    Integer getCartProductCountByUserId(Integer userId);


    /*
    查询某用户购物车中已选中的商品信息，注意checked=1，并通过userId
     */
    List<Cart> findCartProductByUserIdAndChecked(Integer userId);

    /*
    创建一个批量删除购物车中已购买的商品接口
     */
    int deleteCartAlreadyBuyProducts(List<Cart> cartList);



}