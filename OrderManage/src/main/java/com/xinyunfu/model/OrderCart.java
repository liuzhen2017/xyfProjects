package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 购物车表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderCart extends Base{

    /**
     * 购物车id
     */
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Long cartId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品单价。
     */
    private BigDecimal goodsPrice;

    /**
     * SKU说明。
     */
    private String skuDescription;

    /**
     * 商家编号
     */
    private Long storeId;

    /**
     * 商家名称
     */
    private String storeName;

    /**
     * 商品sku
     */
    private Long skuId;

    /**
     * 购物车商品数量
     */
    private Integer buyCount;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 发货类型（自营=0，供应商=1）
     */
    private Integer shipmentType;

    /**
     * 商品类型（普通商品=0，秒杀商品=1，套餐商品=2
     */
    private Integer goodsType;

    /**
     * 商品来源(0=其他,1=京东,2=怡亚通)
     */
    private Integer source;

}
