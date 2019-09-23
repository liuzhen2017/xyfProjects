package com.xinyunfu.product.service;

import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.dto.ProductDTO;
import com.xinyunfu.product.model.ProPackagePro;
import com.xinyunfu.product.model.Product;

import java.util.List;

public interface IPackageProService {

	ResponseInfo<String> saveProPackagePro(ProPackagePro packagePro);

//	ResponseInfo<String> deleteProPackagePro(Integer id,Integer status);

	ResponseInfo<List<Product>> findPackageProByPackageId(Integer packageId);

	ResponseInfo<String> updateStatus(Integer[] ids, int status);

	ProductDTO findPackageProductDTOByProId(ProIdDTO proIdDTO);

}
