package com.xinyunfu.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/7/15
 * @Description :
 */
@Data
public class OrderDTO implements Serializable {

    /**
     * 订单状态 (全部 = -1，待付款 = 0，待发货 = 1，待收货 = 2，待点评 = 4 )
     */
    private Integer status = -1;

    /**
     * 当前页数
     */
    private Integer page = 1;

    /**
     * 总页数
     */
    private Integer pageSize = 10;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 搜索关键字
     */
    private String key;

    /**
     * 取消的类型
     */
    private Integer type;

    /**
     * 订单状态（待付款=0,待发货=1，待签收=2，已签收=3，待点评=4，维权中=5，预留中=6,已退款=7，交易完成=8，已取消=9）默认为0
     */
    private Integer updataStatus;



}
