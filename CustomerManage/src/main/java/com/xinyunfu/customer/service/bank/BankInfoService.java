package com.xinyunfu.customer.service.bank;

import com.xinyunfu.customer.domain.bank.CustBankInfoDTO;
import com.xinyunfu.customer.repository.bank.CustBankInfoMapper;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisSetService;
import com.xinyunfu.customer.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankInfoService {

    @Autowired private CustBankInfoMapper custBankInfoMapper;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisSetService redisSetService;
    @Autowired private RedisStringService redisStringService;

    public List<CustBankInfoDTO> getAllBankInfo(){
        return this.getBankIdList().stream().map(id -> this.getBankInfo(id))
                .filter(info -> Objects.nonNull(info)).collect(Collectors.toList());
    }

    public CustBankInfoDTO getBankInfo(Long id){
        String redisKey = RedisKeyRules.bankInfo(id);
        if(redisCommonService.exists(redisKey)){
            return (CustBankInfoDTO) redisStringService.get(redisKey);
        }

        CustBankInfoDTO bankInfo = custBankInfoMapper.findById(id);

        redisStringService.add(redisKey, bankInfo);
        return bankInfo;
    }

    private List<Long> getBankIdList(){
        String redisKey = RedisKeyRules.allBankInfo();
        if(redisCommonService.exists(redisKey)) {
            return redisSetService.get(redisKey).stream().map(var -> Long.valueOf(var.toString()))
                    .collect(Collectors.toList());
        }
        List<Long> keyList = custBankInfoMapper.getAllId();
        redisSetService.addList(redisKey, keyList);
        return keyList;
    }
}
