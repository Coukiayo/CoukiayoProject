package com.neuedu.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtils {


    //创建一个静态的字符串常量
    //注意规定格式时（例如：时分秒，年月日的时候一定要注意书写规范，注意大小写，要按照代码格式去书写）
    private static final String STANDARD_FORMAT="yyyy-MM-dd HH:mm:ss";

    /*
    将data类型转换成字符串的方法
     */

    //这种方法就是可以自定义转换的字符串格式
    public static String dateToStr(Date date,String format){
        DateTime dateTime = new DateTime(date);
        String string = dateTime.toString(format);
        return string;
    }

    //进行方法重载，创建一个新的方法，将日期转换为规定格式下的字符串
    //这时就需要用到创建的String类型的静态常量
    public static String dateToStr(Date date){
        DateTime dateTime = new DateTime(date);
        String string = dateTime.toString(STANDARD_FORMAT);
        return string;
    }


    /*
    创建将字符串转换为date格式的方法
     */
    //这种方法就是可以自定义转换的字符串格式
    public static Date strToDate(String str,String format){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = dateTimeFormatter.parseDateTime(str);
        Date date = dateTime.toDate();
        return date;
    }

    //进行方法重载，创建一个新的方法，这时就需要用到创建的String类型的静态常量
    public static Date strToDate(String str){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(str);
        Date date = dateTime.toDate();
        return date;
    }



}
