package com.xinyunfu.aop;

import com.xinyunfu.jace.utils.ResponseInfo;
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
     * 为其他服务提供的构造函数
     * @param message
     */
    public CustomException(ExecptionEnum e, ResponseInfo<Object> res){
        e.setCode(Integer.parseInt(res.getCode()));                 //保留其他服务的状态码
        e.setMessage(res.getMessage());                             //保留其他服务的消息
        this.ee = e;
        this.other = true;
    }

}
