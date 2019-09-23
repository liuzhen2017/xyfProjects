package com.ruoyi.service;

import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.domain.ProChannel;
import com.ruoyi.domain.SysDept;

import java.util.List;

/**
 * 类目 服务层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface IProChannelService 
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
     * 删除类目信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProChannelByIds(String ids);


    List<Ztree> selectProChannelTree(ProChannel proChannel);
}
