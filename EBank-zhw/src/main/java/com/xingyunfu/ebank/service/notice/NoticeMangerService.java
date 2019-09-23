package com.xingyunfu.ebank.service.notice;

import com.xingyunfu.ebank.domain.notice.EbankNoticeDTO;
import com.xingyunfu.ebank.mapper.notice.EbankNoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NoticeMangerService {

    @Autowired private EbankNoticeMapper ebankNoticeMapper;

    public List<String> allReceiverPhone(){
        return ebankNoticeMapper.findAll().stream().filter(EbankNoticeDTO::getEnable)
                .filter(EbankNoticeDTO::getPhoneEnable).map(EbankNoticeDTO::getPhone)
                .collect(Collectors.toList());
    }
}
