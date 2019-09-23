package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProFreightMapper;
import com.ruoyi.domain.ProFreight;
import com.ruoyi.service.IProFreightService;
import com.ruoyi.common.core.text.Convert;

/**
 * 店铺邮费模板 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProFreightServiceImpl implements IProFreightService 
{
	@Autowired
	private ProFreightMapper proFreightMapper;

	/**
     * 查询店铺邮费模板信息
     * 
     * @param freightId 店铺邮费模板ID
     * @return 店铺邮费模板信息
     */
    @Override
	public ProFreight selectProFreightById(Long freightId)
	{
	    return proFreightMapper.selectProFreightById(freightId);
	}
	
	/**
     * 查询店铺邮费模板列表
     * 
     * @param proFreight 店铺邮费模板信息
     * @return 店铺邮费模板集合
     */
	@Override
	public List<ProFreight> selectProFreightList(ProFreight proFreight)
	{
	    return proFreightMapper.selectProFreightList(proFreight);
	}
	
    /**
     * 新增店铺邮费模板
     * 
     * @param proFreight 店铺邮费模板信息
     * @return 结果
     */
	@Override
	public int insertProFreight(ProFreight proFreight)
	{
	    return proFreightMapper.insertProFreight(proFreight);
	}
	
	/**
     * 修改店铺邮费模板
     * 
     * @param proFreight 店铺邮费模板信息
     * @return 结果
     */
	@Override
	public int updateProFreight(ProFreight proFreight)
	{
	    return proFreightMapper.updateProFreight(proFreight);
	}

	/**
     * 删除店铺邮费模板对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProFreightByIds(String ids)
	{
		return proFreightMapper.deleteProFreightByIds(Convert.toStrArray(ids));
	}
	
}
