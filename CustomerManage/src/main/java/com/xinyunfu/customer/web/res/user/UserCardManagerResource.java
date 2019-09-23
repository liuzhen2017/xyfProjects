package com.xinyunfu.customer.web.res.user;

import com.xinyunfu.customer.dto.user.UserCardCheckDTO;
import com.xinyunfu.customer.dto.user.UserCardCheckResultDTO;
import com.xinyunfu.customer.dto.user.UserCardImageURL;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.user.UserCardManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.xinyunfu.customer.constant.CommonConstants.header_uid;

@RestController
@Slf4j
@RequestMapping("/customer/card")
public class UserCardManagerResource {

    @Autowired private UserCardManagerService userCardManagerService;

    /**
     * 身份证图片上传
     */
    @PostMapping("/images")
    public void image(@RequestHeader(header_uid)Long userNo, @RequestBody UserCardImageURL imageURL){
        log.info("REST request to add image. userNo: {}, imageURL: {}", userNo, imageURL);
        userCardManagerService.image(userNo, imageURL);
        log.info("REST request to add image. success!");
    }

    /**
     * 身份证校验，触发校验后，保存该数据
     */
    @PostMapping("/auth")
    public UserCardCheckResultDTO userAuth(@RequestHeader(header_uid)Long userNo,
                                           @Validated @RequestBody UserCardCheckDTO cardCheck) throws CustomerException {
        log.info("REST request to user auth. userNo: {}, cardCheck: {}", userNo, cardCheck);
        UserCardCheckResultDTO result = userCardManagerService.userAuth(userNo, cardCheck);
        log.info("REST request to user auth. success! result: {}", result);
        return result;
    }
}
