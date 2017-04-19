package com.fzy.modules.shop.service.imp;

import com.fzy.common.entity.ErrorsMsg;
import com.fzy.common.exception.ServiceException;
import com.fzy.common.utils.Md5Utils;
import com.fzy.common.utils.StringUtils;
import com.fzy.modules.shop.dao.SeckillDao;
import com.fzy.modules.shop.dao.SuccessKilledDao;
import com.fzy.modules.shop.entity.Seckill;
import com.fzy.modules.shop.entity.SuccessKilled;
import com.fzy.modules.shop.entity.dto.Exposer;
import com.fzy.modules.shop.entity.dto.SeckillExcution;
import com.fzy.modules.shop.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by fuzhongyu on 2017/4/19.
 */
@Service
public class SeckillServiceImp implements SeckillService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
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
        return new Exposer(true, Md5Utils.getMd5(seckillId),nowTime.getTime(),startTime.getTime(),endTime.getTime());
    }

    @Override
    @Transactional
    public SeckillExcution executeSeckill(String seckillId, String userPhone, String md5) {
        logger.info("---"+seckillId);
        if(StringUtils.isBlank(md5)||!Md5Utils.getMd5(seckillId).equals(md5)){
            throw new ServiceException(ErrorsMsg.ERR_1001);
        }

        //执行秒杀逻辑:减库存+记录购买
        Date nowTime=new Date();
        int updateCount=seckillDao.reduceNumber(seckillId,nowTime);
        if(updateCount<0){
            throw new ServiceException(ErrorsMsg.ERR_1002);
        }else {
            int insertCount=successKilledDao.insertSuccessKilled(new SuccessKilled(seckillId,userPhone));
            if(insertCount<0){
                throw new ServiceException(ErrorsMsg.ERR_1003);
            }else {
                SuccessKilled successKilled=successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
                return new SeckillExcution(seckillId,1,"秒杀成功",successKilled);
            }
        }
    }
}
