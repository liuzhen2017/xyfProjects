package com.xinyunfu.service.impl;


import com.alibaba.fastjson.JSON;
import com.xinyunfu.config.JDGateWayConfig;
import com.xinyunfu.agent.RestTemplateAgent;
import com.xinyunfu.dto.jd.goods.*;
import com.xinyunfu.dto.jd.order.JDOrderDto;
import com.xinyunfu.dto.jd.order.JDThirdOrderDto;
import com.xinyunfu.dto.jd.param.*;
import com.xinyunfu.dto.jd.shelf.*;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.JdAddr;
import com.xinyunfu.service.JDAddrService;
import com.xinyunfu.service.JDGateWayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class JDGateWayServiceImpl implements JDGateWayService {

      @Autowired
      private RestTemplateAgent restTemplateAgent;

      @Autowired
      private JDGateWayConfig jdGateWayConfig;

      @Autowired
      private ProductManageFeign productManageFeign;

      @Autowired
      private JDAddrService jdAddrService;
      /**
       * get请求 获取token
       */
      @Override
      public String getToken() {

            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getGetTokenUrl();
            // 组装url
            String url = baseUrl + method;
            // 获取header参数
            String headerParam = this.getAuthorization();
            String token = restTemplateAgent.getToken(url, headerParam);
            if (token != null) {
                  log.info(" token={}", token);
                  return token;
            }
            return null;
      }

      /**
       * post请求  生成序列号，下单时用
       */
      @Override
      public Integer createSerialNum() {
            int serialNum;
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getSerialNumUrl();
            // 组装url
            String url = baseUrl + method;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            serialNum = restTemplateAgent.createSerialNum(url, headerParam);
            return serialNum;
      }

      /**
       * POST请求 添加1个上架位置
       */
      @Override
      public boolean addShelf(JDShelfParam shelfParam) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfUrl();
            // 组装url
            String url = baseUrl + method;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            return restTemplateAgent.addShelf(url, headerParam, shelfParam);
      }

      /**
       * PATCH请求 修改指定id的上架位置
       */
      @Override
      public boolean updateSP(JDSPParam spParam) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfUrl();
            // 组装url
            String url = baseUrl + method;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            return restTemplateAgent.updateSP(url, headerParam, spParam);

      }

      /**
       * 根据位置id获得上架位置
       */
      @Override
      public JDShelfPositionDto getShelfPosition(String id) {

            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfUrl();
            // 组装url
            String url = baseUrl + method + "?id=" + id;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            return restTemplateAgent.getShelfPosition(url, headerParam);

      }

      /**
       * 根据父id获得上架位置
       */
      @Override
      public List<JDShelfPositionDto> getSPList(String father_id) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfUrl();
            // 组装url
            String url = baseUrl + method + "?father_id=" + father_id;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            List<JDShelfPositionDto> shelfPostionList = restTemplateAgent.getSPList(url, headerParam);
            return shelfPostionList;
      }

      /**
       * DELETE请求 删除上架位置
       */
      @Override
      public boolean deleteSP(String id) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfUrl();
            // 组装url
            String url = baseUrl + method + "?id=" + id;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            return restTemplateAgent.deleteSP(url, headerParam);
      }

      /**
       * POST请求 商户商品上架
       */
      @Override
      public boolean shelfGoods(List<ShelfGoodsParam> shelfGoodsParam) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfGoodsUrl();
            // 组装url
            String url = baseUrl + method;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            return restTemplateAgent.shelfGoods(url, headerParam, shelfGoodsParam);
      }


      /**
       * DELETE请求 商户商品下架
       *
       * @param skuIds   sku串，以逗号分隔
       * @param shelf_id 上架位置的id
       * @return
       */
      @Override
      public boolean deleteSG(String skuIds, int shelf_id) {

            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfGoodsUrl();
            // 组装url
            String url = baseUrl + method + "?skuids=" + skuIds + "&shelf_id=" + shelf_id;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            return restTemplateAgent.deleteSG(url, headerParam, skuIds);

      }

      /**
       * 分页查询上架商品列表
       */
      @Override
      public List<JDShlefGoodsDto> getSGList(String shelf_id, String skuIds, String page, String pagesize) {

            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfGoodsUrl();
            String url = baseUrl + method + "?shelf_id=" + shelf_id + "&skuids=" + skuIds +
                      "&page=" + page + "&pagesze=" + pagesize;

            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            List<JDShlefGoodsDto> shlefGoodsList = restTemplateAgent.getSGList(url, headerParam);
            return shlefGoodsList;
      }


      /**
       * 根据商品编号串获取上架商品列表
       *
       * @param skus sku串，以逗号分隔，示例：1095883,2815801
       * @return
       */
      @Override
      public List<JDShlefGoodsDto> getShelfGoodsList(String skus) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfGoodsUrl();
            String url = baseUrl + method + "?skus=" + skus;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            List<JDShlefGoodsDto> shlefGoodsList = restTemplateAgent.getShelfGoodsList(url, headerParam);
            return shlefGoodsList;
      }

      /**
       * 分页查询上架商品列表，并获取总数
       */
      @Override
      public JDSGWrapperDto getSGWrapperDto(int shelf_id, String skuIds, String name, int page, int pagesize) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfGoodsUrl();
            String url = baseUrl + method + "?shelf_id=" + shelf_id +
                      "&skuids=" + skuIds +
                      "&name=" + name +
                      "&page=" + page +
                      "&pagesize=" + pagesize;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            JDSGWrapperDto sgWrapperDto = restTemplateAgent.getSGWrapperDto(url, headerParam);
            return sgWrapperDto;
      }

      /**
       * 上架商品的状态
       */
      @Override
      public List<JDSGStatusDto> getSGStatus(String skus) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfGoodsStateUrl();
            String url = baseUrl + method + "?skus=" + skus;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            List<JDSGStatusDto> jdsgStatusDtoList = restTemplateAgent.getSGStatus(url, headerParam);
            return jdsgStatusDtoList;
      }

      /**
       * 上架商品详情
       */
      @Override
      public JDShlefGoodsDto getSGDetail(String sku, String shelf_id) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getShelfGoodsUrl();
            String url = baseUrl + method + "?sku=" + sku + "&shelf_id=" + shelf_id;
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;
            JDShlefGoodsDto shlefGoodsDto = restTemplateAgent.getSGDetail(url, headerParam);
            return shlefGoodsDto;
      }

      /**
       * get请求  获取商品类目列表
       */
      @Override
      public List<JDGoodsPoolDto> getGoodsPoolList() {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getGoodsPoolUrl();
            // 组装url
            String url = baseUrl + method;
            // 获取header参数
            String headerParam = this.getAuthorization();
            List<JDGoodsPoolDto> goodsPoolList = restTemplateAgent.getGoodsPoolList(url, headerParam);
            return goodsPoolList;
      }

      @Override
      public JDGoodsListDto getGoodsList(JDGoodsListParam goodsListParam) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getGoodsListUrl();
            // 组装url
            String url = baseUrl + method + "?page=" + goodsListParam.getPage() +
                      "&pagesize=" + goodsListParam.getPagesize() +
                      "&admin_status=" + goodsListParam.getAdmin_status() +
                      "&start_rebate=" + goodsListParam.getStart_rebate() +
                      "&end_rebate=" + goodsListParam.getEnd_rebate() +
                      "&pool_page_num=" + goodsListParam.getPool_page_num() +
                      "&skuid=" + goodsListParam.getSkuId() +
                      "&name=" + goodsListParam.getName();
            // 获取header参数
            String headerParam = this.getAuthorization();
            JDGoodsListDto goodsListDto = restTemplateAgent.getGoodsList(url, headerParam);
            return goodsListDto;
      }

      /**
       * 获取商品详情
       */
      @Override
      public JDGoodsDetailDto getGoodsDetail(JDGoodsDetailParam goodsDetailParam) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getGoodsDetailUrl();
            String url = baseUrl + method + "?sku=" + goodsDetailParam.getSku() + "&isShow=" + goodsDetailParam.getIsShow();
            // 获取header参数
            String headerParam = this.getAuthorization();
            JDGoodsDetailDto goodsDetailDto = restTemplateAgent.getGoodsDetail(url, headerParam);
            return goodsDetailDto;
      }

      @Override
      public List<JDGoodsState> getGoodsState(String skus) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getGoodsStateUrl();
            String url = baseUrl + method + "?skus=" + skus;
            // 获取header参数
            String headerParam = this.getAuthorization();
            List<JDGoodsState> goodsStateList = restTemplateAgent.getGoodsState(url, headerParam);
            return goodsStateList;
      }

      @Override
      public JDGoodsStyleDto getGoodsStyle(long sku, int type) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getGoodsStyleUrl();
            String url = baseUrl + method + "?sku=" + sku + "&type=" + type;
            // 获取header参数
            String headerParam = this.getAuthorization();
            JDGoodsStyleDto goodsStyleDto = restTemplateAgent.getGoodsStyle(url, headerParam);
            return goodsStyleDto;
      }

      /**
       * 根据商品编号获取商品图片
       */
      @Override
      public Map<String, List<JDImage>> getGoodsImage(String proNo) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getImageUrl();
            // 组装url
            String url = baseUrl + method + "?sku=" + proNo;
            // 获取header参数
            String headerParam = this.getAuthorization();
            Map<String, List<JDImage>> imageMap = restTemplateAgent.getGoodsImage(url, headerParam, proNo);
            return imageMap;
      }

      /**
       * 查询运费
       */
      @Override
      public JDFreightDto getFreight(JDFreightParam freightParam ) {

            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getFreightUrl();
            // 拼接sku为jd要求的格式
            List<JDFreightSkuParam> skus = freightParam.getSkus();
            String json = JSON.toJSONString(skus);
            // 拼接url，json格式的参数需要使用{value}形式传参
            String url = baseUrl + method + "?sku={json}" + "&province=" + freightParam.getProvince() +
                      "&city=" + freightParam.getCity() + "&county=" + freightParam.getCounty() + "&town=" + freightParam.getTown();

            // 获取header参数
            String headerParam = this.getAuthorization();
            JDFreightDto freightDto = restTemplateAgent.getFreight(url, headerParam, json);
            return freightDto;
      }

      /**
       * 获取JD价格
       */
      @Override
      public List<JDPriceDto> getPrice(String skuIds) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getPriceUrl();
            // 组装url
            String url = baseUrl + method + "?skuids=" + skuIds;
            // 获取header参数
            String headerParam = this.getAuthorization();
            List<JDPriceDto> priceDtoList = restTemplateAgent.getPrice(url, headerParam);
            return priceDtoList;
      }

      /**
       * 获取分类信息
       */
      @Override
      public JDCategoryDto getCategory(String cid) {

            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getCategoryUrl();
            // 组装url
            String url = baseUrl + method + "?cid=" + cid;
            // 获取header参数
            String headerParam = this.getAuthorization();
            JDCategoryDto categoryDto = restTemplateAgent.getCategory(url, headerParam);
            return categoryDto;
      }

      @Override
      public List<JDSimilarGoodsDto> getSimilarGoods(String skuId) {

            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getSimilarGoodsUrl();
            // 组装url
            String url = baseUrl + method + "?skuId=" + skuId;
            // 获取header参数
            String headerParam = this.getAuthorization();
            List<JDSimilarGoodsDto> similarGoods = restTemplateAgent.getSimilarGoods(url, headerParam);
            return similarGoods;
      }


      /**
       * 获取库存  批量获取商品库存接口（建议订单详情页、下单使用）
       */
      @Override
      public List<JDStockDto> batchQueryStock(List<JDSkuNumsParam> skuNums, String area) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getStockUrl();
            // 拼接sku为jd要求的格式 	[{"skuId": 2815801, "num":101}]
            String json = JSON.toJSONString(skuNums);
            // 拼接url，json格式的参数需要使用{value}形式传参
            String url = baseUrl + method + "?skuNums={json}" + "&area=" + area;
            // 获取header参数
            String headerParam = this.getAuthorization();
            List<JDStockDto> stockDtoList = restTemplateAgent.batchQueryStock(url, headerParam, json);
            return stockDtoList;
      }

      /**
       * 批量获取库存  商品列表页使用
       */
      @Override
      public List<JDBatchStockDto> getBatchStock(String skuNums, String area) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getBatchStockUrl();
            // 组装url
            String url = baseUrl + method + "?skuNums=" + skuNums + "&area=" + area;
            // 获取header参数
            String headerParam = this.getAuthorization();
            List<JDBatchStockDto> batchStockDto = restTemplateAgent.getBatchStock(url, headerParam);
            return batchStockDto;
      }

      /**
       * 查询订单
       *
       * @param orderId 京东订单号
       */
      @Override
      public List<JDOrderDto> getOrder(long orderId) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getOrderUrl();
            // 组装url
            String url = baseUrl + method + "?orderId=" + orderId;
            // 获取header参数
            String headerParam = this.getAuthorization();
            List<JDOrderDto> orderDtoList = restTemplateAgent.getOrder(url, headerParam);
            return orderDtoList;
      }

      /**
       * 根据第三方订单号查询京东订单号（从序列号号接口获得）
       */
      @Override
      public JDThirdOrderDto getOrderId(String thirdOrder) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getOrderUrl();
            // 组装url
            String url = baseUrl + method + "?thirdOrder=" + thirdOrder;
            // 获取header参数
            String headerParam = this.getAuthorization();
            JDThirdOrderDto thirdOrderDto = restTemplateAgent.getOrderId(url, headerParam);
            return thirdOrderDto;
      }

      /**
       * POST 请求，预下单
       */
      @Override
      public ResponseInfo<String> preOrder(JDPreOrderDtoParam orderParam) {

            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getOrderUrl();
            // 组装url
            String url = baseUrl + method;
            // 获取header参数
            String token = this.getToken();
            // 拼接header参数
            String headerParam = "Bearer " + token;

            JDPreOrderParam preOrder = JDPreOrderParam.builder().
                      sku(orderParam.getSku()).name(orderParam.getName()).address(orderParam.getAddress())
                      .zip(orderParam.getZip()).phone(orderParam.getPhone()).mobile(orderParam.getMobile())
                      .email(orderParam.getEmail()).remark(orderParam.getRemark()).ext(orderParam.getExt())
                      .build();

            if(StringUtils.isEmpty(orderParam.getZip())) {
                  preOrder.setZip("");
            }

            JDSkuParam[] sku = orderParam.getSku();
            JDSkuParam skuParam = null;

            for (JDSkuParam jp : sku) {
                  skuParam = jp;
                  log.info("====开始请求sku===={}====", skuParam.getSkuId());
                  ResponseInfo<String> res = productManageFeign.getSkuNoBySkuId(skuParam.getSkuId());
                  log.info("====请求返回结果===={}====", res);
                  if (res.isSuccess()) {
                        String skuNo = res.getData();
                        skuParam.setSkuId(Long.valueOf(skuNo));
                        log.info("====封装结果=", sku);
                  }
            }
            long thirdOrder = this.createSerialNum();
            //封装最后
            preOrder.setSku(sku);
            preOrder.setThirdOrder(thirdOrder);


            //查询地址行政编码
            JdAddr jdprovience = jdAddrService.getProvinceByName(orderParam.getProvinceName().replace("省",""));

            JdAddr jdcity = jdAddrService.getAreaByNameAndParentId(jdprovience.getAreaId(),orderParam.getCityName());

            JdAddr jdcounty = jdAddrService.getAreaByNameAndParentId(jdcity.getAreaId(),orderParam.getCountyName());

            preOrder.setProvince(jdprovience.getAreaId());
            preOrder.setCity(jdcity.getAreaId());
            preOrder.setCounty(jdcounty.getAreaId());
            preOrder.setTown(orderParam.getTown());
            //组装查询运费的参数
            List<JDFreightSkuParam> freightSkus = new ArrayList<>();

            //组装商品可售验证sku参数
            StringBuilder checkBuilder = new StringBuilder();
            JDSkuParam[] skuParams = orderParam.getSku();
            List<JDSkuNumsParam> skuNums = new ArrayList<>();
            JDSkuNumsParam param = null;
            for (JDSkuParam item : skuParams) {
                  param = new JDSkuNumsParam();
                  JDFreightSkuParam fsp = JDFreightSkuParam.builder().skuId(item.getSkuId()).num(item.getNum()).build();
                  freightSkus.add(fsp);
                  checkBuilder.append(item.getSkuId()).append(",");

                  param.setNum(item.getNum());
                  param.setSkuId(item.getSkuId());
                  skuNums.add(param);
            }

            JDFreightParam freightParam = new JDFreightParam();
            freightParam.setSkus(freightSkus);
            freightParam.setProvince(jdprovience.getAreaId());
            freightParam.setCity(jdcity.getAreaId());
            freightParam.setCounty(jdcounty.getAreaId());
            freightParam.setTown(orderParam.getTown());

            // 运费查询结果
            log.info("=====开始查询邮费={}====");
            JDFreightDto freight = this.getFreight(freightParam);
            log.info("======参数 freightParam={}",freightParam);
            log.info("=====查询结果={}====", freight);

            //校验收获地址

            //查询商品是否可售
            String temp = checkBuilder.toString();
            String checkSku = temp.substring(0, temp.length() - 1);
            log.info("===开启请求商品是否可售====");
            List<JDCheckDto> checkDtos = this.checkGoods(checkSku);
            log.info("===请求商品是否可售--回复={}===", checkDtos);
            StringBuffer msg = new StringBuffer();
            if (CollectionUtils.isNotEmpty(checkDtos)) {
                  for (JDCheckDto item : checkDtos) {
                        if (!item.getSaleState().equals(1)) {
                              msg.append(item.getName() + " 不可出售!");
                        }
                  }
            }
            if (StringUtils.isNotEmpty(msg.toString())) {
                  log.warn("======商品不可售==={}===", msg.toString());
                  return new ResponseInfo("5555","京东商品，亲，该商品暂时不可售哟",null);
            }

            //4.查询商品区域限制购买
            log.info("===开始查询商品区域限制购买");
            List<JDAreaLimitDto> areaLimit = getAreaLimit(checkSku, jdprovience.getAreaId(), jdcity.getAreaId(), jdcounty.getAreaId(), orderParam.getTown());
            log.info("===开始查询商品区域限制购买,回复 info ={}", areaLimit);
            StringBuffer isRestrict = new StringBuffer();
            for (JDAreaLimitDto dto : areaLimit) {
                  if (dto.isAreaRestrict()) {
                        isRestrict.append(dto.getSkuId() + " 区域限制不可购买!");
                  }
            }
            if (StringUtils.isNotEmpty(isRestrict.toString())) {
                  log.warn("======区域限制不可购买==={}===", isRestrict.toString());
                  return new ResponseInfo("5555","京东商品，亲，该区域暂时不支持购买",null);
            }
            //查询库存
            String cityStr = jdprovience.getAreaId() + "_" + jdcity.getAreaId() + "_" + jdcounty.getAreaId() + "_" + orderParam.getTown();
            log.info("=====开始请求查询库存=====");

            List<JDStockDto> stockDtoList = batchQueryStock(skuNums, cityStr);
            StringBuffer stockBuffer = new StringBuffer();
            for (JDStockDto stockDto : stockDtoList) {
                  if (stockDto.getStockStateId() == 34) {
                        stockBuffer.append(stockDto.getStockStateDesc() + "");
                  }
            }
            log.info("当前请求库存结果，预下单库存列表 stockDtoList={}",stockDtoList);

            if (StringUtils.isNotEmpty(stockBuffer.toString())) {
                  log.warn("==========库存无货=========={}",msg.toString());
                  return new ResponseInfo("5555","京东商品，亲，该地区暂时无货哟",null);
            }

            return restTemplateAgent.preOrder(url, headerParam, preOrder);
      }

      /**
       * PATCH 请求，确认下单
       */
      @Override
      public boolean ensureOrder(long order_no, String type) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getOrderUrl();
            // 组装url
            String url = baseUrl + method + "?order_no=" + order_no + "&type=" + type;
            // 获取header参数
            String token = this.getToken();
            String headerParam = "Bearer " + token;
            return restTemplateAgent.ensureOrder(url, headerParam);
      }


      /**
       * 校验商品时候可售
       *
       * @param skuIds 商品编号，支持批量，以【,】分隔 (最高支持100个商品)
       * @return
       */
      @Override
      public List<JDCheckDto> checkGoods(String skuIds) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getGoodsCheckUrl();
            // 组装url
            String url = baseUrl + method + "?skuIds=" + skuIds;
            // 获取header参数
            String token = this.getToken();
            String headerParam = "Bearer " + token;
            return restTemplateAgent.checkGoods(url, headerParam);
      }


      /**
       * 区域限制购买
       *
       * @param sku      商品编号，支持批量，以【，】分割（最高支持50个）
       * @param province 京东一级地址编号
       * @param city     京东二级地址编号
       * @param county   京东三级地址编号
       * @param town     京东四级地址编号，没有填0
       * @return
       */
      @Override
      public List<JDAreaLimitDto> getAreaLimit(String sku, Long province, Long city, Long county, Long town) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getAreaLimitUrl();
            // 组装url
            String url = baseUrl + method + "?sku=" + sku
                      + "&province=" + province
                      + "&city=" + city
                      + "&county=" + county
                      + "&town=" + town;
            // 获取header参数
            String token = this.getToken();
            String headerParam = "Bearer " + token;
            return restTemplateAgent.getAreaLimit(url, headerParam);
      }

      @Override
      public JDLogisticsDto getOrderTrack(Long orderId) {
            String baseUrl = jdGateWayConfig.getBaseUrl();
            String method = jdGateWayConfig.getLogisticsUrl();
            // 组装url
            String url = baseUrl + method + "?orderId=" + orderId;
            // 获取header参数
            String token = this.getToken();
            String headerParam = "Bearer " + token;
            return restTemplateAgent.getOrderTrack(url, headerParam);
      }

      /**
       * 生成 header参数 Authorization
       */
      public String getAuthorization() {
            String appKey = jdGateWayConfig.getAppKey();
            String appSecret = jdGateWayConfig.getAppSecret();
            long timestamp = System.currentTimeMillis() / 1000;
            // 签名大写
            String sign = DigestUtils.md5Hex(appKey + appSecret + timestamp).toUpperCase();
            // 设置header参数
            String headerParam = "ak " + timestamp + "." + appKey + "." + sign;
            return headerParam;
      }

      /**
       * 根据商品列表，获取skuId
       */
      public List<String> getskuIdList(List<JDGoodsDto> goodsList) {
            List<String> skuIdList = new ArrayList<>();
            for (JDGoodsDto goodsDto : goodsList) {
                  skuIdList.add(goodsDto.getSku().toString());
            }
            return skuIdList;
      }

}
