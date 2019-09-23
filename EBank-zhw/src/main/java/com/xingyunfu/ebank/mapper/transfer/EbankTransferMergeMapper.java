package com.xingyunfu.ebank.mapper.transfer;

import com.xingyunfu.ebank.domain.transfer.EbankTransferMergeDTO;
import com.xingyunfu.ebank.dto.transfer.TransferMergePageQueryDTO;
import com.xingyunfu.ebank.dto.transfer.TransferMergeQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EbankTransferMergeMapper {
    void insert(EbankTransferMergeDTO transferMerge);
    Integer update(EbankTransferMergeDTO transferMerge);
    EbankTransferMergeDTO findByEbankOrderNo(String ebankOrderNo);

    List<EbankTransferMergeDTO> pageQuery(TransferMergePageQueryDTO pageQuery);
    Long pageCount(TransferMergePageQueryDTO pageQuery);

    List<EbankTransferMergeDTO> allMerge(TransferMergeQueryDTO mergeQuery);
}
