package com.neuedu.utils;


import java.math.BigDecimal;

/*
商品价格的工具类，注意在商业运算中，如果使用float或者double类型进行运算
就会损失数值精度，是不符合规则的，因此要用到BigDecimal类型，但是在使用
BigDecimal进行运算的时候，注意在BigDecimal的参数列表里要将数字转换成字符串进行运算
如果是使用数字进行BigDecimal运算的话也同样会损失精度
 */
public class ProductPriceByBigDecimal {
    /*
    商品价格的相加方法
     */
    public static BigDecimal productPriceAdd(double price1,double price2){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(price1));
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(price2));
        BigDecimal add = bigDecimal.add(bigDecimal1);
        return add;
    }

    /*
    商品价格的相减方法
    注意：是price1减price2（第一个参数减去第二个参数）
     */
    public static BigDecimal productPriceSubtraction(double price1,double price2){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(price1));
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(price2));
        BigDecimal subtract = bigDecimal.subtract(bigDecimal1);
        return subtract;
    }


    /*
    商品价格的相乘方法
     */
    public static BigDecimal productPricceMultiply(double price1,double price2){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(price1));
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(price2));
        BigDecimal multiply = bigDecimal.multiply(bigDecimal1);
        return multiply;
    }

    /*
    商品价格的相除方法
    注意是price1除以price2（第一个参数除以第二个参数）
     */
    public static BigDecimal productPriceDivision(double price1,double price2){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(price1));
        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(price2));
        /*
        其中scale为2表示在小数点后保留两位小数
        BigDecimal.ROUND_HALF_UP表示将运算的结果进行四舍五入
         */
        BigDecimal divide = bigDecimal.divide(bigDecimal1, 2, BigDecimal.ROUND_HALF_UP);
        return divide;
    }
}
