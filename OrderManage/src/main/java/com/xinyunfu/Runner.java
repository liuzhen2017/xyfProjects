package com.xinyunfu;

import com.xinyunfu.jace.annotion.Microservice;
import com.xinyunfu.jace.annotion.RedisConfigOn;
import com.xinyunfu.jace.annotion.SFTPUtilOn;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@Slf4j
@Microservice       //所有微服必须要有的
@EnableFeignClients //使用了feign配置
@RedisConfigOn      //redis默认封装开启
@MapperScan(basePackages = {"com.xinyunfu.mapper"})
//@SFTPUtilOn
//@EnableCaching //启用缓存
@EnableScheduling //启用定时任务
@EnableAsync //启用异步方法
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
		log.info("==================================【订单服务启动成功】==================================");
	}



}
