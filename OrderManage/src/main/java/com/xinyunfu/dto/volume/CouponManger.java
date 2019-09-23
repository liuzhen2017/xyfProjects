package com.xinyunfu.dto.volume;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @author liuzhen
* 优惠券管理 实体
*/
@Data
@NoArgsConstructor
public class CouponManger implements Serializable {
    @TableId(value="ID", type= IdType.AUTO)
	private String id;
    private Integer couponType;
    private String couponNode;
    private String title;
    private String describes;
    private String picUrl;
    private String rules;
    private BigDecimal valueAmount;
    @JsonFormat(pattern="YYYY-MM-dd",timezone="GMT+8")
    private Date effectiveTime;
    @JsonFormat(pattern="YYYY-MM-dd",timezone="GMT+8")
    private Date invalidTime;
    private Integer totalNum;
    private Integer canNum;
    private Integer usedNum;
    private String couponStatus;
    @JsonFormat
    private Date createdDate;
    @JsonFormat
    private String createdBy;
    @JsonFormat
    private Date updatedDate;
    @JsonFormat
    private String updatedBy;
    private String isCanBuy;
    private String productType;
    private BigDecimal sellPrice;
}