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
public class ActDTO implements Serializable{

	private List<ProChannel> list;

	public List<ProChannel> getList() {
		return list;
	}

	public void setList(List<ProChannel> list) {
		this.list = list;
	}
}
