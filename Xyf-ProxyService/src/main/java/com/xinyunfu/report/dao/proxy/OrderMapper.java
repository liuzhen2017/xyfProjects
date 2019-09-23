package com.xinyunfu.report.dao.proxy;

import com.xinyunfu.report.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

/**
 * @author xxb
 * @date 2019/9/2
 * @Description :
 */
@Mapper
public interface OrderMapper {

    /**
     * 获取订单购买数量
     * @return
     */
    @Select("select  IFNULL(DATE_FORMAT(created_date,'%Y-%m-%d'),'') name," +
            "        IFNULL(COUNT(order_id),'')  num from  order_master GROUP  BY name")
    @Results(value={
            @Result(id =true,property ="name",column ="name",javaType = String.class,jdbcType= JdbcType.TIMESTAMP),
            @Result(property ="num",column ="num",javaType = String.class,jdbcType= JdbcType.NUMERIC)
    })
    public List<Map<String,Object>> selectOrderBuyCount();

    /**
     * 查询订单购买量列表
     *
     * @param OrderItem
     * @return 订单购买量集合
     */
    //WHERE 1=1 AND  created_date&gt;=#{startDate} AND created_date&lt;=#{endDate}
    @Select("select  IFNULL(DATE_FORMAT(created_date,'%Y-%m-%d'),'') createdDate,\n" +
            "   IFNULL(COUNT(order_id),'')  orderBuyNum from  order_master  " +
            "  GROUP  BY createdDate ORDER BY createdDate DESC")
    public List<OrderItem> selectOrderBuyNumList(OrderItem orderItem);
}
