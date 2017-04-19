package com.fzy.common.entity;

import java.util.HashMap;

/**
 *  响应实体
 * Created by fuzhongyu on 2017/4/19.
 */
public class ResponseEntity {

    private static final long serialVersionUID = 1L;

    public ResponseEntity(){
        this.code = ErrorsMsg.SUCC_1.getCode(); //默认响应成功
        this.msg = ErrorsMsg.SUCC_1.getMsg();
    }

    public ResponseEntity(Object result){
        this.code = ErrorsMsg.SUCC_1.getCode(); //默认响应成功
        this.msg = ErrorsMsg.SUCC_1.getMsg();
        this.result = result;
    }


    public ResponseEntity(ErrorsMsg errorsMsg, Object result){
        this.code = errorsMsg.getCode();
        this.msg = errorsMsg.getMsg();
        if(result == null)
            this.result = new HashMap<String,String>();
        else
            this.result = result;
    }

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应码说明
     */
    private String msg;

    /**
     * 返回结果, 需能正确转化成json串
     */
    private Object result;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


}
