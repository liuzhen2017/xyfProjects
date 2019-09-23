package com.xinyunfu.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.Constant.SysConstant;
import com.xinyunfu.entity.CouponList;
import com.xinyunfu.entity.CouponManger;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.CouponListMapper;
import com.xinyunfu.service.CouponListService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhen
 * couponList Service
 */
@Slf4j
@Service
public class CouponListServiceImpl extends ServiceImpl<CouponListMapper, CouponList> implements CouponListService{

    /**
     * 分页查询
     * @param couponList 请求参数
     * @return couponList分页列表
     */
    public ResponseInfo<IPage<CouponList>> list(CouponList couponList,Integer page,Integer pageSize) {
        LambdaQueryWrapper<CouponList> queryWrapper = new LambdaQueryWrapper<>();

        if (couponList.getId() != null) {
            queryWrapper.eq(CouponList::getId, couponList.getId());
        }

        if (couponList.getCouponId() != null) {
            queryWrapper.eq(CouponList::getCouponId, couponList.getCouponId());
        }

        if (!StringUtils.isEmpty(couponList.getUserNo())) {
            queryWrapper.eq(CouponList::getUserNo, couponList.getUserNo());
        }

        if (!StringUtils.isEmpty(couponList.getCouponStatus())) {
            queryWrapper.eq(CouponList::getCouponStatus, couponList.getCouponStatus());
        }
        if (!StringUtils.isEmpty(couponList.getCouponNode())) {
            queryWrapper.eq(CouponList::getCouponNode, couponList.getCouponNode());
        }

        if (!StringUtils.isEmpty(couponList.getTitle())) {
            queryWrapper.eq(CouponList::getTitle, couponList.getTitle());
        }

        if (!StringUtils.isEmpty(couponList.getDescribes())) {
            queryWrapper.eq(CouponList::getDescribes, couponList.getDescribes());
        }

        if (!StringUtils.isEmpty(couponList.getPicUrl())) {
            queryWrapper.eq(CouponList::getPicUrl, couponList.getPicUrl());
        }

        if (!StringUtils.isEmpty(couponList.getRules())) {
            queryWrapper.eq(CouponList::getRules, couponList.getRules());
        }

        if (couponList.getValueAmount() != null) {
            queryWrapper.eq(CouponList::getValueAmount, couponList.getValueAmount());
        }

        if (couponList.getEffectiveTime() != null) {
            queryWrapper.eq(CouponList::getEffectiveTime, couponList.getEffectiveTime());
        }

        if (couponList.getInvalidTime() != null) {
            queryWrapper.eq(CouponList::getInvalidTime, couponList.getInvalidTime());
        }

        if (couponList.getCreatedDate() != null) {
            queryWrapper.eq(CouponList::getCreatedDate, couponList.getCreatedDate());
        }

        if (!StringUtils.isEmpty(couponList.getCreatedBy())) {
            queryWrapper.eq(CouponList::getCreatedBy, couponList.getCreatedBy());
        }

        if (couponList.getUpdatedDate() != null) {
            queryWrapper.eq(CouponList::getUpdatedDate, couponList.getUpdatedDate());
        }

        if (!StringUtils.isEmpty(couponList.getUpdatedBy())) {
            queryWrapper.eq(CouponList::getUpdatedBy, couponList.getUpdatedBy());
        }

        if (couponList.getTicketDate() != null) {
            queryWrapper.eq(CouponList::getTicketDate, couponList.getTicketDate());
        }

        if (couponList.getUseDate() != null) {
            queryWrapper.eq(CouponList::getUseDate, couponList.getUseDate());
        }
        queryWrapper.orderByAsc(CouponList::getId);
        Page<CouponList> pages = new Page<CouponList>(page == null ? 1 : page,
				pageSize == null ? 15 : pageSize);
        return ResponseInfo.success(super.baseMapper.selectPage(pages, queryWrapper));
    }
    
    @Override
	public ResponseInfo<String> updateCoupon(String ids, String userNo, String couponStatus) {
    	Lock lock = new ReentrantLock();
		lock.lock();
		String[] strId = ids.split(";");
		try {
			log.info("===接收修改优惠券信息==ids={},userNo={},couponStatus={}==,",ids,userNo,couponStatus);
	    	List<CouponList> selectBatchIds = super.baseMapper.selectBatchIds(Arrays.asList(ids.split(";")));
	    	log.info("===根据条件查询优惠券信息==结果={}=,",selectBatchIds);
	    	if(selectBatchIds.isEmpty()) {
	    		return ResponseInfo.errorReturn("根据ID查询错误!没有该优惠券!");
	    	}
	    	if(selectBatchIds.size() != strId.length) {
	    		log.warn("===根据条件查询优惠券信息==ID数据和查询数量不匹配={}=,",selectBatchIds);
	    		return ResponseInfo.errorReturn("根据ID查询错误!ID数量和数据库数量不匹配!");
	    	}
			for(CouponList co :selectBatchIds) {
				if(!co.getUserNo().equals(userNo)) {
					return ResponseInfo.errorReturn("该用户不包含此优惠券!");
				}
				if(co.getCouponStatus().equals(couponStatus)) {
					return ResponseInfo.errorReturn("该优惠券状态不一致!");
				}
				co.setCouponStatus(couponStatus);
				this.update(co);
			}
			
		} catch (Exception e) {
			log.error("修改优惠券嘻嘻错误!msg={},e",e.getMessage(),e);
			return ResponseInfo.errorReturn("修改失败!系统错误");
		}finally {
			lock.unlock();
		}
		
		return ResponseInfo.success("修改成功!");
	}

	@Override
	public ResponseInfo<List<CouponList>> queryByIds(String ids) {
    	List<CouponList> selectBatchIds = super.baseMapper.selectBatchIds(Arrays.asList(ids.split(";")));
    	List<CouponList> selectList =selectBatchIds;
    	//修改为已经使用
    	selectBatchIds.forEach(s ->{ s.setCouponStatus(SysConstant.coupon_status_use_ed01);super.saveOrUpdate(s);});
    	
    	return ResponseInfo.success(selectList);
	}

	/**
     * 分页查询
     * @param couponList 请求参数
     * @return couponList分页列表
     */
    public ResponseInfo<List<CouponList>> queryAll(CouponList couponList) {
        LambdaQueryWrapper<CouponList> queryWrapper = new LambdaQueryWrapper<>();

        if (couponList.getId() != null) {
            queryWrapper.eq(CouponList::getId, couponList.getId());
        }

        if (couponList.getCouponId() != null) {
            queryWrapper.eq(CouponList::getCouponId, couponList.getCouponId());
        }

        if (!StringUtils.isEmpty(couponList.getUserNo())) {
            queryWrapper.eq(CouponList::getUserNo, couponList.getUserNo());
        }

        if (!StringUtils.isEmpty(couponList.getCouponStatus())) {
            queryWrapper.eq(CouponList::getCouponStatus, couponList.getCouponStatus());
        }
        if (!StringUtils.isEmpty(couponList.getCouponNode())) {
            queryWrapper.eq(CouponList::getCouponNode, couponList.getCouponNode());
        }

        if (!StringUtils.isEmpty(couponList.getTitle())) {
            queryWrapper.eq(CouponList::getTitle, couponList.getTitle());
        }

        if (!StringUtils.isEmpty(couponList.getDescribes())) {
            queryWrapper.eq(CouponList::getDescribes, couponList.getDescribes());
        }

        if (!StringUtils.isEmpty(couponList.getPicUrl())) {
            queryWrapper.eq(CouponList::getPicUrl, couponList.getPicUrl());
        }

        if (!StringUtils.isEmpty(couponList.getRules())) {
            queryWrapper.eq(CouponList::getRules, couponList.getRules());
        }

        if (couponList.getValueAmount() != null) {
            queryWrapper.eq(CouponList::getValueAmount, couponList.getValueAmount());
        }

        if (couponList.getEffectiveTime() != null) {
            queryWrapper.eq(CouponList::getEffectiveTime, couponList.getEffectiveTime());
        }

        if (couponList.getInvalidTime() != null) {
            queryWrapper.eq(CouponList::getInvalidTime, couponList.getInvalidTime());
        }

        if (couponList.getCreatedDate() != null) {
            queryWrapper.eq(CouponList::getCreatedDate, couponList.getCreatedDate());
        }

        if (!StringUtils.isEmpty(couponList.getCreatedBy())) {
            queryWrapper.eq(CouponList::getCreatedBy, couponList.getCreatedBy());
        }

        if (couponList.getUpdatedDate() != null) {
            queryWrapper.eq(CouponList::getUpdatedDate, couponList.getUpdatedDate());
        }

        if (!StringUtils.isEmpty(couponList.getUpdatedBy())) {
            queryWrapper.eq(CouponList::getUpdatedBy, couponList.getUpdatedBy());
        }

        if (couponList.getTicketDate() != null) {
            queryWrapper.eq(CouponList::getTicketDate, couponList.getTicketDate());
        }

        if (couponList.getUseDate() != null) {
            queryWrapper.eq(CouponList::getUseDate, couponList.getUseDate());
        }
        queryWrapper.orderByAsc(CouponList::getId);
        return ResponseInfo.success(super.baseMapper.selectList(queryWrapper));
    }

    /**
     * 根据条件查询详情
     * @param couponList 请求参数
     * @return couponList详情
     */
    public ResponseInfo<CouponList> query(CouponList couponList) {
        LambdaQueryWrapper<CouponList> queryWrapper = new LambdaQueryWrapper<>();
        if (couponList.getId() != null) {
            queryWrapper.eq(CouponList::getId, couponList.getId());
        }
        if (couponList.getCouponId() != null) {
            queryWrapper.eq(CouponList::getCouponId, couponList.getCouponId());
        }
        if (!StringUtils.isEmpty(couponList.getUserNo())) {
            queryWrapper.eq(CouponList::getUserNo, couponList.getUserNo());
        }
        if (!StringUtils.isEmpty(couponList.getCouponStatus())) {
            queryWrapper.eq(CouponList::getCouponStatus, couponList.getCouponStatus());
        }
        if (couponList.getCreatedDate() != null) {
            queryWrapper.eq(CouponList::getCreatedDate, couponList.getCreatedDate());
        }
        if (!StringUtils.isEmpty(couponList.getCreatedBy())) {
            queryWrapper.eq(CouponList::getCreatedBy, couponList.getCreatedBy());
        }
        if (couponList.getUpdatedDate() != null) {
            queryWrapper.eq(CouponList::getUpdatedDate, couponList.getUpdatedDate());
        }
        if (!StringUtils.isEmpty(couponList.getUpdatedBy())) {
            queryWrapper.eq(CouponList::getUpdatedBy, couponList.getUpdatedBy());
        }
        if (couponList.getTicketDate() != null) {
            queryWrapper.eq(CouponList::getTicketDate, couponList.getTicketDate());
        }
        if (couponList.getUseDate() != null) {
            queryWrapper.eq(CouponList::getUseDate, couponList.getUseDate());
        }
        return ResponseInfo.success(super.getOne(queryWrapper));
    }

     /**
     * 根据主键id查询详情
     * @param id couponListid
     * @return couponList详情
     */
    public ResponseInfo<CouponList> queryById(Long id) {
        return ResponseInfo.success(super.getById(id));
    }

    /**
    * 添加couponList
    * @param couponList 实体
    * @return ResponseEntity
    */
    public ResponseInfo<Boolean> add(CouponList couponList) {
        boolean b =super.save(couponList);
        return b? ResponseInfo.success(null) : ResponseInfo.error(false);
    }

    /**
    * 修改couponList
    * @param couponList 实体
    * @return ResponseEntity
    */
    public ResponseInfo<String> update(CouponList couponList) {
    	CouponList couponListDb = super.getById(couponList.getId());
    	if(!couponListDb.getCouponStatus().equals(SysConstant.coupon_status_no_use00) && couponList.getCouponStatus().equals(SysConstant.coupon_status_invalid02)) {
    		return ResponseInfo.error("优惠券状态有误!不允许消费!");
    	}
        boolean b =super.updateById(couponList);
        return b? ResponseInfo.success("成功") : ResponseInfo.error("失败");
    }

    /**
     * 删除couponList
     * @param id 主键id
     * @return ResponseEntity
     */
    public ResponseInfo<Boolean> delete(Long id) {
        super.removeById(id);
        boolean b =super.removeById(id);
        return b? ResponseInfo.success(null) : ResponseInfo.error(false);
    }
}