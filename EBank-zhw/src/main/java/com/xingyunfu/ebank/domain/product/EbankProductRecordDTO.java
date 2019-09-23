package com.xingyunfu.ebank.domain.product;

import com.xingyunfu.ebank.domain.account.EbankAccountDTO;
import com.xingyunfu.ebank.domain.channel.EbankChannelDTO;
import com.xingyunfu.ebank.dto.product.ProductRecordAddDTO;
import com.xingyunfu.ebank.dto.product.ProductRecordAddV2DTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import static com.xingyunfu.ebank.constant.ProductRecordConstant.status_apply_process;

/**
 * 产品购买记录，产品包括商品和套餐
 */
@NoArgsConstructor
@Data
public class EbankProductRecordDTO {
    private Long id;
    private String ebankOrderNo;            //ebank支付订单号, 购买订单号可能存在多个支付订单号
    private String orderNo;                 //购买订单编号，其它服务的订单编号
    private String sysOrderNo;              //支付中心单号
    private String accountNo;               //每一个用户的账号，记录转账，系统生成
    private String productType;             //产品类型，00商品，01套餐

    private BigDecimal amount;              //付款金额
    private Integer payType;                //支付类型, 2云闪付，16微信支付，32支付宝，64快捷支付，128网银支付
    private Integer tradeType;              //付款方式，1二维码，2jsapi，4h5，8app，16付款码
    private String returnUrl;               //调用端url，只记录，不处理

    private String serverName;              //发起支付请求的服务
    private String status;                //支付状态，0申请支付中，1申请支付成功，2申请支付失败, 3支付失败，4支付成功

    private Integer orderType;               //订单模块请求参数，原样返回
    private String clientIp;                //客户端ip
    private String subject;                 //交易主体

    private Timestamp createTime;           //创建时间
    private Timestamp lastModifyTime;       //最后修改时间

    public EbankProductRecordDTO(ProductRecordAddV2DTO product, EbankAccountDTO ebankAccount, EbankChannelDTO channel){
        this.ebankOrderNo = UUID.randomUUID().toString().replaceAll("-", "");
        this.orderNo = product.getOrderNo();
        this.accountNo = ebankAccount.getAccountNo();
        this.productType = product.getProductType();

        this.amount = product.getAmount();
        this.payType = channel.getPayType();
        this.tradeType = channel.getTradeType();
        this.returnUrl = product.getReturnUrl();

        this.serverName = product.getServerName();
        this.status = status_apply_process;

        this.orderType = product.getOrderType();
        this.clientIp = product.getClientIp();
        this.subject = product.getSubject();
    }
}
