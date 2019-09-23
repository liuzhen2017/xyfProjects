package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProPackageMapper;
import com.ruoyi.domain.ProPackage;
import com.ruoyi.service.IProPackageService;
import com.ruoyi.common.core.text.Convert;

/**
 * 套餐 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProPackageServiceImpl implements IProPackageService 
{
	@Autowired
	private ProPackageMapper proPackageMapper;

	/**
     * 查询套餐信息
     * 
     * @param packageId 套餐ID
     * @return 套餐信息
     */
    @Override
	public ProPackage selectProPackageById(Long packageId)
	{
	    return proPackageMapper.selectProPackageById(packageId);
	}
	
	/**
     * 查询套餐列表
     * 
     * @param proPackage 套餐信息
     * @return 套餐集合
     */
	@Override
	public List<ProPackage> selectProPackageList(ProPackage proPackage)
	{
	    return proPackageMapper.selectProPackageList(proPackage);
	}
	
    /**
     * 新增套餐
     * 
     * @param proPackage 套餐信息
     * @return 结果
     */
	@Override
	public int insertProPackage(ProPackage proPackage)
	{
	    return proPackageMapper.insertProPackage(proPackage);
	}
	
	/**
     * 修改套餐
     * 
     * @param proPackage 套餐信息
     * @return 结果
     */
	@Override
	public int updateProPackage(ProPackage proPackage)
	{
	    return proPackageMapper.updateProPackage(proPackage);
	}

	/**
     * 删除套餐对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProPackageByIds(String ids)
	{
		return proPackageMapper.deleteProPackageByIds(Convert.toStrArray(ids));
	}
	
}
