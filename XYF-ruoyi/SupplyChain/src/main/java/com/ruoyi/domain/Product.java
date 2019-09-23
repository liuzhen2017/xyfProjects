package com.ruoyi.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.Date;


@Data
@Accessors(chain=true)
@TableName("product")
public class Product  {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long proId;
	
	private String proNo;
	
	private String proName;
	private Integer sortNumber;
	private String proTitle;
	private String buyerReading;
	private Long storeId;
	private String storeName;
	private Long freightId;
	private Double weight;
	private Integer killStatus;
	private Integer source;
	private Integer proType;
	private String unit;

	private String couponType;     //支持的券类型 0不支持   拼接券的id
    private String couponTypeName;  //券的名称

    /** 创建者 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdTime;

    /** 更新者 */
    private String updatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedTime;

    /** 备注 */
    private String remarks;

}
