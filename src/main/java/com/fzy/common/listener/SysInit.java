package com.fzy.common.listener;

import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 初始化加载
 * Created by fuzhongyu on 2017/5/3.
 */
public class SysInit implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext=ContextLoader.getCurrentWebApplicationContext().getServletContext();
        servletContext.setAttribute("a","a");
        System.out.println("=================================");
        System.out.println("========执行了SysInit方法=========");
        System.out.println("=================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
