package com.xinyunfu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyunfu.entity.SysSendMessager;
import com.xinyunfu.jace.utils.ResponseInfo;


public interface SysSendMessagerService extends IService<SysSendMessager>{


    /**
     * accFlow分页列表
     *
     * @param entity accFlow实体
     * @return ResponseInfo
     */
    public ResponseInfo<IPage<SysSendMessager>> list(SysSendMessager entity,Integer page,Integer pageSize);

    /**
     * accFlow根据id查询详情
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<SysSendMessager> queryById(Long id);

    /**
     * accFlow根据条件查询详情
     *
     * @return ResponseInfo
     */
    public ResponseInfo<SysSendMessager> query(SysSendMessager entity);

    /**
     * 新增accFlow
     *
     * @param entity 请求参数
     * @return ResponseInfo
     */
    public ResponseInfo<String> add( SysSendMessager entity);

    /**
     * 更新accFlow
     *
     * @param entity 请求参数
     * @return ResponseInfo
     */
    public ResponseInfo<String> update(SysSendMessager entity);

    /**
     * 删除accFlow
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<String> delete(Long id);

    /**
     * 发送短信
     * @param json
     * @return
     */
    public ResponseInfo<String> sendMsg(String json);
    /**
     * 校验验证码
     * @param phone
     * @param code
     * @return
     */
    public ResponseInfo<Boolean> checkMsg(String phone,String code,String templateNo);
}