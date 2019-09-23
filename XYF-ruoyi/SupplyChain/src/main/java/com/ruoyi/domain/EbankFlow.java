package com.ruoyi.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 每笔转账的转入转出记录，不包括合并转账表 ebank_flow
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
@Data
public class EbankFlow extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Integer id;
	/** ebank支付订单编号 */
	private String ebankOrderNo;
	/** 支付中心订单号 */
	private String sysOrderNo;
	/** 账号，系统生成 */
	private String accountNo;
	/** 账户类型 */
	private String accountType;
	/** 流水类型，in转入，out转出 */
	private String flowType;
	/** 流水来源，商品/套餐购买 product, 用户转账 transfer */
	private String flowSource;
	/** 流水金额 */
	private BigDecimal amount;
	/** 流水状态,wait等待支付，success支付成功，failed支付失败 */
	private String status;
	/** 创建时间 */
	private String createTime;
	/** 最后修改时间 */
	private Date lastModifyTime;


    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ebankOrderNo", getEbankOrderNo())
            .append("sysOrderNo", getSysOrderNo())
            .append("accountNo", getAccountNo())
            .append("accountType", getAccountType())
            .append("flowType", getFlowType())
            .append("flowSource", getFlowSource())
            .append("amount", getAmount())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("lastModifyTime", getLastModifyTime())
            .toString();
    }
}
