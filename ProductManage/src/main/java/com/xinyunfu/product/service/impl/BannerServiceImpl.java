package com.xinyunfu.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.mapper.BannerMapper;
import com.xinyunfu.product.model.ProBanner;
import com.xinyunfu.product.service.IBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, ProBanner> implements IBannerService {

	@Autowired
	private BannerMapper bannerMapper;
	
	public List<ProBanner> queryBanners(){
		
		return bannerMapper.queryBanners();
	}

	@Override
	public ProBanner queryBannerById(Long bannerId) {
        ProBanner banner=this.getById(bannerId);
        if (banner!=null) {
            return this.getById(bannerId);
        }else
            throw new RuntimeException("暂无数据");
		
	}

	@Override
	public String selectLinkUrlByBannerId(long bannerId) {
	    return bannerMapper.selectLinkUrlByBannerId(bannerId);
	}

    @Override
    public ResponseInfo<String> delete(String ids) {
        String[] bIds = ids.split(",");
        for (String id : bIds) {
            if (bannerMapper.deleteByBannerId(id) != 1)
                return ResponseInfo.error("ID为"+id+"的广告删除失败");
        }
        return ResponseInfo.success("删除成功");
    }


}
