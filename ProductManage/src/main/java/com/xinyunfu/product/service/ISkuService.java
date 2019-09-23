package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.ProIdDTO;
import com.xinyunfu.product.model.ProSku;
import com.xinyunfu.product.vo.PageVO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 * 
 *@author tym
 *@since  2019/7/8
 */
public interface ISkuService extends IService<ProSku>{

	ResponseInfo<String> saveProsku(ProSku proSku);

	ResponseInfo<String> update(ProSku proSku);

	ResponseInfo<ProSku> findOneById(Long id);

	ResponseInfo<PageVO<ProSku>> queryByPage(ProSku proSku, Integer page, Integer pageSize);

	List<ProSku> findSkuByProId(ProIdDTO proIdDTO);

	ResponseInfo<List<List<ProSku>>> findSkuByProIds(List<Long> ids);

	ResponseInfo<String> updateStatus(Long[] ids, Integer status);

	BigDecimal findDefaultPriceByProId(ProIdDTO proIdDTO);

	Integer findAllSellStockByProId(ProIdDTO proIdDTO);

	Integer findAllStockByProId(ProIdDTO proIdDTO);

	Map<Long,Integer> findProTypeBySkuId(String ids);

	Boolean subtractStock(Map<Long,Integer> map);

	Boolean addStock(Map<Long,Integer> map);

    List<ProSku> queryJDSkuReshelf();

    String getSkuDesc(Long skuId);

    ResponseInfo<String> deleteProSku(Long[] ids, int i);

//	List<ProSku> findSecKillSkuByProId(Long proId);

//	BigDecimal findSecKillDefaultPriceByProId(Long proId);

//	List<ProSku> findPackageSkuByProId(Long proId);

//	BigDecimal findPackageDefaultPriceByProId(Long proId);

//	Integer findSecKillAllSellStockByProId(Long proId);

//	Integer findPackageAllSellStockByProId(Long proId);


	/**
	 * edit by lhpu
	 * ====================================================================
	 */

	boolean addProsku(ProSku proSku);

	ProSku getSkuByProId(long proId);

	boolean checkSkuByProId(Long proId);

	boolean updateSkuByProNo(String proNo);

	String getSkuNoBySkuId(Long skuId);

    List<String> findSkusByProId(Long proId);

	Long getProIdBySkuNo(String skuNo);

    Long getSkuIdByProIdAndGroupNo(Long proId, String groupNo);
}
