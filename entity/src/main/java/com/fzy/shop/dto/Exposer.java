package com.fzy.shop.dto;

/**
 * Created by fuzhongyu on 2017/4/19.
 */
public class Exposer {

    private boolean exposed;  //是否已经开启了秒杀

    private String md5;  //md5值

    private String seckillId;  //商品id

    private long now; //现在时间

    private long start; //秒杀开始时间

    private long end;  //秒杀结束时间

    public Exposer(){}

    public Exposer(boolean exposed, String seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, String seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, String seckillId,  String md5,long now, long start, long end) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(String seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
