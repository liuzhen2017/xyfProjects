package com.xinyunfu.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDTO<T> {
    private Integer pageNo;
    private Integer pageSize;
    private Long totalCount;
    private List<T> dataInfo;

    public PageDTO(PageQueryDTO query){
        this.pageNo = query.getPageNo();
        this.pageSize = query.getPageSize();
    }

    public PageDTO(PageDTO page){
        this.pageNo = page.getPageNo();
        this.pageSize = page.getPageSize();
        this.totalCount = page.getTotalCount();
    }
}
