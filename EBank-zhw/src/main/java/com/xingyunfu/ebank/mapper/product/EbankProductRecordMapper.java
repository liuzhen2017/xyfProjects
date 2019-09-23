package com.xingyunfu.ebank.mapper.product;

import com.xingyunfu.ebank.domain.product.EbankProductRecordDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EbankProductRecordMapper {
    EbankProductRecordDTO findByEbankOrderNo(String ebankOrderNo);
    List<EbankProductRecordDTO> findByOrderNo(String orderNo);
    void insert(EbankProductRecordDTO ebankProductRecord);
    Integer update(EbankProductRecordDTO ebankProductRecord);
}
