package com.xinyunfu.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 售后订单表
 * </p>
 *
 * @author Xurongze
 * @since 2019-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AfterSalesOrder extends Base  {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private String itemNo;

    /**
     * 申请类型（0=未收货，申请退款）（1=已收货，申请售后）
     */
    private Integer applyType;

    /**
     * 售后类型（0=换货）（1=退货退款）（2=退款）
     */
    private Integer salesType;

    /**
     * 货物状态（0=收到货）（1=已收到货）
     */
    private Integer goodsStatus;

    /**
     * 原因类型（0=怕错/多拍/不喜欢）（1=商品损坏）（2=商品描述不符）（3=颜色/款式/图案与描述不符）（4=卖家发错货）（5=其他）
     */
    private Integer whyType;

    /**
     * 退款金额
     */
    private BigDecimal money;

    /**
     * 售后说明
     */
    private String instructions;

    /**
     * 凭证图片url，多张以分号拼接(;)
     */
    private String imgUrl;

    /**
     * 处理状态（0=待处理，1=商家处理中，2=退款成功，3=换货成功，4=退货退款成功，5=退款失败，6=退款关闭，7=退货退款失败，8=换货失败）
     */
    private Integer state;
}
