package com.xinyunfu;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.xinyunfu.jace.annotion.Microservice;
import com.xinyunfu.jace.annotion.RedisConfigOn;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhen
 */
@Microservice       //所有微服必须要有的
@Slf4j
@EnableRabbit
@RedisConfigOn
@EnableFeignClients //使用了feign配置
public class VolumeMarketApplication {
    public static void main(String[] args) {
        SpringApplication.run(VolumeMarketApplication.class,args);
        log.info("====================券集市启动完毕=====================");
    }

    
  
}