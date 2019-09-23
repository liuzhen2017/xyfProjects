package com.xinyunfu.web;


import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.AnswerDTO;
import com.xinyunfu.dto.CommDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.AnswerMaster;
import com.xinyunfu.service.IAnswerItemService;
import com.xinyunfu.service.IAnswerMasterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xurongze
 * @since 2019-07-11
 */
@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private IAnswerMasterService iAnswerMasterService;

    @Autowired
    private IAnswerItemService iAnswerItemService;


    /**
     *  初始化 6张 万能卷
     *      为该用户生成对应的题
     * @param currentUserId
     * @param orderNo 订单编号
     * @return
     */
    @GetMapping("/CreateAnswer/{currentUserId}")
    public ResponseInfo<Object> CreateAnswer(@PathVariable(Common.UID) String currentUserId,
                                             @RequestParam("orderNo") String orderNo){
        if(StringUtils.isEmpty(orderNo))
            throw new CustomException(ExecptionEnum.ERROR_PARAM);
        return ResponseInfo.success(iAnswerMasterService.CreateAnswer(currentUserId,orderNo));
    }


    /**
     * 获取用户万能券 信息
     * @param currentUserId 当前用户的ID
     * @return
     */
    @GetMapping("/getUserCouponInfo/{currentUserId}")
    public ResponseInfo<AnswerMaster> getUserCouponInfo(@PathVariable(Common.UID) String currentUserId){
        return ResponseInfo.success(iAnswerMasterService.getMyLightenCoupon(currentUserId));
    }



    /**
     * 获取我的万能券 信息
     * @param currentUserId 当前用户的ID
     * @return
     */
    @PostMapping("/getMyLightenCoupon")
    public ResponseInfo<AnswerMaster> getMyLightenCoupon(@RequestHeader(Common.UID) String currentUserId){
        return ResponseInfo.success(iAnswerMasterService.getMyLightenCoupon(currentUserId));
    }



    /**
     * 获取我的万能券答题 记录
     * @param currentUserId
     * @return
     */
    @PostMapping("/getMyAnswerInfo")
    public ResponseInfo<List<AnswerDTO>> getMyAnswerInfo(@RequestHeader(Common.UID) String currentUserId){
        return ResponseInfo.success(iAnswerItemService.getMyAnswerInfo(currentUserId));
    }


    /**
     *  答题正确调用，记录该答题 为已答
     * @param currentUserId
     * @param itemId
     * @return
     */
    @PostMapping("/recordAnswer")
    public ResponseInfo<Object> recordAnswer(@RequestHeader(Common.UID) String currentUserId,
                                             @RequestBody CommDTO commDTO ){
        return ResponseInfo.success(iAnswerItemService.recordAnswer(commDTO.getItemId(),currentUserId));
    }



}
