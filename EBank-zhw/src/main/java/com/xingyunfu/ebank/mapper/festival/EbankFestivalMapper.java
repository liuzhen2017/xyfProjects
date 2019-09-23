package com.xingyunfu.ebank.mapper.festival;

import com.xingyunfu.ebank.domain.festival.EbankFestivalDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface EbankFestivalMapper {
    EbankFestivalDTO findByYearAndMonthAndDay(Map<String, Integer> map);
}
