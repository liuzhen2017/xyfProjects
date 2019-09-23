package com.xinyunfu.customer.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PageQueryDTO {

    @NotNull@Min(0)@Max(20)
    private Integer pageNo;
    @NotNull@Min(3)
    private Integer pageSize;
}
