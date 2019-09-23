package com.xingyunfu.ebank.service.account;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.account.AccountAddDTO;
import com.xingyunfu.ebank.dto.account.AccountPageQueryDTO;
import com.xingyunfu.ebank.dto.account.AccountUpdateDTO;
import com.xingyunfu.ebank.dto.user.BankAccountInfoDTO;
import com.xingyunfu.ebank.dto.user.UserInfoDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.exception.EBankExceptionEnum;
import com.xingyunfu.ebank.feign.CustomerManageFeign;
import com.xingyunfu.ebank.mapper.account.EbankAccountMapper;
import com.xingyunfu.ebank.service.redis.RedisKeyRules;
import com.xingyunfu.ebank.service.redis.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

import static com.xingyunfu.ebank.constant.InnerAccountConstant.accountType_system;
import static com.xingyunfu.ebank.constant.InnerAccountConstant.system_user_no;
import static com.xingyunfu.ebank.exception.EBankExceptionEnum.ACCOUNT_ADD_NO_USER;

@Slf4j
@Service
public class AccountMangerService {
    @Autowired private EbankAccountMapper ebankAccountMapper;
    @Autowired private CustomerManageFeign customerManageFeign;
    @Autowired private RedisTemplate redisTemplate;

    /** 创建系统内部账户 */
    @PostConstruct
    private void init() throws EBankException {
        if(Objects.isNull(this.findByUserNo(system_user_no))){
            log.info("Create inner system account!!!");
            AccountAddDTO accountAdd = new AccountAddDTO();
            //系统用户编号，在ebank服务中实际没有用到用户编号，除了从其它服务获取用户相关信息
            accountAdd.setUserNo(system_user_no);
            accountAdd.setPhone("systemPhone");
            accountAdd.setUserName("system_account");
            accountAdd.setIdCardNo("system_id_card");
            accountAdd.setAccountName("system_account");
            accountAdd.setAccountType(accountType_system);
            this.addAccount(accountAdd);
        }
    }

    /**
     * 添加账户
     */
    public EbankAccountDTO addAccount(AccountAddDTO accountAdd) throws EBankException {
        //判断是否已存在该账户
        if(Objects.nonNull(this.findByUserNo(accountAdd.getUserNo()))){
            throw new EBankException(EBankExceptionEnum.ACCOUNT_ADD_HAVE_SAVE_USER);
        }
        //添加账户
        EbankAccountDTO account = new EbankAccountDTO(accountAdd);
        ebankAccountMapper.insert(account);
        return account;
    }

    /**
     * 获取账号，如果不存在则向用户服务发送请求获取账号
     */
    public EbankAccountDTO hardFindByUserNo(Long userNo) throws EBankException {
        EbankAccountDTO innerAccount = this.findByUserNo(userNo);
        if(Objects.nonNull(innerAccount)) return innerAccount;

        UserInfoDTO userInfo = customerManageFeign.getUserInfo(userNo).getData();
        if(Objects.isNull(userInfo) || Objects.isNull(userInfo.getUserNo()))
            throw new EBankException(ACCOUNT_ADD_NO_USER);

        List<BankAccountInfoDTO> bankAccountInfo = customerManageFeign.queryAccount(userNo).getData();

        this.addAccount(new AccountAddDTO(userInfo));
        if(!bankAccountInfo.isEmpty()){
            this.updateAccount(new AccountUpdateDTO(userInfo, bankAccountInfo.get(0)));
        }
        return this.findByUserNo(userNo);
    }

    public EbankAccountDTO findByUserNo(Long userNo){
        EbankAccountDTO account = ebankAccountMapper.findByUserNo(userNo);
        return account;
    }

    public EbankAccountDTO hardFindByAccountNo(String accountNo){
        return this.hardFindByAccountNo(ebankAccountMapper.findByAccountNo(accountNo));
    }

    //如果账户信息不完整，同步用户服务信息
    public EbankAccountDTO hardFindByAccountNo(EbankAccountDTO account){
        if(Objects.isNull(account.getBankCardNo())){
            List<BankAccountInfoDTO> bankAccountInfo = customerManageFeign.queryAccount(account.getUserNo()).getData();
            if(!bankAccountInfo.isEmpty()) {
                UserInfoDTO userInfo = customerManageFeign.getUserInfo(account.getUserNo()).getData();
                BankAccountInfoDTO bankAccount = bankAccountInfo.get(0);
                log.debug("bankAccount: {}", bankAccount);

                //设置account
                account.setUserName(userInfo.getName());
                account.setPhone(bankAccount.getPhone());
                account.setIdCardNo(userInfo.getCardNo());
                account.setBankCardNo(bankAccount.getAccountNo());
                account.setBankName(bankAccount.getBankName());
                account.setBankNo(bankAccount.getBankCode());
                account.setBranchName(bankAccount.getBranchName());
                account.setBranchNo(bankAccount.getBranchNo());
                account.setProvinceName(bankAccount.getProvinceName());
                account.setCityName(bankAccount.getCityName());

                ebankAccountMapper.update(account);
            }
        }
        return account;
    }

    /**
     * 不能更新账户余额
     */
    public void updateAccount(AccountUpdateDTO accountUpdate){
        ebankAccountMapper.update(new EbankAccountDTO(accountUpdate));
    }

    /**
     * 增加/减少余额，所有操作余额的入口只能有这一个
     * @Param var 正1：表示增加余额，负1表示减少余额
     */
    public void operateBalance(String accountNo, BigDecimal amount, Integer var){
        RedisLock redisLock = new RedisLock(redisTemplate, RedisKeyRules.balanceRedisLock(accountNo));
        try {
            if(redisLock.lock()) {
                Map<String, Object> map = new HashMap<>();
                map.put("accountNo", accountNo);
                map.put("balance", amount.multiply(new BigDecimal(var)));
                ebankAccountMapper.updateAmount(map);
            }
        } catch (InterruptedException e) {
            log.error("operate balance error: {}", e);
        }finally {
            redisLock.unlock();
        }
    }

    /**
     * 分页查询
     */
    public PageDTO<EbankAccountDTO> pageQuery(AccountPageQueryDTO pageQuery){
        Integer pageNo = pageQuery.getPageNo();
        Integer pageSize = pageQuery.getPageSize();

        pageQuery.setPageNo((pageNo-1) * pageSize);
        pageQuery.setPageSize(pageSize);

        return new PageDTO<>(pageNo, pageSize, ebankAccountMapper.pageQueryCount(pageQuery),
                ebankAccountMapper.pageQuery(pageQuery));
    }

    public List<EbankAccountDTO> listAccount(List<String> accountNoList){
        if(Objects.isNull(accountNoList) || accountNoList.isEmpty()) return new ArrayList<>();
        return ebankAccountMapper.listAccount(new HashMap<String, List<String>>(){{
            put("accountNoList", accountNoList);
        }});
    }
}
