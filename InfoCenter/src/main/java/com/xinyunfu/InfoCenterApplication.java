package com.xinyunfu;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.xinyunfu.jace.annotion.Microservice;
import com.xinyunfu.jace.annotion.RedisConfigOn;

import lombok.extern.slf4j.Slf4j;

/**
 * @author faster-builder
 */
@Microservice       //所有微服必须要有的
@EnableFeignClients //使用了feign配置
@Slf4j
@EnableRabbit
@RedisConfigOn
public class InfoCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfoCenterApplication.class,args);
        log.info("============消息中心启动完毕=============");
    }
}