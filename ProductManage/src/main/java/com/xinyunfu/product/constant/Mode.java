package com.xinyunfu.product.constant;

/**
 * @author TYM
 * @date 2019/9/3
 * @Description :分类排序移动规则
 */
public interface Mode {
    /**
     * 置顶
     */
    Integer TOP=0;
    /**
     * 前移一位
     */
    Integer LAST=1;
    /**
     * 后移一位
     */
    Integer NEXT=2;
    /**
     * 置底
     */
    Integer BOTTOM=3;

}
