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

    TIMING_CHANGE_ORDER_STATUS_FAIL(7015,"定时结算时修改订单的结算状态失败！"),
    TIMING_CHANGE_ORDER_FAIL(7014,"定时结算时修改用户状态失败！"),
    VALUE_CHECK_FAILED(7013,"校验支付金额和冻结金额不通过！",true,false),
    ERROR_FAIL(7012,"很抱歉，操作失败! 请联系后台开发人员！",true,true),
    WITHDRAWAL_FIAL(7011,"很抱歉，提现失败! 请联系后台开发人员！",true,true),
    THIS_MESSAGE(7010,"X",false,true),
    ERROR_CREATE_USER_FAIL(7009,"创建用户失败！请联系后台开发人员！"),
    ERROR_COUNPONS_FAIL(7008,"消费失败！请联系后台开发人员！",true,false),
    ERROR_GET_ORDER_INFO(7007,"获取待结算的订单信息失败！",true,false),
    GET_USER_INFO_FAIL(7006,"获取用户记录失败！查无此人！",false,false),
    ERROR_INSUFFICIENT_BALANCE(7005,"很抱歉,余额不足！",false,true),
    REFILL_FAILED(7004,"很抱歉，充值失败！请联系后台开发人员！",true,true),
    SYSTEM_ACCOUNT_ENTRY(7003,"很抱歉！系统账户入账失败！请联系后台开发人员！",true,false),
    NOT_YET_SUPPORTED(7002,"暂不支持此类型开户！"),
    CLEAR_CACHE_FAIL(7001,"清除缓存失败！"),
    ERROR_PARAM(7000,"请求参数有误！"),
    /**
     * 提供给其他服务用
     */
    ERROR_OTHER_SERVICE(0,"",false,true);


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
