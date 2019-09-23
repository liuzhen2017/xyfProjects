package com.xinyunfu.product.dto;

import java.io.Serializable;
import java.util.List;

import com.xinyunfu.product.model.ProChannel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SecKillDTO implements Serializable{

	private ProChannel proChannel;
	private List<KillChannelDTO> killChannelDTOS;
	private ProChannel currentSecKill;
	private List<ProDto> proDTOs;
}
