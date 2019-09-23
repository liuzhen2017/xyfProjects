package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.model.ProProperty;
import com.xinyunfu.product.model.ProPropertyValue;
import com.xinyunfu.product.vo.PageVO;

import java.util.List;

public interface IPropertyValueService extends IService<ProPropertyValue>{

	ResponseInfo<List<List<ProPropertyValue>>> findPropertyValueByPropertyIds(List<Long> ids);

	ResponseInfo<String> savePPV(ProPropertyValue proPropertyValue);

	ResponseInfo<String> updatePPV(ProPropertyValue proPropertyValue);

	ResponseInfo<String> updateStatus(Long[] ids, Integer status);

	ResponseInfo<PageVO<ProPropertyValue>> queryByPage(ProPropertyValue proPropertyValue, Integer page,
                                                       Integer pageSize);

	List<ProPropertyValue> findPropertyValueByPropertyId(Long propertyId);

    List<ProPropertyValue> queryJDPropertyValueReshelf();

    ProPropertyValue queryPropertyValueByPropertyIdValueText(Long propertyId, String valueText);

    ResponseInfo<String> deletePropertyValue(Long[] ids, int i);





	/***
	 * edit by lhpu
	 * ==============================================================
	 */

	boolean checkByPropertyIdAndValueText(long propertyId, String valueText);

	List<ProPropertyValue> getPropertyValueByPropertyId(Long propertyId);


    Long getValueId(Long propertyId, String value);

    List<List<ProPropertyValue>> findPropertyValueByProId(Long proId);
}
