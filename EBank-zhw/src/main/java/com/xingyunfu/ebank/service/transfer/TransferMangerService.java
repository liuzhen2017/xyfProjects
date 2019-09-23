package com.xingyunfu.ebank.service.transfer;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.account.EbankAccountExtensionDTO;
import com.xingyunfu.ebank.domain.transfer.EbankTransferDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.dto.transfer.TransferAccountPageQueryDTO;
import com.xingyunfu.ebank.dto.transfer.TransferAddDTO;
import com.xingyunfu.ebank.dto.transfer.TransferPageQueryData;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.mapper.transfer.EbankTransferMapper;
import com.xingyunfu.ebank.service.account.AccountExtensionMangerService;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com.xingyunfu.ebank.exception.EBankExceptionEnum.TRANSFER_ADD_ORDER_NO;

@Slf4j
@Service
public class TransferMangerService {

    @Autowired private EbankTransferMapper ebankTransferMapper;
    @Autowired private AccountMangerService accountMangerService;
    @Autowired private AccountExtensionMangerService accountExtensionMangerService;

    /**
     * 用户间转账，转账信息入库，供定时任务扫描
     */
    public void addTransfer(TransferAddDTO transferAdd) throws EBankException {

        //判断订单号是否已存在
        if(Objects.nonNull(ebankTransferMapper.findByOrderNo(transferAdd.getOrderNo())))
            throw new EBankException(TRANSFER_ADD_ORDER_NO);

        //内部账户信息，如果内部账户不存在，则向其他服务发送请求，然后设置内部账户
        EbankAccountDTO sourceAccount = accountMangerService.hardFindByUserNo(transferAdd.getSourceUserNo());
        EbankAccountDTO receiveAccount = accountMangerService.hardFindByUserNo(transferAdd.getReceiveUserNo());

        EbankAccountExtensionDTO extension = accountExtensionMangerService.findByAccountNo(receiveAccount.getAccountNo());
        //保存转账信息
        if(Objects.isNull(extension) || Objects.isNull(extension.getSuperAccountNo())) {
            ebankTransferMapper.insert(new EbankTransferDTO(transferAdd, sourceAccount, receiveAccount));
        }else{
            EbankAccountDTO personAccount = receiveAccount;
            receiveAccount = accountMangerService.hardFindByAccountNo(extension.getSuperAccountNo());
            ebankTransferMapper.insert(new EbankTransferDTO(transferAdd, sourceAccount, receiveAccount, personAccount));
        }
    }

    /**
     * 锁单
     */
    public Integer lockOrder(String ebankOrderNo, String receiveAccountNo, Long startTime, Long endTime){

        Map<String, Object> map = new HashMap<>();
        map.put("ebankOrderNo", ebankOrderNo);
        map.put("receiveAccountNo", receiveAccountNo);
        map.put("startTime", startTime);
        map.put("endTime", endTime);

        if(Objects.isNull(startTime)) return ebankTransferMapper.lockAllOrder(map);
        return ebankTransferMapper.lockOrder(map);
    }

    /**
     * 查询出订单号下的转账总金额
     */
    public BigDecimal totalAmount(String ebankOrderNo){
        return ebankTransferMapper.totalAmount(ebankOrderNo);
    }

    /**
     * 关闭订单,将订单状态设为已关闭
     */
    public void closeOrder(String ebankOrderNo, Boolean success){
        ebankTransferMapper.closeOrder(new HashMap<String, Object>(){{
            put("ebankOrderNo", ebankOrderNo);put("closed", success);
        }});
    }

    /**
     * 根据ebank订单号，查询出所有该订单号下的外部订单号
     */
    public List<String> findOrderNoByEbankOrderNo(String ebankOrderNo){
        return ebankTransferMapper.findOrderNoByEbankOrderNo(ebankOrderNo);
    }

    /**
     * 查询出某段时间前10个待处理的转账
     */
    public List<String> findTenNeedTransferAccountNo(Long endTime, String receiveAccountType, List<String> forbidAccountNoList){
        return ebankTransferMapper.findTenNeedTransferUser(new HashMap<String, Object>(){{
            put("endTime", endTime);put("receiveAccountType", receiveAccountType);
            put("forbidList", forbidAccountNoList);
        }});
    }

    /**
     * 查询出某段时间所有待处理的转账
     */
    public List<EbankAccountDTO> findAllNeedTransferAccount(Long startTime, Long endTime, String accountType){
        return ebankTransferMapper.findAllNeedTransferUser(new HashMap<String, Object>(){{
            put("startTime", startTime);put("endTime", endTime);put("accountType", accountType);
        }});
    }

    /**
     * 根据条件分页查询代付转账
     */
    public PageDTO<EbankTransferDTO> queryTransfer(TransferPageQueryData query){
        Integer pageNo = query.getPageNo();
        Integer pageSize = query.getPageSize();

        query.setPageNo((pageNo-1) * pageSize);
        query.setPageSize(pageSize);

        return new PageDTO<>(pageNo, pageSize, ebankTransferMapper.pageQueryCount(query),
                ebankTransferMapper.pageQuery(query));
    }

    /**
     * 分页查询待转账的账户
     */
    public PageDTO<EbankAccountDTO> transferAccount(TransferAccountPageQueryDTO query){
        Integer pageNo = query.getPageNo();
        Integer pageSize = query.getPageSize();

        query.setPageNo((pageNo-1) * pageSize);
        query.setPageSize(pageSize);

        return new PageDTO<>(pageNo, pageSize, ebankTransferMapper.transferAccountCount(query),
                ebankTransferMapper.transferAccount(query));
    }
}
