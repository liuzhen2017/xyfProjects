package com.ruoyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;

/**
 * 版本表 pro_home_version
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
public class ProHomeVersion extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
	private Integer versionId;
	/** 版本号 */
	private String version;
	/** 版本样式,采取1st:channel_type_id,2nd:channel_type_id的形式 */
	private String style;
	/** 状态，启用=0,禁用=1,删除=2 */
	private Integer status;
	/** 创建时间 */
	private Date createdTime;
	/** 创建人 */
	private String createdBy;
	/** 修改时间 */
	private Date updatedTime;
	/** 修改人 */
	private String updatedBy;

	public void setVersionId(Integer versionId) 
	{
		this.versionId = versionId;
	}

	public Integer getVersionId() 
	{
		return versionId;
	}
	public void setVersion(String version) 
	{
		this.version = version;
	}

	public String getVersion() 
	{
		return version;
	}
	public void setStyle(String style) 
	{
		this.style = style;
	}

	public String getStyle() 
	{
		return style;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}

	public Integer getStatus() 
	{
		return status;
	}
	public void setCreatedTime(Date createdTime)
	{
		this.createdTime= createdTime;
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

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("versionId", getVersionId())
            .append("version", getVersion())
            .append("style", getStyle())
            .append("status", getStatus())
            .append("createdTime", getCreatedTime())
            .append("createdBy", getCreatedBy())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .toString();
    }
}
