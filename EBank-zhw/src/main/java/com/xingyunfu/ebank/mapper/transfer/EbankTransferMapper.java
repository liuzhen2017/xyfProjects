package com.xingyunfu.ebank.mapper.transfer;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.transfer.EbankTransferDTO;
import com.xingyunfu.ebank.dto.transfer.TransferAccountPageQueryDTO;
import com.xingyunfu.ebank.dto.transfer.TransferPageQueryData;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface EbankTransferMapper {
    void insert(EbankTransferDTO ebankTransfer);
    EbankTransferDTO findByOrderNo(String orderNo);
    Integer lockAllOrder(Map<String, Object> map);
    Integer lockOrder(Map<String, Object> map);
    BigDecimal totalAmount(String ebankOrderNo);
    void closeOrder(Map<String, Object> map);
    List<String> findOrderNoByEbankOrderNo(String ebankOrderNo);
    List<String> findTenNeedTransferUser(Map<String, Object> map);

    List<EbankTransferDTO> pageQuery(TransferPageQueryData page);
    Long pageQueryCount(TransferPageQueryData page);

    List<EbankAccountDTO> transferAccount(TransferAccountPageQueryDTO page);
    Long transferAccountCount(TransferAccountPageQueryDTO page);

    List<EbankAccountDTO> findAllNeedTransferUser(Map<String, Object> map);
}
