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
import com.ruoyi.xinyunfu.domain.OrderMaster;
import com.ruoyi.xinyunfu.service.IOrderMasterService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 订单主 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Controller
@RequestMapping("/xinyunfu/orderMaster")
public class OrderMasterController extends BaseController
{
    private String prefix = "xinyunfu/orderMaster";
	
	@Autowired
	private IOrderMasterService orderMasterService;
	
	@RequiresPermissions("xinyunfu:orderMaster:view")
	@GetMapping()
	public String orderMaster()
	{
	    return prefix + "/orderMaster";
	}
	
	/**
	 * 查询订单主列表
	 */
	@RequiresPermissions("xinyunfu:orderMaster:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(OrderMaster orderMaster)
	{
		startPage();
        List<OrderMaster> list = orderMasterService.selectOrderMasterList(orderMaster);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出订单主列表
	 */
	@RequiresPermissions("xinyunfu:orderMaster:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderMaster orderMaster)
    {
    	List<OrderMaster> list = orderMasterService.selectOrderMasterList(orderMaster);
        ExcelUtil<OrderMaster> util = new ExcelUtil<OrderMaster>(OrderMaster.class);
        return util.exportExcel(list, "orderMaster");
    }
	
	/**
	 * 新增订单主
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存订单主
	 */
	@RequiresPermissions("xinyunfu:orderMaster:add")
	@Log(title = "订单主", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(OrderMaster orderMaster)
	{		
		return toAjax(orderMasterService.insertOrderMaster(orderMaster));
	}

	/**
	 * 修改订单主
	 */
	@GetMapping("/edit/{orderId}")
	public String edit(@PathVariable("orderId") Long orderId, ModelMap mmap)
	{
		OrderMaster orderMaster = orderMasterService.selectOrderMasterById(orderId);
		mmap.put("orderMaster", orderMaster);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存订单主
	 */
	@RequiresPermissions("xinyunfu:orderMaster:edit")
	@Log(title = "订单主", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(OrderMaster orderMaster)
	{		
		return toAjax(orderMasterService.updateOrderMaster(orderMaster));
	}
	
	/**
	 * 删除订单主
	 */
	@RequiresPermissions("xinyunfu:orderMaster:remove")
	@Log(title = "订单主", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(orderMasterService.deleteOrderMasterByIds(ids));
	}
	
}
