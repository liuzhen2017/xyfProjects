package com.xinyunfu.customer.web.res.log;

import com.xinyunfu.customer.dto.log.ClientInfoAddDTO;
import com.xinyunfu.customer.service.log.ClientInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.xinyunfu.customer.constant.CommonConstants.header_uid;

@Slf4j
@RestController
@RequestMapping("/customer/client")
public class ClientInfoResource {
    @Autowired private ClientInfoService clientInfoService;

    /**
     * 添加登陆日志
     */
    @PostMapping("/add")
    public void addLoginInfo(@RequestHeader(header_uid)Long userNo, @RequestBody ClientInfoAddDTO clientInfoAdd){
        log.info("REST request to add login info. userNo: {}, clientInfoAdd: {}", userNo, clientInfoAdd);
        clientInfoService.addLoginInfo(clientInfoAdd, userNo);
        log.info("REST request to add login info. success!");
    }
}
