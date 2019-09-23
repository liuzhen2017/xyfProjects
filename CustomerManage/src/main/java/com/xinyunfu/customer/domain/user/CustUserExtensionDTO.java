package com.xinyunfu.customer.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustUserExtensionDTO {
    private Long id;
    private Long userNo;
    private Integer mealNu;     //套餐购买数量
    private Long superUserNo;

    public CustUserExtensionDTO(Long userNo, Integer mealNu){
        this.userNo = userNo;
        this.mealNu = mealNu;
    }
}
