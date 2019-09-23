package com.xinyunfu.customer.service.log;

import com.xinyunfu.customer.constant.LogTypeConstant;
import com.xinyunfu.customer.domain.log.CustLogDTO;
import com.xinyunfu.customer.exception.CustomerExceptionEnum;
import com.xinyunfu.customer.repository.log.CustLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerLogService {

    @Autowired private CustLogMapper custLogMapper;

    @Async
    public void saveLogWithException(CustomerExceptionEnum e){
        if(e.getSaveLog()) {
            custLogMapper.insert(
                    new CustLogDTO(e.getUserNo(), e.getLogType(),
                            String.format(e.getLogTemp(), e.getLogData()) + ", " + e.getDesc()));
        }
    }

    @Async
    public void saveLog(Long userNo, String logType, String details){

        //登陆日志，删除旧的登陆日志
        if(logType.equals(LogTypeConstant.USER_LOGIN_IN)){
            custLogMapper.deleteByUserNoAndLogType(new CustLogDTO(userNo, logType));
        }

        custLogMapper.insert(new CustLogDTO(userNo, logType, details));
    }

    /**
     * 根据userNo/logType查询前10条日志
     */
    public List<CustLogDTO> queryLog(Long userNo, String lotType){
        return custLogMapper.findByUserNoAndLogType(new CustLogDTO(userNo, lotType));
    }
}
