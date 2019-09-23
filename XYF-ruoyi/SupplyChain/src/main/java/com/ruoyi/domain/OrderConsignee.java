package com.ruoyi.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单收货表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderConsignee extends BaseEntity {

    /**
     * 主键id
     */
    @TableId(value = "consignee_id", type = IdType.AUTO)
    private Long consigneeId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String region;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private Integer postCode;

    /**
     * 收货人名称
     */
    private String consigneeName;

    /**
     * 收货人电话号码
     */
    private String phone;

//    /**
//     * 备注
//     */
//    private String remarks;

    /**
     * 省编号_市编号_区编号_0
     */
    private String codeStr;

}
