package com.xinyunfu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 答题记录表
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
public class AnswerRecord extends Base {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户的ID
     */
    private String userId;

    /**
     * 百科常识答题进度（1-200）
     */
    private Integer lifeCount;

    /**
     * 法律知识的答题进度（1-200）
     */
    private Integer lawCount;

    /**
     * 题目的ID  百科常识;基本法律( 多个ID使用逗号拼接)
     */
    private String subjectIds;
}
