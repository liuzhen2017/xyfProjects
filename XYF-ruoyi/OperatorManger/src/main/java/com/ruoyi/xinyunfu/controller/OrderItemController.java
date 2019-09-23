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
import com.ruoyi.xinyunfu.domain.OrderItem;
import com.ruoyi.xinyunfu.service.IOrderItemService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 订单子 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-28
 */
@Controller
@RequestMapping("/xinyunfu/orderItem")
public class OrderItemController extends BaseController
{
    private String prefix = "xinyunfu/orderItem";
	
	@Autowired
	private IOrderItemService orderItemService;
	
	@RequiresPermissions("xinyunfu:orderItem:view")
	@GetMapping()
	public String orderItem()
	{
	    return prefix + "/orderItem";
	}
	
	/**
	 * 查询订单子列表
	 */
	@RequiresPermissions("xinyunfu:orderItem:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(OrderItem orderItem)
	{
		startPage();
        List<OrderItem> list = orderItemService.selectOrderItemList(orderItem);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出订单子列表
	 */
	@RequiresPermissions("xinyunfu:orderItem:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderItem orderItem)
    {
    	List<OrderItem> list = orderItemService.selectOrderItemList(orderItem);
        ExcelUtil<OrderItem> util = new ExcelUtil<OrderItem>(OrderItem.class);
        return util.exportExcel(list, "orderItem");
    }
	
	/**
	 * 新增订单子
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存订单子
	 */
	@RequiresPermissions("xinyunfu:orderItem:add")
	@Log(title = "订单子", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(OrderItem orderItem)
	{		
		return toAjax(orderItemService.insertOrderItem(orderItem));
	}

	/**
	 * 修改订单子
	 */
	@GetMapping("/edit/{itemId}")
	public String edit(@PathVariable("itemId") Long itemId, ModelMap mmap)
	{
		OrderItem orderItem = orderItemService.selectOrderItemById(itemId);
		mmap.put("orderItem", orderItem);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存订单子
	 */
	@RequiresPermissions("xinyunfu:orderItem:edit")
	@Log(title = "订单子", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(OrderItem orderItem)
	{		
		return toAjax(orderItemService.updateOrderItem(orderItem));
	}
	
	/**
	 * 删除订单子
	 */
	@RequiresPermissions("xinyunfu:orderItem:remove")
	@Log(title = "订单子", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(orderItemService.deleteOrderItemByIds(ids));
	}
	
}
