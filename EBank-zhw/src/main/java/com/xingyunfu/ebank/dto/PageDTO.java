package com.xingyunfu.ebank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Long totalCount;
    private List<T> list;
}
