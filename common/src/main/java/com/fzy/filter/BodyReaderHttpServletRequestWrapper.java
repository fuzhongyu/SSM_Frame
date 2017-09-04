package com.fzy.filter;

import com.fzy.interceptor.LogInterceptor;
import com.fzy.utils.IoUtils;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * 拦截请求，添加自定义请求逻辑(获取请求参数)
 *
 * Created by fuzhongyu on 2017/9/1.
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        //获取请求参数值
        this.body = IoUtils.getBodyString(request).getBytes(Charset.forName("utf-8"));
        //将值写入线程变量中
        try {
            LogInterceptor.bodyReaderThreadLocal.set(new String(body,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            LogInterceptor.bodyReaderThreadLocal.set("");
        }
    }

    /**
     *  继承实现getReader()重写逻辑，自定义的HttpServletRequestWrapper将原始的HttpServletRequest对象进行再次封装
     * @return
     * @throws IOException
     */
    @Override
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }


    /**
     * 将body体中的字符串转换为字节流
     * @return
     * @throws IOException
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}
