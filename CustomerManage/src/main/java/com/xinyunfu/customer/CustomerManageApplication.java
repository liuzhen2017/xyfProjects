package com.xinyunfu.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@EnableCaching
@EnableFeignClients
@SpringCloudApplication
public class CustomerManageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerManageApplication.class, args);
	}
}
