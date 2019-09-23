package com.xinyunfu.customer.service.user;

import com.xinyunfu.customer.config.CustomerVerifyCodeConfig;
import com.xinyunfu.customer.constant.CommonConstants;
import com.xinyunfu.customer.constant.UserConstants;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.dto.ebank.AccountAddDTO;
import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.customer.dto.user.UserRegisterDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.exception.CustomerExceptionEnum;
import com.xinyunfu.customer.feigin.EbankFeign;
import com.xinyunfu.customer.feigin.VolumeMarket;
import com.xinyunfu.customer.feigin.WalletAcctManageFeign;
import com.xinyunfu.customer.repository.user.CustUserMapper;
import com.xinyunfu.customer.service.common.CheckTokenVerifyCodeService;
import com.xinyunfu.customer.service.rabbitmq.VerifyCode;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisStringService;
import com.xinyunfu.customer.utils.EncryptionTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserLoginService {

    @Autowired private CustUserMapper custUserMapper;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private ReferralCodeService referralCodeService;
    @Autowired private VerifyCode verifyCode;
    @Autowired private VolumeMarket volumeMarket;
    @Autowired private EbankFeign ebankFeign;
    @Autowired private CustomerVerifyCodeConfig customerVerifyCodeConfig;
    @Autowired private RabbitTemplate rabbitTemplate;
    @Autowired private CheckTokenVerifyCodeService checkTokenVerifyCodeService;
    @Autowired private WalletAcctManageFeign walletAcctManageFeign;

    /**
     * 校验用户名和密码
     */
    public UserInfoDTO checkLoginInfo(String phone, String password) throws CustomerException {

        CustUserDTO user = custUserMapper.findByPhone(phone);

        //用户不存在
        if(Objects.isNull(user)) throw new CustomerException(CustomerExceptionEnum.USER_LOGIN_NOUSER);
        //校验密码
        if(!EncryptionTools.md5(password, UserConstants.PASSWORD_SALT).equals(user.getPassword()))
            throw new CustomerException(CustomerExceptionEnum.USER_LOGIN_PASSWORD);

        return new UserInfoDTO(user, true);
    }

    /**
     * 短信登陆校验
     */
    public UserInfoDTO checkPhoneLogin(String phone, String verifyCode, String token) throws CustomerException {

        //判断token是否在redis中存在
        checkTokenVerifyCodeService.checkToken(CommonConstants.LOGIN, phone, token);

        //验证码错误
        if(!this.verifyCode.check(phone, verifyCode, CommonConstants.REGISTER))
                throw new CustomerException(CustomerExceptionEnum.USER_LOGIN_VERIFY_CODE);

        //验证码校验通过
        CustUserDTO user = custUserMapper.findByPhone(phone);
        if(Objects.isNull(user)) throw new CustomerException(CustomerExceptionEnum.USER_LOGIN_NOUSER);

        return new UserInfoDTO(user, true);
    }

    /**
     * 注册，创建用户
     */
    public CustUserDTO register(UserRegisterDTO register) throws CustomerException {

        //判断token是否在redis中存在
        checkTokenVerifyCodeService.checkToken(CommonConstants.REGISTER, register.getPhone(), register.getToken());

        //判断用户是否已存在
        if(Objects.nonNull(custUserMapper.findByPhone(register.getPhone()))) throw new CustomerException(CustomerExceptionEnum.USER_LOGIN_HASUSER);
        //校验验证码
        if(!verifyCode.check(register.getPhone(), register.getVerifyCode(), CommonConstants.REGISTER))
                throw new CustomerException(CustomerExceptionEnum.USER_LOGIN_VERIFY_CODE);
        CustUserDTO refUser = null;
        if(!register.getUserCode().equals(customerVerifyCodeConfig.getDefaultCode())){
            log.info("create user, not default user code!!!");
            //判断推荐码是否有效
            refUser = custUserMapper.findByUserCode(register.getUserCode());
            log.debug("ref user: {}", refUser);
            if(Objects.isNull(refUser))
                throw new CustomerException(CustomerExceptionEnum.USER_REGISTER_CODE);
        }

        //验证码校验通过，创建用户
        String currentUserCode = referralCodeService.createCodeWithCheck().toUpperCase();
        CustUserDTO user = new CustUserDTO(register, refUser, currentUserCode);
        custUserMapper.add(user);

        this.asyncNotice(user);
        return user;
    }

    @Async
    public void asyncNotice(CustUserDTO user) {
        //通知券集市
        volumeMarket.registGiveCoupon(user.getUserNo(), user.getReferrerNo());
        //通知ebank
        ebankFeign.addAccount(new AccountAddDTO(user));
        //通知wallet
        walletAcctManageFeign.createAccount(user.getUserNo()+"", user.getNickName(), "U01", "T01");
    }
}
