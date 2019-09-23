package com.xingyunfu.ebank.web.res.account;

import com.xingyunfu.ebank.dto.extension.AccountExtensionAddSuperDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.service.account.AccountExtensionMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ebank/account/extension")
public class AccountExtensionMangerResource {

    @Autowired private AccountExtensionMangerService accountExtensionMangerService;

    @PostMapping("/super")
    public void setSuperAccount(@RequestBody@Validated AccountExtensionAddSuperDTO addSuper) throws EBankException {
        log.info("REST request to set super account. addSuper: {}", addSuper);
        accountExtensionMangerService.setSuperAccount(addSuper);
        log.info("REST request to set super account. success!");
    }
}
