package com.xinyunfu.customer.service.user;

import com.xinyunfu.customer.config.CustomerVerifyCodeConfig;
import com.xinyunfu.customer.domain.user.CustReferralCodeSpecialDTO;
import com.xinyunfu.customer.repository.user.CustReferralCodeSpecialMapper;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RefferralCodeSpecialService {
    @Autowired private CustReferralCodeSpecialMapper custReferralCodeSpecialMapper;
    @Autowired private CustomerVerifyCodeConfig customerVerifyCodeConfig;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisSetService redisSetService;

    @PostConstruct
    public void init(){
        String defaultCode = customerVerifyCodeConfig.getDefaultCode();
        if(Objects.isNull(this.findByReferralCode(defaultCode))){
            log.info("add server referrer code!");
            this.add(defaultCode, "系统推荐码，禁止使用！");
            redisCommonService.delete(RedisKeyRules.specialReferral());
        }
    }

    public CustReferralCodeSpecialDTO findByReferralCode(String referralCode){
        return custReferralCodeSpecialMapper.findByReferralCode(referralCode);
    }

    public void update(Long id, Boolean used){
        CustReferralCodeSpecialDTO code = new CustReferralCodeSpecialDTO();
        code.setId(id);
        code.setUsed(used);

        custReferralCodeSpecialMapper.update(code);
    }

    public void update(String referralCode, String remark){
        CustReferralCodeSpecialDTO code = new CustReferralCodeSpecialDTO();
        code.setReferralCode(referralCode);
        code.setRemark(remark);

        custReferralCodeSpecialMapper.updateByReferralCode(code);
    }

    public void add(String referralCode, String remark){
        CustReferralCodeSpecialDTO code = new CustReferralCodeSpecialDTO();
        code.setUsed(false);
        code.setReferralCode(referralCode);
        code.setRemark(remark);

        custReferralCodeSpecialMapper.add(code);
        redisCommonService.delete(RedisKeyRules.specialReferral());
    }

    public List<String> findAllReferralCode(){
        String redisKey = RedisKeyRules.specialReferral();
        if(redisCommonService.exists(redisKey)){
            return redisSetService.get(redisKey).stream().map(o -> o.toString()).collect(Collectors.toList());
        }
        List<String> list = custReferralCodeSpecialMapper.findAllReferralCode()
                .stream().map(var -> var.toUpperCase()).collect(Collectors.toList());
        redisSetService.addStrList(redisKey, list);
        return list;
    }
}
