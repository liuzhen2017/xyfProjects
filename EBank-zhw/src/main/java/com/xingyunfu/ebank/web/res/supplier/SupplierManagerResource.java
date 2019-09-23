package com.xingyunfu.ebank.web.res.supplier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 与供应商资金往来
 */
@Slf4j
@RestController
@RequestMapping("/ebank/supplier")
public class SupplierManagerResource {

    /**
     * 向供应商转账<br/>
     * 资金方向 ：系统账号 --> 供应商账号
     */
    // TODO: 2019/7/31 业务流程待定

    /**
     * 供应商提现<br/>
     * 资金方向 ：系统账号 --> 供应商账号
     */
    // TODO: 2019/7/31 业务流程待定
}
