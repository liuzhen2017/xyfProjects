package com.ruoyi.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.ruoyi.common.core.page.*;
import com.ruoyi.domain.*;
import com.ruoyi.dto.*;
import org.apache.shiro.authz.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.utils.ResponseInfo;

import java.util.*;

/**
 * @author xxb
 * @date 2019/9/3
 * @Description :报表代理微服务
 */

@FeignClient(value = "XYF-PROXYSERVICE", decode404 = true)
public interface ProxyServiceFeign {
    /**
     * 用户注册量折线图
     * @return
     */
    @PostMapping(value = "/userdata/reportLine")
    public Map<String,Object>  reportUserRegistLine();


    /**
     * 用户注册量图表
     * @return
     */
    @PostMapping(value = "/userdata/reportPie")
    public Map<String,Object>  reportUserRegistPie();


    /**
     * 订单购买量折线图
     * @return
     */
    @PostMapping(value = "/orderdata/reportLine")
    public Map<String,Object>      reportOrderBuyLine();

    /**
     * 出账 入账 金额折线图
     */
    @PostMapping(value = "/transferdata/reportLine")
    public Map<String,Object>      reportTransferLine();

    /**
     * 粉丝推荐量 树状图
     */
    @PostMapping(value = "/fansdata/reportBar")
    public Map<String,Object>      reportFansReferBar();

    /**
     * 用户注册量分页查询
     *
     * @param user
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/userdata/queryUserRegistPage")
    public List<CustUser> selectUserRegistList(CustUser user);


    /**
     * 订单购买量分页查询
     *
     * @param orderItem
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/orderdata/queryOrderBuyPage")
    public List<OrderItem> selectOrderBuyList(OrderItem orderItem);


    /**
     * 入账出账分页查询
     *
     * @param EbankFlow
     * @param page
     * @param pageSize
     * @return
     */
    @PostMapping(value = "/transferdata/queryTransferPage")
    public List<EbankFlow> selectTransferList(EbankFlow ebankFlow);
}
