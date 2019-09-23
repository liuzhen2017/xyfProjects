package com.xinyunfu.customer.domain.address;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 行政区域
 */
@NoArgsConstructor
@Data
public class CustAreaDTO {
    private Long id;
    private Long parentId;          //父级区域id
    private String areaCode;        //区域编号
    private String areaName;        //区域名称
    private String areaLongName;    //区域全名
    private Integer areaLevel;      //行政级别，0国家，1省, 2市，3区/县
    private Integer directLevel;    //直观级别，如直辖市、地级市、县级市同视为市级
    private Integer areaOrder;      //先后顺序
}
