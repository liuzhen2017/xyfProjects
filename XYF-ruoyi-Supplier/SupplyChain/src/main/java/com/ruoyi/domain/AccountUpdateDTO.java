package com.ruoyi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDTO {

      private Long userNo;                    //用户编号
      private String userName;                //用户姓名
      private String idCardNo;                //身份证编号
      private String accountName;             //账号名
      private String accountType;             //账户类型

      private String bankCardNo;              //银行卡号
      private String bankName;                //银行名称
      private String bankNo;                  //银行编号
      private String branchName;              //分行名称
      private String branchNo;                //分行行号

      private String provinceName;            //省份
      private String cityName;                //城市

      private String businessStatus;          //业务状态 not use
      private Boolean enable;                 //是否可用

}
