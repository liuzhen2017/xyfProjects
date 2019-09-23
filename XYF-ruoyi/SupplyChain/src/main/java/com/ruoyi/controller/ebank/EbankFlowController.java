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
import com.ruoyi.domain.EbankFlow;
import com.ruoyi.service.IEbankFlowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 每笔转账的转入转出记录，不包括合并转账 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-05
 */
@Controller
@RequestMapping("/ruoyi/ebankFlow")
public class EbankFlowController extends BaseController
{
    private String prefix = "ruoyi/ebankFlow";
	
	@Autowired
	private IEbankFlowService ebankFlowService;
	
	@RequiresPermissions("ruoyi:ebankFlow:view")
	@GetMapping()
	public String ebankFlow()
	{
	    return prefix + "/ebankFlow";
	}
	
	/**
	 * 查询每笔转账的转入转出记录，不包括合并转账列表
	 */
	@RequiresPermissions("ruoyi:ebankFlow:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(EbankFlow ebankFlow)
	{
		startPage();
        List<EbankFlow> list = ebankFlowService.selectEbankFlowList(ebankFlow);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出每笔转账的转入转出记录，不包括合并转账列表
	 */
	@RequiresPermissions("ruoyi:ebankFlow:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(EbankFlow ebankFlow)
    {
    	List<EbankFlow> list = ebankFlowService.selectEbankFlowList(ebankFlow);
        ExcelUtil<EbankFlow> util = new ExcelUtil<EbankFlow>(EbankFlow.class);
        return util.exportExcel(list, "ebankFlow");
    }
	
	/**
	 * 新增每笔转账的转入转出记录，不包括合并转账
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存每笔转账的转入转出记录，不包括合并转账
	 */
	@RequiresPermissions("ruoyi:ebankFlow:add")
	@Log(title = "每笔转账的转入转出记录，不包括合并转账", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(EbankFlow ebankFlow)
	{		
		return toAjax(ebankFlowService.insertEbankFlow(ebankFlow));
	}

	/**
	 * 修改每笔转账的转入转出记录，不包括合并转账
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		EbankFlow ebankFlow = ebankFlowService.selectEbankFlowById(id);
		mmap.put("ebankFlow", ebankFlow);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存每笔转账的转入转出记录，不包括合并转账
	 */
	@RequiresPermissions("ruoyi:ebankFlow:edit")
	@Log(title = "每笔转账的转入转出记录，不包括合并转账", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(EbankFlow ebankFlow)
	{		
		return toAjax(ebankFlowService.updateEbankFlow(ebankFlow));
	}
	
	/**
	 * 删除每笔转账的转入转出记录，不包括合并转账
	 */
	@RequiresPermissions("ruoyi:ebankFlow:remove")
	@Log(title = "每笔转账的转入转出记录，不包括合并转账", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(ebankFlowService.deleteEbankFlowByIds(ids));
	}
	
}
