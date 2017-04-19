package com.fzy.modules.shop.service.imp;

import com.fzy.modules.shop.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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

    @Test
    public void getSeckillList() throws Exception {
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

    }

}