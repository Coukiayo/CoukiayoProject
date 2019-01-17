package com.neuedu.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件的工具类
 */
public class PropertiesUtils {

    private static Properties properties = new Properties();


    static {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取配置文件中的内容
     */
    public static String readPropertiesByKey(String key){
        String property = properties.getProperty(key);
        return property;
    }

}
