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

/**
 * <p>
 * 订单价格清单表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderPrices extends Base {

    /**
     * 主键id
     */
    @TableId(value = "price_id", type = IdType.AUTO)
    private Long priceId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 主订单编号
     */
    private String masterNo;

    /**
     * 子订单编号
     */
    private String itemNo;

    /**
     * sku规格参数
     */
    private Long skuId;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 价格类型（商品=0，优惠劵=1，邮费=2）
     */
    private Integer priceType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 备注
     */
    private String remarks;

}
