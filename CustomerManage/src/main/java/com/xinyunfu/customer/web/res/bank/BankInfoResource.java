package com.xinyunfu.customer.web.res.bank;

import com.xinyunfu.customer.domain.bank.CustBankInfoDTO;
import com.xinyunfu.customer.dto.SingleIdDTO;
import com.xinyunfu.customer.service.bank.BankInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer/bank")
public class BankInfoResource {

    @Autowired private BankInfoService bankInfoService;

    /**
     * 查询所有银行信息
     */
    @PostMapping("/all")
    public List<CustBankInfoDTO> getAllBankInfo(){

        log.info("REST request to get all bank info.");
        List<CustBankInfoDTO> result = bankInfoService.getAllBankInfo();
        log.info("REST request to get all bank info. success! bank number: {}", result.size());
        return result;
    }

    /**
     * 查询单个银行信息
     */
    @PostMapping("/one")
    public CustBankInfoDTO getBankInfo(@RequestBody SingleIdDTO singleId){
        Long id = singleId.getId();
        log.info("REST request to get one bank info. id: {}", id);
        CustBankInfoDTO result = bankInfoService.getBankInfo(id);
        log.info("REST request to get one bank info. success!");
        return result;
    }
}
