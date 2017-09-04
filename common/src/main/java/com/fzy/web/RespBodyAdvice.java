package com.fzy.web;

import com.fzy.entity.ResponseEntity;
import com.fzy.interceptor.LogInterceptor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数拦截器，获取返回参数
 * Created by fuzhongyu on 2017/8/30.
 */
@ControllerAdvice
public class RespBodyAdvice implements ResponseBodyAdvice<ResponseEntity> {


    @Override
    public ResponseEntity beforeBodyWrite(ResponseEntity responseEntity, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println("=========>responseEntity"+responseEntity);
        Map<String,Object> returnParamsMap=new HashMap();
        if(responseEntity!=null){
            returnParamsMap.put("code",responseEntity.getCode());
            returnParamsMap.put("msg",responseEntity.getMsg());
        }

        LogInterceptor.executeResultThreadLocal.set(returnParamsMap);

        return responseEntity;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //do nothing
        return true;
    }
}
