package com.xinyunfu.product.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("pro_pay_type")
public class ProPayType {
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 商品id */
    private Long proId;
    /** 支付类型id,拼接字符串 */
    private Long payTypeId;
}
