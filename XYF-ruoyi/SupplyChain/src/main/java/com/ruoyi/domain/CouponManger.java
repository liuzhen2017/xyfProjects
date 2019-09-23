package com.ruoyi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.math.BigDecimal;

/**
 * 优惠券表 coupon_manger
 * 
 * @author ruoyi
 * @date 2019-08-04
 */
@Data
public class CouponManger extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/**  */
	private Long id;
	/** 优惠券类型->对应商品类型 */
	private Integer couponType;
	/**  */
	private String couponTypeName;
	/** 使用规则,比如注册regist,推荐recommend */
	private String couponNode;
	/** 标题 */
	private String title;
	/** 描述 */
	private String describes;
	/** 图片地址 */
	private String picUrl;
	/** 使用规则 */
	private String rules;
	/** 价值 */
	private BigDecimal valueAmount;
	/** 有效日期 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String effectiveTime;
	/** 失效日期 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private String invalidTime;
	/** 总数 */
	private Integer totalNum;
	/** 可用数量 */
	private Integer canNum;
	/** 已经使用 */
	private Integer usedNum;
	/** 00未使用,01已经使用,02,已经过期,03.系统禁用 */
	private String couponStatus;
	/** 创建时间 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdDate;
	/**  */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updatedDate;
	/** 是否可以购买 */
	private String isCanBuy;
	/** 售价 */
	private BigDecimal sellPrice;
	/** 商品类型 */
	private String productType;

	/** 商品类型 */
	private String productTypeName;

}
