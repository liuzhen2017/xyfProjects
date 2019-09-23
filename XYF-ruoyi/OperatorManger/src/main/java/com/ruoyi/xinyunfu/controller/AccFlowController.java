package com.ruoyi.web.controller.xinyunfu;

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
import com.ruoyi.xinyunfu.domain.AccFlow;
import com.ruoyi.xinyunfu.service.IAccFlowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 账户流水 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Controller
@RequestMapping("/xinyunfu/accFlow/")
public class AccFlowController extends BaseController
{
    private String prefix = "xinyunfu/accFlow";
	
	@Autowired
	private IAccFlowService accFlowService;
	
	@RequiresPermissions("xinyunfu:accFlow:view")
	@GetMapping()
	public String accFlow()
	{
	    return prefix + "/accFlow";
	}
	
	/**
	 * 查询账户流水列表
	 */
	@RequiresPermissions("xinyunfu:accFlow:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(AccFlow accFlow)
	{
		startPage();
        List<AccFlow> list = accFlowService.selectAccFlowList(accFlow);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出账户流水列表
	 */
	@RequiresPermissions("xinyunfu:accFlow:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(AccFlow accFlow)
    {
    	List<AccFlow> list = accFlowService.selectAccFlowList(accFlow);
        ExcelUtil<AccFlow> util = new ExcelUtil<AccFlow>(AccFlow.class);
        return util.exportExcel(list, "accFlow");
    }
	
	/**
	 * 新增账户流水
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存账户流水
	 */
	@RequiresPermissions("xinyunfu:accFlow:add")
	@Log(title = "账户流水", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(AccFlow accFlow)
	{		
		return toAjax(accFlowService.insertAccFlow(accFlow));
	}

	/**
	 * 修改账户流水
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		AccFlow accFlow = accFlowService.selectAccFlowById(id);
		mmap.put("accFlow", accFlow);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存账户流水
	 */
	@RequiresPermissions("xinyunfu:accFlow:edit")
	@Log(title = "账户流水", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(AccFlow accFlow)
	{		
		return toAjax(accFlowService.updateAccFlow(accFlow));
	}
	
	/**
	 * 删除账户流水
	 */
	@RequiresPermissions("xinyunfu:accFlow:remove")
	@Log(title = "账户流水", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(accFlowService.deleteAccFlowByIds(ids));
	}
	
}
