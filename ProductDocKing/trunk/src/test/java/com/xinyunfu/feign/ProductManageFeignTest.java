package com.xinyunfu.feign;

import com.alibaba.fastjson.JSON;
import com.xinyunfu.commons.goods.*;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductManageFeignTest {

      @Autowired
      private ProductManageFeign productManageFeign;

      //固定邮费，从yml中取
      @Value("${postage}")
      private BigDecimal postage;

      @Test
      public void checkProByProNo() {
            String proNo = "JD326154";
            ResponseInfo<Boolean> result = productManageFeign.checkProByProNo(proNo);
            System.out.println(result.getData());
      }

      @Test
      public void queryProductByProNo() {
            String proNo = "JD326154";
            //Product product = Product.builder().proNo(proNo).proName("测试数据007").status(99).source(1).build();

            ResponseInfo<Product> dbPro = productManageFeign.queryProductByProNo(proNo);

            System.out.println("查询的商品信息" + dbPro.toString());

      }

      @Test
      public void saveProduct() {
            String proNo = "JD326154";
            Product product = Product.builder().proNo(proNo).proName("测试数据007").status(99).source(1).build();
            String result = productManageFeign.save(product).getMessage();
            System.out.println("商品入库:   " + result + "  商品id:    " + product.getProId());
      }


      /**
       * product入库
       */
      @Test
      public void productIntoDb() {
            try {
                  String proNo = "JD326154";
                  Product product = Product.builder().proNo(proNo).proName("测试数据007").status(99).source(1).build();
                  ResponseInfo<Boolean> response = productManageFeign.checkProByProNo(proNo);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        String result = productManageFeign.save(product).getData();
                        log.info("product入库信息：result={}", result);
                  }

            } catch (Exception e) {
                  log.info("productIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }


      /**
       * proProperty 入库
       */
      @Test
      public void proPropertyIntoDb() {

            try {
                  long proId = 1l;
                  String propertyName = "ha";
                  ProProperty proProperty = ProProperty.builder().
                            proId(proId).propertyName(propertyName).build();
                  ResponseInfo<Boolean> responseInfo = productManageFeign.checkPropertyByProIdAndName(proId, propertyName);
                  if (!responseInfo.getData()) {
                        ResponseInfo<String> result = productManageFeign.addProperty(proProperty);
                        //String result = productManageFeign.add(proProperty).getMessage();
                        log.info("proProperty入库信息：result={}", result.getData());
                  }

            } catch (Exception e) {
                  log.info("proPropertyIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }


      /**
       * proPropertyValue 入库
       */
      @Test
      public void proPropertyValueIntoDb() {

            try {
                  long propertyId = 1L;
                  String valueText = "灰色";
                  ProPropertyValue proPropertyValue = ProPropertyValue.builder().propertyId(
                            propertyId).valueText(valueText).build();
                  ResponseInfo<Boolean> response = productManageFeign.checkByPropertyIdAndValueText(propertyId, valueText);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        ResponseInfo<String> result = productManageFeign.add(proPropertyValue);
                        log.info("proPropertyValue入库信息：result={}", result.getData());
                  }

            } catch (Exception e) {
                  log.info("proPropertyValueIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }


      /**
       * 在property和propertyValue入库后，才能组装groupNo，再更新proSku并入库
       */
      @Test
      public void wrapperSku() {

            //Long proId = 0L;
            //1.先获取所有的来源为JD，状态为99待审核的 product，拿到proId
            ResponseInfo<List<Long>> response = productManageFeign.getJDProductIds(1, 99);

            List<Long> productList = response.getData();
            if (CollectionUtils.isNotEmpty(productList)) {
                  for (Long proId : productList) {
                        log.info("商品信息，product={}", proId.toString());
                  }
            }
            //1.根据proId查询property，再根据property查询propertyValue


            //2.拿到值后拼接


      }


      /**
       * proSku入库
       */
      //@Test
      //public void proSkuIntoDb() {
      //      try {
      //            Long proId = 1L;
      //            ProSku proSku = ProSku.builder().skuNo("JDtest258").proId(proId).imgUrl("阿").build();
      //            ResponseInfo<Boolean> response = productManageFeign.checkSkuByProId(proId);
      //            //已存在不做操作，不存在则insert
      //            if (!response.getData()) {
      //                  ResponseInfo<String> result = productManageFeign.addSku(proSku);
      //                  log.info("proSku 入库信息：result={}", result.getData());
      //            }
      //
      //      } catch (Exception e) {
      //            log.info("proSkuIntoDb 异常,msg={},e={}", e.getMessage(), e);
      //
      //      }
      //
      //}

      /**
       * proImage入库
       */
      @Test
      public void proImageIntoDb() {
            try {
                  Long proId = 5L;
                  List<ProImage> ImageList = new ArrayList<>();
                  ProImage proImage1 = ProImage.builder().proId(proId).imgUrl("hala").isDefault(1).build();
                  ProImage proImage2 = ProImage.builder().proId(proId).imgUrl("madeli").isDefault(0).build();

                  ImageList.add(proImage1);
                  ImageList.add(proImage2);


                  boolean sign = productManageFeign.checkProImageByProId(proId).getData();
                  //已存在不做操作，不存在则insert
                  if (!sign) {
                        ResponseInfo<String> result = productManageFeign.saveProImages(ImageList);
                        log.info("proImage入库信息：result={}", result.getData());
                  }

            } catch (Exception e) {
                  log.info("proImageIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }


      /**
       * 计算sku 最低价、总价并入库
       */
      @Test
      public void calculatePrice() {

            BigDecimal price = new BigDecimal("100");
            BigDecimal costPrice = new BigDecimal("50");
            //原价
            BigDecimal marketPrice = price.multiply(new BigDecimal(1.3)).setScale(2, BigDecimal.ROUND_HALF_UP);

            BigDecimal temp = price.multiply(new BigDecimal(0.27));

            //最低价计算规则：1.17*costPrice(协议价) - 0.27*price(出售价) +6(邮费)
            BigDecimal minSellPrice = costPrice.multiply(new BigDecimal(1.17)).subtract(temp).add(postage).setScale(2, BigDecimal.ROUND_HALF_UP);

            System.out.println("期望原价 130.00，计算价格："+marketPrice);
            System.out.println("期望最低价 37.50，计算价格："+minSellPrice);
      }



      /**
       * ProDetails入库
       */
      @Test
      public void proDetailsIntoDb() {
            try {
                  Long proId = 2L;
                  ProDetails proDetails = ProDetails.builder().proId(proId).details("world").specs("cool").build();

                  ResponseInfo<Boolean> response = productManageFeign.checkProDetailsByProId(proId);
                  //已存在不做操作，不存在则insert
                  if (!response.getData()) {
                        ResponseInfo<String> result = productManageFeign.addProdetails(proDetails);
                        log.info("proDetails入库信息：result={}", result.getData());
                  }

            } catch (Exception e) {
                  log.info("proDetailsIntoDb 异常,msg={},e={}", e.getMessage(), e);

            }

      }

      @Test
      public void getJDprodct(){

            try {
                  ResponseInfo<List<Long>> response = productManageFeign.getJDProductIds(1, 99);
                  List<Long> productIds = response.getData();

            }catch (Exception e){
                  log.info("getJDprodct 异常,msg={},e={}", e.getMessage(), e);

            }

      }


      @Test
      public void getProperty(){
            try {
                  ResponseInfo<ProProperty> resProperty = productManageFeign.queryPropertyByProIdAndName(1L, "颜色");
                  ProProperty dbProperty = resProperty.getData();
            }catch (Exception e){
                  log.info("getProperty 异常,msg={},e={}", e.getMessage(), e);

            }

      }


      @Test
      public void getProIdByProNo(){
            try {
                  ResponseInfo<Long> response = productManageFeign.getProIdByProNo("JD5255229");

                  Long proId = response.getData();
            }catch (Exception e){
                  log.info("getProIdByProNo 异常,msg={},e={}", e.getMessage(), e);

            }
      }


      @Test
      public void updateSkuByProNo(){

            String proNo = "JD3398565";
            ResponseInfo<String> response = productManageFeign.updateSkuByProNo(proNo);
      }


      @Test
      public  void syncAddSku(){

            ProSku proSku = ProSku.builder().imgUrl("htttp://test").skuNo("测试111").groupNo("1:1").costPrice(new BigDecimal(111)).minSellPrice(new BigDecimal(110)).build();

            String json =  JSON.toJSONString(proSku);
            ResponseInfo<String> response = productManageFeign.addSku(json);
      }


}