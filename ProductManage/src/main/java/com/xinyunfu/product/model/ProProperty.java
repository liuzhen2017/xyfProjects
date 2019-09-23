package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("pro_property")
@NoArgsConstructor
@AllArgsConstructor
public class ProProperty extends BaseModel{
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long propertyId;
	/**属性名 */
	private String propertyName;
	/** 商品id */
	private Long proId;
}
