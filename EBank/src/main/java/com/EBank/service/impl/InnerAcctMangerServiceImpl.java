package com.EBank.service.impl;

import com.EBank.config.PayManger;
import com.EBank.constant.SysConstant;
import com.EBank.entity.AccFlow;
import com.EBank.entity.InnerAcctManger;
import com.EBank.entity.NotifyInfo;
import com.EBank.entity.PayChannel;
import com.EBank.feign.CustomerManageFeign;
import com.EBank.feign.OrderManage;
import com.EBank.feign.OrderTreeFeign;
import com.EBank.mapper.InnerAcctMangerMapper;
import com.EBank.mapper.PayChannelMapper;
import com.EBank.service.AccFlowService;
import com.EBank.service.InnerAcctMangerService;
import com.EBank.service.NoticeInfoService;
import com.EBank.util.PayMangerUtil;
import com.EBank.util.RSAUtil;
import com.EBank.util.RedisCommonUtil;
import com.EBank.util.RedisLock;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyunfu.jace.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.EBank.constant.SysConstant.*;

/**
 * @author liuzhen
 * innerAcctManger Service
 */
@Slf4j
@Service
public class InnerAcctMangerServiceImpl extends ServiceImpl<InnerAcctMangerMapper, InnerAcctManger> implements InnerAcctMangerService {


    @Autowired
    AccFlowService accFlowService;

    @Autowired
    RedisCommonUtil redisCommonUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    PayMangerUtil payMangerUtil;
    @Autowired
    PayManger payManger;
    @Autowired
    CustomerManageFeign customerManageFeign;
    @Autowired
    OrderTreeFeign orderTreeFeign;
    @Autowired
    OrderManage orderManage;
    @Autowired
    PayChannelMapper payChannelMapper;
    @Autowired
    NoticeInfoService noticeInfoService;
    @Autowired
    InnerAcctMangerService innerAcctMangerService;
    /**
     * 出账线程池
     */
    ExecutorService threadPoolBycallbackPayment = Executors.newWorkStealingPool(20);

    /**
     * 分页查询
     *
     * @param innerAcctManger 请求参数
     * @return innerAcctManger分页列表
     */
    public ResponseInfo<IPage<InnerAcctManger>> list(InnerAcctManger innerAcctManger, Integer page, Integer pageSize) {
        LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();


        if (!StringUtils.isEmpty(innerAcctManger.getAccNo())) {
            queryWrapper.eq(InnerAcctManger::getAccNo, innerAcctManger.getAccNo());
        }

        if (!StringUtils.isEmpty(innerAcctManger.getAccName())) {
            queryWrapper.eq(InnerAcctManger::getAccName, innerAcctManger.getAccName());
        }

        if (!StringUtils.isEmpty(innerAcctManger.getBankName())) {
            queryWrapper.eq(InnerAcctManger::getBankName, innerAcctManger.getBankName());
        }

        if (!StringUtils.isEmpty(innerAcctManger.getBankNo())) {
            queryWrapper.eq(InnerAcctManger::getBankNo, innerAcctManger.getBankNo());
        }

        if (innerAcctManger.getBalance() != null) {
            queryWrapper.eq(InnerAcctManger::getBalance, innerAcctManger.getBalance());
        }

        if (innerAcctManger.getFreezingBalance() != null) {
            queryWrapper.eq(InnerAcctManger::getFreezingBalance, innerAcctManger.getFreezingBalance());
        }

        if (innerAcctManger.getCanUserBalance() != null) {
            queryWrapper.eq(InnerAcctManger::getCanUserBalance, innerAcctManger.getCanUserBalance());
        }

        if (!StringUtils.isEmpty(innerAcctManger.getUserId())) {
            queryWrapper.eq(InnerAcctManger::getUserId, innerAcctManger.getUserId());
        }

        if (!StringUtils.isEmpty(innerAcctManger.getAccType())) {
            queryWrapper.eq(InnerAcctManger::getAccType, innerAcctManger.getAccType());
        }

        if (!StringUtils.isEmpty(innerAcctManger.getBusiStatus())) {
            queryWrapper.eq(InnerAcctManger::getBusiStatus, innerAcctManger.getBusiStatus());
        }

        if (innerAcctManger.getCreatedDate() != null) {
            queryWrapper.eq(InnerAcctManger::getCreatedDate, innerAcctManger.getCreatedDate());
        }

        if (innerAcctManger.getCreatedBy() != null) {
            queryWrapper.eq(InnerAcctManger::getCreatedBy, innerAcctManger.getCreatedBy());
        }

        if (innerAcctManger.getUpdatedDate() != null) {
            queryWrapper.eq(InnerAcctManger::getUpdatedDate, innerAcctManger.getUpdatedDate());
        }

        if (innerAcctManger.getUpdatedBy() != null) {
            queryWrapper.eq(InnerAcctManger::getUpdatedBy, innerAcctManger.getUpdatedBy());
        }

        if (innerAcctManger.getEnable() != null) {
            queryWrapper.eq(InnerAcctManger::getEnable, innerAcctManger.getEnable());
        }
        queryWrapper.orderByDesc(InnerAcctManger::getId);
        Page<InnerAcctManger> pages = new Page<InnerAcctManger>(page == null ? 1 : page, pageSize == null ? 15 : pageSize);
        return ResponseInfo.success(super.baseMapper.selectPage(pages, queryWrapper));
    }

    /**
     * 根据条件查询详情
     *
     * @param innerAcctManger 请求参数
     * @return innerAcctManger详情
     */
    public ResponseInfo<InnerAcctManger> query(InnerAcctManger innerAcctManger) {
        LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(innerAcctManger.getAccNo())) {
            queryWrapper.eq(InnerAcctManger::getAccNo, innerAcctManger.getAccNo());
        }
        if (!StringUtils.isEmpty(innerAcctManger.getAccName())) {
            queryWrapper.eq(InnerAcctManger::getAccName, innerAcctManger.getAccName());
        }
        if (!StringUtils.isEmpty(innerAcctManger.getBankName())) {
            queryWrapper.eq(InnerAcctManger::getBankName, innerAcctManger.getBankName());
        }
        if (!StringUtils.isEmpty(innerAcctManger.getBankNo())) {
            queryWrapper.eq(InnerAcctManger::getBankNo, innerAcctManger.getBankNo());
        }
        if (innerAcctManger.getBalance() != null) {
            queryWrapper.eq(InnerAcctManger::getBalance, innerAcctManger.getBalance());
        }
        if (innerAcctManger.getFreezingBalance() != null) {
            queryWrapper.eq(InnerAcctManger::getFreezingBalance, innerAcctManger.getFreezingBalance());
        }
        if (innerAcctManger.getCanUserBalance() != null) {
            queryWrapper.eq(InnerAcctManger::getCanUserBalance, innerAcctManger.getCanUserBalance());
        }
        if (!StringUtils.isEmpty(innerAcctManger.getUserId())) {
            queryWrapper.eq(InnerAcctManger::getUserId, innerAcctManger.getUserId());
        }
        if (!StringUtils.isEmpty(innerAcctManger.getAccType())) {
            queryWrapper.eq(InnerAcctManger::getAccType, innerAcctManger.getAccType());
        }
        if (!StringUtils.isEmpty(innerAcctManger.getBusiStatus())) {
            queryWrapper.eq(InnerAcctManger::getBusiStatus, innerAcctManger.getBusiStatus());
        }
        if (innerAcctManger.getCreatedDate() != null) {
            queryWrapper.eq(InnerAcctManger::getCreatedDate, innerAcctManger.getCreatedDate());
        }
        if (innerAcctManger.getCreatedBy() != null) {
            queryWrapper.eq(InnerAcctManger::getCreatedBy, innerAcctManger.getCreatedBy());
        }
        if (innerAcctManger.getUpdatedDate() != null) {
            queryWrapper.eq(InnerAcctManger::getUpdatedDate, innerAcctManger.getUpdatedDate());
        }
        if (innerAcctManger.getUpdatedBy() != null) {
            queryWrapper.eq(InnerAcctManger::getUpdatedBy, innerAcctManger.getUpdatedBy());
        }
        if (innerAcctManger.getEnable() != null) {
            queryWrapper.eq(InnerAcctManger::getEnable, innerAcctManger.getEnable());
        }
        return ResponseInfo.success(super.getOne(queryWrapper));
    }

    /**
     * 根据主键id查询详情
     *
     * @param id innerAcctMangerid
     * @return innerAcctManger详情
     */
    public ResponseInfo<InnerAcctManger> queryById(Long id) {
        return ResponseInfo.success(super.getById(id));
    }

    /**
     * 添加innerAcctManger
     *
     * @param innerAcctManger 实体
     * @return ResponseEntity
     */
    public ResponseInfo<String> add(InnerAcctManger innerAcctManger) {
        if (super.save(innerAcctManger)) {
            return ResponseInfo.success("添加成功!");
        }
        return ResponseInfo.error("添加失败!");
    }

    /**
     * 修改innerAcctManger
     *
     * @param innerAcctManger 实体
     * @return ResponseEntity
     */
    public ResponseInfo<String> update(InnerAcctManger innerAcctManger) {
        if (super.updateById(innerAcctManger)) {
            return ResponseInfo.success("修改成功!");
        }
        return ResponseInfo.error("修改失败!");
    }

    /**
     * 删除innerAcctManger
     *
     * @param id 主键id
     * @return ResponseEntity
     */
    public ResponseInfo<String> delete(Long id) {
        if (super.removeById(id)) {
            return ResponseInfo.success("删除成功!");
        }
        return ResponseInfo.error("删除失败!");
    }

    @Override
    public ResponseInfo<String> addInner(InnerAcctManger innerAcctManger) {
        //分布式锁
        log.info("...........begin get Redislock...........");
        //是否可以购买
        //TODO 查询订单树服务 ->是否还有未消费的卡券
        log.info("...........get Redislock is Success,Begin query inner Acc...........");
        //根据用户查询是否已经存在
        LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InnerAcctManger::getUserId, innerAcctManger.getUserId());
        //如果等于null
        if (this.getOne(queryWrapper) == null) {
            log.info("...........query innerAcc By userNo,userNo={},...........", innerAcctManger.getUserId());

            //查询数据库,生成accMo
            log.info("...........get AccNoByRedis,result =null...........", innerAcctManger.getUserId());
            Object innerAccNo = redisCommonUtil.getCache("inner_acc_no" + innerAcctManger.getUserId());
            if (innerAccNo == null) {
                //根据用户查询是否已经存在
                queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(InnerAcctManger::getAccType, innerAcctManger.getAccType());
                queryWrapper.orderByDesc(InnerAcctManger::getId);
                InnerAcctManger acctManger = this.getOne(queryWrapper);
                //如果为空,则取默认值
                if (acctManger == null) {
                    innerAccNo = 0;
                } else {
                    innerAccNo = acctManger.getAccNo();
                }

            }
            String accNo = Long.parseLong(innerAccNo + "") + 1 + "";
            if (SysConstant.inner_no_max.length() <= accNo.length()) {
                accNo = "00" + SysConstant.inner_no_max.substring(0, SysConstant.inner_no_max.length() - accNo.length()) + accNo;
            } else {
                accNo = SysConstant.inner_no_max.substring(0, SysConstant.inner_no_max.length() - accNo.length()) + accNo;
            }
            innerAcctManger.setAccNo(accNo);
            if (this.save(innerAcctManger)) {
                return ResponseInfo.success("添加成功!");
            } else {
                return ResponseInfo.errorReturn("添加失败!");
            }
        }
        log.info("request Time out ....");
        return ResponseInfo.error("请求超时,请重试");
    }

    /**
     * 入账回调
     * 1.状态是成功->
     * 修改支付状态
     * 如果是套餐->,通知订单树 微服务,有用户购买套餐
     * 如果是商品-> 修改订单状态
     * 2.状态是失败->
     * 修改支付为失败.定时任务去轮询->发送短信管理员去解决
     *
     * @param json
     * @return
     */
    @Override
    public String callbackAccountEntry(String json) {

        //使用异步处理
        Future<String> future = threadPoolBycallbackPayment.submit(() -> {
            log.info("============收到支付中心入账回调==============={}=", json);
            JSONObject jsonObject = JSONObject.parseObject(json);
            String data = jsonObject.getString("data");
            String sign = jsonObject.getString("sign");
            boolean isPass = false;
            String restCode = jsonObject.getString("resCode");
            String resMsg = jsonObject.getString("resMsg");
            //解析data
            try {
                data = RSAUtil.decrypt(data, payManger.getPayPriKey(), "utf-8");
            } catch (Exception e) {
                log.error("回调验签失败!msg={},e={}", e.getMessage(), e);
                return "false";
            }

            //校验验签
            //订单号
            jsonObject = JSONObject.parseObject(data);
            String outTradeNo = jsonObject.getString("outTradeNo");
            //成交时间
            String dealtime = jsonObject.getString("dealtime");
            //订单状态
            Integer status = jsonObject.getInteger("status");
            //手续费，单位分
            String fee = jsonObject.getString("fee");
            String payType = jsonObject.getString("payType");
            //订单版本号
            String version = jsonObject.getString("version");
            BigDecimal amount = jsonObject.getBigDecimal("amount");
            String privateValue = jsonObject.getString("privateValue");
            //订单版本号
            //查询商户私有信息
            AccFlow accFlow = new AccFlow();
            accFlow.setOrderNumber(outTradeNo);
            ResponseInfo<AccFlow> accFlowResponseInfo = accFlowService.query(accFlow);
            log.info("===============根据订单号查询账户流水=======accFlow={}=========", accFlow);
            if (accFlowResponseInfo.getData() == null) {
                log.error("===========根据订单查询，数据库没有该数据===orderNo=", outTradeNo);
                return "false";
            }
//            //如果支护成功
//            if (payStatus_success_1 == status) {
//                //如果是购买套餐/
//                String orderType = privateValue.split("_")[0];
//                Integer count = Integer.parseInt(privateValue.split("_")[1].equals("null") ? "0" : privateValue.split("_")[1]);
//                if (orderType_setMeal_02.equals(orderType)) {
//                    //通知订单树
//                    log.info("==========准备通知订单树=======");
//                    String userResonse = customerManageFeign.getUserInfo(accFlowResponseInfo.getData().getUserNo());
//                    ResponseInfo<JSONObject> responseInfo1 = JSONObject.parseObject(userResonse, ResponseInfo.class);
//                    JSONObject userObject = responseInfo1.getData();
//
//                    Integer userType = userObject.getInteger("level");
//                    ResponseInfo<String> responseInfo = orderTreeFeign.joinOrderTree(outTradeNo, accFlowResponseInfo.getData().getUserNo(), userType, count);
//                    log.info("=============通知订单树结束==============返回结果={}===", responseInfo);
//                    //保存发送记录
//                    saveNotifyInfo("orderTreeFeign", responseInfo.getMessage(), responseInfo.getCode().equals(sys_success) ? "0" : "1", orderTreeFeign.getClass().getSimpleName(), "joinOrderTree", outTradeNo, accFlowResponseInfo.getData().getUserNo(), 0, 0);
//                }
//            }
            //通知订单中心
            log.info("==========准备通知订单中心=======");
            ResponseInfo<Boolean> responseInfo = orderManage.payCallback(outTradeNo, accFlowResponseInfo.getData().getPayNumber(), amount, status == 1,Integer.parseInt(privateValue.split("_")[2]));
            saveNotifyInfo("orderManage", responseInfo.getMessage(), responseInfo.getData() ? "0" : "1", orderManage.getClass().getSimpleName(), "payCallback", outTradeNo, accFlow.getPayNumber(), amount, status == 1);
            log.info("==========通知订单中心结束===返回结果={}====", responseInfo);

            accFlowService.updateFlowByOrderNo(outTradeNo, status);
            //修改入账信息
            //平台入账
            LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InnerAcctManger::getAccType, SysConstant.userType_4);
            InnerAcctManger platformAcc = innerAcctMangerService.getOne(queryWrapper);
            platformAcc.setBalance(platformAcc.getBalance().add(amount));
            platformAcc.setCanUserBalance(platformAcc.getCanUserBalance().add(amount));
            innerAcctMangerService.saveOrUpdate(platformAcc);
            return "true";
        });
        try {
            String str = future.get();
            return str;
        } catch (Exception e) {
            log.info("执行错误,msg ={},dei={}", e.getMessage(), e);
            return "false";
        }

    }

    /**
     * 保存发送通知
     *
     * @param notifyObject 通知对象
     * @param errMsg       错误消息
     * @param status       状态
     * @param notifyParmat 通知参数,按顺序注入方法
     * @param notifyClass  通知类
     * @param notifyMethod 通知方法
     */
    public void saveNotifyInfo(String notifyObject, String errMsg, String status, String notifyClass, String notifyMethod, Object... notifyParmat) {
        //创建发送记录
        NotifyInfo notifyInfo = new NotifyInfo();
        notifyInfo.setErrMsg(errMsg);
        notifyInfo.setNotifyObject(notifyObject);
        notifyInfo.setNotifyParmat(JSONObject.toJSONString(notifyParmat));
        notifyInfo.setNotifyStatus(status);
        notifyInfo.setNotifyMethod(notifyMethod);
        notifyInfo.setNotifyClass(notifyClass);
        noticeInfoService.save(notifyInfo);
    }

    /**
     * 代付回调
     * 1.如果是代付
     * 修改账户流水为成功
     *
     * @param json
     * @return
     */
    @Override
    public String callbackPayment(String json) {
        //使用异步处理
        Future<String> future = threadPoolBycallbackPayment.submit(() -> {
            log.info("============收到支付中心代付回调============={}===", json);
            JSONObject jsonObject = JSONObject.parseObject(json);
            String content = jsonObject.getString("data");
            String sign = jsonObject.getString("sign");

            //解析data
            try {
                content = RSAUtil.decrypt(content, payManger.getPayPriKey(), "utf-8");
            } catch (Exception e) {
                log.error("回调验签失败!msg={},e={}", e.getMessage(), e);
                return "false";
            }
            JSONObject data = JSONObject.parseObject(content);
            //订单号
            String outTradeNo = data.getString("outTradeNo");
            //成交时间
            String dealtime = data.getString("dealtime");
            //订单状态
            Integer status = data.getInteger("status");
            //手续费，单位分
            String fee = data.getString("fee");
            String payType = data.getString("payType");
            //订单版本号
            String version = data.getString("version");
            //查询商户私有信息
            String privateValue = data.getString("privateValue");
            //渠道
            String channelType = data.getString("channelType");
            //订单凭证号
            String voucher = data.getString("voucher");
            BigDecimal amount = data.getBigDecimal("amount");

            AccFlow accFlow = new AccFlow();
            accFlow.setOrderNumber(outTradeNo);
            ResponseInfo<AccFlow> accFlowResponseInfo = accFlowService.query(accFlow);
            log.info("===============根据订单号查询账户流水=======accFlow={}=========", accFlow);
            if (accFlowResponseInfo.getData() == null) {
                log.error("===========根据订单查询，数据库没有该数据===orderNo=", outTradeNo);
                return "false";
            }
            //如果支护成功
            if (payStatus_success_1 == status) {
                //如果是购买套餐/
                //TODO 发送短信提醒
                //发送短信提醒
            }
            //通知订单中心
            accFlowService.updateFlowByOrderNo(outTradeNo, status);
            //平台入账
            LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InnerAcctManger::getAccType, SysConstant.userType_4);
            InnerAcctManger platformAcc = innerAcctMangerService.getOne(queryWrapper);
            platformAcc.setBalance(platformAcc.getBalance().subtract(amount));
            platformAcc.setCanUserBalance(platformAcc.getCanUserBalance().subtract(amount));
            innerAcctMangerService.saveOrUpdate(platformAcc);
            return "true";
        });
        try {
            String str = future.get();
            return str;
        } catch (Exception e) {
            log.info("执行错误,msg ={},dei={}", e.getMessage(), e);
            return "false";
        }

    }

    /**
     * 卡券交易付款
     * 1.A推荐用户B
     * 2.B用户购买套餐,订单编号001
     * 3.将001订单编号传给支付平台,进行支付.
     *
     * @param recvUserNo   接收用户
     * @param sourceUserNo 收益来源人
     * @param amount       金额
     * @param chanel       渠道->微服务名
     * @param orderNo
     * @return
     */
    @Override
    public ResponseInfo<String> paymentByCoupon(String recvUserNo, BigDecimal amount, String chanel, String sourceUserNo, String orderNo) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock();
        //使用异步处理
        //1.查询收益来源人的订单,看看还有没有余额
        RedisLock redisLock = new RedisLock(redisTemplate, "paymentByCoupon" + recvUserNo, 1000 * 60);
        try {
            redisLock.lock();
            AccFlow accFlow = new AccFlow();
            accFlow.setUserNo(sourceUserNo);
            //出账 ->购买
            accFlow.setTranType(acctranType_1);
            log.info("=========开始查询用户最近购买的订单==========");
            ResponseInfo<AccFlow> responseInfo = accFlowService.query(accFlow);
            AccFlow accFlow1 = responseInfo.getData();
            log.info("=========查到用户订单==============", accFlow1);
            AccFlow accFlow2 = new AccFlow();
            accFlow.setUserNo(accFlow1.getReceAccNo());
            accFlow.setTranType(acctranType_1);
            accFlow.setOrderNumber(accFlow1.getOrderNumber());
            //支出总金额
            int count = accFlowService.queryPayCountByOrderNo(accFlow1.getUserNo(), accFlow1.getOrderNumber());
            log.info("=========查寻用户订单支出了几次========count={}======", count);
            //查询用户信息
            String userResonse = customerManageFeign.getUserInfo(recvUserNo);
            ResponseInfo<JSONObject> res = JSONObject.parseObject(userResonse, ResponseInfo.class);
            if (!res.isSuccess()) {
                return ResponseInfo.errorReturn("查询客户信息异常!");
            }
            JSONObject userObject = res.getData();
            if (userObject == null) {
                return ResponseInfo.errorReturn("查询客户信息异常!");
            }
//            如果大于
            if (count > celCount && userObject.getString("level").equals("0")) {
                log.error("=======用户订单支付次数过多!请核实=====================");
                redisLock.unlock();
                return ResponseInfo.errorReturn("提交付款失败!购买订单支付次数过多!");
            }
            //调用第三方支付
            //查询用户账
            InnerAcctManger innerAcctManger = new InnerAcctManger();
            innerAcctManger.setUserId(recvUserNo);
            ResponseInfo<InnerAcctManger> result = this.query(innerAcctManger);
            JSONObject userAcc = null;

            String custStr = customerManageFeign.queryAccount(recvUserNo);
            ResponseInfo<List<JSONObject>> accResult = JSONObject.parseObject(custStr, ResponseInfo.class);
            if (!accResult.isSuccess()) {
                return ResponseInfo.errorReturn("查询客户信息异常!");
            }
            List<JSONObject> userAccout = accResult.getData();
            if (userAccout.isEmpty()) {
                return ResponseInfo.errorReturn("没有查询到该用户绑定卡信息!");
            }
            //查找默认的卡
            for (JSONObject jb : userAccout) {
                userAcc = jb;
                if (jb.getBoolean("defaultCard")) {
                    break;
                }
                userAcc = null;
            }
            //如果没有设置默认卡，取第一条
            if (userAcc == null) {
                userAcc = userAccout.get(0);
            }
            if (userAcc == null) {
                return ResponseInfo.errorReturn("提交付款失败!账户没有设置!");
            }
            //查询平台账号
            LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InnerAcctManger::getAccType, SysConstant.userType_4);
            InnerAcctManger platformAcc = this.getOne(queryWrapper);
            log.info("=========查寻查询平台账号==============acct={}", platformAcc);

            List<AccFlow> list = pintAccFlow(platformAcc.getAccName(), orderNo, amount, chanel, platformAcc.getAccName(), platformAcc.getAccNo(), userAcc);
            AccFlow payAccFlow = new AccFlow();
            payAccFlow.setOrderNumber(orderNo);
            payAccFlow.setTranType(acctranType_1);
            log.info("=========开始请求代付中心==============");
            String json = payMangerUtil.payment(payAccFlow, 1, null, "恭喜您:参与公司活动,获得" + amount + " 元奖励", 8 + "", userAcc);
            log.info("=========代付中心返回==============result={}", json);


            JSONObject results = JSONObject.parseObject(json);
            String data = results.getString("data");
            if (!pay_success_code.equals(results.getString("retCode"))) {
                return ResponseInfo.errorReturn(results.getString("retMsg"));
            }
            String restCode = results.getString("resCode");
            String resMsg = results.getString("resMsg");
            //解析data
            try {
                data = RSAUtil.decrypt(results.getString("data"), payManger.getPayPriKey(), "utf-8");
            } catch (Exception e) {
                log.error("支付验签失败!msg={},e={}", e.getMessage(), e);
                saveNotifyInfo("payMangerUtil", resMsg, restCode.equals(pay_success_code) ? "0" : "1", payMangerUtil.getClass().getSimpleName(), "payment", 1, null, "恭喜您:参与公司活动,获得" + amount + " 元奖励", 8 + "", userAcc);
                return ResponseInfo.errorReturn("支付验签失败!");
            }

            //2.
            //保存流水
            accFlowService.saveBatch(list);
            JSONObject jsonObject = JSONObject.parseObject(json);
            saveNotifyInfo("payMangerUtil", resMsg, restCode.equals(pay_success_code) ? "0" : "1", payMangerUtil.getClass().getSimpleName(), "payment", 1, null, "恭喜您:参与公司活动,获得" + amount + " 元奖励", 8 + "", userAcc);
            if (!jsonObject.getString("retCode").equals(pay_success_code)) {
                return ResponseInfo.errorReturn(resMsg);
            }

            jsonObject = JSONObject.parseObject(data);
//            String voucher = null;
            BigDecimal fee = null;
            if (jsonObject.getString("resCode").equals(pay_success_code)) {
                jsonObject = JSONObject.parseObject(data);
                String voucher = jsonObject.getString("voucher");
                fee = jsonObject.getBigDecimal("fee");
                list.forEach(s -> {
                    s.setPayNumber(voucher);
                });
                return ResponseInfo.success("提交成功!");
            } else {
                log.error("===========代付中心返回错误!msg={}", data);
                return ResponseInfo.errorReturn(jsonObject.getString("resMsg "));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseInfo.errorReturn("系统异常!");
        } finally {
            redisLock.unlock();
        }
    }

    @Override
    public ResponseInfo<String> chainPresentation(String chainUserId, BigDecimal amount) {
        log.info("=========开始提现,账号Id={},金额={}==========",chainUserId,amount);
        //查找供应链 商户,如果没有,则创建
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock();
        //根据用户查询是否已经存在
        LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InnerAcctManger::getUserId, chainUserId);
        //查询用户账号
        try {
            //TODO 根据用查询用户账号
           InnerAcctManger acctManger = innerAcctMangerService.getOne(queryWrapper);
            log.info("=======根据条件查询供应链账户={}========",acctManger);

           if(acctManger ==null){
               log.error("======提现失败!=账户不存在!======");
               return ResponseInfo.errorReturn("提现失败!账户不存在!");
           }
           if(acctManger.getCanUserBalance().compareTo(amount) <=-1){
               log.error("提现失败!账户余额不足!账户余额: "+acctManger.getCanUserBalance()+",提现金额: "+amount);
               return ResponseInfo.errorReturn("提现失败!账户余额不足!账户余额: "+acctManger.getCanUserBalance()+",提现金额: "+amount);
           }
            //查询平台账号
            //记录流水->1.提现流水
            AccFlow accFlow =new AccFlow();
           accFlow.setUserNo(chainUserId);
           accFlow.setTranType(acctranType_1);
           accFlow.setTranDate(new Date());
           accFlow.setChannel("presentation");
           accFlow.setPayAccNo(acctManger.getAccNo());
           accFlow.setPayAccName(acctManger.getAccName());
           accFlow.setPayAmount(amount);
           //查询绑卡记录,填写绑卡信息
            //TODO
//           accFlow.setReceAccNo();
//            accFlow.setReceAccName();
            //TODO 查询用户绑卡
            JSONObject userAcc =new JSONObject();
            //保存流水
            accFlowService.save(accFlow);
            //调用第三方支付
            log.info("=========开始请求代付中心==============");
            String json = payMangerUtil.payment(accFlow, 1, null, "用户提现" + amount + " 元", 8 + "", userAcc);
            log.info("=========代付中心返回==============result={}", json);


            JSONObject results = JSONObject.parseObject(json);
            String data = results.getString("data");
            if (!pay_success_code.equals(results.getString("retCode"))) {
                return ResponseInfo.errorReturn(results.getString("retMsg"));
            }
            String restCode = results.getString("resCode");
            String resMsg = results.getString("resMsg");
            //解析data
            try {
                data = RSAUtil.decrypt(results.getString("data"), payManger.getPayPriKey(), "utf-8");
            } catch (Exception e) {
                log.error("支付验签失败!msg={},e={}", e.getMessage(), e);
                saveNotifyInfo("payMangerUtil", resMsg, restCode.equals(pay_success_code) ? "0" : "1", payMangerUtil.getClass().getSimpleName(), "payment", 1, null, "恭喜您:参与公司活动,获得" + amount + " 元奖励", 8 + "", userAcc);
                return ResponseInfo.errorReturn("支付验签失败!");
            }

          JSONObject  jsonObject = JSONObject.parseObject(data);
//            String voucher = null;
            BigDecimal fee = null;
            if (jsonObject.getString("resCode").equals(pay_success_code)) {
                jsonObject = JSONObject.parseObject(data);
                String voucher = jsonObject.getString("voucher");
                fee = jsonObject.getBigDecimal("fee");
                accFlow.setPayNumber(voucher);
                //供应商出账,余额减少
                acctManger.setBalance(acctManger.getBalance().subtract(amount));
                acctManger.setCanUserBalance(acctManger.getCanUserBalance().subtract(amount));
                innerAcctMangerService.saveOrUpdate(acctManger);
                return ResponseInfo.success("提现成功!");
            } else {
                log.error("===========代付中心返回错误!msg={}", data);
                return ResponseInfo.errorReturn(jsonObject.getString("resMsg "));
            }

        }catch (Exception e){
            log.error("======执行失败!msg={},e={}",e.getMessage(),e);
            return ResponseInfo.errorReturn("支付失败!系统异常");
        }finally {
            lock.writeLock().unlock();;

        }
    }

    @Override
    public ResponseInfo<String> paymentByChain(String chainUserId, BigDecimal amount, String orderNo, String channel) {
        //查找供应链 商户,如果没有,则创建
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock();
        //根据用户查询是否已经存在
        LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InnerAcctManger::getUserId, chainUserId);
        //查询用户账号
        try {
            //TODO 根据用查询用户账号
            InnerAcctManger acctManger = innerAcctMangerService.getOne(queryWrapper);
            if (acctManger == null) {
                acctManger = new InnerAcctManger();
                acctManger.setUserId(chainUserId);
                acctManger.setAccType(userType_3);
                addInner(acctManger);
            }
            //查询平台账号
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(InnerAcctManger::getAccType, SysConstant.userType_4);
            InnerAcctManger platformAcc = innerAcctMangerService.getOne(queryWrapper);
            //记录流水->1.平台支出到用户 ,2用户收入平台支出
            List<AccFlow> list = innerAcctMangerService.pintAccFlow(chainUserId, orderNo, amount, channel, platformAcc.getAccName(), platformAcc.getAccNo(), acctManger);
            //保存流水
            accFlowService.saveBatch(list);
            //增加内部账户
            //平台出账
            platformAcc.setBalance(platformAcc.getBalance().subtract(amount));
            platformAcc.setCanUserBalance(platformAcc.getCanUserBalance().subtract(amount));
            innerAcctMangerService.saveOrUpdate(platformAcc);
            //供应商入账
            acctManger.setBalance(acctManger.getBalance().add(amount));
            acctManger.setCanUserBalance(acctManger.getCanUserBalance().add(amount));
            innerAcctMangerService.saveOrUpdate(platformAcc);
            return ResponseInfo.success("支付成功!");
        }catch (Exception e){
            log.error("======执行失败!msg={},e={}",e.getMessage(),e);
            return ResponseInfo.errorReturn("支付失败!系统异常");
        }finally {
            lock.writeLock().unlock();;

        }
    }

    /**
     * 入账
     *
     * @param userNo    当前操作用户编号
     * @param orderNo   订单编号->唯一
     * @param amount    交易金额
     * @param channel   渠道,记录是哪个服务的入账,订单树微服务不需要 回调,其他微服务需要回调
     * @param payType   交易方式
     * @param returnUrl 返回路径
     * @param
     * @return
     */
    @Override
    public ResponseInfo<String> accountEntry(String userNo, String orderNo, BigDecimal amount, String channel, int payType, String returnUrl, String subject, String tradeType, Integer orderCount, String buyType,Integer orderType) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().tryLock();
        //根据用户查询是否已经存在
        LambdaQueryWrapper<InnerAcctManger> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InnerAcctManger::getUserId, userNo);
        //查询用户账号
        //TODO 根据用查询用户账号
        InnerAcctManger acctManger = innerAcctMangerService.getOne(queryWrapper);
        if (acctManger == null) {
            acctManger = new InnerAcctManger();
            acctManger.setUserId(userNo);
            acctManger.setUserName(userNo);
            acctManger.setAccType(userType_1);
            addInner(acctManger);
        }
        //查询平台账号
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InnerAcctManger::getAccType, SysConstant.userType_4);
        InnerAcctManger platformAcc = innerAcctMangerService.getOne(queryWrapper);
        String payLoad = null;
        if (acctManger != null) {
            //拼接支付信息
            List<AccFlow> list = innerAcctMangerService.pintAccFlow(userNo, orderNo, amount, channel, acctManger.getAccName(), acctManger.getAccNo(), platformAcc);
            //从数据库查询支护方式
            PayChannel payChannel= payChannelMapper.selectByPayTypeOrderBy(payType,tradeType);
            //调用支付
            String json = payMangerUtil.placeAnOrder(list.get(0), payType, returnUrl, subject, payChannel.getOpenKey(), orderCount + "_" + buyType+"_"+orderType);
            JSONObject result = JSONObject.parseObject(json);

            //修改账户消费情况
            String restCode = result.getString("retCode");
            String resMsg = result.getString("retMsg");
            //交易失败，推出
            if (!pay_success_code.equals(restCode)) {
                return ResponseInfo.errorReturn(resMsg);
            }

            //解析data
            String data = null;
            try {
                data = RSAUtil.decrypt(result.getString("data"), payManger.getPayPriKey(), "utf-8");
            } catch (Exception e) {
                log.error("支付验签失败!msg={},e={}", e.getMessage(), e);
                saveNotifyInfo("payMangerUtil", resMsg, restCode.equals(pay_success_code) ? "0" : "1", payMangerUtil.getClass().getSimpleName(), "placeAnOrder", list.get(0), payType, returnUrl, subject, tradeType);
                return ResponseInfo.errorReturn("支付验签失败!");
            }

            //验签
            if (!RSAUtil.verify(data, result.getString("sign"), payManger.getPayPullKey(), "utf-8")) {
                return ResponseInfo.errorReturn("支护交易验证不通过!");
            }
            result = JSONObject.parseObject(data);
            restCode = result.getString("resCode");
            resMsg = result.getString("resMsg");
            saveNotifyInfo("payMangerUtil", resMsg, restCode.equals(pay_success_code) ? "0" : "1", payMangerUtil.getClass().getSimpleName(), "placeAnOrder", list.get(0), payType, returnUrl, subject, tradeType);
            String sysOrderNo = result.getString("sysOrderNo");
            String status = result.getString("status");
            if (result.getString("resCode").equals(pay_success_code)) {
                list.forEach(s -> {
                    s.setPayNumber(sysOrderNo);
                    s.setBusiStatus(status + "");
                });
                //保存流水
                accFlowService.saveBatch(list);
                Map<String, Object> map = new HashMap<>();
                map.put("orderNo", result.getString("outTradeNo"));
                map.put("payNo", result.getString("sysOrderNo"));
                map.put("returnUrl", result.getString("payload"));
                map.put("openType", payChannel.getOpenKey());
                return ResponseInfo.success(JSONObject.toJSONString(map));
            } else {
                return ResponseInfo.errorReturn(resMsg);
            }
        } else {
            return ResponseInfo.errorReturn("该流水不存在!");
        }
    }

    public void saveAccBannel(InnerAcctManger platformAcc, InnerAcctManger innerAcctManger, BigDecimal money, boolean isPay) {
        if (!isPay) {
            //平台增加
            platformAcc.setCanUserBalance(platformAcc.getCanUserBalance().add(money));
            platformAcc.setBalance(platformAcc.getBalance().add(money));
            //普通用户减少
            innerAcctManger.setCanUserBalance(platformAcc.getCanUserBalance().subtract(money));
            innerAcctManger.setBalance(platformAcc.getBalance().subtract(money));


        } else {
            //平台增加
            platformAcc.setCanUserBalance(platformAcc.getCanUserBalance().subtract(money));
            platformAcc.setBalance(platformAcc.getBalance().subtract(money));
            //普通用户减少
            innerAcctManger.setCanUserBalance(platformAcc.getCanUserBalance().add(money));
            innerAcctManger.setBalance(platformAcc.getBalance().add(money));
        }
        this.saveOrUpdate(platformAcc);
        this.saveOrUpdate(innerAcctManger);
    }

    /**
     * 拼接交易流水
     *
     * @param userNo
     * @param orderNo
     * @param amount
     * @param channel
     * @return
     */
    @Override
    public List<AccFlow> pintAccFlow(String userNo, String orderNo, BigDecimal amount, String channel, String accName, String accNo, InnerAcctManger platformAcc) {
        List<AccFlow> accFlowList = new ArrayList<>();
        //出账
        AccFlow accFlow = new AccFlow();
        accFlow.setBusiStatus("00");
        accFlow.setUserNo(userNo);
        accFlow.setCreatedBy("admin");
        accFlow.setUpdatedBy("admin");
        accFlow.setEnable(1);
        accFlow.setOrderNumber(orderNo);
        //付款方
        accFlow.setPayAmount(amount);
        accFlow.setPayAccName(accName);
        accFlow.setPayAccNo(accNo);
        //收款方
        accFlow.setReceAccName(platformAcc.getAccName());
        accFlow.setReceAccNo(platformAcc.getBankNo());

        accFlow.setTranDate(new Date());
        accFlow.setTranType(acctranType_1);
        accFlow.setChannel(channel);
        accFlowList.add(accFlow);


        //入账
        accFlow = new AccFlow();
        accFlow.setBusiStatus("00");
        accFlow.setCreatedBy("admin");
        accFlow.setUpdatedBy("admin");
        accFlow.setEnable(1);
        accFlow.setOrderNumber(orderNo);
        accFlow.setUserNo(userNo);
        //付款方
        accFlow.setPayAmount(amount);
        accFlow.setPayAccName(platformAcc.getAccName());
        accFlow.setPayAccNo(platformAcc.getBankNo());
        //收款方
        accFlow.setReceAccName(accName);
        accFlow.setReceAccNo(accNo);

        accFlow.setTranDate(new Date());
        accFlow.setTranType(accTranType_2);
        accFlow.setChannel(channel);
        accFlowList.add(accFlow);
        return accFlowList;
    }

    public List<AccFlow> pintAccFlow(String userNo, String orderNo, BigDecimal amount, String channel, String accName, String accNo, JSONObject userAcc) {
        List<AccFlow> accFlowList = new ArrayList<>();
        //出账
        AccFlow accFlow = new AccFlow();
        accFlow.setBusiStatus("00");
        accFlow.setUserNo(userNo);
        accFlow.setCreatedBy("admin");
        accFlow.setUpdatedBy("admin");
        accFlow.setEnable(1);
        accFlow.setOrderNumber(orderNo);
        //付款方
        accFlow.setPayAmount(amount);
        accFlow.setPayAccName(accName);
        accFlow.setPayAccNo(accNo);
        //收款方
        accFlow.setReceAccName(userAcc.getString("accountName"));
        accFlow.setReceAccNo(userAcc.getString("cardNo"));

        accFlow.setTranDate(new Date());
        accFlow.setTranType(acctranType_1);
        accFlow.setChannel(channel);
        accFlowList.add(accFlow);


        //入账
        accFlow = new AccFlow();
        accFlow.setBusiStatus("00");
        accFlow.setCreatedBy("admin");
        accFlow.setUpdatedBy("admin");
        accFlow.setEnable(1);
        accFlow.setOrderNumber(orderNo);
        accFlow.setUserNo(userNo);
        //付款方
        accFlow.setPayAmount(amount);
        accFlow.setPayAccName(userAcc.getString("accountName"));
        accFlow.setPayAccNo(userAcc.getString("cardNo"));
        //收款方
        accFlow.setReceAccName(accName);
        accFlow.setReceAccNo(accNo);

        accFlow.setTranDate(new Date());
        accFlow.setTranType(accTranType_2);
        accFlow.setChannel(channel);
        accFlowList.add(accFlow);
        return accFlowList;
    }
}