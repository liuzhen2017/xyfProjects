package com.xinyunfu.customer.repository.user;

import com.xinyunfu.customer.domain.user.CustUserExtensionDTO;
import com.xinyunfu.customer.dto.user.UserReferrerInfoDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustUserExtensionMapper {

    CustUserExtensionDTO findByUserNo(Long userNo);
    Integer update(CustUserExtensionDTO extension);
    void insert(CustUserExtensionDTO extension);
    UserReferrerInfoDTO selectReferrerMeal(Long userNo);
    //粉丝实名认证数
    Integer selectTotalCardCount(Long userNo);
    //粉丝绑定银行卡数
    Integer selectTotalBankCount(Long userNo);
}
