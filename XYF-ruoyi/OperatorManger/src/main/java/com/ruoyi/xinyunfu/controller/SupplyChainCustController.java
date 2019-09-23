package com.ruoyi.xinyunfu.controller;

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
import com.ruoyi.xinyunfu.domain.SupplyChainCust;
import com.ruoyi.xinyunfu.service.ISupplyChainCustService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 供应链 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Controller
@RequestMapping("/xinyunfu/supplyChainCust")
public class SupplyChainCustController extends BaseController
{
    private String prefix = "xinyunfu/supplyChainCust";
	
	@Autowired
	private ISupplyChainCustService supplyChainCustService;
	
	@RequiresPermissions("xinyunfu:supplyChainCust:view")
	@GetMapping()
	public String supplyChainCust()
	{
	    return prefix + "/supplyChainCust";
	}
	
	/**
	 * 查询供应链列表
	 */
	@RequiresPermissions("xinyunfu:supplyChainCust:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SupplyChainCust supplyChainCust)
	{
		startPage();
        List<SupplyChainCust> list = supplyChainCustService.selectSupplyChainCustList(supplyChainCust);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出供应链列表
	 */
	@RequiresPermissions("xinyunfu:supplyChainCust:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SupplyChainCust supplyChainCust)
    {
    	List<SupplyChainCust> list = supplyChainCustService.selectSupplyChainCustList(supplyChainCust);
        ExcelUtil<SupplyChainCust> util = new ExcelUtil<SupplyChainCust>(SupplyChainCust.class);
        return util.exportExcel(list, "supplyChainCust");
    }
	
	/**
	 * 新增供应链
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存供应链
	 */
	@RequiresPermissions("xinyunfu:supplyChainCust:add")
	@Log(title = "供应链", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SupplyChainCust supplyChainCust)
	{		
		return toAjax(supplyChainCustService.insertSupplyChainCust(supplyChainCust));
	}

	/**
	 * 修改供应链
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		SupplyChainCust supplyChainCust = supplyChainCustService.selectSupplyChainCustById(id);
		mmap.put("supplyChainCust", supplyChainCust);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存供应链
	 */
	@RequiresPermissions("xinyunfu:supplyChainCust:edit")
	@Log(title = "供应链", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SupplyChainCust supplyChainCust)
	{		
		return toAjax(supplyChainCustService.updateSupplyChainCust(supplyChainCust));
	}
	
	/**
	 * 删除供应链
	 */
	@RequiresPermissions("xinyunfu:supplyChainCust:remove")
	@Log(title = "供应链", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(supplyChainCustService.deleteSupplyChainCustByIds(ids));
	}
	
}
