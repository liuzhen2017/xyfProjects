package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 答题主表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class AnswerMaster extends Base {

    @TableId(value = "answer_id", type = IdType.AUTO)
    private Long answerId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 购买套餐的订单号
     */
    private Long orderNo;

    /**
     * 万能券的总数
     */
    private Integer count = 0;

    /**
     * 万能券激活数量
     */
    private Integer couponCount = 0;

    /**
     * 总答题量
     */
    private Integer totalCount = 0;

}
