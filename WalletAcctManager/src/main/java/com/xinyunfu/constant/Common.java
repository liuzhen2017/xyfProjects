package com.xinyunfu.constant;

/**
 * @author XRZ
 * @date 2019/8/20
 * @Description : 公共变量
 */
public interface Common {

    String UID = "currentUserId";

    /**
     * 系统用户
     */
    String SYSTEM_ACCOUNT = "S001";

    /**
     * 收益账户（系统用户）
     */
    String INNER_ACCOUNT = "S002";

    /**
     * 流水类型 转入
     */
    String FLOW_TYPE_IN = "in";

    /**
     * 流水类型 转出
     */
    String FLOW_TYPE_OUT = "out";

    /**
     * 流水来源 购买商品/购买套餐
     */
    String FLOW_SOURCE_PRODUCT = "product";

    /**
     * 流水来源 用户转账
     */
    String FLOW_SOURCE_TRANSFER = "transfer";

    /**
     * 流水来源 提现
     */
    String FLOW_SOURCE_WITHDRAW = "withdraw";

    /**
     * 流水状态 待支付
     */
    String STATUS_WAIT = "wait";

    /**
     * 流水状态 支付成功
     */
    String STATUS_SUCCESS = "success";

    /**
     * 流水状态 支付失败
     */
    String STATUS_FAILED = "failed";


    /**
     * 账号类型 积分钱包
     */
    String ACCOUNT_TYPE_INTEGRAL = "T00";

    /**
     * 账号类型 现金钱包
     */
    String ACCOUNT_TYPE_MONEY = "T01";

    /**
     * 账号类型 优惠券钱包
     */
    String ACCOUNT_TYPE_COUPON = "T02";

    /**
     * 用户类型 系统用户
     */
    String USER_TYPE_SYSTEM = "U00";

    /**
     * 用户类型 APP用户
     */
    String USER_TYPE_APP = "U01";

    /**
     * 用户类型 供应商
     */
    String USER_TYPE_SUP = "U02";

    /**
     * 启用 表数据
     */
    int ENABLE = 1;

    /**
     * 禁用 表数据
     */
    int DISABLE = 0;
}
