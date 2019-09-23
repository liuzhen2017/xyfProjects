package com.ruoyi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author TYM
 * @date 2019/8/5
 * @Description :
 */
@Data
@Accessors(chain = true)
public class ProDto implements Serializable {
    private Long proId;
    private String proName;
    private String proTitle;
    private String proNo;
    private Integer sortNumber;
    private String unit;
    private Double weight;
    private String couponType;                //支持的券类型 0不支持   拼接券的id
    private String couponTypeName;            //券的名称
    private Long proPayTypeId;                //商品支付类型主键id
    private List<Integer> payType;
    private Long packageId;                   //套餐主键id
    private String tcName;
    private BigDecimal tcPrice;
    private List<String> propertyName;
    private List<String> propertyIds;               //属性主键id集合
    private List<String> propertyValue;
    //    private List<Long> valueIds;
    private List<String> valueIds;                  // 属性值主键id集合
    private Long proChannelRelationId;                          //商品分类关系主键id
    private String channelName;
    private Long channelId;
    private Long detailsId;                   //商品详情主键id
    private String specs;
    private String details;
    private List<String> imgIds;                //商品图片主键id
    private String imgUrl;
    private Integer proType;
    private String skuImgFiles;               //sku图片

    private String skuIds;                //sku主键id集合
    private List<String> pro;
    private List<BigDecimal> price;           //sku价格
    private List<BigDecimal> marketPrice;     //sku市场价
    private List<BigDecimal> costPrice;       //sku成本价
    private List<BigDecimal> minSellPrice;    //券后价(最低售价)
    private List<Integer> stock;              //sku库存
    private List<Integer> killStock;          //秒杀库存
    private List<BigDecimal> killPrice;       //秒杀价


}
