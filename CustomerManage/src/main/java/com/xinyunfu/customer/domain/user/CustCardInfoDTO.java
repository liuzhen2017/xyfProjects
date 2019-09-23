package com.xinyunfu.customer.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 证件信息
 */
@NoArgsConstructor
@Data
public class CustCardInfoDTO {

    private Long id;
    private String name;            //证件人姓名
    private String cardNo;          //证件号
    private String birthday;        //出生日期
    private Integer sex;            //性别

    private String nation;          //民族
    private String beginTime;       //有效期开始时间
    private String endTime;         //有效期结束时间
    private String issuing;         //签发机关

    private Integer status;         //状态，0禁用，1正常，2删除

    private String province;        //省
    private String city;            //市
    private String prefecture;      //区/县
    private String address;         //详细地址

    private String frontPhoto;      //证件正面照
    private String reversePhoto;    //证件反面照

    private String remark;          //备注
    private Timestamp createTime;      //创建时间
    private Timestamp lastModifyTime;   //最后修改时间
}
