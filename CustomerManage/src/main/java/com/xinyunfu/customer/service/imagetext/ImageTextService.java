package com.xinyunfu.customer.service.imagetext;

import com.xinyunfu.customer.dto.common.CommonTokenDTO;
import com.xinyunfu.customer.dto.imagetext.BankCardRespDTO;
import com.xinyunfu.customer.dto.imagetext.IdCardRespDTO;
import com.xinyunfu.customer.exception.CustomerException;
import com.xinyunfu.customer.service.imagetext.baidu.BaiduAuthService;
import com.xinyunfu.customer.service.imagetext.baidu.BaiduImageTextService;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ImageTextService{
    @Autowired private BaiduImageTextService baiduImageTextService;
    @Autowired private BaiduAuthService baiduAuthService;

    /**
     * 身份证识别
     */
    public IdCardRespDTO idCard(String base64Image) throws CustomerException, JSONException {

        String var0 = "base64,";
        if(base64Image.contains(var0))
            base64Image = base64Image.split(var0)[1];//去掉图片头

        return baiduImageTextService.idCard(base64Image);
    }


    /**
     * 银行卡识别
     */
    public BankCardRespDTO bankCard(String base64Image) throws CustomerException, JSONException {

        String var0 = "base64,";
        if(base64Image.contains(var0))
            base64Image = base64Image.split(var0)[1];//去掉图片头
        return baiduImageTextService.bankCard(base64Image);
    }

    public CommonTokenDTO token(){
        return new CommonTokenDTO(baiduAuthService.getAuth());
    }

}
