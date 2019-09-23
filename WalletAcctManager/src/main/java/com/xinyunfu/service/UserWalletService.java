package com.xinyunfu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.entity.UserWallet;
import com.xinyunfu.jace.utils.ResponseInfo;

import java.math.BigDecimal;


public interface UserWalletService extends IService<UserWallet>{

    /**
     * 给指定账户 发红包 （分润）
     * @param str  用户编号;金额,用户编号,金额
     * @return
     */
    boolean redEnvelopes(String str);
    
    
    /**
     * 用户钱包分页列表
     *
     * @param request 用户钱包实体
     * @return ResponseInfo
     */
    IPage<UserWallet> list(UserWallet entity,Integer page,Integer pageSize);

    /**
     * 获取指定用户编号的账号信息
     * 校验用户是否存在，如果不存则创建
     * @param userNo 用户编号
     */
    UserWallet getUser(String userNo);

    /**
     * 新增用户钱包
     *
     * @param request 请求参数
     * @return ResponseInfo
     */
    boolean add(String userNo,String userName,String userType,String accountType);


    /**
     * 系统用户 入账
     * @param money      金额
     * @return
     */
    boolean SystemAccountEntry(BigDecimal money);

    /**
     * 系统账户 出账
     * @param accountNo  账户编号
     * @param money      金额
     * @return
     */
    boolean SystemOutOfAccount(String accountNo,BigDecimal money);

    /**
     * 消费
     * @param orderNo  订单编号
     * @param userNo   用户编号
     * @param money    消费金额
     * @return
     */
    boolean consume(String orderNo,String userNo,BigDecimal money);

    /**
     * 供应商提现
     * @param accountNo
     * @param money
     * @return
     */
    void SupplierWithdraw(String accountNo,String money);

}