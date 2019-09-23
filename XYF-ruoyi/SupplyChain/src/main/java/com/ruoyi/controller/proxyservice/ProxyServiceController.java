package com.ruoyi.controller.proxyservice;

import com.alibaba.druid.support.json.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.ruoyi.common.core.controller.*;
import com.ruoyi.common.core.page.*;
import com.ruoyi.domain.*;
import com.ruoyi.dto.*;
import com.ruoyi.feign.*;
import org.apache.shiro.authz.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.utils.ResponseInfo;

import javax.xml.ws.*;
import java.util.*;

/**
 * @author xxb
 * @date 2019/9/3
 * @Description :报表操作处理
 */
@Controller
@RequestMapping("/proxyService")
public class ProxyServiceController extends BaseController {
      @Autowired
      private ProxyServiceFeign  proxyServiceFeign;

    /**
     * 获取用户注册量折线图数据
     * @return
     */
    @RequestMapping("/userRegistLine")
    @ResponseBody
      public Map<String,Object> userRegistLine(){
        Map<String,Object> userRegistData=proxyServiceFeign.reportUserRegistLine();
        return userRegistData;
      }

    @RequestMapping("/userRegistPie")
    @ResponseBody
    public Map<String,Object> userRegistPie(){
        Map<String,Object> userRegistData=proxyServiceFeign.reportUserRegistPie();
        return userRegistData;
    }

    /**
     * 获取订单购买量折线图数据
     * @return
     */
    @RequestMapping("/orderBuyLine")
    @ResponseBody
    public Map<String,Object> orderBuyLine(){
        Map<String,Object> orderBuyData=proxyServiceFeign.reportOrderBuyLine();
        return orderBuyData;
    }

    /**
     * 获取入账 出账金额折线图数据
     * @return
     */
    @RequestMapping("/transferLine")
    @ResponseBody
    public Map<String,Object> transferLine(){
        Map<String,Object> transferData=proxyServiceFeign.reportTransferLine();

        return transferData;
    }

    /**
     * 粉丝推荐树状图图数据
     * @return
     */
    @RequestMapping("/fansReferBar")
    @ResponseBody
    public Map<String,Object> reportFansReferBar() {
        Map<String, Object> transferData = proxyServiceFeign.reportFansReferBar();

        return transferData;
    }

    /**
     * 查询用户注册列表
     */
    @PostMapping("/queryUserRegistPage")
    @ResponseBody
    public TableDataInfo listUserRegistPage(CustUser user)
    {
        startPage();
        List<CustUser> list = proxyServiceFeign.selectUserRegistList(user);
        return getDataTable(list);
    }

    /**
     * 查询订单购买列表
     */
    @PostMapping("/queryOrderBuyPage")
    @ResponseBody
    public TableDataInfo listOrderBuyPage(OrderItem orderItem)
    {
        startPage();
        List<OrderItem> list = proxyServiceFeign.selectOrderBuyList(orderItem);
        return getDataTable(list);
    }

    /**
     * 查询入出账列表
     */
    @PostMapping("/queryTransferPage")
    @ResponseBody
    public TableDataInfo listTransferPage(EbankFlow ebankFlow)
    {
//        startPage();
        List<EbankFlow> list = proxyServiceFeign.selectTransferList(ebankFlow);
        return getDataTable(list);
    }
}
