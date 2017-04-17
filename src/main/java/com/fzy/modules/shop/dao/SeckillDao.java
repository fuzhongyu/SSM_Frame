package com.fzy.modules.shop.dao;

import com.fzy.modules.shop.entity.Seckill;

import java.util.Date;
import java.util.List;

/**
 * 秒杀商品实体类
 * Created by fuzhongyu on 2017/4/17.
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 更新条数
     */
    Integer reduceNumber(String seckillId, Date killTime);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    Seckill queryById(String id);

    /**
     * 根据偏移量查询商品
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(int offset,int limit);



}
