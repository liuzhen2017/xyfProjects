package com.xinyunfu.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyunfu.entity.CouponMarket;

/**
 * @author liuzhen
 * couponMarket Mapper
 */
@Mapper
public interface CouponMarketMapper extends BaseMapper<CouponMarket> {
	/**
	 * 根据用户查询最后一次有效的订单
	 * @param userNo
	 * @return
	 */
	@Select("select * from coupon_market where user_no='${userNo}' and coupon_status ='06'   order by   created_date desc limit 1")
	public List<CouponMarket> queryLastMarketByUser(@Param("userNo") String userNo);
	/**
	 * 查询普通用户是否有未处理的卡券
	 * @param userNo
	 * @return
	 */
	@Select("select count(0) as counts from coupon_market where user_no='${userNo}' and coupon_status not in('01','07')   order by  created_date desc")
	public Integer queryByUserNo(@Param("userNo") String userNo);
	/**
	 * 查询会员第笔订单
	 * @param userNo
	 * @return
	 */
	@Select("select * from coupon_market where user_no='${userNo}' and coupon_status ='06'   order by  created_date asc  limit 1")
	public List<CouponMarket> queryFirstMarketByUser(@Param("userNo") String userNo);
	/**
	 * 根据订单号修改优惠券状态,转让价
	 * @param userNo
	 * @return
	 */
	@Update("update coupon_market set coupon_status ='${couponStatus}',sign_price ='${signPice}',sign_date= CURDATE() where order_no = '${orderNo}' ")
	public void updateByByOrder(@Param("couponStatus")String couponStatus,@Param("orderNo")String orderNo,@Param("signPice")BigDecimal price);
	/**
	 * 根据订单号修改优惠券状态,转让价
	 * @param userNo
	 * @return
	 */
	@Update("update coupon_market set coupon_status ='${couponStatus}' where order_no = '${orderNo}' ")
	public void updateByByOrder(@Param("couponStatus")String couponStatus,@Param("orderNo")String orderNo);
	/**
	 * 查询待用户转让类型
	 * @param userNo
	 * @return
	 */
	public Map<String, Object> queryByGroupBySign(@Param("userNo") String userNo,@Param("couponStatus")List<String> couponStatus,@Param("beginDate")String beginDate,@Param("endDate")String endDate);
	
	
	/**
	 * 查询券集市集合
	 * @param currentUserId
	 * @param couponStatus
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	@Select(" SELECT c.*,temp.numbers,temp.totals   FROM coupon_market c LEFT JOIN (   SELECT t.order_no,SUM((sign_price-service_charge+reward_amount)*number) total,SUM(number) numbers    FROM coupon_market t WHERE t.coupon_status = '${couponStatus}' AND t.user_no ='${userNo}'     GROUP BY order_no     )temp         ON c.order_no =temp.order_no    WHERE c.coupon_status = '${couponStatus}'  AND c.user_no ='${userNo}'   AND c.sign_date between '${beginDate}' AND  '${endDate}'  GROUP BY c.order_no ")
	public Page<Map<String, Object>>  queryByGroupList(@Param("page") Page<Map<String, Object>> page,@Param("userNo")String userNo,@Param("couponStatus") String couponStatus,@Param("beginDate") String beginDate,
			@Param("endDate") String endDate);
	
	/**
	 * 查找转让集合
	 * @param currentUserId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public Page<Map<String, Object>> queryByGroupListSigns(@Param("page") Page<Map<String, Object>> page,@Param("userNo")String userNo,@Param("couponStatus") String couponStatus,@Param("beginDate") String beginDate,
			@Param("endDate") String endDate);
	
}