package com.fzy.common.servlet;

import javax.servlet.http.HttpServlet;

/**
 * 初始化加载
 * Created by fuzhongyu on 2017/5/3.
 */
public class SysInitServlet extends HttpServlet {

    public void init(){
        System.out.println("=================================");
        System.out.println("===执行了SysInitServlet方法=======");
        System.out.println("=================================");
    }

    public void destroy() {

    }
}
