package com.xinyunfu.service.impl;

import com.xinyunfu.model.OrderTree;
import com.xinyunfu.model.PayLog;
import com.xinyunfu.model.TreeNo;
import com.xinyunfu.model.UserPackageOrder;
import com.xinyunfu.sao.VolumeMarketSao;
import com.xinyunfu.constants.VIP;
import com.xinyunfu.mapper.TreeNoMapper;
import com.xinyunfu.service.ITreeNoService;
import com.xinyunfu.task.PayMoneyWorker;
import com.xinyunfu.utils.OrderUtils;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.concurrent.ExecutorService;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.crypto.engines.TnepresEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jace
 * @since 2019-07-08
 */
@Service
@Slf4j
public class TreeNoServiceImpl extends ServiceImpl<TreeNoMapper, TreeNo> implements ITreeNoService {

	
	@Autowired
	TreeNoMapper mapper;
	
	@Autowired
	UserPackageOrderServiceImpl upoService;
	
	@Autowired
	private OrderTreeServiceImpl otService;
	
	@Autowired
	private PayLogServiceImpl payService;
	
	@Resource(name="payWorker")
	private ExecutorService es;
	
	/**
	 *                                                             
	 *		获取节点的位置
	 *      TreeNo tn 选择需要挂入的节点
	 * @return
	 * @throws Exception 
	 */
	public void insertNodeNoProcessor(TreeNo parent,UserPackageOrder upo) throws Exception {
		//	剩余有效节点数
		int vaildCount = parent.getValidLeafCount();
	    if(vaildCount>4) {
	    	fullNode(parent, upo);
	    }else if(vaildCount>2) {
	    	fullSonLeafAndNoLeft(parent, upo);
	    }else {
	    	fullSonLeafAndFullLeft(parent,upo);
	    }
	}
	
	/**
	 * 插入订单节点关系表，节点表（未指定父节点编号）
	 * @param parent
	 * @param upo
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.NESTED)
	public TreeNo repare(UserPackageOrder upo) throws Exception {
		 TreeNo tn = new TreeNo();
		 OrderTree ot = new OrderTree();
		try {
		 //设置子节点的所有操作
		 tn.setNodeNo(OrderUtils.createNodeNo());
		 tn.setValidLeafCount(6);// 有效节点6个
		 tn.setEnables(1);//  数据有效
		 tn.setSubmitDate(System.currentTimeMillis()+"");// 提交时间
		 this.save(tn);
		 //订单节点关系表
		 ot.setMainOrderNo(upo.getMainOrderNo());// 订单编号
		 ot.setUserNo(upo.getUserNo());// 用户编号
		 ot.setNodeNo(tn.getNodeNo());// 套餐对应的节点编号    one(套餐编号) >> many (节点编号)
		 ot.setEnables(1);
		 otService.save(ot);//插入order_tree表
		 //生成节点成功，那么设置用户套餐订单表为不可用状态,推广大使则无法适用
		 upo.setVaildCount(upo.getVaildCount()-1);//有效数量减一个
		 if(upo.getVaildCount()==0) {
			 upo.setEnables(0);//置为失效
		 }
		 upoService.updateById(upo);
		}catch(Exception e) {
			log.info("data insert user_package_order and tree_no fail,UserPackageOrder:"+upo);
			throw new Exception("-----------保存数据失败------------");
		}
		return tn;
	}
	
	/**
	 * 		减少1个节点
	 * @param node
	 * @param upo
	 * @return
	 */
	public void reduceOne(TreeNo node,UserPackageOrder upo) {
		log.info("reduceOne == > TreeNo node={} ",node);
		log.info("reduceOne == > UserPackageOrder upo={}",upo);
	    int count = node.getValidLeafCount();
	    node.setValidLeafCount(count-1);
	    OrderTree nodeOfupo = otService.getNodeOfUpo(node.getNodeNo());//结果为null
	    //	另起线程，支付并记录日志
	    PayLog pl = new PayLog();
	    pl.setUserNo(nodeOfupo.getUserNo());//收款人信息
	    pl.setPayNo(node.getNodeNo()+"_"+(6-node.getValidLeafCount()));// 
	    pl.setIsSubmit(otService.getSubmit(nodeOfupo.getMainOrderNo()));
	    pl.setMainOrderNo(nodeOfupo.getMainOrderNo());
	    pl.setSourceUser(upo.getUserNo());// 哪个用户购买套餐产生的收益
	    payService.save(pl);
	    log.info("-----------------------------------pay money----------------------------------");
	    es.execute(new PayMoneyWorker(pl,payService));
		
	    if(node.getValidLeafCount()==0) {
			node.setEnables(0);  //	节点置为失效
			outNode(node);		//	出局此节点，1.vip用户，直接将主表置为失效，2.superVIP则查询剩余数量：有，继续进行订单树寻点操作（调用最初的订单提交接口），否则，置为失效。3、重新寻点，为防止再次寻找到此节点必须先把treeNo状态翻转一下
		}else {
		     log.info("node:"+node+",已经获得佣金一笔，收入：90 元");
		     this.updateById(node);
		}
	    
	}
	
	
	
	
	
	/**
	 * 节点出局： 
	 *   1.减少调有效套餐数量，同时vip用户直接套餐条目置为失效
	 *   2.推广大使用户，如有剩余套餐即可进入节点寻位逻辑
	 * @param node
	 * @return 
	 */
	private void outNode(TreeNo node) {
		log.info("node节点：[{}] 已获取全部佣金，即将进入节点失效状态",node);
	    UserPackageOrder upo = mapper.getUserPackageOrder(node.getNodeNo());  //	之前已经置为失效了，所以这里不再操作	
        //将该节点的订单节点表置为失效
	    String userNo = otService.getOrderTreeByNodeNo(node.getNodeNo()).getUserNo();
	    otService.updateSetEnables(node.getNodeNo());//将order_tree置为失效
	    node.setEnables(0);
	    this.updateById(node);
	    if(null != upo&&upo.getUserType().equals(VIP.SUPER_VIP)){
	    		// 	这个套餐还有就继续消耗这个套餐，这个套餐没有了，就不再继续消耗了，而是查找一下，还有没有别的套餐
	    		log.info("特约大使 再次寻点 第 {} 轮,订单相关信息： {}",upo.getCount()-upo.getVaildCount(),upo);
	    		try {
	    			otService.excuteOrderTree(upo);
	    		} catch (Exception e) {
	    			e.printStackTrace();
	    		}
	    }else {//	当前套餐已经跑完了，现在开始跑下一个主订单的套餐
              UserPackageOrder newPackage = upoService.getUserPackageByUserNo(userNo);
              if(null != newPackage) {
                log.info("特约大使 再次寻点 第 1 轮,订单相关信息： {}",upo);
                 try {
	    			otService.excuteOrderTree(newPackage);
	    		 } catch (Exception e) {
	    			e.printStackTrace();
	    		 }
              }
	    }
	}




	/**
	 * 6缺6用户，直接赠送懒用户,按照时间顺序来赠送
	 * @return
	 */
	public TreeNo presentedLazyUser() {
		
		return mapper.getLazyUser();
	}
	
	
	
	
	/**
	 * 6缺6的处理方法
	 * @param tn      等待寻位的节点
	 * @param parent  父节点
	 * @throws Exception 
	 */
	public void fullNode(TreeNo parent,UserPackageOrder upo) throws Exception {
		 TreeNo tn = this.repare(upo);
		 log.info("fullNode parent {} ,upo {}",parent,upo);
		 if(StringUtils.isEmpty(parent.getLeftLeafNo())) {
			 parent.setLeftLeafNo(tn.getNodeNo());
		 }else {
			 parent.setRightLeafNo(tn.getNodeNo());
		 }
		 noSonLeaf(parent,tn,upo);
	}
	
	
	
	
	
	
	
	/**
	 * 前提：tn的父节点已经指定，且parent的左右子节点上已经挂好了tn
	 * 无子节点的情况，即缺6，缺5的逻辑:
	 *                      需要上寻 父节点的节点
	 * @param parent
	 * @param tn
	 */
	public void noSonLeaf(TreeNo parent,TreeNo tn,UserPackageOrder upo) {
        tn.setParentLeafNo(parent.getNodeNo());
        //**********************
        this.updateById(tn);//**
        reduceOne(parent,upo);//购买父节点
        String parentNo = parent.getParentLeafNo();
        if(!VIP.ADMIN_NODE_NO.equals(parentNo)) {
        	TreeNo gradeParent = mapper.getNodeByNodeNo(parentNo);
        	reduceOne(gradeParent,upo);
		}else {
			log.info("==================================平台获得用户{}=====================================",upo);
		}
	}
	
	
	

	/**
	 * 	满子节点，左子节点未满，此时6缺4,或6缺3
	 *	6缺4，存在两种：1.子节点下面挂了一个   2.父节点左右子树挂满
	 *  6缺3，存在两种：1.子节点推了两人，父节点一人   2.父节点推满两人
	 * 	
	 * 	父节点的左右子树已满，只能到第二代子节点去找位置（此处逻辑有错误，当为3个有效节点时，可能存在子节点的左右节点均以挂满）、
	 *	简单来说，就是子节点推荐了两人，而父节点只推荐了一人
	 * @param parent 第二代父节点
	 * @param tn     待选择节点
	 * @throws Exception 
	 */
	public void fullSonLeafAndNoLeft(TreeNo parent,UserPackageOrder upo) throws Exception {
		
		log.info("fullSonLeafAndNoLeft parent {} ,upo {}",parent,upo);
		TreeNo tn = this.repare(upo);
		//	右子叶节点没有叶子，则优先占位子到父节点下面
		if(StringUtils.isEmpty(parent.getRightLeafNo())) {
			tn.setParentLeafNo(parent.getNodeNo());
			this.updateById(tn);
			parent.setRightLeafNo(tn.getNodeNo());
			this.updateById(parent);
			reduceOne(parent,upo);
			if(!VIP.ADMIN_NODE_NO.equals(parent.getParentLeafNo())) {
				TreeNo treeNo = mapper.getNodeByNodeNo(parent.getParentLeafNo());
				reduceOne(treeNo,upo);
			}
		}else {
			String leftLeafNo = parent.getLeftLeafNo();
			tn.setParentLeafNo(leftLeafNo);
			this.updateById(tn);
			TreeNo treeNo = mapper.getNodeByNodeNo(leftLeafNo);
			if(StringUtils.isEmpty(treeNo.getLeftLeafNo())) {
				treeNo.setLeftLeafNo(tn.getNodeNo());
			}else {
				treeNo.setRightLeafNo(tn.getNodeNo());
			}
			this.updateById(treeNo);
			reduceOne(treeNo,upo);
			reduceOne(parent,upo);
		}
		
		
	}
	
	/**
	 * 
	 * 父节点的左右子树已满，只能到第二代子节点去找位置
	 * @param parent 第二代父节点
	 * @param tn     待选择节点
	 * @throws Exception 
	 */
	public void fullSonLeafAndFullLeft(TreeNo parent,UserPackageOrder upo) throws Exception {
		log.info("fullNode parent {} ,upo {}",parent,upo);
		TreeNo tn = this.repare(upo);
		String leftLeafNo = parent.getLeftLeafNo();//左节点
		String rightLeafNo = parent.getRightLeafNo();//右节点
		TreeNo leftLeafNode = mapper.getNodeByNodeNo(leftLeafNo);
		if(leftLeafNode.getRightLeafNo()!=null&&leftLeafNode.getLeftLeafNo()!=null) {//左子树全部满了
			tn.setParentLeafNo(rightLeafNo);
		}else {
			tn.setParentLeafNo(leftLeafNo);
		}
		this.updateById(tn);
		TreeNo treeNo = mapper.getNodeByNodeNo(tn.getParentLeafNo());
		if(StringUtils.isEmpty(treeNo.getLeftLeafNo())) {
			treeNo.setLeftLeafNo(tn.getNodeNo());
		}else {
			treeNo.setRightLeafNo(tn.getNodeNo());
		}
		this.updateById(treeNo);
		reduceOne(treeNo,upo);
		reduceOne(parent,upo);
	}
	
	
	/**
	 * 获取
	 * @param mainOrder
	 * @return
	 */
	public TreeNo getNodeByMainOrder(String mainOrder) {
		return mapper.getNodeByMainOrderNo(mainOrder);
	}
	
	
	
	/**
	 * 赠送平台
	 * @throws Exception 
	 */
	public void givingRoot(UserPackageOrder upo) throws Exception {
		 log.info("----------------------------------------------------------------------------------------------------");
         log.info("--------平台获得用户贡献，标记："+upo);
         log.info("----------------------------------------------------------------------------------------------------");
	     TreeNo tn = this.repare(upo);
	     tn.setParentLeafNo(VIP.ADMIN_NODE_NO);	
	     this.updateById(tn);
           
	}

	
	public TreeNo getNodeByUserNo(String userNo) {
		//select * from tree_no where enables=1 and node_no = (select node_no from order_tree where user_no=#{userNo} and enables=1 )
		return mapper.getValidTreeNoByUserNo(userNo);
	}

	public TreeNo getTreeNoByVildCount(int i) {
		return mapper.getTreeNoByVildCount(i);
	}
}
