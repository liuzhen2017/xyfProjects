package com.ruoyi.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageQueryInnerDTO<T> {
    private Integer pageNo;
    private Long userNo;
    private Long startTime;
    private Long endTime;
    private String userCode;
    private String phone;
    private Integer sex;
    private Integer pageSize;
    private Long totalCount;
    private Integer level;
    private List<T> list;
}
