package com.xingyunfu.ebank.web.res.account;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.account.AccountAddDTO;
import com.xingyunfu.ebank.dto.account.AccountPageQueryDTO;
import com.xingyunfu.ebank.dto.account.AccountUpdateDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.xingyunfu.ebank.constant.InnerAccountConstant.accountType_system;

/**
 * 虚拟账号管理
 */
@Slf4j
@RestController
@RequestMapping("/ebank/account")
public class AccountMangerResource {

    @Autowired private AccountMangerService accountMangerService;

    /**
     * 创建内部账户，创建用户时调用此接口
     */
    @PutMapping
    public void addAccount(@Validated @RequestBody AccountAddDTO accountAdd) throws EBankException {
        log.info("REST request to add account. accountAdd: {}", accountAdd);
        if(accountAdd.getAccountType().equals(accountType_system)) return;
        accountMangerService.addAccount(accountAdd);
        log.info("REST request to add account. success!");
    }

    /**
     * 分页查询内部账户
     */
    @PostMapping("/page")
    public PageDTO<EbankAccountDTO> pageQuery(@RequestBody@Validated AccountPageQueryDTO pageQuery){
        log.info("REST request to page query account. pageQuery: {}", pageQuery);
        PageDTO<EbankAccountDTO> page = accountMangerService.pageQuery(pageQuery);
        log.info("REST request to page query account. success! totalCount: {}", page.getTotalCount());
        return page;
    }

    /**
     * 账户查询
     */
    @GetMapping
    public EbankAccountDTO getAccount(@RequestParam Long userNo){
        log.info("REST request to add account. userNo: {}", userNo);
        EbankAccountDTO account = accountMangerService.findByUserNo(userNo);
        log.info("REST request to query account. success! userNo: {}", userNo);
        return account;
    }

    /**
     * 更新账户
     */
    @PostMapping
    public void updateAccount(@RequestBody AccountUpdateDTO accountUpdate){
        log.info("REST request to update account. accountUpdate: {}", accountUpdate);
        accountMangerService.updateAccount(accountUpdate);
        log.info("REST request to update account. success!");
    }
}
