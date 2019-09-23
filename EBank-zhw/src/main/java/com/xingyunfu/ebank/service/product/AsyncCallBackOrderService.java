package com.xingyunfu.ebank.service.product;

import com.xingyunfu.ebank.feign.OrderManageFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class AsyncCallBackOrderService {
    @Autowired
    private OrderManageFeign orderManageFeign;

    @Async
    public void callBackOrder(String orderNo, String sysOrderNo, BigDecimal amount,
                              Boolean status, Integer orderType) throws InterruptedException {
        Thread.sleep(3000);
        log.info("Start wallet call back. orderNo: {}", orderNo);
        orderManageFeign.payCallback(orderNo, sysOrderNo, amount, status, orderType);
    }
}
