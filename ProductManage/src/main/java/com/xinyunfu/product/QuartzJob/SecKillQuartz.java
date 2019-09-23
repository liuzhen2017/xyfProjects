package com.xinyunfu.product.QuartzJob;

import com.xinyunfu.product.mapper.ProChannelMapper;
import com.xinyunfu.product.mapper.ProductMapper;
import com.xinyunfu.product.service.IChannelService;
import com.xinyunfu.product.service.IProductService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class SecKillQuartz extends QuartzJobBean {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private ProChannelMapper channelMapper;
    @Autowired
    private IChannelService channelService;

    @Override
    protected  void  executeInternal(JobExecutionContext context) throws JobExecutionException {
//        Calendar cal=Calendar.getInstance();
//        int hour=cal.get(Calendar.HOUR_OF_DAY);
//
//        if (hour==22){
//            int hour2=hour-2;
//            String channelName=hour2+":00";
//            productService.updateProductByChannelName(channelName,0);
//        }
//        if (hour==10){
//            String channelName=hour+":00";
//            productService.updateProductByChannelName(channelName,1);
//        }
//        else {
//            int hour2=hour-2;
//            String channelName1=hour+":00";
//            String channelName2=hour2+":00";
//            productService.updateProductByChannelName(channelName1,1);
//            productService.updateProductByChannelName(channelName2,0);
//        }
    }





















}
