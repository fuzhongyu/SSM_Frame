package com.fzy.exception;


import com.fzy.entity.ErrorsMsg;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errCode;
	private String errMsg;


	public ServiceException() {
		super();
	}

	public ServiceException(String errCode) {
		super();
		this.errCode = errCode;
	}

	public ServiceException(String errCode, String message) {
		super(message);
		this.errCode = errCode;
		this.errMsg = message;
	}

	public ServiceException(ErrorsMsg errorsMsg) {
		super(errorsMsg.getMsg());
		this.errCode = errorsMsg.getCode();
		this.errMsg = errorsMsg.getMsg();
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String errCode, String message, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
		this.errMsg = message;
	}
	public ServiceException(String errCode, Throwable cause) {
		super("", cause);
		this.errCode = errCode;
	}
	
	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
