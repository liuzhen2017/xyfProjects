package com.example.demo.dbconfig;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author jace
 * 
 * 多数据库实例配置
 */
@Configuration
public class DataSourceJaceConfig {
   
	
	@Primary
    @Bean(name = "cm")
    @ConfigurationProperties(prefix = "spring.datasource.cm")
    public DataSource dataSourcePreprocess() {
        return DataSourceBuilder.create().build();
    }

   
	@Primary
    @Bean(name = "om")
    @ConfigurationProperties(prefix = "spring.datasource.om")
    public DataSource dataSourceSentence() {
        return DataSourceBuilder.create().build();
    }
	
}
