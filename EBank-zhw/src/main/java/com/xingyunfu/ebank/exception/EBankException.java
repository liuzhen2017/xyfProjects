package com.xingyunfu.ebank.exception;

import lombok.Getter;

@Getter
public class EBankException extends Exception{
    private Integer errCode;
    private String desc;
    private EBankExceptionEnum exceptionEnum;

    public EBankException(EBankExceptionEnum e){
        super(e.getErrCode() + " - " + e.getDesc());
        this.errCode = e.getErrCode();
        this.desc = e.getDesc();
        this.exceptionEnum = e;
    }
}
