package com.fzy.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 获取请求流中参数
 *
 * 步骤：将取出来的字符串，再次转换成流，然后把它放入到新request 对象中
 *
 * Created by fuzhongyu on 2017/9/1.
 */
@WebFilter(filterName = "bodyReaderFilter",urlPatterns = "/*", initParams = {
        @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico")}) //忽略静态资源获取
public class BodyReaderFilter implements Filter {

    private Logger logger= LoggerFactory.getLogger(BodyReaderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String contentType=request.getContentType();
        String method=((HttpServletRequest) request).getMethod();
        //如果post请求使用application/json,application/x-www-form-urlencoded,text/xml请求方式，则用自定义请求拦截来获取请求参数
        if(contentType!=null&&"post".equalsIgnoreCase(method)&&
                (contentType.toLowerCase().startsWith("application/json")||
                        contentType.toLowerCase().startsWith("application/x-www-form-urlencoded")||
                        contentType.toLowerCase().startsWith("text/xml"))){

            ServletRequest requestWrapper=new BodyReaderHttpServletRequestWrapper(httpServletRequest);
            chain.doFilter(requestWrapper,response);

        }else {
            chain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {

    }
}
