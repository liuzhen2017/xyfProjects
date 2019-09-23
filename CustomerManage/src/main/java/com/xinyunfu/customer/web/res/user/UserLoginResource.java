package com.xinyunfu.customer.web.res.user;

import com.xinyunfu.customer.config.CustomerVerifyCodeConfig;
import com.xinyunfu.customer.constant.LogTypeConstant;
import com.xinyunfu.customer.domain.log.CustLogDTO;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.customer.dto.user.UserLoginDTO;
import com.xinyunfu.customer.dto.user.UserRegisterDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.checktoken.AllCheckTokenService;
import com.xinyunfu.customer.service.log.CustomerLogService;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisStringService;
import com.xinyunfu.customer.service.user.UserLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.xinyunfu.customer.constant.CommonConstants.header_ip;
import static com.xinyunfu.customer.exception.CustomerExceptionEnum.COMMON_CHECK_TOKEN_ERROR;
import static com.xinyunfu.customer.exception.CustomerExceptionEnum.USER_OFTEN_LOGIN;

/**
 * 注册、登陆相关
 */
@Slf4j
@RestController
@RequestMapping("/customer/user")
public class UserLoginResource {

    @Autowired private UserLoginService userLoginService;
    @Autowired private CustomerLogService customerLogService;
    @Autowired private CustomerVerifyCodeConfig customerVerifyCodeConfig;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private AllCheckTokenService allCheckTokenService;

    /**
     * 登陆
     */
    @PostMapping("/login")
    public UserInfoDTO login(@Validated@RequestBody UserLoginDTO userLogin, @RequestHeader(header_ip)String ip) throws Exception {
        log.info("REST request to login. user login : {}", userLogin);

        //redis记录登陆，登陆成功清除
        String redisKey = RedisKeyRules.loginOften(userLogin.getPhone());
        Integer loginTime = 0;
        if(redisCommonService.exists(redisKey)){
            if((loginTime = (Integer) redisStringService.get(redisKey)) > 5) throw new CustomerException(USER_OFTEN_LOGIN);
            redisCommonService.delete(redisKey);
        }
        redisStringService.add(redisKey, loginTime + 1);

        UserInfoDTO userInfo;
        if(Objects.nonNull(userLogin.getPassword())) {   //手机号密码登陆
            log.info("login with password");
            //校验前端图形验证码
            if(!allCheckTokenService.checkToken(userLogin.getToken(), userLogin.getTokenType(), ip))
                throw new CustomerException(COMMON_CHECK_TOKEN_ERROR);

            userInfo = userLoginService.checkLoginInfo(userLogin.getPhone(), userLogin.getPassword());
        } else {            //短信登陆
            log.info("login with verify code");
            userInfo = userLoginService.checkPhoneLogin(userLogin.getPhone(), userLogin.getVerifyCode(), userLogin.getToken());
        }

        if(Objects.isNull(userInfo.getReferralCode())) userInfo.setReferralCode(customerVerifyCodeConfig.getDefaultCode());

        //异步记录用户登陆日志
        customerLogService.saveLog(userInfo.getUserNo(), LogTypeConstant.USER_LOGIN_IN,
                String.format(LogTypeConstant.temp_2, userInfo.getUserNo()));

        //登陆成功清除记录的登陆redis
        redisCommonService.delete(redisKey);

        log.info("Login success! id: {}, nickName: {}", userInfo.getId(), userInfo.getNickName());
        return userInfo;
    }

    /**
     * 第三方登陆
     */
    @PostMapping("/third-party/login")
    public void thirdPartyLogin(){
        // TODO: 2019/7/8 第三方登陆
    }

    /**
     * 注册<br/>
     * 注册完成之后立即登陆
     */
    @PostMapping("/register")
    public UserInfoDTO register(@Validated@RequestBody UserRegisterDTO register) throws CustomerException {
        log.info("REST request to register. reg : {}", register);
        if(Objects.nonNull(register.getUserCode())) {
            register.setUserCode(register.getUserCode().toUpperCase());//转大写
            if(register.getUserCode().trim().equals(""))//空字符串判断为null
                register.setUserCode(null);
        }
        CustUserDTO user = userLoginService.register(register);
        UserInfoDTO userInfo = new UserInfoDTO(user, true);
        userInfo.setFirstLogin(true);
        if(userInfo.getFirstLogin()) redisStringService.add(RedisKeyRules.userFirstLogin(userInfo.getUserNo()), true);
        log.info("REST request register. success! userInfo: {}", userInfo);
        return userInfo;
    }
}
