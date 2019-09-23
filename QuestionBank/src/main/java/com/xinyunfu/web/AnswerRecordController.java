package com.xinyunfu.web;

import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.CommDTO;
import com.xinyunfu.dto.PageDTO;
import com.xinyunfu.dto.RecordDTO;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.IAnswerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author XRZ
 * @date 2019/7/29
 * @Description :
 */
@RestController
@RequestMapping("/AnswerRecord")
public class AnswerRecordController {

    @Autowired
    private IAnswerRecordService iAnswerRecordService;

    /**
     * 获取 上知天文/法律知识 的答题记录
     * @param currentUserId
     * @param object
     * @return
     */
    @PostMapping("/getCount")
    public ResponseInfo<RecordDTO> getCount(@RequestHeader(Common.UID) String currentUserId){
        return ResponseInfo.success(iAnswerRecordService.getCount(currentUserId));
    }


    /**
     * 获取指定类型的 题目
     * @param currentUserId
     * @param vo
     * @return
     */
    @PostMapping("/getAnswerRecord")
    public ResponseInfo<PageDTO> getAnswerRecord(@RequestHeader(Common.UID) String currentUserId,
                                                 @RequestBody CommDTO vo){
        return ResponseInfo.success(iAnswerRecordService.getAnswerRecord(currentUserId,vo));
    }


    /**
     * 增加 答题进度
     * @param currentUserId
     * @param vo
     * @return
     */
    @PostMapping("/add")
    public ResponseInfo<Object> add(@RequestHeader(Common.UID) String currentUserId,
                                    @RequestBody CommDTO vo){
        return ResponseInfo.success(iAnswerRecordService.add(currentUserId, vo));
    }



}
