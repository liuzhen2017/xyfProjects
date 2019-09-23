package com.xinyunfu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.xinyunfu.entity.CouponList;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.service.CouponListService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhen
 * couponList Controller
 */
@RestController
@RequestMapping("/couponList/")
@Slf4j
public class CouponListController {
     @Autowired
    private CouponListService couponListService;
     


    /**
     * couponList分页列表
     * 
     *
     * @param request couponList实体
     * @return ResponseInfo
     */
    @RequestMapping(value="queryList",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseInfo<IPage<CouponList>> list(@RequestHeader(name="currentUserId") String currentUserId, @RequestBody String parmat) {
    	JSONObject jb =JSONObject.parseObject(parmat);
    	CouponList entity =JSONObject.parseObject(parmat,CouponList.class);
    	Integer page =jb.getInteger("page");
    	Integer pageSize =jb.getInteger("pageSize");
    	//如果为空，则取默认值
    	log.info("=====heand userId={}=,userNo={}====",currentUserId,entity.getUserNo());
    	if(!StringUtils.isEmpty(currentUserId)) {
    		entity.setUserNo(currentUserId);
    	}
        return couponListService.list(entity,page,pageSize);
    }
    /**
     * couponList分页列表
     *
     * @param request couponList实体
     * @return ResponseInfo
     */
    @RequestMapping(value="queryByIds",method = {RequestMethod.GET})
    public ResponseInfo<List<CouponList>> queryByIds(@RequestParam("ids")String ids) {
    	
        return couponListService.queryByIds(ids);
    }
    /**
     * couponList分页列表
     *
     * @param request couponList实体
     * @return ResponseInfo
     */
    @RequestMapping(value="queryListAll",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseInfo<List<CouponList>> queryListAll(@RequestHeader(name="currentUserId") String currentUserId, @RequestBody String parmat) {
    	CouponList entity =JSONObject.parseObject(parmat,CouponList.class);
    	if(!org.apache.commons.lang3.StringUtils.isEmpty(currentUserId)) {
    		entity.setUserNo(currentUserId);
    	}
//    	entity.setCouponStatus(SysConstant.coupon_status_no_use00);
    	
        return couponListService.queryAll(entity);
    }
    /**
     * couponList分页列表
     *
     * @param request couponList实体
     * @return ResponseInfo
     */
    @GetMapping(value="queryListByOrder")
    public ResponseInfo<List<CouponList>> queryListByOrder(@RequestParam(name="userNo") String userNo) {
    	log.info("==========userNo={}=========",userNo);
    	CouponList entity = new CouponList();
   		entity.setUserNo(userNo);
//    	entity.setCouponStatus(SysConstant.coupon_status_no_use00);
    	
        return couponListService.queryAll(entity);
    }
    /**
     * couponList根据id查询详情
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    @GetMapping("/queryById")
    public ResponseInfo<CouponList> queryById(@PathVariable Long id) {
        return  couponListService.queryById(id);
    }

    /**
     * couponList根据条件查询详情
     *
     * @return ResponseInfo
     */
    @GetMapping("/query")
    public ResponseInfo<CouponList> query(CouponList entity) {
        return  couponListService.query(entity);
    }

    /**
     * 新增couponList
     *
     * @param request 请求参数
     * @return ResponseInfo
     */
    @PostMapping
    public ResponseInfo<Boolean> add(@Validated @RequestBody CouponList entity) {
        return couponListService.add(entity);
    }

    /**
     * 更新couponList
     *
     * @param request 请求参数
     * @param id 主键id
     * @return ResponseInfo
     */
    @RequestMapping(value="updateById",method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseInfo<String> update(@RequestBody String json) {
    	CouponList entity =JSONObject.parseObject(json,CouponList.class);
        return couponListService.update(entity);
    }
    /**
     * 更新couponList
     *
     * @param request 请求参数
     * @param id 主键id
     * @return ResponseInfo
     */
    @GetMapping("updateCoupon")
    public ResponseInfo<String> updateCoupon(@RequestParam String ids,@RequestParam String userNo,@RequestParam String couponStatus) {
    	
        return couponListService.updateCoupon(ids,userNo,couponStatus);
    }

    /**
     * 删除couponList
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    @DeleteMapping("/{id}")
    public ResponseInfo<Boolean> delete(@PathVariable Long id) {
        return couponListService.delete(id);
    }
}