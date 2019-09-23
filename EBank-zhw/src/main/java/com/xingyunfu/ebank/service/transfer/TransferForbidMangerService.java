package com.xingyunfu.ebank.service.transfer;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.transfer.EbankTransferForbidDTO;
import com.xingyunfu.ebank.dto.account.AccountAddDTO;
import com.xingyunfu.ebank.dto.user.UserInfoDTO;
import com.xingyunfu.ebank.dto.user.UserPhoneListDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.feign.CustomerManageFeign;
import com.xingyunfu.ebank.mapper.transfer.EbankTransferForbidMapper;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransferForbidMangerService {
    @Autowired
    private EbankTransferForbidMapper ebankTransferForbidMapper;
    @Autowired
    private CustomerManageFeign customerManageFeign;
    @Autowired
    private AccountMangerService accountMangerService;

    public List<EbankTransferForbidDTO> findAll() {
        return ebankTransferForbidMapper.findAll();
    }

    public void add(UserPhoneListDTO phoneList) {
        phoneList.getPhone().removeAll(this.findAll().stream().map(EbankTransferForbidDTO::getUserPhone).collect(Collectors.toList()));
        if(phoneList.getPhone().isEmpty()) return;
        List<UserInfoDTO> userInfoList = customerManageFeign.findByPhoneList(phoneList).getData();
        userInfoList.forEach(userInfo -> {
            try {
                EbankAccountDTO account = accountMangerService.findByUserNo(userInfo.getUserNo());
                if (Objects.isNull(account)) account = accountMangerService.addAccount(new AccountAddDTO(userInfo));

                ebankTransferForbidMapper.insert(
                        new EbankTransferForbidDTO(account.getAccountNo(), userInfo.getUserNo(), userInfo.getPhone()));

            } catch (EBankException e) {
                log.error("add account error!");
            }
        });
    }
}
