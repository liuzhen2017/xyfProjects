package com.xinyunfu.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageQueryInnerDTO<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Long totalCount;
    private List<T> list;

    public PageQueryInnerDTO(PageQueryInnerDTO page){
        this.pageNo = page.getPageNo();
        this.pageSize = page.getPageSize();
        this.totalCount = page.getTotalCount();
    }
}
