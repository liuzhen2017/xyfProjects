package com.xinyunfu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
* @author liuzhen
* 每笔转账的转入转出记录 实体
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletAccFlow implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 订单编号 （主订单编号）
     */
    private String orderNo;

    /**
     * 账号
     */
    private String accountNo;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 流水类型，in转入，out转出
     */
    private String flowType;

    /**
     * 流水来源，商品/套餐购买 product, 用户转账 transfer
     */
    private String flowSource;

    /**
     * 流水金额
     */
    private BigDecimal amount;

    /**
     * 流水状态,wait等待支付，success支付成功，failed支付失败
     */
    private String status;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     *最后修改时间
     */
    private Timestamp lastModifyTime;

    /**
     *  流水描述
     */
    private String flowDesc;

    /**
     * 展示时间
     */
    @TableField(exist = false)
    private String showDate;


    public WalletAccFlow(String orderNo, String accountNo, String accountType, String flowType, String flowSource, BigDecimal amount, String status, String flowDesc) {
        this.orderNo = orderNo;
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.flowType = flowType;
        this.flowSource = flowSource;
        this.amount = amount;
        this.status = status;
        this.flowDesc = flowDesc;
    }
}