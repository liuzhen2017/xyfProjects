package com.ruoyi.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author TYM
 * @date 2019/8/20
 * @Description :
 */
@FeignClient("InfoCenter")
public interface InfoCenterFeign {

    /**
     * 给指定号码发送消息
     *
     * @param recvObject 接收方(电话号码)
     * @param sendType   发送类型,01发送验证码,02发送短信,04发送极光推送
     * @param templateNo 模板编号
     * @return
     */
    @PostMapping("/send/message")
    public com.ruoyi.utils.ResponseInfo<String> sendMsg(@RequestBody String body);


    /**
     * 校验验证码
     * @param req json({"phone": "123456789","code":"123454","templateNo":"adfsdfsadfgggffff"}
     * @return
     */
    @PostMapping("/send/checkSendMsg.do")
    public com.ruoyi.utils.ResponseInfo<Object> checkSendMsg(@RequestBody String req);


}
