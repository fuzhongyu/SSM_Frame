package com.fzy.modules.shop.service.imp;

import com.fzy.common.entity.ErrorsMsg;
import com.fzy.common.exception.ServiceException;
import com.fzy.common.utils.Md5Utils;
import com.fzy.modules.shop.dao.cache.SeckillCacheDao;
import com.fzy.modules.shop.dao.cache.SuccessKilledCacheDao;
import com.fzy.modules.shop.entity.Seckill;
import com.fzy.modules.shop.entity.SuccessKilled;
import com.fzy.modules.shop.entity.dto.Exposer;
import com.fzy.modules.shop.entity.dto.SeckillExcution;
import com.fzy.modules.shop.service.SeckillService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by fuzhongyu on 2017/4/19.
 */
@Service
public class SeckillServiceImp implements SeckillService {

    private Logger logger= LoggerFactory.getLogger(SeckillServiceImp.class);

    @Autowired
    private SeckillCacheDao seckillDao;

    @Autowired
    private SuccessKilledCacheDao successKilledDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    static {
        System.out.println("=========<静态块加载>=========");
    }

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @param seckill
     * @return
     */
    @Override
    public PageInfo<Seckill> getSeckillPage(int pageNo,int pageSize, Seckill seckill){
         return  seckillDao.queryPage(pageNo,pageSize,seckill);
    }


    @Override
    public Seckill getById(String seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(String seckillId) {
        Seckill seckill=seckillDao.queryById(seckillId);
        if (seckill==null){
            return new Exposer(false,seckillId);
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        return new Exposer(true, seckillId,Md5Utils.getMd5(seckillId),nowTime.getTime(),startTime.getTime(),endTime.getTime());
    }

    @Override
    @Transactional
    public SeckillExcution executeSeckill(String seckillId, String userPhone, String md5) {
        if(StringUtils.isBlank(md5)||!Md5Utils.getMd5(seckillId).equals(md5)){
            throw new ServiceException(ErrorsMsg.ERR_1001);
        }
        SuccessKilled killed=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        if(killed!=null){
            logger.info(ErrorsMsg.ERR_1003.getMsg());
            throw new ServiceException(ErrorsMsg.ERR_1003);
        }

        //执行秒杀逻辑:减库存+记录购买
        Date nowTime=new Date();
        int updateCount=seckillDao.reduceNumber(seckillId,nowTime);
        if(updateCount<0){
            logger.info(ErrorsMsg.ERR_1002.getMsg());
            throw new ServiceException(ErrorsMsg.ERR_1002);
        }else {
            SuccessKilled successKilled=new SuccessKilled(seckillId,userPhone);
            successKilled.preInsert();
            int insertCount=successKilledDao.insertSuccessKilled(successKilled);
            if(insertCount<0){
                logger.info(ErrorsMsg.ERR_9999.getMsg());
                throw new ServiceException(ErrorsMsg.ERR_9999);
            }else {
                return new SeckillExcution(seckillId,1,"秒杀成功",successKilled);
            }
        }
    }
}
