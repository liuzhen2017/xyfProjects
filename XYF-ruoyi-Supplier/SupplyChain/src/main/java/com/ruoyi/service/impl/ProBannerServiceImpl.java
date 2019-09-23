package com.ruoyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mapper.ProBannerMapper;
import com.ruoyi.domain.ProBanner;
import com.ruoyi.service.IProBannerService;
import com.ruoyi.common.core.text.Convert;

/**
 * 广告 服务层实现
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Service
public class ProBannerServiceImpl implements IProBannerService 
{
	@Autowired
	private ProBannerMapper proBannerMapper;

	/**
     * 查询广告信息
     * 
     * @param bannerId 广告ID
     * @return 广告信息
     */
    @Override
	public ProBanner selectProBannerById(Long bannerId)
	{
	    return proBannerMapper.selectProBannerById(bannerId);
	}
	
	/**
     * 查询广告列表
     * 
     * @param proBanner 广告信息
     * @return 广告集合
     */
	@Override
	public List<ProBanner> selectProBannerList(ProBanner proBanner)
	{
	    return proBannerMapper.selectProBannerList(proBanner);
	}
	
    /**
     * 新增广告
     * 
     * @param proBanner 广告信息
     * @return 结果
     */
	@Override
	public int insertProBanner(ProBanner proBanner)
	{
	    return proBannerMapper.insertProBanner(proBanner);
	}
	
	/**
     * 修改广告
     * 
     * @param proBanner 广告信息
     * @return 结果
     */
	@Override
	public int updateProBanner(ProBanner proBanner)
	{
	    return proBannerMapper.updateProBanner(proBanner);
	}

	/**
     * 删除广告对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteProBannerByIds(String ids)
	{
		return proBannerMapper.deleteProBannerByIds(Convert.toStrArray(ids));
	}
	
}
