package com.xinyunfu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyunfu.model.OrderPrices;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 订单价格清单表 Mapper 接口
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-06
 */
public interface OrderPricesMapper extends BaseMapper<OrderPrices> {

    @Select("select user_id from order_prices where item_no = #{itemNo}")
    String checkUserByItemNo(@Param("itemNo") String itemNo);

    @Select("select user_id from order_prices where master_no = #{masterNo}")
    String checkUserByMasterNo(@Param("masterNo") String masterNo);

}
