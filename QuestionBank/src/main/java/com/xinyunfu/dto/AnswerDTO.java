package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/11
 * @Description : 返回我的答题 记录 DTO
 */
@Data
public class AnswerDTO implements Serializable {

    /**
     * 答题子表ID
     */
    private Long itemId;


    /**
     * 答题主表的ID
     */
    private Long answerId;

    /**
     * 该题目的序号
     */
    private Integer itemNumber;

    /**
     * 答题状态（未答=0，正确=1）
     */
    private Integer Status;

    /**
     * 题目的ID
     */
    private Integer subjectId;

    /**
     * 题目的类型
     */
    private String type;

    /**
     * 题目的标题
     */
    private String title;

    /**
     * 题目的选项
     */
    private List<OptionDTO> option = new ArrayList<>();

    /**
     * 答案
     */
    private String answer;

}
