package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDStockDto {
      private long skuId;     //商品编号
      private String areaId;        //配送地址ID,以“_”隔开
      private int stockStateId;     //库存状态编号 33,39,40,36,34
      private String stockStateDesc;      //33 有货 现货-下单立即发货,39 有货 在途-正在内部配货，预计2 ~6天到达本仓库,40 有货 可配货-下单后从有货仓库配货,36 预订, 34 无货
      private int remainNum;        //剩余数量，返回真实剩余数量， -1代表未知：（数量大于50返回都是-1）
}
