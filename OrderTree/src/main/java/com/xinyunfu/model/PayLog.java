package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xinyunfu.jace.entites.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jace
 * @since 2019-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PayLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 提现人的编号
     */
    private String userNo;

    /**
     * 订单树自定义订单号，节点编号_第几次提现
     */
    private String payNo;

    /**
     * 返回结果
     */
    private String resultJson;

    /**
     * 主订单编号
     */
    private String mainOrderNo;

    /**
     * 是否提交转让，推广大使默认是提交
     */
    private Integer isSubmit;

    /**
     * 收益来源人-即购买套餐人
     */
    private String sourceUser;


}
