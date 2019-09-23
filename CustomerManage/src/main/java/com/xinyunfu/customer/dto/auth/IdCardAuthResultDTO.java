package com.xinyunfu.customer.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class IdCardAuthResultDTO {
    private String status;      //状态码:详见状态码说明 01 通过，02不通过
    private String msg;         //提示信息
    private String idCard;      //身份证号
    private String name;        //姓名
    private String sex;         //性别
    private String area;        //身份证所在地
    private String province;    //省
    private String city;        //市
    private String prefecture;  //区/县
    private String birthday;    //出生年月
    private String addrCode;    //地区代码
    private String lastCode;    //身份证校验码
}
