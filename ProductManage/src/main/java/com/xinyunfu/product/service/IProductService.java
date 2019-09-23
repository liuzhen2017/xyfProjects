package com.xinyunfu.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.product.dto.*;
import com.xinyunfu.product.model.ProDetails;
import com.xinyunfu.product.model.ProImage;
import com.xinyunfu.product.model.Product;
import com.xinyunfu.product.vo.PageVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tym
 * @since 2019/7/8
 */
public interface IProductService extends IService<Product> {

      PageVO<Product> findProductByPage(Product product, Integer page, Integer pageSize);

      ResponseInfo<String> saveProduct(Product product);

      ResponseInfo<String> updateProduct(Product product);

      ResponseInfo<String> deleteProduct(Long[] ids, Integer status);

      ResponseInfo<String> updateStatus(String ids, Integer status);

      Product findProductById(Long proId);

      List<Product> findProductByChannelId(Long channelId);

      ThirdChannelDTO queryProDtosByChannelId(ChannelPageDTO channelPageDTO);

      Map<Long, List<String>> queryPayTypeAndCouponType(List<Long> idsList);

//	List<ProDto> findProDtoByProId(List<Long> ids);

      void updateProductByChannelName(String channelName1, Integer killStatus);

      ProductDTO findProductDtoById(ProIdDTO proIdDTO);

      Map<String, Product> queryJDProInstock();

      Map<String, Long> queryProIdByProNo(String[] proNos);

      Map<Long, Product> getJDProInstock();

      Product queryProductByProNo(String proNo);

      Boolean checkProByProNo(String proNo);

//    Long findProIdByProId(Long proId);

      Long getProIdByProNo(String proNo);

      List<ProDto> queryProDtosListByChannelId(Long channelId);

      //eidt by lhpu
      List<Long> getJDProductIds(int source, int status);

      Page<RProductDTO> getRProductDTO(RProductDTO rProductDTO, Integer page, Integer pageSize);

      ProDto findProDtoByProId(Long proId);

      IPage<ProDto> getProDtoPageByChannelId(ChannelPageDTO channelPageDTO);

      IPage<ProDto> getProDtoPageByChannelIdOrderByAllSellStockDesc(ChannelPageDTO channelPageDTO);

      IPage<ProDto> getProDtoPageByChannelIdOrderByPriceDesc(ChannelPageDTO channelPageDTO);

      IPage<ProDto> getProDtoPageByChannelIdOrderByPriceAsc(ChannelPageDTO channelPageDTO);

      IPage<ProDto> findProDtoByProName(PageByProNameDTO pageByProNameDTO);


      //  ====================================================


      boolean offSaleByproNo(String proNo);

      boolean offSaleByproId(Long proId);

      Product getProductBySkuNo(String skuNo);
}
