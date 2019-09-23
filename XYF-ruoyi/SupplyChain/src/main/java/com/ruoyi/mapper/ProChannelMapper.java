package com.ruoyi.mapper;

import com.ruoyi.domain.ProChannel;
import java.util.List;	

/**
 * 类目 数据层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface ProChannelMapper 
{
	/**
     * 查询类目信息
     * 
     * @param channelId 类目ID
     * @return 类目信息
     */
	public ProChannel selectProChannelById(Long channelId);
	
	/**
     * 查询类目列表
     * 
     * @param proChannel 类目信息
     * @return 类目集合
     */
	public List<ProChannel> selectProChannelList(ProChannel proChannel);
	
	/**
     * 新增类目
     * 
     * @param proChannel 类目信息
     * @return 结果
     */
	public int insertProChannel(ProChannel proChannel);
	
	/**
     * 修改类目
     * 
     * @param proChannel 类目信息
     * @return 结果
     */
	public int updateProChannel(ProChannel proChannel);
	
	/**
     * 删除类目
     * 
     * @param channelId 类目ID
     * @return 结果
     */
	public int deleteProChannelById(Long channelId);
	
	/**
     * 批量删除类目
     * 
     * @param channelIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProChannelByIds(String[] channelIds);
	
}