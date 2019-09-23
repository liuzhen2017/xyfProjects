package com.ruoyi;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@EnableFeignClients
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@MapperScan("com.ruoyi.mapper")
public class SupplyChainApplication
{
    public static void main(String [] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(SupplyChainApplication.class, args);
//        log.info();
        System.out.println("============星云福供应商后台服务启动成功===========");
    }
}