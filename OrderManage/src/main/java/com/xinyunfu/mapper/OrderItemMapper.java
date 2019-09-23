package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.dto.back.BackOrderDTO;
import com.xinyunfu.dto.back.SelectOrderDTO;
import com.xinyunfu.dto.customer.PageCountDTO;
import com.xinyunfu.model.OrderItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 订单子表 Mapper 接口
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {


    @Update("update order_item set enable = 0,pay_status = 3 where item_no = #{itemNo}")
    Integer delOrder(@Param("itemNo") String itemNo);

    @Update("update order_item set status = #{status} where item_no = #{itemNo}")
    Integer updateStatus(@Param("itemNo") String itemNo,@Param("status") Integer status);

    @Select("select status from order_item where item_no = #{itemNo}")
    Integer getStatus(@Param("itemNo") String itemNo);

    List<OrderItem> getItemNos(@Param("currentUserId") String currentUserId,@Param("key") String key);

    String getUserPageCount(@Param("no") String no);

    Page<BackOrderDTO> findOrderItemByChainId(@Param("page") Page page, @Param("vo") SelectOrderDTO vo);


}
