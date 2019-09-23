package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/7/13
 * @Description : 取消订单类型相关常量
 */
public interface QancelType {

    /**
     * 自动取消
     */
    int AUTO = 0;

    /**
     * 我不想买了
     */
    int NOT_BUY = 1;

    /**
     * 信息填写错误
     */
    int INFO_ERROR = 2;

    /**
     * 重新下单
     */
    int AGAIN = 3;

    /**
     * 其他原因
     */
    int OTHER = 4;

    String[] VAL = {"自动取消","我不想买了","信息填写错误","重新下单","其他原因"};
}
