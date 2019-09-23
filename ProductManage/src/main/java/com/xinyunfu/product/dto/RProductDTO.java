package com.xinyunfu.product.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xinyunfu.product.model.BaseModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.List;

/**
 * @author TYM
 * @date 2019/8/9
 * @Description :
 */
@Data
@Accessors(chain = true)
public class RProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long proId;

    private String proNo;   //商品编码

    private String skuNo;   //sku编码
    private Integer sortNumber;  //商品排序号
    private String proName;
    private String channelName;
//    private String specs;
//    private String details;

    private BigDecimal price;
    private String imgUrl;

    private String proTitle;

    private String buyerReading;

    private Long storeId;

    private String storeName;

    private Long freightId;
    private Double weight;
    private Integer killStatus;
    private Integer source;
    private Integer proType;
    private String unit;

    private String couponType;     //支持的券类型 0不支持   拼接券的id
    private String couponTypeName;  //券的名称
    private Integer status;            //状态，启用=0,禁用=1,删除=2
    private String remarks;            //备注
    private Timestamp createdTime;     //创建时间

    private BigDecimal costPrice;   //成本价


}
