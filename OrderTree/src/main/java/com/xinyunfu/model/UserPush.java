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
public class UserPush extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id，唯一，用户管理模块指定
     */
    private String userNo;

    /**
     * 推荐关系链，存一个推荐关系数组
     */
    private String pusherNoLinked;

    /**
     * 用户权限级别，普通vip
     */
    private String userType;


}
