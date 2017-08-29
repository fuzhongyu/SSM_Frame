package com.fzy.shop;

import com.fzy.entity.BaseEntity;
import com.fzy.shop.annota_valide.ValParam_3;
import com.fzy.shop.annota_valide.ValideParam;
import com.fzy.shop.annota_valide.ValideParam_2;
import com.fzy.shop.annota_valide.ValideParam_4;
import com.fzy.shop.annota_valide.inter.ValInt;
import com.fzy.shop.annota_valide.inter.ValInt_2;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by fuzhongyu on 2017/4/17.
 */
//同时属于两个验证分组时指定验证顺序
@GroupSequence({ValInt_2.class, ValInt.class,Seckill.class})
public class Seckill extends BaseEntity<Seckill> {

    @ValideParam_2(message = "name长度不符",groups = ValInt_2.class)
//    @ValParam_3(groups = ValInt.class)
    @ValideParam_4.List({@ValideParam_4(forName = "admin",message = "名字不能为admin",groups = ValInt.class),@ValideParam_4(forName = "root",message = "名字不能为root",groups = ValInt.class)})
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
