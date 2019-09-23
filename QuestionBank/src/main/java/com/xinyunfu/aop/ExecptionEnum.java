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

    ADD_SUBJECT_NUMBER_FAIL(4013,"增加万能券点亮数失败！"),
    UPDATE_SUJBECT_MASTER_FAIL(4012,"修改答题主对象失败！"),
    UPDATE_SUJBECT_ITEM_FAIL(4011,"修改答题子对象失败！"),
    SAVE_SUBJECT_MASTER_FAIL(4010,"生成主答题对象失败！"),
    SAVE_SUBJECT_FAIL(4009,"万能券生成六道题失败！"),
    ERROR_PAGE_OUT(4008,"页数超出范围！"),
    SUBJECT_INIT_FAIL(4007,"初始化题目记录失败！"),
    SUBJECT_TYPE_NULL(4006,"题目类型为空！"),
    CLEAR_CACHE_FAIL(4005,"清除缓存失败！"),
    ERROR_SAVE_FAIL(4004,"导入题库失败！"),
    ERROR_RESUBMINT(4003,"该题已答，请勿重复提交",false,true),
    ERROR_PARAM(4002,"请求参数有误！"),
    ERROR_UPDATE_ANSWER(4001,"更新答题记录失败！"),
    ERROR_FILE_TYPE(4000,"文件格式不正确！",false,true),
    /**
     * 提供给其他服务用 不需要记录日志，不需要展示给前端看
     */
    TYPE_NOTLOG_NOTSHOW(0,"",false,true),
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
    TYPE_LOG_NOTSHOW(0,"",true,true);

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
