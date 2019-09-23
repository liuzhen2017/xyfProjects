package com.ruoyi.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XRZ
 * @date 2019/8/23
 * @Description : 供应商后台 搜索的条件
 */
@Data
public class SelectOrderDTO implements Serializable {

    /**
     * 商家ID
     */
    private String storeId;

    /**
     * 订单编号
     */
    private String itemNo;

    /**
     * 订单状态
     */
    private String status;

    /**
     * 收货人
     */
    private String consigneeName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 商品名字
     */
    private String goodsName;

    /**
     * 订单来源
     */
    private String orderSource;

    /**
     * 下单时间 开始时间
     */
    private String startDate;

    /**
     * 下单时间 结束时间
     */
    private String endDate;

    /**
     * 订单编号，- 拼接 web端使用
     */
    private String itemNos;

}
