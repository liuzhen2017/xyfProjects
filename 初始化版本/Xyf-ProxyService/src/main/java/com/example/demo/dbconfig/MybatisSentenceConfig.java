package com.example.demo.dbconfig;

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
@MapperScan(basePackages = {"com.example.demo.dao.om"}, sqlSessionFactoryRef = "sqlSessionFactory2")
public class MybatisSentenceConfig {
   
	@Autowired
    @Qualifier("om")
    private DataSource om;

    @Bean(name = "sqlSessionFactory2")
    @Primary
    public SqlSessionFactory sqlSessionFactory2() throws Exception {
       // SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    	 MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
    	 mybatisPlus.setDataSource(om);
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
    public SqlSessionTemplate sqlSessionTemplate2() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory2());
        return sqlSessionTemplate;
    }
}
