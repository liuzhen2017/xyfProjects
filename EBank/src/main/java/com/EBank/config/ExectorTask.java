package com.EBank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/13
 */
@Configuration
@EnableAsync
public class ExectorTask {
    @Value("${taks.corePoolSize}")
    private int corePoolSize;
    @Value("${taks.maxPoolSize}")
    private int maxPoolSize;
    @Value("${taks.queueCapacity}")
    private int queueCapacity;
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }
}
