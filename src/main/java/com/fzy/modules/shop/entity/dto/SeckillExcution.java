package com.fzy.modules.shop.entity.dto;

import com.fzy.modules.shop.entity.SuccessKilled;

/**
 * 秒杀执行结果
 * Created by fuzhongyu on 2017/4/19.
 */
public class SeckillExcution {

    private String seckillId; //秒杀商品id

    private Integer state; //秒杀执行结果状态  1-成功，0-失败

    private String stateInfo; //状态表示

    private SuccessKilled successKilled;  //秒杀成功对象

    public SeckillExcution() {}

    public SeckillExcution(String seckillId, Integer state, String stateInfo, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.successKilled = successKilled;
    }

    public String getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(String seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
