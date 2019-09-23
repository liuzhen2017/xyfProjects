package com.ruoyi.xinyunfu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 订单子表 order_item
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
public class OrderItem extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 子订单id 自增 */
	private Long itemId;
	/** 用户ID  */
	private String userId;
	/** 子订单编号 */
	private String itemNo;
	/** 主订单编号 */
	private String masterNo;
	/** 商家ID */
	private Long storeId;
	/** 商家名称 */
	private String storeName;
	/** 子订单应付金额 */
	private BigDecimal amount;
	/** 商品总计 */
	private BigDecimal totalAmount;
	/** 子订单实付金额 */
	private BigDecimal payAmount;
	/** 总运费 */
	private BigDecimal totalFreight;
	/** 手续费（预留字段） */
	private BigDecimal charge;
	/** 总优惠金额 */
	private BigDecimal totalDiscount;
	/** 购买总数量 */
	private Integer totalCount;
	/** 订单状态（待付款=0,待发货=1，待签收=2，已签收=3，待点评=4，维权中=5，预留中=6,已退款=7，交易完成=8，已取消=9）默认为0 */
	private Integer status;
	/** 快递公司名称 */
	private String shippingCompName;
	/** 快递单号 */
	private String shippingSn;
	/** 发货时间(时间戳) */
	private Date deliveryTime;
	/** 订单来源 (自营=0，供应商=1)(保留字段) */
	private Integer orderSource;
	/** 支付时间 */
	private String payTime;
	/** 支付方式 （微信=16，支付宝=32，快捷支付=64，网银支付=128） */
	private Integer payType;
	/** 支付交易号 */
	private String payNumber;
	/** 订单状态（0待支付/1支付成功/2支付失败/3交易关闭） */
	private Integer payStatus;
	/** 备注 */
	private String remarks;
	/** 创建时间 */
	private Date createdTime;
	/** 创建人id */
	private String createdBy;
	/** 最后修改时间 */
	private Date updatedTime;
	/** 最后修改人id */
	private String updatedBy;
	/** 是否可用（可用=1，禁用=0） */
	private Integer enable;

	public void setItemId(Long itemId) 
	{
		this.itemId = itemId;
	}

	public Long getItemId() 
	{
		return itemId;
	}
	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getUserId() 
	{
		return userId;
	}
	public void setItemNo(String itemNo) 
	{
		this.itemNo = itemNo;
	}

	public String getItemNo() 
	{
		return itemNo;
	}
	public void setMasterNo(String masterNo) 
	{
		this.masterNo = masterNo;
	}

	public String getMasterNo() 
	{
		return masterNo;
	}
	public void setStoreId(Long storeId) 
	{
		this.storeId = storeId;
	}

	public Long getStoreId() 
	{
		return storeId;
	}
	public void setStoreName(String storeName) 
	{
		this.storeName = storeName;
	}

	public String getStoreName() 
	{
		return storeName;
	}
	public void setAmount(BigDecimal amount) 
	{
		this.amount = amount;
	}

	public BigDecimal getAmount() 
	{
		return amount;
	}
	public void setTotalAmount(BigDecimal totalAmount) 
	{
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalAmount() 
	{
		return totalAmount;
	}
	public void setPayAmount(BigDecimal payAmount) 
	{
		this.payAmount = payAmount;
	}

	public BigDecimal getPayAmount() 
	{
		return payAmount;
	}
	public void setTotalFreight(BigDecimal totalFreight) 
	{
		this.totalFreight = totalFreight;
	}

	public BigDecimal getTotalFreight() 
	{
		return totalFreight;
	}
	public void setCharge(BigDecimal charge) 
	{
		this.charge = charge;
	}

	public BigDecimal getCharge() 
	{
		return charge;
	}
	public void setTotalDiscount(BigDecimal totalDiscount) 
	{
		this.totalDiscount = totalDiscount;
	}

	public BigDecimal getTotalDiscount() 
	{
		return totalDiscount;
	}
	public void setTotalCount(Integer totalCount) 
	{
		this.totalCount = totalCount;
	}

	public Integer getTotalCount() 
	{
		return totalCount;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setShippingCompName(String shippingCompName) 
	{
		this.shippingCompName = shippingCompName;
	}

	public String getShippingCompName() 
	{
		return shippingCompName;
	}
	public void setShippingSn(String shippingSn) 
	{
		this.shippingSn = shippingSn;
	}

	public String getShippingSn() 
	{
		return shippingSn;
	}
	public void setDeliveryTime(Date deliveryTime) 
	{
		this.deliveryTime = deliveryTime;
	}

	public Date getDeliveryTime() 
	{
		return deliveryTime;
	}
	public void setOrderSource(Integer orderSource) 
	{
		this.orderSource = orderSource;
	}

	public Integer getOrderSource() 
	{
		return orderSource;
	}
	public void setPayTime(String payTime) 
	{
		this.payTime = payTime;
	}

	public String getPayTime() 
	{
		return payTime;
	}
	public void setPayType(Integer payType) 
	{
		this.payType = payType;
	}

	public Integer getPayType() 
	{
		return payType;
	}
	public void setPayNumber(String payNumber) 
	{
		this.payNumber = payNumber;
	}

	public String getPayNumber() 
	{
		return payNumber;
	}
	public void setPayStatus(Integer payStatus) 
	{
		this.payStatus = payStatus;
	}

	public Integer getPayStatus() 
	{
		return payStatus;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}

	public String getRemarks() 
	{
		return remarks;
	}
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	public Date getCreatedTime()
	{
		return createdTime;
	}
	public void setCreatedBy(String createdBy) 
	{
		this.createdBy = createdBy;
	}

	public String getCreatedBy() 
	{
		return createdBy;
	}
	public void setUpdatedTime(Date updatedTime)
	{
		this.updatedTime = updatedTime;
	}

	public Date getUpdatedTime()
	{
		return updatedTime;
	}
	public void setUpdatedBy(String updatedBy) 
	{
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy() 
	{
		return updatedBy;
	}
	public void setEnable(Integer enable) 
	{
		this.enable = enable;
	}

	public Integer getEnable() 
	{
		return enable;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemId", getItemId())
            .append("userId", getUserId())
            .append("itemNo", getItemNo())
            .append("masterNo", getMasterNo())
            .append("storeId", getStoreId())
            .append("storeName", getStoreName())
            .append("amount", getAmount())
            .append("totalAmount", getTotalAmount())
            .append("payAmount", getPayAmount())
            .append("totalFreight", getTotalFreight())
            .append("charge", getCharge())
            .append("totalDiscount", getTotalDiscount())
            .append("totalCount", getTotalCount())
            .append("status", getStatus())
            .append("shippingCompName", getShippingCompName())
            .append("shippingSn", getShippingSn())
            .append("deliveryTime", getDeliveryTime())
            .append("orderSource", getOrderSource())
            .append("payTime", getPayTime())
            .append("payType", getPayType())
            .append("payNumber", getPayNumber())
            .append("payStatus", getPayStatus())
            .append("remarks", getRemarks())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("enable", getEnable())
            .toString();
    }
}
