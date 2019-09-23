package com.ruoyi.controller.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.dto.BackOrderDTO;
import com.ruoyi.dto.CommdityInfoDTO;
import com.ruoyi.dto.SelectOrderDTO;
import com.ruoyi.feign.OrderManageFeign;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.utils.ResponseInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.domain.OrderItem;
import com.ruoyi.service.IOrderItemService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单子 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-07-27
 */
@Controller
@RequestMapping("/system/orderItem")
public class OrderItemController extends BaseController
{
    private String prefix = "system/orderItem";
	
	@Autowired
	private IOrderItemService orderItemService;

	@Autowired
    private OrderManageFeign orderManageFeign;
	
	@RequiresPermissions("system:orderItem:view")
	@GetMapping()
	public String orderItem()
	{
	    return prefix + "/orderItem";
	}

    /**
     * 根据订单号获取订单详细信息
     * @param itemNo
     * @param model
     * @return
     */
	@GetMapping("/Commdity/{itemNo}")
	public String commdity(@PathVariable("itemNo") String itemNo,Model model){
        ResponseInfo<CommdityInfoDTO> res = orderManageFeign.getCommditysByItemNo(itemNo);
        model.addAttribute("data",res.getData());
	    return prefix + "/Commdity";
    }
	
	/**
	 * 查询订单子列表
	 */
	@RequiresPermissions("system:orderItem:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SelectOrderDTO vo)
	{
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        com.ruoyi.utils.ResponseInfo<Page<BackOrderDTO>> res = orderManageFeign.findOrderItemByChainId(vo, pageNum, pageSize);
		return getDataTable(res.getData().getRecords(), Long.valueOf(res.getData().getTotal()));
	}

    /**
     * 获取所有物流信息
     * @return
     */
	@GetMapping("/getExpress")
    @ResponseBody
	public Object getExpress(){
        return orderManageFeign.getExpresss().getData();
    }
	
	/**
	 * 导出所有订单子列表
	 */
	@RequiresPermissions("system:orderItem:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SelectOrderDTO vo) {
        com.ruoyi.utils.ResponseInfo<Page<BackOrderDTO>> res = orderManageFeign.findOrderItemByChainId(vo, 1, 1000);
        ExcelUtil<BackOrderDTO> util = new ExcelUtil<BackOrderDTO>(BackOrderDTO.class);
        return util.exportExcel(res.getData().getRecords(), "订单列表");
    }

    /**
     * 导出选择的 子订单
     * @param vo
     * @return
     */
    @PostMapping("/exportSelect")
    @ResponseBody
    public AjaxResult exportSelect(SelectOrderDTO vo) {
        List<String> str = Arrays.asList(vo.getItemNos().split("-"));
        com.ruoyi.utils.ResponseInfo<Page<BackOrderDTO>> res = orderManageFeign.findOrderItemByChainId(vo, 1, 1000);
        ExcelUtil<BackOrderDTO> util = new ExcelUtil<BackOrderDTO>(BackOrderDTO.class);
        List<BackOrderDTO> collect = res.getData().getRecords().stream().filter(o -> str.contains(o.getItemNo())).collect(Collectors.toList());
        return util.exportExcel(collect, "订单列表");
    }
	
	/**
	 * 立即发货
	 */
	@GetMapping("/confirmDelivery/{itemNo}")
	public String confirmDelivery(@PathVariable("itemNo") String itemNo, Model model){
        model.addAttribute("itemNo",itemNo);
	    return "system/orderItem/confirmDelivery";
	}

    /**
     * 订单确认发货
     * @param orderItem
     * @return
     */
	@RequiresPermissions("system:orderItem:confirmDeliverySave")
	@Log(title = "立即发货", businessType = BusinessType.INSERT)
	@PostMapping("/confirmDeliverySave")
	@ResponseBody
	public AjaxResult confirmDeliverySave(OrderItem orderItem){
       if( orderManageFeign.confirmDelivery(ShiroUtils.getUserId().toString(),orderItem.getItemNo(),orderItem.getShippingCompName(),orderItem.getShippingSn()).isSuccess()){
           return success("发货成功！");
       };
		return error("很抱歉！发货失败！");
	}



	
}
