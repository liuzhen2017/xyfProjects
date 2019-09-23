package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("pro_property_value")
public class ProPropertyValue extends BaseModel{

	@TableId(type = IdType.AUTO)
	private Long valueId;
	/**属性id */
	private Integer propertyId;
	/**属性值名称 */
	private String valueText;
}
