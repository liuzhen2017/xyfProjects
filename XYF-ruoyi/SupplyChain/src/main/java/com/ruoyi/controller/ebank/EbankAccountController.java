package com.ruoyi.web.controller.system;

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
import com.ruoyi.domain.EbankAccount;
import com.ruoyi.service.IEbankAccountService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 每个转账账户的虚拟账号 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
@Controller
@RequestMapping("/system/ebankAccount")
public class EbankAccountController extends BaseController
{
    private String prefix = "system/ebankAccount";
	
	@Autowired
	private IEbankAccountService ebankAccountService;
	
	@RequiresPermissions("system:ebankAccount:view")
	@GetMapping()
	public String ebankAccount()
	{
	    return prefix + "/ebankAccount";
	}

	@GetMapping("/SupplyAccount")
	public String SupplyAccount()
	{
		return "/system/SupplyAccount/ebankAccount";
	}

	/**
	 * 查询每个转账账户的虚拟账号列表
	 */
	@RequiresPermissions("system:ebankAccount:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(EbankAccount ebankAccount)
	{
		startPage();
        List<EbankAccount> list = ebankAccountService.selectEbankAccountList(ebankAccount);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出每个转账账户的虚拟账号列表
	 */
	@RequiresPermissions("system:ebankAccount:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EbankAccount ebankAccount)
    {
    	List<EbankAccount> list = ebankAccountService.selectEbankAccountList(ebankAccount);
        ExcelUtil<EbankAccount> util = new ExcelUtil<EbankAccount>(EbankAccount.class);
        return util.exportExcel(list, "ebankAccount");
    }
	
	/**
	 * 新增每个转账账户的虚拟账号
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存每个转账账户的虚拟账号
	 */
	@RequiresPermissions("system:ebankAccount:add")
	@Log(title = "每个转账账户的虚拟账号", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(EbankAccount ebankAccount)
	{		
		return toAjax(ebankAccountService.insertEbankAccount(ebankAccount));
	}

	/**
	 * 修改每个转账账户的虚拟账号
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		EbankAccount ebankAccount = ebankAccountService.selectEbankAccountById(id);
		mmap.put("ebankAccount", ebankAccount);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存每个转账账户的虚拟账号
	 */
	@RequiresPermissions("system:ebankAccount:edit")
	@Log(title = "每个转账账户的虚拟账号", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(EbankAccount ebankAccount)
	{		
		return toAjax(ebankAccountService.updateEbankAccount(ebankAccount));
	}
	
	/**
	 * 删除每个转账账户的虚拟账号
	 */
	@RequiresPermissions("system:ebankAccount:remove")
	@Log(title = "每个转账账户的虚拟账号", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ebankAccountService.deleteEbankAccountByIds(ids));
	}
	
}
