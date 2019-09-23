package com.xingyunfu.ebank.mapper.account;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.dto.account.AccountPageQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EbankAccountMapper {

    EbankAccountDTO findByUserNo(Long userNo);
    EbankAccountDTO findByAccountNo(String accountNo);
    void insert(EbankAccountDTO ebankAccount);
    void update(EbankAccountDTO ebankAccount);
    void updateAmount(Map<String, Object> map);
    List<EbankAccountDTO> pageQuery(AccountPageQueryDTO pageQuery);
    Long pageQueryCount(AccountPageQueryDTO pageQuery);

    List<EbankAccountDTO> listAccount(Map<String, List<String>> map);
}
