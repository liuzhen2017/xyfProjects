package com.ruoyi.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author XRZ
 * @date 2019/8/3
 * @Description : 发票信息DTO
 */
@Data
public class InvoiceDTO implements Serializable {

    /**
     * 发票类型（电子发票=0，纸只发票=1）
     */
    private Integer type;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 纳税人识别号
     */
    private String taxpayerNumber;

    /**
     * 子订单应付金额
     */
    private BigDecimal amount;

    /**
     * 开票金额 （实付金额）
     */
    private BigDecimal payAmount;

    /**
     * 子订单编号
     */
    private String itemNo;

    /**
     * 订单状态（待付款=0,待发货=1，待签收=2，已签收=3，待点评=4，维权中=5，预留中=6,已退款=7，交易完成=8，已取消=9，待回调=10，异常订单=11）默认为0
     */
    private Integer status;

    /**
     * 发票ID
     */
    private String invoiceId;

    /**
     * 发票状态（未开=0，审核中=1，已开=2）
     */
    private Integer invoiceIdStatus;
    //订单ID
    private Long orderInvoiceId;
}
