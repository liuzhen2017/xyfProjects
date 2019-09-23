package com.xinyunfu.product.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = "InfoCenter_startSecKill")
public class RecieveMsg {

//    @Autowired
//    private SeckillService seckillService;
//    @RabbitHandler
//    public void startSeckill(){
//        log.info("=============接收到开始秒杀请求=============");
//        seckillService.updateKillStatus();
//
//    }


}
