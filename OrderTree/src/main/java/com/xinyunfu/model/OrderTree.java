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
public class OrderTree extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主订单编号
     */
    @TableId(type=IdType.INPUT)
    private String mainOrderNo;

    /**
     * 订单树节点
     */
    private String nodeNo;

    /**
     * 当前节点的用户编号
     */
    private String userNo;

    /**
     * 节点是否仍有效，1有效，0无效
     */
    private Integer enables;


}
