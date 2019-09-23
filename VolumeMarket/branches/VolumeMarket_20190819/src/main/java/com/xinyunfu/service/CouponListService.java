package com.xinyunfu.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.entity.CouponList;
import com.xinyunfu.jace.utils.ResponseInfo;


public interface CouponListService extends IService<CouponList>{
    
    
    /**
     * couponList分页列表
     *
     * @param request couponList实体
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<IPage<CouponList>> list(CouponList entity,Integer page,Integer pageSize);

    /**
     * couponList根据id查询详情
     *
     * @param id 主键id
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<CouponList> queryById(Long id);

    /**
     * couponList根据条件查询详情
     *
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<CouponList> query(CouponList entity);

    /**
     * 新增couponList
     *
     * @param request 请求参数
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<Boolean> add( CouponList entity);

    /**
     * 更新couponList
     *
     * @param request 请求参数
     * @param id 主键id
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<String> update(CouponList entity);

    /**
     * 删除couponList
     *
     * @param id 主键id
     * @return ResponseInfo<Boolean>
     */
    public ResponseInfo<Boolean> delete(Long id);
    
    public ResponseInfo<List<CouponList>> queryAll(CouponList couponList);

	public ResponseInfo<List<CouponList>> queryByIds(String ids);

	public ResponseInfo<String> updateCoupon(String ids, String userNo, String couponStatus);
}