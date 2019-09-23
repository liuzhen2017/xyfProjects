package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pro_image")
public class ProImage extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
    /**主键id */
	private Long id;
	/**商品id */
	private Long proId;
	/**图片路径 */
	private String imgUrl;
	/**是否是主图  1为主图  0不为主图 */
	private Integer  isDefault;
	 
}
