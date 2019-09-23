package com.xinyunfu.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.Constant.SysConstant;
import com.xinyunfu.config.SysCoupon;
import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.entity.CouponList;
import com.xinyunfu.entity.CouponManger;
import com.xinyunfu.entity.CouponMarket;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.feign.OrderTreeFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.CouponListMapper;
import com.xinyunfu.mapper.CouponMangerMapper;
import com.xinyunfu.mapper.CouponMarketMapper;
import com.xinyunfu.service.CouponListService;
import com.xinyunfu.service.CouponMangerService;
import com.xinyunfu.service.CouponMarketService;
import com.xinyunfu.service.NoticeInfoService;
import com.xinyunfu.util.SnowFlake;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhen 优惠券管理 Service
 */
@Slf4j
@Service
public class CouponMangerServiceImpl extends ServiceImpl<CouponMangerMapper, CouponManger>
		implements CouponMangerService {

	@Autowired
	CouponMangerMapper couponMangerMapper;
	@Autowired
	CustomerManageFeign customerManage;
	@Autowired
	CouponListMapper coListMapper;
	@Autowired
	CouponListService colistService;
	@Autowired
	CouponMarketService couponMarketService;
	@Autowired
	CouponMarketMapper couponMarketMapper;
	@Autowired
    CustomerManageFeign custFeign;
	@Autowired
	private AmqpTemplate rabbitTemplate;
	@Autowired
	private SysCoupon sysCoupon;
	@Autowired
	private NoticeInfoService noticeInfoService;

	@Autowired
	private OrderTreeFeign orderTreeFeign;
	/**
	 * 分页查询
	 * 
	 * @param couponManger 请求参数
	 * @return 优惠券管理分页列表
	 */
	public ResponseInfo<IPage<CouponManger>> list(CouponManger couponManger, Integer page, Integer pageSize) {

		LambdaQueryWrapper<CouponManger> queryWrapper = new LambdaQueryWrapper<>();

		if (couponManger.getId() != null) {
			queryWrapper.eq(CouponManger::getId, couponManger.getId());
		}

		if (couponManger.getCouponType() != null) {
			queryWrapper.eq(CouponManger::getCouponType, couponManger.getCouponType());
		}

		if (!StringUtils.isEmpty(couponManger.getCouponNode())) {
			queryWrapper.eq(CouponManger::getCouponNode, couponManger.getCouponNode());
		}

		if (!StringUtils.isEmpty(couponManger.getTitle())) {
			queryWrapper.eq(CouponManger::getTitle, couponManger.getTitle());
		}

		if (!StringUtils.isEmpty(couponManger.getDescribes())) {
			queryWrapper.eq(CouponManger::getDescribes, couponManger.getDescribes());
		}

		if (!StringUtils.isEmpty(couponManger.getPicUrl())) {
			queryWrapper.eq(CouponManger::getPicUrl, couponManger.getPicUrl());
		}

		if (!StringUtils.isEmpty(couponManger.getRules())) {
			queryWrapper.eq(CouponManger::getRules, couponManger.getRules());
		}

		if (couponManger.getValueAmount() != null) {
			queryWrapper.eq(CouponManger::getValueAmount, couponManger.getValueAmount());
		}

		if (couponManger.getEffectiveTime() != null) {
			queryWrapper.eq(CouponManger::getEffectiveTime, couponManger.getEffectiveTime());
		}

		if (couponManger.getInvalidTime() != null) {
			queryWrapper.eq(CouponManger::getInvalidTime, couponManger.getInvalidTime());
		}

		if (couponManger.getTotalNum() != null) {
			queryWrapper.eq(CouponManger::getTotalNum, couponManger.getTotalNum());
		}

		if (couponManger.getCanNum() != null) {
			queryWrapper.eq(CouponManger::getCanNum, couponManger.getCanNum());
		}

		if (couponManger.getUsedNum() != null) {
			queryWrapper.eq(CouponManger::getUsedNum, couponManger.getUsedNum());
		}

		if (!StringUtils.isEmpty(couponManger.getCouponStatus())) {
			queryWrapper.eq(CouponManger::getCouponStatus, couponManger.getCouponStatus());
		}

		if (couponManger.getCreatedDate() != null) {
			queryWrapper.eq(CouponManger::getCreatedDate, couponManger.getCreatedDate());
		}

		if (!StringUtils.isEmpty(couponManger.getCreatedBy())) {
			queryWrapper.eq(CouponManger::getCreatedBy, couponManger.getCreatedBy());
		}

		if (couponManger.getUpdatedDate() != null) {
			queryWrapper.eq(CouponManger::getUpdatedDate, couponManger.getUpdatedDate());
		}

		if (!StringUtils.isEmpty(couponManger.getUpdatedBy())) {
			queryWrapper.eq(CouponManger::getUpdatedBy, couponManger.getUpdatedBy());
		}
		if (!StringUtils.isEmpty(couponManger.getIsCanBuy())) {
			queryWrapper.eq(CouponManger::getIsCanBuy, couponManger.getIsCanBuy());
		}
//        queryWrapper.orderByDesc(CouponManger::getCreateDate);
//        return super.baseMapper.selectPage(couponManger.toPage(), queryWrapper);

		queryWrapper.orderByAsc(CouponManger::getId);
		Page<CouponManger> pages = new Page<CouponManger>(page == null ? 1 : page, pageSize == null ? 15 : pageSize);
		IPage<CouponManger> selectPage = super.baseMapper.selectPage(pages, queryWrapper);
		return ResponseInfo.success(selectPage);
	}

	/**
	 * 根据条件查询详情
	 * 
	 * @param couponManger 请求参数
	 * @return 优惠券管理详情
	 */
	public ResponseInfo<CouponManger> query(CouponManger couponManger) {
		LambdaQueryWrapper<CouponManger> queryWrapper = new LambdaQueryWrapper<>();
		if (couponManger.getId() != null) {
			queryWrapper.eq(CouponManger::getId, couponManger.getId());
		}
		if (couponManger.getCouponType() != null) {
			queryWrapper.eq(CouponManger::getCouponType, couponManger.getCouponType());
		}
		if (!StringUtils.isEmpty(couponManger.getCouponNode())) {
			queryWrapper.eq(CouponManger::getCouponNode, couponManger.getCouponNode());
		}
		if (!StringUtils.isEmpty(couponManger.getTitle())) {
			queryWrapper.eq(CouponManger::getTitle, couponManger.getTitle());
		}
		if (!StringUtils.isEmpty(couponManger.getDescribes())) {
			queryWrapper.eq(CouponManger::getDescribes, couponManger.getDescribes());
		}
		if (!StringUtils.isEmpty(couponManger.getPicUrl())) {
			queryWrapper.eq(CouponManger::getPicUrl, couponManger.getPicUrl());
		}
		if (!StringUtils.isEmpty(couponManger.getRules())) {
			queryWrapper.eq(CouponManger::getRules, couponManger.getRules());
		}
		if (couponManger.getValueAmount() != null) {
			queryWrapper.eq(CouponManger::getValueAmount, couponManger.getValueAmount());
		}
		if (couponManger.getEffectiveTime() != null) {
			queryWrapper.eq(CouponManger::getEffectiveTime, couponManger.getEffectiveTime());
		}
		if (couponManger.getInvalidTime() != null) {
			queryWrapper.eq(CouponManger::getInvalidTime, couponManger.getInvalidTime());
		}
		if (couponManger.getTotalNum() != null) {
			queryWrapper.eq(CouponManger::getTotalNum, couponManger.getTotalNum());
		}
		if (couponManger.getCanNum() != null) {
			queryWrapper.eq(CouponManger::getCanNum, couponManger.getCanNum());
		}
		if (couponManger.getUsedNum() != null) {
			queryWrapper.eq(CouponManger::getUsedNum, couponManger.getUsedNum());
		}
		if (!StringUtils.isEmpty(couponManger.getCouponStatus())) {
			queryWrapper.eq(CouponManger::getCouponStatus, couponManger.getCouponStatus());
		}
		if (couponManger.getCreatedDate() != null) {
			queryWrapper.eq(CouponManger::getCreatedDate, couponManger.getCreatedDate());
		}
		if (!StringUtils.isEmpty(couponManger.getCreatedBy())) {
			queryWrapper.eq(CouponManger::getCreatedBy, couponManger.getCreatedBy());
		}
		if (couponManger.getUpdatedDate() != null) {
			queryWrapper.eq(CouponManger::getUpdatedDate, couponManger.getUpdatedDate());
		}
		if (!StringUtils.isEmpty(couponManger.getUpdatedBy())) {
			queryWrapper.eq(CouponManger::getUpdatedBy, couponManger.getUpdatedBy());
		}
		return ResponseInfo.success(super.getOne(queryWrapper));
	}

	/**
	 * 根据主键id查询详情
	 * 
	 * @param id 优惠券管理id
	 * @return 优惠券管理详情
	 */
	public ResponseInfo<CouponManger> queryById(Long id) {
		return ResponseInfo.success(super.getById(id));
	}

	/**
	 * 添加优惠券管理
	 * 
	 * @param couponManger 实体
	 * @return ResponseEntity
	 */
	public ResponseInfo<Boolean> add(CouponManger couponManger) {
		couponManger.setId(SnowFlake.nextId()+"");
		couponManger.setCanNum(couponManger.getTotalNum());
		return ResponseInfo.success(super.save(couponManger));
	}

	/**
	 * 修改优惠券管理
	 * 
	 * @param couponManger 实体
	 * @return ResponseEntity
	 */
	public ResponseInfo<Boolean> update(CouponManger couponManger) {
		return ResponseInfo.success(super.updateById(couponManger));

	}

	/**
	 * 删除优惠券管理
	 * 
	 * @param id 主键id
	 * @return ResponseEntity
	 */
	public ResponseInfo<Boolean> delete(Long id) {
		return ResponseInfo.success(super.removeById(id));
	}

	/**
	 * 根据节点生成 1.根据条件查询 2.创建子表 3.保存字表信息 4.修改库存 5.发送通知
	 */
	@Override
	public ResponseInfo<String> giveCoupon(String userNo, String couponNode, String orderNo) {
		return giveCouponBat(userNo,couponNode,orderNo,1);
	}
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResponseInfo<String> giveCouponBat(String userNo, String couponNode, String orderNo,Integer nums) {
		Lock lock = new ReentrantLock();
		lock.lock();
		log.info("============接收到发放优惠券请求===========userNo={},couponNode ={}=,orderNo={}", userNo, couponNode, orderNo);
		LambdaQueryWrapper<CouponManger> queryWrapper = new LambdaQueryWrapper<>();
		// 查询参数
		CouponManger couponManger = new CouponManger();
		couponManger.setCouponNode(couponNode);
		couponManger.setCouponStatus(SysConstant.coupon_status_no_use00);

		// 比较方式
		queryWrapper.eq(CouponManger::getCouponStatus, couponManger.getCouponStatus());
		queryWrapper.eq(CouponManger::getCouponNode, couponManger.getCouponNode());
		// 大于
		queryWrapper.le(CouponManger::getEffectiveTime, new Date());
		// 小于
		queryWrapper.ge(CouponManger::getInvalidTime, new Date());
		queryWrapper.ge(CouponManger::getCanNum, 0);
		// 查询
		log.info("============执行条件查询,查询未发放,已经生效的优惠券,并且可使用大于0===========");
		List<CouponManger> list = couponMangerMapper.selectList(queryWrapper);
		log.info("============执行条件查询,查询结果{}===========", list);
		List<CouponList> listCou=null; 
		List<CouponManger> saveCouPon =new ArrayList<>(list.size());
		if (!list.isEmpty()) {
			for (CouponManger cou : list) {
				Integer num = cou.getCanNum() - nums;
				if (num >= 0) {
					// 生成优惠券列表
					listCou =new ArrayList<>(nums);	
					for(int i =0;i< nums;i++) {
						listCou.add(createdList(cou, userNo, couponNode));
					}
					
					// 修改库存
					cou.setCanNum(num);
					cou.setUsedNum(cou.getUsedNum() + nums);
//					//保存修改集合
					saveCouPon.add(cou);
					// 发送通知
//					Map<String, Object> map = new HashMap<>();
//					map.put("recvObject", "15818660647");
//					map.put("sendType", "02");
//					map.put("sendContent", "恭喜您,获得系统赠送 <" + cou.getTitle() + "> 1张,请及时使用!");
//					this.rabbitTemplate.convertAndSend("InfoCenter_sendSMS", JSONObject.toJSONString(map));
					log.info("==================赠送成功==================");
					// 保存列表
					log.info("====执行新增列表==listCou={}==",listCou.size());
					colistService.saveBatch(listCou);
					// 删除redis
//					lock.unlock();
				}
				
			}
			//执行修改列表
			log.info("====执行修改列表==coupon={}==",saveCouPon.size());
			this.updateBatchById(saveCouPon);
			
			lock.unlock();
			
			return ResponseInfo.success("赠送成功!");
			
		}
		lock.unlock();
		log.warn("==================赠送失败!没有符合条件的优惠券!==================");
		return ResponseInfo.errorReturn("赠送失败!没有符合条件的优惠券!");
	}

	/**
	 * 生成优惠券列表
	 * 
	 * @param cou
	 * @param userNo
	 * @return
	 */
	public CouponList createdList(CouponManger cou, String userNo, String source) {
		CouponList li = JSONObject.parseObject(JSONObject.toJSONString(cou), CouponList.class);
		li.setEffectiveTime(new Date());
		li.setCouponId(cou.getId()+"");
		li.setTicketDate(new Date());
		// 生成唯一ID
		li.setId(SnowFlake.nextId()+"");
		li.setUserNo(userNo);
		li.setSource(source);
		li.setInvalidTime(new Date());
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + 1);
		Date date = curr.getTime();
		li.setDueDate(date);

		return li;
	}

	@Override
	public ResponseInfo<String> distriCouon(String userNo, String orderNo,Integer nums) {
		// 分配给用户
		log.info("==========开始分配给用户==============");
		LambdaQueryWrapper<CouponMarket> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CouponMarket::getOrderNo, orderNo);
		List<CouponMarket> list2 = couponMarketService.list(queryWrapper);
		if (!list2.isEmpty()) {
			return ResponseInfo.errorReturn("重复提交订单!");
		}

		Integer num = Integer.parseInt(sysCoupon.getGiveNum());
		List<CouponMarket> list = new ArrayList<>(num);
		CouponMarket couponMarket = null;
		Calendar curr = Calendar.getInstance();
		curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) + 1);
		Date date = curr.getTime();
		int couponNum =nums *6;
		ResponseInfo<UserInfoDTO> userInfo = custFeign.getUserInfo(Long.parseLong(userNo));		
		if(!userInfo.isSuccess()) {
			return ResponseInfo.errorReturn(userInfo.getMessage());
		}
		UserInfoDTO dto  =userInfo.getData();
		Integer level = dto.getLevel();
		List<CouponMarket> listCoupon =new ArrayList<>(couponNum);
		for (int i = 0; i < couponNum; i++) {
			couponMarket = new CouponMarket();
			couponMarket.setId(SnowFlake.nextId());
			couponMarket.setAmount(BigDecimal.valueOf(Long.parseLong(sysCoupon.getAmount())));
			couponMarket.setCouponId(0L);
			couponMarket.setOrderNo(orderNo);
			couponMarket.setTitle(sysCoupon.getTitle());
			couponMarket.setRules(sysCoupon.getRules());
			couponMarket.setNumber(3);
			couponMarket.setCouponStatus(SysConstant.coupon_status_no_activation04);
			if(level ==1) {
				couponMarket.setCouponStatus(SysConstant.coupon_status_no_sign);
				if(sysCoupon.getIsReward().equals("true")) {
					log.info("=====该用户奖励===amount={}",sysCoupon.getRewardAmount());
					couponMarket.setRewardAmount(new BigDecimal(sysCoupon.getRewardAmount()));
					couponMarket.setRewardType(sysCoupon.getRewardType());
				}
				log.info("=====该用户收取服务费===amount={}",sysCoupon.getServiceChargeAmount());
				couponMarket.setServiceCharge(new BigDecimal(sysCoupon.getServiceChargeAmount()));
				couponMarket.setSignPrice(new BigDecimal(sysCoupon.getAmount()));
				couponMarket.setSignDate(new Date());
				
			}
			couponMarket.setUserType(level);
			couponMarket.setUserNo(userNo);
			couponMarket.setCreatedBy("admin");
			couponMarket.setCreatedDate(new Date());
			couponMarket.setUseDate(new Date());
			couponMarket.setUpdatedBy("admin");
			couponMarket.setDueDate(date);
			listCoupon.add(couponMarket);
	
		}
		couponMarketService.saveBatch(listCoupon);		
		//分配多张
		giveCouponBat(userNo,"setMeal",orderNo,nums);			
		//开始通知订单树
		log.info("=========开始通知订单树=====");
		ResponseInfo<String> orderTreeResult = orderTreeFeign.joinOrderTree(orderNo, userNo, level, nums);
		log.info("========订单树返回,result={}=====",orderTreeResult);
		if(!orderTreeResult.isSuccess()) {
			log.error("=====订单树返回失败={}=====",orderTreeResult.getMessage());
			noticeInfoService.saveNotifyInfo("orderTreeFeign",orderTreeResult.getMessage(),null,"OrderTreeFeign","joinOrderTree",orderNo, userNo, level, nums);
		}
		
		log.info("==========批量新增数量=============={}", list.size());
		return ResponseInfo.success("分配成功");
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResponseInfo<String> updateByMarket(String userNo, Integer subjectIndex, String couponStatus,
			String orderNo) {
		// 根据用户查询前三个
		log.info("============修改用户券集市状态===========userNo={},subjectIndex ={},couponStatus={},orderNo={}", userNo,
				subjectIndex, couponStatus, orderNo);
		// TODO 分布式事务锁
		LambdaQueryWrapper<CouponMarket> queryWrapper = new LambdaQueryWrapper<>();
		// 查询参数
		CouponMarket couponmarket = new CouponMarket();
		couponmarket.setOrderNo(orderNo);
		queryWrapper.eq(CouponMarket::getOrderNo, couponmarket.getOrderNo());
		queryWrapper.orderByDesc(CouponMarket::getId);
		// 查询
		log.info("============执行条件查询===========");
		Page<CouponMarket> pages = new Page<CouponMarket>(subjectIndex,1);
		IPage<CouponMarket> selectPage = couponMarketMapper.selectPage(pages, queryWrapper);
		if(selectPage.getRecords().isEmpty() || selectPage.getRecords().size() ==0) {
			return ResponseInfo.errorReturn("根据该订单号查询不存在!");
		}
		
		log.info("============查询是否有两个推荐人,以获取奖励===========");
		ResponseInfo<Boolean> relation = customerManage.relation(Long.valueOf(userNo));
		log.info("============执行条件查询,查询结果===========", selectPage.getSize());
		String msg ="激活成功!";
		if (!selectPage.getRecords().isEmpty() && selectPage.getSize() == 1) {
			for (CouponMarket ket : selectPage.getRecords()) {
				
				if (couponStatus.equals(SysConstant.coupon_status_no_activation05)) {
					if(SysConstant.coupon_status_no_activation05.equals(selectPage.getRecords().get(0).getCouponStatus())) {
					  return ResponseInfo.errorReturn("该题目已经激活!");	
					}
					// 设置激活日期
					ket.setActiDate(new Date());
					
					//查找用户是否已经推荐两人,并且是否有奖励
					if(relation.getData() && sysCoupon.getIsReward().equals("true")) {
						log.info("=====该用户奖励===amount={}",sysCoupon.getRewardAmount());
						ket.setRewardAmount(new BigDecimal(sysCoupon.getRewardAmount()));
						ket.setRewardType(sysCoupon.getRewardType());
						msg ="推荐两人奖励"+ket.getRewardAmount().multiply(new BigDecimal(ket.getNumber()));
					}					
					ket.setServiceCharge(new BigDecimal(sysCoupon.getServiceChargeAmount()));
					
				} else {
					// 设置使用日期
					ket.setUseDate(new Date());
				}
				ket.setCouponStatus(couponStatus);
				couponMarketMapper.updateById(ket);

			}
			log.info("=====券集市修改完毕=====");
			return ResponseInfo.success(msg);
		}
		log.warn("==================修改失败!没有符合条件的优惠券!==================");
		return ResponseInfo.error("修改失败!没有符合条件的优惠券!");
	}

	@Override
	public ResponseInfo<String> buyCoupon(String userNo, String couponid, int nums) {
		Lock lock = new ReentrantLock();
		lock.tryLock();
		log.info("============购买优惠券===========userNo={},couponid ={},num={}", userNo, couponid, nums);
		LambdaQueryWrapper<CouponManger> queryWrapper = new LambdaQueryWrapper<>();
		// 查询参数
		CouponManger couponManger = new CouponManger();
		couponManger.setCouponStatus(SysConstant.coupon_status_no_use00);

		// 比较方式
		queryWrapper.eq(CouponManger::getId, couponid);
		// 大于
		// queryWrapper.le(CouponManger::getEffectiveTime, new Date());
		// 小于
		queryWrapper.ge(CouponManger::getInvalidTime, new Date());
		queryWrapper.ge(CouponManger::getCanNum, nums - 1);
		// 查询
		log.info("============执行条件查询,查询未发放,已经生效的优惠券,并且可使用大于 {}===========", nums);
		CouponManger cou = couponMangerMapper.selectOne(queryWrapper);
		log.info("============执行条件查询,查询结果===========", cou);
		List<CouponList> list = new ArrayList<>(nums);
		CouponList coupon = null;
		if (cou != null && cou.getCanNum() - nums >= 0) {
			for (int i = 0; i < nums; i++) {
				// 生成优惠券列表
				coupon = createdList(cou, userNo, SysConstant.coupon_source_buy);
				list.add(coupon);
			}
			// 保存列表
			colistService.saveBatch(list);
			// 修改库存
			cou.setCanNum(cou.getCanNum() - nums);
			cou.setUsedNum(cou.getUsedNum() + nums);
			this.update(cou);
			// 发送通知
			Map<String, Object> map = new HashMap<>();
			map.put("recvObject", userNo);
			map.put("sendType", "02");
			map.put("sendContent", "恭喜您,购买 <" + cou.getTitle() + "> 1张,请及时使用!");
			this.rabbitTemplate.convertAndSend("InfoCenter_sendSMS", JSONObject.toJSONString(map));
			log.info("==================购买成功==================");
			// 删除redis
			lock.unlock();
			return ResponseInfo.success("购买成功!");
		}
		lock.unlock();
		log.warn("==================购买失败!优惠券已经消耗完!==================");
		return ResponseInfo.errorReturn("购买失败!优惠券已经消耗完!");
	}

	/**
	 * 查询新人领的券
	 */
	@Override
	public ResponseInfo<List<CouponList>> queryNewCuponMarket(String currentUserId) {
		log.info("======开始查询新人礼包=,userNo={}=========",currentUserId);
		CouponList couponList =new CouponList();
		couponList.setUserNo(currentUserId);
		couponList.setCouponNode(SysConstant.coupon_Node_regist);
		if(org.apache.commons.lang3.StringUtils.isEmpty(currentUserId)) {
			return queryCoupon();
		}
		ResponseInfo<List<CouponList>> queryAll = colistService.queryAll(couponList);
		log.info("======开始查询新人礼包=,查询结果=========",queryAll);
		return queryAll;
	}
	
	public ResponseInfo<List<CouponList>> queryCoupon(){
		log.info("============接收到查询新人券的请求==========");
		LambdaQueryWrapper<CouponManger> queryWrapper = new LambdaQueryWrapper<>();
		// 查询参数
		CouponManger couponManger = new CouponManger();
		couponManger.setCouponNode(SysConstant.coupon_Node_regist);
		couponManger.setCouponStatus(SysConstant.coupon_status_no_use00);

		// 比较方式
		queryWrapper.eq(CouponManger::getCouponStatus, couponManger.getCouponStatus());
		queryWrapper.eq(CouponManger::getCouponNode, couponManger.getCouponNode());
		// 大于
		queryWrapper.le(CouponManger::getEffectiveTime, new Date());
		// 小于
		queryWrapper.ge(CouponManger::getInvalidTime, new Date());
		queryWrapper.ge(CouponManger::getCanNum, 0);
		// 查询
		log.info("============执行条件查询,查询未发放,已经生效的优惠券,并且可使用大于0===========");
		List<CouponManger> list = couponMangerMapper.selectList(queryWrapper);
		log.info("============执行条件查询,查询结果===========", list);
		List<CouponList> queryList =new ArrayList<>();
		if (!list.isEmpty()) {
			for (CouponManger cou : list) {
					// 生成优惠券列表
					CouponList coupon = createdList(cou, "temp", SysConstant.coupon_Node_regist);
					queryList.add(coupon);
				
			}
		}
		return ResponseInfo.success(queryList);
	}
	
}