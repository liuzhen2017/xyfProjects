package com.xinyunfu.service.impl;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.OrderTreeMapper;
import com.xinyunfu.mapper.UserPushMapper;
import com.xinyunfu.model.OrderTree;
import com.xinyunfu.model.TreeNo;
import com.xinyunfu.model.UserPackageOrder;
import com.xinyunfu.rule.EngineRule;
import com.xinyunfu.sao.CustomerManageSao;
import com.xinyunfu.sao.VolumeMarketSao;
import com.xinyunfu.service.IOrderTreeService;
import com.xinyunfu.utils.OrderUtils;
import com.xinyunfu.utils.RedisCommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jace
 * @since 2019-07-04
 */
@Service
@Slf4j
public class OrderTreeServiceImpl extends ServiceImpl<OrderTreeMapper, OrderTree> implements IOrderTreeService {
	
	
	@Autowired
	private UserPushMapper upMapper;
	
	@Autowired
	private UserPackageOrderServiceImpl upoService;
	
	@Autowired
	private TreeNoServiceImpl treeService;
	
	@Autowired
	private OrderTreeMapper mapper;
	
	@Value("${spring.free.button}")
	public String takeOn;
	
	@Autowired
	RedisCommonUtil redis;
	
	@Autowired
	private CustomerManageSao sao;
	
	@Autowired
	private VolumeMarketSao vmSao;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	private RuleConfigServiceImpl ruleConfigService;
	
	
	/**
	 *	执行订单树功能:
	 *   1.查询出userNo的推荐关系列表
	 * @return
	 * @throws Exception 
	 */
	public ResponseInfo<String> excuteOrderTree(UserPackageOrder upo) throws Exception{

		return ((EngineRule)applicationContext.getBean(ruleConfigService.getRuleBean().getBeanName())).handler(upo);
	}
	
	


	/**
	 * 刷新订单树，一个订单树进入会导致最少一，最多两个淘汰
	 * @return
	 * @throws Exception
	 */
	public boolean refreshTree() throws Exception {
		
		
		
		return false;
	}
	
	
	
	
	/**
	 * 免费赠送模式
	 * @return 
	 * @throws Exception 
	 */
	public void freeGiving(UserPackageOrder upo) throws Exception {
		//查询有效的父节点
		log.info("---------------------------------free giving------------------------------------");
		TreeNo parent = treeService.presentedLazyUser();
		if(null==parent){//赠送给平台
			treeService.givingRoot(upo);
		}else {
			treeService.fullNode(parent, upo);
		}
	}
	
	
	/**
	 * 正常模式
	 * @throws Exception 
	 */
	public void commonOrderTree(String userNo,UserPackageOrder upo) throws Exception {
		log.info("=====> commonOrderTree 进入订单{}",upo);
		TreeNo parent = treeService.getNodeByUserNo(userNo);//
		log.info("=====> commonOrderTree 父节点 {}",parent);
		treeService.insertNodeNoProcessor(parent, upo);
	}
	
	
	
	public OrderTree getNodeOfUpo(String nodeNo) {
		return mapper.getByNodeNo(nodeNo);
	}
	
	
	/**
	 * 查询是否存在有效的节点
	 * @param upo
	 * @return
	 */
	public int getValidNode(UserPackageOrder upo) {
		return mapper.getVailNodeNo(upo.getMainOrderNo());
	}


	
	public OrderTree getOrderTreeByUserNo(String userNo) {
		return mapper.getOrderTreeByUserNo(userNo);
	}
	
	/**
	 * 将nodeNo节点置为失效
	 * @param nodeNo
	 */
	public void updateSetEnables(String nodeNo) {
		mapper.updateSetEnables(nodeNo);
	}


	public Integer getSubmit(String mainOrderNo) {
	
		return mapper.getMainOrderSubmit(mainOrderNo);
	}
	
	
	
	/**
	 * 获取推荐关系
	 * @param userNo
	 * @return
	 */
	public JSONArray getPushList(String userNo){
		String pushList = (String)redis.getCache(OrderUtils.createPushlistKey(userNo));
		pushList = StringUtils.isBlank(pushList)?upMapper.getPushList(userNo):pushList;
		if(null == pushList || StringUtils.isBlank(pushList)) {
			sao.getPushLinked(Long.parseLong(userNo));
		}
		return JSONObject.parseArray(pushList);
	}
	
	
	
	public OrderTree getVailNode(String userNo) {
      	return mapper.getOrderTreeByUserNo(userNo);
	}




	public OrderTree getOrderTreeByNodeNo(String nodeNo) {
		
		return mapper.getByNodeNo(nodeNo);
	}
}
