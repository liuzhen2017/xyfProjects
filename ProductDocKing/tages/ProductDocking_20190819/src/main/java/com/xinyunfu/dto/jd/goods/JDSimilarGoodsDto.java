package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDSimilarGoodsDto {
      private int dim;        //维度序号
      private String saleName;            //销售名称
      //private String saleAttrList;
      private List<JDGoodsSaleAttr> saleAttrList;         //标签信息

}
