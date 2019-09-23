package com.xinyunfu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.commons.goods.*;
import com.xinyunfu.config.JDGateWayConfig;
import com.xinyunfu.dto.jd.goods.*;
import com.xinyunfu.dto.jd.param.JDGoodsDetailParam;
import com.xinyunfu.dto.jd.param.JDGoodsListParam;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.GoodsLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SyncDataService {

      @Autowired
      private ProductManageFeign productManageFeign;

      @Autowired
      private JDGateWayService jdGateWayService;

      @Autowired
      private GoodsLogService goodsLogService;

      @Autowired
      private JDGateWayConfig jdGateWayConfig;

      private final Map<String, List<String>> skuIdsMap = new HashMap<>();

      private Map<String, List<JDImage>> imageMap = new HashMap<>();

      protected SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      //固定邮费，从yml中取
      @Value("${postage}")
      private BigDecimal postage;

      public void loadData() {

            long startTime = System.currentTimeMillis();

            //1.遍历主列表数据并入库部分信息
            this.syncGoodsList();

            //2.入库商品和详情
            this.syncGoodsInfo();

            //3入库商品图片
            this.syncImageInfo();

            //4.入库商品属性、属性值
            this.syncGoodsAttr();

            //5.组装groupNo，更新proSku并入库
            this.wrapperSku();

            //todo 6.请求失败数据重试

            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime) / 60000;
            GoodsLog goodsLog = GoodsLog.builder().description("京东商品同步运行时间").
                      startTime(new Timestamp(startTime)).endTime(new Timestamp(endTime)).runTime(time).build();
            goodsLogService.saveGoodsLog(goodsLog);
            log.info("----------------整个流程运行时间 time={} min------------------------", time);

      }

      /**
       * 1.遍历主列表数据并入库部分信息
       */
      public void syncGoodsList() {

            long startTime = System.currentTimeMillis();
            //TODO 获取主列表，后续优化思路：将获取到的主列表单独写入本地的一个表，然后从表获取数据去处理同步入库业务？
            List<JDGoodsDto> goodsList = this.getGoosList();
            List<String> skuIdList = jdGateWayService.getskuIdList(goodsList);
            skuIdsMap.put("skuIdList", skuIdList);
            if (CollectionUtils.isNotEmpty(goodsList)) {
                  //遍历获取每一条商品
                  for (JDGoodsDto goodsDto : goodsList) {

                        wrapperGoodsAndIntoDB(goodsDto);
                  }
            }
            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime) / 60000;
            GoodsLog goodsLog = GoodsLog.builder().description("入库product、proSku部分信息").
                      startTime(new Timestamp(startTime)).endTime(new Timestamp(endTime)).runTime(time).build();
            goodsLogService.saveGoodsLog(goodsLog);
            log.info("同步主列表 运行时间 time={}min", time);
      }

      /**
       * 2.入库商品和详情
       */
      public void syncGoodsInfo() {

            long startTime = System.currentTimeMillis();
            String proNo = "";
            List<String> skuIdList = skuIdsMap.get("skuIdList");
            if (CollectionUtils.isNotEmpty(skuIdList)) {
                  for (String skuId : skuIdList) {
                        proNo = "JD" + skuId;
                        //根据proNo从本地库查询 product
                        try {
                              ResponseInfo<Product> resProduct = productManageFeign.queryProductByProNo(proNo);
                              Product product = resProduct.getData();
                              if (product != null) {
                                    Long proId = product.getProId();
                                    ProDetails proDetails = ProDetails.builder().proId(proId).build();
                                    //组装主列表数据和商品详情
                                    this.getGoodsDetailInfo(skuId, product, proDetails);
                                    //入库
                                    this.updateProIntoDb(product);
                                    this.proDetailsIntoDb(proDetails, proId);
                              }
                        } catch (Exception e) {
                              log.info("syncGoodsInfo 异常,msg={},e={}", e.getMessage(), e);
                        }
                  }
            }
            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime) / 60000;
            GoodsLog goodsLog = GoodsLog.builder().description("更新主列表并入库商品详情").
                      startTime(new Timestamp(startTime)).endTime(new Timestamp(endTime)).runTime(time).build();
            goodsLogService.saveGoodsLog(goodsLog);
            log.info("更新product和入库商品详情 运行时间 time={}min", time);
      }

      /**
       * 3.入库商品图片
       * <p>
       * 调用JD接口批量查询商品图片信息（最高支持100个），库存（商品列表）入库
       */
      public void syncImageInfo() {

            long startTime = System.currentTimeMillis();
            //获取所有的skuids
            List<String> skuIdList = skuIdsMap.get("skuIdList");
            int length = skuIdList.size();
            int pageSize = 100;
            String skus = "";

            //每100个sku串为一组
            if (length > pageSize) {
                  int i = 0;
                  for (i = 0; i < ((length - 1) / pageSize); i++) {
                        List<String> segmentList = skuIdList.subList(i * pageSize, (i + 1) * pageSize);
                        //拼接sku串
                        skus = this.wrapperStr(segmentList);
                        //根据sku串批量查询图片信息
                        imageMap = jdGateWayService.getGoodsImage(skus);
                        this.wrapperImageAndIntoDB(segmentList, imageMap);
                  }

                  //最后一组的数据
                  List<String> appendList = skuIdList.subList(i * pageSize, length);
                  skus = this.wrapperStr(appendList);
                  imageMap = jdGateWayService.getGoodsImage(skus);
                  this.wrapperImageAndIntoDB(appendList, imageMap);
            } else {
                  skus = this.wrapperStr(skuIdList);
                  imageMap = jdGateWayService.getGoodsImage(skus);
                  this.wrapperImageAndIntoDB(skuIdList, imageMap);
            }

            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime) / 60000;
            GoodsLog goodsLog = GoodsLog.builder().description("同步图片").
                      startTime(new Timestamp(startTime)).endTime(new Timestamp(endTime)).runTime(time).build();
            goodsLogService.saveGoodsLog(goodsLog);
            log.info("同步图片 运行时间 time={}min", time);
      }

      /**
       * 4.入库商品属性、属性值
       */
      public void syncGoodsAttr() {

            long startTime = System.currentTimeMillis();
            String proNo = "";
            List<String> skuIdList = skuIdsMap.get("skuIdList");

            for (String skuId : skuIdList) {
                  proNo = "JD" + skuId;
                  //根据proNo从本地库查询 proId
                  try {
                        ResponseInfo<Long> resProId = productManageFeign.getProIdByProNo(proNo);
                        Long proId = resProId.getData();
                        if (proId != null) {
                              //获取同类商品列表
                              List<JDSimilarGoodsDto> similarGoodsList = jdGateWayService.getSimilarGoods(skuId);

                              if (CollectionUtils.isNotEmpty(similarGoodsList)) {
                                    for (JDSimilarGoodsDto similarGood : similarGoodsList) {
                                          String propertyName = similarGood.getSaleName();

                                          ProProperty proProperty = ProProperty.builder().proId(proId).propertyName(propertyName).build();
                                          //proProperty入库
                                          this.proPropertyIntoDb(proProperty, proId, propertyName);

                                          try {
                                                // TODO 后续优化：属性值和属性也是通过自增id关联，需要某种策略不通过查库就可获得关联字段
                                                ResponseInfo<ProProperty> resProperty = productManageFeign.queryPropertyByProIdAndName(proId, propertyName);
                                                ProProperty dbProperty = resProperty.getData();
                                                //如果这里入库失败，则dbProperty有可能为null
                                                if (dbProperty != null) {
                                                      Long propertyId = dbProperty.getPropertyId();
                                                      //属性值 和相关联的skuid 列表
                                                      List<JDGoodsSaleAttr> saleAttrList = similarGood.getSaleAttrList();

                                                      //本身一个proProperty对应多个proPropertyValue，现在为了简便入库，设定一个proid对应一个skuid，一个skuid对应多个属性，每个属性对应一个值
                                                      for (JDGoodsSaleAttr goodsSaleAttr : saleAttrList) {
                                                            String[] relatedSkus = goodsSaleAttr.getSkuIds();
                                                            if (relatedSkus != null && relatedSkus.length > 0) {
                                                                  for (int i = 0; i < relatedSkus.length; i++) {
                                                                        String sku = relatedSkus[i];
                                                                        if (sku.equals(skuId)) {
                                                                              String valueText = goodsSaleAttr.getSaleValue();
                                                                              //组装 proPropertyValue
                                                                              ProPropertyValue proPropertyValue = ProPropertyValue.builder().propertyId(
                                                                                        propertyId).valueText(valueText).build();
                                                                              //属性值入库
                                                                              this.proPropertyValueIntoDb(proPropertyValue, propertyId, valueText);
                                                                        }
                                                                  }
                                                            }
                                                      }
                                                }
                                          } catch (Exception e) {
                                                log.info("Feign调用 queryPropertyByProIdAndName 异常,msg={},e={}", e.getMessage(), e);
                                          }
                                    }
                              }
                        }
                  } catch (Exception e) {
                        log.info("syncGoodsInfo 异常,msg={},e={}", e.getMessage(), e);
                  }

            }

            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime) / 60000;
            GoodsLog goodsLog = GoodsLog.builder().description("同步属性、属性值").
                      startTime(new Timestamp(startTime)).endTime(new Timestamp(endTime)).runTime(time).build();
            goodsLogService.saveGoodsLog(goodsLog);
            log.info("同步属性、属性值 运行时间 time={}min", time);
      }


      /**
       * 获取商品主列表信息
       */
      public List<JDGoodsDto> getGoosList() {

            log.info("begin 获取主列表");
            long startTime = System.currentTimeMillis();
            JDGoodsListParam goodsListParam = new JDGoodsListParam();
            List<JDGoodsDto> jdGoodsDtos = new ArrayList<>();

            try {
                  int page = 0;
                  goodsListParam.setPage(page);
                  //TODO 优化：请求JD接口异常，写入库？
                  JDGoodsListDto goodsListDto = jdGateWayService.getGoodsList(goodsListParam);
                  if (goodsListDto != null) {
                        List<JDGoodsDto> goodsList = goodsListDto.getData();
                        //获取当前页（默认第一页，京东 page从0开始）的数据并入库
                        if (CollectionUtils.isNotEmpty(goodsList)) {
                              jdGoodsDtos.addAll(goodsList);
                        }
                        //每次查询一页（30条）数据
                        while (goodsListDto.getTotal() > (goodsListParam.getPage() + 1) * goodsListParam.getPagesize()) {
                              //分页
                              goodsListParam.setPage(++page);
                              log.info("进入分页，total={},page={},poolPage={}",
                                        goodsListDto.getTotal(), goodsListParam.getPage() + 1, goodsListParam.getPool_page_num());
                              goodsListDto = jdGateWayService.getGoodsList(goodsListParam);
                              if (goodsListDto != null) {
                                    goodsList = goodsListDto.getData();
                                    if (CollectionUtils.isNotEmpty(goodsList)) {
                                          jdGoodsDtos.addAll(goodsList);
                                    }
                              }
                        }
                  }
            } catch (Exception e) {
                  log.error("syncGoosList 异常,msg={},e={}", e.getMessage(), e);
            }

            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime) / 60000;
            String remark = "商品数量: " + String.valueOf(jdGoodsDtos.size());
            GoodsLog goodsLog = GoodsLog.builder().description("拉取主列表").
                      startTime(new Timestamp(startTime)).endTime(new Timestamp(endTime)).runTime(time).remark(remark).build();
            goodsLogService.saveGoodsLog(goodsLog);
            log.info("拉取主列表运行时间 time={}min", time);
            log.info("商品数量：total={}", jdGoodsDtos.size());

            return jdGoodsDtos;

      }

      /**
       * 组装主列表并入库
       *
       * @param goodsDto
       */
      public void wrapperGoodsAndIntoDB(JDGoodsDto goodsDto) {
            if (goodsDto != null) {
                  String skuId = goodsDto.getSku().toString();
                  //先组装部分product固定的属性
                  //TODO 优化：从JD接口获取数据后入本地库的JD表，查询时一次性查出所需要的数据，直接组装成一个完整的对象？
                  String proNo = "JD" + skuId;
                  Product product = Product.builder().proNo(proNo).proName(goodsDto.getName()).status(99).source(1).build();
                  //TODO 优化：所有需要入库的对象组装好后放入队列，批量入库？
                  // if(goodsDto.getStatus() != )
                  //product 入库
                  this.productIntoDb(product, proNo);

                  //先组装部分sku的属性
                  //TODO 优化：子表关联product表时的关联字段修改为String类型，组装对象时不再需查库操作？
                  try {
                        ResponseInfo<Long> resProId = productManageFeign.getProIdByProNo(proNo);
                        Long proId = resProId.getData();
                        if (proId != null) {
                              //组装 ProChannelRelation
                              String category = goodsDto.getCategory();       //"category": "6728;6743;6757"，只需获取三级分类信息
                              if (category != null) {
                                    int temp = category.lastIndexOf(";");
                                    Integer channelId = Integer.valueOf(category.substring(temp + 1));
                                    ProChannelRelation pcr = ProChannelRelation.builder().proId(proId).channelId(channelId).build();
                                    //proChannelRelation 入库
                                    this.proCRIntoDb(pcr, proId, channelId);
                              }

                              //组装proSku
                              //协议价，对应JD price
                              BigDecimal costPrice = goodsDto.getPrice();
                              //售价，对应JD Jd_price
                              BigDecimal price = goodsDto.getJd_price();
                              String imagePath = jdGateWayConfig.getImagePrefixUrl() + goodsDto.getImage_path();
                              ProSku proSku = ProSku.builder().skuNo(skuId).proId(proId).
                                        imgUrl(imagePath).price(price.setScale(2, BigDecimal.ROUND_HALF_UP)).
                                        costPrice(costPrice.setScale(2, BigDecimal.ROUND_HALF_UP)).build();

                              //计算最低价和总价
                              this.calculatePrice(proSku, price, costPrice, skuId);
                              //proSku 入库，groupNo后续再入库
                              this.proSkuIntoDb(proSku, proId);
                        }

                  } catch (Exception e) {
                        log.info("wrapperGoodsAndIntoDB 异常,msg={},e={}", e.getMessage(), e);

                  }

            }

      }


      /**
       * 获取详情并入库
       */
      public void getGoodsDetailInfo(String skuId, Product product, ProDetails proDetails) {

            JDGoodsDetailParam goodsDetailParam = new JDGoodsDetailParam();
            goodsDetailParam.setSku(skuId);
            JDGoodsDetailDto goodsDetailDto = jdGateWayService.getGoodsDetail(goodsDetailParam);

            if (goodsDetailDto != null) {
                  product.setWeight(goodsDetailDto.getWeight());
                  product.setUnit(goodsDetailDto.getSaleUnit());
                  proDetails.setSpecs(goodsDetailDto.getParam());
                  proDetails.setDetails(goodsDetailDto.getAppintroduce());
            } else {
                  log.info("获取商品详情失败，skuId={}", skuId);
            }
      }


      /**
       * 计算sku 最低价、总价并入库
       *
       * @param proSku    待入库的sku对象
       * @param price     售价，对应JD Jd_price
       * @param costPrice 协议价，对应JD price
       */
      public void calculatePrice(ProSku proSku, BigDecimal price, BigDecimal costPrice, String skuId) {

            //原价
            BigDecimal marketPrice = price.multiply(new BigDecimal(1.3)).setScale(2, BigDecimal.ROUND_HALF_UP);
            //综合成本
            BigDecimal totalCost = costPrice.multiply(new BigDecimal(1.16)).add(postage).setScale(2, BigDecimal.ROUND_HALF_UP);
            //折扣
            BigDecimal rebate = costPrice.divide(price,2);
            //综合折扣
            BigDecimal totalRebate = totalCost.divide(price, 2);
            //券后价
            BigDecimal minSellPrice;

            //7折以上的产品直接下架
            if(rebate.compareTo(new BigDecimal(0.7))==1){
                  proSku.setStatus(1);
                  minSellPrice = new BigDecimal(1000000);
            }else if(price.compareTo(new BigDecimal(50))<1){
                  if(totalCost.compareTo(new BigDecimal(25))<1){
                        //销售价<=50元，并且运营成本 <=25 ,则设置为 0
                        minSellPrice = new BigDecimal(0);
                  }else {
                        //销售价<=50元，并且运营成本 > 25 ,则直接下架
                        proSku.setStatus(1);
                        minSellPrice = new BigDecimal(1000000);
                  }
            }else{
                  minSellPrice = totalRebate.subtract(new BigDecimal(0.45)).multiply(price);
                  if(minSellPrice.compareTo(new BigDecimal(0))==-1){
                        minSellPrice = new BigDecimal(0);
                  }
            }

            proSku.setMarketPrice(marketPrice);
            proSku.setMinSellPrice(minSellPrice);

      }

      /**
       * 在property和propertyValue入库后，才能组装groupNo，再更新proSku并入库
       */
      @Async
      public void wrapperSku() {
            long startTime = System.currentTimeMillis();

            //最终拼接的gourpNo
            String finalGroupNo = "";
            String temp = "";
            List<String> groupNoList = new ArrayList<>();
            StringBuilder gnBulider = new StringBuilder();
            try {
                  //1.先获取所有的来源为JD，状态为99待审核的 product，拿到proId列表
                  ResponseInfo<List<Long>> response = productManageFeign.getJDProductIds(1, 99);
                  List<Long> productList = response.getData();
                  if (CollectionUtils.isNotEmpty(productList)) {
                        for (Long proId : productList) {
                              //根据proId查询property
                              try {
                                    ResponseInfo<List<ProProperty>> attrResponse = productManageFeign.getAttrListByProId(proId);
                                    List<ProProperty> propertyList = attrResponse.getData();

                                    if (CollectionUtils.isNotEmpty(propertyList)) {
                                          for (ProProperty property : propertyList) {
                                                Long propertyId = property.getPropertyId();
                                                //根据propertyId查询propertyValue
                                                try {
                                                      ResponseInfo<List<ProPropertyValue>> attrValueRes = productManageFeign.getPropertyValueByPropertyId(propertyId);
                                                      List<ProPropertyValue> attrValueList = attrValueRes.getData();
                                                      if (CollectionUtils.isNotEmpty(attrValueList)) {
                                                            for (ProPropertyValue attrValue : attrValueList) {
                                                                  Long valueId = attrValue.getValueId();
                                                                  String groupNo = propertyId.toString() + ":" + valueId.toString();
                                                                  groupNoList.add(groupNo);
                                                            }
                                                      }
                                                } catch (Exception e) {
                                                      log.info("Feign调用 getPropertyValueByPropertyId 异常,msg={},e={}", e.getMessage(), e);
                                                }
                                          }
                                    }
                              } catch (Exception e) {
                                    log.info("Feign调用 getAttrListByProId 异常,msg={},e={}", e.getMessage(), e);
                              }

                              if (CollectionUtils.isNotEmpty(groupNoList)) {
                                    for (String str : groupNoList) {
                                          gnBulider.append(str).append(",");
                                    }
                                    //遍历完当前proId下的数据后清空list，供下一遍历使用
                                    groupNoList.clear();
                              }

                              if (gnBulider.length() > 0) {
                                    temp = gnBulider.toString();
                                    //清空gnBulider，供下一次遍历使用
                                    gnBulider.delete(0, gnBulider.length());
                                    finalGroupNo = temp.substring(0, temp.length() - 1);
                              } else {
                                    finalGroupNo = "1:1";
                              }
                              //更新proSku中的groupNo，根据proId查询proSku
                              try {
                                    ResponseInfo<ProSku> skuResponse = productManageFeign.getSkuByProId(proId);
                                    ProSku proSku = skuResponse.getData();
                                    if (proSku != null) {
                                          proSku.setGroupNo(finalGroupNo);
                                          this.updateProSkuAndIntoDb(proSku);
                                    }
                              } catch (Exception e) {
                                    log.info("Feign调用 getSkuByProId 异常,msg={},e={}", e.getMessage(), e);
                              }
                        }
                  }
            } catch (Exception e) {
                  log.info("wrapperSku 异常,msg={},e={}", e.getMessage(), e);
            }

            long endTime = System.currentTimeMillis();
            long time = (endTime - startTime) / 60000;
            GoodsLog goodsLog = GoodsLog.builder().description("组装groupNo并更新prosku").
                      startTime(new Timestamp(startTime)).endTime(new Timestamp(endTime)).runTime(time).build();
            goodsLogService.saveGoodsLog(goodsLog);
            log.info("组装groupNo，更新proSku 运行时间 time={}min", time);

      }


      /**
       * 拼接skuIds串
       */
      public String wrapperStr(List<String> skuIdList) {

            String skus = "";
            StringBuilder skuBuilder = new StringBuilder();
            for (String str : skuIdList) {
                  skuBuilder.append(str).append(",");
            }
            skus = skuBuilder.toString();
            skus.substring(0, skus.length() - 1);
            return skus;
      }

      /**
       * 组装图片信息并入库
       */
      public void wrapperImageAndIntoDB(List<String> segmentList, Map<String, List<JDImage>> imageMap) {

            for (String skuId : segmentList) {
                  List<ProImage> proImageList = new ArrayList<>();
                  String proNo = "JD" + skuId;
                  //根据proNo从本地库查询 product
                  try {
                        ResponseInfo<Product> resProduct = productManageFeign.queryProductByProNo(proNo);
                        Product product = resProduct.getData();
                        if (product != null && imageMap.containsKey(skuId)) {
                              Long proId = product.getProId();
                              List<JDImage> imageList = imageMap.get(skuId);
                              for (JDImage jdImage : imageList) {
                                    String imagePath = jdGateWayConfig.getImagePrefixUrl() + jdImage.getPath();
                                    ProImage proImage = ProImage.builder().proId(proId).imgUrl(imagePath).isDefault(jdImage.getIsPrimary()).build();
                                    proImageList.add(proImage);
                              }

                              //image入库
                              this.proImageIntoDb(proImageList, proId);
                        }
                  } catch (Exception e) {
                        log.info("wrapperImageAndIntoDB 异常,msg={},e={}", e.getMessage(), e);
                  }
            }
      }


//————————————————入库———————————————————————————————————————————————

      /**
       * product入库
       */
      public void productIntoDb(Product product, String proNo) {
            try {
                  // TODO 优化：建JD表后，直接请求本地数据库，不用再通过Feign请求商品管理服务
                  ResponseInfo<Boolean> response = productManageFeign.checkProByProNo(proNo);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        try {
                              ResponseInfo<String> result = productManageFeign.save(product);
                              log.info("product入库信息：result={}", result.getData());
                        } catch (Exception e) {
                              log.info("Feign调用 saveProduct 异常,msg={},e={}", e.getMessage(), e);
                        }
                  }
            } catch (Exception e) {
                  log.info("productIntoDb 异常,msg={},e={}", e.getMessage(), e);
            }

      }


      /**
       * 更新product并入库
       */
      public void updateProIntoDb(Product product) {
            try {
                  ResponseInfo<String> result = productManageFeign.updateProduct(product);
                  log.info("更新本地库product信息：result={}", result.getData());
            } catch (Exception e) {
                  log.info("updateProIntoDb 异常,msg={},e={}", e.getMessage(), e);
            }

      }

      /**
       * ProDetails入库
       */
      public void proDetailsIntoDb(ProDetails proDetails, Long proId) {
            try {
                  ResponseInfo<Boolean> response = productManageFeign.checkProDetailsByProId(proId);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        try {
                              ResponseInfo<String> result = productManageFeign.addProdetails(proDetails);
                              log.info("proDetails入库信息：result={}", result.getData());
                        } catch (Exception e) {
                              log.info("Feign调用 addProdetails 异常,msg={},e={}", e.getMessage(), e);
                        }
                  }

            } catch (Exception e) {
                  log.info("proDetailsIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }

      /**
       * proImage入库
       */
      public void proImageIntoDb(List<ProImage> proImageList, Long proId) {
            try {
                  ResponseInfo<Boolean> response = productManageFeign.checkProImageByProId(proId);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        try {
                              ResponseInfo<String> result = productManageFeign.saveProImages(proImageList);
                              log.info("proImage入库信息：result={}", result.getData());
                        } catch (Exception e) {
                              log.info("Feign调用 saveProImages 异常,msg={},e={}", e.getMessage(), e);
                        }
                  }

            } catch (Exception e) {
                  log.info("proImageIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }

      /**
       * proProperty入库
       */
      public void proPropertyIntoDb(ProProperty proProperty, long proId, String propertyName) {
            try {
                  ResponseInfo<Boolean> response = productManageFeign.checkPropertyByProIdAndName(proId, propertyName);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        try {
                              ResponseInfo<String> result = productManageFeign.addProperty(proProperty);
                              log.info("proProperty入库信息：result={}", result.getData());
                        } catch (Exception e) {
                              log.info("Feign调用 addProperty 异常,msg={},e={}", e.getMessage(), e);
                        }

                  }

            } catch (Exception e) {
                  log.info("proPropertyIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }


      /**
       * proPropertyValue 入库
       */
      public void proPropertyValueIntoDb(ProPropertyValue proPropertyValue, long propertyId, String valueText) {
            try {
                  ResponseInfo<Boolean> response = productManageFeign.checkByPropertyIdAndValueText(propertyId, valueText);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        try {
                              ResponseInfo<String> result = productManageFeign.add(proPropertyValue);
                              log.info("proPropertyValue入库信息：result={}", result.getData());
                        } catch (Exception e) {
                              log.info("Feign调用 add(proPropertyValue) 异常,msg={},e={}", e.getMessage(), e);
                        }
                  }

            } catch (Exception e) {
                  log.info("proPropertyValueIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }


      /**
       * proSku入库
       */
      public void proSkuIntoDb(ProSku proSku, Long proId) {
            try {
                  ResponseInfo<Boolean> response = productManageFeign.checkSkuByProId(proId);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        try {
                              String json = JSON.toJSONString(proSku);
                              ResponseInfo<String> result = productManageFeign.addSku(json);
                              log.info("proSku 入库信息：result={}", result.getData());
                        } catch (Exception e) {
                              log.info("Feign调用 addSku 异常,msg={},e={}", e.getMessage(), e);

                        }
                  }

            } catch (Exception e) {
                  log.info("proSkuIntoDb 异常,msg={},e={}", e.getMessage(), e);

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


      /**
       * ProChannelRelation 入库
       */
      public void proCRIntoDb(ProChannelRelation pcr, Long proId, Integer channelId) {
            try {
                  ResponseInfo<Boolean> response = productManageFeign.checkByPidAndCid(proId, channelId);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        try {
                              ResponseInfo<String> result = productManageFeign.add(pcr);
                              log.info("proChannelRelation 入库信息：result={}", result.getData());
                        } catch (Exception e) {
                              log.info("Feign调用 addProChannelRelation 异常,msg={},e={}", e.getMessage(), e);

                        }
                  }

            } catch (Exception e) {
                  log.info("proSkuIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }


}