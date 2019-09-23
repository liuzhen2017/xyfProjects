package com.xinyunfu.product.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@TableName("product")
public class Product extends BaseModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long proId;               //商品id

    private String proNo;             //商品编码  关联第三方商品id

    private String proName;           //商品名称

    private String proTitle;          //商品标题
    private String buyerReading;      //购物须知
    private Long storeId;             //店铺id
    private String storeName;         //店铺名称
    private Long freightId;           //邮费模板id
    private Double weight;            //重量
    private Integer killStatus;       //秒杀状态
    private Integer source;           //商品来源  0自营,1京东,2怡亚通
    private Integer proType;          //商品类型 0普通商品,1秒杀商品,2套餐商品
    private String unit;              //单位
    private Integer sortNumber;       //排序号
    private String couponType;        //支持的券类型 0不支持   拼接券的id
    private String couponTypeName;    //券的名称

}
