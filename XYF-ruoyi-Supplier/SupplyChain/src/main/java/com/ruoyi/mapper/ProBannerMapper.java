package com.ruoyi.mapper;

import com.ruoyi.domain.ProBanner;
import java.util.List;	

/**
 * 广告 数据层
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public interface ProBannerMapper 
{
	/**
     * 查询广告信息
     * 
     * @param bannerId 广告ID
     * @return 广告信息
     */
	public ProBanner selectProBannerById(Long bannerId);
	
	/**
     * 查询广告列表
     * 
     * @param proBanner 广告信息
     * @return 广告集合
     */
	public List<ProBanner> selectProBannerList(ProBanner proBanner);
	
	/**
     * 新增广告
     * 
     * @param proBanner 广告信息
     * @return 结果
     */
	public int insertProBanner(ProBanner proBanner);
	
	/**
     * 修改广告
     * 
     * @param proBanner 广告信息
     * @return 结果
     */
	public int updateProBanner(ProBanner proBanner);
	
	/**
     * 删除广告
     * 
     * @param bannerId 广告ID
     * @return 结果
     */
	public int deleteProBannerById(Long bannerId);
	
	/**
     * 批量删除广告
     * 
     * @param bannerIds 需要删除的数据ID
     * @return 结果
     */
	public int deleteProBannerByIds(String[] bannerIds);
	
}