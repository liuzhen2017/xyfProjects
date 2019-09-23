package com.gateway.exception;

import java.text.MessageFormat;

/**
 * 描述 专门处理系统运行时异常并记录异常信息到日志文件
 *
 * @author huyc
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private  String excepitonCode = "9999";
	
	public String getExcepitonCode() {
		return excepitonCode;
	}


	public void setExcepitonCode(String excepitonCode) {
		this.excepitonCode = excepitonCode;
	}


	public BusinessException() {
        super();
    }


    public BusinessException(String message,String exceptionCode) {
        super(message);
    	this.excepitonCode = exceptionCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null || "".equals(message)) {
            message = this.getMessage();
        }
        return message;
    }

    public String getMessage(Object... args) {
        String message = getMessage();
        return MessageFormat.format(message, args);
    }
    
    
    
}
