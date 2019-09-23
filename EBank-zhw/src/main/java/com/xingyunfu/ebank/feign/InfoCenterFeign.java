package com.xingyunfu.ebank.feign;

import com.rnmg.jace.utils.ResponseInfo;
import com.xingyunfu.ebank.dto.infocenter.SendMessageCustomDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("InfoCenter")
@RequestMapping("/infoCenter/message")
public interface InfoCenterFeign {

    /**
     * 自定义发送短信
     */
    @PostMapping("/custom")
    ResponseInfo<Object> sendMessage(@RequestBody SendMessageCustomDTO custom);
}
