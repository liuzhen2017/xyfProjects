package com.xinyunfu.dto.jd.order;

import com.xinyunfu.dto.jd.goods.JDSkuDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDPreOrderDto {
      private long jdOrderId;       //京东订单号
      private BigDecimal freight;         //运费（合同有运费配置才会返回，否则不会返回该字段），单位元
      private BigDecimal orderPrice;      //订单价格
      private BigDecimal orderNakedPrice;       //订单裸价
      private BigDecimal orderTaxPrice;         //订单税额
      private JDSkuDto[] sku;       //商品信息
}
