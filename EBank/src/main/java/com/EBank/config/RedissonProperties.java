//package com.EBank.config;
//
///**
// * @author liuzhen
// * @version 1.0
// * @date 2019/7/8
// */
//import lombok.Data;
////import org.redisson.api.RBloomFilter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@ConfigurationProperties(prefix = "spring.redisson")
//@Data
//public class RedissonProperties {
//
//    /**
//     * 哨兵master 名称
//     */
//    private String master;
//
//    /**
//     * 哨兵节点
//     */
//    private String nodes;
//
//    /**
//     * 哨兵配置
//     */
//    private boolean masterOnlyWrite;
//
//    /**
//     *
//     */
//    private int failMax;
//    private String sentinel;
//    private String timeOut;
//    private String pool;
//    private String password;
//}