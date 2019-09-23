package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 时间限制表
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Time implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 时间类型（限制下单优惠券=0，限制下单时间=1）
     */
    private Integer type;

    /**
     * 有效时间（08:00_23:30）
     */
    private String effeciveTime;

    /**
     * 无效时间的提示语
     */
    private String tips;


}
