package com.fzy.common.utils;

/**
 *
 * 字符串工具类
 * Created by fuzhongyu on 2017/4/17.
 */
public class StringUtils {

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if(cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
