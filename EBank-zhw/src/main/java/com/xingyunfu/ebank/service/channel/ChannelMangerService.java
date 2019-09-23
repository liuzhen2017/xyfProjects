package com.xingyunfu.ebank.service.channel;

import com.xingyunfu.ebank.domain.channel.EbankChannelDTO;
import com.xingyunfu.ebank.dto.PageDTO;
import com.xingyunfu.ebank.exception.EBankException;
import com.xingyunfu.ebank.exception.EBankExceptionEnum;
import com.xingyunfu.ebank.mapper.channel.EbankChannelMapper;
import com.xingyunfu.ebank.service.redis.RedisCommonService;
import com.xingyunfu.ebank.service.redis.RedisKeyRules;
import com.xingyunfu.ebank.service.redis.RedisSetService;
import com.xingyunfu.ebank.service.redis.RedisStringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChannelMangerService {

    @Autowired
    private EbankChannelMapper ebankChannelMapper;
    @Autowired
    private RedisCommonService redisCommonService;
    @Autowired
    private RedisSetService redisSetService;
    @Autowired
    private RedisStringService redisStringService;

    public EbankChannelDTO findByEbankPayTypeAndClientType(Integer ebankPayType, String clientType) {
        String redisKeys = RedisKeyRules.ebankChannel(ebankPayType, clientType);
        if (redisCommonService.exists(redisKeys)) {
            return (EbankChannelDTO) redisStringService.get(redisKeys);
        }

        EbankChannelDTO payChannel = new EbankChannelDTO();
        payChannel.setEbankPayType(ebankPayType);
        payChannel.setClientType(clientType);
        EbankChannelDTO channel = ebankChannelMapper.findByEbankPayTypeAndClientType(payChannel);

        redisStringService.add(redisKeys, channel);
        return channel;
    }

    public List<Long> getAllId() {
        String redisKey = RedisKeyRules.allChannelId();
        if (redisCommonService.exists(redisKey)) {
            return redisSetService.get(redisKey).stream().map(var -> Long.valueOf(var.toString()))
                    .collect(Collectors.toList());
        }
        List<Long> result = ebankChannelMapper.findAllId();
        redisSetService.addList(redisKey, result);
        return result;
    }

    public PageDTO<EbankChannelDTO> pageQuery(Integer pageNo, Integer pageSize) {

        return new PageDTO<>(pageNo, pageSize, Long.valueOf(this.getAllId().size()),
                ebankChannelMapper.pageQuery(new HashMap<String, Integer>() {{
                            put("pageNo", pageSize * pageNo);put("pageSize", pageSize);
                        }}).stream().map(id -> this.findById(id)).collect(Collectors.toList()));
    }

    public EbankChannelDTO findById(Long id) {
        String redisKey = RedisKeyRules.oneChannel(id);
        if (redisCommonService.exists(redisKey)) {
            return (EbankChannelDTO) redisStringService.get(redisKey);
        }
        EbankChannelDTO result = ebankChannelMapper.findById(id);
        redisStringService.add(redisKey, result);
        return result;
    }

    public void update(EbankChannelDTO ebankChannel) throws EBankException {
        log.debug("ebankChannel: {}", ebankChannel);

        if((Objects.nonNull(ebankChannel.getPayType()) && Objects.isNull(ebankChannel.getPayName()))
                || (Objects.nonNull(ebankChannel.getTradeType()) && Objects.isNull(ebankChannel.getTradeName())))
            throw new EBankException(EBankExceptionEnum.CHANNEL_UPDATE_ERROR);

        ebankChannelMapper.update(ebankChannel);

        //清除redis
        EbankChannelDTO channel = this.findById(ebankChannel.getId());
        redisCommonService.delete(RedisKeyRules.oneChannel(channel.getId()));
        redisCommonService.delete(RedisKeyRules.ebankChannel(channel.getEbankPayType(), channel.getClientType()));
    }
}
