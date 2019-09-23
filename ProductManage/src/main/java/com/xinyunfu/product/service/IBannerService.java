package com.xinyunfu.product.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.model.ProBanner;

public interface IBannerService extends IService<ProBanner> {


	List<ProBanner> queryBanners();

	ProBanner queryBannerById(Long bannerId);

    String selectLinkUrlByBannerId(long bannerId);

    ResponseInfo<String> delete(String ids);

}
