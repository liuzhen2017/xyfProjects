package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.PageVODTO;
import com.xinyunfu.product.dto.ProDto;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.dto.ProductDTO;
import com.xinyunfu.product.model.ProPackage;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 * 
 *@author tym
 *@since  2019/7/9
 */
public interface IPackageService extends IService<ProPackage>{

	ResponseInfo<String> saveProPackage(ProPackage proPackage);

	ResponseInfo<String> update(ProPackage proPackage);

	ResponseInfo<ProPackage> findOneById(Integer packageId);

	ResponseInfo<IPage<ProPackage>> findProductByPage(ProPackage proPackage, Integer page, Integer pageSize);

	ResponseInfo<String> updateStatus(Integer[] ids, int status);

	IPage<ProDto> findAllPackage(PageVODTO proName);

	ProductDTO findPackageProductDTOByProId(ProIdDTO proIdDTO);

    ProPackage findProPackageByProId(Long proId);
}
