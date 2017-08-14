package com.fzy.common.utils;

import com.fzy.modules.shop.service.SeckillService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

/**
 * 初始化加载
 * Created by fuzhongyu on 2017/5/3.
 */
public class SysInitBean implements InitializingBean,ServletContextAware {

    @Autowired
    private SeckillService seckillService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("=====================================");
        System.out.println("执行了SysInitBean方法:afterPropertiesSet--"+seckillService.getSeckillList().size());
        System.out.println("=====================================");

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        System.out.println("=====================================");
        System.out.println("执行了SysInitBean方法:setServletContext");
        System.out.println("=====================================");
    }
}
