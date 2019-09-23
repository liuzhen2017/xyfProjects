package com.xingyunfu.ebank.schedule;

import com.xingyunfu.ebank.constant.TransferConstant;
import com.xingyunfu.ebank.domain.transfer.EbankTransferMergeDTO;
import com.xingyunfu.ebank.dto.infocenter.SendMessageCustomDTO;
import com.xingyunfu.ebank.dto.paycenter.summary.SummaryReqDTO;
import com.xingyunfu.ebank.dto.paycenter.summary.SummaryRespDTO;
import com.xingyunfu.ebank.dto.transfer.TransferMergeQueryDTO;
import com.xingyunfu.ebank.feign.InfoCenterFeign;
import com.xingyunfu.ebank.mapper.transfer.EbankTransferMergeMapper;
import com.xingyunfu.ebank.service.notice.NoticeMangerService;
import com.xingyunfu.ebank.service.paycenter.AccountRecordSummaryService;
import com.xingyunfu.ebank.service.paycenter.PayCenterConfig;
import com.xingyunfu.ebank.service.redis.RedisCommonService;
import com.xingyunfu.ebank.service.redis.RedisKeyRules;
import com.xingyunfu.ebank.service.redis.RedisLock;
import com.xingyunfu.ebank.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 定期对账<br/>
 * 对账订单时间区间：当天0点至当前时间点
 */
@Slf4j
@Component
public class ScanTransferMergeOrderSchedule {

    @Autowired private AccountRecordSummaryService accountRecordSummaryService;
    @Autowired private PayCenterConfig payCenterConfig;
    @Autowired private EbankTransferMergeMapper ebankTransferMergeMapper;
    @Autowired private InfoCenterFeign infoCenterFeign;
    @Autowired private RedisTemplate redisTemplate;
    @Autowired private RedisStringService redisStringService;
    @Autowired private RedisCommonService redisCommonService;
    @Autowired private NoticeMangerService noticeMangerService;
    @Autowired private TransferScheduleService transferScheduleService;

    @Scheduled(cron = "0 0/10 * * * ?")//每10分钟执行一次，配置文件指定特定时间点执行
    public void scanMergeOrder() {
        log.info("==================START SCAN TRANSFER MERGE ORDER===================");

        ZonedDateTime currentTime = ZonedDateTime.now();
        ZonedDateTime currentTime_8 = ZonedDateTime.ofInstant(currentTime.toInstant(), ZoneId.of("+8"));

        Long endTimeStamp = transferScheduleService.start(currentTime_8, payCenterConfig.scanRightHour());
        if(Objects.isNull(endTimeStamp)) return;

        ZonedDateTime startTime = ZonedDateTime.of(currentTime_8.getYear(), currentTime_8.getMonthValue(), currentTime_8.getDayOfMonth(),
                0, 0, 0, 0, ZoneId.of("+8"));
        ZonedDateTime endTime = Instant.ofEpochSecond(endTimeStamp).atZone(ZoneId.of("+8"));
        String pattern = "yyyy-MM-dd HH:mm:ss";
        String startTimeStr = startTime.format(DateTimeFormatter.ofPattern(pattern));
        String endTimeStr = endTime.format(DateTimeFormatter.ofPattern(pattern));

        RedisLock lock = new RedisLock(redisTemplate, RedisKeyRules.scanTransferMergeOrder());
        try {
            if (lock.lock()) {

                String redisKey = RedisKeyRules.scanDoSendMessage();
                if(redisCommonService.exists(redisKey)){
                    log.info("In current time, schedule has done, closed!");
                    return;
                }

                //执行对账
                SummaryRespDTO summaryResp = accountRecordSummaryService.recordSummary(
                        new SummaryReqDTO(payCenterConfig.getMerchantNo(), startTimeStr, endTimeStr));

                //查询ebank出账
                List<EbankTransferMergeDTO> transferMergeAllList = ebankTransferMergeMapper.allMerge(
                        new TransferMergeQueryDTO(startTime.toEpochSecond(), endTime.toEpochSecond(), null, null));
                List<EbankTransferMergeDTO> successList = transferMergeAllList.stream()
                        .filter(var -> TransferConstant.status_success.equals(var.getStatus())).collect(Collectors.toList());
                List<EbankTransferMergeDTO> failedList = transferMergeAllList.stream()
                        .filter(var -> TransferConstant.status_apply_failed.equals(var.getStatus())).collect(Collectors.toList());
                List<EbankTransferMergeDTO> waitHandleList = transferMergeAllList.stream()
                        .filter(var -> TransferConstant.status_wait_handle.equals(var.getStatus())).collect(Collectors.toList());
                List<EbankTransferMergeDTO> handleSuccessList = transferMergeAllList.stream()
                        .filter(var -> TransferConstant.status_handle_success.equals(var.getStatus())).collect(Collectors.toList());

                //消息被推送人
                List<String> phoneList = noticeMangerService.allReceiverPhone();
                //%s至%s，代付中心订单处理，共%s单, 成功%s单, 失败%s单，
                // 待人工处理%s单（单笔超额）, 人工已处理%s单，
                // 支出总金额%s元。
                // APP数据中心订单处理，成功%s单，支出总金额%s元。
                infoCenterFeign.sendMessage(new SendMessageCustomDTO("009", phoneList,
                        Arrays.asList(startTimeStr, endTimeStr, transferMergeAllList.size(), successList.size(), failedList.size(),
                                waitHandleList.size(), handleSuccessList.size(),
                                successList.stream().map(EbankTransferMergeDTO::getAmount).reduce(BigDecimal::add).orElse(new BigDecimal(0)),
                                summaryResp.getOrderQuantity(), new BigDecimal(summaryResp.getTotalAmount()).divide(new BigDecimal(100)))));
                redisStringService.add(redisKey, true, 5*60L);
                log.info("This scan transfer merge order, spend time: {}(second).",
                        Instant.now().getEpochSecond() - currentTime.toInstant().getEpochSecond());
            }
        }catch (Exception e){
            log.error("exception: {}", e);
        }finally {
            lock.unlock();
        }
    }
}
