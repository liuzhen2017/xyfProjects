package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.model.ProProperty;
import com.xinyunfu.product.vo.PageVO;

import java.util.List;

public interface IPropertyService extends IService<ProProperty>{

	ResponseInfo<List<ProProperty>> findPropertyByProId(Long proId);

	List<List<ProProperty>> findPropertyByProIds(List<Long> ids);

	ResponseInfo<String> saveProProperty(ProProperty proProperty);

	boolean addProperty(ProProperty proProperty);

	ResponseInfo<String> updateProProperty(ProProperty proProperty);

	ResponseInfo<String> updateStatus(Long[] ids, int status);

	ResponseInfo<PageVO<ProProperty>> queryByPage(ProProperty proProperty, Integer page, Integer pageSize);

    List<ProProperty> queryJDPropertyReshelf();

    ProProperty queryPropertyByProIdAndName(Long proId, String propertyName);

    boolean checkPropertyByProIdAndName(long proId, String propertyName);

    ResponseInfo<String> deleteProperty(Long[] ids, int i);


	/**
	 * edit by lhpu
	 * =====================================================================
	 */
	List<ProProperty> getAttrListByProId(Long proId);


    Long saveProperty(ProProperty property);
}
