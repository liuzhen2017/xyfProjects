package com.xinyunfu.customer.service.redis;

/**
 * redis中key的管理<br/>
 * 数据发生更改，redis缓存失效，不主动往redis写数据
 */
public class RedisKeyRules {

    private static String base = "customer:";

    /** 单个用户信息 */
    public static String userInfo(Long userNo){ return base + "user:info:no:" + userNo; }

    /** 单个收货地址信息 */
    public static String shippingAddress(Long id){ return base + "shipping:address:id:" + id; }

    /** 当前用户的所有的收货地址的key列表 */
    public static String allShippingAddress(Long userNo){ return base + "shipping:address:user:no:" + userNo; }

    /** 单个行政区域key */
    public static String area(Long id){ return base + "area:info:single:id:" + id; }

    /** 当前行政区域下的区域的key列表 */
    public static String lowerArea(Long id){ return base + "area:info:lower:id:" + id; }

    /** 当前区域等级的所有区域的key列表 */
    public static String levelArea(Integer level){ return base + "area:info:level:" + level; }

    /** 银行卡信息 */
    public static String bankAccount(Long id){ return base + "bank:account:info:id:" + id; }

    /** 当前用户的所有的银行卡的id */
    public static String userBankAccount(Long userNo){ return base + "bank:account:user:no:" + userNo; }

    /** 生成用户推荐码的分布式锁 */
    public static String userReferral(){ return base + "user:referral:key:"; }

    /** 保存生成用户推荐码的index的key */
    public static String referralKey(){ return base + "user:referral:key:index"; }

    /** 保存特殊推荐码 */
    public static String specialReferral(){ return base + "user:special:referral:key"; }

    /** 百度识图token */
    public static String baiduImageToken(){ return base + "baidu:image:token:key"; }

    /** 保存单个银行的key */
    public static String bankInfo(Long id){ return base + "bank:info:id:" + id; }

    /** 保存所有银行的key的key */
    public static String allBankInfo(){ return base + "bank:info:all"; }

    /** 保存用户是否有两个推荐人 */
    public static String userHaveTwoSon(Long userNo){ return base + "relation:two:user:no:" + userNo;}

    /** 用户是否是第一次登陆 */
    public static String userFirstLogin(Long userNo) { return base + "first:login:user:no:" + userNo; }

    /** redis注册短信token */
    public static String verifyToken(String verifyType, String phone){ return base + "verify:token:type:" + verifyType + ":phone:" + phone; }

    /** 防止用户频繁登陆 */
    public static String loginOften(String phone){ return base + "login:often:" + phone; }

    /** 用户扩展 */
    public static String userExtension(Long userNo){ return base + "user:extension:user:no:" + userNo; }
}
