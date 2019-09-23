package com.xinyunfu.customer.service.user;

import com.xinyunfu.customer.constant.CommonConstants;
import com.xinyunfu.customer.constant.InnerAccountConstant;
import com.xinyunfu.customer.constant.UserConstants;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.dto.PageDTO;
import com.xinyunfu.customer.dto.PageQueryDTO;
import com.xinyunfu.customer.dto.PageQueryInnerDTO;
import com.xinyunfu.customer.dto.ebank.AccountUpdateDTO;
import com.xinyunfu.customer.dto.user.*;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.exception.CustomerExceptionEnum;
import com.xinyunfu.customer.feigin.EbankFeign;
import com.xinyunfu.customer.repository.user.CustUserMapper;
import com.xinyunfu.customer.service.common.CheckTokenVerifyCodeService;
import com.xinyunfu.customer.service.rabbitmq.VerifyCode;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisStringService;
import com.xinyunfu.customer.utils.EncryptionTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.xinyunfu.customer.exception.CustomerExceptionEnum.USER_LOGIN_NOUSER;

@Slf4j
@Service
public class UserManagerService {
    @Autowired private CustUserMapper custUserMapper;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private VerifyCode verifyCode;
    @Autowired private CheckTokenVerifyCodeService checkTokenVerifyCodeService;
    @Autowired private EbankFeign ebankFeign;
    @Autowired private UserExtensionMangerService userExtensionMangerService;

    public CustUserWithExtensionDTO findExByUserNo(Long userNo){
        return new CustUserWithExtensionDTO(this.findByUserNo(userNo), userExtensionMangerService.findByUserNo(userNo));
    }

    public CustUserDTO findByUserNo(Long userNo){

        //默认从缓存中取
        String redisKey = RedisKeyRules.userInfo(userNo);
        if(redisCommonService.exists(redisKey)){
            return (CustUserDTO) redisStringService.get(redisKey);
        }

        CustUserDTO user = custUserMapper.findByUserNo(userNo);

        //同步缓存
        redisStringService.add(redisKey, user);
        return user;
    }

    public void changeUserInfo(Long userNo, UserChangeDTO userChange){
        CustUserDTO user = new CustUserDTO();
        user.setUserNo(userNo);
        user.setNickName(userChange.getNickName());
        user.setWechat(userChange.getWechat());
        user.setSex(userChange.getSex());
        user.setPhotoUrl(userChange.getPhotoUrl());

        this.updateUserInfo(user);
    }

    public void changeUserLevel(Long userNo, Integer level){

        //查询券集市，判断用户是否有未消耗完的券

        CustUserDTO user = new CustUserDTO();
        user.setUserNo(userNo);
        user.setLevel(level);
        this.updateUserInfo(user);

        //通知ebank修改用户级别
        AccountUpdateDTO accountUpdate = new AccountUpdateDTO();
        accountUpdate.setUserNo(userNo);
        accountUpdate.setAccountType(!level.equals(InnerAccountConstant.level_1)?
                        InnerAccountConstant.accountType_common: InnerAccountConstant.accountType_ambassador);
        ebankFeign.updateAccount(accountUpdate);
    }

    public void updateUserInfo(CustUserDTO user){
        custUserMapper.update(user);
        redisCommonService.delete(RedisKeyRules.userInfo(user.getUserNo()));
    }

    /** 根据旧支付密码重置新支付密码 */
    public void payPassword(Long userNo, UserResetPasswordDTO resetPayPassword) throws CustomerException {
        //预检查
        if(!resetPayPassword.getNewPassword().equals(resetPayPassword.getConfirmPassword()))//确认密码是否一致
            throw new CustomerException(CustomerExceptionEnum.USER_PASSWORD_CONFIRM);
        if(Objects.nonNull(resetPayPassword.getOldPassword()) &&
                resetPayPassword.getNewPassword().equals(resetPayPassword.getOldPassword()))  //判断新旧密码是否相同
            throw new CustomerException(CustomerExceptionEnum.USER_PAY_PASSWORD_SAME);

        CustUserDTO user = custUserMapper.findByUserNo(userNo);

        //校验旧密码
        String oldSaltPayPassword = EncryptionTools.md5(resetPayPassword.getOldPassword(), UserConstants.PASSWORD_SALT);
        if(Objects.nonNull(user.getPayPassword()) && !user.getPayPassword().equals(oldSaltPayPassword)){
            throw new CustomerException(CustomerExceptionEnum.USER_PAY_PASSWORD_OLD);
        }

        //更新密码
        user.setPayPassword(EncryptionTools.md5(resetPayPassword.getNewPassword(), UserConstants.PASSWORD_SALT));
        this.updateUserInfo(user);
    }

    /** 根据验证码重置支付密码 */
    public void payPassword(Long userNo, UserResetVerifyDTO userResetPayVerify) throws CustomerException {

        if(Objects.nonNull(userResetPayVerify.getToken())) {
            checkTokenVerifyCodeService.checkToken(CommonConstants.RESET_PAY_PASSWORD,
                    userResetPayVerify.getPhone(), userResetPayVerify.getToken());
        }

        //预检查
        if(!userResetPayVerify.getNewPassword().equals(userResetPayVerify.getConfirmPassword()))
            throw new CustomerException(CustomerExceptionEnum.USER_PASSWORD_CONFIRM);

        CustUserDTO user = custUserMapper.findByUserNo(userNo);

        //校验验证码
        if(!verifyCode.check(user.getPhone(), userResetPayVerify.getVerifyCode(), CommonConstants.RESET_PAY_PASSWORD))
            throw new CustomerException(CustomerExceptionEnum.USER_LOGIN_VERIFY_CODE);

        //更新密码
        user.setPayPassword(EncryptionTools.md5(userResetPayVerify.getNewPassword(), UserConstants.PASSWORD_SALT));
        this.updateUserInfo(user);
    }

    public void checkPayPassword(Long userNo, String  payPassword) throws CustomerException {
        CustUserDTO user = custUserMapper.findByUserNo(userNo);
        String md5PayPassword = EncryptionTools.md5(payPassword, UserConstants.PASSWORD_SALT);
        if(!md5PayPassword.equals(user.getPayPassword()))
            throw new CustomerException(CustomerExceptionEnum.USER_PAY_PASSWORD_ERROR);
    }

    /**
     * 根据旧登陆密码重置新登陆密码，不存在登陆密码则不校验登陆密码
     */
    public void password(Long userNo, UserResetPasswordDTO userResetPassword) throws CustomerException {

        CustUserDTO user = custUserMapper.findByUserNo(userNo);

        //预检查
        if(!userResetPassword.getNewPassword().equals(userResetPassword.getConfirmPassword()))
            throw new CustomerException(CustomerExceptionEnum.USER_PASSWORD_CONFIRM);

        //校验旧密码
        if(Objects.nonNull(user.getPassword())) {
            String oldSaltPassword = EncryptionTools.md5(userResetPassword.getOldPassword(), UserConstants.PASSWORD_SALT);
            if (!user.getPassword().equals(oldSaltPassword)) {
                throw new CustomerException(CustomerExceptionEnum.USER_PAY_PASSWORD_OLD);
            }
        }

        //更新密码
        user.setPassword(EncryptionTools.md5(userResetPassword.getNewPassword(), UserConstants.PASSWORD_SALT));
        this.updateUserInfo(user);
    }

    /** 根据验证码重置登陆密码 */
    public void password(UserResetVerifyDTO userResetVerify) throws CustomerException {

        //预检查
        if(!userResetVerify.getNewPassword().equals(userResetVerify.getConfirmPassword()))
            throw new CustomerException(CustomerExceptionEnum.USER_PASSWORD_CONFIRM);

        checkTokenVerifyCodeService.checkToken(CommonConstants.RESET_PASSWORD,
                userResetVerify.getPhone(), userResetVerify.getToken());

        CustUserDTO user = custUserMapper.findByPhone(userResetVerify.getPhone());
        if(Objects.isNull(user))
            throw new CustomerException(USER_LOGIN_NOUSER);
        //校验验证码
        if(!verifyCode.check(user.getPhone(), userResetVerify.getVerifyCode(), CommonConstants.RESET_PASSWORD))
            throw new CustomerException(CustomerExceptionEnum.USER_LOGIN_VERIFY_CODE);

        //更新密码
        user.setPassword(EncryptionTools.md5(userResetVerify.getNewPassword(), UserConstants.PASSWORD_SALT));
        this.updateUserInfo(user);
    }

    /**
     * 判断用户是否有两个子推荐人
     */
    public Boolean relation(Long userNo){
        String redisKey = RedisKeyRules.userHaveTwoSon(userNo);
        Boolean result = redisCommonService.exists(redisKey)
                && (Boolean) redisStringService.get(redisKey);

        if(!result){
            List<Long> userList = custUserMapper.findIdByReferrerNo(userNo);
            if(result = Objects.nonNull(userList) && userList.size()>=2) redisStringService.add(redisKey, true);
        }
        return result;
    }

    /**
     * 获得当前推荐人的子推荐人
     */
    public PageDTO<CustUserWithExtensionDTO> referrer(Long userNo, PageQueryDTO query){

        List<Long> userNoList = null;
        Long totalCount = custUserMapper.findReferrerTotal(userNo);
        if(totalCount > 0) {
            userNoList = custUserMapper.findUserNoByReferrerNo(new HashMap<String, Object>(){{
                put("referrerNo", userNo);
                put("pageNo", query.getPageSize()*query.getPageNo());
                put("pageSize", query.getPageSize());
            }});
        }

        PageDTO<CustUserWithExtensionDTO> page = new PageDTO(query);
        page.setTotalCount(totalCount);
        page.setDataInfo((Objects.isNull(userNoList)?new ArrayList<Long>(): userNoList)
                .stream().map(var -> this.findExByUserNo(var)).collect(Collectors.toList()));

        return page;
    }

    /**
     * 获得当前推荐人的子推荐人套餐信息
     */
    public UserReferrerInfoDTO userReferrerInfo(Long userNo){
        return userExtensionMangerService.referrerTotalMeal(userNo);
    }

    /**
     * 总后台分页查询用户
     */
    public PageQueryInnerDTO<CustUserWithExtensionDTO> pageQuery(UserPageQueryDTO pageQuery){

        Integer pageNo = pageQuery.getPageNo();
        Integer pageSize = pageQuery.getPageSize();

        pageQuery.setPageNo((pageNo-1)*pageSize);

        return new PageQueryInnerDTO<>(pageNo, pageSize, custUserMapper.totalPage(pageQuery),
                custUserMapper.findUserNoPage(pageQuery).stream()
                        .map(var -> this.findExByUserNo(var)).collect(Collectors.toList()));
    }

    public List<CustUserDTO> findByPhoneList(UserPhoneListDTO phone){
        return custUserMapper.findByPhoneList(phone);
    }
}
