package com.xingyunfu.ebank.service.product;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.channel.EbankChannelDTO;
import com.xingyunfu.ebank.domain.product.EbankProductRecordDTO;
import com.xingyunfu.ebank.dto.product.ProductRecordAddV2DTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.mapper.product.EbankProductRecordMapper;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.xingyunfu.ebank.constant.ProductRecordConstant.*;

@Slf4j
@Service
public class ProductRecordMangerService {
    @Autowired private EbankProductRecordMapper ebankProductRecordMapper;
    @Autowired private AccountMangerService accountMangerService;

    /**
     * 创建购买商品记录，用户向平台转账
     */
    public String add(ProductRecordAddV2DTO productRecordAdd, EbankChannelDTO channel, EbankAccountDTO ebankAccount) throws EBankException {

        //新的订单，写入数据库
        EbankProductRecordDTO record = new EbankProductRecordDTO(productRecordAdd, ebankAccount, channel);
        ebankProductRecordMapper.insert(record);
        return record.getEbankOrderNo();
    }

    /**
     * 申请支付成功 /失败
     */
    public void applySuccessOrFailed(String ebankOrderNo, Boolean success, String sysOrderNo){
        EbankProductRecordDTO ebankProductRecord = new EbankProductRecordDTO();
        ebankProductRecord.setEbankOrderNo(ebankOrderNo);
        ebankProductRecord.setSysOrderNo(sysOrderNo);
        ebankProductRecord.setStatus(success?status_apply_success:status_apply_failed);
        ebankProductRecordMapper.update(ebankProductRecord);
    }

    /**
     * 钱包支付
     */
    public void walletSuccessOrFailed(String ebankOrderNo, Boolean success){
        EbankProductRecordDTO ebankProductRecord = new EbankProductRecordDTO();
        ebankProductRecord.setEbankOrderNo(ebankOrderNo);
        ebankProductRecord.setStatus(success?status_pay_success:status_pay_failed);
        ebankProductRecordMapper.update(ebankProductRecord);
    }

    /**
     * 回调通知，支付成功/失败
     */
    public Integer paySuccessOrFailed(String ebankOrderNo, Boolean success){
        EbankProductRecordDTO ebankProductRecord = new EbankProductRecordDTO();
        ebankProductRecord.setEbankOrderNo(ebankOrderNo);
        ebankProductRecord.setStatus(success?status_pay_success:status_pay_failed);
        return ebankProductRecordMapper.update(ebankProductRecord);
    }

    public EbankProductRecordDTO findByEbankOrderNo(String ebankOrderNo){
        return ebankProductRecordMapper.findByEbankOrderNo(ebankOrderNo);
    }

    public List<EbankProductRecordDTO> findByOrderNo(String orderNo){
        return ebankProductRecordMapper.findByOrderNo(orderNo);
    }
}
