package com.ruoyi.web.controller.system;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.dto.InvoiceDTO;
import com.ruoyi.feign.OrderManageFeign;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.utils.ResponseInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.domain.OrderInvoice;
import com.ruoyi.service.IOrderInvoiceService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 订单发票 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-08-02
 */
@Controller
@RequestMapping("/system/orderInvoice")
public class OrderInvoiceController extends BaseController
{
    private String prefix = "system/orderInvoice";
    @Autowired
	private OrderManageFeign orderManageFeign;
	@Autowired
	private IOrderInvoiceService orderInvoiceService;
	
	@RequiresPermissions("system:orderInvoice:view")
	@GetMapping()
	public String orderInvoice()
	{
	    return prefix + "/orderInvoice";
	}
	
	/**
	 * 查询订单发票列表
	 */
	@RequiresPermissions("system:orderInvoice:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(OrderInvoice orderInvoice)
	{
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer page = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		ResponseInfo<Page<InvoiceDTO>> res =orderManageFeign.getInvoceDTO(orderInvoice.getType(), orderInvoice.getStatus(), page, pageSize);
		return getDataTable(res.getData().getRecords(),res.getData().getTotal());
	}
	
	
	/**
	 * 导出订单发票列表
	 */
	@RequiresPermissions("system:orderInvoice:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OrderInvoice orderInvoice)
    {
    	List<OrderInvoice> list = orderInvoiceService.selectOrderInvoiceList(orderInvoice);
        ExcelUtil<OrderInvoice> util = new ExcelUtil<OrderInvoice>(OrderInvoice.class);
        return util.exportExcel(list, "orderInvoice");
    }
	
	/**
	 * 新增订单发票
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存订单发票
	 */
	@RequiresPermissions("system:orderInvoice:add")
	@Log(title = "订单发票", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(OrderInvoice orderInvoice)
	{		
		return toAjax(orderInvoiceService.insertOrderInvoice(orderInvoice));
	}

	/**
	 * 修改订单发票
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id, ModelMap mmap)
	{
		OrderInvoice orderInvoice = orderInvoiceService.selectOrderInvoiceById(id);
		mmap.put("orderInvoice", orderInvoice);
	    return prefix + "/edit";
	}
	/**
	 * 修改订单发票
	 */
	@GetMapping("/updateInvoice")
	public AjaxResult updateInvoice( String ids)
	{
		//TODO 修改发票信息
		//orderInvoiceService.updateInvoice(ids);
		ResponseInfo<String> result =orderManageFeign.batchUpdateInvoice(ShiroUtils.getUserId()+"",ids,2);
		if(!result.isSuccess()){
			return AjaxResult.error(result.getMessage());
		}
		return  AjaxResult.success();
	}
	/**
	 * 修改保存订单发票
	 */
	@RequiresPermissions("system:orderInvoice:edit")
	@Log(title = "订单发票", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(OrderInvoice orderInvoice)
	{		
		return toAjax(orderInvoiceService.updateOrderInvoice(orderInvoice));
	}
	
	/**
	 * 删除订单发票
	 */
	@RequiresPermissions("system:orderInvoice:remove")
	@Log(title = "订单发票", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(orderInvoiceService.deleteOrderInvoiceByIds(ids));
	}
	
}
