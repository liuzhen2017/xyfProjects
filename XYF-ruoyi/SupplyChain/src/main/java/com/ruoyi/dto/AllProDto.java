package com.ruoyi.dto;

import com.ruoyi.domain.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author TYM
 * @date 2019/8/26
 * @Description :
 */
@Data
public class AllProDto implements Serializable {
    /**
     * 商品基本信息
     */
    private Product product;
    /**
     * 支付方式
     */
    private ProPayType proPayType;
    /**
     * 拼接支付方式id
     */
    private String payType;
    private String payTypeStr;
    /**
     * 商品分类关系
     */
    private ProChannelRelation proChannelRelation;
    /**
     * 套餐
     */
    private ProPackage proPackage;
    /**
     * 商品详情
     */
    private ProDetails proDetails;
    /**
     * 商品图片
     */
    private List<ProImage> proImages;
    private String proImagesStr;
    /**
     * 商品属性
     */
    private List<ProProperty> proProperties;
    private String proPropertiesStr;
    /**
     * 商品属性值
     */
    private List<List<ProPropertyValue>> propertyValues;
    private String propertyValuesStr;
    /**
     * 商品sku
     */
    private List<String> proSkuJsons;


}
