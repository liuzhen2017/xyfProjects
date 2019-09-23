package com.example.demo.dbconfig;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
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
@MapperScan(basePackages = {"com.example.demo.dao.cm"}, sqlSessionFactoryRef = "sqlSessionFactory1")
public class MybatisPreprocessConfig {
   
	
	
	@Autowired
    @Qualifier("cm")
    private DataSource cm;

    @Bean(name = "sqlSessionFactory1")
    @Primary
    public SqlSessionFactory sqlSessionFactory1() throws Exception {
        //原生的sqlSessionFactory
    	 MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
    	 mybatisPlus.setDataSource(cm);
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
    public SqlSessionTemplate sqlSessionTemplate1() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory1());
        return sqlSessionTemplate;
    }
}
