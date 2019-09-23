package com.xinyunfu.Constant;

/**
 * 常量区域
 * 
 * @author liuzhen
 * @version 1.0
 * @date 2019/7/8
 */
public class SysConstant {
	/**
	 * 优惠券状态 00未使用
	 */
	public final static String coupon_status_no_use00 = "00";
	/**
	 * 优惠券状态 01已经使用,
	 */
	public final static String coupon_status_use_ed01 = "01";
	/**
	 * 优惠券状态,02,已经过期,
	 */
	public final static String coupon_status_invalid02 = "02";
	/**
	 * 优惠券状态 03.系统禁用
	 */
	public final static String coupon_status_prohibit03 = "03";

	/**
	 * 优惠券状态 04.未激活
	 */
	public final static String coupon_status_no_activation04 = "04";
	/**
	 * 优惠券状态 05.已经激活
	 */
	public final static String coupon_status_no_activation05 = "05";
	/**
	 * 优惠券状态 06 转让中
	 */
	public final static String coupon_status_no_sign = "06";
	/**
	 * 优惠券状态 07 待入账
	 */
	public final static String coupon_status_to_be_recorded = "07";

	/**
	 * 优惠券状态 08 锁定中
	 */
	public final static String coupon_status_lock = "08";

	/**
	 * 优惠券类型 01.可以购买类型
	 */
	public final static Integer coupon_type_can_by = -1;
	/**
	 * 优惠券类型 00.万能券
	 */
	public final static Integer coupon_type_0 = 0;

	public final static String coupon_source_buy = "buy";

	/**
	 * 购买节点
	 */
	public final static String coupon_Node_setMeal = "setMeal";
	/**
	 * 新人节点
	 */
	public final static String coupon_Node_regist = "regist";
	/**
	 * 推荐节点
	 */
	public final static String coupon_Node_recommend = "recommend";
	
	public final static String redisCachePayKey="VolumeMarket_Key";
	
	public final static String redisCacheBackTransKey="VolumeMarket_backTransKey";
	
	public final static String redisCacheloseOrderNotifyKey="VolumeMarket_loseOrderNotify";
	
}
