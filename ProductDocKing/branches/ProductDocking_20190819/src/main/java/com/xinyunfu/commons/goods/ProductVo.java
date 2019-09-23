package com.xinyunfu.commons.goods;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Product product;
	private ProDetails proDetails;
	private List<ProImage> imageList;
	private BigDecimal postage;
	private Integer allSellStock;
	private BigDecimal price;
}
