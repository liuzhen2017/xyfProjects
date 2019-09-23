package com.EBank.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.EBank.entity.AccFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @author liuzhen
 * accFlow Mapper
 */
@Mapper
public interface AccFlowMapper extends BaseMapper<AccFlow> {

    /**
     * 根据用户查询当前购买订单触发了几次
      * @param userNo 当前用户
     * @param orderNumber 订单编号
     * @return
     */
  @Select("select count(id) from acc_flow where user_no ='${userNo}' and tran_type = 1 and order_number ='${orderNumber}'")
  public Integer queryPayCountByOrderNo(@Param("userNo") String userNo,@Param("orderNumber") String orderNumber);

    /**
     * 根据订单修改状态
     * @param outTradeNo
     * @param status
     */
   @Update("update acc_flow set busi_status =${status} where order_number ='${outTradeNo}' ")
   public   void updateFlowByOrderNo(@Param("outTradeNo")String outTradeNo,@Param("status") Integer status);
}