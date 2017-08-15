package com.fzy.utils;

import org.springframework.util.DigestUtils;

/**
 * MD5加密工具类
 * Created by fuzhongyu on 2017/4/18.
 */
public class Md5Utils {

    //定义默认盐值
    private static final String DEFAULT_SALT_VALUE="Created by fuzhongyu on 2017/4/18.";

    /**
     * 字符串md5加密
     * @param str 要加密的字符
     * @return
     */
    public static String getMd5(String str){
        return getMd5(str,null);
    }

    /**
     * 字符串md5加密
     * @param str 要加密的字符
     * @param saltValue 自定义盐值
     * @return
     */
    public static String getMd5(String str,String saltValue){
        String encryptionStr;
        saltValue=saltValue==null?DEFAULT_SALT_VALUE:saltValue;
        encryptionStr=str==null?saltValue:saltValue+str;
        return DigestUtils.md5DigestAsHex(encryptionStr.getBytes());
    }


}
