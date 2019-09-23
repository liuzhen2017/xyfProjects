package com.xingyunfu.ebank.web.res.transfer;

import com.xingyunfu.ebank.dto.user.UserPhoneListDTO;
import com.xingyunfu.ebank.service.transfer.TransferForbidMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ebank/transfer/forbid")
public class TransferForbidMangerResource {
    @Autowired private TransferForbidMangerService transferForbidMangerService;

    @PostMapping("/add")
    public void add(@RequestBody@Validated UserPhoneListDTO phoneList){
        log.info("REST request to add transfer forbid. phoneList: {}", phoneList);
        transferForbidMangerService.add(phoneList);
        log.info("REST request to add transfer forbid. success!");
    }
}
