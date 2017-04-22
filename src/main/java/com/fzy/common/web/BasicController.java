package com.fzy.common.web;

import com.fzy.common.entity.ErrorsMsg;
import com.fzy.common.entity.ResponseEntity;
import com.fzy.common.exception.ServiceException;
import com.fzy.common.utils.StringUtils;
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
				responseEntity = new ResponseEntity(ErrorsMsg.ERR_9999);
			}
		}else{
			logger.error(ex.getMessage() ,ex);
			responseEntity = new ResponseEntity(ErrorsMsg.ERR_9999);
		}
		return responseEntity;
    }

	

	
	
}
