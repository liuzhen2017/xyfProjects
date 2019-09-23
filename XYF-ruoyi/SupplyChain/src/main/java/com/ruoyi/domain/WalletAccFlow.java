package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 每笔转账的转入转出记录表 wallet_acc_flow
 *
 * @author ruoyi
 * @date 2019-08-20
 */
@Data
public class WalletAccFlow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    @Excel(name = "流水编号")
    private String id;
    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderNo;
    /** 账号，系统生成 */
    @Excel(name = "账户编号")
    private String accountNo;
    /** 账户类型 */
    @Excel(name = "账户类型")
    private String accountType;
    /** 流水类型，in转入，out转出 */
    @Excel(name = "流水类型")
    private String flowType;
    /** 流水来源，商品/套餐购买 product, 用户转账 transfer */
    @Excel(name = "流水来源")
    private String flowSource;
    /** 流水金额 */
    @Excel(name = "流水金额")
    private BigDecimal amount;
    /** 流水状态,wait等待支付，success支付成功，failed支付失败 */
    @Excel(name = "流水状态")
    private String status;
    /** 创建时间 */
    @Excel(name = "创建时间")
    private String createTime;
    /** 最后修改时间 */
    private String lastModifyTime;
    /**  */
    @Excel(name = "流水备注")
    private String flowDesc;

    private String showDate;


}
