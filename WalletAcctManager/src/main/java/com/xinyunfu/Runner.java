package com.xinyunfu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.xinyunfu.jace.annotion.Microservice;
import com.xinyunfu.jace.annotion.RedisConfigOn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Microservice
@Slf4j
@RedisConfigOn
@EnableFeignClients
@EnableScheduling //启用定时任务
@MapperScan(basePackages = {"com.xinyunfu.mapper"})
public class Runner {


    public static void main(String[] args) {
        SpringApplication.run(Runner.class,args);
        log.info("=============================【现金钱包启动成功】=============================");
    }

    
  
}