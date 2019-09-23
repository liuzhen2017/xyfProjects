package com.ruoyi.util;

import lombok.Data;

import java.util.List;

/**
 * @author XRZ
 * @date 2019/8/6
 * @Description :
 */
@Data
public class Res<T> {

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 每页条数
     */
    private Integer size;

    /**
     * 当前页数
     */
    private Integer current;

    /**
     * 总页数
     */
    private Integer pages;

    /**
     * 数据集合
     */
    private List<T> records;

}
