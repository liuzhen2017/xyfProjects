package com.xinyunfu.rule.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.xinyunfu.constants.VIP;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.TreeNoMapper;
import com.xinyunfu.model.TreeNo;
import com.xinyunfu.model.UserPackageOrder;
import com.xinyunfu.rule.EngineRule;
import com.xinyunfu.sao.VolumeMarketSao;
import com.xinyunfu.service.impl.OrderTreeServiceImpl;
import com.xinyunfu.service.impl.TreeNoServiceImpl;
import com.xinyunfu.service.impl.UserPackageOrderServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jace
 *
 *	基于推荐关系:  平台用户优先赠送6缺1，其次赠送6缺6
 */
@Component
@Slf4j
public class LessOneRuleImpl implements EngineRule{

	
	
	@Autowired
	OrderTreeServiceImpl otSerice;

	@Autowired
	private UserPackageOrderServiceImpl upoService;
	
	@Autowired
	private VolumeMarketSao vmSao;
	
	@Autowired
	private TreeNoServiceImpl tnService;
	
	
	@Override
	public ResponseInfo<String> handler(UserPackageOrder upo) throws Exception {
		JSONArray pushlist = otSerice.getPushList(upo.getUserNo());
        if(null==pushlist) {
        	log.info("======================获取用户推荐关系失败========================");
        	log.info("=========UserPackageOrder:{} =======",upo);
        	otSerice.freeGiving(upo);
        	return null;
        }
        log.info("------------------查询用户:{},推荐链：{}",upo.getUserNo(),pushlist);
        String userNo = "";
        for(int i = 0;i<pushlist.size();i++) {
        	userNo = pushlist.getString(i);
			if(VIP.ADMIN_NODE_NO.equals(userNo)) { //推荐人是系统节点
				  lessOne(upo);
				return ResponseInfo.success(null);
			}else {
				//  推荐人不是系统节点，那么进行用户有效性查找 
				if(upoService.checkUserVaild(userNo)) {  // 有效
					otSerice.commonOrderTree(userNo, upo); 
				    return ResponseInfo.success(null);
				}else { //  无效，推送掉单通知
					try {
						vmSao.loseOrderNotify(userNo, upo.getUserNo());
					}catch (Exception e) {
						log.info(" ==>  通知飞弹异常   <==   ==e:{}",e);
					}
				}
			}
        }
        otSerice.freeGiving(upo);
		return null;
	}

	
	
	/**
	 * 6缺1
	 * @param upo
	 * @throws Exception 
	 */
	public void lessOne(UserPackageOrder upo) throws Exception {
         TreeNo parent = tnService.getTreeNoByVildCount(1);
         if(null != parent) {
        	 tnService.insertNodeNoProcessor(parent, upo); 
         }else {
        	 otSerice.freeGiving(upo);
         }
	}
}
