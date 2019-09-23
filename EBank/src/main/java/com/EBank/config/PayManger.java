package com.EBank.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/9
 */
@Configuration
@ConfigurationProperties(prefix = "pay-manger")
@Data
public class PayManger {
    /**
     * 基础路径
     */
    private String baseUrl;
    /**
     * 统一下单->向平台支付
     */
    private String paymentUrl;
    /**
     * 统一下单->向平台支付
     */
    private String queryPamentUrl;
    /**
     * 查询向下单交易结果 ->向平台支付结果
     */
    private String remittanceUrl;
    /**
     * 提交付款申请->平台代付给用户
     */
    private String queryRemittanceUrl;
    /**
     * 商户号 秘钥
     */
    private String merchantkey;
    /**
     * 商户号
     */
    private String merchantNo;
    /**
     * 入账异步通知结果
     */
    private String notifyurlRemittanceurl;
    /**
     * 出账异步通知结果
     */
    private String notifyurlpaymentUrl;
    /**
     * 支付公钥
     */
    private String payPullKey;
    /**
     * 支付私钥
     */
    private String payPriKey;;
}
