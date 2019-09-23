package com.xinyunfu.product.config;

import com.xinyunfu.product.QuartzJob.SecKillQuartz;
import com.xinyunfu.product.service.IProductService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

@Configuration
public class QuartzConfig {
    @Autowired
    private IProductService productService;

//    //定义任务详情
//    @Bean
//    public JobDetail secKillJobDetail() {
//        //指定job的名称和持久化保存任务
//        return JobBuilder
//                .newJob(SecKillQuartz.class)
//                .withIdentity("secKillQuartz")
//                .storeDurably()
//                .build();
//    }
    //定义触发器
//    @Bean
//    public Trigger secKillTrigger() {
//        CronScheduleBuilder scheduleBuilder
//                = CronScheduleBuilder.cronSchedule("0 0 10,12,14,16,18 * * ? ");
//        return TriggerBuilder.newTrigger()
//                             .forJob(secKillJobDetail())
//                             .withIdentity("secKillQuartz")
//                             .withSchedule(scheduleBuilder).build();
//    }

    @Scheduled(cron = "0 0 10,12,14,16,18,20,22 * * ?")
    public void domain(){
        java.util.Calendar cal= java.util.Calendar.getInstance();
        int hour=cal.get(Calendar.HOUR_OF_DAY);

        if (hour==22){
            int hour2=hour-2;
            String channelName=hour2+":00";
            productService.updateProductByChannelName(channelName,0);
        }
        if (hour==10){
            String channelName=hour+":00";
            productService.updateProductByChannelName(channelName,1);
        }
        else {
            int hour2=hour-2;
            String channelName1=hour+":00";
            String channelName2=hour2+":00";
            productService.updateProductByChannelName(channelName1,1);
            productService.updateProductByChannelName(channelName2,0);
        }
    }
}
