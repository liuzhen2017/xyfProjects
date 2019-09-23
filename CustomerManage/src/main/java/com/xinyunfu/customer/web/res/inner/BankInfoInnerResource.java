package com.xinyunfu.customer.web.res.inner;

import com.xinyunfu.customer.domain.bank.CustBankAccountDTO;
import com.xinyunfu.customer.dto.bank.BankAccountInfoDTO;
import com.xinyunfu.customer.service.bank.BankAccountManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/customer/inner/bank")
public class BankInfoInnerResource {

    @Autowired private BankAccountManagerService bankAccountManagerService;
    /**
     * 查询当前用户所有的银行卡，包含以及银行卡相关信息
     */
    @GetMapping("/account/all")
    public List<BankAccountInfoDTO> getBankAccount(@RequestParam Long userNo){
        log.info("REST request to get bank account. userNo: {}", userNo);
        List<CustBankAccountDTO> accounts = bankAccountManagerService.queryAccount(userNo);
        if(Objects.isNull(accounts)) accounts = new ArrayList<>();
        List<BankAccountInfoDTO> result = accounts.stream().map(acc -> new BankAccountInfoDTO(acc, false)).collect(Collectors.toList());
        log.info("REST request to get bank account. success! result number: {}", result.size());
        return result;
    }
}
