package com.xinyunfu.report.dbconfig;

import org.springframework.boot.context.properties.*;
import org.springframework.boot.jdbc.*;
import org.springframework.context.annotation.*;

import javax.sql.*;
import java.util.*;

/**
     * @author jace
     *
     * 多数据库实例配置
     */
    @Configuration
    public class DataSourceJaceConfig {



//        @Primary
//        @Bean(name = "cm")
//        @ConfigurationProperties(prefix = "spring.datasource.cm")
//        public DataSource dataSourcePreprocess() {
//            return DataSourceBuilder.create().build();
//        }
//
//
//        @Primary
//        @Bean(name = "om")
//        @ConfigurationProperties(prefix = "spring.datasource.om")
//        public DataSource dataSourceSentence() {
//            return DataSourceBuilder.create().build();
//        }


        @Primary
        @Bean(name = "proxy")
        @ConfigurationProperties(prefix = "spring.datasource.proxy")
        public DataSource dataSourceProxy() {
            return DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver").url(" jdbc:mysql://192.168.1.24/ProxyService?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8").username("root").password("root321!").build();
        }

    }



