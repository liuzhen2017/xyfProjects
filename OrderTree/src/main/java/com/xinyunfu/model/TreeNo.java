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
public class TreeNo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 节点编号
     */
    private String nodeNo;

    /**
     * 左子节点编号
     */
    private String rightLeafNo;

    /**
     * 右子节点编号
     */
    private String leftLeafNo;

    /**
     * 父子节点编号
     */
    private String parentLeafNo;

    /**
     * 剩余可用节点数
     */
    private Integer validLeafCount;

    /**
     * 生成节点的时间戳
     */
    private String submitDate;

    /**
     * 节点是否有效
     */
    private Integer enables;


}
