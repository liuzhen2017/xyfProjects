package com.ruoyi.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class TransferMergeResultDTO {

    @Excel(name = "ebank合并订单号")
    private String ebankOrderNo;            //ebank合并订单号
    @Excel(name = "收款人ebank账户")
    private String receiveAccountNo;        //收款人ebank账户
    @Excel(name = "收款人账户类型",readConverterExp="common=普通用户,ambassador=推广大使,supplier=供应链用户,system=平台账号")
    private String receiveAccountType;      //收款人账户类型
    @Excel(name = "转帐金额")
    private BigDecimal amount;              //转帐金额
    @Excel(name = "是否已关闭" , readConverterExp="true=已关闭,false=未关闭" )
    private Boolean closed;                 //是否已关闭
    @Excel(name = "合并订单状态",readConverterExp="applying=申请支付中,apply_filed=申请支付失败,success=支付成功,wait_handle=等待人工处理,handle_success=手工处理完成")
    private String status;                  //合并订单状态

    @Excel(name = "用户姓名")
    private String userName;                //用户姓名
    @Excel(name = "银行卡绑定的手机号")
    private String phone;                   //银行卡绑定的手机号
    @Excel(name = "身份证编号")
    private String idCardNo;                //身份证编号

    @Excel(name = "银行卡号")
    private String bankCardNo;              //银行卡号
    @Excel(name = "银行名称")
    private String bankName;                //银行名称
    @Excel(name = "银行编号")
    private String bankNo;                  //银行编号
    @Excel(name = "分行名称")
    private String branchName;              //分行名称
    @Excel(name = "分行行号")
    private String branchNo;                //分行行号

    private String provinceName;            //省份
    private String cityName;                //城市

    private String createTime;           //创建时间
    private String lastModifyTime;       //最后修改时间


}
