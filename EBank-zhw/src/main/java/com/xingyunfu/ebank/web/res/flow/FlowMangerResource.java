package com.xingyunfu.ebank.web.res.flow;

import com.xingyunfu.ebank.domain.flow.EbankFlowDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.flow.FlowPageQueryDTO;
import com.xingyunfu.ebank.service.flow.FlowMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ebank/flow")
public class FlowMangerResource {

    @Autowired private FlowMangerService flowMangerService;

    @PostMapping("/page")
    public PageDTO<EbankFlowDTO> pageQuery(@RequestBody@Validated FlowPageQueryDTO pageQuery){
        log.info("REST request to page query flow. pageQuery: {}", pageQuery);
        PageDTO<EbankFlowDTO> page = flowMangerService.pageQuery(pageQuery);
        log.info("REST request to page query flow. success! totalCount: ", page.getTotalCount());
        return page;
    }
}
