package com.xinyunfu.customer.repository.user;

import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.dto.user.UserPageQueryDTO;
import com.xinyunfu.customer.dto.user.UserPhoneListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustUserMapper {
    CustUserDTO findByUserNo(Long userNo);
    void add(CustUserDTO user);
    CustUserDTO findByPhone(String phone);
    List<CustUserDTO> findByPhoneList(UserPhoneListDTO phone);
    void update(CustUserDTO user);
    CustUserDTO findByUserCode(String userCode);
    List<Long> findIdByReferrerNo(Long referrerNo);
    List<Long> findUserNoByReferrerNo(Map<String, Object> map);
    Long findReferrerTotal(Long referrerNo);

    List<Long> findUserNoPage(UserPageQueryDTO pageQuery);
    Long totalPage(UserPageQueryDTO pageQuery);
}
