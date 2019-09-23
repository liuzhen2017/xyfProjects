package com.xinyunfu.controller;

import com.xinyunfu.dto.jd.ProductDto;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.ProductDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/productDto/")
@Slf4j
public class ProductDtoController {
      @Autowired
      private ProductDtoService productDtoService;

      /**
       * 多数参数都可以从 proSku中获取
       *
       * @param proId     proId
       * @param price     出售价格：proSku中的 price
       * @param costPirce 协议价：proSku中的 costPirce
       * @param skuId     jd skuId，对应 proSku中的 skuNo
       * @param shelfId   上架位置，需要指定
       * @return
       */
      @GetMapping("query.do")
      public ResponseInfo getProductDto(@RequestParam("proId") Long proId,
                                        @RequestParam("price") BigDecimal price, @RequestParam("costPirce") BigDecimal costPirce,
                                        @RequestParam("skuId") String skuId, @RequestParam("shelfId") String shelfId) {

            return productDtoService.getProductDto(proId, price, costPirce, skuId, shelfId);
      }
}