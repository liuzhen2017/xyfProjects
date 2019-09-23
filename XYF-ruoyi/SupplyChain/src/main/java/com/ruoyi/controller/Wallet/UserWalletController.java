package com.ruoyi.controller.Wallet;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.domain.UserWallet;
import com.ruoyi.dto.RProductDTO;
import com.ruoyi.feign.InfoCenterFeign;
import com.ruoyi.feign.WalletAcctManageFeign;
import com.ruoyi.service.IUserWalletService;
import com.ruoyi.utils.ResponseInfo;
import lombok.ToString;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

/**
 * 用户钱包 信息操作处理
 *
 * @author ruoyi
 * @date 2019-08-20
 */
@Controller
@RequestMapping("/system/userWallet")
public class UserWalletController extends BaseController {
    private String prefix = "system/userWallet";

    @Autowired
    private IUserWalletService userWalletService;

    @Autowired
    private WalletAcctManageFeign walletAcctManageFeign;

    @Autowired
    private InfoCenterFeign infoCenterFeign;

    @Autowired
    private Environment env;

    @Value("${cusUser.upgrade.validPhone}")
    private String validPhone;

    @Value("${cusUser.upgrade.walletTemplateNo}")
    private String walletTemplateNo;

    @RequiresPermissions("system:userWallet:view")
    @GetMapping()
    public String userWallet() {
        return prefix + "/userWallet";
    }

    /**
     * 查询用户钱包列表
     */
    @RequiresPermissions("system:userWallet:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(UserWallet userWallet) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer page = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        ResponseInfo<Page<UserWallet>> res = walletAcctManageFeign.getUserWalletPage(userWallet, page, pageSize);
        return getDataTable(res.getData().getRecords(),res.getData().getTotal());
    }


    /**
     * 导出用户钱包列表
     */
    @RequiresPermissions("system:userWallet:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(UserWallet userWallet) {
        List<UserWallet> list = userWalletService.selectUserWalletList(userWallet);
        ExcelUtil<UserWallet> util = new ExcelUtil<UserWallet>(UserWallet.class);
        return util.exportExcel(list, "userWallet");
    }

    /**
     * 新增用户钱包
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存用户钱包
     */
    @RequiresPermissions("system:userWallet:add")
    @Log(title = "用户钱包", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(UserWallet userWallet) {
        return toAjax(userWalletService.insertUserWallet(userWallet));
    }

    /**
     * 修改用户钱包
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        UserWallet userWallet = userWalletService.selectUserWalletById(id);
        mmap.put("userWallet", userWallet);
        return prefix + "/edit";
    }
    /**
     * 用户钱包充值
     */
    @GetMapping("/recharge")
    public String recharge(ModelMap mmap) {
        mmap.put("validPhone",validPhone);
        return prefix + "/recharge";
    }
    /**
     * 修改保存用户钱包
     */
    @RequiresPermissions("system:userWallet:edit")
    @Log(title = "用户钱包", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(UserWallet userWallet) {
        return toAjax(userWalletService.updateUserWallet(userWallet));
    }

    /**
     * 删除用户钱包
     */
    @RequiresPermissions("system:userWallet:remove")
    @Log(title = "用户钱包", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(userWalletService.deleteUserWalletByIds(ids));
    }


//===============================================向钱包充值=============================================================


    /**
     * 校验验证码
     * @param phone
     * @param code
     * @param templateNo
     * @return
     */
    @GetMapping("/checkSendMsg")
    public ResponseInfo<Object> checkSendMsg(@RequestParam("phone")String phone,
                                             @RequestParam("code")String code,
                                             @RequestParam("templateNo")String templateNo){
        phone=env.getProperty("InfoCenter.sendMsg.phoneNo", String.class);
        templateNo="78d281b57529e76803bc685e86797ccb";
        String temp = "{\"phone\": \"%s\", \"code\":\"%s\", \"templateNo\":\"%s\"}";
        Boolean sendResult =  infoCenterFeign.checkSendMsg(String.format(temp, phone, code, templateNo)).isSuccess();
        if (!sendResult){
            return ResponseInfo.error("验证码错误,请重新输入");
        }
        return ResponseInfo.success(sendResult);
    }

    /**
     * 根据账户(accountNo)给用户钱包充值
     *
     * @return
     */
    @PostMapping("/accRecharge")
    @ResponseBody
    public AjaxResult accRecharge(@RequestBody String body) {
        JSONObject jb =JSONObject.parseObject(URLDecoder.decode(body).replace("=",""));
        String code =jb.getString("code");
        BigDecimal money =jb.getBigDecimal("money");
        if(this.checkSendMsg(validPhone,code,walletTemplateNo).isSuccess()){
            Boolean flag=walletAcctManageFeign.recharge(jb.getString("accountNo"), money).isSuccess();
            if (flag){
                return success();
            }else{
                return AjaxResult.error("充值失败,请稍后重试");
            }
        }else {
            return AjaxResult.error("短信验证失败!");
        }
    }
    /**
     * 发送短信验证吗
     */
    @Log(title = "用户钱包", businessType = BusinessType.UPDATE)
    @PostMapping("/sendValidCode")
    @ResponseBody
    public AjaxResult sendValidCode()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("recvObject",validPhone);
        jsonObject.put("sendType","01");
        jsonObject.put("templateNo",walletTemplateNo);
        ResponseInfo<String> responseInfo = infoCenterFeign.sendMsg(jsonObject.toJSONString());
        if(responseInfo.isSuccess()){
            return toAjax(true);
        }
        return error(responseInfo.getMessage());
    }

}
