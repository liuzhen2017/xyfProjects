package com.xinyunfu.report.controller;

import com.ruoyi.common.core.controller.*;
import com.ruoyi.common.core.page.*;
import com.xinyunfu.report.model.*;
import com.xinyunfu.report.service.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.*;
import java.util.*;

/**
 * @author XRZ
 * @date 2019/9/2
 * @Description :
 */

@RestController
    @RequestMapping("transferdata")
@Slf4j
public class TransferDataController extends BaseController {

    @Autowired
    private TransferService transferService;

    @RequestMapping("reportLine")
    public Map<String,Object> reportUserRegistData(){
        Map<String,Object> transfermap=new HashMap<>();
        try {
            transfermap=transferService.queryTransferData();
            return transfermap;
        } catch (Exception e) {
            log.error("==请求异常=,msg={}", e.getMessage());
        }
        return transfermap;
    }

    /**
     * 查询入账出账金额集合
     */
    @RequestMapping("/queryTransferPage")
    public List<EbankFlow> selectTransferAmountList(EbankFlow ebankFlow)
    {
        return transferService.selectTransferAmountList(ebankFlow);
    }
}
