package com.xinyunfu.customer.exception;

import lombok.Getter;

@Getter
public class CustomerException extends Exception{
    private Integer errCode;
    private String desc;
    private CustomerExceptionEnum exceptionEnum;

    public CustomerException(CustomerExceptionEnum e){
        super(e.getErrCode() + " - " + e.getDesc());
        this.errCode = e.getErrCode();
        this.desc = e.getDesc();
        this.exceptionEnum = e;
    }
}
