package com.xinyunfu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "jd-gateway")
@Data
public class JDGateWayConfig {

      private String baseUrl;

      private String appKey;

      private String appSecret;

      private String getTokenUrl;

      private String serialNumUrl;

      private String goodsPoolUrl;

      private String goodsListUrl;

      private String goodsDetailUrl;

      private String imageUrl;

      private String imagePrefixUrl;

      private String freightUrl;

      private String priceUrl;

      private String stockUrl;

      private String batchStockUrl;

      private String orderUrl;

      private String categoryUrl;

      private String similarGoodsUrl;

      private String shelfUrl;

      private String shelfGoodsUrl;

      private String shelfGoodsStateUrl;        //上架商品状态

      private String goodsStyleUrl;

      private String goodsStateUrl;       //kpl商品上下架状态

      private String goodsCheckUrl;      //查询商品时候可售

      private String areaLimitUrl;       //区域购买限制

      private String logisticsUrl;        //订单跟踪

}
