package com.xinyunfu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.dto.CommDTO;
import com.xinyunfu.dto.PageDTO;
import com.xinyunfu.dto.RecordDTO;
import com.xinyunfu.model.AnswerRecord;

/**
 * @author XRZ
 * @date 2019/7/30
 * @Description :
 */
public interface IAnswerRecordService extends IService<AnswerRecord> {

    /**
     * 获取指定题目类型的数量
     * @param currentUserId
     * @return
     */
    RecordDTO getCount(String currentUserId);

    /**
     * 获取指定类型的题目
     * @param currentUserId
     * @param vo
     */
    PageDTO getAnswerRecord(String currentUserId, CommDTO vo);

    /**
     * 增加答题进度
     * @param currentUserId
     * @param vo
     */
    boolean add(String currentUserId,CommDTO vo);
}
