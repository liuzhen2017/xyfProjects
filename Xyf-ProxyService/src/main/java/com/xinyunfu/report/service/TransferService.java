package com.xinyunfu.report.service;


import com.xinyunfu.report.model.*;

import java.util.*;

/**
 * @author XRZ
 * @date 2019/9/2
 * @Description :
 */
public interface TransferService {

    /**
     * 获取出账 入账数据
     * @param parmat
     * @return
     * @throws Exception
     */
    public Map<String,Object> queryTransferData()throws Exception;

    /**
     * 获取入账出账金额集合
     * @param parmat EbankFlow
     * @return 入账出账金额集合
     * @throws Exception
     */
    public List<EbankFlow> selectTransferAmountList(EbankFlow ebankFlow);
}
