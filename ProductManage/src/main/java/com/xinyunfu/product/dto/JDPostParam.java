package com.xinyunfu.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author TYM
 * @date 2019/8/20
 * @Description :
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class JDPostParam {

    private List<JDSkuNumsParam> skuNums; 	 	//[{“skuId”:skuId1,”num”:商品数量1},{“skuId”:skuId2,”num”:商品数量2}]，批量以逗号分隔
    private String provinceName; 	    //一级地址省名称
    private String cityName; 	        //二级地址市名称
    private String countyName;          //三级地址区/县名称
    private String townName;            //四级地址


}
