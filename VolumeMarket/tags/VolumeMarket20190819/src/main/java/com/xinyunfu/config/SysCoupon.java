package com.xinyunfu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/9
 */
@Configuration
@ConfigurationProperties(prefix = "sys-coupon")
@Data
public class SysCoupon {
	/**
	 * 发放数量
	 */
	private String giveNum;
	/**
	 * 价值
	 */
	private String amount;
    private String title;
    private String rules;
    private String isReward;
    private String rewardAmount;
    private String rewardType;
    private String serviceChargeAmount;
}
