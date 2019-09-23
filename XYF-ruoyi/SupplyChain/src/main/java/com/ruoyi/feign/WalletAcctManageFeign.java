package com.ruoyi.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.domain.UserWallet;
import com.ruoyi.domain.WalletAccFlow;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author TYM
 * @date 2019/8/20
 * @Description :
 */
@FeignClient("WalletAcctManage")
public interface WalletAcctManageFeign {
    /**
     * 供应商开钱包账户,用户类型指定为U02,钱包类型指定为T01
     * @param userNo        	用户编号
     * @param userName          用户姓名
     * @param userType          用户类型 U00系统用户,U01 APP用户,U02 供应商
     * @param accountType       钱包类型 T00积分钱包,T01现金钱包,T02优惠券钱包
     * @return boolean
     */
    @GetMapping("/userWallet/createAccount")
    public com.ruoyi.utils.ResponseInfo<Object> createAccount(@RequestParam("userNo")String userNo,
                                                              @RequestParam("userName")String userName,
                                                              @RequestParam("userType")String userType,
                                                              @RequestParam("accountType")String accountType);


    /**
     * 分页查询用户钱包
     * @param userWallet
     * @return
     */
    @PostMapping("/userWallet/getList")
    public com.ruoyi.utils.ResponseInfo<Page<UserWallet>> getUserWalletPage(@RequestBody UserWallet userWallet,
                                                                            @RequestParam("page") Integer page,
                                                                            @RequestParam("pageSize") Integer pageSize);

    /**
     * 分页查询转账流水
     * @param walletAccFlow
     * @return
     */
    @PostMapping("/accFlow/getList")
    public com.ruoyi.utils.ResponseInfo<Page<WalletAccFlow>> getWalletAccFlowPage(@RequestBody WalletAccFlow walletAccFlow,
                                                                                  @RequestParam("page") Integer page,
                                                                                  @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据id给用户账户充值
     * @param id
     * @return
     */
    @GetMapping("/userWallet/recharge")
    public com.ruoyi.utils.ResponseInfo<Object> recharge(@RequestParam("accountNo") String accountNo, @RequestParam("money")BigDecimal money);


    /**
     * 获取我的账户信息
     * @param obj
     * @return
     */
    @PostMapping("/userWallet/getAccountInfo")
    public com.ruoyi.utils.ResponseInfo<UserWallet> getAccountInfo(@RequestHeader("currentUserId") String currentUserId);


    /**
     *  后台供应商提现
     * @param accountNo  提现账户
     * @param money      提现金额
     * @return
     */
    @GetMapping("/userWallet/SupplierWithdraw")
    public com.ruoyi.utils.ResponseInfo<Object> SupplierWithdraw(@RequestParam("accountNo") String accountNo, @RequestParam("money") String money);

    /**
     * 账户余额提现审批
     * @param id 流水号
     * @param type 是否同意审批 （0同意/1拒绝）
     * @param message 驳回的消息
     * @return
     */
    @GetMapping("/accFlow/withdrawApproval")
    public com.ruoyi.utils.ResponseInfo<Object> withdrawApproval(@RequestParam("id") String id,
                                                                 @RequestParam("type") Integer type,
                                                                 @RequestParam("message") String message);

}
