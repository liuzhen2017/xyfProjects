package com.xinyunfu.product.dto;

import java.io.Serializable;
import java.util.List;

import com.xinyunfu.product.model.ProProperty;
import com.xinyunfu.product.model.ProPropertyValue;

import com.xinyunfu.product.model.ProSku;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain = true)
public class PropertyDTO implements Serializable{

	private ProProperty property;
	private List<ProPropertyValue> proPropertyValues;
}
