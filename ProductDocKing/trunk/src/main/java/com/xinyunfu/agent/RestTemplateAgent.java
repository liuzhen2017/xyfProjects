package com.xinyunfu.agent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.api.R;
import com.xinyunfu.config.JDGateWayConfig;
import com.xinyunfu.dto.jd.goods.*;
import com.xinyunfu.dto.jd.order.JDOrderDto;
import com.xinyunfu.dto.jd.order.JDPreOrderDto;
import com.xinyunfu.dto.jd.order.JDThirdOrderDto;
import com.xinyunfu.dto.jd.param.*;
import com.xinyunfu.dto.jd.shelf.*;
import com.xinyunfu.feign.ProductManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.ErrorMessage;
import com.xinyunfu.model.GoodsLog;
import com.xinyunfu.model.ResponseResult;
import com.xinyunfu.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RestTemplateAgent {

      @Autowired
      RestTemplate restTemplate;

      @Autowired
      JDGateWayConfig jdConfig;

      @Autowired
      private ProductManageFeign productManageFeign;

      /**
       * get请求 获取token
       */
      public String getToken(String url, String parame) {
            log.info("begin getToken ,url={},param ={}", url, parame);
            String token = null;
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", parame);
                  ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getToken result,result ={}", result);
                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        token = JSONObject.parseObject(result.getBody()).getString("token");
                  }
            } catch (Exception e) {
                  log.info("getToken 异常,msg={},e={}", e.getMessage(), e);
            }
            return token;
      }

      /**
       * post请求  生成序列号
       **/
      public Integer createSerialNum(String url, String parame) {
            log.info("begin createSerialNum ,url={},param ={}", url, parame);
            int serialNum = -1;
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", parame);
                  ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), String.class);
                  log.info("recv createSerialNum result,result ={}", result);
                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        serialNum = JSONObject.parseObject(result.getBody()).getInteger("sn");
                  }
            } catch (Exception e) {
                  log.info("createSerialNum 异常,msg={},e={}", e.getMessage(), e);
            }
            return serialNum;
      }


      /**
       * POST请求 添加1个上架位置
       */
      public boolean addShelf(String url, String headerParam, JDShelfParam shelfParam) {
            log.info("begin addShelf ,url={},headerParam ={}", url, headerParam);
            boolean flag = false;
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  HttpEntity<JDShelfParam> request = new HttpEntity<>(shelfParam, headers); //组装请求
                  ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                  //ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), String.class,request);
                  log.info("recv addShelf result,result ={}", response);

                  if (response.getStatusCode().equals(HttpStatus.OK)) {
                        flag = true;
                  }
            } catch (Exception e) {
                  log.info("addShelf 异常,msg={},e={}", e.getMessage(), e);
            }
            return flag;
      }


      /**
       * PATCH请求 修改指定id的上架位置
       */
      public boolean updateSP(String url, String headerParam, JDSPParam spParam) {
            log.info("begin updateSP ,url={},headerParam ={}", url, headerParam);
            boolean flag = false;
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  HttpEntity<JDSPParam> request = new HttpEntity<>(spParam, headers); //组装请求
                  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH, request, String.class);
                  //ResponseEntity<String> response = restTemplate.patchForObject(url, request, String.class);
                  log.info("recv updateSP result,result ={}", response);

                  if (response.getStatusCode().equals(HttpStatus.OK)) {
                        flag = true;
                  }
            } catch (Exception e) {
                  log.info("updateSP 异常,msg={},e={}", e.getMessage(), e);
            }
            return flag;
      }

      /**
       * 根据位置id获得上架位置
       */
      public JDShelfPositionDto getShelfPosition(String url, String headerParam) {
            log.info("begin getShelfPosition ,url={},headerParame ={}", url, headerParam);
            JDShelfPositionDto shelfPosition = new JDShelfPositionDto();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET,
                            new HttpEntity<>(headers), String.class);
                  log.info("recv getShelfPosition result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        shelfPosition = JSONObject.parseObject(result.getBody(), JDShelfPositionDto.class);
                  }
            } catch (Exception e) {
                  log.info("getShelfPosition 异常,msg={},e={}", e.getMessage(), e);
            }
            return shelfPosition;
      }


      /**
       * 根据父id获得上架位置
       */
      public List<JDShelfPositionDto> getSPList(String url, String headerParam) {
            log.info("begin getSPList ,url={},headerParame ={}", url, headerParam);
            List<JDShelfPositionDto> shelfPositionList = new ArrayList<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET,
                            new HttpEntity<>(headers), String.class);
                  log.info("recv getSPList result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getBody());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        shelfPositionList = JSONObject.parseArray(jsonStr, JDShelfPositionDto.class);
                  }
            } catch (Exception e) {
                  log.info("getSPList 异常,msg={},e={}", e.getMessage(), e);
            }
            return shelfPositionList;
      }


      /**
       * DELETE请求 删除上架位置
       */
      public boolean deleteSP(String url, String headerParam) {
            log.info("begin getSPList ,url={},headerParame ={}", url, headerParam);
            boolean flag = false;
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.DELETE,
                            new HttpEntity<>(headers), String.class);
                  log.info("recv getSPList result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        flag = true;
                  }
            } catch (Exception e) {
                  log.info("deleteSP 异常,msg={},e={}", e.getMessage(), e);
            }
            return flag;
      }

      /**
       * POST请求 商户商品上架
       */
      public boolean shelfGoods(String url, String headerParam, List<ShelfGoodsParam> shelfGoodsParam) {
            log.info("begin shelfGoods ,url={},headerParam ={},shelfGoodsParam={}", url, headerParam, shelfGoodsParam);
            boolean flag = false;
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  String str = JSON.toJSONString(shelfGoodsParam); // List转json
                  log.info("list参数转为json字符串，str={}", str);
                  HttpEntity<String> request = new HttpEntity<>(str, headers); //组装请求
                  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
                  //ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
                  //ResponseEntity<String> response = restTemplate.postForEntity(url, new HttpEntity<>(shelfGoodsParam, header), String.class);
                  log.info("recv shelfGoods result,result ={}", response);

                  if (response.getStatusCode().equals(HttpStatus.OK)) {
                        flag = true;
                  }
            } catch (Exception e) {
                  log.info("shelfGoods 异常,msg={},e={}", e.getMessage(), e);
            }
            return flag;
      }


      /**
       * DELETE请求 商户商品下架
       */
      public boolean deleteSG(String url, String headerParam, String skuIds) {
            log.info("begin getSPList ,url={},headerParame ={}", url, headerParam);
            boolean flag = false;
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.DELETE,
                            new HttpEntity<>(headers), String.class, skuIds);
                  log.info("recv getSPList result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        flag = true;
                  }
            } catch (Exception e) {
                  log.info("deleteSG 异常,msg={},e={}", e.getMessage(), e);
            }
            return flag;
      }


      /**
       * 分页查询上架商品列表
       */
      public List<JDShlefGoodsDto> getSGList(String url, String headerParam) {
            log.info("begin getShlefGoodslList ,url={},param ={}", url, headerParam);
            List<JDShlefGoodsDto> shlefGoodsList = new ArrayList<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getShlefGoodslList result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getBody());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        shlefGoodsList = JSONObject.parseArray(jsonStr, JDShlefGoodsDto.class);
                  }
            } catch (Exception e) {
                  log.info("getSGList 异常,msg={},e={}", e.getMessage(), e);
            }
            return shlefGoodsList;
      }

      /**
       * 根据商品编号串获取上架商品列表
       */

      public List<JDShlefGoodsDto> getShelfGoodsList(String url, String headerParam) {
            log.info("begin getShlefGoodslList ,url={},param ={}", url, headerParam);
            List<JDShlefGoodsDto> shlefGoodsList = new ArrayList<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getShlefGoodslList result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getBody());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        shlefGoodsList = JSONObject.parseArray(jsonStr, JDShlefGoodsDto.class);
                  }
            } catch (Exception e) {
                  log.info("getShelfGoodsList 异常,msg={},e={}", e.getMessage(), e);
            }
            return shlefGoodsList;
      }

      /**
       * 分页查询上架商品列表，并获取总数
       */
      public JDSGWrapperDto getSGWrapperDto(String url, String headerParam) {
            log.info("begin getSGWrapperDto ,url={},param ={}", url, headerParam);
            JDSGWrapperDto sgWrapperDto = new JDSGWrapperDto();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getSGWrapperDto result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONObject json = JSONObject.parseObject(result.getBody());
                        JSONArray jsonArray = json.getJSONArray("data");
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        List<JDShlefGoodsDto> shlefGoodsList = JSONObject.parseArray(jsonStr, JDShlefGoodsDto.class);
                        sgWrapperDto.setTotal(json.getInteger("total"));
                        sgWrapperDto.setData(shlefGoodsList);
                  }
            } catch (Exception e) {
                  log.info("getSGWrapperDto 异常,msg={},e={}", e.getMessage(), e);
            }
            return sgWrapperDto;
      }

      /**
       * 上架商品的状态
       */
      public List<JDSGStatusDto> getSGStatus(String url, String headerParam) {
            log.info("begin getSGStatus ,url={},param ={}", url, headerParam);
            List<JDSGStatusDto> jdsgStatusDtoList = new ArrayList<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getSGStatus result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getBody());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        jdsgStatusDtoList = JSONObject.parseArray(jsonStr, JDSGStatusDto.class);
                  }
            } catch (Exception e) {
                  log.info("getSGStatus 异常,msg={},e={}", e.getMessage(), e);
            }
            return jdsgStatusDtoList;
      }

      /**
       * 上架商品详情
       */
      public JDShlefGoodsDto getSGDetail(String url, String headerParam) {
            log.info("begin getSGDetail ,url={},param ={}", url, headerParam);
            JDShlefGoodsDto shlefGoodsDto = new JDShlefGoodsDto();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getSGDetail result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        log.info("返回结果 result={}", result.getBody());
                        shlefGoodsDto = JSONObject.parseObject(result.getBody(), JDShlefGoodsDto.class);

                  }
            } catch (Exception e) {
                  log.info("getSGDetail 异常,msg={},e={}", e.getMessage(), e);
            }
            return shlefGoodsDto;
      }

      /**
       * get请求  获取商品池列表
       */
      public List<JDGoodsPoolDto> getGoodsPoolList(String url, String parame) {
            log.info("begin getGoodsPoolList ,url={},param ={}", url, parame);
            List<JDGoodsPoolDto> goodsPoolList = new ArrayList<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", parame);
                  // RestTemplate中使用ParameterizedTypeReference参数化类型，设置返回类型为 List<JDGoodsPoolDto>
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getGoodsPoolList result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getBody());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        goodsPoolList = JSONObject.parseArray(jsonStr, JDGoodsPoolDto.class);
                  }
            } catch (Exception e) {
                  log.info("getGoodsPoolList 异常,msg={},e={}", e.getMessage(), e);
            }
            return goodsPoolList;
      }

      /**
       * 获取所有商品列表
       */
      public JDGoodsListDto getGoodsList(String url, String headerParame) {
            log.info("begin getGoodsList ,url={},headerParame ={}", url, headerParame);
            JDGoodsListDto goodsListDto = new JDGoodsListDto();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParame);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getGoodsList result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONObject jsonObject = JSONObject.parseObject(result.getBody());
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        String jsonStr = JSONObject.toJSONString(dataArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        List<JDGoodsDto> goodsList = JSONObject.parseArray(jsonStr, JDGoodsDto.class);
                        goodsListDto.setTotal(jsonObject.getInteger("total"));
                        goodsListDto.setData(goodsList);
                  }
            } catch (Exception e) {
                  //TODO 将异常的skuId入库，供重试调用
                  log.info("异常url，url={}", url);
                  log.info("getGoodsList 异常,msg={},e={}", e.getMessage(), e);
            }
            return goodsListDto;
      }

      /**
       * 获取商品详情
       */
      public JDGoodsDetailDto getGoodsDetail(String url, String headerParame) {
            log.info("begin getGoodsDetail ,url={},headerParame ={}", url, headerParame);
            JDGoodsDetailDto goodsDetailDto = new JDGoodsDetailDto();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParame);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getGoodsDetail result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        goodsDetailDto = JSONObject.parseObject(result.getBody(), JDGoodsDetailDto.class);
                  }
            } catch (Exception e) {
                  log.info("getGoodsDetail 异常,msg={},e={}", e.getMessage(), e);
            }
            return goodsDetailDto;
      }


      /**
       * 获取kpl商品列表状态
       */
      public ResponseInfo<List<JDGoodsState>> getGoodsState(String url, String headerParame) {
            log.info("begin getGoodsState ,url={},headerParame ={}", url, headerParame);


            try {
                  ResponseInfo<String> result = HttpClientUtil.doGet(url,null,headerParame);
                  if (Integer.valueOf(result.getCode()).equals(HttpStatus.OK.value())) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getData());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        List<JDGoodsState> goodsStateList  = JSONObject.parseArray(jsonStr, JDGoodsState.class);
                        return ResponseInfo.success(goodsStateList);
                  }else{
                        JSONObject resultJson = JSONObject.parseObject(result.getData());
                        String msg = resultJson.getString("Message");
                        return new ResponseInfo("jd555",msg,null);
                  }
            } catch (Exception e) {
                  log.info("getPrice 异常,msg={},e={}", e.getMessage(), e);
                  return ResponseInfo.error(null);
            }
      }

      /**
       * 获取商品pc端/移动端样式
       */
      public JDGoodsStyleDto getGoodsStyle(String url, String headerParam) {
            log.info("begin getGoodsStyle ,url={},headerParame ={}", url, headerParam);
            JDGoodsStyleDto goodsStyleDto = new JDGoodsStyleDto();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);

                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getGoodsStyle result,result ={}", result);
                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        goodsStyleDto = JSONObject.parseObject(result.getBody(), JDGoodsStyleDto.class);
                  }

            } catch (Exception e) {
                  log.info("getGoodsStyle 异常,msg={},e={}", e.getMessage(), e);
            }

            return goodsStyleDto;
      }

      /**
       * 获取商品图片
       */
      public Map<String, List<JDImage>> getGoodsImage(String url, String headerParame, String proNo) {
            log.info("begin getGoodsImage ,url={},headerParame ={},proNo ={}", url, headerParame, proNo);
            Map<String, List<JDImage>> imageMap = new HashMap<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParame);

                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getGoodsImage result,result ={}", result);
                  if (result.getStatusCode().equals(HttpStatus.OK)) {

                        JSONObject json = JSONObject.parseObject(result.getBody());
                        String[] sku = proNo.split(",");

                        if (sku != null && sku.length > 0) {
                              for (int i = 0; i < sku.length; i++) {
                                    String skuid = sku[i];
                                    JSONArray jsonArray = json.getJSONArray(skuid);
                                    //将array数组转换成字符串，解析后的字符串将值 为null的属性过滤了，导致转为对象时匹配不了。需要使用SerializerFeature保留null值key。
                                    String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                                    //把字符串转换成集合
                                    List<JDImage> imageList = JSONObject.parseArray(jsonStr, JDImage.class);
                                    imageMap.put(skuid, imageList);
                              }
                        }

                  }
            } catch (Exception e) {
                  log.info("getGoodsImage 异常,msg={},e={}", e.getMessage(), e);
            }
            return imageMap;
      }

      /**
       * 获取运费
       */
      public JDFreightDto getFreight(String url, String headerParame, String json) {
            log.info("begin getFreight ,url={},headerParame ={},json ={}", url, headerParame, json);

            JDFreightDto freightDto = new JDFreightDto();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParame);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class, json);
                  log.info("recv getFreight result,result ={}", result);
                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        freightDto = JSONObject.parseObject(result.getBody(), JDFreightDto.class);
                  }
            } catch (Exception e) {
                  log.info("getFreight 异常,msg={},e={}", e.getMessage(), e);
            }
            return freightDto;
      }

      /**
       * 获取价格
       */
      public ResponseInfo getPrice(String url, String headerParame) {
            log.info("begin getPrice ,url={},headerParame ={}", url, headerParame);
            try {
                  ResponseInfo<String> result = HttpClientUtil.doGet(url,null,headerParame);
                  if (Integer.valueOf(result.getCode()).equals(HttpStatus.OK.value())) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getData());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        List<JDPriceDto> priceDtoList = JSONObject.parseArray(jsonStr, JDPriceDto.class);
                        return ResponseInfo.success(priceDtoList);
                  }else{
                        JSONObject resultJson = JSONObject.parseObject(result.getData());
                        String msg = resultJson.getString("Message");
                        return new ResponseInfo("jd555",msg,null);
                  }
            } catch (Exception e) {
                  log.info("getPrice 异常,msg={},e={}", e.getMessage(), e);
                  return ResponseInfo.error("系统异常");
            }
      }

      /**
       * 查询分类信息
       */
      public JDCategoryDto getCategory(String url, String headerParame) {
            log.info("begin getCategory ,url={},headerParame ={}", url, headerParame);
            JDCategoryDto categoryDto = new JDCategoryDto();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParame);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getCategory result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        categoryDto = JSONObject.parseObject(result.getBody(), JDCategoryDto.class);
                  }
            } catch (Exception e) {
                  log.info("getCategory 异常,msg={},e={}", e.getMessage(), e);
            }
            return categoryDto;
      }

      /**
       * 查询同类商品
       */
      public List<JDSimilarGoodsDto> getSimilarGoods(String url, String headerParame) {
            log.info("begin getSimilarGoods ,url={},headerParame ={}", url, headerParame);
            List<JDSimilarGoodsDto> similarGoodsDto = new ArrayList<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParame);
                  ResponseEntity<String> result = restTemplate.exchange(
                            url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                  log.info("recv getSimilarGoods result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getBody());
                        //组装数据
                        for (int i = 0; i < jsonArray.size(); i++) {

                              JSONObject jo = jsonArray.getJSONObject(i);
                              JSONArray saleAttrList = jo.getJSONArray("saleAttrList");
                              String jsonStr = JSONObject.toJSONString(saleAttrList, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                              List<JDGoodsSaleAttr> saleList = JSONObject.parseArray(jsonStr, JDGoodsSaleAttr.class);
                              JDSimilarGoodsDto similarGoods = new JDSimilarGoodsDto();
                              similarGoods.setSaleAttrList(saleList);
                              similarGoods.setDim(jo.getInteger("dim"));
                              similarGoods.setSaleName(jo.getString("saleName"));
                              similarGoodsDto.add(similarGoods);
                        }
                  }
            } catch (Exception e) {
                  log.info("getSimilarGoods 异常,msg={},e={}", e.getMessage(), e);
            }
            return similarGoodsDto;
      }

      /**
       * 批量获取库存方式1
       */
      public List<JDStockDto> batchQueryStock(String url, String headerParam, String json) {
            log.info("begin getStock ,url={},headerParame ={},json={}", url, headerParam, json);
            List<JDStockDto> stockDtoList = new ArrayList<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(url,
                            HttpMethod.GET, new HttpEntity<>(headers), String.class, json);
                  log.info("recv getStock result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getBody());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        stockDtoList = JSONObject.parseArray(jsonStr, JDStockDto.class);
                  }
            } catch (Exception e) {
                  log.info("batchQueryStock 异常,msg={},e={}", e.getMessage(), e);
            }
            return stockDtoList;
      }

      /**
       * 批量获取库存方式2
       */
      public List<JDBatchStockDto> getBatchStock(String url, String headerParam) {
            log.info("begin getBatchStock ,url={},headerParame ={}", url, headerParam);
            List<JDBatchStockDto> stockDtoList = new ArrayList<>();
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.GET,
                            new HttpEntity<>(headers), String.class);
                  log.info("recv getBatchStock result,result ={}", result);

                  if (result.getStatusCode().equals(HttpStatus.OK)) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getBody());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        stockDtoList = JSONObject.parseArray(jsonStr, JDBatchStockDto.class);
                  }
            } catch (Exception e) {
                  log.info("getBatchStock 异常,msg={},e={}", e.getMessage(), e);
            }
            return stockDtoList;
      }

      /**
       * 查询订单
       */
      public ResponseInfo<List<JDOrderDto>> getOrder(String url, String headerParam) {
            log.info("begin getOrder ,url={},headerParame ={}", url, headerParam);
            try {
                  ResponseInfo<String> result = HttpClientUtil.doGet(url,null,headerParam);
                  if (Integer.valueOf(result.getCode()).equals(HttpStatus.OK.value())) {

                        JSONArray jsonArray = JSONObject.parseArray(result.getData());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        List<JDOrderDto> orderDtoList = JSONObject.parseArray(jsonStr, JDOrderDto.class);
                        return ResponseInfo.success(orderDtoList);
                  }else{
                        JSONObject resultJson = JSONObject.parseObject(result.getData());
                        String msg = resultJson.getString("Message");
                        return new ResponseInfo("5555",msg,null);
                  }
            } catch (Exception e) {
                  log.info("getPrice 异常,msg={},e={}", e.getMessage(), e);
                  return ResponseInfo.error(null);
            }
      }

      /**
       * 反查订单
       */
      public ResponseInfo<JDThirdOrderDto> getOrderId(String url, String headerParam) {
            log.info("begin getOrderId ,url={},headerParame ={}", url, headerParam);
            try {
                  ResponseInfo<String> result = HttpClientUtil.doGet(url,null,headerParam);
                  if (Integer.valueOf(result.getCode()).equals(HttpStatus.OK.value())) {

                        JDThirdOrderDto thirdOrderDto = JSONObject.parseObject(result.getData(),JDThirdOrderDto.class);
                        return ResponseInfo.success(thirdOrderDto);
                  }else{
                        JSONObject resultJson = JSONObject.parseObject(result.getData());
                        String msg = resultJson.getString("Message");
                        return new ResponseInfo("5555",msg,null);
                  }
            } catch (Exception e) {
                  log.info("getOrderId 异常,msg={},e={}", e.getMessage(), e);
                  return ResponseInfo.error(null);
            }
      }

      /**
       * POST 请求，预下单
       */
      public ResponseInfo<String>  preOrder(String url, String headerParam, JDPreOrderParam preOrderParam) {
            log.info("begin preOrder ,url={},headerParame={},preOrderParam={}", url, headerParam, preOrderParam);
            try {
                  String json = JSON.toJSONString(preOrderParam);
                  long startTime = System.currentTimeMillis();
                  ResponseInfo<String> res = HttpClientUtil.doPostJson(url, json, headerParam);
                  log.info("=====请求结果={}========",res);
                  long endTime = System.currentTimeMillis();
                  long time = endTime - startTime;
                  log.info(" 预下单 运行时间 time = {}ms", time);
                  if (res != null) {
                        if (res.getCode().equals("200")) {
                              return ResponseInfo.success(Long.toString(preOrderParam.getThirdOrder()));
                        } else {
                              JSONObject resultJson = JSONObject.parseObject(res.getData());
                              String msg = resultJson.getString("Message");
                              if (msg.contains("乡镇不存在")) {
                                    return new ResponseInfo("5555", "您的收货地址有误，请核对后重新下单", null);

                              } else {
                                    return new ResponseInfo("5555", msg, null);
                              }
                        }
                  }
            } catch (Exception e) {
                  log.error("preOrder 异常e={}", e);
            }
            return ResponseInfo.errorReturn("系统繁忙，请稍候");
      }

      /**
       * 确认订单
       */
      public boolean ensureOrder(String url, String headerParam) {
            log.info("begin ensureOrder ,url={},headerParame={}", url, headerParam);
            boolean flag = false;
            try {
                  HttpHeaders headers = new HttpHeaders();
                  headers.setContentType(MediaType.APPLICATION_JSON);
                  // 设置header参数
                  headers.set("Authorization", headerParam);
                  //HttpEntity<Long> request = new HttpEntity<>(order_no, headers); //组装请求
                  ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PATCH,
                            new HttpEntity<>(headers), String.class);
                  log.info("recv ensureOrder result,result ={}", response);

                  if (response.getStatusCode().equals(HttpStatus.OK)) {
                        flag = true;
                  }
            } catch (Exception e) {
                  log.info("ensureOrder 异常,msg={},e={}", e.getMessage(), e);
            }
            return flag;
      }


      /**
       * 查询商品是否可售
       *
       * @param url
       * @param headerParam
       * @return
       */
      public ResponseInfo checkGoods(String url, String headerParam) {
            log.info("begin checkGoods ,url={},headerParame ={}", url, headerParam);

            //Boolean flag = false;
            try {
                  ResponseInfo<String> result = HttpClientUtil.doGet(url,null,headerParam);
                  if (Integer.valueOf(result.getCode()).equals(HttpStatus.OK.value())) {
                        JSONArray jsonArray = JSONObject.parseArray(result.getData());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        List<JDCheckDto> goodsCheck = JSONObject.parseArray(jsonStr, JDCheckDto.class);
                        for(JDCheckDto item : goodsCheck){
                              if(item.getSaleState() ==0){
                                    return new ResponseInfo("0000","该商品暂时不可售",false);
                              }else{
                                    if(item.getIsCanVAT() ==0 || item.getIs7ToReturn()==0){
                                          String proNo = "Jd"+item.getSkuId().toString();
                                          //根据skuNo查询ProSku
                                          productManageFeign.offSaleByproNo(proNo);
                                          return new ResponseInfo("0000","该商品暂时不可售",false);
                                    }
                              }
                        }
                        return ResponseInfo.success(true);
                  }else{
                        JSONObject resultJson = JSONObject.parseObject(result.getData());
                        String msg = resultJson.getString("Message");
                        return new ResponseInfo("0000",msg,false);
                  }
            } catch (Exception e) {
                  log.info("getPrice 异常,msg={},e={}", e.getMessage(), e);
                  return ResponseInfo.error("系统异常");
            }
      }


      /**
       * 查询区域是否限制
       *
       * @param url
       * @param headerParam
       * @return
       */
      public ResponseInfo getAreaLimit(String url, String headerParam) {
            log.info("begin getAreaLimit ,url={},headerParame ={}", url, headerParam);

            try {
                  ResponseInfo<String> result = HttpClientUtil.doGet(url,null,headerParam);
                  if (Integer.valueOf(result.getCode()).equals(HttpStatus.OK.value())) {

                        JSONArray jsonArray = JSONObject.parseArray(result.getData());
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        List<JDAreaLimitDto> areaLimit = JSONObject.parseArray(jsonStr, JDAreaLimitDto.class);
                        for(JDAreaLimitDto item : areaLimit){
                              if(item.isAreaRestrict()){
                                    return new ResponseInfo("0000","该区域暂时无法购买",false);
                              }
                        }
                        return ResponseInfo.success(true);
                  }else{
                        JSONObject resultJson = JSONObject.parseObject(result.getData());
                        String msg = resultJson.getString("Message");
                        return new ResponseInfo("0000",msg,false);
                  }
            } catch (Exception e) {
                  log.info("getPrice 异常,msg={},e={}", e.getMessage(), e);
                  return ResponseInfo.error("系统异常");
            }

      }


      /**
       * 获取物流信息
       *
       * @param url
       * @param headerParam
       * @return
       */
      public ResponseInfo<JDLogisticsDto> getOrderTrack(String url, String headerParam) {
            log.info("begin getOrderTrack ,url={},headerParame ={}", url, headerParam);
            JDLogisticsDto logisticsDto = new JDLogisticsDto();
            try {
                  ResponseInfo<String> result = HttpClientUtil.doGet(url,null,headerParam);
                  if (Integer.valueOf(result.getCode()).equals(HttpStatus.OK.value())) {
                        JSONObject json = JSONObject.parseObject(result.getData());
                        String str = json.getString("orderTrack");
                        Long jdOrderId = Long.valueOf(json.getString("jdOrderId"));
                        JSONArray jsonArray = JSONObject.parseArray(str);
                        String jsonStr = JSONObject.toJSONString(jsonArray, SerializerFeature.WRITE_MAP_NULL_FEATURES);
                        List<JDOrderTrackDto> orderTrcakList = JSONObject.parseArray(jsonStr, JDOrderTrackDto.class);
                        logisticsDto.setOrderTrack(orderTrcakList);
                        logisticsDto.setJdOrderId(jdOrderId);
                        return ResponseInfo.success(logisticsDto);
                  }else{
                        JSONObject resultJson = JSONObject.parseObject(result.getData());
                        String msg = resultJson.getString("Message");
                        return new ResponseInfo("5555",msg,null);
                  }
            } catch (Exception e) {
                  log.info("getPrice 异常,msg={},e={}", e.getMessage(), e);
                  return ResponseInfo.error(null);
            }
      }
}