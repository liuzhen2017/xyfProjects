package com.xinyunfu.jace.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xinyunfu.jace.utils.SftpUtil;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SftpUtil.class)
public @interface SFTPUtilOn {

}
