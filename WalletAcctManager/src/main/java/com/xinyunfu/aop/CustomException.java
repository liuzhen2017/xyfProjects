package com.xinyunfu.aop;

import lombok.Getter;

/**
 * @author XRZ
 * @date 2019/7/31
 * @Description : 自定义异常类
 */
@Getter
public class CustomException extends RuntimeException{

    /**
     * 异常枚举
     */
    private ExecptionEnum ee;

    /**
     * 是否为其他服务的错误消息
     */
    private boolean other;


    public CustomException(ExecptionEnum e){
        this.ee = e;
    }

    /**
     * 动态错误异常 (不记录入库，展示给前端看)
     * @param message
     */
    public CustomException(String message){
        this.ee = ExecptionEnum.THIS_MESSAGE;
        ee.setMessage(message); //重写Message
    }

    /**
     * 为其他服务提供的构造函数
     * @param e
     * @param code
     * @param message
     */
    public CustomException(ExecptionEnum e,String code,String message) {
        e.setCode(Integer.parseInt(code));                  //保留其他服务的状态码
        e.setMessage(message);     //保留其他服务的消息
        this.ee = e;
        this.other = true;
    }
}
