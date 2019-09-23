package com.xingyunfu.ebank.web.res.transfer;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.transfer.EbankTransferDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.transfer.TransferAccountPageQueryDTO;
import com.xingyunfu.ebank.dto.transfer.TransferAddDTO;
import com.xingyunfu.ebank.dto.transfer.TransferPageQueryData;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.service.paycenter.PayCenterConfig;
import com.xingyunfu.ebank.service.transfer.TransferMangerService;
import com.xingyunfu.ebank.service.transfer.TransferMergeMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 用户间转账管理，实际是系统代付
 */
@Slf4j
@RestController
@RequestMapping("/ebank/transfer")
public class TransferMangerResource {

    @Autowired private TransferMangerService transferMangerService;
    @Autowired private TransferMergeMangerService transferMergeMangerService;
    @Autowired private PayCenterConfig payCenterConfig;

    /**
     * 创建用户间转账
     */
    @PutMapping
    public void transferAccounts(@RequestBody TransferAddDTO transfer) throws EBankException {
        log.info("REST request to transfer accounts. transfer: {}", transfer);
        transferMangerService.addTransfer(transfer);
        log.info("REST request to transfer accounts. success!");
    }

    /**
     * 根据条件查询订单
     */
    @PostMapping("/page")
    public PageDTO<EbankTransferDTO> queryTransfer(@RequestBody TransferPageQueryData query){
        log.info("REST request to query transfer. query: {}", query);

        PageDTO<EbankTransferDTO> page = transferMangerService.queryTransfer(query);

        log.info("REST request to query transfer. success! totalCount: {}", page.getTotalCount());
        return page;
    }

    /**
     * 查询待转账用户
     */
    @PostMapping("/account")
    public PageDTO<EbankAccountDTO> transferAccount(@RequestBody TransferAccountPageQueryDTO query){
        log.info("REST request to query transfer account. query: {}", query);

        PageDTO<EbankAccountDTO> page = transferMangerService.transferAccount(query);

        log.info("REST request to query transfer account. success! totalCount: {}", page.getTotalCount());
        return page;
    }

    /**
     * 触发转账，测试时调用
     */
    @GetMapping
    public void startOneTransfer(@RequestParam String accountNo){
        log.info("REST reqeust to start one transfer. account: {}", accountNo);
        if(!payCenterConfig.getPayRight()){
            log.info("start it!");
            Long endTime = new Date().getTime()/1000;
            transferMergeMangerService.transferApply(accountNo, null, endTime);
        }
        log.info("REST request to start one transfer. success!");
    }
}
