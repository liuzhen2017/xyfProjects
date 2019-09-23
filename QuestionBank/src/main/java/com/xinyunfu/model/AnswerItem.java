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
 * 答题子表
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
public class AnswerItem extends Base {

    @TableId(value = "item_id", type = IdType.AUTO)
    private Long itemId;

    /**
     * 用户的ID
     */
    private String userId;

    /**
     * 答题主表的ID
     */
    private Long answerId;

    /**
     * 题目的序号
     */
    private Integer itemNumber;

    /**
     * 题目ID
     */
    private Integer subjectId;


    /**
     * 答题状态（未答=0，正确=1）
     */
    private Integer itemStatus;



    public AnswerItem(String userId, Long answerId, Integer itemNumber, Integer subjectId, String createdBy, String updatedBy) {
        this.userId = userId;
        this.answerId = answerId;
        this.itemNumber = itemNumber;
        this.subjectId = subjectId;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
