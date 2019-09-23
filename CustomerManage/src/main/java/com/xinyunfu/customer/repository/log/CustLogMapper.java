package com.xinyunfu.customer.repository.log;

import com.xinyunfu.customer.domain.log.CustLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustLogMapper {

    void insert(CustLogDTO log);
    List<CustLogDTO> findByUserNoAndLogType(CustLogDTO log);
    void deleteByUserNoAndLogType(CustLogDTO log);
}
