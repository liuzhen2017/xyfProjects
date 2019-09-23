package com.xingyunfu.ebank.service.redis;

/**
 * redis中key的管理<br/>
 * 数据发生更改，redis缓存失效，不主动往redis写数据
 */
public class RedisKeyRules {

    private static String base = "ebank:";

    /** 所有支付渠道的key */
    public static String allChannelId(){ return base + "channel:all:ids"; }
    /** 每个支付渠道的id */
    public static String oneChannel(Long id){ return base + "channel:one:id:" + id; }
    /** 根据ebank支付类型和客户端类型查询 */
    public static String ebankChannel(Integer ebankPayType, String clientType){
        return base + "channel:ebankPayType:" + ebankPayType + ":clientType:" + clientType;
    }

    /** 修改账户余额分布式锁 */
    public static String balanceRedisLock(String accountNo){ return base + "redisLock:balance:account:no" + accountNo; }
    /** 合并用户转账分布式锁 */
    public static String transferRedisLock(String accountNo){ return base + "redisLock:transfer:account:no:" + accountNo; }

    /** 执行对账分布式锁 */
    public static String scanTransferMergeOrder(){ return base + "scan:transfer:merge:order"; }
    /** 执行对账结果短信发送判断 */
    public static String scanDoSendMessage(){ return base + "scan:do:send:message"; }

    /** ebank扩展表数据 */
    public static String ebankAccountExtension(String accountNo){ return base + "account:extension:account:no:" + accountNo; }

    /** 执行对账明细的分布式锁 */
    public static String scanTransferMergeDetailOrder(){ return base + "scan:transfer:merge:order:detail"; }
    /** 执行对账明细的执行结果 */
    public static String scanTransferMergeDetailClose(){ return base + "scan:transfer:merge:order:detail:close"; }
}
