package com.xinyunfu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/30
 * @Description :
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO implements Serializable {

    /**
     * 题目的集合
     */
    private List<AnswerDTO> list;

    /**
     * 总条数
     */
    private Integer total = 200;

    /**
     * 每页大小
     */
    private Integer size = 20;

    /**
     * 当前页数
     */
    private Integer current;

    /**
     * 总页数
     */
    private Integer pages = 10;

    public PageDTO(List<AnswerDTO> list, Integer current) {
        this.list = list;
        this.current = current;
    }
}
