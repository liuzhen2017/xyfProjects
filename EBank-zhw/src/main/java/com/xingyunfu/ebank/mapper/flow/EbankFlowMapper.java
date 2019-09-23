package com.xingyunfu.ebank.mapper.flow;

import com.xingyunfu.ebank.domain.flow.EbankFlowDTO;
import com.xingyunfu.ebank.dto.flow.FlowPageQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EbankFlowMapper {

    void insert(EbankFlowDTO flow);
    void update(EbankFlowDTO flow);

    List<EbankFlowDTO> pageQuery(FlowPageQueryDTO pageQuery);
    Long pageQueryCount(FlowPageQueryDTO pageQuery);
}
