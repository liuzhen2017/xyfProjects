package com.xinyunfu.customer.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获得当前推荐人的子推荐人套餐信息
 */
@Data
@NoArgsConstructor
public class UserReferrerInfoDTO {

    Integer totalMealNu;
    Integer totalMealUser;
    //粉丝实名认证数
    Integer totalCardCount;
    //粉丝绑定银行卡数
    Integer totalBankCount;
}
