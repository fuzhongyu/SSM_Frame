package com.fzy.modules.shop.entity;

import com.fzy.common.entity.BaseEntity;

import java.util.Date;

/**
 * Created by fuzhongyu on 2017/4/17.
 */
public class SuccessKilled extends BaseEntity{

    private String seckillId; //商品id
    private String userPhone; //用户手机号
    private Integer state; //状态：-1-无，0-秒杀成功，1-已付款，2-已发货
    private Date createTime; //创建时间
    private Seckill seckill; //商品

    public SuccessKilled(){}

    public SuccessKilled(String seckillId, String userPhone) {
        this.seckillId = seckillId;
        this.userPhone = userPhone;
    }

    public String getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(String seckillId) {
        this.seckillId = seckillId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }
}
