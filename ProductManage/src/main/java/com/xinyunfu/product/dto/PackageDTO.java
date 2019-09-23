package com.xinyunfu.product.dto;

import java.math.BigDecimal;

public class PackageDTO {
    private Long proId;
    private String proName;
    private String imageUrl;
    private BigDecimal price;
    private Integer allSellStock;
    private Integer source; //1京东 其他自营
    private Integer allStock;
    private Integer killStatus;
}
