package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author XRZ
 * @date 2019/7/29
 * @Description :
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderCoupons implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 券类型的ID
     */
    private String couponsId;

    /**
     * 图片路径
     */
    private String img;

    /**
     * 描述
     */
    private String tatile;

    /**
     * 使用范围
     */
    private String usingRange;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 总计
     */
    private BigDecimal totalPrice;

    /**
     * 面值
     */
    private BigDecimal valueAmount;

    /**
     * 购买数量
     */
    private Integer count;

    /**
     * 有效时间
     */
    private String validTime;

    /**
     * 支付方式
     */
    private Integer payType;

    /**
     * 创建时间
     */
    protected Timestamp createdDate;

    /**
     * 创建人id
     */
    @JsonIgnore
    protected String createdBy;

    /**
     * 最后修改时间
     */
    @JsonIgnore
    protected Timestamp updatedDate;

    /**
     * 最后修改人id
     */
    @JsonIgnore
    protected String updatedBy;

    /**
     * 是否可用（可用=1，禁用=0）
     */
    @JsonIgnore
    protected Integer enable;
}























