package com.xingyunfu.ebank.mapper.channel;

import com.xingyunfu.ebank.domain.channel.EbankChannelDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EbankChannelMapper {

    EbankChannelDTO findByEbankPayTypeAndClientType(EbankChannelDTO payChannel);
    List<Long> findAllId();
    EbankChannelDTO findById(Long id);
    List<Long> pageQuery(Map<String, Integer> map);
    void update(EbankChannelDTO ebankChannel);
}
