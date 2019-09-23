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
public class UserPackageOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private String userNo;

    /**
     * 主订单号：推广大使会有多个
     */
    private String mainOrderNo;

    /**
     * 订单数量
     */
    private Integer count;

    /**
     * 剩余有效数量
     */
    private Integer vaildCount;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 数据是否启用，1启用，0是禁用
     */
    private Integer enables;

    /**
     * 是否提交转让，0已提交，1未提交
     */
    private Integer isSubmit;


}
