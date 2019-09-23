package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProHomeVersionMapper;
import com.ruoyi.domain.ProHomeVersion;
import com.ruoyi.service.IProHomeVersionService;
import com.ruoyi.common.core.text.Convert;

/**
 * 版本 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProHomeVersionServiceImpl implements IProHomeVersionService 
{
	@Autowired
	private ProHomeVersionMapper proHomeVersionMapper;

	/**
     * 查询版本信息
     * 
     * @param versionId 版本ID
     * @return 版本信息
     */
    @Override
	public ProHomeVersion selectProHomeVersionById(Integer versionId)
	{
	    return proHomeVersionMapper.selectProHomeVersionById(versionId);
	}
	
	/**
     * 查询版本列表
     * 
     * @param proHomeVersion 版本信息
     * @return 版本集合
     */
	@Override
	public List<ProHomeVersion> selectProHomeVersionList(ProHomeVersion proHomeVersion)
	{
	    return proHomeVersionMapper.selectProHomeVersionList(proHomeVersion);
	}
	
    /**
     * 新增版本
     * 
     * @param proHomeVersion 版本信息
     * @return 结果
     */
	@Override
	public int insertProHomeVersion(ProHomeVersion proHomeVersion)
	{
	    return proHomeVersionMapper.insertProHomeVersion(proHomeVersion);
	}
	
	/**
     * 修改版本
     * 
     * @param proHomeVersion 版本信息
     * @return 结果
     */
	@Override
	public int updateProHomeVersion(ProHomeVersion proHomeVersion)
	{
	    return proHomeVersionMapper.updateProHomeVersion(proHomeVersion);
	}

	/**
     * 删除版本对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProHomeVersionByIds(String ids)
	{
		return proHomeVersionMapper.deleteProHomeVersionByIds(Convert.toStrArray(ids));
	}
	
}
