package com.ruoyi.controller.product;

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
import com.ruoyi.domain.ProDetails;
import com.ruoyi.service.IProDetailsService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 商品参数详情 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proDetails")
public class ProDetailsController extends BaseController
{
    private String prefix = "system/proDetails";
	
	@Autowired
	private IProDetailsService proDetailsService;
	
	@RequiresPermissions("system:proDetails:view")
	@GetMapping()
	public String proDetails()
	{
	    return prefix + "/proDetails";
	}
	
	/**
	 * 查询商品参数详情列表
	 */
	@RequiresPermissions("system:proDetails:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProDetails proDetails)
	{
		startPage();
        List<ProDetails> list = proDetailsService.selectProDetailsList(proDetails);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出商品参数详情列表
	 */
	@RequiresPermissions("system:proDetails:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProDetails proDetails)
    {
    	List<ProDetails> list = proDetailsService.selectProDetailsList(proDetails);
        ExcelUtil<ProDetails> util = new ExcelUtil<ProDetails>(ProDetails.class);
        return util.exportExcel(list, "proDetails");
    }
	
	/**
	 * 新增商品参数详情
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存商品参数详情
	 */
	@RequiresPermissions("system:proDetails:add")
	@Log(title = "商品参数详情", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProDetails proDetails)
	{		
		return toAjax(proDetailsService.insertProDetails(proDetails));
	}

	/**
	 * 修改商品参数详情
	 */
	@GetMapping("/edit/{proId}")
	public String edit(@PathVariable("proId") Long proId, ModelMap mmap)
	{
		ProDetails proDetails = proDetailsService.selectProDetailsById(proId);
		mmap.put("proDetails", proDetails);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存商品参数详情
	 */
	@RequiresPermissions("system:proDetails:edit")
	@Log(title = "商品参数详情", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProDetails proDetails)
	{		
		return toAjax(proDetailsService.updateProDetails(proDetails));
	}
	
	/**
	 * 删除商品参数详情
	 */
	@RequiresPermissions("system:proDetails:remove")
	@Log(title = "商品参数详情", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(proDetailsService.deleteProDetailsByIds(ids));
	}
	
}
