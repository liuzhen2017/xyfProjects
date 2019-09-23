package com.xinyunfu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.entity.WalletAccFlow;

import java.math.BigDecimal;


public interface AccFlowService extends IService<WalletAccFlow>{
    
    
    /**
     * 每笔转账的转入转出记录分页列表
     *
     * @param request 每笔转账的转入转出记录实体
     * @return ResponseInfo
     */
    IPage<WalletAccFlow> list(WalletAccFlow entity, Integer page, Integer pageSize);


    /**
     *  记录流水
     * @param orderNo       主订单编号（无可可-1）
     * @param accountNo     账户编号
     * @param accountType   账户类型    T00积分钱包,T01现金钱包,T02优惠券钱包
     * @param flowType      流水类型    in转入，out转出
     * @param flowSource    流水来源    商品/套餐购买 product, 用户转账 transfer,充值recharge
     * @param amount        流水金额
     * @param status        流水状态    recharge充值,wait等待支付，success支付成功，failed支付失败
     * @param desc          流水描述
     * @return
     */
    boolean add(String orderNo, String accountNo, String accountType, String flowType, String flowSource, BigDecimal amount,String status,String desc);


    /**
     * 获取我的记录
     * @param userNo       用户编号
     * @param page         当前页数
     * @param pageSize     没页大小
     * @param type         数据类型 （in/out）
     * @return
     */
    IPage<WalletAccFlow> getMyRecord(String userNo, Integer page, Integer pageSize,String type);


    /**
     * 供应商账户余额提现审批
     * @param id 流水号
     * @param type 是否同意审批 （0同意/1拒绝）
     * @param message 驳回消息
     * @return
     */
    void withdrawApproval(String id,Integer type,String message);



}