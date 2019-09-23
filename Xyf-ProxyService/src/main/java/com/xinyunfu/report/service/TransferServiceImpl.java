package com.xinyunfu.report.service;

import com.xinyunfu.report.dao.proxy.TransferMapper;
import com.xinyunfu.report.model.*;
import com.xinyunfu.report.utils.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author XRZ
 * @date 2019/9/2
 * @Description :
 */
@Service
@Slf4j
public class TransferServiceImpl implements TransferService {
    @Autowired
    private TransferMapper transferMapper;
    @Autowired
    private IEchartsFactoryService echartsFactoryService;

    @Override
    public Map<String,Object> queryTransferData() throws Exception {
        //入账信息
        List<Map<String, Object>> ListTransIn = transferMapper.getTransferInMoney();
        //出账信息
        List<Map<String, Object>> ListTransOut = transferMapper.getTransferOutMoney();
        Map<String, Object> map=new HashMap<String, Object>();
        List<Map<String, Object>> source[] = new ArrayList[2];
        source[0]=ListTransIn;
        source[1]=ListTransOut;
        String title[]=new String[]{"出账金额","入账金额"};
        try {
            map=echartsFactoryService.createdLine(source, title);
            return map;
        } catch (Exception e) {
            log.error("queryTransferData is Error,Msg =", e.getMessage(), e);
            return map;
        }
    }


    /**
     * 获取入账出账金额集合
     * @param parmat EbankFlow
     * @return 入账出账金额集合
     * @throws Exception
     */
    public List<EbankFlow> selectTransferAmountList(EbankFlow ebankFlow){
        return transferMapper.selectTransferList(ebankFlow);
    }
}
