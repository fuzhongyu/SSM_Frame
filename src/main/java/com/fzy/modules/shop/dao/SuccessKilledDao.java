package com.fzy.modules.shop.dao;

import com.fzy.modules.shop.entity.SuccessKilled;

import java.util.List;

/**
 * 秒杀成功实体类
 * Created by fuzhongyu on 2017/4/17.
 */
public interface SuccessKilledDao{

    /**
     * 插入购买明细
     * @param successKilled
     * @return 插入的行数
     */
    Integer insertSuccessKilled(SuccessKilled successKilled);

    /**
     * 批量插入
     * @param list
     * @return
     */
    Integer insertSuccessKilledBatch(List<SuccessKilled> list);


    /**
     * 根据商品名称查询
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(String seckillId);




}
