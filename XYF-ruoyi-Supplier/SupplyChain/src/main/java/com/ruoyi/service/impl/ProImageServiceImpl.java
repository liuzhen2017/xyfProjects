package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProImageMapper;
import com.ruoyi.domain.ProImage;
import com.ruoyi.service.IProImageService;
import com.ruoyi.common.core.text.Convert;

/**
 * 商品图片 服务层实现
 * 
 * @author ruoyi
 * @date 2019-08-04
 */
@Service
public class ProImageServiceImpl implements IProImageService 
{
	@Autowired
	private ProImageMapper proImageMapper;

	/**
     * 查询商品图片信息
     * 
     * @param id 商品图片ID
     * @return 商品图片信息
     */
    @Override
	public ProImage selectProImageById(Long id)
	{
	    return proImageMapper.selectProImageById(id);
	}
	
	/**
     * 查询商品图片列表
     * 
     * @param proImage 商品图片信息
     * @return 商品图片集合
     */
	@Override
	public List<ProImage> selectProImageList(ProImage proImage)
	{
	    return proImageMapper.selectProImageList(proImage);
	}
	
    /**
     * 新增商品图片
     * 
     * @param proImage 商品图片信息
     * @return 结果
     */
	@Override
	public int insertProImage(ProImage proImage)
	{
	    return proImageMapper.insertProImage(proImage);
	}
	
	/**
     * 修改商品图片
     * 
     * @param proImage 商品图片信息
     * @return 结果
     */
	@Override
	public int updateProImage(ProImage proImage)
	{
	    return proImageMapper.updateProImage(proImage);
	}

	/**
     * 删除商品图片对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProImageByIds(String ids)
	{
		return proImageMapper.deleteProImageByIds(Convert.toStrArray(ids));
	}
	
}
