package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.GoodsSynclogMapper;
import com.ruoyi.domain.GoodsSynclog;
import com.ruoyi.service.IGoodsSynclogService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品同步 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-12
 */
@Service
public class GoodsSynclogServiceImpl implements IGoodsSynclogService 
{
	@Autowired
	private GoodsSynclogMapper goodsSynclogMapper;

	/**
     * 查询商品同步信息
     * 
     * @param id 商品同步ID
     * @return 商品同步信息
     */
    @Override
	public GoodsSynclog selectGoodsSynclogById(Integer id)
	{
	    return goodsSynclogMapper.selectGoodsSynclogById(id);
	}
	
	/**
     * 查询商品同步列表
     * 
     * @param goodsSynclog 商品同步信息
     * @return 商品同步集合
     */
	@Override
	public List<GoodsSynclog> selectGoodsSynclogList(GoodsSynclog goodsSynclog)
	{
	    return goodsSynclogMapper.selectGoodsSynclogList(goodsSynclog);
	}
	
    /**
     * 新增商品同步
     * 
     * @param goodsSynclog 商品同步信息
     * @return 结果
     */
	@Override
	public int insertGoodsSynclog(GoodsSynclog goodsSynclog)
	{
	    return goodsSynclogMapper.insertGoodsSynclog(goodsSynclog);
	}
	
	/**
     * 修改商品同步
     * 
     * @param goodsSynclog 商品同步信息
     * @return 结果
     */
	@Override
	public int updateGoodsSynclog(GoodsSynclog goodsSynclog)
	{
	    return goodsSynclogMapper.updateGoodsSynclog(goodsSynclog);
	}

	/**
     * 删除商品同步对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteGoodsSynclogByIds(String ids)
	{
		return goodsSynclogMapper.deleteGoodsSynclogByIds(Convert.toStrArray(ids));
	}
	
}
