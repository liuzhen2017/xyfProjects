package com.xinyunfu.product.dto;

import java.io.Serializable;
import java.util.List;

import com.xinyunfu.product.model.ProChannel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDTO implements Serializable{

	private List<ProDto> list;
}
