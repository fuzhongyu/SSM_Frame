package com.fzy.modules.shop.entity;

import com.fzy.common.entity.BaseEntity;

import java.util.Date;

/**
 * Created by fuzhongyu on 2017/4/17.
 */
public class Seckill extends BaseEntity {

    private String name;  //商品名称
    private Integer number;  //商品库存
    private Date startTime;  //秒杀开始时间
    private Date endTime;  //秒杀结束时间
    private Date createTime;  //商品创建时间


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
