package com.xinyunfu.customer.service.log;

import com.xinyunfu.customer.domain.log.CustClientInfoDTO;
import com.xinyunfu.customer.dto.log.ClientInfoAddDTO;
import com.xinyunfu.customer.repository.log.CustClientInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientInfoService {

    @Autowired private CustClientInfoMapper clientInfoMapper;

    /**
     * 添加登陆设备信息
     */
    @Async
    public void addLoginInfo(ClientInfoAddDTO clientInfoAdd, Long userNo){

        CustClientInfoDTO clientInfo = new CustClientInfoDTO(clientInfoAdd, userNo);
        clientInfoMapper.add(clientInfo);
    }
}
