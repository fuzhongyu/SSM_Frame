package com.fzy.modules.shop.dao;

import com.fzy.modules.shop.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by fuzhongyu on 2017/4/17.
 */
//配置spring和junit整合，junit启动时加载springIOC容器:spring-test(spring提供的方便测试的依赖),junit依赖
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:mybatis-config.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {

    }

    @Test
    public void queryById() throws Exception {
        Seckill seckill=seckillDao.queryById("1");
        System.out.println(seckill);
    }

    @Test
    public void queryAll() throws Exception {

    }

}