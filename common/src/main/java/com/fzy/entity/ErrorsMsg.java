package com.fzy.entity;

/**
 * Created by fuzhongyu on 2017/4/19.
 */
public enum ErrorsMsg {

    SUCC_1("1","操作成功"),
    ERR_9999("9999","操作异常"),

    ERR_1001("1001","数据被篡改"),
    ERR_1002("1002","秒杀结束"),
    ERR_1003("1003","重复秒杀"),
    ERR_1004("1004","用户未注册");


    private String code; //相应状态码
    private String msg;  //响应吗说明

    ErrorsMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
