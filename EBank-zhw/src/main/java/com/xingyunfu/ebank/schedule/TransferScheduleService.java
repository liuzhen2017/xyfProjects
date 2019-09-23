package com.xingyunfu.ebank.schedule;

import com.xingyunfu.ebank.constant.TransferConstant;
import com.xingyunfu.ebank.service.festival.FestivalMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

@Slf4j
@Service
public class TransferScheduleService {

    @Autowired private FestivalMangerService festivalMangerService;

    public Long start(ZonedDateTime currentTime_8, Map<Integer, Integer> rightHour){
        //周六、周日不执行
        Integer dayOfWeek = currentTime_8.getDayOfWeek().getValue();
        if(dayOfWeek.equals(6) || dayOfWeek.equals(7)){
            log.warn("Today is {} day of week, do not start schedule!", dayOfWeek);
            return null;
        }

        Integer hour = currentTime_8.getHour();         //东八区小时数
        Integer min = currentTime_8.getMinute()/10*10;  //东八区分钟数
        log.info("System config start time: {}", TransferConstant.timeFun.apply(rightHour));
        log.info("Current time is {}:{}", hour, min==0?"00":min);
        if(!rightHour.containsKey(hour) || rightHour.get(hour)!=min){
            log.warn("Current time not in system config start time!");
            return null;
        }

        //节假日不执行
        Integer year = currentTime_8.getYear();
        Integer month = currentTime_8.getMonthValue();
        Integer dayOfMonth = currentTime_8.getDayOfMonth();
        log.info("Today is {}-{}-{}", year, month, dayOfMonth);
        if(festivalMangerService.festival(year, month, dayOfMonth)){
            log.warn("Today is festival, do not start schedule!");
            return null;
        }

        return ZonedDateTime.of(currentTime_8.getYear(), currentTime_8.getMonthValue(), currentTime_8.getDayOfMonth(),
                hour, min, 0, 0, ZoneId.systemDefault()).toInstant().toEpochMilli()/1000;
    }
}
