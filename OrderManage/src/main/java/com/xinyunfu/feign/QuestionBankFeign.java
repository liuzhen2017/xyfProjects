package com.xinyunfu.feign;

import com.xinyunfu.jace.utils.ResponseInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author XRZ
 * @date 2019/7/24
 * @Description : 趣味答题
 */
@FeignClient("QuestionBank")
public interface QuestionBankFeign {

    @GetMapping("/answer/CreateAnswer/{currentUserId}")
    ResponseInfo<String> CreateAnswer(@PathVariable("currentUserId") String currentUserId,
                                      @RequestParam("orderNo") String orderNo);

}
