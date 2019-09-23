package com.xinyunfu.product.model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
@TableName("pro_banner")
public class ProBanner extends BaseModel{

	@TableId(type = IdType.AUTO)
	private Long bannerId;
	private Integer forwardType;  //跳转类型 1第三方路径,2专题页,3分类页,4商品页
	private String bannerName;
	private String imgUrl;
	private String linkUrl;       //链接路径  跳转类型为第三方路径时,存URL地址,为专题页和分类页时,存分类id,为商品页时,存商品id
	private String color;         //背景颜色
	private String startDate;
	private String endDate;
	private Integer sortNumber;
}
