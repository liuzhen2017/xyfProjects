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
import com.ruoyi.domain.PayType;
import com.ruoyi.service.IPayTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 支付类型 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-24
 */
@Controller
@RequestMapping("/system/payType")
public class PayTypeController extends BaseController
{
    private String prefix = "system/payType";
	
	@Autowired
	private IPayTypeService payTypeService;
	
	@RequiresPermissions("system:payType:view")
	@GetMapping()
	public String payType()
	{
	    return prefix + "/payType";
	}
	
	/**
	 * 查询支付类型列表
	 */
	@RequiresPermissions("system:payType:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(PayType payType)
	{
		startPage();
        List<PayType> list = payTypeService.selectPayTypeList(payType);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出支付类型列表
	 */
	@RequiresPermissions("system:payType:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(PayType payType)
    {
    	List<PayType> list = payTypeService.selectPayTypeList(payType);
        ExcelUtil<PayType> util = new ExcelUtil<PayType>(PayType.class);
        return util.exportExcel(list, "payType");
    }
	
	/**
	 * 新增支付类型
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存支付类型
	 */
	@RequiresPermissions("system:payType:add")
	@Log(title = "支付类型", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(PayType payType)
	{		
		return toAjax(payTypeService.insertPayType(payType));
	}

	/**
	 * 修改支付类型
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		PayType payType = payTypeService.selectPayTypeById(id);
		mmap.put("payType", payType);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存支付类型
	 */
	@RequiresPermissions("system:payType:edit")
	@Log(title = "支付类型", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(PayType payType)
	{		
		return toAjax(payTypeService.updatePayType(payType));
	}
	
	/**
	 * 删除支付类型
	 */
	@RequiresPermissions("system:payType:remove")
	@Log(title = "支付类型", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(payTypeService.deletePayTypeByIds(ids));
	}
	
}
