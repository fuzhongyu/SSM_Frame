package com.fzy.shop;

import com.fzy.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 秒杀成功实体类
 * Created by fuzhongyu on 2017/4/17.
 */
@MyBatisDao
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
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") String seckillId, @Param("userPhone") String userPhone);




}
