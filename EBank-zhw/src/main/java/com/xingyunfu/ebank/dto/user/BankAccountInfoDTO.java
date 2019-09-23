package com.xingyunfu.ebank.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BankAccountInfoDTO {

    private Long id;
    private Long bankId;            //银行卡所属银行

    private String accountName;     //开户人姓名
    private String accountNo;       //银行卡号
    private String cardNo;          //开户人身份证号
    private Boolean defaultCard;    //是否是默认卡
    private String phone;           //银行卡绑定的手机号

    private String bankName;        //银行名称
    private String bankCode;        //银行编号

    private Long provinceId;        //开户省份id
    private Long cityId;            //开户城市id
    private Long regionId;          //开户区域id
    private String provinceName;    //开户省份
    private String cityName;        //开户城市名称
    private String regionName;      //开户区域名称
    private String address;         //具体地址

    private Integer accountType;    //账号类型，0个人账号，1企业账号
    private String cardType;        //银行卡类型
    private String cardName;        //银行卡名称
    private Integer status;         //银行卡状态，审核中=1,审核通过=2,审核失败=4,删除=8

    private String branchNo;        //银行联行号
    private String branchName;      //银行联行名
    private String branchBank;      //支行信息
    private String imageFullpath;   //手持银行卡图像的完整路径
}
