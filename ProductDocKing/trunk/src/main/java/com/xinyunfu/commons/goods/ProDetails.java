package com.xinyunfu.commons.goods;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProDetails extends BaseModel{

	private static final long serialVersionUID = 1L;
	private Long proId;
	private String specs;			//商品规格
	private String details;		//'商品详情

}
