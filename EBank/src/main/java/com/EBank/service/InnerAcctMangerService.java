package com.EBank.service;

import com.EBank.entity.AccFlow;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.EBank.entity.InnerAcctManger;
import com.xinyunfu.jace.utils.ResponseInfo;
//import org.redisson.api.RLock;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;


public interface InnerAcctMangerService extends IService<InnerAcctManger>{


    /**
     * innerAcctManger分页列表
     *
     * @param entity innerAcctManger实体
     * @return ResponseInfo
     */
    public ResponseInfo<IPage<InnerAcctManger>> list(InnerAcctManger entity, Integer page, Integer pageSize);

    /**
     * innerAcctManger根据id查询详情
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<InnerAcctManger> queryById(Long id);

    /**
     * innerAcctManger根据条件查询详情
     *
     * @return ResponseInfo
     */
    public ResponseInfo<InnerAcctManger> query(InnerAcctManger entity);

    /**
     * 新增innerAcctManger
     *
     * @param entity 请求参数
     * @return ResponseInfo
     */
    public ResponseInfo<String> add( InnerAcctManger entity);

    /**
     * 更新innerAcctManger
     *
     * @param entity 请求参数
     * @return ResponseInfo
     */
    public ResponseInfo<String> update(InnerAcctManger entity);

    /**
     * 删除innerAcctManger
     *
     * @param id 主键id
     * @return ResponseInfo
     */
    public ResponseInfo<String> delete(Long id);

    /**
     *  卡券交易付款
     *  1.A推荐用户B
     * 2.B用户购买套餐,订单编号001
     * 3.将001订单编号传给支付平台,进行支付.
     * @param recvUserNo 接收用户
     * @param sourceUserNo 收益来源人
     * @param amount 金额
     * @param chanel 渠道->微服务名
     * @return
     */
    ResponseInfo<String> paymentByCoupon(String recvUserNo, BigDecimal amount, String chanel,String sourceUserNo,String orderNo);
    ResponseInfo<String> addInner(InnerAcctManger innerAcctManger);

    ResponseInfo<String> accountEntry(String userNo, String orderNo, BigDecimal amount, String channel,int payType,String returnUrl,String subject,String tradeType,Integer orderCount,String buyTpe,Integer orderType);

    String callbackAccountEntry(String json);

    String callbackPayment(String json);

    public List<AccFlow> pintAccFlow(String userNo, String orderNo, BigDecimal amount, String channel, String accName, String accNo, InnerAcctManger platformAcc);
    /**
     * 向供应商支付金额
     * 1.记录流水
     * 2.账户增加
     * @param chainUserId
     * @param amount
     * @param orderNo
     * @param chanel
     * @return
     */
    ResponseInfo<String> paymentByChain(String chainUserId, BigDecimal amount, String orderNo, String chanel);

    ResponseInfo<String> chainPresentation(String chainUserId, BigDecimal amount);
    /**
     * @author liuzhen
     * @version 1.0
     * @date 2019/7/8
     */


//    interface DistributedLocker {
//
//        RLock lock(String lockKey);
//
//        RLock lock(String lockKey, int timeout);
//
//        RLock lock(String lockKey, TimeUnit unit, int timeout);
//
//        boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);
//
//        void unlock(String lockKey);
//
//        void unlock(RLock lock);
//    }
}