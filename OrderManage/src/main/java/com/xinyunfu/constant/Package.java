package com.xinyunfu.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author XRZ
 * @date 2019/7/26
 * @Description : 套餐相关变量
 */
@Component
public class Package {

    /**
     * 大使购买金额限制
     */
    public static BigDecimal ambassador;

    /**
     * 普通用户购买数量限制
     */
    public static Integer commonMaxNum;


    @Value("${package.ambassador.maxMoney}")
    public void setVipMaxMoney(BigDecimal ambassador) {
        Package.ambassador = ambassador;
    }

    @Value("${package.common.maxNum}")
    public void setCommonMaxNum(Integer commonMaxNum) {
        Package.commonMaxNum = commonMaxNum;
    }
}
