package com.xinyunfu.customer.domain.bank;

import com.xinyunfu.customer.constant.BankConstants;
import com.xinyunfu.customer.domain.address.CustAreaDTO;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.dto.auth.BankCardAuthResultDTO;
import com.xinyunfu.customer.dto.bank.BankAccountAddDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 银行卡
 */
@NoArgsConstructor
@Data
public class CustBankAccountDTO {

    private Long id;
    private Long userNo;            //银行卡所有人
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
    private Integer status;         //银行卡状态，删除=0，审核通过=1

    private String branchNo;        //银行联行号
    private String branchName;      //银行联行名
    private String branchBank;      //支行信息
    private String imageFullpath;   //手持银行卡图像的完整路径

    private String remark;          //备注
    private Timestamp createTime;      //创建时间
    private Timestamp lastModifyTime;  //最后修改时间

    public CustBankAccountDTO(CustUserDTO user, CustBankInfoDTO bankInfo,
                              BankCardAuthResultDTO authResult, BankAccountAddDTO bankAccountAdd,
                              CustAreaDTO regionArea, CustAreaDTO cityArea, CustAreaDTO provinceArea){
        this.userNo = user.getUserNo();
        this.bankId = bankInfo.getId();

        this.accountName = user.getName();
        this.accountNo = authResult.getAccountNo();
        this.cardNo = user.getCardNo();
        this.defaultCard = bankAccountAdd.getDefaultCard();
        this.phone = bankAccountAdd.getPhone();

        this.bankName = bankInfo.getCname();
        this.bankCode = bankInfo.getBankCode();

        this.provinceId = provinceArea.getId();
        this.cityId = cityArea.getId();
        this.regionId = regionArea.getId();
        this.provinceName = provinceArea.getAreaName();
        this.cityName = cityArea.getAreaName();
        this.regionName = regionArea.getAreaName();
        this.address = bankAccountAdd.getAddress();

        this.accountType = BankConstants.ACCOUNT_TYPE_PERSON;
        this.cardType = authResult.getCardType();
        this.cardName = authResult.getCardName();
        this.status = BankConstants.STATUS_APPROVAL;
    }
}
