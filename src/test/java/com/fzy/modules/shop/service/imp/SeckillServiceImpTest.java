package com.fzy.modules.shop.service.imp;

import com.fzy.common.utils.Md5Utils;
import com.fzy.modules.shop.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.ContextLoader;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fuzhongyu on 2017/4/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml"})
public class SeckillServiceImpTest {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Resource
    private SeckillServiceImp seckillServiceImp;

    @Autowired
    private HttpServletResponse httpServletResponse;

    ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
    HttpServletResponse response=applicationContext.getBean(HttpServletResponse.class);


    @Test
    public void getSeckillList() throws Exception {
        System.out.println(httpServletResponse);
        System.out.println(response);
        List<Seckill> list=seckillServiceImp.getSeckillList();
        logger.info("list={}",list);

    }

    @Test
    public void getById() throws Exception {

    }

    @Test
    public void exportSeckillUrl() throws Exception {

    }

    @Test
    public void executeSeckill() throws Exception {

        seckillServiceImp.executeSeckill("2","18365268281", Md5Utils.getMd5("2"));

    }

}