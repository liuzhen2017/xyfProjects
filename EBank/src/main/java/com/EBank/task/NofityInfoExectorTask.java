package com.EBank.task;

import com.EBank.entity.AccFlow;
import com.EBank.entity.NotifyInfo;
import com.EBank.feign.OrderManage;
import com.EBank.mapper.NofityInfoMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 定时任务
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/13
 */
@Service
@Slf4j
public class NofityInfoExectorTask extends QuartzJobBean {

    @Autowired
    NofityInfoMapper nofityInfoMapper;
    @Autowired
    private static ApplicationContext applicationContext;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        NotifyInfo info =new NotifyInfo();
        LambdaQueryWrapper<NotifyInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(NotifyInfo::getNotifyStatus, info.getNotifyStatus());
        IPage<NotifyInfo> iPage =null;
        Page<NotifyInfo> pages = new Page<NotifyInfo>(1,100);
        iPage= nofityInfoMapper.selectPage(pages,queryWrapper);
        log.info("============begin query NotifyInfo ==,list={}============",iPage.getRecords().size());
        while (iPage.getCurrent() > iPage.getTotal()){
            pages = new Page<NotifyInfo>(iPage.getCurrent()+1,100);
            iPage= nofityInfoMapper.selectPage(pages,queryWrapper);
            List<NotifyInfo> list =iPage.getRecords();
            if(!list.isEmpty()){
                //创建对象
                list.forEach(notifyInfo -> {
                    String clazzName= notifyInfo.getNotifyClass();
                    String method =notifyInfo.getNotifyMethod();
                    String parmat =notifyInfo.getNotifyParmat();
//                     Object obj =  applicationContext.getBean(clazzName);

                    try {
                        log.info("==========begin created Class ,name ={},method ={},parmat ={}===============",clazzName,method,parmat);
                        Class clazz= Class.forName(clazzName);
                        Method[] methods= clazz.getMethods();
                        for(Method m :methods){
                            if(m.getName().equals(method)){
                                log.info("=======find Meth,begin invoke====");
                                m.invoke(parmat);
                            }
                        }
                        //修改为失效
                        notifyInfo.setNotifyStatus("0");
                        nofityInfoMapper.updateById(notifyInfo);
                    }catch (Exception e){
                        log.error("=========执行错误!=========e={},msg={}",e,e.getMessage());
                    }

                });
            }
        }


    }
}
