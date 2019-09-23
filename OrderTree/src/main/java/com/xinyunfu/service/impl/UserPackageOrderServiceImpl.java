package com.xinyunfu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.constants.VIP;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.PayLogMapper;
import com.xinyunfu.mapper.TreeNoMapper;
import com.xinyunfu.mapper.UserPackageOrderMapper;
import com.xinyunfu.model.PayLog;
import com.xinyunfu.model.TreeNo;
import com.xinyunfu.model.UserPackageOrder;
import com.xinyunfu.model.UserPush;
import com.xinyunfu.sao.CustomerManageSao;
import com.xinyunfu.service.IUserPackageOrderService;
import com.xinyunfu.task.PayMoneyWorker;
import com.xinyunfu.task.Wroker;
import com.xinyunfu.utils.OrderUtils;
import com.xinyunfu.utils.RedisCommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jace
 * @since 2019-07-06
 */
@Service
@Slf4j
public class UserPackageOrderServiceImpl extends ServiceImpl<UserPackageOrderMapper, UserPackageOrder> implements IUserPackageOrderService {

	
	

	@Autowired
	private UserPackageOrderMapper mapper;
	
	@Autowired
	private OrderTreeServiceImpl otService;
	
	@Autowired
	private CustomerManageSao sao;
	
	@Autowired
	private TreeNoMapper treeNoMapper;
	
	@Resource(name="singleWorker")
	private ExecutorService executorService;
	
	@Autowired
	private PayLogMapper plMapper;
	
	
	@Autowired
	private TreeNoServiceImpl tnService;
	
	@Autowired
	private PayLogServiceImpl payService;
	
	@Resource(name="payWorker")
	private ExecutorService es;
	
	
	@Autowired
	private UserPushServiceImpl upService;
	
	@Autowired
	RedisCommonUtil redis;
 
	
	public ResponseInfo<List> getUserInfo(long userNo){
		log.info("==================================getUserInfo=================================");
		return sao.getPushLinked(userNo);
	}
	
	/**
	 * 查询是否存在有效订单树节点
	 * @param userNo
	 * @return
	 */
	public ResponseInfo<String> allowJoinTree(String userNo,String userType) {
        
		if(VIP.SUPER_VIP.equals(userType)||mapper.getCountByUserNo(userNo)==0) {//   推荐大使直接可以进入
			return ResponseInfo.success(null);
        }else {
        	return new ResponseInfo<String>("0001", "用户已存在有效订单树节点", null);
        }
	}

	
	
	/**
	 *	查询剩余有效的节点数，获取万能卷的数量
	 * @param json
	 * @return
	 */
	public ResponseInfo<String> getVildNodeCount(JSONObject json){
		String userNo = json.getString("userNo");
		String userType = json.getString("userType");
		int vaildCount = 0;
		if(VIP.SUPER_VIP.equals(userType)) {// 推广大使
			//  -1的原因是当节点置为0的时候才会过来减少主订单数量
			int vaildPackageCount = mapper.getPackageCount(userNo);
			vaildCount = treeNoMapper.getVaildNodeCount(userNo)*3+vaildPackageCount*18;
		}else {
			vaildCount = treeNoMapper.getVaildNodeCount(userNo)*3;
		}
		return ResponseInfo.success(vaildCount+"");
	}
	
	
	
	
	/**
	 * 
	 * @param userNo  用户编号
	 * @param count   套餐数量
	 * @param orderNo 订单号（主订单）
	 * @return
	 */
	@Transactional
	public ResponseInfo<String> insertOne(String userNo,int count,String orderNo,String userType){
		
		if((!redis.exist(OrderUtils.createPushlistKey(userNo)))&&(!this.getPushList(userNo, userType))) {
			return new ResponseInfo<String>("0002", "获取推荐关系失败", "获取推荐关系失败");
		}
		
		if(VIP.SUPER_VIP.equals(userType)) {
			return this.insertsuperVIP(userNo, count, orderNo,userType);
		}else {
			if(mapper.getCountByUserNo(userNo)!=0) {
				return new ResponseInfo<String>("0001", "用户已存在有效订单树节点", null);
			}else {
				return this.insertVIP(userNo, count, orderNo,userType);
			}
		}
		
	}
	
	
	/**
	 *  提交订单树任务:1.查询是否有未提现支付的订单
	 * @param upo
	 * @return
	 */
	@PostMapping("/api/assignment")
	public ResponseInfo<String> assignment(String mainOrderNo){
		UserPackageOrder upo = mapper.getOneByMainOrderNo(mainOrderNo);
		if(null == upo) return new ResponseInfo<>("0002","不存在未转让的卷",null);
		List<PayLog> plist = plMapper.getNoPayByMainOrderNo(mainOrderNo);
		log.info("plist===>> {}",plist);
		if(null!=plist&&(!plist.isEmpty())) {
		  for(PayLog pl : plist) {// 
			 pl.setIsSubmit(0);
			 es.execute(new PayMoneyWorker(pl, payService));
		  } 
		}
		mapper.updateSubmitByOrderNo(mainOrderNo);
		return ResponseInfo.success(null);
	}
	
	
	
	/**
	 * 	普通用户提交套餐
	 * @param userNo
	 * @param count
	 * @param orderNo
	 * @return
	 */
	@Transactional
	public ResponseInfo<String> insertVIP(String userNo,int count,String orderNo,String userType){
		ResponseInfo<String> res = null;
		log.info("套餐购买人：{}，购买数量：{}，购买人类型：{}，订单编号：{}",userNo,count,userType,orderNo);
		UserPackageOrder upo = new UserPackageOrder();
		upo.setMainOrderNo(orderNo);
		upo.setUserNo(userNo);
		if(VIP.COMMON_VIP.equals(userType)) {
			count = 1;
		}
		upo.setCount(count);
		upo.setVaildCount(count);
		upo.setEnables(1);
		upo.setUserType(userType);
			//推广大使自动提交
		if(VIP.SUPER_VIP.equals(userType)) {
			upo.setIsSubmit(0) ;
		}else {
			upo.setIsSubmit(1);
		}
		this.save(upo);
		if(!VIP.SUPER_VIP.equals(userType)||(otService.getOrderTreeByUserNo(upo.getUserNo())==null)) {
			//如果不是vip那么一定要加入任务队列否则无法执行  || 即使是vip，也必须要是没有任何有效节点存在，即order_tree查不到数据才能加入队列
			executorService.execute(new Wroker(upo, otService));
		}
		res = ResponseInfo.success("恭喜您，提交订单树成功");
		return res;	
	}
	
	
	
	/**
	 *    推广大使提交套餐
	 * @param userNo
	 * @param count
	 * @param orderNo
	 * @return
	 */
	@Transactional
	public ResponseInfo<String> insertsuperVIP(String userNo,int count,String orderNo,String userType){
		log.info("推广大使用户      -------userNo:{},购买了套餐:{},订单编号：{},提交任务中............",userNo,count,orderNo);
		UserPackageOrder upo = mapper.getOneByUserNo(userNo);
		if(null == upo) {//  已经全部结清，则不在并单
			return insertVIP(userNo, count, orderNo,userType);
		}else {    //  如存在未结清的单，则需要并单，并单则不需要提交订单树进行操作
			int allCount = upo.getCount();
			int vaildCount = upo.getVaildCount();
			log.info("推广大使用户      -------userNo:{},存在未结清的订单：{},剩余：{}套，开启并单",userNo,upo.getMainOrderNo(),vaildCount);
			upo.setCount(allCount+count);
			upo.setVaildCount(vaildCount+count);
			this.updateById(upo);
			return ResponseInfo.success(null);
		}
		
	}


	
	

	/**
	 *	根据推荐关系获取节点编号
	 *	
	 * @param pushList
	 * @return
	 */
	public String getVaildNodeNoByUserNo(JSONArray pushList) {
		
		for(int i = 0 ;i< pushList.size();i++) {
		  String tempUserNo = pushList.getString(i);
		   if(VIP.ADMIN_NODE_NO.equals(tempUserNo)) {//推荐人为平台用户，那么直接走，6缺6赠送原则
			  TreeNo tn =  tnService.presentedLazyUser();
		   }else {
			  TreeNo tn = this.getValidNode(pushList.getString(i));
		   }
		}
		return null;
	}
	
	
   
	/**
	 * 1.   根据用户编号查询存在的有效订单号
	 * 2.   根据订单号确认该用户的节点
	 * @return
	 */
	public TreeNo getValidNode(String userNo) {
		UserPackageOrder upo = mapper.getOneByUserNo(userNo);
		if(null == upo) {
			return null;
		}else {
			String mainOrderNo = upo.getMainOrderNo();        //主订单编号
			TreeNo tn = treeNoMapper.getNodeByMainOrderNo(mainOrderNo); //获取有效的节点编号
			return tn;
		}
		
	}


	/**
	 *  查询全部有效数据
	 * @return
	 */
	public List<UserPackageOrder> findAll() {
		
		return mapper.findAll();
	}

	
	/**
	 * 用户存在未完结的套餐订单
	 * @param userNo
	 * @return
	 */
	public boolean checkUserVaild(String userNo) {
		
		return mapper.getCountByUserNo(userNo)>0;
	}
	
	
	
	
	/**
	 * 	获取当前用户的推荐关系图
	 * @param userNo
	 * @return
	 */
	public boolean getPushList(String userNo,String userType) {
	  try {
        
		  ResponseInfo<List> res = sao.getPushLinked(Long.parseLong(userNo));
		  log.info("========== >feign to customerManage pushLinked："+res);
		  UserPush up = new UserPush();
		  up.setUserNo(userNo);
		  if(ResponseInfo.CODE_SUCCESS.equals(res.getCode())) {// 获取成功
			  String pushlist = JSONArray.toJSONString(res.getData());
			  redis.setCache(OrderUtils.createPushlistKey(userNo), pushlist);
			  up.setPusherNoLinked(pushlist);
			  up.setUserType(userType);
			  upService.save(up);
			  redis.setCache(userNo, up.getPusherNoLinked());
			  return true;
		  }
	  	}catch (Exception e) {
		log.info("==============>e:{}",e);
	}
	   	return false;
	}
	
	
	public int getUnitPrice(String mainOrderNo) {
		return mapper.getUnitPriceByOrderNo(mainOrderNo);
	}

	public UserPackageOrder getUserPackageByUserNo(String userNo) {
		// TODO Auto-generated method stub
		return mapper.getOneByUserNo(userNo);
	}
	
}
