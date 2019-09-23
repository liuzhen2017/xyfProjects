package com.xinyunfu.dto.jd.param;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDSkuParam {
      private long skuId;   //商品编号
      private int num;        //购买数量
      @Builder.Default
      private Boolean bNeedAnnex=true ;          //表示是否需要附件，默认每个订单都给附件，默认值为：true
      private Boolean bNeedGift;          //表示是否需要赠品，如赠品无货，可以给false，不影响主商品下单

}
