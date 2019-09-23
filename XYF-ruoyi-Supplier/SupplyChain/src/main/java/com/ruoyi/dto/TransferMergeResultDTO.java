package com.ruoyi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TransferMergeResultDTO {

    private String ebankOrderNo;            //ebank合并订单号
    private String receiveAccountNo;        //收款人ebank账户
    private String receiveAccountType;      //收款人账户类型
    private BigDecimal amount;              //转帐金额
    private Boolean closed;                 //是否已关闭
    private String status;                  //合并订单状态

    private String userName;                //用户姓名
    private String phone;                   //银行卡绑定的手机号
    private String idCardNo;                //身份证编号

    private String bankCardNo;              //银行卡号
    private String bankName;                //银行名称
    private String bankNo;                  //银行编号
    private String branchName;              //分行名称
    private String branchNo;                //分行行号

    private String provinceName;            //省份
    private String cityName;                //城市

    private String createTime;           //创建时间
    private String lastModifyTime;       //最后修改时间


}
