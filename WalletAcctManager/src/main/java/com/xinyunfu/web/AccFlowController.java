package com.xinyunfu.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.GetRecordDTO;
import com.xinyunfu.dto.UserInfoDTO;
import com.xinyunfu.entity.UserWallet;
import com.xinyunfu.entity.WalletAccFlow;
import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xinyunfu.service.AccFlowService;

/**
 * @author faster-builder
 * 每笔转账的转入转出记录 Controller
 */
@RestController
@RequestMapping("/accFlow")
public class AccFlowController {

    @Autowired
    private AccFlowService accFlowService;


    /**
     * 获取我的记录
     * @param currentUserId
     * @param vo
     * @return
     */
    @PostMapping("/getMyRecord")
    public ResponseInfo<IPage<WalletAccFlow>> getMyRecord(@RequestHeader(Common.UID) String currentUserId,
                                                                  @RequestBody GetRecordDTO vo){
        return ResponseInfo.success(accFlowService.getMyRecord(currentUserId,vo.getPage(),vo.getPageSize(),vo.getType()));
    }



    /**
     * 获取所有流水记录
     * @param walletAccFlow
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping("/getList")
    public ResponseInfo<IPage<WalletAccFlow>> getList(@RequestBody WalletAccFlow walletAccFlow,
                                                      @RequestParam("page") Integer page,
                                                      @RequestParam("pageSize") Integer pageSize){
        return ResponseInfo.success(accFlowService.list(walletAccFlow,page,pageSize));
    }

    /**
     * 供应商账户余额提现审批
     * @param id 流水号
     * @param type 是否同意审批 （0同意/1拒绝）
     * @param message 驳回消息
     * @return
     */
    @GetMapping("/withdrawApproval")
    public ResponseInfo<Object> withdrawApproval(@RequestParam("id") String id,
                                                 @RequestParam("type") Integer type,
                                                 @RequestParam("message") String message){
        accFlowService.withdrawApproval(id, type,message);
        return ResponseInfo.success(null);
    }




}