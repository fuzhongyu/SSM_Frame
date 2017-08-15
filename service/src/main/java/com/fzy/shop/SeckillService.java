package com.fzy.shop;

import com.fzy.shop.dto.Exposer;
import com.fzy.shop.dto.SeckillExcution;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fuzhongyu on 2017/4/19.
 */
public interface SeckillService {

    /**
     * 查询秒杀接口
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     *
     * @return
     */
    PageInfo<Seckill> getSeckillPage(int pageNo, int pageSize, Seckill seckill);

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(String seckillId);

    /**
     * 秒杀开启时输出秒杀接口，否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(String seckillId);

    /**
     * 执行秒杀
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    @Transactional
    SeckillExcution executeSeckill(String seckillId, String userPhone, String md5);


}