package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/7/20
 * @Description :
 */
@Data
public class CommDTO implements Serializable {

    /**
     * 子答题表的ID
     */
    private Long itemId;

    /**
     * 当前页数
     */
    private Integer page = 1;


    /**
     * 题目的类型
     */
    private Integer type;
}
