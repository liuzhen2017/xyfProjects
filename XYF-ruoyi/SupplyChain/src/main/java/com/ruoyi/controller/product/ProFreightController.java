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
import com.ruoyi.domain.ProFreight;
import com.ruoyi.service.IProFreightService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 店铺邮费模板 信息操作处理
 *
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/ruoyi/proFreight")
public class ProFreightController extends BaseController
{
	private String prefix = "ruoyi/proFreight";

	@Autowired
	private IProFreightService proFreightService;

	@RequiresPermissions("ruoyi:proFreight:view")
	@GetMapping()
	public String proFreight()
	{
		return prefix + "/proFreight";
	}

	/**
	 * 查询店铺邮费模板列表
	 */
	@RequiresPermissions("ruoyi:proFreight:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(ProFreight proFreight)
	{
		startPage();
		List<ProFreight> list = proFreightService.selectProFreightList(proFreight);
		return getDataTable(list);
	}


	/**
	 * 导出店铺邮费模板列表
	 */
	@RequiresPermissions("ruoyi:proFreight:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(ProFreight proFreight)
	{
		List<ProFreight> list = proFreightService.selectProFreightList(proFreight);
		ExcelUtil<ProFreight> util = new ExcelUtil<ProFreight>(ProFreight.class);
		return util.exportExcel(list, "proFreight");
	}

	/**
	 * 新增店铺邮费模板
	 */
	@GetMapping("/add")
	public String add()
	{
		return prefix + "/add";
	}

	/**
	 * 新增保存店铺邮费模板
	 */
	@RequiresPermissions("ruoyi:proFreight:add")
	@Log(title = "店铺邮费模板", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(ProFreight proFreight)
	{
		return toAjax(proFreightService.insertProFreight(proFreight));
	}

	/**
	 * 修改店铺邮费模板
	 */
	@GetMapping("/edit/{freightId}")
	public String edit(@PathVariable("freightId") Long freightId, ModelMap mmap)
	{
		ProFreight proFreight = proFreightService.selectProFreightById(freightId);
		mmap.put("proFreight", proFreight);
		return prefix + "/edit";
	}

	/**
	 * 修改保存店铺邮费模板
	 */
	@RequiresPermissions("ruoyi:proFreight:edit")
	@Log(title = "店铺邮费模板", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(ProFreight proFreight)
	{
		return toAjax(proFreightService.updateProFreight(proFreight));
	}

	/**
	 * 删除店铺邮费模板
	 */
	@RequiresPermissions("ruoyi:proFreight:remove")
	@Log(title = "店铺邮费模板", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{
		return toAjax(proFreightService.deleteProFreightByIds(ids));
	}

}
