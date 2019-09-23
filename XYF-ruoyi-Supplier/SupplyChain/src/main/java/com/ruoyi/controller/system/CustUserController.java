package com.ruoyi.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
	public TableDataInfo list(CustUser custUser)
	{
		startPage();
        List<CustUser> list = custUserService.selectCustUserList(custUser);
		return getDataTable(list);
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
	
}
