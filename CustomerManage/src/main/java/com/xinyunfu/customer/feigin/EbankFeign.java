package com.xinyunfu.customer.feigin;

import com.xinyunfu.customer.dto.bank.AccountExtensionAddSuperDTO;
import com.xinyunfu.customer.dto.ebank.AccountAddDTO;
import com.xinyunfu.customer.dto.ebank.AccountUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("EBank")
@RequestMapping("/ebank/account")
public interface EbankFeign {
    @PutMapping
    void addAccount(@RequestBody AccountAddDTO accountAdd);

    @PostMapping
    void updateAccount(@RequestBody AccountUpdateDTO accountUpdate);

    @PostMapping("/extension/super")
    void setSuperAccount(@RequestBody AccountExtensionAddSuperDTO addSuper);
}
