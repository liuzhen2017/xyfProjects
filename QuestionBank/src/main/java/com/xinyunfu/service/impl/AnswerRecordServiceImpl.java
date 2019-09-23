package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.dto.*;
import com.xinyunfu.mapper.AnswerRecordMapper;
import com.xinyunfu.model.AnswerRecord;
import com.xinyunfu.model.Subject;
import com.xinyunfu.service.IAnswerItemService;
import com.xinyunfu.service.IAnswerRecordService;
import com.xinyunfu.service.ISubjectService;
import com.xinyunfu.utils.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author XRZ
 * @date 2019/7/30
 * @Description :
 */
@Slf4j
@Service
public class AnswerRecordServiceImpl extends ServiceImpl<AnswerRecordMapper, AnswerRecord> implements IAnswerRecordService {

    @Autowired
    private ISubjectService iSubjectService;

    @Autowired
    private IAnswerItemService answerItemService;

    @Autowired
    private RedisCommonUtil  redis;

    /**
     * 初始化题目记录
     * @param currentUserId
     */
    public void init(String currentUserId){
        AnswerRecord ar = new AnswerRecord();
        ar.setUserId(currentUserId);
        ar.setUpdatedBy(currentUserId);
        ar.setCreatedBy(currentUserId);
        ar.setSubjectIds(getIds(Common.LIFE)+";"+getIds(Common.LAW));
        if(!super.save(ar))
            throw new CustomException(ExecptionEnum.SUBJECT_INIT_FAIL);
    }


    /**
     * 根据类型获取ID （随机 200道题的ID ，使用逗号拼接）
     * @param type
     * @return
     */
    public String getIds(Integer type){
        //获取题库指定类型中所有题目ID
        Integer[] ids = iSubjectService.list(new LambdaQueryWrapper<Subject>()
                .select(Subject::getSubjectId)
                .eq(Subject::getSubjectType, type)).stream().map(s -> s.getSubjectId()).toArray(Integer[]::new);
        int index = Common.random.nextInt(ids.length / 200); //200一组
        return StringUtils.join(Arrays.copyOfRange(ids, index * 200, (index + 1) * 200),",");
    }


    /**
     * 获取指定题目类型的数量
     *
     * @param currentUserId
     * @param type
     * @return
     */
    @Override
    public RecordDTO getCount(String currentUserId) {
        LambdaQueryWrapper<AnswerRecord> lq = new LambdaQueryWrapper<>();
        lq.select(AnswerRecord::getLawCount);
        lq.select(AnswerRecord::getLifeCount);
        lq.eq(AnswerRecord::getUserId,currentUserId);
        AnswerRecord one = super.getOne(lq);
        if(null == one){
            this.init(currentUserId);
            return new RecordDTO();
        }
        return new RecordDTO(one.getLifeCount(),one.getLawCount());
    }

    /**
     * 获取指定类型的题目
     *
     * @param currentUserId
     * @param vo
     */
    @Override
    public PageDTO getAnswerRecord(String currentUserId, CommDTO vo) {
        if(null == vo.getType())
            throw new CustomException(ExecptionEnum.SUBJECT_TYPE_NULL);
        if(vo.getPage() ==0 || vo.getPage() > 10)
            throw new CustomException(ExecptionEnum.ERROR_PAGE_OUT);
        String key = Common.KEY_BASIC+currentUserId;
        String item = vo.getType()+"_"+vo.getPage();
        if(redis.hexists(key,item)){
            return (PageDTO) redis.getHashCache(key,item);
        }
        //获取我的题目ID
        AnswerRecord one = super.getOne(new LambdaQueryWrapper<AnswerRecord>().eq(AnswerRecord::getUserId, currentUserId));
        String[] split = one.getSubjectIds().split(";");
        String ids = ""; //题目ID
        Integer record = 0; //答题的进度
        if( vo.getType() == Common.LIFE) {
            ids = split[0];
            record = one.getLifeCount();
        }else{
            ids = split[1];
            record = one.getLawCount();
        }
        //分页
        String[] arrs = Arrays.copyOfRange(ids.split(","), (vo.getPage() - 1) * Common.PAGE_SIEZ, vo.getPage() * Common.PAGE_SIEZ);
        //转换为DTO对象
        List<AnswerDTO> list = new ArrayList<>();
        for (int i = 0; i < arrs.length; i++) {
            int num = i + ((vo.getPage() - 1) * Common.PAGE_SIEZ);
            Subject sub = iSubjectService.getById(arrs[i]);
            AnswerDTO ad = new AnswerDTO();
            ad.setItemNumber(num+1);
            ad.setStatus(record > num ? Common.CORRECT:Common.UNDEFINED);
            ad.setType(sub.getSubjectType());
            ad.setTitle(sub.getSubjectTitle());
            ad.setAnswer(sub.getSubjectAnswer());
            //拆分答案
            String[] sp = sub.getSubjectOption().split("#");
            List<OptionDTO> option = new ArrayList<>();
            for (int j = 0; j < sp.length; j++) {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setOption(answerItemService.getOption(j));
                optionDTO.setValue(sp[j]);
                option.add(optionDTO);
            }
            ad.setOption(option);
            list.add(ad);
        }
        PageDTO dto = new PageDTO(list, vo.getPage());
        if(null != dto && !redis.setHashCache(key, item, dto,Common.EXC_REDIS)){
            log.warn("[答题服务]==========>普通答题记录放入缓存失败！");
        }
        return dto;
    }

    /**
     * 增加答题进度
     *
     * @param currentUserId
     * @param vo
     */
    @Override
    @Transactional
    public boolean add(String currentUserId, CommDTO vo) {
        if(null == vo.getType())
            throw new CustomException(ExecptionEnum.SUBJECT_TYPE_NULL);
        AnswerRecord one = super.getOne(new LambdaQueryWrapper<AnswerRecord>().eq(AnswerRecord::getUserId, currentUserId));
        if( vo.getType() == Common.LIFE) {
            one.setLifeCount(one.getLifeCount()+1);
        }else{
            one.setLawCount(one.getLawCount()+1);
        }
        if(super.updateById(one)) redis.clear(Common.KEY_BASIC+currentUserId);
        return true;
    }

}
