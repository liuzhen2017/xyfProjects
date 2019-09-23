package com.xinyunfu;

import com.xinyunfu.jace.annotion.Microservice;
import com.xinyunfu.jace.annotion.RedisConfigOn;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;


@Slf4j
@Microservice       //所有微服必须要有的
@EnableFeignClients //使用了feign配置
@RedisConfigOn      //redis默认封装开启
@EnableAsync
@MapperScan(basePackages = {"com.xinyunfu.mapper"})
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
        log.info("==================================【答题服务启动成功】==================================");
	}

}
