package com.xinyunfu.customer.service.user;

import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.domain.user.CustUserExtensionDTO;
import com.xinyunfu.customer.dto.bank.AccountExtensionAddSuperDTO;
import com.xinyunfu.customer.dto.extension.UserExtensionSetSuperUserDTO;
import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.customer.dto.user.UserPhoneListDTO;
import com.xinyunfu.customer.dto.user.UserReferrerInfoDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.feigin.EbankFeign;
import com.xinyunfu.customer.repository.user.CustUserExtensionMapper;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xinyunfu.customer.exception.CustomerExceptionEnum.USER_EXTENSION_NON_PHONE;

@Slf4j
@Service
public class UserExtensionMangerService {
    @Autowired private CustUserExtensionMapper custUserExtensionMapper;
    @Autowired private UserManagerService userManagerService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisStringService redisStringService;
    @Autowired private EbankFeign ebankFeign;

    public CustUserExtensionDTO findByUserNo(Long userNo){
        String redisKey = RedisKeyRules.userExtension(userNo);
        if(redisCommonService.exists(redisKey))
            return (CustUserExtensionDTO) redisStringService.get(redisKey);
        CustUserExtensionDTO extension = custUserExtensionMapper.findByUserNo(userNo);
        if(Objects.isNull(extension)) {
            extension = new CustUserExtensionDTO(userNo, 0);
            custUserExtensionMapper.insert(extension);
        }

        redisStringService.add(redisKey, extension);
        return extension;
    }

    public void update(CustUserExtensionDTO extension){
        custUserExtensionMapper.update(extension);
        redisCommonService.delete(RedisKeyRules.userExtension(extension.getUserNo()));
    }

    public void update(UserExtensionSetSuperUserDTO setSuperUser) throws CustomerException {
        List<String> allPhone = new ArrayList<>(setSuperUser.getPhoneList());
        allPhone.add(setSuperUser.getSuperPhone());
        List<CustUserDTO> userList = userManagerService.findByPhoneList(new UserPhoneListDTO(allPhone));
        allPhone.removeAll(userList.stream().map(CustUserDTO::getPhone).collect(Collectors.toList()));
        if(allPhone.size()>0) throw new CustomerException(USER_EXTENSION_NON_PHONE.setDesc("手机号" + allPhone + "未注册"));

        UserInfoDTO superUserInfo = userList.stream().filter(var -> var.getPhone().equals(setSuperUser.getSuperPhone()))
                .map(var -> new UserInfoDTO(var, false)).findFirst().get();
        List<UserInfoDTO> userInfoList = userList.stream().filter(var -> !var.getPhone().equals(setSuperUser.getSuperPhone()))
                .map(var -> new UserInfoDTO(var, false)).collect(Collectors.toList());

        for (UserInfoDTO userInfo : userInfoList) {
            CustUserExtensionDTO extension = this.findByUserNo(userInfo.getUserNo());
            extension.setSuperUserNo(superUserInfo.getUserNo());
            this.update(extension);
        }

        ebankFeign.setSuperAccount(new AccountExtensionAddSuperDTO(superUserInfo, userInfoList));
    }

    public UserReferrerInfoDTO referrerTotalMeal(Long userNo){
        UserReferrerInfoDTO userReferrerInfoDTO= custUserExtensionMapper.selectReferrerMeal(userNo);
        //获取粉丝实名认证数
        Integer totalCardCount=custUserExtensionMapper.selectTotalCardCount(userNo);
        //获取粉丝绑定银行卡数
        Integer totalBankCount=custUserExtensionMapper.selectTotalBankCount(userNo);
        userReferrerInfoDTO.setTotalCardCount(totalCardCount);
        userReferrerInfoDTO.setTotalBankCount(totalBankCount);
        return  userReferrerInfoDTO;
    }
}
