package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.AnswerDTO;
import com.xinyunfu.dto.OptionDTO;
import com.xinyunfu.model.AnswerItem;
import com.xinyunfu.mapper.AnswerItemMapper;
import com.xinyunfu.model.Subject;
import com.xinyunfu.service.IAnswerItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.service.IAnswerMasterService;
import com.xinyunfu.service.ISubjectService;
import com.xinyunfu.utils.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 答题子表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
@Slf4j
@Service
public class AnswerItemServiceImpl extends ServiceImpl<AnswerItemMapper, AnswerItem> implements IAnswerItemService {

    @Autowired
    private AnswerItemMapper answerItemMapper;

    @Autowired
    private IAnswerMasterService iAnswerMasterService;

    @Autowired
    private ISubjectService iSubjectService;

    @Autowired
    private RedisCommonUtil redis;

    /**
     * 获取我的万能券答题 记录
     *
     * @param currentUserId
     * @return
     */
    @Override
    public List<AnswerDTO> getMyAnswerInfo(String currentUserId) {
        String key = Common.KEY_SPECIAL+currentUserId;
        String item = "getMyAnswerInfo";
        if(redis.hexists(key,item)){
            return (List<AnswerDTO>) redis.getHashCache(key,item);
        }
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        List<AnswerItem> list = super.list(new LambdaQueryWrapper<AnswerItem>()
                .eq(AnswerItem::getUserId, currentUserId)
                .eq(AnswerItem::getEnable, Common.ENABLE));
        //转换为DTO对象
        list.forEach(a -> {
            if(null == a) return;
            AnswerDTO ao = new AnswerDTO();
            ao.setItemId(a.getItemId());
            ao.setAnswerId(a.getAnswerId());
            ao.setItemNumber(a.getItemNumber());
            ao.setStatus(a.getItemStatus());
            ao.setSubjectId(a.getSubjectId());
            //获取答题对象
            Subject sub = iSubjectService.getById(a.getSubjectId());
            ao.setType(sub.getSubjectType());
            ao.setTitle(sub.getSubjectTitle());
            //拆分答案
            String[] split = sub.getSubjectOption().split("#");
            List<OptionDTO> option = ao.getOption();
            for (int i = 0; i < split.length; i++) {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setItemId(a.getItemId());
                optionDTO.setSubjectId(a.getSubjectId());
                optionDTO.setOption(this.getOption(i));
                optionDTO.setValue(split[i]);
                option.add(optionDTO);
            }
            ao.setAnswer(sub.getSubjectAnswer());
            answerDTOS.add(ao);
        });
        if(!CollectionUtils.isEmpty(answerDTOS) && !redis.setHashCache(key,item,answerDTOS,Common.EXC_REDIS)){
            log.warn("[答题服务]=========>万能券答题记录放入缓存失败！");
        }
        return answerDTOS;
    }

    @Override
    public String getOption(int i){
        String str = "A";
        if(i == 1){
            str = "B";
        }else if(i == 2){
            str = "C";
        }else if(i == 3){
            str = "D";
        }
        return str;
    }

    /**
     * 答题正确调用，记录该答题 为 已答
     *
     * @param itemId
     * @return
     */
    @Override
    @Transactional
    public Object recordAnswer(Long itemId,String currentUserId) {
        //校验该题是否为已答
        if(answerItemMapper.getStatus(itemId) == 1)
            throw new CustomException(ExecptionEnum.ERROR_RESUBMINT);
        AnswerItem answerItem = new AnswerItem();
        answerItem.setItemId(itemId);
        answerItem.setItemStatus(1); //修改答题状态为 已答
        answerItem.setUpdatedBy(currentUserId);
        if(!super.updateById(answerItem))
            throw new CustomException(ExecptionEnum.ERROR_UPDATE_ANSWER);
        //增加对应 主表的答题记录 和 增加 激活券的 数量
       return iAnswerMasterService.addCount( super.getById(itemId).getAnswerId(),currentUserId);
    }
}
