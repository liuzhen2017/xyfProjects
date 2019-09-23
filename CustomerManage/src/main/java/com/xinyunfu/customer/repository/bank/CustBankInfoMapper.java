package com.xinyunfu.customer.repository.bank;

import com.xinyunfu.customer.domain.bank.CustBankInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustBankInfoMapper {

    List<Long> getAllId();
    CustBankInfoDTO findById(Long id);
}
