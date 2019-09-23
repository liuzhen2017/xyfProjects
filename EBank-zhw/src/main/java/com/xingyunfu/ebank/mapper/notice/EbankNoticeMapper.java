package com.xingyunfu.ebank.mapper.notice;

import com.xingyunfu.ebank.domain.notice.EbankNoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EbankNoticeMapper {

    List<EbankNoticeDTO> findAll();
}
