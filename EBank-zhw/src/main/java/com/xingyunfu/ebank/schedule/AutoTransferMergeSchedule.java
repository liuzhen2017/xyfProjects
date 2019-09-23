package com.xingyunfu.ebank.schedule;

import com.xingyunfu.ebank.constant.InnerAccountConstant;
import com.xingyunfu.ebank.domain.transfer.EbankTransferForbidDTO;
import com.xingyunfu.ebank.service.paycenter.PayCenterConfig;
import com.xingyunfu.ebank.service.transfer.TransferForbidMangerService;
import com.xingyunfu.ebank.service.transfer.TransferMangerService;
import com.xingyunfu.ebank.service.transfer.TransferMergeMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 启用自动转账
 * 合并订单
 */
@Slf4j
@Component
public class AutoTransferMergeSchedule {

    @Autowired private TransferMergeMangerService transferMergeMangerService;
    @Autowired private TransferMangerService transferMangerService;
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private TransferForbidMangerService transferForbidMangerService;

    @Autowired private TransferScheduleService transferScheduleService;

    private static Integer TRY_NUM = 5;

    @Scheduled(cron = "0 0/10 * * * ?")//每10分钟执行一次，配置文件指定特定时间点执行
    public void autoTransfer(){
        log.info("============START AUTO TRANSFER MERGE ORDER============");

        ZonedDateTime currentTime = ZonedDateTime.now();
        Random random = new Random();
        ZonedDateTime currentTime_8 = ZonedDateTime.ofInstant(currentTime.toInstant(), ZoneId.of("+8"));

        Long endTime = transferScheduleService.start(currentTime_8, payCenterConfig.getRightHour());
        if(Objects.isNull(endTime)) return;
        log.info("Start auto transfer merge, merge start time is 0, end time is {}", endTime);

        Map<String, Integer> accountTryNum = new HashMap<>();
        Boolean autoTransferAll = payCenterConfig.getAutoTransferAll();
        Set<String> forBidSet = new HashSet<>(transferForbidMangerService.findAll()
                .stream().map(EbankTransferForbidDTO::getAccountNo).collect(Collectors.toList()));
        while (true){

            forBidSet.addAll(accountTryNum.entrySet().stream()
                    .filter(entry -> entry.getValue()>=TRY_NUM).map(Map.Entry::getKey)
                    .collect(Collectors.toList()));

            List<String> receiveAccountNoList = transferMangerService.findTenNeedTransferAccountNo(endTime,
                    autoTransferAll? null: InnerAccountConstant.accountType_common, new ArrayList<>(forBidSet));
            if(Objects.isNull(receiveAccountNoList) || receiveAccountNoList.isEmpty()) break;

            String receiveAccountNo = receiveAccountNoList.get(random.nextInt(receiveAccountNoList.size()));
            accountTryNum.put(receiveAccountNo, accountTryNum.getOrDefault(receiveAccountNo, 0) + 1);

            transferMergeMangerService.transferApply(receiveAccountNo, null, endTime);
        }

        log.info("This transfer merge order, spend time: {}(second), merge order number: {}.",
                Instant.now().getEpochSecond() - currentTime.toInstant().getEpochSecond(), accountTryNum.keySet().size());
        log.info("Close auto transfer merge!");
    }
}
