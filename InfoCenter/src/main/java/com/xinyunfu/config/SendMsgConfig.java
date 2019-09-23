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
@ConfigurationProperties(prefix = "send-message")
@Data
public class SendMsgConfig {
	/**
	 * 发送短信主题
	 */
	private String sendSMS;
	/**
	 * 基础路径
	 */
	private String baseUrl;
	/**
	 * 发送验证码路径
	 */
	private String sendCodeUrl;
	/**
	 * 校验验证码路径
	 */
	private String vaildCodeUrl;
	/**
	 * 发送普通短信路径
	 */
	private String smsSendUrl;
	/**
	 * 注册模板
	 */
	private String registVipTpl;
	/**
	 * 重置密码模板
	 */
	private String resetPwdTpl;
	/**
	 * 推送APP模板
	 */
	private String pushAppTpl;
	/**
	 * 修改支付密码模板
	 */
	private String updatePayPwdTpl;
	/**
	 * 绑定手机模板
	 */
	private String bingPhonTpl;
	/**
	 * 解绑手机模板
	 */
	private String unbingPhonTpl;
	/**
	 * 验证码登陆模板
	 */
	private String vaildCodeLoginTpl;
	private String MerchantNoKey;
	private String MerchantNo;
	/**
	 * APP推送
	 */
	private String appPushUrl;
	/**
	 *  绑定APP
	 */
	private String appBindUrl;
	/**
	 *  APP推送
	 */
	private String baseUrlApp;

}
