package com.xinyunfu.mapper;

import com.xinyunfu.model.RuleConfig;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jace
 * @since 2019-08-22
 */
@Mapper
public interface RuleConfigMapper extends BaseMapper<RuleConfig> {

	
	
	@Select("select * from rule_config where enables=1")
	public RuleConfig getRuleBean();
	
	
	@Select("select * from rule_config")
	public List<RuleConfig> findAll();
}
