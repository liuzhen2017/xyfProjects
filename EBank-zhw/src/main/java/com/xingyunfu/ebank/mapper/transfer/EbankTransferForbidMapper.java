package com.xingyunfu.ebank.mapper.transfer;

import com.xingyunfu.ebank.domain.transfer.EbankTransferForbidDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EbankTransferForbidMapper {
    List<EbankTransferForbidDTO> findAll();
    void insert(EbankTransferForbidDTO transferForbid);
}
