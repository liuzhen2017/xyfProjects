package com.xinyunfu.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.entity.CouponList;
import com.xinyunfu.entity.CouponManger;
import com.xinyunfu.jace.utils.ResponseInfo;


public interface CouponMangerService extends IService<CouponManger>{
    
    
    /**
     * 优惠券管理分页列表
     *
     * @param request 优惠券管理实体
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<IPage<CouponManger>> list(CouponManger entity,Integer page,Integer pageSize);

    /**
     * 优惠券管理根据id查询详情
     *
     * @param id 主键id
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<CouponManger> queryById(Long id);

    /**
     * 优惠券管理根据条件查询详情
     *
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<CouponManger> query(CouponManger entity);

    /**
     * 新增优惠券管理
     *
     * @param request 请求参数
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<Boolean> add( CouponManger entity);

    /**
     * 更新优惠券管理
     *
     * @param request 请求参数
     * @param id 主键id
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<Boolean> update(CouponManger entity);

    /**
     * 删除优惠券管理
     *
     * @param id 主键id
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<Boolean> delete(Long id);
    /**
	 *  系统按节点赠送优惠券
	 * @param userNo 用户编号
	 * @param couponNode 节点信息 注册regist,推荐recommend
	 * @return
	 */
	public ResponseInfo<String> giveCoupon(String userNo, String couponNode,String orderNo);
	/**
	 * 套餐购买成功后,配置18张券
	 * @param userNo 用户编号
	 * @param orderNo 订单号
	 * @return
	 */
	public ResponseInfo<String> distriCouon(String userNo, String orderNo,Integer nums);
	/**
	 * 修改状态
	 * @param userNo
	 * @param subjectIndex
	 * @return
	 */
	public ResponseInfo<String> updateByMarket(String userNo, Integer subjectIndex,String couponStatus,String orderNo);

	public ResponseInfo<String> buyCoupon(String userNo, String couponid, int num);

	public ResponseInfo<List<CouponList>> queryNewCuponMarket(String currentUserId);
}