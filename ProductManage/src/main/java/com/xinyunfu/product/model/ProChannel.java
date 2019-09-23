package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pro_channel")
public class ProChannel extends BaseModel{

	@TableId(type = IdType.AUTO)
    /**
     * id
     */
	private Long id;
    /**
     * 分类id,关联fatherId
     */
	private Long channelId;
    /**
     * 分类名称
     */
	private String channelName;
    /**
     * 父级id,关联channelId
     */
	private Long fatherId;
    /**
     * 父级分类名称
     */
    private String fatherName;
    /**
     * 排序号
     */
	private Integer sortNumber;
    /**
     * 分类广告图片地址
     */
	private String bannerUrl;
    /**
     * 展示图片
     */
	private String imageUrl;
    /**
     * 背景颜色
     */
	private String color;
    /**
     * 链接路径
     */
    private String linkUrl;
    /**
     * 跳转图片
     */
    private String bannerImg;
    /**
     * 跳转类型,1链接,2二级内页,3三级分类,4商品详情
     */
    private String forwardType;
    /**
     * 栏目类型id
     */
    private Long channelTypeId;
    /**
     * 栏目类型名称
     */
    private String channelTypeName;
    /**
     * 子级分类数量(表中无此字段)
     */
    @TableField(exist = false)
    private Integer amount;
	
}
