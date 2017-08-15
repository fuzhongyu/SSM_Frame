package com.fzy.web;

import com.fzy.entity.ErrorsMsg;
import com.fzy.entity.ResponseEntity;
import com.fzy.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器基类
 */
public abstract class BasicController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 参数绑定异常
	 * 注：当这个Controller中任何一个方法发生异常，会被这个方法拦截到
	 */
	@ResponseBody
	@ExceptionHandler()
    public ResponseEntity bindException(Exception ex) {
		ResponseEntity responseEntity = null;
		if(ex instanceof ServiceException){
			ServiceException serviceException = (ServiceException) ex;
			if (StringUtils.isNotBlank(serviceException.getErrCode())){
				responseEntity = new ResponseEntity(serviceException.getErrCode(),serviceException.getMessage(),null);
			}else{
				logger.error(ex.getMessage() ,ex);
				responseEntity = new ResponseEntity(ErrorsMsg.ERR_9999,null);
			}
		}else{
			logger.error(ex.getMessage() ,ex);
			responseEntity = new ResponseEntity(ErrorsMsg.ERR_9999,null);
		}
		return responseEntity;
    }

	

	
	
}
