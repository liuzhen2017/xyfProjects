package com.xinyunfu.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author liuzhen
* 优惠券管理 实体
*/
@Data
@NoArgsConstructor
public class CouponManger implements Serializable{
    @TableId(value="ID")
	private String id;
    private Integer couponType;
    private String couponNode;
    private String title;
    private String describes;
    private String picUrl;
    private String rules;
    private BigDecimal valueAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date effectiveTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date invalidTime;
    private Integer totalNum;
    private Integer canNum;
    private Integer usedNum;
    private String couponStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;
    @JsonFormat
    private String createdBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedDate;
    @JsonFormat
    private String updatedBy;
    private String isCanBuy;
    private String productType;
    private BigDecimal sellPrice;
    private String productTypeName;
}