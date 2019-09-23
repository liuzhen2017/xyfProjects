package com.xinyunfu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xinyunfu.aop.CustomException;
import com.xinyunfu.aop.ExecptionEnum;
import com.xinyunfu.constant.Common;
import com.xinyunfu.constant.Redis;
import com.xinyunfu.dto.customer.ShippingAddressInfoDTO;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderConsigneeMapper;
import com.xinyunfu.model.OrderConsignee;
import com.xinyunfu.service.IOrderConsigneeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.utils.JsonUtils;
import com.xinyunfu.utils.RedisCommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 订单收货表 服务实现类
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
@Slf4j
@Service
public class OrderConsigneeServiceImpl extends ServiceImpl<OrderConsigneeMapper, OrderConsignee> implements IOrderConsigneeService {

    @Autowired
    private RedisCommonUtil redis;

    @Autowired
    private OrderConsigneeMapper orderConsigneeMapper;

    @Autowired
    private CustomerManageFeign customerManageFeign;

    /**
     * 根据订单编号获取 送货地址详细信息
     *
     * @param currentUserId
     * @param orderNo
     * @return
     */
    @Override
    public OrderConsignee getConsigneeByOrderNo(String currentUserId, String orderNo) {
        String key = Redis.KEY_ORDERCONSIGNEE+currentUserId+orderNo;
        if(redis.exists(key))
            return (OrderConsignee) redis.getCache(key);
        OrderConsignee one = super.getOne(new LambdaQueryWrapper<OrderConsignee>()
                .eq(OrderConsignee::getOrderNo, orderNo)
                .eq(OrderConsignee::getEnable, Common.ENABLE), true);
        if(null == one)
            throw new CustomException(ExecptionEnum.BY_ORDERNO_GET_CONSIGNEE_FAIL);
        if(!redis.setCache(key,one,Redis.EXC_REDIS)){
            log.warn("[订单服务]==========>订单收货信息放入缓存失败！");
        }
        return one;
    }

    /**
     * 添加收货地址
     *
     * @param currentUserId
     * @param itemNos
     * @param addressId
     * @return
     */
    @Override
    @Transactional
    public boolean add(String currentUserId,List<String> itemNos, long addressId) {
        ResponseInfo<ShippingAddressInfoDTO> res = customerManageFeign.getAddress(addressId);
        if(!res.isSuccess())
            throw new CustomException(ExecptionEnum.TYPE_NOTLOG_NOTSHOW,res.getCode(),res.getMessage());
        ShippingAddressInfoDTO address = res.getData();
        if(!currentUserId.equals(address.getUserNo().toString()))
            throw new CustomException(ExecptionEnum.ADDRESS_INFO_ERROR);
        //循环子订单列表 保存送货地址
        for (String itemNo : itemNos) {
            OrderConsignee oc = new OrderConsignee();
            oc.setOrderNo(itemNo);
            oc.setProvince(address.getAreaInfo().getProvince().getAreaLongName());
            oc.setCity(address.getAreaInfo().getCity().getAreaLongName());
            oc.setRegion(address.getAreaInfo().getRegion().getAreaLongName());
            if(address.getAreaInfo().getTown() != null) {
                oc.setTown(address.getAreaInfo().getTown().getAreaLongName());
            }
            oc.setAddress(address.getAddress());
            if(StringUtils.isNotEmpty(address.getPostCode())){
                 oc.setPostCode(Integer.valueOf(address.getPostCode()));
            }
            oc.setConsigneeName(address.getName());
            oc.setPhone(address.getPhone());
            oc.setCreatedBy(currentUserId);
            oc.setUpdatedBy(currentUserId);
            if(orderConsigneeMapper.insert(oc) != 1)
                throw new CustomException(ExecptionEnum.SAVE_CONSIGNEE_FAIL);
        }
        return true;
    }

}
