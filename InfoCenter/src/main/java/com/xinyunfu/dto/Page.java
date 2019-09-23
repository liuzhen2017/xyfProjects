package com.xinyunfu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalCount;
    private List<T> data;
}
