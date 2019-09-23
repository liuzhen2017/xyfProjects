package com.ruoyi.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author XRZ
 * @date 2019/8/23
 * @Description :
 */
@Data
public class BackOrderDTO implements Serializable {

    /**
     * 子订单id 自增
     */
    private Long itemId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 子订单编号
     */
    @Excel(name = "订单编号")
    private String itemNo;

    /**
     * 商家ID
     */
    @Excel(name = "商家ID")
    private Long storeId;

    /**
     * 商家名称
     */
    @Excel(name = "商家名称")
    private String storeName;
//
//    /**
//     * 商品总金额
//     */
//    @Excel(name = "商品总额")
//    private BigDecimal totalAmount = new BigDecimal(0);
//
//    /**
//     * 子订单实付金额
//     */
//    @Excel(name = "实付金额")
//    private BigDecimal payAmount = new BigDecimal(0);
//
    /**
     * 总运费
     */
    @Excel(name = "订单运费")
    private BigDecimal totalFreight = new BigDecimal(0);

    /**
     * 商品成本价格
     */
    @Excel(name = "商品价格")
    private BigDecimal costAmount = new BigDecimal(0);

    /**
     * 购买总数量
     */
    @Excel(name = "购买数量")
    private Integer totalCount = 0;

    /**
     * 订单状态（待付款=0,待发货=1，待签收=2，已签收=3，待点评=4，维权中=5，预留中=6,已退款=7，交易完成=8，已取消=9，异常订单=11）默认为0
     */
    private Integer status;

    /**
     * 商品id
     */
    @Excel(name = "商品ID")
    private Long goodsId;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String goodsName;

    /**
     * 下单时间
     */
    @Excel(name = "下单时间")
    private String createdDate;

    /**
     * 收货人名称
     */
    @Excel(name = "收货人")
    private String consigneeName;

    /**
     * 收货人电话号码
     */
    @Excel(name = "手机号码")
    private String phone;

    /**
     * 邮政编码
     */
    @Excel(name = "邮政编码")
    private String postCode;

    /**
     * 规格参数
     */
    @Excel(name = "规格参数")
    private String skuDesc;

    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    private String address;

    /**
     * 订单备注
     */
    @Excel(name = "订单备注")
    private String remarks;


}
