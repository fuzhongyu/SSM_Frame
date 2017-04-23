package com.fzy.modules.shop.dao;

import com.fzy.common.annotation.MyBatisDao;
import com.fzy.modules.shop.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 秒杀商品实体类
 * Created by fuzhongyu on 2017/4/17.
 */
@MyBatisDao
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 更新条数
     */
    Integer reduceNumber(@Param("seckillId") String seckillId, @Param("killTime") Date killTime);

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
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询商品
     * @param seckill
     * @return
     */
    List<Seckill> query(Seckill seckill);



}
