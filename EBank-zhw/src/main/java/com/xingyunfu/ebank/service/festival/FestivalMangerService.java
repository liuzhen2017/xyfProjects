package com.xingyunfu.ebank.service.festival;

import com.xingyunfu.ebank.mapper.festival.EbankFestivalMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Slf4j
@Service
public class FestivalMangerService {

    @Autowired private EbankFestivalMapper ebankFestivalMapper;

    /**
     * 判断当天是否是节假日
     */
    public Boolean festival(Integer year, Integer month, Integer day){
        return Objects.nonNull(ebankFestivalMapper.findByYearAndMonthAndDay(new HashMap<String, Integer>(){{
            put("years", year);put("months", month);put("days", day);
        }}));
    }
}
