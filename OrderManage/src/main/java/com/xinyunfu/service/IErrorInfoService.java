package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.model.ErrorInfo;

import java.util.Map;

/**
 * <p>
 * 错误信息表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-09
 */
public interface IErrorInfoService extends IService<ErrorInfo> {

    /**
     * 记录错误消息
     * @param httpMethod
     * @param requestClass
     * @param requestMethod
     * @param requestParam
     * @param responseCode
     * @param responseMessage
     */
    void recordErrorInfo(String httpMethod, String requestClass, String requestMethod, Map<String, Object> requestParam, Integer responseCode, String responseMessage);

}
