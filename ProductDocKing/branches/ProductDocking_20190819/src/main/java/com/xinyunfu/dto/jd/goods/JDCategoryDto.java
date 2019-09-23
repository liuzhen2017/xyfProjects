package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDCategoryDto {
      private Integer catId;         //分类id
      private Integer parentId;          //父级分类id
      private String name;
      private Integer catClass;       //0：一级分类；1：二级分类；2：三级分类；
      private Integer state;        //1：有效；0：无效；

}
