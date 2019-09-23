package com.xinyunfu.customer.web.res.bank;

import com.xinyunfu.customer.domain.bank.CustBankAccountDTO;
import com.xinyunfu.customer.dto.SingleIdDTO;
import com.xinyunfu.customer.dto.bank.BankAccountAddDTO;
import com.xinyunfu.customer.dto.bank.BankAccountInfoDTO;
import com.xinyunfu.customer.dto.user.UserCardCheckResultDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.bank.BankAccountManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xinyunfu.customer.constant.CommonConstants.header_uid;

/**
 * 银行卡管理
 */
@Slf4j
@RestController
@RequestMapping("/customer/account")
public class BankAccountManagerResource {

    @Autowired private BankAccountManagerService bankAccountManagerService;

    /**
     * 添加银行卡
     */
    @PostMapping
    public UserCardCheckResultDTO addAccount(@RequestHeader(header_uid)Long userNo, @Validated @RequestBody BankAccountAddDTO bankAccountAdd) throws CustomerException {
        log.info("REST request to add account. userNo: {}, bankAccountAdd: {}", userNo, bankAccountAdd);
        UserCardCheckResultDTO result = bankAccountManagerService.addAccount(userNo, bankAccountAdd);
        log.info("REST request to add account. success! result: {}", result);
        return result;
    }

    /**
     * 删除银行卡
     */
    @PostMapping("/delete")
    public void deleteAccount(@RequestHeader(header_uid)Long userNo, @RequestBody SingleIdDTO singleId){
        Long accountId = singleId.getId();
        log.info("REST request to delete account. userNo: {}, accountId: {}", userNo, accountId);
        bankAccountManagerService.deleteAccount(userNo, accountId);
        log.info("REST request to delete account. success!");
    }

    /**
     * 查询当前用户银行卡
     */
    @PostMapping("/all")
    public List<BankAccountInfoDTO> queryAccount(@RequestHeader(header_uid)Long userNo){
        log.info("REST request to query account. userNo: {}", userNo);
        List<CustBankAccountDTO> accounts = bankAccountManagerService.queryAccount(userNo);
        if(Objects.isNull(accounts)) accounts = new ArrayList<>();
        List<BankAccountInfoDTO> result = accounts.stream().map(acc -> new BankAccountInfoDTO(acc, true)).collect(Collectors.toList());
        log.info("REST request to query account. success! account number: {}", result.size());
        return result;
    }

    /**
     * 查询单个银行卡的信息
     */
    @PostMapping("/one")
    public BankAccountInfoDTO queryOneAccount(@RequestHeader(header_uid)Long userNo, @RequestBody SingleIdDTO singleId){
        log.info("REST request to query one account. userNo: {}, id: {}", userNo, singleId.getId());
        CustBankAccountDTO account = bankAccountManagerService.findById(singleId.getId());
        BankAccountInfoDTO result = new BankAccountInfoDTO(account,true);
        log.info("REST request to query account. success!");
        return result;
    }
}
