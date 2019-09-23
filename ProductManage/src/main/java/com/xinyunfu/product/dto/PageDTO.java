package com.xinyunfu.product.dto;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageDTO<T> {

	private int current_page;   //当前页
	private int size;           //每页数量
	private int total_page;     //总页数
	private int total_sum;      //总条数
	private List<T> currentPageData;
}
