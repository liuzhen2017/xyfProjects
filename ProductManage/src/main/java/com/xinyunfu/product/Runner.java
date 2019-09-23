package com.xinyunfu.product;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.jace.annotion.Microservice;
import com.xinyunfu.jace.annotion.RedisConfigOn;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@Microservice       //所有微服必须要有的
@EnableFeignClients //使用了feign配置
@Slf4j
@RedisConfigOn
@MapperScan(basePackages = {"com.xinyunfu.product.mapper"})
@EnableScheduling
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
		log.info("===========商品服务启动成功===========");
	}



}
