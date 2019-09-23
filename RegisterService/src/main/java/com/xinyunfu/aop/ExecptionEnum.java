package com.xinyunfu.aop;

import lombok.Getter;
import lombok.Setter;

/**
 * @author XRZ
 * @date 2019/7/31
 * @Description : 异常消息枚举
 */
@Getter
public enum ExecptionEnum {

    KEY_CANNOT_BE_RESOLVED(4444,"很抱歉，您的数据有误！"),

    /**
     * 提供给其他服务用 不需要记录日志，不需要展示给前端看
     */
    TYPE_NOTLOG_NOTSHOW(0,""),
    /**
     * 提供给其他服务用 需要记录日志，需要展示给前端看
     */
    TYPE_LOG_SHOW(0,"",true,true),
    /**
     * 提供给其他服务用 不需要记录日志，需要展示给前端看
     */
    TYPE_NOTLOG_SHOW(0,"",false,true),
    /**
     * 提供给其他服务用 需要记录日志，不需要展示给前端看
     */
    TYPE_LOG_NOTSHOW(0,"",true,false);

    /**
     * 错误状态码
     */
    @Setter
    private int code;

    /**
     * 错误消息
     */
    @Setter
    private String message;

    /**
     * 是否需要入库记录日志
     */
    private boolean log;

    /**
     * 是否需要显示给前端看
     */
    private boolean show;

    /**
     * 抛出异常信息，不需要显示给前端看和入库记录
     *
     * @param code    状态码
     * @param message 信息
     */
    ExecptionEnum(int code,String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 抛出异常信息
     *
     * @param code      状态码
     * @param message   消息
     * @param log       是否需要入库记录日志
     * @param show      是否需要展示消息给前端看
     */
    ExecptionEnum(int code,String message, boolean log, boolean show) {
        this.code = code;
        this.message = message;
        this.log = log;
        this.show = show;
    }

    /**
     * 根据 错误状态码获取 错误消息
     * @param code
     * @return
     */
    public static String getMessageByCode(int code) {
        for (ExecptionEnum state : values())
            if (state.getCode() == code)
                return state.getMessage();
        return null;
    }


}
