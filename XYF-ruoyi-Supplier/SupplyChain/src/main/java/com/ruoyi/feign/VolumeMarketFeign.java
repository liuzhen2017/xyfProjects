package com.ruoyi.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.domain.CouponManger;
import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author TYM
 * @date 2019/8/5
 * @Description :
 */
@FeignClient("VolumeMarket")
@RequestMapping("/couponManger/")
public interface VolumeMarketFeign {
    /**
     * 新增优惠券管理
     *
     * @return ResponseInfo
     */
    @PostMapping("/add")
    public com.ruoyi.utils.ResponseInfo<Boolean> add(@RequestBody CouponManger entity);
    /**
            * 优惠券管理分页列表
	 *
	 * @return ResponseInfo
	 */
    @PostMapping(value = "queryLists.do")
    public com.ruoyi.utils.ResponseInfo<Object> queryLists(@RequestBody String parmat);
    /**
     /**
     * 更新优惠券管理
     *
     * @return ResponseInfo
     */
    @PutMapping("/update")
    public com.ruoyi.utils.ResponseInfo<Boolean> update(@RequestBody CouponManger entity);
    /**
     * 优惠券管理根据id查询详情
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    @GetMapping("/queryById")
    public com.ruoyi.utils.ResponseInfo<CouponManger> queryById(@RequestParam("id") Long id);
}
