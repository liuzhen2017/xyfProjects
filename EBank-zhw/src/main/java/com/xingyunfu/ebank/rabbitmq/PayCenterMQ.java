package com.xingyunfu.ebank.rabbitmq;

import com.xingyunfu.ebank.dto.paycenter.notice.PayNoticeDataDTO;
import com.xingyunfu.ebank.service.paycenter.PayCenterConfig;
import com.xingyunfu.ebank.service.paycenter.PaymentNoticeService;
import com.xingyunfu.ebank.util.JsonMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class PayCenterMQ {
    @Autowired
    private PaymentNoticeService paymentNoticeService;
    @Autowired
    private PayCenterConfig payCenterConfig;     //配置

    /**
     * 商品/套餐购买支付回调
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "ebank_pay_notice_callback",durable = "true"),
            exchange = @Exchange(name = "ebank_pay_notice",type = ExchangeTypes.DIRECT),
            key = "ebank_pay_key"))
    public void payNoticeServer(String reqBaseStr) throws Exception {
        log.info("========MQ=========start==================");
        log.info("start pay notice. reqBaseStr: {}" ,reqBaseStr);
        paymentNoticeService.payNotice(reqBaseStr);
        log.info("stop pay notice.");
    }

    /**
     * 商品/套餐购买支付回调，测试
     */
    @RabbitHandler
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "ebank_pay_notice_callback_test", durable = "true"),
            exchange = @Exchange(name = "ebank_pay_notice_test", type = ExchangeTypes.DIRECT),
            key = "ebank_pay_key_test"))
    public void payNoticeTest(String data) throws IOException {
        if (!payCenterConfig.getPayRight()) {
            log.info("========MQ=========start=========test=========");
            log.info("start pay notice test: {}", data);
            paymentNoticeService.payNotice(JsonMapperUtil.getMapper().readValue(data, PayNoticeDataDTO.class));
        }
    }
}
