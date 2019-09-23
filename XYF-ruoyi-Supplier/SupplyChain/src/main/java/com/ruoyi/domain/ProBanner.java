package com.ruoyi.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 广告表 pro_banner
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProBanner extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 广告id */
	private Long bannerId;
	/** 广告名称 */
	private String bannerName;
	/** 图片路径 */
	private String imgUrl;
	/** 广告链接 */
	private String linkUrl;
	/** 开始时间 */
	private String startDate;
	/** 结束时间 */
	private String endDate;
	/** 排序号 */
	private Integer sortNumber;
	/** 状态 */
	private Integer status;


	private String color;
	private String forwardType;
}
