package com.ruoyi.service;

import com.ruoyi.domain.ProImage;
import java.util.List;

/**
 * 商品图片 服务层
 * 
 * @author ruoyi
 * @date 2019-08-04
 */
public interface IProImageService 
{
	/**
     * 查询商品图片信息
     * 
     * @param id 商品图片ID
     * @return 商品图片信息
     */
	public ProImage selectProImageById(Long id);
	
	/**
     * 查询商品图片列表
     * 
     * @param proImage 商品图片信息
     * @return 商品图片集合
     */
	public List<ProImage> selectProImageList(ProImage proImage);
	
	/**
     * 新增商品图片
     * 
     * @param proImage 商品图片信息
     * @return 结果
     */
	public int insertProImage(ProImage proImage);
	
	/**
     * 修改商品图片
     * 
     * @param proImage 商品图片信息
     * @return 结果
     */
	public int updateProImage(ProImage proImage);
		
	/**
     * 删除商品图片信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteProImageByIds(String ids);
	
}
