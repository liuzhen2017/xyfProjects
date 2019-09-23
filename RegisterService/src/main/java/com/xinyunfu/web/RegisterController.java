package com.xinyunfu.web;

import com.alibaba.fastjson.JSONObject;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.dto.DataDTO;
import com.xinyunfu.dto.SendCodeDTO;
import com.xinyunfu.dto.UserRegisterDTO;
import com.xinyunfu.fegin.CustomerManageFeign;
import com.xinyunfu.utils.MD5Util;
import com.xinyunfu.utils.RSAEncrypt;
import com.xinyunfu.utils.ResponseInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import rx.internal.operators.OnSubscribeDelaySubscriptionOther;
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author XRZ
 * @date 2019/8/3
 * @Description : 邀请注册相关接口
 */
@Slf4j
@Controller
public class RegisterController {

    @Value("${downloadUrl}")
    private String downloadUrl;

    @Autowired
    private CustomerManageFeign customerManageFeign;

    /**
     * 去注册
     */
    @GetMapping("/toRegister")
    public ModelAndView toRegister(@RequestParam("code") String code){
        ModelAndView vo = new ModelAndView("toRegister");
        vo.addObject("code",code);
        return vo;
    }

    /**
     //     * 调用用户模块注册
     //     */
    @ResponseBody
    @PostMapping("/submit")
    public ResponseInfo<Object> register(@RequestBody String string){
        String s="";
        JSONObject jsonObject =JSONObject.parseObject(string);
        JSONObject data =jsonObject.getJSONObject("data");
        s="phone="+data.get("phone")+"&";
        s+="verifyCode="+data.get("verifyCode")+"&";
        s+="password="+data.get("password")+"&";
        s+="userCode="+data.get("userCode")+"&";
        s+="token="+data.get("token")+"&";
        s+="keyn=B6A05C25A3C98305F69CE720DF21AB5A";
        if(!MD5Util.string2MD5(s).equals(data.getString("sign")))
            throw new CustomException(ExecptionEnum.KEY_CANNOT_BE_RESOLVED);
        UserRegisterDTO dto = JSONObject.parseObject(data.toString(), UserRegisterDTO.class);
        log.info("dto: {}", dto);
        ResponseInfo<Object> res = customerManageFeign.register(dto);
        if (!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW, res);
        return ResponseInfo.success(new HashMap<String,String>(){{put("downloadUrl",downloadUrl);}});


    }
    /**
     * 获取验证码
     */
    @ResponseBody
    @PostMapping("/sendCode")
    public ResponseInfo<Object> sendCode(@RequestBody String string, HttpServletRequest request){
        String json;
        JSONObject jsonObject =JSONObject.parseObject(string);
        JSONObject data =jsonObject.getJSONObject("data");
        String  s="phone="+data.get("phone")+"&";
        s+="token="+data.get("token")+"&";
        s+="tokenType="+data.get("tokenType")+"&";
        s+="type="+data.get("type")+"&";
        s+="keyn=B6A05C25A3C98305F69CE720DF21AB5A";
        if(!MD5Util.string2MD5(s).equals(data.getString("sign")))
            throw new CustomException(ExecptionEnum.KEY_CANNOT_BE_RESOLVED);
        SendCodeDTO dto = JSONObject.parseObject(data.toString(), SendCodeDTO.class);
        return customerManageFeign.code(dto,getIpAddress(request));

    }

    /**
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


}
