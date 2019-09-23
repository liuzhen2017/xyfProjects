package com.xinyunfu.service.impl;

import com.xinyunfu.dto.jd.goods.JDFreightDto;
import com.xinyunfu.dto.jd.goods.JDStockDto;
import com.xinyunfu.dto.jd.param.*;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.JdAddr;
import com.xinyunfu.service.JDAddrService;
import com.xinyunfu.service.JDGateWayService;
import com.xinyunfu.service.PreOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PreOrderServiceImpl implements PreOrderService {

      @Autowired
      private JDGateWayService jdService;

      @Autowired
      private ProductManageFeign productManageFeign;

      @Autowired
      private JDAddrService jdAddrService;

      @Override
      public ResponseInfo<String> queryFreight(JDPostParam param) {

            List<JDSkuNumsParam> resParam = param.getSkuNums();
            String provinceName =param.getProvinceName();
            String cityName =param.getCityName();
            String countyName = param.getCountyName();
            String townName = param.getTownName();
            Long town;
            //组装查询运费的参数
            List<JDFreightSkuParam> freightSkus = new ArrayList<>();
            JDFreightSkuParam freightSkuParam = new JDFreightSkuParam();
            //组装 List<JDFreightSkuParam>
            for (JDSkuNumsParam item : resParam) {
                  // 根据获取到的 skuId拿到 skuNo
                  log.info("====开始请求sku===={}====", item.getSkuId());
                  ResponseInfo<String> res = productManageFeign.getSkuNoBySkuId(item.getSkuId());
                  log.info("====请求返回结果===={}====", res);
                  if (res.isSuccess()) {
                        String skuNo = res.getData();
                        freightSkuParam.setSkuId(Long.valueOf(skuNo));
                        freightSkuParam.setNum(item.getNum());
                        freightSkus.add(freightSkuParam);
                        log.info("====封装结果=", resParam);
                  }
            }

            // 根据省市区匹配 area_id
            JdAddr jdprovience = jdAddrService.getProvinceByName(provinceName.replace("省", ""));
            JdAddr jdcity = jdAddrService.getAreaByNameAndParentId(jdprovience.getAreaId(), cityName);
            JdAddr jdcounty = jdAddrService.getAreaByNameAndParentId(jdcity.getAreaId(), countyName);
            if(StringUtils.isNotEmpty(townName)){
                  JdAddr jdtown = jdAddrService.getAreaByNameAndParentId(jdcounty.getAreaId(), townName);
                  town = jdtown.getAreaId();
            }else {
                  town = 0L;
            }
            //组装最终的参数
            JDFreightParam freightParam = JDFreightParam.builder().skus(freightSkus).
                      province(jdprovience.getAreaId()).city(jdcity.getAreaId()).county(jdcounty.getAreaId()).town(town).build();

            //运费查询结果
            log.info("开始查询邮费，参数 freightParam={}", freightParam);
            JDFreightDto freight = jdService.getFreight(freightParam);
            if (freight != null) {
                  BigDecimal totalFreight = freight.getFreight();
                  log.info("=====查询结果={}====", freight);
                  if(totalFreight.compareTo(new BigDecimal(8))<1){
                        totalFreight = new BigDecimal(0);
                  }else{
                        totalFreight = totalFreight.subtract(new BigDecimal(8));
                  }
                  return ResponseInfo.success(totalFreight.toString());
            }

            return new ResponseInfo("p555", "获取数据异常，请稍后重试", null);
      }

      /**
       * @param
       * @return
       */
      @Override
      public ResponseInfo checkSale(JDSaleSkusParam skusParam) {

            StringBuilder builder = new StringBuilder();
            String checkSku = "";
            String skuIds = skusParam.getSkuIds();
            if (skuIds.contains(",")) {
                  String[] skus = skuIds.split(",");
                  //遍历skus，查出 skuNo后再组装成 jd需要的参数
                  for (String item : skus) {
                        // 根据获取到的 skuId拿到 skuNo
                        log.info("====开始请求sku===={}====", item);
                        ResponseInfo<String> res = productManageFeign.getSkuNoBySkuId(Long.valueOf(item));
                        if (res.isSuccess()) {
                              String skuNo = res.getData();
                              builder.append(skuNo).append(",");
                        }
                  }
                  String temp = builder.toString();
                  if (StringUtils.isNotEmpty(temp)) {
                        checkSku = temp.substring(0, temp.length() - 1);
                  }
            } else {
                  ResponseInfo<String> res = productManageFeign.getSkuNoBySkuId(Long.valueOf(skuIds));
                  if (res.isSuccess()) {
                        String skuNo = res.getData();
                        checkSku = skuNo;
                  }
            }

            log.info("===开启请求商品是否可售====");
            return jdService.checkGoods(checkSku);
      }

      @Override
      public ResponseInfo queryAreaLimit(JDAreaLimitParam param) {

            StringBuilder builder = new StringBuilder();
            String areaLimitSku = "";
            String skuIds = param.getSkuIds();
            String provinceName = param.getProvinceName();
            String cityName = param.getCityName();
            String countyName = param.getCountyName();
            String townName = param.getTowntyName();
            Long town;
            if (skuIds.contains(",")) {
                  String[] skus = skuIds.split(",");
                  //遍历skus，查出 skuNo后再组装成 jd需要的参数
                  for (String item : skus) {
                        // 根据获取到的 skuId拿到 skuNo
                        log.info("====开始请求sku===={}====", item);
                        ResponseInfo<String> res = productManageFeign.getSkuNoBySkuId(Long.valueOf(item));
                        if (res.isSuccess()) {
                              String skuNo = res.getData();
                              builder.append(skuNo).append(",");
                        }
                  }
                  String temp = builder.toString();
                  if (StringUtils.isNotEmpty(temp)) {
                        areaLimitSku = temp.substring(0, temp.length() - 1);
                  }
            } else {
                  ResponseInfo<String> res = productManageFeign.getSkuNoBySkuId(Long.valueOf(skuIds));
                  if (res.isSuccess()) {
                        String skuNo = res.getData();
                        areaLimitSku = skuNo;
                  }
            }

            // 根据省市区匹配 area_id
            JdAddr jdprovience = jdAddrService.getProvinceByName(provinceName.replace("省", ""));
            JdAddr jdcity = jdAddrService.getAreaByNameAndParentId(jdprovience.getAreaId(), cityName);
            JdAddr jdcounty = jdAddrService.getAreaByNameAndParentId(jdcity.getAreaId(), countyName);
            if(StringUtils.isNotEmpty(townName)){
                  JdAddr jdtown = jdAddrService.getAreaByNameAndParentId(jdcounty.getAreaId(), townName);
                  town = jdtown.getAreaId();
            }else {
                  town = 0L;
            }
            //调用接口，查询商品区域限制购买
            log.info("===开始查询商品区域限制购买");
            return jdService.getAreaLimit(areaLimitSku, jdprovience.getAreaId(), jdcity.getAreaId(), jdcounty.getAreaId(), town);
      }

      @Override
      public ResponseInfo queryStock(JDStockParam param) {

            List<JDSkuNumsParam> resParam = param.getSkuNums();
            String provinceName = param.getProvinceName();
            String cityName = param.getCityName();
            String countyName = param.getCountyName();
            String townName = param.getTownName();
            Long town;

            //组装查询库存的参数
            List<JDSkuNumsParam> stockSkus = new ArrayList<>();
            JDSkuNumsParam stockSkuParam =null;
            //组装 List<JDFreightSkuParam>
            for (JDSkuNumsParam item : resParam) {
                  // 根据获取到的 skuId拿到 skuNo
                  log.info("====开始请求sku===={}====", item.getSkuId());
                  ResponseInfo<String> res = productManageFeign.getSkuNoBySkuId(item.getSkuId());
                  log.info("====请求返回结果===={}====", res);
                  if (res.isSuccess()) {
                        stockSkuParam = new JDSkuNumsParam();
                        String skuNo = res.getData();
                        stockSkuParam.setSkuId(Long.valueOf(skuNo));
                        stockSkuParam.setNum(item.getNum());
                        stockSkus.add(stockSkuParam);
                        log.info("====封装结果=", resParam);
                  }
            }
            // 根据省市区匹配 area_id
            JdAddr jdprovience = jdAddrService.getProvinceByName(provinceName.replace("省", ""));
            JdAddr jdcity = jdAddrService.getAreaByNameAndParentId(jdprovience.getAreaId(), cityName);
            JdAddr jdcounty = jdAddrService.getAreaByNameAndParentId(jdcity.getAreaId(), countyName);
            if(StringUtils.isNotEmpty(townName)){
                  JdAddr jdtown = jdAddrService.getAreaByNameAndParentId(jdcounty.getAreaId(), townName);
                  town = jdtown.getAreaId();
            }else {
                  town = 0L;
            }
            //查询库存
            String area = jdprovience.getAreaId() + "_" + jdcity.getAreaId() + "_" + jdcounty.getAreaId() + "_" + town;
            log.info("=====开始请求查询库存=====");
            List<JDStockDto> stockDtoList = jdService.batchQueryStock(stockSkus, area);
            if (CollectionUtils.isEmpty(stockDtoList)) {
                  log.info("queryStock 调用 jd接口异常，返回结果：stockDtoList={}", stockDtoList);
                  return new ResponseInfo("0000", "京东商品，亲，该订单中包含无货的商品", false);
            }
            StringBuffer msg = new StringBuffer();
            for (JDStockDto stockDto : stockDtoList) {
                  if (stockDto.getStockStateId() == 34) {
                        msg.append(stockDto.getSkuId()+" sku "+stockDto.getStockStateDesc() + "");
                  }
            }
            log.info("当前请求库存结果，预下单库存列表 stockDtoList={}", stockDtoList);

            if (StringUtils.isNotEmpty(msg.toString())) {
                  log.warn("==========库存无货=========={}", msg.toString());
                  return new ResponseInfo("0000", "京东商品，亲，该订单中包含无货的商品", false);
            }
            return ResponseInfo.success(true);
      }
}
