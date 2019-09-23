package com.xinyunfu.product.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ModeDTO implements Serializable{

	private String area;
	private String post;
}
