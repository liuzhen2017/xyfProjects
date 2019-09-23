package com.xinyunfu.customer.repository.bank;

import com.xinyunfu.customer.domain.bank.CustBankAccountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustBankAccountMapper {

    void update(CustBankAccountDTO bankAccount);
    List<CustBankAccountDTO> findByUserNo(Long userNo);
    List<Long> findIdByUserNo(Long userNo);
    CustBankAccountDTO findById(Long id);
    void add(CustBankAccountDTO bankAccount);
    CustBankAccountDTO findByAccountNo(String accountNo);
}
