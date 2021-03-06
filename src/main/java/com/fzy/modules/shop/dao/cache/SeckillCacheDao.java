package com.fzy.modules.shop.dao.cache;

import com.fzy.common.entity.Page;
import com.fzy.common.utils.RedisUtils;
import com.fzy.modules.shop.dao.SeckillDao;
import com.fzy.modules.shop.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 秒杀商品实体类
 * Created by fuzhongyu on 2017/4/17.
 */
@Repository
public class SeckillCacheDao {

    private final static String SHOP_SECKILL_CACHE="shop_seckill_cache_";

    @Autowired
    private SeckillDao seckillDao;

    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 更新条数
     */
    public Integer reduceNumber(String seckillId, Date killTime){
        return seckillDao.reduceNumber(seckillId,killTime);
    }

    /**
     * 根据id查询商品,如果在redis缓存中存在则直接获取，否则从数据库查询放入到缓存，并返回
     * @param id
     * @return
     */
    public Seckill queryById(String id){

        String key=SHOP_SECKILL_CACHE+id;

        if(RedisUtils.getObject(key)!=null){
            return (Seckill) RedisUtils.getObject(key);
        }else {
            Seckill seckill=seckillDao.queryById(id);
            if(seckill!=null){
                RedisUtils.setObject(key,seckill,0);
            }
            return seckill;
        }

    }

    /**
     * 根据偏移量查询商品
     * @param offset
     * @param limit
     * @return
     */
    public List<Seckill> queryAll(int offset,int limit){
        return seckillDao.queryAll(offset,limit);
    }

    /**
     * 分页查询
     * @param seckill
     * @return
     */
    public Page<Seckill>  query(Seckill seckill){
        Page<Seckill> page=new Page<>();
        List<Seckill> list=seckillDao.query(seckill);
        page.setList(list);
        return page;
    }


}
