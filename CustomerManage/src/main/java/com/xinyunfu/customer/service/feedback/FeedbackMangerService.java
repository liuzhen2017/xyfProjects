package com.xinyunfu.customer.service.feedback;
import com.xinyunfu.customer.domain.feedback.CustFeedbackDTO;
import com.xinyunfu.customer.dto.feedback.FeedbackAddDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.exception.CustomerExceptionEnum;
import com.xinyunfu.customer.repository.feedback.CustFeedbackMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class FeedbackMangerService {
    @Autowired private CustFeedbackMapper custFeedbackMapper;

    public void addFeedback(Long userNo, FeedbackAddDTO feedbackAdd) throws CustomerException {
        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime rightTime = ZonedDateTime.of(currentTime.getYear(), currentTime.getMonthValue(), currentTime.getDayOfMonth(), 0,
                0, 0, 0, currentTime.getZone());
        Map<String, Object> map = new HashMap<>();
        map.put("userNo", userNo);
        map.put("startTime", Long.valueOf(rightTime.toInstant().toEpochMilli()/1000));
        List<Long> feedbackList = custFeedbackMapper.findTodayFeedBack(map);
        //每个用户一天只能添加1个反馈
        if(Objects.nonNull(feedbackList) && feedbackList.size()>= 1)
            throw new CustomerException(CustomerExceptionEnum.FEEDBACK_ERROR);
        custFeedbackMapper.insert(new CustFeedbackDTO(userNo, feedbackAdd));
    }
}
