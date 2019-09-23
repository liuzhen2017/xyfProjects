package com.xingyunfu.ebank.schedule;

import com.xingyunfu.ebank.constant.TransferConstant;
import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.transfer.EbankTransferMergeDTO;
import com.xingyunfu.ebank.dto.paycenter.detail.DetailOrderListDTO;
import com.xingyunfu.ebank.dto.paycenter.detail.DetailReqDTO;
import com.xingyunfu.ebank.dto.paycenter.detail.DetailRespDTO;
import com.xingyunfu.ebank.dto.transfer.TransferMergeQueryDTO;
import com.xingyunfu.ebank.mapper.transfer.EbankTransferMergeMapper;
import com.xingyunfu.ebank.service.account.AccountMangerService;
import com.xingyunfu.ebank.service.paycenter.AccountRecordDetailService;
import com.xingyunfu.ebank.service.paycenter.PayCenterConfig;
import com.xingyunfu.ebank.service.redis.RedisCommonService;
import com.xingyunfu.ebank.service.redis.RedisKeyRules;
import com.xingyunfu.ebank.service.redis.RedisLock;
import com.xingyunfu.ebank.service.redis.RedisStringService;
import com.xingyunfu.ebank.service.transfer.TransferMergeMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static com.xingyunfu.ebank.constant.InnerAccountConstant.system_user_no;

/**
 * 定时扫描合并转帐失败的订单
 * 仅扫描当天的合并转账
 */
@Slf4j
@Component
public class TransferMergeTrySchedule {
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private TransferScheduleService transferScheduleService;
    @Autowired private AccountRecordDetailService accountRecordDetailService;
    @Autowired private TransferMergeMangerService transferMergeMangerService;
    @Autowired private AccountMangerService accountMangerService;
    @Autowired private EbankTransferMergeMapper ebankTransferMergeMapper;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0/10 * * * ?")//每10分钟执行一次，配置文件指定特定时间点执行
    public void scanMergeOrderDetail() {
        log.info("==================START SCAN TRANSFER MERGE ORDER DETAIL===================");

        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime currentTime_8 = ZonedDateTime.ofInstant(currentTime.toInstant(), ZoneId.of("+8"));

        Long endTimeStamp = transferScheduleService.start(currentTime_8, payCenterConfig.scanDetailRightHour());
        if(Objects.isNull(endTimeStamp)) return;

        ZonedDateTime startTime = ZonedDateTime.of(currentTime_8.getYear(), currentTime_8.getMonthValue(), currentTime_8.getDayOfMonth(),
                0, 0, 0, 0, ZoneId.of("+8"));
        ZonedDateTime endTime = Instant.ofEpochSecond(endTimeStamp).atZone(ZoneId.of("+8"));
        String pattern = "yyyy-MM-dd HH:mm:ss";
        String startTimeStr = startTime.format(DateTimeFormatter.ofPattern(pattern));
        String endTimeStr = endTime.format(DateTimeFormatter.ofPattern(pattern));

        RedisLock redisLock = new RedisLock(redisTemplate, RedisKeyRules.scanTransferMergeDetailOrder());
        try {
            if(redisLock.lock()){
                String redisKey = RedisKeyRules.scanTransferMergeDetailClose();
                if(redisCommonService.exists(redisKey)){
                    log.info("In current time, schedule has done, closed!");
                    return;
                }

                List<EbankTransferMergeDTO> transferMergeFailedList = ebankTransferMergeMapper.allMerge(
                        new TransferMergeQueryDTO(startTime.toEpochSecond(), endTime.toEpochSecond(), TransferConstant.status_apply_failed, null));
                log.warn("Transfer merge failed list: {}", transferMergeFailedList);
                if(Objects.isNull(transferMergeFailedList) || transferMergeFailedList.isEmpty()) return;

                DetailRespDTO detailResp = accountRecordDetailService.recordDetail(
                        new DetailReqDTO(payCenterConfig.getMerchantNo(), startTimeStr, endTimeStr));

                EbankAccountDTO systemAccount = accountMangerService.findByUserNo(system_user_no);
                transferMergeFailedList.forEach(mergeFailed -> {
                    DetailOrderListDTO detailOrder = detailResp.getOrderList().stream()
                            .filter(var -> var.getOutTradeNo().equals(mergeFailed.getEbankOrderNo())).findFirst().orElse(null);
                    if(Objects.isNull(detailOrder)){
                        transferMergeMangerService.transferApplyTry(mergeFailed, systemAccount);
                    }else{
                        transferMergeMangerService.closeTransferApply(mergeFailed, systemAccount, detailOrder.getVoucher());
                    }
                });

                redisStringService.add(redisKey, true, 5*60L);
                log.info("This scan transfer merge order detail, spend time: {}(second).",
                        Instant.now().getEpochSecond() - currentTime.toInstant().getEpochSecond());
            }
        } catch (Exception e) {
            log.error("exception: {}", e);
        } finally {
            redisLock.unlock();
        }
    }
}
