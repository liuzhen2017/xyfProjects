package com.xinyunfu.dto.jd.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JDImage {
      private long id;
      private long skuId;     //商品编号
      private String path;    //图片url，需要在前面添加http://img13.360buyimg.com/n0/
      private String created;       //创建日期
      private String modified;     //修改日期
      private int yn;         //是否启动
      private int isPrimary;        //1：主图 0：附图
      private String orderSort;     //图片排序顺序
      private String position;      //位置
      private String type;          //类别
      private String features;    //特征

}
