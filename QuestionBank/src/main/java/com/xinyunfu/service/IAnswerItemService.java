package com.xinyunfu.service;

import com.xinyunfu.dto.AnswerDTO;
import com.xinyunfu.model.AnswerItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 答题子表 服务类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
public interface IAnswerItemService extends IService<AnswerItem> {

    /**
     * 获取我的万能券答题 记录
     * @param currentUserId
     * @return
     */
    List<AnswerDTO>  getMyAnswerInfo(String currentUserId);

    /**
     *  答题正确调用，记录该答题 为已答
     * @param itemId
     * @param currentUserId
     * @return
     */
    Object recordAnswer(Long itemId,String currentUserId);


    String getOption(int i);

}
