package com.xinyunfu.report;

import lombok.extern.slf4j.*;
import org.mybatis.spring.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.client.discovery.*;
import org.springframework.cloud.netflix.eureka.*;
import org.springframework.cloud.openfeign.*;

@EnableFeignClients //使用了feign配置
@SpringBootApplication
@Slf4j

@MapperScan(basePackages = {"com.xinyunfu.report.dao"})
public class ProxyServiceRunner {

	public static void main(String[] args) {
		SpringApplication.run(ProxyServiceRunner.class, args);
		log.info("=====代理服务启动=====");
	}

}
