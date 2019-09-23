package com.ruoyi.controller.system;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.VO.CusUserUpgradeVo;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.dto.CustUserDto;
import com.ruoyi.dto.PageQueryInnerDTO;
import com.ruoyi.feign.CustomerManageFeign;
import com.ruoyi.feign.InfoCenterFeign;
import com.ruoyi.utils.ResponseInfo;
import org.apache.poi.ss.formula.functions.T;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.domain.CustUser;
import com.ruoyi.service.ICustUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;


/**
 * 用户 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-03
 */
@Controller
@RequestMapping("/system/custUser")
public class CustUserController extends BaseController
{
    private String prefix = "system/custUser";
	
	@Autowired
	private ICustUserService custUserService;

    @Autowired
    private CustomerManageFeign customerManageFeign;

    @Autowired
	private InfoCenterFeign infoCenterFeign;

	@Value("${cusUser.upgrade.validPhone}")
	private String validPhone;

	@Value("${cusUser.upgrade.templateNo}")
	private String templateNo;

	@RequiresPermissions("system:custUser:view")
	@GetMapping()
	public String custUser()
	{
	    return prefix + "/custUser";
	}

    /**
     * 查询用户列表
     */
    @RequiresPermissions("system:custUser:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(PageQueryInnerDTO pageQueryInnerDTO)
    {
        pageQueryInnerDTO.setUserCode(null);
		PageDomain pageDomain = TableSupport.buildPageRequest();
		pageQueryInnerDTO.setPageNo(pageDomain.getPageNum());
		pageQueryInnerDTO.setPageSize(pageDomain.getPageSize());
		com.ruoyi.utils.ResponseInfo<PageQueryInnerDTO<CustUserDto>> responseInfo = customerManageFeign.listcs(pageQueryInnerDTO);
		if(responseInfo.isSuccess()){
			return getDataTable(responseInfo.getData().getList(),responseInfo.getData().getTotalCount());
		}
		return  null;
    }
	
	
	/**
	 * 导出用户列表
	 */
	@RequiresPermissions("system:custUser:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(CustUser custUser)
    {
    	List<CustUser> list = custUserService.selectCustUserList(custUser);
        ExcelUtil<CustUser> util = new ExcelUtil<CustUser>(CustUser.class);
        return util.exportExcel(list, "custUser");
    }
	
	/**
	 * 新增用户
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存用户
	 */
	@RequiresPermissions("system:custUser:add")
	@Log(title = "用户", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(CustUser custUser)
	{		
		return toAjax(custUserService.insertCustUser(custUser));
	}

	/**
	 * 修改用户
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		CustUser custUser = custUserService.selectCustUserById(id);
		mmap.put("custUser", custUser);
	    return prefix + "/edit";
	}
	/**
	 * 升级VIP
	 */
	@GetMapping("/upgradeVIP")
	public String upgradeVIP(ModelMap mmap)
	{
		mmap.put("validPhone",validPhone);
		return prefix + "/upgradevip";
	}
	/**
	 * 升级VIP
	 */
	@RequiresPermissions("system:custUser:upgradeVIP")
	@PostMapping("/upgradeVIP")
	@ResponseBody
	public AjaxResult upgradeVIP(@Validated CusUserUpgradeVo cusUserUpgradeVo)
	{
		JSONObject json = new JSONObject();
		json.put("phone",validPhone);
		json.put("code",cusUserUpgradeVo.getValidCode());
		json.put("templateNo",templateNo);
		Boolean sendResult =  infoCenterFeign.checkSendMsg(json.toJSONString()).isSuccess();
		if (!sendResult){
			return error(AjaxResult.Type.ERROR,"验证码错误！");

		}
		ResponseInfo<Object> objectResponseInfo = customerManageFeign.changeLevel(cusUserUpgradeVo.getUserNo(), 1);
		if (objectResponseInfo.isSuccess()){
			return success();
		}
		return error();
	}
	/**
	 * 发送短信验证吗
	 */
	@Log(title = "用户", businessType = BusinessType.UPDATE)
	@PostMapping("/sendValidCode")
	@ResponseBody
	public AjaxResult sendValidCode()
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("recvObject",validPhone);
		jsonObject.put("sendType","01");
		jsonObject.put("templateNo",templateNo);
		ResponseInfo<String> responseInfo = infoCenterFeign.sendMsg(jsonObject.toJSONString());
		if(responseInfo.isSuccess()){
			return toAjax(true);
		}
		return error(responseInfo.getMessage());
	}

	/**
	 * 修改保存用户
	 */
	@RequiresPermissions("system:custUser:edit")
	@Log(title = "用户", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(CustUser custUser)
	{		
		return toAjax(custUserService.updateCustUser(custUser));
	}
	
	/**
	 * 删除用户
	 */
	@RequiresPermissions("system:custUser:remove")
	@Log(title = "用户", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(custUserService.deleteCustUserByIds(ids));
	}


    /**
     * 用户升级
     */
    @GetMapping("/upgrade")
    public String upgrade()
    {
        return prefix + "/upgrade";
    }

    /**
     * 修改保存用户升级
     */
    @RequiresPermissions("system:custUser:upgrade")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @GetMapping("/saveUpgrade")
    @ResponseBody
    public AjaxResult upgradeSave(CustUser custUser)
    {
        return toAjax(custUserService.updateCustUser(custUser));
    }
}
