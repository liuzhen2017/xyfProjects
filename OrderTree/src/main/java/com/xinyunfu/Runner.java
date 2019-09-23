package com.xinyunfu;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.xinyunfu.jace.annotion.Microservice;
import com.xinyunfu.jace.annotion.RedisConfigOn;


@Microservice       //所有微服必须要有的
@EnableFeignClients //使用了feign配置
@RedisConfigOn      //redis默认封装开启
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}

}
