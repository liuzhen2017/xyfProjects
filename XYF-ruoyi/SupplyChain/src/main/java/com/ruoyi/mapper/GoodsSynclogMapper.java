package com.ruoyi.mapper;

import com.ruoyi.domain.GoodsSynclog;
import java.util.List;	

/**
 * 商品同步 数据层
 * 
 * @author ruoyi
 * @date 2019-08-12
 */
public interface GoodsSynclogMapper 
{
	/**
     * 查询商品同步信息
     * 
     * @param id 商品同步ID
     * @return 商品同步信息
     */
	public GoodsSynclog selectGoodsSynclogById(Integer id);
	
	/**
     * 查询商品同步列表
     * 
     * @param goodsSynclog 商品同步信息
     * @return 商品同步集合
     */
	public List<GoodsSynclog> selectGoodsSynclogList(GoodsSynclog goodsSynclog);
	
	/**
     * 新增商品同步
     * 
     * @param goodsSynclog 商品同步信息
     * @return 结果
     */
	public int insertGoodsSynclog(GoodsSynclog goodsSynclog);
	
	/**
     * 修改商品同步
     * 
     * @param goodsSynclog 商品同步信息
     * @return 结果
     */
	public int updateGoodsSynclog(GoodsSynclog goodsSynclog);
	
	/**
     * 删除商品同步
     * 
     * @param id 商品同步ID
     * @return 结果
     */
	public int deleteGoodsSynclogById(Integer id);
	
	/**
     * 批量删除商品同步
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteGoodsSynclogByIds(String[] ids);
	
}