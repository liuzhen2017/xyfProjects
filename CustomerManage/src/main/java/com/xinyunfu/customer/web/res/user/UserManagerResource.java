package com.xinyunfu.customer.web.res.user;

import com.xinyunfu.customer.config.CustomerVerifyCodeConfig;
import com.xinyunfu.customer.dto.PageDTO;
import com.xinyunfu.customer.dto.PageQueryDTO;
import com.xinyunfu.customer.dto.user.*;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.user.UserManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Collectors;

import static com.xinyunfu.customer.constant.CommonConstants.header_uid;
import static com.xinyunfu.customer.exception.CustomerExceptionEnum.USER_LOGIN_NOUSER;

/**
 * 外部接口，用户管理相关
 */
@Slf4j
@RestController
@RequestMapping("/customer/user")
public class UserManagerResource {

    @Autowired private UserManagerService userManagerService;
    @Autowired private CustomerVerifyCodeConfig customerVerifyCodeConfig;

    /**
     * 获取完整用户信息
     */
    @PostMapping("/info")
    public UserInfoDTO userInfo(@RequestHeader(header_uid) Long userNo){
        log.info("REST request to get user info. userNo : {}", userNo);
        UserInfoDTO userInfo = new UserInfoDTO(userManagerService.findExByUserNo(userNo), true);
        if(Objects.isNull(userInfo.getReferralCode())) userInfo.setReferralCode(customerVerifyCodeConfig.getDefaultCode());
        log.info("REST request to get user info. success! userNo: {}", userInfo.getUserNo());
        return userInfo;
    }

    /**
     * 修改用户信息 -- 昵称、微信号、性别
     */
    @PostMapping("/info/change")
    public void userInfo(@RequestHeader(header_uid) Long userNo, @RequestBody UserChangeDTO userChange){
        log.info("REST request to change user info. userNo: {}, userChange: {}", userNo, userChange);
        userManagerService.changeUserInfo(userNo, userChange);
        log.info("REST request to change user info. success!");
    }

    /**
     * 第一次设置支付密码<br/>
     * 根据旧支付密码，设置新支付密码
     */
    @PostMapping("/paypassword/reset")
    public void resetPayPassword(@RequestHeader(header_uid) Long userNo, @RequestBody UserResetPasswordDTO resetPayPassword) throws CustomerException {
        log.info("REST request to reset pay password. userNo: {}, resetPayPassword: {}", userNo, resetPayPassword);
        userManagerService.payPassword(userNo, resetPayPassword);
        log.info("REST request to rest pay password. success!");
    }

    /**
     * 校验支付密码
     */
    @PostMapping("/paypassword/check")
    public void checkPayPassword(@RequestHeader(header_uid)Long userNo, @RequestBody UserPayPasswordCheckDTO check) throws CustomerException {
        log.info("REST request to check pay password. userNo: {}", userNo);
        userManagerService.checkPayPassword(userNo, check.getPayPassword());
        log.info("REST request to check pay password. success!");
    }

    /**
     * 根据短信验证码重新设置支付密码
     */
    @PostMapping("/paypassword/code")
    public void resetPayPassword(@RequestHeader(header_uid)Long userNo, @RequestBody UserResetVerifyDTO userResetPayVerify) throws CustomerException {
        log.info("REST request to reset pay password. userNo: {}, userResetPayVerify: {}", userNo, userResetPayVerify);
        userManagerService.payPassword(userNo, userResetPayVerify);
        log.info("REST request to rest pay password. success!");
    }

    /**
     * 根据旧密码修改登陆密码
     */
    @PostMapping("/password/reset")
    public void resetPassword(@RequestHeader(header_uid)Long userNo, @RequestBody UserResetPasswordDTO resetPassword) throws CustomerException {
        log.info("REST request to reset login password. userNo: {}, resetPassword: {}", userNo, resetPassword);
        userManagerService.password(userNo, resetPassword);
        log.info("REST request to rest password. success!");
    }

    /**
     * 根据短信验证码重新设置登陆密码
     */
    @PostMapping("/password/code")
    public void resetPassword(@RequestBody UserResetVerifyDTO userResetVerify) throws CustomerException {
        log.info("REST request to reset login password. userResetVerify: {}", userResetVerify);
        if(Objects.isNull(userResetVerify.getPhone()) || userResetVerify.getPhone().trim().equals(""))
            throw new CustomerException(USER_LOGIN_NOUSER);
        userManagerService.password(userResetVerify);
        log.info("REST request to rest password. success!");
    }

    /**
     * 获得当前推荐人的子推荐人
     */
    @PostMapping("/referrer")
    public PageDTO<UserInfoDTO> referrer(@RequestHeader(header_uid) Long userNo, @RequestBody@Validated PageQueryDTO query){
        log.info("REST request to query referrer user info. userNo: {}, query: {}", userNo, query);
        PageDTO<CustUserWithExtensionDTO> page = userManagerService.referrer(userNo, query);
        PageDTO<UserInfoDTO> userPage = new PageDTO(page);
        userPage.setDataInfo(page.getDataInfo().stream().map(var -> new UserInfoDTO(var, true)).collect(Collectors.toList()));
        log.info("REST request to query referrer user info. success! user number: {}", page.getTotalCount());
        return userPage;
    }

    /**
     * 获得当前推荐人的子推荐人套餐信息
     */
    @PostMapping("/referrer/info")
    public UserReferrerInfoDTO userReferrerInfo(@RequestHeader(header_uid) Long userNo){
        log.info("REST request to get user referrer info. userNo: {}", userNo);
        UserReferrerInfoDTO referrerInfo = userManagerService.userReferrerInfo(userNo);
        if(Objects.isNull(referrerInfo.getTotalMealNu())) referrerInfo.setTotalMealNu(0);
        log.info("REST request to get user referrer info. success!");
        return referrerInfo;
    }

    /**
     * 获取上级推荐人的信息
     */
    @PostMapping("/referrer/parent")
    public UserInfoDTO referrer(@RequestHeader(header_uid)Long userNo){
        log.info("REST request to get referrer user info. userNo: {}", userNo);
        CustUserWithExtensionDTO currentUser = userManagerService.findExByUserNo(userNo);
        UserInfoDTO parentUserInfo = null;
        if(Objects.nonNull(currentUser.getReferrerNo())){
            parentUserInfo = new UserInfoDTO(userManagerService.findExByUserNo(currentUser.getReferrerNo()), true);
            if(Objects.isNull(parentUserInfo.getReferralCode()))
                parentUserInfo.setReferralCode(customerVerifyCodeConfig.getDefaultCode());
        }
        log.info("REST request to get referrer user  info. success! parentUserInfo not null: {}", Objects.nonNull(parentUserInfo));
        return parentUserInfo;
    }
}
