package com.xinyunfu.product.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PageVODTO implements Serializable {
    private Integer page;
    private Integer pageSize;
    private String queryParam;
}
