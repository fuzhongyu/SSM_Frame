package com.fzy.modules.shop.dao;

import com.fzy.modules.shop.entity.Seckill;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by fuzhongyu on 2017/4/17.
 */
//配置spring和junit整合，junit启动时加载springIOC容器:spring-test(spring提供的方便测试的依赖),junit依赖
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring-context.xml"})
public class SeckillDaoTest{

    @Autowired
    public SeckillDao seckillDao;


//    ApplicationContext applicationContext;
//    SeckillDao seckillDao;
//    @Before
//    public void init(){
//        applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
//        seckillDao=applicationContext.getBean(SeckillDao.class);
//    }


    @Test
    public void reduceNumber() throws Exception {
        System.out.println(seckillDao.reduceNumber("1",new Date()));
    }

    @Test
    public void queryById() throws Exception {
        Seckill seckill = seckillDao.queryById("1");
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> list=seckillDao.queryAll(2,1);
        for (Seckill seckill:list){
            System.out.println(seckill.getName());
        }
    }

}