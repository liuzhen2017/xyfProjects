package com.xinyunfu.product.dto;

import lombok.Data;

/**
 * @author TYM
 * @date 2019/8/22
 * @Description :
 */
@Data
public class PageByProNameDTO {
    /**
     * 当前页数
     */
    private Integer page;
    /**
     * 每页个数
     */
    private Integer pageSize;
    /**
     * 查询参数
     */
    private String queryParam;
    /**
     * 分类id
     */
    private Long channelId;
    /**
     * 排序字段
     */
    private String orderByCloum;
    /**
     * 是否升序  0,升序  1,降序
     */
    private Integer isAsc;


}
