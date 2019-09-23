package com.xingyunfu.ebank.web.res.paycenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rabbitmq.tools.json.JSONUtil;
import com.xingyunfu.ebank.constant.PayCenterConstant;
import com.xingyunfu.ebank.dto.paycenter.PayCenterReqBaseDTO;
import com.xingyunfu.ebank.dto.paycenter.notice.PayNoticeDataDTO;
import com.xingyunfu.ebank.dto.paycenter.notice.TransferNoticeDataDTO;
import com.xingyunfu.ebank.service.paycenter.PayCenterConfig;
import com.xingyunfu.ebank.service.paycenter.PaymentNoticeService;
import com.xingyunfu.ebank.util.JsonMapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 支付中心回调地址
 */
@Slf4j
@RestController
@RequestMapping("/ebank/callback/")
public class PayCenterCallBackResource {

    @Autowired
    private PaymentNoticeService paymentNoticeService;
    @Autowired
    private PayCenterConfig payCenterConfig;     //配置
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 订单支付通知
     * 由于支付中心的请求头是Content-Type:text/plain，只能使用string接收数据
     */
    @PostMapping(value = "/pay")
    public String payNotice(@RequestBody String reqBaseStr) throws Exception {
        log.info("REST request to payment notice. reqBaseStr: {}", reqBaseStr);
        rabbitTemplate.convertAndSend("ebank_pay_notice", "ebank_pay_key", reqBaseStr);
        log.info("REST request to payment notice. success!");
        return PayCenterConstant.callback_success;
    }

    /**
     * 订单支付通知模拟请求
     */
    @PostMapping("/pay/simulation")
    public String payNoticeSimulation(@RequestBody PayNoticeDataDTO noticeData) throws JsonProcessingException {
        log.info("REST request to payment notice simulation. noticeData: {}", noticeData);
        if (!payCenterConfig.getPayRight()) {
            log.info("do simulation... ...");
            rabbitTemplate.convertAndSend("ebank_pay_notice_test", "ebank_pay_key_test",
                    JsonMapperUtil.getMapper().writeValueAsString(noticeData));
        }
        log.info("REST request to payment notice simulation. success!");
        return PayCenterConstant.callback_success;
    }

/***************  业务发生了更改: 付款申请成功之后认为付款已经成功，通知中心不再执行回调操作  ****************/
    /**
     * 付款结果通知
     */
    @PostMapping(value = "/transfer")
    public String transferNotice(@RequestBody String reqBaseStr) throws Exception {
        log.info("REST request to transfer notice. reqBaseStr: {}", reqBaseStr);
        paymentNoticeService.transferNotice(reqBaseStr);
        log.info("REST request to transfer notice. success!");
        return PayCenterConstant.callback_success;
    }

    /**
     * 付款结果通知模拟请求
     */
    @PostMapping("/transfer/simulation")
    public String transferNotice(@RequestBody TransferNoticeDataDTO noticeData) {
        log.info("REST request to transfer notice simulation. noticeData: {}", noticeData);
        if (!payCenterConfig.getPayRight()) {
            log.info("do simulation... ...");
            paymentNoticeService.transferNotice(noticeData);
        }
        log.info("REST request to transfer notice simulation. success!");
        return PayCenterConstant.callback_success;
    }
}
