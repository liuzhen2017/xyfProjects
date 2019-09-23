package com.EBank.service;

import com.EBank.entity.AccFlow;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.jace.utils.ResponseInfo;


public interface AccFlowService extends IService<AccFlow>{


    /**
     * accFlow分页列表
     *
     * @param entity accFlow实体
     * @return ResponseInfo
     */
    public ResponseInfo<IPage<AccFlow>> list(AccFlow entity, Integer page, Integer pageSize);

    /**
     * accFlow根据id查询详情
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<AccFlow> queryById(Long id);

    /**
     * accFlow根据条件查询详情
     *
     * @return ResponseInfo
     */
    public ResponseInfo<AccFlow> query(AccFlow entity);

    /**
     * 新增accFlow
     *
     * @param entity 请求参数
     * @return ResponseInfo
     */
    public ResponseInfo<String> add( AccFlow entity);

    /**
     * 更新accFlow
     *
     * @param entity 请求参数
     * @return ResponseInfo
     */
    public ResponseInfo<String> update(AccFlow entity);

    /**
     * 删除accFlow
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<String> delete(Long id);

    /**
     * 查找用户购买订单触发了几次
     * @param userNo
     * @param orderNumber
     * @return
     */
    int queryPayCountByOrderNo(String userNo,String orderNumber);


    /**
     * 根据订单编号修改状态
     * @param outTradeNo
     * @param status
     */
    void updateFlowByOrderNo(String outTradeNo, Integer status);
}