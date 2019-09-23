package com.ruoyi.controller.ebank;

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
import com.ruoyi.domain.AcctManger;
import com.ruoyi.service.IAcctMangerService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 供应链账户 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
@Controller
@RequestMapping("/system/acctManger")
public class AcctMangerController extends BaseController
{
    private String prefix = "system/acctManger";
	
	@Autowired
	private IAcctMangerService acctMangerService;
	
	@RequiresPermissions("system:acctManger:view")
	@GetMapping()
	public String acctManger()
	{
	    return prefix + "/acctManger";
	}
	
	/**
	 * 查询账户列表
	 */
	@RequiresPermissions("system:acctManger:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AcctManger acctManger)
	{
		startPage();
        List<AcctManger> list = acctMangerService.selectAcctMangerList(acctManger);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出账户列表
	 */
	@RequiresPermissions("system:acctManger:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AcctManger acctManger)
    {
    	List<AcctManger> list = acctMangerService.selectAcctMangerList(acctManger);
        ExcelUtil<AcctManger> util = new ExcelUtil<AcctManger>(AcctManger.class);
        return util.exportExcel(list, "acctManger");
    }
	
	/**
	 * 新增账户
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存账户
	 */
	@RequiresPermissions("system:acctManger:add")
	@Log(title = "账户", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AcctManger acctManger)
	{

		return toAjax(acctMangerService.insertAcctManger(acctManger));
	}

	/**
	 * 修改账户
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		AcctManger acctManger = acctMangerService.selectAcctMangerById(id);
		mmap.put("acctManger", acctManger);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存账户
	 */
	@RequiresPermissions("system:acctManger:edit")
	@Log(title = "账户", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AcctManger acctManger)
	{		
		return toAjax(acctMangerService.updateAcctManger(acctManger));
	}
	
	/**
	 * 删除账户
	 */
	@RequiresPermissions("system:acctManger:remove")
	@Log(title = "账户", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(acctMangerService.deleteAcctMangerByIds(ids));
	}
	
}
