package com.xinyunfu.customer.service.bank;

import com.xinyunfu.customer.constant.AreaConstant;
import com.xinyunfu.customer.constant.BankConstants;
import com.xinyunfu.customer.constant.UserConstants;
import com.xinyunfu.customer.domain.address.CustAreaDTO;
import com.xinyunfu.customer.domain.bank.CustBankAccountDTO;
import com.xinyunfu.customer.domain.bank.CustBankInfoDTO;
import com.xinyunfu.customer.domain.user.CustUserDTO;
import com.xinyunfu.customer.dto.auth.BankCardAuthResultDTO;
import com.xinyunfu.customer.dto.bank.BankAccountAddDTO;
import com.xinyunfu.customer.dto.ebank.AccountUpdateDTO;
import com.xinyunfu.customer.dto.user.UserCardCheckResultDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.feigin.EbankFeign;
import com.xinyunfu.customer.repository.bank.CustBankAccountMapper;
import com.xinyunfu.customer.service.address.AreaManagerService;
import com.xinyunfu.customer.service.auth.BankCardAuthService;
import com.xinyunfu.customer.service.redis.RedisCommonService;
import com.xinyunfu.customer.service.redis.RedisKeyRules;
import com.xinyunfu.customer.service.redis.RedisSetService;
import com.xinyunfu.customer.service.redis.RedisStringService;
import com.xinyunfu.customer.service.user.UserManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xinyunfu.customer.exception.CustomerExceptionEnum.*;

/**
 * 银行卡信息管理
 */
@Slf4j
@Service
public class BankAccountManagerService {
    @Autowired
    private CustBankAccountMapper custBankAccountMapper;
    @Autowired
    private UserManagerService userManagerService;
    @Autowired
    private RedisStringService redisStringService;
    @Autowired
    private RedisCommonService redisCommonService;
    @Autowired
    private RedisSetService redisSetService;
    @Autowired
    private BankCardAuthService bankCardAuthService;
    @Autowired
    private BankInfoService bankInfoService;
    @Autowired
    private AreaManagerService areaManagerService;
    @Autowired private EbankFeign ebankFeign;

    private static String SUCCESS_STATUS = "01";

    /**
     * 添加银行卡
     */
    public UserCardCheckResultDTO addAccount(Long userNo, BankAccountAddDTO bankAccountAdd) throws CustomerException {

        //判断当前用户是否已实名验证过
        CustUserDTO user = userManagerService.findByUserNo(userNo);
        if (!UserConstants.aStatus_1.equals(user.getAuthStatus()))
            throw new CustomerException(AUTH_ID_CARD_NO_AUTH.setDataData(userNo, bankAccountAdd.getAccountNo()).setUserNo(userNo));

        //一个用户只能绑定一个银行卡
        if (!this.queryAccount(userNo).isEmpty())
            throw new CustomerException(AUTH_BANK_MORE_CARD_ERROR.setDataData(userNo, bankAccountAdd.getAccountNo()).setUserNo(userNo));

        //判断该卡是否已存在，删除状态的银行卡不作为判重条件
        CustBankAccountDTO account = custBankAccountMapper.findByAccountNo(bankAccountAdd.getAccountNo());
        if (Objects.nonNull(account) && !account.getStatus().equals(BankConstants.STATUS_DELETE))
            throw new CustomerException(AUTH_BANK_CARD_EXISTS.setDataData(userNo, bankAccountAdd.getAccountNo()).setUserNo(userNo));

        //判断银行是否存在
        CustBankInfoDTO bankInfo = bankInfoService.getBankInfo(bankAccountAdd.getBankId());
        if (Objects.isNull(bankInfo))
            throw new CustomerException(AUTH_BANK_NOT_EXISTS.setDataData(userNo, bankAccountAdd.getAccountNo()).setUserNo(userNo));

        //判断选择的区域是否存在，且保证id是区/县一级
        CustAreaDTO regionArea = areaManagerService.findById(bankAccountAdd.getRegionId());

        if(Objects.nonNull(regionArea) && regionArea.getAreaLevel() == AreaConstant.area_level_4)//如果是乡镇一级，重新定位到区/县一级
            regionArea = areaManagerService.findById(regionArea.getParentId());

        if (Objects.isNull(regionArea) || regionArea.getAreaLevel() != AreaConstant.area_level_3)
            throw new CustomerException(AUTH_AREA_NOT_EXISTS.setDataData(userNo, bankAccountAdd.getAccountNo()).setUserNo(userNo));

        //开始认证
        BankCardAuthResultDTO authResult = bankCardAuthService.auth(bankAccountAdd.getAccountNo(), user.getCardNo(), user.getName());
        log.debug("bank card auth result: {}", authResult);

        //认证失败，写入日志
        if (!authResult.getStatus().equals(SUCCESS_STATUS))
            throw new CustomerException(AUTH_BANK_CARD_ERROR.setDataData(userNo, bankAccountAdd.getAccountNo()).setUserNo(userNo));

        //判断用户选择的银行是否正确
        if (!bankInfo.getCname().contains(authResult.getBank())
                && !authResult.getBank().contains(bankInfo.getCname()))
            throw new CustomerException(AUTH_BANK_NAME_ERROR.setDataData(userNo, bankAccountAdd.getAccountNo()).setUserNo(userNo));

        //根据区，获得省市
        CustAreaDTO cityArea = areaManagerService.findById(regionArea.getParentId());
        CustAreaDTO provinceArea = areaManagerService.findById(cityArea.getParentId());

        //构造银行卡数据
        CustBankAccountDTO bankAccount = new CustBankAccountDTO(user, bankInfo, authResult, bankAccountAdd, regionArea, cityArea, provinceArea);

        //判断银行卡是不是借记卡
        String rightCardType = "借记卡";
        Boolean addRight = authResult.getCardType().contains(rightCardType);
        //不是借记卡，设置为删除状态
        if(!addRight) bankAccount.setStatus(BankConstants.STATUS_DELETE);

        //写入数据库
        custBankAccountMapper.add(bankAccount);
        redisSetService.add(RedisKeyRules.userBankAccount(userNo), bankAccount.getId());

        //卡类型错误，结束
        if (!addRight) throw new CustomerException(AUTH_CARD_TYPE_ERROR.setDataData(userNo, bankAccountAdd.getAccountNo()).setUserNo(userNo));

        //通知ebank修改用户数据
        AccountUpdateDTO accountUpdate = new AccountUpdateDTO(user, bankAccount);
        log.debug("向ebank发送请求更新银行卡: {}", accountUpdate);
        ebankFeign.updateAccount(accountUpdate);

        return new UserCardCheckResultDTO(authResult.getStatus().equals(SUCCESS_STATUS), authResult.getMsg());
    }

    /**
     * 删除银行卡<br/>
     * 实行软删除，银行卡状态修改为删除态
     */
    public void deleteAccount(Long userNo, Long accountId) {
        CustBankAccountDTO bankAccount = new CustBankAccountDTO();
        bankAccount.setId(accountId);
        bankAccount.setUserNo(userNo);
        bankAccount.setStatus(BankConstants.STATUS_DELETE);
        bankAccount.setDefaultCard(false);

        custBankAccountMapper.update(bankAccount);
        redisCommonService.delete(RedisKeyRules.bankAccount(accountId));
        redisSetService.remove(RedisKeyRules.userBankAccount(userNo), accountId);
    }

    /**
     * 查询出所有状态不是删除态的银行卡
     */
    public List<CustBankAccountDTO> queryAccount(Long userNo) {
        return this.queryAccountKeys(userNo).stream().map(id -> this.findById(id))
                .filter(card -> card.getStatus().equals(BankConstants.STATUS_APPROVAL))
                .collect(Collectors.toList());
    }

    /**
     * 查询单个银行卡信息
     */
    public CustBankAccountDTO findById(Long id) {
        String redisKey = RedisKeyRules.bankAccount(id);
        if (redisCommonService.exists(redisKey)) {
            return (CustBankAccountDTO) redisStringService.get(redisKey);
        }

        CustBankAccountDTO bankAccount = custBankAccountMapper.findById(id);

        redisStringService.add(redisKey, bankAccount);
        return bankAccount;
    }

    private List<Long> queryAccountKeys(Long userNo) {
        String redisKey = RedisKeyRules.userBankAccount(userNo);
        if (redisCommonService.exists(redisKey)) {
            return redisSetService.get(redisKey).stream().map(id -> Long.valueOf(id.toString())).collect(Collectors.toList());
        }

        List<Long> keys = custBankAccountMapper.findIdByUserNo(userNo);
        if (Objects.isNull(keys) || keys.isEmpty()) return new ArrayList<>();

        log.debug("keys: {}", keys);
        redisSetService.addList(redisKey, keys);
        return keys;
    }
}
