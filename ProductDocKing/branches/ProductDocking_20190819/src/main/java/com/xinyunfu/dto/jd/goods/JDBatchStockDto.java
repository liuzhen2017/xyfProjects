package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDBatchStockDto {
      private long sku;       //商品编号
      private String area;    //地址，以“_”隔开
      private String desc;    //库存状态描述
      private String state;   //库存状态编号 33,39,40,36;有货 现货-下单立即发货;有货 在途-正在内部配货，预计2 ~6天到达本仓库 40 有货 可配货-下单后从有货仓库配货;预订
}
