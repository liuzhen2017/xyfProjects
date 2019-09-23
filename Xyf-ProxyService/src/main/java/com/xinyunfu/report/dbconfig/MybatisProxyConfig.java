package com.xinyunfu.report.dbconfig;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

@Configuration
@MapperScan(basePackages = {"com.xinyunfu.report.dao.proxy"}, sqlSessionFactoryRef = "proxySqlSessionFactory")
public class MybatisProxyConfig {

    @Autowired
    @Qualifier("proxy")
    private DataSource proxy;

    @Bean(name = "proxySqlSessionFactory")
    @Primary
    public SqlSessionFactory proxySqlSessionFactory() throws Exception {
        // SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(proxy);
        mybatisPlus.setVfs(SpringBootVFS.class);
        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        //数据库字段设计为驼峰命名，默认开启的驼峰转下划线会报错字段找不到
        mc.setMapUnderscoreToCamelCase(true);
        mybatisPlus.setConfiguration(mc);
        return mybatisPlus.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(proxySqlSessionFactory());
        return sqlSessionTemplate;
    }
}
