package com.xinyunfu.customer.service.user;

import com.xinyunfu.customer.constant.AuthInfoConstants;
import com.xinyunfu.customer.constant.UserConstants;
import com.xinyunfu.customer.domain.user.CustCardInfoDTO;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.dto.auth.IdCardAuthResultDTO;
import com.xinyunfu.customer.dto.user.UserCardCheckDTO;
import com.xinyunfu.customer.dto.user.UserCardCheckResultDTO;
import com.xinyunfu.customer.dto.user.UserCardImageURL;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.repository.user.CustCardInfoMapper;
import com.xinyunfu.customer.service.auth.IdCardAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

import static com.xinyunfu.customer.exception.CustomerExceptionEnum.*;

/**
 * 用户认证
 */
@Slf4j
@Service
public class UserCardManagerService {

    @Autowired
    private IdCardAuthService idCardAuthService;
    @Autowired
    private CustCardInfoMapper custCardInfoMapper;
    @Autowired
    private UserManagerService userManagerService;

    private static String SUCCESS_STATUS = "01";

    public void image(Long userNo, UserCardImageURL imageURL) {
        CustCardInfoDTO cardInfo = custCardInfoMapper.findByUserNo(userNo);

        if (Objects.nonNull(imageURL.getFrontPhoto())) cardInfo.setFrontPhoto(imageURL.getFrontPhoto());
        if (Objects.nonNull(imageURL.getReversePhoto())) cardInfo.setReversePhoto(imageURL.getReversePhoto());

        custCardInfoMapper.update(cardInfo);
    }

    /**
     * 校验身份证
     */
    public UserCardCheckResultDTO userAuth(Long userNo, UserCardCheckDTO cardCheck) throws CustomerException {
        //判断当前用户是否已实名验证过
        CustUserDTO user = userManagerService.findByUserNo(userNo);
        if (UserConstants.aStatus_1.equals(user.getAuthStatus()))
            throw new CustomerException(AUTH_ID_CARD_CONTAINS.setDataData(userNo, cardCheck.getCardNo()).setUserNo(userNo));

        //判断当前身份证号是否已实名验证过
        if (Objects.nonNull(custCardInfoMapper.findByCardNo(cardCheck.getCardNo())))
            throw new CustomerException(AUTH_ID_CARD_NO.setDataData(userNo, cardCheck.getCardNo()).setUserNo(userNo));

        //开始认证
        IdCardAuthResultDTO authResult = idCardAuthService.auth(cardCheck.getCardNo(), cardCheck.getUserName());
        log.debug("id card auth result: {}", authResult);

        if (!authResult.getStatus().equals(SUCCESS_STATUS))
            throw new CustomerException(AUTH_ID_CARD_ERROR.setDataData(userNo, cardCheck.getCardNo()).setUserNo(userNo));

        //认证通过，保存认证结果
        //设置认证结果
        this.optUser(user, authResult);
        CustCardInfoDTO cardInfo = this.optCard(authResult);
        cardInfo.setEndTime(cardCheck.getEndTime());

        //保存认证结果
        custCardInfoMapper.addOrUpdate(cardInfo);
        userManagerService.updateUserInfo(user);

        return new UserCardCheckResultDTO(authResult.getStatus().equals(SUCCESS_STATUS), authResult.getMsg());
    }

    private void optUser(CustUserDTO user, IdCardAuthResultDTO authResult) {
        Boolean success = authResult.getStatus().equals(SUCCESS_STATUS);

        user.setName(authResult.getName());
        user.setCardNo(authResult.getIdCard());
        user.setCardType(UserConstants.type_0);
        user.setAuthStatus(success ? UserConstants.aStatus_1 : UserConstants.aStatus_0);
        user.setAuthTime(new Timestamp(Instant.now().toEpochMilli()));
        user.setSex(authResult.getSex().equals("男") ? UserConstants.sex_1 : UserConstants.sex_0);
    }

    private CustCardInfoDTO optCard(IdCardAuthResultDTO authResult) {
        CustCardInfoDTO cardInfo = new CustCardInfoDTO();
        Boolean success = authResult.getStatus().equals(SUCCESS_STATUS);

        cardInfo.setName(authResult.getName());
        cardInfo.setCardNo(authResult.getIdCard());
        cardInfo.setBirthday(authResult.getBirthday());
        cardInfo.setSex(authResult.getSex().equals("男") ? UserConstants.sex_1 : UserConstants.sex_0);

        cardInfo.setStatus(success ? AuthInfoConstants.id_card_status_1 : AuthInfoConstants.id_card_status_0);

        cardInfo.setProvince(authResult.getProvince());
        cardInfo.setCity(authResult.getCity());
        cardInfo.setPrefecture(authResult.getPrefecture());
        cardInfo.setAddress(authResult.getArea());

        return cardInfo;
    }
}
