package com.EBank.service.impl;

import com.EBank.entity.AccFlow;
import com.EBank.mapper.AccFlowMapper;
import com.EBank.service.AccFlowService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuzhen
 * accFlow Service
 */
@Slf4j
@Service
public class AccFlowServiceImpl extends ServiceImpl<AccFlowMapper, AccFlow> implements AccFlowService{

    @Autowired
    AccFlowMapper accFlowMapper;
    /**
     * 分页查询
     * @param accFlow 请求参数
     * @return accFlow分页列表
     */
    public ResponseInfo<IPage<AccFlow>> list(AccFlow accFlow, Integer pageSize, Integer page) {
        LambdaQueryWrapper<AccFlow> queryWrapper = new LambdaQueryWrapper<>();


        if (!StringUtils.isEmpty(accFlow.getOrderNumber())) {
            queryWrapper.eq(AccFlow::getOrderNumber, accFlow.getOrderNumber());
        }

        if (!StringUtils.isEmpty(accFlow.getPayNumber())) {
            queryWrapper.eq(AccFlow::getPayNumber, accFlow.getPayNumber());
        }

        if (!StringUtils.isEmpty(accFlow.getPayAccNo())) {
            queryWrapper.eq(AccFlow::getPayAccNo, accFlow.getPayAccNo());
        }

        if (!StringUtils.isEmpty(accFlow.getPayAccName())) {
            queryWrapper.eq(AccFlow::getPayAccName, accFlow.getPayAccName());
        }

        if (!StringUtils.isEmpty(accFlow.getReceAccNo())) {
            queryWrapper.eq(AccFlow::getReceAccNo, accFlow.getReceAccNo());
        }

        if (!StringUtils.isEmpty(accFlow.getReceAccName())) {
            queryWrapper.eq(AccFlow::getReceAccName, accFlow.getReceAccName());
        }

        if (accFlow.getPayAmount() != null) {
            queryWrapper.eq(AccFlow::getPayAmount, accFlow.getPayAmount());
        }

        if (accFlow.getTranDate() != null) {
            queryWrapper.eq(AccFlow::getTranDate, accFlow.getTranDate());
        }

        if (!StringUtils.isEmpty(accFlow.getTranType())) {
            queryWrapper.eq(AccFlow::getTranType, accFlow.getTranType());
        }

        if (!StringUtils.isEmpty(accFlow.getBusiStatus())) {
            queryWrapper.eq(AccFlow::getBusiStatus, accFlow.getBusiStatus());
        }

        if (accFlow.getEnable() != null) {
            queryWrapper.eq(AccFlow::getEnable, accFlow.getEnable());
        }

        if (accFlow.getCreatedDate() != null) {
            queryWrapper.eq(AccFlow::getCreatedDate, accFlow.getCreatedDate());
        }

        if (accFlow.getCreatedBy() != null) {
            queryWrapper.eq(AccFlow::getCreatedBy, accFlow.getCreatedBy());
        }

        if (accFlow.getUpdatedDate() != null) {
            queryWrapper.eq(AccFlow::getUpdatedDate, accFlow.getUpdatedDate());
        }

        if (accFlow.getUpdatedBy() != null) {
            queryWrapper.eq(AccFlow::getUpdatedBy, accFlow.getUpdatedBy());
        }
        queryWrapper.orderByDesc(AccFlow::getId);
        Page<AccFlow> pages = new Page<AccFlow>(page == null ? 1 : page , pageSize == null ? 15 : pageSize);
        return ResponseInfo.success(super.baseMapper.selectPage(pages, queryWrapper));
    }

    @Override
    public void updateFlowByOrderNo(String outTradeNo, Integer status) {
        baseMapper.updateFlowByOrderNo(outTradeNo,status);
    }

    /**
     * 根据条件查询详情
     * @param accFlow 请求参数
     * @return accFlow详情
     */
    public ResponseInfo<AccFlow> query(AccFlow accFlow) {
        LambdaQueryWrapper<AccFlow> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(accFlow.getOrderNumber())) {
            queryWrapper.eq(AccFlow::getOrderNumber, accFlow.getOrderNumber());
        }
        if (!StringUtils.isEmpty(accFlow.getPayNumber())) {
            queryWrapper.eq(AccFlow::getPayNumber, accFlow.getPayNumber());
        }
        if (!StringUtils.isEmpty(accFlow.getPayAccNo())) {
            queryWrapper.eq(AccFlow::getPayAccNo, accFlow.getPayAccNo());
        }
        if (!StringUtils.isEmpty(accFlow.getPayAccName())) {
            queryWrapper.eq(AccFlow::getPayAccName, accFlow.getPayAccName());
        }
        if (!StringUtils.isEmpty(accFlow.getReceAccNo())) {
            queryWrapper.eq(AccFlow::getReceAccNo, accFlow.getReceAccNo());
        }
        if (!StringUtils.isEmpty(accFlow.getReceAccName())) {
            queryWrapper.eq(AccFlow::getReceAccName, accFlow.getReceAccName());
        }
        if (accFlow.getPayAmount() != null) {
            queryWrapper.eq(AccFlow::getPayAmount, accFlow.getPayAmount());
        }
        if (accFlow.getTranDate() != null) {
            queryWrapper.eq(AccFlow::getTranDate, accFlow.getTranDate());
        }
        if (!StringUtils.isEmpty(accFlow.getTranType())) {
            queryWrapper.eq(AccFlow::getTranType, accFlow.getTranType());
        }
        if (!StringUtils.isEmpty(accFlow.getBusiStatus())) {
            queryWrapper.eq(AccFlow::getBusiStatus, accFlow.getBusiStatus());
        }
        if (accFlow.getEnable() != null) {
            queryWrapper.eq(AccFlow::getEnable, accFlow.getEnable());
        }
        if (accFlow.getCreatedDate() != null) {
            queryWrapper.eq(AccFlow::getCreatedDate, accFlow.getCreatedDate());
        }
        if (accFlow.getCreatedBy() != null) {
            queryWrapper.eq(AccFlow::getCreatedBy, accFlow.getCreatedBy());
        }
        if (accFlow.getUpdatedDate() != null) {
            queryWrapper.eq(AccFlow::getUpdatedDate, accFlow.getUpdatedDate());
        }
        if (accFlow.getUpdatedBy() != null) {
            queryWrapper.eq(AccFlow::getUpdatedBy, accFlow.getUpdatedBy());
        }
        queryWrapper.orderByDesc(AccFlow::getId);
        //取第一页第一条,相当于 limit
        Page<AccFlow> pages = new Page<AccFlow>(1,1);
        IPage<AccFlow> iPage = super.page(pages,queryWrapper);
        if(! iPage.getRecords().isEmpty()){
            return  ResponseInfo.success(iPage.getRecords().get(0));
        }
        return ResponseInfo.success(null);
    }

    /**
     * 根据主键id查询详情
     * @param id accFlowid
     * @return accFlow详情
     */
    public ResponseInfo<AccFlow> queryById(Long id) {
        return ResponseInfo.success(super.getById(id));
    }

    /**
     * 添加accFlow
     * @param accFlow 实体
     * @return ResponseEntity
     */
    public ResponseInfo<String> add(AccFlow accFlow) {
        if(super.save(accFlow)) {
            return ResponseInfo.success("新增成功!");
        }else {
            return ResponseInfo.error("新增失败!");
        }
    }

    /**
     * 修改accFlow
     * @param accFlow 实体
     * @return ResponseEntity
     */
    public ResponseInfo<String> update(AccFlow accFlow) {

        if(super.updateById(accFlow)) {
            return ResponseInfo.success("修改成功!");
        }else {
            return ResponseInfo.error("修改失败!");
        }
    }

    @Override
    public int queryPayCountByOrderNo(String userNo,  String orderNumber) {
        return accFlowMapper.queryPayCountByOrderNo(userNo,orderNumber);
    }

    /**
     * 删除accFlow
     * @param id 主键id
     * @return ResponseEntity
     */
    public ResponseInfo<String> delete(Long id) {
        if(super.removeById(id)) {
            return ResponseInfo.success("删除成功!");
        }else {
            return ResponseInfo.error("删除失败!");
        }
    }
}