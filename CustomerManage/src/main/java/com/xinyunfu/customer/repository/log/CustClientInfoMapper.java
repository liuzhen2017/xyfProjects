package com.xinyunfu.customer.repository.log;

import com.xinyunfu.customer.domain.log.CustClientInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustClientInfoMapper {

    void add(CustClientInfoDTO clientInfo);
}
