package com.xinyunfu.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.UserInfoDTO;
import com.xinyunfu.entity.UserWallet;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xinyunfu.service.UserWalletService;

import java.math.BigDecimal;

/**
 * @author faster-builder
 * 用户钱包 Controller
 */
@Slf4j
@RestController
@RequestMapping("/userWallet")
@AllArgsConstructor
public class UserWalletController {


    @Autowired
    private UserWalletService userWalletService;
    @Autowired
    private CustomerManageFeign customerManageFeign;


    /**
     * 给指定账户 发红包 （分润）
     * @param str  用户编号;金额,用户编号,金额
     * @return
     */
    @GetMapping("/redEnvelopes")
    public ResponseInfo<Object> redEnvelopes(@RequestParam("str") String str){
        return ResponseInfo.success(userWalletService.redEnvelopes(str));
    }


    /**
     * 获取我的账户信息
     * @param obj
     * @return
     */
    @PostMapping("/getAccountInfo")
    public ResponseInfo<UserWallet> getAccountInfo(@RequestHeader(Common.UID) String currentUserId){
        return ResponseInfo.success(userWalletService.getUser(currentUserId));
    }


    /**
     * 开户  (按不支持开系统账户)
     * @param userNo        用户编号
     * @param userName      用户姓名
     * @param userType      用户类型 U00系统用户,U01 APP用户,U02 供应商
     * @param accountType   钱包类型 T00积分钱包,T01现金钱包,T02优惠券钱包
     * @return
     */
    @GetMapping("/createAccount")
    public ResponseInfo<Object> createAccount(@RequestParam("userNo") String userNo,
                                              @RequestParam("userName") String userName,
                                              @RequestParam("userType") String userType,
                                              @RequestParam("accountType") String accountType){
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(userNo) || StringUtils.isEmpty(userType) || StringUtils.isEmpty(accountType))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        if(userType.equals(Common.USER_TYPE_SYSTEM))
            throw new CustomException(ExecptionEnum.NOT_YET_SUPPORTED);
        return ResponseInfo.success(userWalletService.add(userNo, userName, userType, accountType));
    }


    /**
     * 后台充值
     * @param accountNo 账户编号
     * @param money     金额
     * @return
     */
    @GetMapping("/recharge")
    public ResponseInfo<Object> recharge(@RequestParam("accountNo") String accountNo,
                                         @RequestParam("money") BigDecimal money){
        if (StringUtils.isEmpty(accountNo) || null == money || money.compareTo(new BigDecimal(0)) != 1)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        //转入到system账户中
        userWalletService.SystemAccountEntry(money);
        //再从system账户中转入到指定账户
        return ResponseInfo.success(userWalletService.SystemOutOfAccount(accountNo,money));
    }

    /**
     *  后台供应商提现
     * @param accountNo  提现账户
     * @param money      提现金额
     * @return
     */
    @GetMapping("/SupplierWithdraw")
    public ResponseInfo<Object> SupplierWithdraw(@RequestParam("accountNo") String accountNo,
                                         @RequestParam("money") String money){
        if(StringUtils.isEmpty(accountNo) || StringUtils.isEmpty(money))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        userWalletService.SupplierWithdraw(accountNo, money);
        return ResponseInfo.success(null);
    }

    /**
     * 获取钱包列表
     * @param entity
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/getList")
    public ResponseInfo<Page<UserWallet>> getList(@RequestBody UserWallet entity,
                                      @RequestParam("page") Integer page,
                                      @RequestParam("pageSize") Integer pageSize){
        if (null == page || null == pageSize || page < 1 || pageSize < 1)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        return ResponseInfo.success((Page)userWalletService.list(entity,page,pageSize));
    }


    /**
     *
     * 现金支付消费 (主要用于校验金额 冻结)
     * @param userNo    用户编号
     * @param money     消费金额
     * @param orderNo   主订单编号
     * @return
     */
    @GetMapping("/consume")
    public ResponseInfo<Object> consume(@RequestParam("userNo") String userNo,
                                        @RequestParam("orderNo") String orderNo,
                                        @RequestParam("money") BigDecimal money){
        if(StringUtils.isEmpty(userNo) || StringUtils.isEmpty(orderNo) || null == money || money.compareTo(new BigDecimal(0)) != 1)
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        if(!userWalletService.consume(orderNo, userNo, money))
            throw new CustomException(ExecptionEnum.ERROR_INSUFFICIENT_BALANCE);
        return ResponseInfo.success(true);
    }



}