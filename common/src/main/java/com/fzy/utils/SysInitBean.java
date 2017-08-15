package com.fzy.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;
import javax.servlet.ServletContext;

/**
 * 初始化加载
 * Created by fuzhongyu on 2017/5/3.
 */
public class SysInitBean implements InitializingBean,ServletContextAware {


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("=====================================");
        System.out.println("执行了SysInitBean方法:afterPropertiesSet");
        System.out.println("=====================================");

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println("=====================================");
        System.out.println("执行了SysInitBean方法:setServletContext");
        System.out.println("=====================================");
    }
}
