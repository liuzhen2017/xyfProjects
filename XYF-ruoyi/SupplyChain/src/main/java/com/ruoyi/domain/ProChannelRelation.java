package com.ruoyi.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品分类关系表 pro_channel_relation
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Data
@Accessors(chain = true)
public class ProChannelRelation implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	@Excel
	private Long id;
	/** 商品id */
	private Long proId;
	private String proName;

	/** 类目id */
	private Long channelId;
	private String channelName;
    /**
     * 商品展示图片
     */
	private String imgUrl;
    /**
     * 背景颜色
     */
    private String color;
	/** 状态，启用=0,禁用=1,删除=2 */
	private Integer status;
	/** 备注 */
	private String remarks;
	/** 创建时间 */
	private Date createdTime;
	/** 创建人 */
	private String createdBy;
	/** 修改时间 */
	private Date updatedTime;
	/** 修改人 */
	private String updatedBy;


}
