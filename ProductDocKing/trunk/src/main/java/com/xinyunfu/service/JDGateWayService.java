package com.xinyunfu.service;


import com.xinyunfu.dto.jd.goods.*;
import com.xinyunfu.dto.jd.order.JDOrderDto;
import com.xinyunfu.dto.jd.order.JDPreOrderDto;
import com.xinyunfu.dto.jd.order.JDThirdOrderDto;
import com.xinyunfu.dto.jd.param.*;
import com.xinyunfu.dto.jd.shelf.*;
import com.xinyunfu.jace.utils.ResponseInfo;

import java.util.List;
import java.util.Map;

public interface JDGateWayService {
      /**
       * 获取token
       */
      String getToken();

      /**
       * 生成 serialNumber
       */
      Integer createSerialNum();

      /**
       * POST请求 添加1个上架位置
       */
      boolean addShelf(JDShelfParam shelfParam);

      /**
       * PATCH请求 修改指定id的上架位置
       */
      boolean updateSP(JDSPParam spParam);

      /**
       * 根据位置id获得上架位置
       */
      JDShelfPositionDto getShelfPosition(String id);

      /**
       * 根据父id获得上架位置
       */
      List<JDShelfPositionDto> getSPList(String father_id);

      /**
       * DELETE请求 删除上架位置
       */
      boolean deleteSP(String id);

      /**
       * POST请求 商户商品上架
       */
      boolean shelfGoods(List<ShelfGoodsParam> shelfGoodsParam);

      /**
       * DELETE请求 商户商品下架
       */
      boolean deleteSG(String skuIds, int shelf_id);

      /**
       * 分页查询上架商品列表
       */
      List<JDShlefGoodsDto> getSGList(String shelf_id, String skuIds, String page, String pagesize);

      /**
       * 根据商品编号串获取上架商品列表
       */
      List<JDShlefGoodsDto> getShelfGoodsList(String skus);

      /**
       * 分页查询上架商品列表，并获取总数
       */
      JDSGWrapperDto getSGWrapperDto(int shelf_id, String skuIds, String name, int page, int pagesize);

      /**
       * 上架商品的状态
       */
      List<JDSGStatusDto> getSGStatus(String skus);

      /**
       * 上架商品详情
       */
      JDShlefGoodsDto getSGDetail(String sku, String shelf_id);

      /**
       * 获取商品类目列表
       */
      List<JDGoodsPoolDto> getGoodsPoolList();

      /**
       * 获取商品列表
       */
      JDGoodsListDto getGoodsList(JDGoodsListParam goodsListParam);

      /**
       * 获取商品详情
       */
      JDGoodsDetailDto getGoodsDetail(JDGoodsDetailParam goodsDetailParam);

      /**
       * 获取kpl商品列表状态
       */
      ResponseInfo<List<JDGoodsState>> getGoodsState(String skus);

      /**
       * 获取商品pc端/移动端样式
       */
      JDGoodsStyleDto getGoodsStyle(long sku, int type);

      /**
       * 根据商品编号获取商品图片信息
       */
      Map<String, List<JDImage>> getGoodsImage(String proNo);

      /**
       * 获取运费
       */
      JDFreightDto getFreight(JDFreightParam freightParam);

      /**
       * 查询商品价格
       * param skuIds 多个商品以，分隔(最高支持100个商品)
       */
      ResponseInfo getPrice(String skuIds);

      /**
       * 批量查询库存方式1 (订单详情页、下单使用)
       */
      List<JDStockDto> batchQueryStock(List<JDSkuNumsParam> skuNums, String area);

      /**
       * 批量查询库存方式2 (商品列表页使用)
       */
      List<JDBatchStockDto> getBatchStock(String skuNums, String area);

      /**
       * 订单查询
       */
      ResponseInfo<List<JDOrderDto>> getOrder(long orderId);

      /**
       * 订单反查
       */
      ResponseInfo<JDThirdOrderDto> getOrderId(String thirdOrder);

      /**
       * POST 请求，预下单
       */
      ResponseInfo<String> preOrder(JDPreOrderDtoParam orderParam);

      /**
       * PATCH 请求，确定下单
       */
      boolean ensureOrder(long order_no, String type);

      /**
       * 根据商品列表，获取skuId
       */
      List<String> getskuIdList(List<JDGoodsDto> goodsList);

      /**
       * 根据分类id获取分类信息（分类id从商品详情获取，1个商品对应多个分类）
       */
      JDCategoryDto getCategory(String cid);

      /**
       * 根据商品编号获取同类商品信息
       */
      List<JDSimilarGoodsDto> getSimilarGoods(String skuId);

      /**
       * kpl商品可售验证
       *
       * @param skuIds 商品编号，支持批量，以【,】分隔 (最高支持100个商品)
       * @return
       */
      ResponseInfo checkGoods(String skuIds);


      /**
       * 区域购买限制查询
       */
      ResponseInfo getAreaLimit(String sku, Long province, Long city, Long county, Long town);


      /**
       *   获取物流信息
       */
      ResponseInfo<JDLogisticsDto> getOrderTrack (Long orderId);

}