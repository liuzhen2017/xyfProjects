package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/7/20
 * @Description :
 */
@Data
public class OptionDTO implements Serializable {

    /**
     * 答题子表ID
     */
    private Long itemId;

    /**
     * 题目的ID
     */
    private Integer subjectId;

    /**
     * 选项
     */
    private String option;

    /**
     * 值
     */
    private String value;
}
