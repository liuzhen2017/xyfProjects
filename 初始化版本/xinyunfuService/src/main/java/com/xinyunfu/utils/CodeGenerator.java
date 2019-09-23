package com.xinyunfu.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;


/**
 * @author jiangqiang 代码自动生成器
 * 
 * 
 */
public class CodeGenerator {

	/**
	 * <p>
	 * 读取控制台内容
	 * </p>
	 */
	public static String scanner(String tip) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("请输入" + tip + "：");
		System.out.println(help.toString());
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StringUtils.isNotEmpty(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}

//	public static void main(String[] args) {
//		//表名
//		String tabName = "app_user";
//		List<String> tables = new ArrayList<>(Arrays.asList(tabName));
//		for (String tableName : tables) {
//			// 代码生成器
//			AutoGenerator mpg = new AutoGenerator();
//			// 全局配置
//			GlobalConfig gc = new GlobalConfig();
//			String projectPath = System.getProperty("user.dir");
//			gc.setOutputDir(projectPath + "/src/main/java"); //java类生成的位置
//			gc.setAuthor("jace");// 操作人
//			gc.setOpen(false);
//			mpg.setGlobalConfig(gc);
//
//			// 数据源配置
//			DataSourceConfig dsc = new DataSourceConfig();
//			dsc.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=UTC");//数据库url
//			dsc.setDriverName("com.mysql.cj.jdbc.Driver");//driverName
//			dsc.setUsername("root");//数据库账号
//			dsc.setPassword("");//数据库名字
//			mpg.setDataSource(dsc);
//
//			// 包配置
//			PackageConfig pc = new PackageConfig();
//			pc.setParent("com.xinyunfu");//runner.java所在的路径
//			pc.setEntity("model");
//			pc.setController("controller");
//			mpg.setPackageInfo(pc);
//
//			// 自定义配置
//			InjectionConfig cfg = new InjectionConfig() {
//				@Override
//				public void initMap() {
//					// to do nothing
//				}
//			};
//			List<FileOutConfig> focList = new ArrayList<>();
//			focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
//				@Override
//				public String outputFile(TableInfo tableInfo) {
//					// 自定义输入文件名称
//					return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
//				}
//			});
//			cfg.setFileOutConfigList(focList);
//			mpg.setCfg(cfg);
//			mpg.setTemplate(new TemplateConfig().setXml(null));
//
//			// 策略配置
//			StrategyConfig strategy = new StrategyConfig();
//			strategy.setNaming(NamingStrategy.underline_to_camel);
//			strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//			strategy.setSuperEntityClass("com.rnmg.jace.entities.BaseEntity");// 父类
//			strategy.setSuperEntityColumns("created_by", "created_date", "updated_by", "updated_date");// 继承父类的字段
//			strategy.setRestControllerStyle(true);
//			strategy.setInclude(tableName);
//			strategy.setEntityBuilderModel(true);
//			strategy.setEntityLombokModel(true);
//			strategy.setControllerMappingHyphenStyle(false);
//			strategy.setTablePrefix(pc.getModuleName() + "_");
//			mpg.setStrategy(strategy);
//			mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//			mpg.execute();
//		}

//	}

}
