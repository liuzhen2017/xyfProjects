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
 * 题库表
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
public class Subject extends Base {


    @TableId(value = "subject_id", type = IdType.AUTO)
    private Integer subjectId;

    /**
     * 题目类型
     */
    private String subjectType;

    /**
     * 题目
     */
    private String subjectTitle;

    /**
     * 选项 (以#拼接：A你#B我#C他#DA)
     */
    private String subjectOption;

    /**
     * 答案（A/B/C/D）
     */
    private String subjectAnswer;

}
