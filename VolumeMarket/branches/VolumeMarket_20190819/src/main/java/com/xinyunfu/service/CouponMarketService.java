package com.xinyunfu.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.entity.CouponMarket;
import com.xinyunfu.jace.utils.ResponseInfo;


public interface CouponMarketService extends IService<CouponMarket>{
    
    
    /**
     * couponMarket分页列表
     *
     * @param request couponMarket实体
     * @return ResponseInfo
     */
    public ResponseInfo<IPage<CouponMarket>> list(CouponMarket entity,Integer page,Integer pageSize);

    /**
     * couponMarket根据id查询详情
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<CouponMarket> queryById(Long id);

    /**
     * couponMarket根据条件查询详情
     *
     * @return ResponseInfo
     */
    public ResponseInfo<CouponMarket> query(CouponMarket entity);

    /**
     * 新增couponMarket
     *
     * @param request 请求参数
     * @return ResponseInfo
     */
    public ResponseInfo<Boolean> add( CouponMarket entity);

    /**
     * 更新couponMarket
     *
     * @param request 请求参数
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<Boolean> update(CouponMarket entity);

    /**
     * 删除couponMarket
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<Boolean> delete(Long id);

	public ResponseInfo<String> queryCuponMarket();

	public ResponseInfo<String> signCoupon(String currentUserId,BigDecimal price);

	ResponseInfo<Map<String, Object>> queryByGroupBy(String userNo,String couponStatus,String beginDate,String endDate,Integer page,Integer pageSize);

	public ResponseInfo<String> updateSignCoupon(String currentUserId);

	public ResponseInfo<String> queryIsCanBuy(UserInfoDTO res,String currentUserId);

	public ResponseInfo<String> queryCanSign(String currentUserId);

	public ResponseInfo<IPage<Map<String, Object>>> queryByGroupList(Integer page, Integer pageSize, String currentUserId,
			String couponStatus, String beginDate, String endDate);

	public ResponseInfo<String> paymentByCoupon(String recvUserNo, String channel,
			String sourceUserNo, String orderNo);

	public ResponseInfo<String> backTransferAccounts(String orderId, Integer status);

	public void loseOrderNotify(String string, String string2);

	public ResponseInfo<String> queryIsCanBuyByMS(UserInfoDTO data, String currentUserId);
}