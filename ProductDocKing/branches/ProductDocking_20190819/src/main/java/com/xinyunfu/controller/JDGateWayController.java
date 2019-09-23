package com.xinyunfu.controller;


import com.xinyunfu.dto.jd.goods.*;
import com.xinyunfu.dto.jd.order.JDOrderDto;
import com.xinyunfu.dto.jd.order.JDThirdOrderDto;
import com.xinyunfu.dto.jd.param.*;
import com.xinyunfu.dto.jd.shelf.*;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.JDGateWayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/jdEntry/")
@Slf4j
public class JDGateWayController {

      @Autowired
      private JDGateWayService jdGateWayService;

      @RequestMapping("getToken.do")
      public ResponseInfo<String> getToken(@RequestParam("param") String param) {

            return ResponseInfo.success(jdGateWayService.getToken());
      }

      /**
       * 生成序列号，下单时用
       *
       * @param param
       * @return
       */
      @PostMapping("createSerialNum.do")
      public ResponseInfo<Integer> createSerialNum(@RequestBody String param) {

            return ResponseInfo.success(jdGateWayService.createSerialNum());
      }

      /**
       * POST请求 添加1个上架位置
       */
      @PostMapping("addShelf.do")
      public ResponseInfo<String> addShelf(@RequestBody JDShelfParam shelfParam) {

            if (jdGateWayService.addShelf(shelfParam)) {
                  return ResponseInfo.success("添加成功");
            }
            return ResponseInfo.error("添加失败");
      }

      /**
       * PATCH请求 修改指定id的上架位置
       */
      @PatchMapping("updateSP.do")
      public ResponseInfo<String> updateSP(@RequestBody JDSPParam spParam) {

            if (jdGateWayService.updateSP(spParam)) {
                  return ResponseInfo.success("修改上架位置成功");
            }
            return ResponseInfo.error("修改上架位置失败");
      }

      /**
       * 根据上架位置id获得上架位置
       */
      @GetMapping("getShelfPosition.do")
      public ResponseInfo<JDShelfPositionDto> getShelfPosition(@RequestParam("id") String id) {

            JDShelfPositionDto shelfPosition = jdGateWayService.getShelfPosition(id);
            if (shelfPosition != null) {
                  return ResponseInfo.success(shelfPosition);
            }
            return ResponseInfo.error(null);

      }

      /**
       * 根据父id获得上架位置列表
       */
      @GetMapping("getSPList.do")
      public ResponseInfo<List<JDShelfPositionDto>> getSPList(@RequestParam("father_id") String father_id) {

            List<JDShelfPositionDto> shelfPositionList = jdGateWayService.getSPList(father_id);
            if (CollectionUtils.isNotEmpty(shelfPositionList)) {
                  return ResponseInfo.success(shelfPositionList);
            }
            return ResponseInfo.error(shelfPositionList);

      }

      /**
       * DELETE请求 删除上架位置
       */
      @DeleteMapping("deleteSP.do")
      public ResponseInfo<String> deleteSP(@RequestParam("id") String id) {

            if (jdGateWayService.deleteSP(id)) {
                  return ResponseInfo.success("删除成功");
            }
            return ResponseInfo.error("删除失败");
      }


      /**
       * POST请求 商户商品上架
       */
      @PostMapping("shelfGoods.do")
      public ResponseInfo<String> shelfGoods(@RequestBody List<ShelfGoodsParam> shelfGoodsParam) {

            if (jdGateWayService.shelfGoods(shelfGoodsParam)) {
                  return ResponseInfo.success("商品上架成功");
            }
            return ResponseInfo.error("商品上架失败");
      }

      /**
       * DELETE请求 商户商品下架
       */
      @DeleteMapping("deleteSG.do")
      public ResponseInfo<String> deleteSG(@RequestParam("skuIds") String skuIds, @RequestParam("shelf_id") int shelf_id) {

            if (jdGateWayService.deleteSG(skuIds, shelf_id)) {
                  return ResponseInfo.success("下架成功");
            }
            return ResponseInfo.error("下架失败");
      }

      /**
       * 分页查询上架商品列表
       */
      @GetMapping("getSGList.do")
      public ResponseInfo<List<JDShlefGoodsDto>> getSGList(@RequestParam("shelf_id") String shelf_id,
                                                           @RequestParam("skuIds") String skuIds,
                                                           @RequestParam("page") String page,
                                                           @RequestParam("pagesize") String pagesize) {
            List<JDShlefGoodsDto> shlefGoodsList = jdGateWayService.getSGList(shelf_id, skuIds, page, pagesize);
            if (CollectionUtils.isNotEmpty(shlefGoodsList)) {
                  return ResponseInfo.success(shlefGoodsList);
            }
            return ResponseInfo.error(shlefGoodsList);

      }

      /**
       * 根据sku串获取上架商品列表
       */
      @GetMapping("getShelfGoodsList.do")
      public ResponseInfo<List<JDShlefGoodsDto>> getShelfGoodsList(@RequestParam("skus") String skus) {

            List<JDShlefGoodsDto> shlefGoodsList = jdGateWayService.getShelfGoodsList(skus);
            if (CollectionUtils.isNotEmpty(shlefGoodsList)) {
                  return ResponseInfo.success(shlefGoodsList);
            }
            return ResponseInfo.error(shlefGoodsList);

      }


      /**
       * 分页查询上架商品列表，并获取总数
       */
      @GetMapping("getSGWrapperDto.do")
      public ResponseInfo<JDSGWrapperDto> getSGWrapperDto(@RequestParam("shelf_id") int shelf_id,
                                                          @RequestParam(value = "skuIds", required = false) String skuIds,
                                                          @RequestParam(value = "name", required = false) String name,
                                                          @RequestParam("page") int page, @RequestParam("pagesize") int pagesize) {

            JDSGWrapperDto sgWrapperDto = jdGateWayService.getSGWrapperDto(shelf_id, skuIds, name, page, pagesize);
            if (sgWrapperDto != null) {
                  return ResponseInfo.success(sgWrapperDto);
            }
            return ResponseInfo.error(null);

      }


      /**
       * 上架商品的状态
       *
       * @param skus sku串，以逗号分隔
       */
      @GetMapping("getSGStatus.do")
      public ResponseInfo<List<JDSGStatusDto>> getSGStatus(@RequestParam("skus") String skus) {

            List<JDSGStatusDto> sgstusdto = jdGateWayService.getSGStatus(skus);
            if (CollectionUtils.isNotEmpty(sgstusdto)) {
                  return ResponseInfo.success(sgstusdto);
            }
            return ResponseInfo.error(sgstusdto);

      }

      /**
       * 上架商品详情
       */
      @GetMapping("getSGDetail.do")
      public ResponseInfo<JDShlefGoodsDto> getSGDetail(@RequestParam("sku") String sku, @RequestParam("shelf_id") String shelf_id) {

            JDShlefGoodsDto shlefGoodsDto = jdGateWayService.getSGDetail(sku, shelf_id);
            if (shlefGoodsDto != null) {
                  return ResponseInfo.success(shlefGoodsDto);
            }
            return ResponseInfo.error(null);

      }

      /**
       * 获取商品pc端/移动端样式
       * <p>
       * 平台采用自己的样式
       */
      @RequestMapping("getGoodsStyle.do")
      public ResponseInfo<JDGoodsStyleDto> getGoodsStyle(@RequestParam(value = "sku", required = false) long sku,
                                                         @RequestParam(value = "type", defaultValue = "0") int type) {

            JDGoodsStyleDto goodsStyleDto = jdGateWayService.getGoodsStyle(sku, type);
            if (goodsStyleDto != null) {
                  return ResponseInfo.success(goodsStyleDto);
            }
            return ResponseInfo.error(null);
      }

      /**
       * 获取商品池列表
       */
      @RequestMapping("getGoodsPoolList.do")
      public ResponseInfo<List<JDGoodsPoolDto>> getGoodsPoolList(@RequestParam("param") String param) {

            List<JDGoodsPoolDto> goodsPool = jdGateWayService.getGoodsPoolList();
            if (CollectionUtils.isNotEmpty(goodsPool)) {
                  return ResponseInfo.success(goodsPool);
            }
            return ResponseInfo.error(goodsPool);
      }


      /**
       * 获取商品列表
       */
      @RequestMapping("getGoodsList.do")
      public ResponseInfo<JDGoodsListDto> getGoodsList(JDGoodsListParam goodsListParam) {

            JDGoodsListDto goodsListDto = jdGateWayService.getGoodsList(goodsListParam);
            if (goodsListDto != null) {
                  return ResponseInfo.success(goodsListDto);
            }
            return ResponseInfo.error(null);
      }

      /**
       * 获取商品详情
       */
      @RequestMapping("getGoodsDetail.do")
      public ResponseInfo<JDGoodsDetailDto> getGoodsDetail(JDGoodsDetailParam goodsDetailParam) {

            JDGoodsDetailDto goodsDetailDto = jdGateWayService.getGoodsDetail(goodsDetailParam);
            if (goodsDetailDto != null) {
                  return ResponseInfo.success(goodsDetailDto);
            }
            return ResponseInfo.error(null);
      }

      /**
       * kpl商品的上下架状态
       *
       * @param skus sku串，以逗号分隔
       */
      @GetMapping("getGoodsState.do")
      public ResponseInfo<List<JDGoodsState>> getGoodsState(@RequestParam("skus") String skus) {

            List<JDGoodsState> goodsStateList = jdGateWayService.getGoodsState(skus);
            if (CollectionUtils.isNotEmpty(goodsStateList)) {
                  return ResponseInfo.success(goodsStateList);
            }
            return ResponseInfo.error(goodsStateList);
      }

      /**
       * 获取商品图片信息
       */
      @RequestMapping("getGoodsImage.do")
      public ResponseInfo<Map<String, List<JDImage>>> getGoodsImage(@RequestParam("proNo") String proNo) {

            Map<String, List<JDImage>> imageMap = jdGateWayService.getGoodsImage(proNo);
            if (imageMap != null && imageMap.size() > 0) {
                  return ResponseInfo.success(imageMap);
            }
            return ResponseInfo.error(imageMap);
      }

      /**
       * 获取运费
       */
      @RequestMapping("getFreight.do")
      public ResponseInfo<JDFreightDto> getFreight(@RequestBody JDFreightParam freightParam) {

            JDFreightDto freightDto = jdGateWayService.getFreight(freightParam);
            if (freightDto != null) {
                  return ResponseInfo.success(freightDto);
            }
            return ResponseInfo.error(null);
      }

      /**
       * 获取分类信息
       */
      @RequestMapping("getCategory.do")
      public ResponseInfo<JDCategoryDto> getCategory(@RequestParam("cid") String cid) {

            JDCategoryDto categoryDto = jdGateWayService.getCategory(cid);
            if (categoryDto != null) {
                  return ResponseInfo.success(categoryDto);
            }
            return ResponseInfo.error(null);
      }

      /**
       * 获取同类商品
       */
      @RequestMapping("getSimilarGoods.do")

      public ResponseInfo<List<JDSimilarGoodsDto>> getSimilarGoods(@RequestParam("skuId") String skuId) {

            List<JDSimilarGoodsDto> similarGoodsDto = jdGateWayService.getSimilarGoods(skuId);
            if (CollectionUtils.isNotEmpty(similarGoodsDto)) {
                  return ResponseInfo.success(similarGoodsDto);
            }
            return ResponseInfo.error(similarGoodsDto);
      }

      /**
       * 获取价格
       */
      @RequestMapping("getPrice.do")
      public ResponseInfo<List<JDPriceDto>> getPrice(@RequestParam("skuids") String skuIds) {
            List<JDPriceDto> priceDtoList = jdGateWayService.getPrice(skuIds);
            if (CollectionUtils.isNotEmpty(priceDtoList)) {
                  return ResponseInfo.success(priceDtoList);
            }
            return ResponseInfo.error(priceDtoList);
      }

      /**
       * 批量获取stock
       */
      // todo update
      @RequestMapping("batchQueryStock.do")
      public ResponseInfo<List<JDStockDto>> batchQueryStock(
                @RequestParam("skuNums") List<JDSkuNumsParam> skuNums, @RequestParam("area") String area) {

            List<JDStockDto> stockDtoList = jdGateWayService.batchQueryStock(skuNums, area);
            if (CollectionUtils.isNotEmpty(stockDtoList)) {
                  return ResponseInfo.success(stockDtoList);
            }
            return ResponseInfo.error(stockDtoList);
      }

      /**
       * 批量获取stock
       */
      @RequestMapping("getBatchStock.do")
      public ResponseInfo<List<JDBatchStockDto>> getBatchStock(
                @RequestParam("skuNums") String skuNums, @RequestParam("area") String area) {

            List<JDBatchStockDto> stockDtoList = jdGateWayService.getBatchStock(skuNums, area);
            if (CollectionUtils.isNotEmpty(stockDtoList)) {
                  return ResponseInfo.success(stockDtoList);
            }
            return ResponseInfo.error(stockDtoList);
      }

      /**
       * 查询订单
       */
      @RequestMapping("getOrder.do")
      public ResponseInfo<List<JDOrderDto>> getOrder(@RequestParam("orderId") long orderId) {

            List<JDOrderDto> orderDtoList = jdGateWayService.getOrder(orderId);
            if (CollectionUtils.isNotEmpty(orderDtoList)) {
                  return ResponseInfo.success(orderDtoList);
            } else {
                  log.info("查询订单失败");
                  return ResponseInfo.error(orderDtoList);
            }
      }

      /**
       * 根据第三方订单号反查jd订单号
       */
      @RequestMapping("getOrderId.do")
      public ResponseInfo<JDThirdOrderDto> getOrderId(@RequestParam("thirdOrder") String thirdOrder) {

            JDThirdOrderDto thirdOrderDto = jdGateWayService.getOrderId(thirdOrder);
            if (thirdOrderDto != null) {
                  return ResponseInfo.success(thirdOrderDto);
            }
            return ResponseInfo.error(null);
      }

      /**
       * POST请求，预下单
       */
      @PostMapping("preOrder.do")
      public ResponseInfo<String> preOrder(@RequestBody JDPreOrderDtoParam orderParam) {
            log.info("========接收到预下单请求=,==requestParam ={}====", orderParam);
            return jdGateWayService.preOrder(orderParam);
      }


      /**
       * 确认下单
       *
       * @param order_no 第三方订单号
       * @param type     更新上架真实销量：1表示需要，0表示不需要，默认值1
       */
      @PatchMapping("ensureOrder.do")
      public ResponseInfo<String> ensureOrder(@RequestParam("order_no") long order_no, @RequestParam(value = "type", defaultValue = "1") String type) {

            if (jdGateWayService.ensureOrder(order_no, type)) {
                  return ResponseInfo.success("确认下单成功");
            }
            return ResponseInfo.error("确认下单失败");
      }


      //获取物流信息
      @GetMapping("getOrderTrack")
      public ResponseInfo<JDLogisticsDto> getOrderTrack(@RequestParam("orderId") Long orderId){
            JDLogisticsDto logistics = jdGateWayService.getOrderTrack(orderId);
            if(logistics != null){
                  return ResponseInfo.success(logistics);
            }
            return ResponseInfo.error(null);
      }
}
