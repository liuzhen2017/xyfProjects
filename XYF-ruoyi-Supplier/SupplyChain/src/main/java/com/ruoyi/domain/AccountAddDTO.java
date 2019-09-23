package com.ruoyi.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AccountAddDTO {

      private Long userNo;                    //用户编号
      private String userName;                //用户姓名
      private String idCardNo;                //身份证编号
      private String accountName;             //账号名
      private String accountType;             //账户类型

      //账户类型	说明
      //common	普通用户
      //ambassador	推广大使
      //supplier	供应链用户
      //system	平台账号，此账号只能有一个

}
