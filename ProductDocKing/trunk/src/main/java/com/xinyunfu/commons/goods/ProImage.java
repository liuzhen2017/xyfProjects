package com.xinyunfu.commons.goods;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProImage extends BaseModel {

      private Long id;
      private Long proId;
      private String imgUrl;
      private Integer isDefault;    //是否为默认显示,1为默认

}
