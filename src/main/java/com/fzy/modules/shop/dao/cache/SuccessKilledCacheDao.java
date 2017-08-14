package com.fzy.modules.shop.dao.cache;

import com.fzy.common.utils.RedisUtils;
import com.fzy.modules.shop.dao.SuccessKilledDao;
import com.fzy.modules.shop.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 秒杀成功实体类
 * Created by fuzhongyu on 2017/4/17.
 */
@Repository
public class SuccessKilledCacheDao {

    private final String SHOP_SUCCESSKILL_CACHE="shop_successkill_cache_";

    @Autowired
    private SuccessKilledDao successKilledDao;

    /**
     * 插入购买明细
     * @param successKilled
     * @return 插入的行数
     */
    public Integer insertSuccessKilled(SuccessKilled successKilled){
        return successKilledDao.insertSuccessKilled(successKilled);
    }

    /**
     * 批量插入
     * @param list
     * @return
     */
    public Integer insertSuccessKilledBatch(List<SuccessKilled> list){
        return successKilledDao.insertSuccessKilledBatch(list);
    }


    /**
     * 根据商品名称查询
     * @param seckillId
     * @return
     */
    public SuccessKilled queryByIdWithSeckill(String seckillId,String userPhone){

        String key=SHOP_SUCCESSKILL_CACHE+seckillId+"_"+userPhone;

        if(RedisUtils.getObject(key)!=null){
            return (SuccessKilled) RedisUtils.getObject(key);
        }else {
            SuccessKilled  successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
            if(successKilled!=null){
                RedisUtils.setObject(key,successKilled,0);
            }
            return successKilled;
        }
    }




}
