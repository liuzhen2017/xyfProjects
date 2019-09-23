package com.xinyunfu.feign;


import com.xinyunfu.commons.goods.*;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ProductManage", decode404 = true)
public interface ProductManageFeign {

      /**
       * 根据商品编号查询商品信息
       */
      @GetMapping("/product/queryProductByProNo")
      ResponseInfo<Product> queryProductByProNo(@RequestParam("proNo") String proNo);

      /**
       * 根据商品编号查询商品id
       * @param proNo
       * @return
       */
      @GetMapping("/product/getProIdByProNo")
      ResponseInfo<Long> getProIdByProNo(@RequestParam("proNo") String proNo);

      /**
       * 根据商品编号查询商品是否已存在
       */
      @GetMapping("/product/checkProByProNo")
      ResponseInfo<Boolean> checkProByProNo(@RequestParam("proNo") String proNo);


      /**
       * 新增商品
       */
      @PostMapping("/product/saveProduct")
      ResponseInfo<String> save(@RequestBody Product product);

      /**
       * 更新商品
       */
      @PostMapping("/product/update")
      ResponseInfo<String> updateProduct(@RequestBody Product product);


      /**
       * 根据来源、状态查询商品
       * 查询所有来源为京东,状态为99（待审核）的商品id
       *
       * @return
       */
      @GetMapping("/product/getJDProductIds")
      ResponseInfo<List<Long>> getJDProductIds(@RequestParam("resource") int source, @RequestParam("status") int status);



      /**
       * 根据商品id查询商品详情是否已存在
       *
       * @param proId
       * @return
       */
      @GetMapping("/proDetails/checkProDetailsByProId")
      ResponseInfo<Boolean> checkProDetailsByProId(@RequestParam("proId") Long proId);

      /**
       * 新增商品详情
       */
      @PostMapping("/proDetails/add")
      ResponseInfo<String> addProdetails(@RequestBody ProDetails proDetails);


      /**
       * proImages入库
       */
      @PostMapping("/proImage/saveProImages")
      ResponseInfo<String> saveProImages(@RequestBody List<ProImage> list);


      /**
       * 根据商品id查询图片是否已存在
       */
      @GetMapping("/proImage/checkProImageByProId")
      ResponseInfo<Boolean> checkProImageByProId(@RequestParam("proId") Long proId);

      /**
       * 根据商品id和属性名查询属性对象
       */
      @GetMapping("/property/queryPropertyByProIdAndName")
      ResponseInfo<ProProperty> queryPropertyByProIdAndName(
                @RequestParam("proId") Long proId, @RequestParam("propertyName") String propertyName);

      /**
       * 根据商品id和属性名查询属性对象是否已存在
       */
      @GetMapping("/property/checkPropertyByProIdAndName")
      ResponseInfo<Boolean> checkPropertyByProIdAndName(@RequestParam("proId") long proId, @RequestParam("propertyName") String propertyName);


      /**
       * 新增property
       */
      @PostMapping("/property/addProperty")
      ResponseInfo<String> addProperty(@RequestBody ProProperty proProperty);



      /**
       * 根据商品id查询商品属性
       * @param proId
       * @return
       */
      @GetMapping("/property/getAttrListByProId")
      ResponseInfo<List<ProProperty>> getAttrListByProId(@RequestParam("proId") Long proId);


      /**
       * 根据属性id和属性值查询属性值对象是否存在
       */
      @GetMapping("/propertyValue/checkByPropertyIdAndValueText")
      ResponseInfo<Boolean> checkByPropertyIdAndValueText(
                @RequestParam("propertyId") long propertyId, @RequestParam("valueText") String valueText);


      /**
       * 新增属性值
       */
      @PostMapping("/propertyValue/add")
      ResponseInfo<String> add(@RequestBody ProPropertyValue proPropertyValue);

      /**
       * 根据属性id查询属性值对象
       *
       * @param propertyId
       * @return
       */
      @GetMapping("/propertyValue/getPropertyValueByPropertyId")
      ResponseInfo<List<ProPropertyValue>> getPropertyValueByPropertyId(@RequestParam("propertyId") Long propertyId);


      /**
       * 根据商品id查询sku是否存在
       *
       * @return
       */
      @GetMapping("/sku/checkSkuByProId")
      ResponseInfo<Boolean> checkSkuByProId(@RequestParam("proId") Long proId);


      /**
       * 新增sku
       */
      @PostMapping("/sku/synsAddSku")
      ResponseInfo<String> addSku(@RequestBody String json);

      /**
       * 更新sku
       */
      @PostMapping("/sku/updateSku")
      ResponseInfo<String> updateSku(@RequestBody ProSku proSku);

      /**
       * 根据proId查询sku，当前设计为1对1
       */
      @GetMapping("/sku/getSkuByProId")
      ResponseInfo<ProSku> getSkuByProId(@RequestParam("proId") long proId);


      /**
       * 根据proId和channelId查询当前关系是否存在
       */
      @GetMapping("/channelRelation/checkByPidAndCid")
      ResponseInfo<Boolean> checkByPidAndCid(@RequestParam("proId") Long proId, @RequestParam("channelId") Integer channelId);


      /**
       *新增商品分类关系
       */
      @PostMapping("/channelRelation/add")
      ResponseInfo<String> add(ProChannelRelation proChannelRelation);


      /**
       * 批量添加分类
       *
       * @param
       * @return
       */
      @PostMapping("/channel/batchSave")
      ResponseInfo<String> batchSaveProChannel(@RequestBody List<ProChannel> proChannels);


      /**
       * 根据skuId查询skuNo
       */
      @GetMapping("/sku/getSkuNo")
      ResponseInfo<String> getSkuNoBySkuId(@RequestParam("skuId") Long skuId);

}
