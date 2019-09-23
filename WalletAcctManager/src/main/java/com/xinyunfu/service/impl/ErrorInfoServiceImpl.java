package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.entity.ErrorInfo;
import com.xinyunfu.mapper.ErrorInfoMapper;
import com.xinyunfu.service.IErrorInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 错误信息表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-09
 */
@Slf4j
@Service
public class ErrorInfoServiceImpl extends ServiceImpl<ErrorInfoMapper, ErrorInfo> implements IErrorInfoService {

    @Autowired
    private ErrorInfoMapper errorInfoMapper;

    /**
     * 记录错误消息
     *
     * @param httpMethod
     * @param requestClass
     * @param requestMethod
     * @param requestParam
     * @param responseCode
     * @param responseMessage
     */
    @Async
    @Override
    public void recordErrorInfo(String httpMethod, String requestClass, String requestMethod, Map<String, Object> requestParam, Integer responseCode, String responseMessage) {
        ErrorInfo errorInfo = new ErrorInfo()
                .setHttpMethod(httpMethod)
                .setHttpMethod(httpMethod)
                .setRequestClass(requestClass)
                .setRequestMethod(requestMethod)
                .setRequsetParam(requestParam.toString())
                .setResponseCode(String.valueOf(responseCode))
                .setResponseMessage(responseMessage);
        if(errorInfoMapper.insert(errorInfo) == 1){
            log.info("[记录日志]=========>记录错误日志成功！");
        }else{
            log.error("[记录日志]=========>记录错误日志失败！");
        }
    }
}
