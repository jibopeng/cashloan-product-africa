package com.ajaya.cashloan.core.common.exception;

/**
 * 访问码异常
 * @author dufy
 * @version 1.0
 * @date 2017年4月25日
 */
public class SysAccessCodeException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	protected static final int SERVICE_EXCEPTION__CODE = 400;
	
	protected int code;
	
	public SysAccessCodeException(int code, String businessMessage) {
		this(businessMessage);
	}

	public SysAccessCodeException(String businessMessage, Throwable cause, int code) {
		this(businessMessage, cause);
	}

	public SysAccessCodeException(String businessMessage, Throwable cause) {
		super(businessMessage, cause);
		this.code = SERVICE_EXCEPTION__CODE;
	}

	public SysAccessCodeException(String message) {
		super(message);
		this.code = SERVICE_EXCEPTION__CODE;

	}

	public SysAccessCodeException(Throwable t) {
		this(t.getMessage(), t);
	}
	
	
	
}
