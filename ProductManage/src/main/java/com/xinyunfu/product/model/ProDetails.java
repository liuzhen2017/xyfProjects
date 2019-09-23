package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pro_details")
public class ProDetails extends BaseModel {

      /**
       *
       */
      private static final long serialVersionUID = 1L;
      @TableId(type = IdType.AUTO)
      /**主键id */
      private Long id;
      /**商品id */
      private Long proId;
      /**商品规格 */
      private String specs;
      /**商品详情 */
      private String details;


}
