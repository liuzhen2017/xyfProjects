package com.EBank;

import com.xinyunfu.jace.annotion.Microservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author faster-builder
 */
@Microservice       //所有微服必须要有的
@EnableFeignClients //使用了feign配置
@EnableScheduling  //定时任务
@Slf4j
//@RedisConfigOn
public class EBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(EBankApplication.class,args);
        log.info("=============支付中心启动成功!============");
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}