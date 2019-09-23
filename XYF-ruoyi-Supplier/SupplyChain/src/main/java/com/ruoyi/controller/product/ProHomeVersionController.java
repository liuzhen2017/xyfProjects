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
import com.ruoyi.domain.ProHomeVersion;
import com.ruoyi.service.IProHomeVersionService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 版本 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/proHomeVersion")
public class
ProHomeVersionController extends BaseController
{
    private String prefix = "system/proHomeVersion";
	
	@Autowired
	private IProHomeVersionService proHomeVersionService;
	
	@RequiresPermissions("system:proHomeVersion:view")
	@GetMapping()
	public String proHomeVersion()
	{
	    return prefix + "/proHomeVersion";
	}
	
	/**
	 * 查询版本列表
	 */
	@RequiresPermissions("system:proHomeVersion:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProHomeVersion proHomeVersion)
	{
		startPage();
        List<ProHomeVersion> list = proHomeVersionService.selectProHomeVersionList(proHomeVersion);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出版本列表
	 */
	@RequiresPermissions("system:proHomeVersion:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProHomeVersion proHomeVersion)
    {
    	List<ProHomeVersion> list = proHomeVersionService.selectProHomeVersionList(proHomeVersion);
        ExcelUtil<ProHomeVersion> util = new ExcelUtil<ProHomeVersion>(ProHomeVersion.class);
        return util.exportExcel(list, "proHomeVersion");
    }
	
	/**
	 * 新增版本
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存版本
	 */
	@RequiresPermissions("system:proHomeVersion:add")
	@Log(title = "版本", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProHomeVersion proHomeVersion)
	{		
		return toAjax(proHomeVersionService.insertProHomeVersion(proHomeVersion));
	}

	/**
	 * 修改版本
	 */
	@GetMapping("/edit/{versionId}")
	public String edit(@PathVariable("versionId") Integer versionId, ModelMap mmap)
	{
		ProHomeVersion proHomeVersion = proHomeVersionService.selectProHomeVersionById(versionId);
		mmap.put("proHomeVersion", proHomeVersion);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存版本
	 */
	@RequiresPermissions("system:proHomeVersion:edit")
	@Log(title = "版本", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProHomeVersion proHomeVersion)
	{		
		return toAjax(proHomeVersionService.updateProHomeVersion(proHomeVersion));
	}
	
	/**
	 * 删除版本
	 */
	@RequiresPermissions("system:proHomeVersion:remove")
	@Log(title = "版本", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(proHomeVersionService.deleteProHomeVersionByIds(ids));
	}
	
}
