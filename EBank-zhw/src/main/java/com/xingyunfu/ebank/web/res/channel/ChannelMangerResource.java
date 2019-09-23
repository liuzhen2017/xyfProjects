package com.xingyunfu.ebank.web.res.channel;

import com.xingyunfu.ebank.domain.channel.EbankChannelDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.channel.ChannelUpdateDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.service.channel.ChannelMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付渠道管理
 */
@Slf4j
@RestController
@RequestMapping("/ebank/channel")
public class ChannelMangerResource {
    @Autowired private ChannelMangerService channelMangerService;

    /**
     * 分页查询支付渠道
     */
    @GetMapping
    public PageDTO<EbankChannelDTO> pageQuery(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        log.info("REST request to page query channel. pageNo: {}, pageSize: {}", pageNo, pageSize);
        PageDTO<EbankChannelDTO> page = channelMangerService.pageQuery(pageNo, pageSize);
        log.info("REST request to page query channel. success! totalCount: {}", page.getTotalCount());
        return page;
    }

    /**
     * 更新支付渠道<br/>
     * 修改实际支付方式和付款方式，修改是否可用
     */
    @PostMapping
    public void update(@RequestBody@Validated ChannelUpdateDTO channelUpdate) throws EBankException {
        log.info("REST request to update channel. channelUpdate: {}", channelUpdate);
        channelMangerService.update(new EbankChannelDTO(channelUpdate));
        log.info("REST request to update channel. success!");
    }
}
