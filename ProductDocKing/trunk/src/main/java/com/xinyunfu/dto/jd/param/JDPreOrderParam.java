package com.xinyunfu.dto.jd.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JDPreOrderParam {
      private long thirdOrder;      //第三方的订单单号（需要调用生成序列号接口获得）
      private JDSkuParam[] sku;     //商品信息，(最高支持一次对50个不同sku下单)
      private String name;          //商品编号
      private long province;         //一级地址id
      private long city;       //二级地址id
      private long county;           //级地址id
      private long town;       //四级地址id（地址接口查询） (如果该地区有四级地址，则必须传递四级地址，没有四级地址则传0)
      private String address;       //详细地址（少于30字）
      private String zip;           //邮编，没有传空字符串
      private String phone;         //座机号，没有传空字符串
      private long mobile;          //手机号
      private String email;         //邮箱（接收订单邮件，可传B端商家邮箱地址）
      private String remark;        //备注（少于100字），没有传空字符串
      private String ext;           //订单自定义字段
}
