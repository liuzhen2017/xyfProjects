package com.xinyunfu.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderTreeWorker {

	
	
	@Bean(name="singleWorker")
	public ExecutorService getExecutor() {
		return Executors.newSingleThreadExecutor();
	}
	
	
	@Bean(name="payWorker")
	public ExecutorService getFixThreadPool() {
		return Executors.newFixedThreadPool(2);
	}
}
