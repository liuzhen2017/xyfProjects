package com.xinyunfu.web;


import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.model.Time;
import com.xinyunfu.service.ITimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 时间限制表 前端控制器
 * </p>
 *
 * @author Xurongze
 * @since 2019-08-19
 */
@RestController
@RequestMapping("/time")
public class TimeController {

    @Autowired
    private ITimeService iTimeService;


    @PostMapping("/changeTimeOut")
    public ResponseInfo<Object> changeTimeOut(@RequestBody Time time){
        iTimeService.updateTime(time);
        return ResponseInfo.success(null);
    }

}
