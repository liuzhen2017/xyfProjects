package com.xinyunfu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.feign.VolumeMarketFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.AnswerItem;
import com.xinyunfu.model.AnswerMaster;
import com.xinyunfu.mapper.AnswerMasterMapper;
import com.xinyunfu.service.IAnswerItemService;
import com.xinyunfu.service.IAnswerMasterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.service.ISubjectService;
import com.xinyunfu.utils.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 答题主表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-11
 */
@Slf4j
@Service
public class AnswerMasterServiceImpl extends ServiceImpl<AnswerMasterMapper, AnswerMaster> implements IAnswerMasterService {

    @Autowired
    private AnswerMasterMapper answerMasterMapper;

    @Autowired
    private IAnswerItemService iAnswerItemService;

    @Autowired
    private ISubjectService iSubjectService;

    @Autowired
    private VolumeMarketFeign volumeMarketFeign;

    @Autowired
    private RedisCommonUtil redis;

    /**
     * 初始化 6张 万能卷
     *      为该用户生成对应的题
     * @param currentUserId
     * @return
     */
    @Override
    public boolean CreateAnswer(String currentUserId,String orderNo) {
        AnswerMaster master = answerMasterMapper.selectOne(new LambdaQueryWrapper<AnswerMaster>()
                .eq(AnswerMaster::getUserId, currentUserId));
        if(null != master){//修改
            master.setOrderNo(Long.valueOf(orderNo));
            master.setCount(Common.PACKAGE_COUNT);
            master.setCouponCount(0);
            if(answerMasterMapper.updateById(master) != 1)
                throw new CustomException(ExecptionEnum.UPDATE_SUJBECT_MASTER_FAIL);
            //清空子答题记录 设置为失效
            AnswerItem answerItem = new AnswerItem();
            answerItem.setEnable(Common.DISABLE); //设置为失效
            if(!iAnswerItemService.update(answerItem,new UpdateWrapper<AnswerItem>()
                    .eq("enable",Common.ENABLE)
                    .eq("answer_id",master.getAnswerId())))
                throw new CustomException(ExecptionEnum.UPDATE_SUJBECT_ITEM_FAIL);
        }else{ //新增
            //生成主答题对象
            master = new AnswerMaster();
            master.setOrderNo(Long.valueOf(orderNo));
            master.setUserId(Long.valueOf(currentUserId));
            master.setCreatedBy(currentUserId);
            master.setUpdatedBy(currentUserId);
            master.setCount(Common.PACKAGE_COUNT); //设置万能卷的数量
            if(!super.save(master))
                throw new CustomException(ExecptionEnum.SAVE_SUBJECT_MASTER_FAIL);
        }
        //从题库获取六道题
        int[] ids = iSubjectService.getSubject();
        ArrayList<AnswerItem> items = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            items.add(new AnswerItem(currentUserId,master.getAnswerId(),i+1,ids[i],currentUserId,currentUserId));
        }
        //生成新的六道题
        if(!iAnswerItemService.saveBatch(items))
            throw new CustomException(ExecptionEnum.SAVE_SUBJECT_FAIL);
        redis.clear(Common.KEY_SPECIAL+currentUserId);
        log.info("[答题服务]==========>初始化6张万能券成功，用户编号为：{}，订单编号为：{}",currentUserId,orderNo);
        return true;
    }

    /**
     * 获取我的万能券 信息
     *
     * @param currentUserId 当前用户的ID
     * @return
     */
    @Override
    public AnswerMaster getMyLightenCoupon(String currentUserId) {
        String key = Common.KEY_SPECIAL+currentUserId;
        String item = "getMyLightenCoupon";
        if(redis.hexists(key,item)){
            return (AnswerMaster) redis.getHashCache(key,item);
        }
        AnswerMaster answerMaster = super.getOne(new LambdaQueryWrapper<AnswerMaster>()
                .eq(AnswerMaster::getUserId,currentUserId)
                .eq(AnswerMaster::getEnable,Common.ENABLE),true);
        if (null == answerMaster) {
            answerMaster = new AnswerMaster();
        }
        if(!redis.setHashCache(key,item,answerMaster,Common.EXC_REDIS)){
            log.warn("[答题服务]=========>万能券信息放入缓存失败！");
        }
        return answerMaster;
    }

    /**
     * 增加 万能卷激活数量和 答题数量
     *
     * @param masterId
     * @param currentUserId 当前用户的ID
     * @return
     */
    @Override
    @Transactional
    public Object addCount(Long masterId,String currentUserId) {
        if(answerMasterMapper.addCount(masterId) != 1)
            throw new CustomException(ExecptionEnum.ADD_SUBJECT_NUMBER_FAIL);
        AnswerMaster answerMaster = answerMasterMapper.selectById(masterId);
        Map<String,Object> map = new HashMap<>();
        map.put("orderNo",answerMaster.getOrderNo());
        map.put("userNo",currentUserId);
        map.put("subjectIndex",answerMaster.getCouponCount());
        ResponseInfo<Object> res = volumeMarketFeign.activationCoupon(new JSONObject(map));
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_LOG_SHOW,res);
        redis.clear(Common.KEY_SPECIAL+currentUserId);
        log.info("[答题服务]==========>增加答题进度通知券集市成功！用户编号为：{}，订单编号为：{}，激活的万能券数量为：{}",currentUserId,answerMaster.getOrderNo(),answerMaster.getCouponCount());
        return res.getMessage();
    }
}
