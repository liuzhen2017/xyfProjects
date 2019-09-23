package com.ruoyi.mapper;

import com.ruoyi.domain.ProHomeVersion;
import java.util.List;	

/**
 * 版本 数据层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface ProHomeVersionMapper 
{
	/**
     * 查询版本信息
     * 
     * @param versionId 版本ID
     * @return 版本信息
     */
	public ProHomeVersion selectProHomeVersionById(Integer versionId);
	
	/**
     * 查询版本列表
     * 
     * @param proHomeVersion 版本信息
     * @return 版本集合
     */
	public List<ProHomeVersion> selectProHomeVersionList(ProHomeVersion proHomeVersion);
	
	/**
     * 新增版本
     * 
     * @param proHomeVersion 版本信息
     * @return 结果
     */
	public int insertProHomeVersion(ProHomeVersion proHomeVersion);
	
	/**
     * 修改版本
     * 
     * @param proHomeVersion 版本信息
     * @return 结果
     */
	public int updateProHomeVersion(ProHomeVersion proHomeVersion);
	
	/**
     * 删除版本
     * 
     * @param versionId 版本ID
     * @return 结果
     */
	public int deleteProHomeVersionById(Integer versionId);
	
	/**
     * 批量删除版本
     * 
     * @param versionIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProHomeVersionByIds(String[] versionIds);
	
}