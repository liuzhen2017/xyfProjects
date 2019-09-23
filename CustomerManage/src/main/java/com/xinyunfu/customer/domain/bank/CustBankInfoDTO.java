package com.xinyunfu.customer.domain.bank;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 银行信息
 */
@NoArgsConstructor
@Data
public class CustBankInfoDTO {

    private Long id;
    private String cname;           //中文名
    private String ename;           //英文名
    private String bankCode;        //银行编号
    private String createTime;      //银行创建时间
    private String url;             //银行官网
    private String logo;            //银行logo
    private Integer status;         //银行状态
    private String remark;          //备注
    private Integer rank;            //排序权重
}
