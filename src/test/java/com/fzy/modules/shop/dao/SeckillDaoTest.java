package com.fzy.modules.shop.dao;

import com.fzy.modules.shop.entity.Seckill;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.net.SocketTimeoutException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by fuzhongyu on 2017/4/17.
 */
//配置spring和junit整合，junit启动时加载springIOC容器:spring-test(spring提供的方便测试的依赖),junit依赖
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring-context.xml"})
public class SeckillDaoTest{

    @Resource
    public SeckillDao seckillDao;
//
//
////    ApplicationContext applicationContext;
////    SeckillDao seckillDao;
////    @Before
////    public void init(){
////        applicationContext=new ClassPathXmlApplicationContext("classpath:spring-context.xml");
////        seckillDao=applicationContext.getBean(SeckillDao.class);
////    }
//
//
    @Test
    public void reduceNumber() throws Exception {
        System.out.println(seckillDao.reduceNumber("1",new Date()));
    }
//
//    @Test
//    public void queryById() throws Exception {
//        Seckill seckill = seckillDao.queryById("1");
//        System.out.println(seckill);
//    }
//
//    @Test
//    public void queryAll() throws Exception {
//        List<Seckill> list=seckillDao.queryAll(2,1);
//        for (Seckill seckill:list){
//            System.out.println(seckill.getName());
//        }
//    }
//
//    @Test
//    public void insert(){
//        Seckill seckill=new Seckill();
//        seckill.setId("101");
//        seckill.setDeleteFlag(false);
//        seckillDao.insert(seckill);
//    }

//    @Test
//    public void find(){
//        Seckill seckill=new Seckill();
//        seckill.setName("1");
//        List<Seckill> seckillList=seckillDao.find(seckill,null,null);
//        for (Seckill se:seckillList){
//            System.out.println(se.getId());
//        }
//    }

//    @Test
//    public void showTables(){
//        String str=seckillDao.showTables("shop_seckill1");
//        System.out.println(str);
//    }

//    @Test
//    public void showColumn(){
//        List<String> list=seckillDao.showColumns("shop_seckill","number");
//        for (String str:list){
//            System.out.println(str);
//        }
//    }
//
//    @Test
//    public void columnsType(){
//        System.out.println(seckillDao.columnsType("shop_seckill","remarks"));
//    }
//
//    @Test
//    public void colInfo(){
//        Map<String,Object> map=seckillDao.colInfo("shop_seckill","remarks");
//        System.out.println(map.get("dataType")+"   "+map.get("maxLength").toString());
//    }

}