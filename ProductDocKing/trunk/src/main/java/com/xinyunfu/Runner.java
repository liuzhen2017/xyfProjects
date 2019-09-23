package com.xinyunfu;

import com.xinyunfu.jace.annotion.Microservice;
import com.xinyunfu.jace.annotion.RedisConfigOn;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;


@Microservice       //所有微服必须要有的
@EnableFeignClients //使用了feign配置
@RedisConfigOn      //redis默认封装开启
@MapperScan(basePackages = {"com.xinyunfu.mapper"})
@EnableCaching //启用缓存
@EnableAsync
@Slf4j
public class Runner {

      public static void main(String[] args) {
            SpringApplication.run(Runner.class, args);
            log.info("\r\n==============商品同步服务启动成功!==============");
      }


      @Bean
      public RestTemplate restTemplate() {

            //SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
            //上一行被注释掉的是Spring自己的实现，下面是依赖了httpclient包后的实现
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectTimeout(5000);
            //连接建立后，发起request到收到response的时间，可能会出现脏读，单位ms，这里设为90秒
            requestFactory.setReadTimeout(90000);
            RestTemplate restTemplate = new RestTemplate(requestFactory);
            return restTemplate;

            //return new RestTemplate(new OkHttp3ClientHttpRequestFactory());

      }

}
