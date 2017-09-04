package com.fzy.utils;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 *  数据流操作工具类
 * Created by fuzhongyu on 2017/9/1.
 */
public class IoUtils {

    /**
     * httpServletRequest流操作方法
     * @param request
     * @return
     */
    public static String getBodyString(ServletRequest request){
        StringBuilder stringBuilder=new StringBuilder();
        InputStream inputStream=null;
        BufferedReader reader=null;
        try {
            inputStream=request.getInputStream();
            reader=new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")));
            String line="";
            while ((line=reader.readLine())!=null){
                stringBuilder.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder.toString();
    }
}
