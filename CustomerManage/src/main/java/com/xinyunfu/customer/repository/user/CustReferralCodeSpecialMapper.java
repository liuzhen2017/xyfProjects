package com.xinyunfu.customer.repository.user;

import com.xinyunfu.customer.domain.user.CustReferralCodeSpecialDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustReferralCodeSpecialMapper {

    CustReferralCodeSpecialDTO findByReferralCode(String referralCode);
    void update(CustReferralCodeSpecialDTO referralCodeSpecial);
    void add(CustReferralCodeSpecialDTO referralCodeSpecial);
    void updateByReferralCode(CustReferralCodeSpecialDTO referralCodeSpecial);
    List<String> findAllReferralCode();
}
