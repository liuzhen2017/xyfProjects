package com.xinyunfu.service;

import com.xinyunfu.commons.goods.ProSku;
import com.xinyunfu.dto.jd.ProductDto;
import com.xinyunfu.dto.jd.goods.JDGoodsState;
import com.xinyunfu.dto.jd.shelf.JDShlefGoodsDto;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ProductDtoService {

      @Autowired
      private ProductManageFeign productManageFeign;

      @Autowired
      private JDGateWayService jdGateWayService;

      //固定邮费，从yml中取
      @Value("${postage}")
      private BigDecimal postage;

      public ResponseInfo getProductDto(Long proId, BigDecimal price, BigDecimal costPrice, String skuId, String shelfId) {

            ProductDto productDto = new ProductDto();
            //调用商品上下架状态接口
            log.info("实时查询商品上下架状态，skuId={}", skuId);
            ResponseInfo<List<JDGoodsState>> stateResult = jdGateWayService.getGoodsState(skuId);
            log.info("实时查询商品上下架状态===结果 result={}", stateResult);

            List<JDGoodsState> states = stateResult.getData();
            if (CollectionUtils.isNotEmpty(states)) {
                  JDGoodsState goodState = states.get(0);
                  //jd商品状态已下架的，平台也要相应的下架，state 1：上架，0：下架
                  if (goodState.getState() == 0) {
                        String proNo = "JD" + skuId;
                        productManageFeign.offSaleByproNo(proNo);
                  }
            }

            ResponseInfo result = this.getPrice(proId, price, costPrice, skuId, shelfId);
            if (result.isSuccess()) {
                  BigDecimal proPrice = (BigDecimal) result.getData();
                  productDto.setPrice(proPrice);
                  return ResponseInfo.success(productDto);
            }

            return result;
      }


      public ResponseInfo getPrice(Long proId, BigDecimal price, BigDecimal costPrice, String skuId, String shelfId) {

            log.info("实时查询价格参数 ,proId={},price ={},costPrice ={},skuId ={},shelfId ={}", proId, price, costPrice, skuId, shelfId);
            JDShlefGoodsDto detail = jdGateWayService.getSGDetail(skuId, shelfId);
            if (detail != null) {
                  BigDecimal jdPrice = detail.getJd_price();
                  BigDecimal jdCostPirce = detail.getPrice();

                  //如果实时查询的价格和传过来的价格以及协议价都相同，则返回查出的价格；不同，则需要更新本地库
                  if (jdPrice.compareTo(price) == 0 && jdCostPirce.compareTo(costPrice) == 0) {
                        return ResponseInfo.success(jdPrice);
                  } else {
                        ResponseInfo result = this.calculatePrice(proId, jdPrice, jdCostPirce);
                        if (!result.isSuccess()) {
                              return result;
                        }

                  }
                  return ResponseInfo.success(jdPrice);
            }
            log.warn("实时查询商品价格失败");

            //实时查询价格异常，先按原价格处理
            return ResponseInfo.success(price);
      }


      /**
       * 计算sku 最低价、平台销售价、总价并入库
       *
       * @param proId       需要根据 proId查出对应的sku对象
       * @param jdPrice     售价，对应JD Jd_price
       * @param jdCostPirce 协议价，对应JD price
       */
      @Async
      public ResponseInfo calculatePrice(Long proId, BigDecimal jdPrice, BigDecimal jdCostPirce) {
            try {
                  log.info("===begin request calculatePrice ====jdPrice={},   jdCostPirce={}", jdPrice, jdCostPirce);
                  ResponseInfo<ProSku> response = productManageFeign.getSkuByProId(proId);
                  log.info("===end calculatePrice ====,result ={}", response);
                  ProSku proSku = response.getData();
                  if (proSku != null) {
                        //原价
                        BigDecimal marketPrice = jdPrice.multiply(new BigDecimal(1.3)).setScale(2, BigDecimal.ROUND_HALF_UP);
                        //综合成本
                        BigDecimal totalCost = jdCostPirce.multiply(new BigDecimal(1.16)).add(postage).setScale(2, BigDecimal.ROUND_HALF_UP);
                        //折扣
                        BigDecimal rebate = jdCostPirce.divide(jdPrice, 2);
                        //综合折扣
                        BigDecimal totalRebate = totalCost.divide(jdPrice, 2);
                        //券后价
                        BigDecimal minSellPrice;

                        //7折以上的产品直接下架
                        if (rebate.compareTo(new BigDecimal(0.7)) == 1) {
                              minSellPrice = new BigDecimal(1000000);
                              proSku.setStatus(1);

                        } else if (jdPrice.compareTo(new BigDecimal(50)) < 1) {
                              if (totalCost.compareTo(new BigDecimal(25)) < 1) {
                                    //销售价<=50元，并且运营成本 <=25 ,则设置为 0
                                    minSellPrice = new BigDecimal(0);
                              } else {
                                    //销售价<=50元，并且运营成本 > 25 ,则直接下架
                                    proSku.setStatus(1);
                                    minSellPrice = new BigDecimal(1000000);
                              }
                        } else {
                              minSellPrice = totalRebate.subtract(new BigDecimal(0.45)).multiply(jdPrice);
                              if (minSellPrice.compareTo(new BigDecimal(0)) == -1) {
                                    minSellPrice = new BigDecimal(0);
                              }
                        }
                        //if (proSku.getStatus() != null && proSku.getStatus()== 1) {
                        //      String proNo = "JD".concat(skuId);
                        //      ResponseInfo<String> result = productManageFeign.offSaleByproNo(proNo);
                        //      if(result.isSuccess()){
                        //            log.info("调用商品服务下架商品成功，proNo={}",proNo);
                        //      }else{
                        //            log.info("调用商品服务下架商品失败，proNo={}",proNo);
                        //      }
                        //}
                        if (proSku.getStatus() != null && proSku.getStatus() == 1) {
                              ResponseInfo<String> result = productManageFeign.offSaleByproId(proId);
                              if (result.isSuccess()) {
                                    log.info("调用商品服务下架商品成功，proId={}", proId);
                              }else {
                                    log.info("调用商品服务下架商品失败，proId={}", proId);
                              }
                              return new ResponseInfo("p555", "该商品已经下架", null);
                        }
                        proSku.setPrice(jdPrice);
                        proSku.setCostPrice(jdCostPirce);
                        proSku.setMarketPrice(marketPrice);
                        proSku.setMinSellPrice(minSellPrice);
                        this.updateProSkuAndIntoDb(proSku);
                  }
                  return ResponseInfo.success(null);
            } catch (Exception e) {
                  log.info("获取sku 异常,msg={},e={}", e.getMessage(), e);
                  return ResponseInfo.errorReturn("获取sku 异常!");
            }

      }

      /**
       * 更新proSku并入库
       */
      public void updateProSkuAndIntoDb(ProSku proSku) {
            try {
                  ResponseInfo<String> result = productManageFeign.updateSku(proSku);
                  log.info("更新本地库proSku信息：result={}", result.getData());
            } catch (Exception e) {
                  log.info("updateProSkuAndIntoDb 异常,msg={},e={}", e.getMessage(), e);
            }
      }

}
