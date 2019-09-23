package com.xinyunfu.dto.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * 订单商品表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderCommodity extends Base {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 子订单编号
     */
    private String itemNo;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品sku_id
     */
    private Long skuId;

    /**
     * 商品sku说明
     */
    private String skuRemarks;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 出售单价
     */
    private BigDecimal price;

    /**
     * 市场价格
     */
    private BigDecimal marketPrice;

    /**
     * 运费
     */
    private BigDecimal freight;

    /**
     * 优惠金额
     */
    private BigDecimal discount;

    /**
     * 成本价格
     */
    private BigDecimal costPrice;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * 使用券的ID，以逗号拼接
     */
    private String coupons;

}
