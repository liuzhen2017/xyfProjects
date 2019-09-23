package com.xinyunfu.customer.web.res.feedback;

import com.xinyunfu.customer.dto.feedback.FeedbackAddDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.feedback.FeedbackMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.xinyunfu.customer.constant.CommonConstants.header_uid;

@Slf4j
@RestController
@RequestMapping("/customer/feedback")
public class FeedbackMangerResource {

    @Autowired private FeedbackMangerService feedbackMangerService;

    /**
     * 添加反馈
     */
    @PostMapping("/add")
    public void addFeedback(@RequestHeader(header_uid)Long userNo, @Validated @RequestBody FeedbackAddDTO feedbackAdd) throws CustomerException {
        log.info("REST request to add feedback. userNo: {}", userNo);
        feedbackMangerService.addFeedback(userNo, feedbackAdd);
        log.info("REST request to add feedback. success!");
    }
}
