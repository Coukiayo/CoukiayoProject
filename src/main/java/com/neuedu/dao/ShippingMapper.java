package com.neuedu.dao;

import com.neuedu.pojo.Shipping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShippingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbggenerated
     */
    int insert(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbggenerated
     */
    Shipping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbggenerated
     */
    List<Shipping> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Shipping record);

    /*
    通过用户ID以及地址ID删除地址（最大程度的防止横向越权）
     */
    int deleteAdressByUserIdAndShippingId(@Param(value = "userId") Integer userId,
                                          @Param(value = "shippingId") Integer shippingId);



    /*
    登录状态下更新地址。并且需要注意的是如果在更新过程中为添加某个字段的
    值，更新过后，就可能将原有的地址数据覆盖为空，所以在mapper文件中也要进行一个非空
    判断
     */
    int updateAdressByIdAndUserId(Shipping shipping);


    /*
    登录状态下选中查看具体的地址，通过userId以及ShippingId进行查询，防止横向越权
     */
    Shipping selectAdressByUserIdAndShippingId(@Param(value = "userId") Integer userId,
                                               @Param(value = "shippingId") Integer shippingId);


    /*
    登录状态下查看某个用户地址的详细信息的方法接口，此时要通过userId进行查询
     */
    List<Shipping> selctAdressInfoByUserId(Integer userId);
}