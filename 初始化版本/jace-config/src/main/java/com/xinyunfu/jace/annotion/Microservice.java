package com.xinyunfu.jace.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.xinyunfu.jace.config.MybatisPlusConfig;
import com.xinyunfu.jace.config.SpringAopConfig;
import com.xinyunfu.jace.config.SpringConfig;;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication
@Import({MybatisPlusConfig.class,SpringAopConfig.class,SpringConfig.class})
public @interface Microservice {

}
