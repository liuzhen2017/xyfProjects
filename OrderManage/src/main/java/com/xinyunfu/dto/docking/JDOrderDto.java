package com.xinyunfu.dto.docking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDOrderDto implements Serializable {

      private int pOrder;     //父订单号
      private int orderState;       //订单状态 0为取消订单 1为有效
      private long jdOrderId;       //京东订单号
      private BigDecimal freight;         //运费
      private int state;            //物流状态 0 是新建 1是妥投 2是拒收
      private int submitState;            //0为未确认下单订单
      private BigDecimal orderPrice;            //订单价格
      private BigDecimal orderNakedPrice;       //订单裸价
      private int type;             //订单类型 1是父订单 2是子订单
      private BigDecimal orderTaxPrice;         //订单税费
      private JDSkuDto[] sku;       //商品信息
}
