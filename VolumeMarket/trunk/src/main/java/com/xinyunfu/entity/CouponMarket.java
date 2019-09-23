package com.xinyunfu.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author liuzhen
* couponMarket 实体
*/
@Data
@NoArgsConstructor
public class CouponMarket{
	@Id
	private Long id;
    private Long couponId;
    private BigDecimal amount;
    private String orderNo;
    private String userNo;
    private String couponStatus;
    @JsonFormat(pattern="YYYY-MM-dd",timezone = "GMT+8")
    private Date createdDate;
    private String createdBy;
    @JsonFormat(pattern="YYYY-MM-dd",timezone = "GMT+8")
    private Date updatedDate;
    private String updatedBy;
    @JsonFormat(pattern="YYYY-MM-dd",timezone = "GMT+8")
    private Date useDate;
    @JsonFormat(pattern="YYYY-MM-dd",timezone = "GMT+8")
    private Date actiDate;
    private BigDecimal signPrice;
    @JsonIgnore 
    private Date signDate;
    private String title;
    private String rules;
    @JsonFormat(pattern="YYYY-MM-dd",timezone = "GMT+8")
    private Date dueDate;
    private Integer number;
    private Integer userType;
    private BigDecimal totalPrice;
    private Integer isVaild;
    private BigDecimal serviceCharge;
    private BigDecimal rewardAmount;
    private String rewardType;
    private String consumerOrderNo;
    private BigDecimal valueAmount;
    private String ranges;
}