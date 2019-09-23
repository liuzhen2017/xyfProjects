package com.xingyunfu.ebank.service.account;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.account.EbankAccountExtensionDTO;
import com.xingyunfu.ebank.dto.account.AccountAddDTO;
import com.xingyunfu.ebank.dto.extension.AccountExtensionAddSuperDTO;
import com.xingyunfu.ebank.dto.user.UserInfoDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.mapper.account.EbankAccountExtensionMapper;
import com.xingyunfu.ebank.service.redis.RedisCommonService;
import com.xingyunfu.ebank.service.redis.RedisKeyRules;
import com.xingyunfu.ebank.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class AccountExtensionMangerService {
    @Autowired private EbankAccountExtensionMapper ebankAccountExtensionMapper;
    @Autowired private AccountMangerService accountMangerService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisStringService redisStringService;

    public void setSuperAccount(AccountExtensionAddSuperDTO addSuper) throws EBankException {
        UserInfoDTO superUserInfo = addSuper.getSuperUserInfo();
        EbankAccountDTO superAccount = accountMangerService.findByUserNo(superUserInfo.getUserNo());
        if(Objects.isNull(superAccount))
            superAccount = accountMangerService.addAccount(new AccountAddDTO(superUserInfo));

        for (UserInfoDTO userInfo : addSuper.getUserInfoList()) {
            EbankAccountDTO account = accountMangerService.findByUserNo(userInfo.getUserNo());
            if(Objects.isNull(account))
                account = accountMangerService.addAccount(new AccountAddDTO(userInfo));
            EbankAccountExtensionDTO extension = ebankAccountExtensionMapper.findByAccountNo(account.getAccountNo());
            if(Objects.isNull(extension)) extension = new EbankAccountExtensionDTO();

            extension.setAccountNo(account.getAccountNo());
            extension.setUserPhone(userInfo.getPhone());
            extension.setSuperAccountNo(superAccount.getAccountNo());
            extension.setSuperPhone(superUserInfo.getPhone());

            ebankAccountExtensionMapper.addOrUpdate(extension);
            redisCommonService.delete(RedisKeyRules.ebankAccountExtension(account.getAccountNo()));
        }
    }

    public EbankAccountExtensionDTO findByAccountNo(String accountNo){
        String redisKey = RedisKeyRules.ebankAccountExtension(accountNo);
        if(redisCommonService.exists(redisKey))
            return (EbankAccountExtensionDTO) redisStringService.get(redisKey);
        EbankAccountExtensionDTO extension = ebankAccountExtensionMapper.findByAccountNo(accountNo);
        if(Objects.isNull(extension)) {
            extension = new EbankAccountExtensionDTO();
            extension.setAccountNo(accountNo);
            ebankAccountExtensionMapper.addOrUpdate(extension);
        }
        redisStringService.add(redisKey, extension);
        return extension;
    }
}
