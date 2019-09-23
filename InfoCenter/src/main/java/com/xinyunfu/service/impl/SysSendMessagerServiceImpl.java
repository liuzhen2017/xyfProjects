package com.xinyunfu.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.Constant.SysConstant;
import com.xinyunfu.agent.RestTemplateAgent;
import com.xinyunfu.config.SendMsgConfig;
import com.xinyunfu.entity.SysSendMessager;
import com.xinyunfu.jace.utils.ResponseInfo;
import com.xinyunfu.mapper.SysSendMessagerMapper;
import com.xinyunfu.service.SysSendMessagerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author liuzhen accFlow Service
 */
@Slf4j
@Service
public class SysSendMessagerServiceImpl extends ServiceImpl<SysSendMessagerMapper, SysSendMessager>
		implements SysSendMessagerService {

	@Autowired
	SysSendMessagerMapper accFlowMapper;

	@Autowired
	RestTemplateAgent restTemplateAgent;
	@Autowired
	SendMsgConfig sendmsgConfig;

	/**
	 * 分页查询
	 * 
	 * @param accFlow 请求参数
	 * @return accFlow分页列表
	 */
	public ResponseInfo<IPage<SysSendMessager>> list(SysSendMessager accFlow, Integer pageSize, Integer page) {
		LambdaQueryWrapper<SysSendMessager> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper.orderByDesc(SysSendMessager::getId);
		Page<SysSendMessager> pages = new Page<SysSendMessager>(page == null ? 1 : page,
				pageSize == null ? 15 : pageSize);
		return ResponseInfo.success(super.baseMapper.selectPage(pages, queryWrapper));
	}

	/**
	 * 根据条件查询详情
	 * 
	 * @param accFlow 请求参数
	 * @return accFlow详情
	 */
	public ResponseInfo<SysSendMessager> query(SysSendMessager accFlow) {
		LambdaQueryWrapper<SysSendMessager> queryWrapper = new LambdaQueryWrapper<>();

		queryWrapper.orderByDesc(SysSendMessager::getId);
		// 取第一页第一条,相当于 limit
		Page<SysSendMessager> pages = new Page<SysSendMessager>(1, 1);
		IPage<SysSendMessager> iPage = super.page(pages, queryWrapper);
		if (!iPage.getRecords().isEmpty()) {
			return ResponseInfo.success(iPage.getRecords().get(0));
		}
		return ResponseInfo.success(null);
	}

	/**
	 * 根据主键id查询详情
	 * 
	 * @param id accFlowid
	 * @return accFlow详情
	 */
	public ResponseInfo<SysSendMessager> queryById(Long id) {
		return ResponseInfo.success(super.getById(id));
	}

	/**
	 * 添加accFlow
	 * 
	 * @param accFlow 实体
	 * @return ResponseEntity
	 */
	public ResponseInfo<String> add(SysSendMessager accFlow) {
		if (super.save(accFlow)) {
			return ResponseInfo.success("新增成功!");
		} else {
			return ResponseInfo.error("新增失败!");
		}
	}

	/**
	 * 修改accFlow
	 * 
	 * @param accFlow 实体
	 * @return ResponseEntity
	 */
	public ResponseInfo<String> update(SysSendMessager accFlow) {

		if (super.updateById(accFlow)) {
			return ResponseInfo.success("修改成功!");
		} else {
			return ResponseInfo.error("修改失败!");
		}
	}

	/**
	 * 删除accFlow
	 * 
	 * @param id 主键id
	 * @return ResponseEntity
	 */
	public ResponseInfo<String> delete(Long id) {
		if (super.removeById(id)) {
			return ResponseInfo.success("删除成功!");
		} else {
			return ResponseInfo.error("删除失败!");
		}
	}

	@Override
	public ResponseInfo<String> sendMsg(String json) {
		log.info("========接收到发送短信=======parmat ={}", json);
		// 转成对象
		SysSendMessager sendMsg = JSONObject.parseObject(json, SysSendMessager.class);
		//设置默认值
		sendMsg.setClientSource("3");
		sendMsg.setClientSystem("UNIT-TEST");
		sendMsg.setClientVersion("0.0.0.0");
		sendMsg.setDeviceId("api_test_v2");
		// 拼接发送
		JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(sendMsg));
		data.put("Mobileno", sendMsg.getRecvObject());
		data.put("InterfaceVersion",1);
		
		data.put("TimeStamp",diffSeconds());
		data.put("MerchantNo", sendmsgConfig.getMerchantNo());
		data.put("device_id", "api_test_v2");
		// 封装参数
		JSONObject parmat = new JSONObject();
		parmat.put("MerchantNo", sendmsgConfig.getMerchantNo());
		parmat.put("data", data);
		JSONObject result = null;
		// 发送验证码
		if (SysConstant.sendType_code_1.equals(sendMsg.getSendType())) {
			result = restTemplateAgent.postForObject(sendmsgConfig.getBaseUrl() + sendmsgConfig.getSendCodeUrl(),
					parmat, true);
		} else if (SysConstant.sendType_msg_2.equals(sendMsg.getSendType())) {
			data.put("Content", sendMsg.getSendContent());
			result = restTemplateAgent.postForObject(sendmsgConfig.getBaseUrl() + sendmsgConfig.getSmsSendUrl(), parmat,
					true);
		} else if (SysConstant.sendType_app_bind_3.equals(sendMsg.getSendType())) {
			data.put("clientid",JSONObject.parseObject(json).get("deviceId"));
			data.put("usercode",sendMsg.getUserNo());
			result = restTemplateAgent.postForObject(sendmsgConfig.getBaseUrlApp() + sendmsgConfig.getAppBindUrl(), parmat,
					true);
		} else if (SysConstant.sendType_app_push_4.equals(sendMsg.getSendType())) {
			JSONObject jb =new JSONObject();
			data.put("clientid",JSONObject.parseObject(json).get("deviceId"));
			data.put("usercode",sendMsg.getUserNo());
			data.put("Platform","星云福");
			data.put("Parameter",jb.getString("parameter"));
			data.put("NotificationType",jb.getString("notificationtype"));
			result = restTemplateAgent.postForObject(sendmsgConfig.getBaseUrlApp() + sendmsgConfig.getAppPushUrl(), parmat,
					true);
		}
		String errMsg = null;
		Integer status = 1;
		if (result.getBooleanValue("Success")) {
			status = 0;
		}
		errMsg = result.getString("Message");
		sendMsg.setErrMsg(errMsg);
		sendMsg.setSendStatus(status);
		this.save(sendMsg);
		log.info("========短信发送完毕=======结果 ={},错误信息={}", result.getBooleanValue("Success"), errMsg);
		return status == 0?ResponseInfo.success(errMsg):ResponseInfo.error(errMsg);
	}

	/**
     * 获取指定时间到格林威治时间的秒数
     * UTC：格林威治时间1970年01月01日00时00分00秒（UTC+8北京时间1970年01月01日08时00分00秒）
     * @return
     */
    public static long diffSeconds(){
        Calendar calendar = Calendar.getInstance();

        calendar.clear();
        Date datetime = Calendar.getInstance().getTime();
        calendar.setTime(datetime);

        TimeZone timeZone = TimeZone.getTimeZone("GMT+08:00");
        calendar.setTimeZone(timeZone);
        return calendar.getTimeInMillis()/1000;
    }
	@Override
	public ResponseInfo<Boolean> checkMsg(String phone, String code, String TemplateNo) {
		log.info("=======校验验证码=======phone ={},code={},TemplateNo={}", phone, code, TemplateNo);
		// 拼接发送
		JSONObject data = new JSONObject();
		data.put("Mobileno", phone);
		data.put("TemplateNo", TemplateNo);
		data.put("Code", code);
		data.put("ClientSource",3);
		data.put("ClientSystem","UNIT-TEST");
		data.put("ClientVersion","0.0.0.0");
	    data.put("InterfaceVersion",1);
		data.put("TimeStamp",diffSeconds());
		data.put("MerchantNo", sendmsgConfig.getMerchantNo());
		data.put("device_id", "api_test_v2");
		
		// 封装参数
		JSONObject parmat = new JSONObject();
		parmat.put("MerchantNo", sendmsgConfig.getMerchantNo());
		parmat.put("data", data);
		JSONObject result = null;
		// 发送验证码
		result = restTemplateAgent.postForObject(sendmsgConfig.getBaseUrl() + sendmsgConfig.getVaildCodeUrl(), parmat,
				true);
		if (result.getBooleanValue("Success")) {
		}
		String errMsg = result.getString("Message");
		log.info("========校验验证码完毕=======结果 ={}", result.getBooleanValue("Success"), errMsg);
		return ResponseInfo.success(result.getBooleanValue("Success"));
	}
}