package com.xinyunfu.controller;

import com.xinyunfu.dto.jd.param.JDAreaLimitParam;
import com.xinyunfu.dto.jd.param.JDPostParam;
import com.xinyunfu.dto.jd.param.JDSaleSkusParam;
import com.xinyunfu.dto.jd.param.JDStockParam;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.PreOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PreOrderController {

      @Autowired
      private PreOrderService preOrderService;

      /**
       * 查询邮费
       *
       * @param param
       * @return
       */
      @PostMapping("/preOrder/queryFreight")
      public ResponseInfo<String> queryFreight(@RequestBody JDPostParam param) {

            return preOrderService.queryFreight(param);
      }

      /**
       * 查询商品是否可售
       *
       * @param skuIds
       * @return
       */
      @PostMapping("/preOrder/checkSale")
      public ResponseInfo checkSale(@RequestBody JDSaleSkusParam skuIds) {

            return preOrderService.checkSale(skuIds);
      }


      /**
       * 查询区域购买是否被限制
       *
       * @param param
       * @return
       */
      @PostMapping("/preOrder/queryAreaLimit")
      public ResponseInfo queryAreaLimit(@RequestBody JDAreaLimitParam param) {
            log.info("====开始查询区域是否被限制,parmat ={}====",param);
            return preOrderService.queryAreaLimit(param);
      }

      /**
       * 查询商品是否有货
       *
       * @param param
       * @return
       */
      @PostMapping("/preOrder/queryStock")
      public ResponseInfo queryStock(@RequestBody JDStockParam param) {

            return preOrderService.queryStock(param);
      }
}
