package com.xinyunfu.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.Constant.SysConstant;
import com.xinyunfu.customer.dto.user.UserInfoDTO;
import com.xinyunfu.entity.CouponMarket;
import com.xinyunfu.feign.CustomerManageFeign;
import com.xinyunfu.feign.EBankFeign;
import com.xinyunfu.feign.OrderTreeFeign;
import com.xinyunfu.feign.QuestionBankFeign;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.CouponMarketMapper;
import com.xinyunfu.service.CouponMarketService;
import com.xinyunfu.service.NoticeInfoService;
import com.xinyunfu.util.RandomUtils;
import com.xinyunfu.util.RedisLock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhen couponMarket Service
 */
@Slf4j
@Service
public class CouponMarketServiceImpl extends ServiceImpl<CouponMarketMapper, CouponMarket>
		implements CouponMarketService {
	private String nowDate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
	@Autowired
	CustomerManageFeign customerManage;
	@Autowired
	CouponMarketMapper couponMarketMapper;
	@Autowired
	QuestionBankFeign questionBankFeign;
	@Autowired
	OrderTreeFeign orderTreeFeign;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private EBankFeign ebankFeign;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private NoticeInfoService noticeInfoService;
	/**
	 * 分页查询
	 * 
	 * @param couponMarket 请求参数
	 * @return couponMarket分页列表
	 */
	@SuppressWarnings("unchecked")
	public ResponseInfo<IPage<CouponMarket>> list(CouponMarket couponMarket, Integer page, Integer pageSize) {
		LambdaQueryWrapper<CouponMarket> queryWrapper = new LambdaQueryWrapper<>();
		if (couponMarket.getId() != null) {
			queryWrapper.eq(CouponMarket::getId, couponMarket.getId());
		}

		if (couponMarket.getCouponId() != null) {
			queryWrapper.eq(CouponMarket::getCouponId, couponMarket.getCouponId());
		}

		if (couponMarket.getAmount() != null) {
			queryWrapper.eq(CouponMarket::getAmount, couponMarket.getAmount());
		}

		if (!StringUtils.isEmpty(couponMarket.getOrderNo())) {
			queryWrapper.eq(CouponMarket::getOrderNo, couponMarket.getOrderNo());
		}

		if (!StringUtils.isEmpty(couponMarket.getUserNo())) {
			queryWrapper.eq(CouponMarket::getUserNo, couponMarket.getUserNo());
		}

		if (!StringUtils.isEmpty(couponMarket.getCouponStatus())) {
			queryWrapper.eq(CouponMarket::getCouponStatus, couponMarket.getCouponStatus());
		}

		if (couponMarket.getCreatedDate() != null) {
			queryWrapper.eq(CouponMarket::getCreatedDate, couponMarket.getCreatedDate());
		}

		if (!StringUtils.isEmpty(couponMarket.getCreatedBy())) {
			queryWrapper.eq(CouponMarket::getCreatedBy, couponMarket.getCreatedBy());
		}

		if (couponMarket.getUpdatedDate() != null) {
			queryWrapper.eq(CouponMarket::getUpdatedDate, couponMarket.getUpdatedDate());
		}

		if (!StringUtils.isEmpty(couponMarket.getUpdatedBy())) {
			queryWrapper.eq(CouponMarket::getUpdatedBy, couponMarket.getUpdatedBy());
		}

		if (couponMarket.getUseDate() != null) {
			queryWrapper.eq(CouponMarket::getUseDate, couponMarket.getUseDate());
		}
		queryWrapper.orderByDesc(CouponMarket::getCreatedDate);

		Page<CouponMarket> pages = new Page<CouponMarket>(page == null ? 1 : page, pageSize == null ? 15 : pageSize);
		IPage<CouponMarket> selectPage = super.baseMapper.selectPage(pages, queryWrapper);
		return ResponseInfo.success(selectPage);
	}

	/**
	 * 分页查询
	 * 
	 * @param couponMarket 请求参数
	 * @return couponMarket分页列表
	 */
	@Override
	public ResponseInfo<Map<String, Object>> queryByGroupBy(String userNo, String couponStatus, String beginDate,
			String endDate, Integer page, Integer pageSize) {
		// 如果查找已经转让
		Map<String, Object> result = new HashMap<>();
		result = super.baseMapper.queryByGroupBySign(userNo, Arrays.asList(couponStatus.split(";")), beginDate, endDate);
		if (SysConstant.coupon_status_use_ed01.equals(couponStatus)) {
			couponStatus=SysConstant.coupon_status_to_be_recorded;
			result.put("Enrolled", super.baseMapper.queryByGroupBySign(userNo, Arrays.asList(couponStatus.split(";")), beginDate, endDate));
		}
		return ResponseInfo.success(result);
	}

	@Override
	public ResponseInfo<IPage<Map<String, Object>>> queryByGroupList(Integer page, Integer pageSize,
			String currentUserId, String couponStatus, String beginDate, String endDate) {

		// 根据条件查询
		Page<Map<String, Object>> pages = new Page<Map<String, Object>>(page == null ? 1 : page,
				pageSize == null ? 15 : pageSize);
		IPage<Map<String, Object>> pageResult = null;
		if (SysConstant.coupon_status_use_ed01.equals(couponStatus)) {
			pageResult = super.baseMapper.queryByGroupListSigns(pages, currentUserId, couponStatus, beginDate,endDate);
		} else {
			pageResult = super.baseMapper.queryByGroupList(pages, currentUserId, couponStatus, beginDate, endDate);
		}

		return ResponseInfo.success(pageResult);
	}

	/**
	 * 根据条件查询详情
	 * 
	 * @param couponMarket 请求参数
	 * @return couponMarket详情
	 */
	public ResponseInfo<CouponMarket> query(CouponMarket couponMarket) {
		LambdaQueryWrapper<CouponMarket> queryWrapper = new LambdaQueryWrapper<>();
		if (couponMarket.getId() != null) {
			queryWrapper.eq(CouponMarket::getId, couponMarket.getId());
		}
		if (couponMarket.getCouponId() != null) {
			queryWrapper.eq(CouponMarket::getCouponId, couponMarket.getCouponId());
		}
		if (couponMarket.getAmount() != null) {
			queryWrapper.eq(CouponMarket::getAmount, couponMarket.getAmount());
		}
		if (!StringUtils.isEmpty(couponMarket.getOrderNo())) {
			queryWrapper.eq(CouponMarket::getOrderNo, couponMarket.getOrderNo());
		}
		if (!StringUtils.isEmpty(couponMarket.getUserNo())) {
			queryWrapper.eq(CouponMarket::getUserNo, couponMarket.getUserNo());
		}
		if (!StringUtils.isEmpty(couponMarket.getCouponStatus())) {
			queryWrapper.eq(CouponMarket::getCouponStatus, couponMarket.getCouponStatus());
		}
		if (couponMarket.getCreatedDate() != null) {
			queryWrapper.eq(CouponMarket::getCreatedDate, couponMarket.getCreatedDate());
		}
		if (!StringUtils.isEmpty(couponMarket.getCreatedBy())) {
			queryWrapper.eq(CouponMarket::getCreatedBy, couponMarket.getCreatedBy());
		}
		if (couponMarket.getUpdatedDate() != null) {
			queryWrapper.eq(CouponMarket::getUpdatedDate, couponMarket.getUpdatedDate());
		}
		if (!StringUtils.isEmpty(couponMarket.getUpdatedBy())) {
			queryWrapper.eq(CouponMarket::getUpdatedBy, couponMarket.getUpdatedBy());
		}
		if (couponMarket.getUseDate() != null) {
			queryWrapper.eq(CouponMarket::getUseDate, couponMarket.getUseDate());
		}
		return ResponseInfo.success(super.getOne(queryWrapper));
	}

	/**
	 * 根据主键id查询详情
	 * 
	 * @param id couponMarketid
	 * @return couponMarket详情
	 */
	public ResponseInfo<CouponMarket> queryById(Long id) {
		return ResponseInfo.success(super.getById(id));
	}

	/**
	 * 添加couponMarket
	 * 
	 * @param couponMarket 实体
	 * @return ResponseEntity
	 */
	public ResponseInfo<Boolean> add(CouponMarket couponMarket) {
		return ResponseInfo.success(super.save(couponMarket));
	}

	/**
	 * 修改couponMarket
	 * 
	 * @param couponMarket 实体
	 * @return ResponseEntity
	 */
	public ResponseInfo<Boolean> update(CouponMarket couponMarket) {
		return ResponseInfo.success(super.updateById(couponMarket));
	}

	/**
	 * 删除couponMarket
	 * 
	 * @param id 主键id
	 * @return ResponseEntity
	 */
	public ResponseInfo<Boolean> delete(Long id) {
		return ResponseInfo.success(super.removeById(id));
	}

	@Override
	public ResponseInfo<String> queryCuponMarket() {
		LinkedList<JSONObject> list = new LinkedList<>();
		JSONObject priceType30 = new JSONObject();
		JSONObject priceType20 = new JSONObject();
		JSONObject priceType10 = new JSONObject();

		priceType30.put("count", RandomUtils.getCount3());
		priceType20.put("count", RandomUtils.getCount2());
		priceType10.put("count", RandomUtils.getCount1());

		priceType30.put("price", 50);
		priceType20.put("price", 50);
		priceType10.put("price", 50);
		priceType30.put("sellPrice", 30);
		priceType20.put("sellPrice", 20);
		priceType10.put("sellPrice", 10);

		priceType30.put("title", "无门槛金券50元，可无限叠加使用");
		priceType10.put("title", "无门槛金券50元，可无限叠加使用");
		priceType20.put("title", "无门槛金券50元，可无限叠加使用");
		priceType30.put("scope", "全品类通用无门槛");
		priceType20.put("scope", "全品类通用无门槛");
		priceType10.put("scope", "全品类通用无门槛");

		list.add(priceType10);
		list.add(priceType20);
		list.add(priceType30);
		

		return ResponseInfo.success(JSONObject.toJSONString(list));
	}

	@Override
//	@Transactional(readOnly = false, noRollbackFor = Exception.class)
	public ResponseInfo<String> signCoupon(String currentUserId, BigDecimal price) {
		log.info("====接收转让请求==,userNo={},price={}===", currentUserId, price);
		// 查询用户转让
		Lock lock = new ReentrantLock();
		lock.lock();
		List<CouponMarket> selectList = null;
		try {
			// 查询用户最近订单
			log.info("=======查询已经激活订单==");
			LambdaQueryWrapper<CouponMarket> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(CouponMarket::getCouponStatus, SysConstant.coupon_status_no_activation05);
			queryWrapper.eq(CouponMarket::getUserNo, currentUserId);
			selectList = couponMarketMapper.selectList(queryWrapper);
//
			if (selectList.isEmpty() || selectList.size() != 6) {
				log.warn("转让失败!没有查询生效的万能券!或者没有激活!");
				return ResponseInfo.errorReturn("转让失败!没有查询生效的万能券!");
			}
			if (selectList.get(0).getUserType() == 1) {
				return ResponseInfo.errorReturn("转让失败!会员已经转让!");
			}
			//循环修改数据
			selectList.forEach(s->{s.setCouponStatus(SysConstant.coupon_status_no_sign);s.setSignPrice(price);
			s.setSignDate(new Date());couponMarketMapper.updateById(s);});
			
//			couponMarketMapper.updateByByOrder(SysConstant.coupon_status_no_sign, selectList.get(0).getOrderNo(),
//					price);
			log.info("=====调用订单树中心,通知转让=======");
			ResponseInfo<String> result = orderTreeFeign.assignment(selectList.get(0).getOrderNo(), price.intValue());
			log.info("=====调用订单树中心,回复===msg={}====", result);
			if (!result.isSuccess()) {
				couponMarketMapper.updateByByOrder(SysConstant.coupon_status_no_activation05,
						selectList.get(0).getOrderNo(), price);
				noticeInfoService.saveNotifyInfo("orderTreeFeign", result.getMessage(), null, "OrderTreeFeign",
						"assignment", selectList == null ? null : selectList.get(0).getOrderNo(), price.intValue());
				log.error("转让失败!订单树通知错误!");
				return ResponseInfo.errorReturn(result.getMessage());
			}

			return ResponseInfo.success("转让成功!");
		} catch (Exception e) {
			noticeInfoService.saveNotifyInfo("orderTreeFeign", e.getMessage(), null, "OrderTreeFeign", "assignment",
					selectList == null ? null : selectList.get(0).getOrderNo(), price.intValue());
			log.error("========转让错误!===msg={},e===", e.getMessage(), e);
			return ResponseInfo.errorReturn("转让失败!系统错误,请重试!...");
		} finally {
			lock.unlock();
		}

	}

	@Override
	public ResponseInfo<String> updateSignCoupon(String currentUserId) {
		log.info("=======查询用户最后一次订单==");
		List<CouponMarket> marketByUser = couponMarketMapper.queryLastMarketByUser(currentUserId);
		log.info("=======查询订单={}=", marketByUser);
		marketByUser.forEach(m -> {
			m.setCouponStatus(SysConstant.coupon_status_use_ed01);
			this.update(m);
		});
//		couponMarketMapper.updateByByOrder(SysConstant.coupon_status_use_ed01, orderNo);
		return ResponseInfo.success("修改成功!");
	}

	@Override
	public ResponseInfo<String> queryIsCanBuy(UserInfoDTO result, String currentUserId) {
		log.info("=======查询用户是否可以购买==,result={}", result, currentUserId);
		if(result.getId() ==null ) {
			return ResponseInfo.success("不允许购买");
		}
		if (result.getLevel() == 1) {
			return ResponseInfo.success("可以购买!");
		}
		if (couponMarketMapper.queryByUserNo(currentUserId) > 0) {
			return ResponseInfo.success("不允许购买");
		}
		return ResponseInfo.success("可以购买!");
	}

	@Override
	public ResponseInfo<String> queryCanSign(String currentUserId) {
		// 查询用户最近订单
		log.info("=======查询用户最后一次订单==");
		LambdaQueryWrapper<CouponMarket> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CouponMarket::getCouponStatus, SysConstant.coupon_status_no_activation05);
		queryWrapper.eq(CouponMarket::getUserNo, currentUserId);
		List<CouponMarket> selectList = couponMarketMapper.selectList(queryWrapper);
		log.info("=======查询订单={}=", selectList);
		if (selectList.isEmpty() || selectList.size() != 6) {
			log.warn("查询失败!没有查询生效的万能券!");
			return ResponseInfo.success("转让失败!没有查询生效的万能券!");
		}
		if (selectList.get(0).getUserType() == 1) {
			return ResponseInfo.success("会员用户已经转让!");
		}
		CouponMarket dto = selectList.get(0);
		dto.setNumber(dto.getNumber() * selectList.size());
		JSONObject parseObject = JSONObject.parseObject(JSONObject.toJSONString(dto));
		parseObject.put("dueDate",DateFormatUtils.format(dto.getDueDate(),"yyyy-MM-dd"));
		parseObject.put("signDate",DateFormatUtils.format(dto.getCreatedDate(),"yyyy-MM-dd"));
		parseObject.put("showPrice", 50);
		return ResponseInfo.success(parseObject.toJSONString());
	}

	ExecutorService executor = Executors.newCachedThreadPool();

	/**
	 * 消费优惠券 1.当订单树推荐人触发,产生奖励用户交易 2.如果是会员,查询创建时间最旧的订单,取三张优惠券,消费掉. 通知EBank支付
	 * 3.如果是非会员,查询最近一笔生效的订单,取三张优惠券,消耗掉.通知EBank支付
	 */
	@Override
	public ResponseInfo<String> paymentByCoupon(String recvUserNo, String channel, String sourceUserNo,
			String orderNo) {
		RedisLock redisLock =new RedisLock(redisTemplate, SysConstant.redisCachePayKey+"recvUserNo");
		Future<ResponseInfo<String>> result = executor.submit(() -> {
			try {
				if(redisLock.lock()) {
					log.info("========收到订单树的消费券请求={},recvUserNo={},channel={},sourceUserNo={},orderNo={}===========",
							recvUserNo, channel, sourceUserNo, orderNo);
//					ResponseInfo<UserInfoDTO> userInfo =customerManage.getUserInfo(Long.parseLong(recvUserNo));
//					log.info("======查询用户信息,用户返回=====,userInfo={}", userInfo);
//					if (!userInfo.isSuccess()) {
//						return ResponseInfo.errorReturn(userInfo.getMessage());
//					}6
//					UserInfoDTO dto = userInfo.getData();
					UserInfoDTO dto = new UserInfoDTO();
					dto.setLevel(1);
					Integer level = dto.getLevel();
					// 如果是用户
					List<CouponMarket> lastMarketByUser = null;
					// 1.根据用户类型,查询订单
					if (level == 1) {
						lastMarketByUser = couponMarketMapper.queryFirstMarketByUser(recvUserNo);
					} else {
						lastMarketByUser = couponMarketMapper.queryLastMarketByUser(recvUserNo);
					}
					log.info("======查询用户优惠券=====,lastMarketByUser={}", lastMarketByUser);
					if (lastMarketByUser.isEmpty()
							||( !SysConstant.coupon_status_no_sign.equals(lastMarketByUser.get(0).getCouponStatus()) 
							&& !SysConstant.coupon_status_no_activation05.equals(lastMarketByUser.get(0).getCouponStatus()))
							) {
						log.error("====券的状态有误!===userNo={}", recvUserNo);
						
						return ResponseInfo.errorReturn("券的状态有误");
					}
					// 修改用户券->转让中,等 回调的时候,修改券的状态=>已经使用
					CouponMarket market = lastMarketByUser.get(0);
					market.setCouponStatus(SysConstant.coupon_status_to_be_recorded);
					// 消费订单号,后面回调查询
					market.setConsumerOrderNo(orderNo);
					market.setUseDate(new Date());
					market.setTotalPrice(market.getSignPrice().subtract(market.getServiceCharge()).add(market.getRewardAmount())
							.multiply(new BigDecimal(market.getNumber())));
					log.info("======计算总价,(单价-手续费+奖励) *乘以数量={}=====", market.getTotalPrice());
					log.info("======开始调用EBank中心=========");
					// 调用支付中心,进行支付
					JSONObject parmentObject =new JSONObject();
					parmentObject.put("orderNo", orderNo);
					parmentObject.put("sourceUserNo", sourceUserNo);
					parmentObject.put("receiveUserNo", recvUserNo);
					parmentObject.put("amount", market.getTotalPrice());
					parmentObject.put("serverName", "VolumeMarket");
					ResponseInfo<String> paymentByCoupon = ebankFeign.transfer(parmentObject);
					log.info("======调用EBank中心结果===={}=====", paymentByCoupon);
					noticeInfoService.saveNotifyInfo("ebankFeign", paymentByCoupon.getMessage(), null, "EBankFeign",
							"paymentByCoupon", recvUserNo, market.getTotalPrice(), channel, sourceUserNo, orderNo);
					this.update(market);
					// 调用发送消息
					log.info("==调用发送消息==");
					Map<String, Object> map = new HashMap<>();
					map.put("userNo", recvUserNo);
					map.put("templateName", "001");
					JSONArray jsonArray =new JSONArray();
					jsonArray.add(market.getNumber());
					jsonArray.add(market.getTotalPrice());
					map.put("data",jsonArray);
					rabbitTemplate.convertAndSend("info_center_exchange","info_center_key", JSONObject.toJSONString(map));
					
					//
					if (Integer.parseInt(orderNo.split("_")[1])%6 == 0 ) {
						// 调用发送消息
						log.info("==调用发送消息==");
						map.put("recvObject", dto.getPhone());
						map.put("sendType", "02");
						map.put("sendContent", "恭喜您:您的金券已经全部转让成功!获得了再次购买超级礼包的资格，及时购买获得更多收益！");
						rabbitTemplate.convertAndSend("InfoCenter_sendSMS", JSONObject.toJSONString(map));
					}
				  }
					
				} catch (Exception e) {
					
				}finally {
					redisLock.unlock();
				}
				
			log.info("==调用发送完毕==");
			return ResponseInfo.success("处理成功!");
		});

		try {
			return result.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("====线程异常==msg={},e", e.getMessage(), e);
			return ResponseInfo.errorReturn("线程异常!");
		}
		

	}

	@Override
	public ResponseInfo<String> backTransferAccounts(String orderId, Integer status) {
		log.info("===处理支付中心回调===");
		RedisLock redisLock =new RedisLock(redisTemplate, SysConstant.redisCacheBackTransKey);
		try {
		   if(redisLock.lock()) {
				LambdaQueryWrapper<CouponMarket> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(CouponMarket::getConsumerOrderNo, orderId);
				// 根据订单号查询
				CouponMarket coupon = this.getOne(queryWrapper);
				log.info("===根据订单号查询结果={}==", coupon);
				if (coupon == null) {
					return ResponseInfo.errorReturn("根据该订单号查询失败!");
				}
				if (!SysConstant.coupon_status_no_sign.equals(coupon.getCouponStatus())) {
					return ResponseInfo.errorReturn("该订单状态错误,没有在待结算中!");
				}
				coupon.setCouponStatus(SysConstant.coupon_status_use_ed01);
				this.update(coupon);
		   }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			redisLock.unlock();
		}
		
		return ResponseInfo.success("回调处理成功!");
	}

	@Override
	public void loseOrderNotify(String loseUser, String userNo) {
		RedisLock redisLock =new RedisLock(redisTemplate, SysConstant.redisCacheloseOrderNotifyKey);
		executor.submit(() -> {
			//多线程记录
			
			try {
				if(redisLock.lock()) {
					ResponseInfo<UserInfoDTO> userInfo = customerManage.getUserInfo(Long.parseLong(userNo));
					log.info("======查询用户信息,用户返回=====,userInfo={}", userInfo);
	
					UserInfoDTO dto = userInfo.getData();
					Integer level = dto.getLevel();
					// 如果是用户
					List<CouponMarket> lastMarketByUser = null;
					// 1.根据用户类型,查询订单
					if (level == 1) {
						lastMarketByUser = couponMarketMapper.queryFirstMarketByUser(userNo);
					} else {
						lastMarketByUser = couponMarketMapper.queryLastMarketByUser(userNo);
					}
					log.info("======查询用户优惠券=====,lastMarketByUser={}", lastMarketByUser);
					if (lastMarketByUser.isEmpty() || lastMarketByUser.size() == 0) {
						log.error("====券的状态有误!===userNo={}", lastMarketByUser);
					}
	
					CouponMarket market = lastMarketByUser.get(0);
					market.setCouponStatus(SysConstant.coupon_status_no_sign);
					market.setTotalPrice(market.getSignPrice().subtract(market.getServiceCharge()).add(market.getRewardAmount())
							.multiply(new BigDecimal(market.getNumber())));
					log.info("======计算总价,(单价-手续费+奖励) *乘以数量={}=====", market.getTotalPrice());
					// 调用发送消息
					log.info("==调用发送个推消息==");
					Map<String, Object> map = new HashMap<>();
					map.put("userNo", loseUser);
					map.put("templateName", "002");
					JSONArray jsonArray =new JSONArray();
					jsonArray.add(dto.getNickName());
					jsonArray.add(market.getTotalPrice());
					map.put("data",jsonArray);
	
					rabbitTemplate.convertAndSend("info_center_exchange","info_center_key", JSONObject.toJSONString(map));
					ResponseInfo<UserInfoDTO> resultDto = customerManage.getUserInfo(Long.parseLong(userNo));
					// 调用发送消息
					log.info("==调用发送消息==");
					map.put("recvObject", resultDto.getData().getPhone());
					map.put("sendType", "02");
					map.put("sendContent", "您的粉丝"+dto.getNickName()+"成功转让了3张券，转让收入"+market.getTotalPrice()+"元 ，您已错过了好几个小目标~");
	//				
					rabbitTemplate.convertAndSend("InfoCenter_sendSMS", JSONObject.toJSONString(map));
					log.info("==调用发送完毕==");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				redisLock.unlock();
			}
			
		});

	}
    
	@Override
	public ResponseInfo<String> queryIsCanBuyByMS(UserInfoDTO data, String currentUserId) {
		log.info("=======查询用户是否可以购买==,result={}", data, currentUserId);
		if (data.getLevel() == 1) {
			return ResponseInfo.success("可以购买!");
		}
		if (couponMarketMapper.queryByUserNo(currentUserId) > 0) {
			return ResponseInfo.errorReturn("不允许购买");
		}
		return ResponseInfo.success("可以购买!");
	}
	
}