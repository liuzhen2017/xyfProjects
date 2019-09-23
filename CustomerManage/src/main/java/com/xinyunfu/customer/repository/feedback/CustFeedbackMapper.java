package com.xinyunfu.customer.repository.feedback;

import com.xinyunfu.customer.domain.feedback.CustFeedbackDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustFeedbackMapper {
    void insert(CustFeedbackDTO custFeedback);
    List<Long> findTodayFeedBack(Map<String, Object> map);
}
