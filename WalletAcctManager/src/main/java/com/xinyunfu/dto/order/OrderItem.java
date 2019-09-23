package com.xinyunfu.dto.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 订单子表
 * </p>
 *
 * @author Xurongze
 * @since 2019-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderItem extends Base{

    /**
     * 子订单id 自增
     */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Long itemId;

    /**
     * 用户ID 
     */
    private String userId;

    /**
     * 子订单编号
     */
    private String itemNo;

    /**
     * 主订单编号
     */
    private String masterNo;

    /**
     * 商家ID
     */
    private Long storeId;

    /**
     * 商家名称
     */
    private String storeName;

    /**
     * 商品总金额
     */
    private BigDecimal totalAmount = new BigDecimal(0);

    /**
     * 子订单应付金额
     */
    private BigDecimal amount = new BigDecimal(0);


    /**
     * 子订单实付金额
     */
    private BigDecimal payAmount = new BigDecimal(0);

    /**
     * 总运费
     */
    private BigDecimal totalFreight = new BigDecimal(0);

    /**
     * 手续费（预留字段）
     */
    private BigDecimal charge = new BigDecimal(0);

    /**
     * 总优惠金额
     */
    private BigDecimal totalDiscount = new BigDecimal(0);

    /**
     * 总成本价格
     */
    private BigDecimal costAmount = new BigDecimal(0);

    /**
     * 购买总数量
     */
    private Integer totalCount = 0;

    /**
     * 订单状态（待付款=0,待发货=1，待签收=2，已签收=3，待点评=4，维权中=5，预留中=6,已退款=7，交易完成=8，已取消=9，异常订单=11）默认为0
     */
    private Integer status;

    /**
     * 快递公司名称
     */
    private String shippingCompName;

    /**
     * 快递单号
     */
    private String shippingSn;

    /**
     * 发货时间
     */
    private Timestamp deliveryTime;

    /**
     * 订单来源(0=其他,1=京东,2=怡亚通)
     */
    private Integer orderSource;


    /**
     * 商家结算 商家结算(0=初始化/1=可结算/2=待结算)
     */
    private Integer settlement;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 支付交易号
     */
    private String payNumber;

    /**
     * 支付方式 （微信=16，支付宝=32，快捷支付=64，网银支付=128）
     */
    private Integer payType;

    /**
     * 支付状态（0待支付/1支付成功/2支付失败/3交易关闭）
     */
    private Integer payStatus;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 第三方订单号
     */
    private Long thirdOrder;


    /**
     * 剩余时间
     */
    @TableField(exist = false)
    private String lastTime;

    /**
     * 订单状态对应值
     */
    @TableField(exist = false)
    private String statusVal;

    /**
     * 下单时间，格式化后的 String 类型时间
     */
    @TableField(exist = false)
    private String createTime;

    /**
     * 商品集合
     */
    @TableField(exist = false)
    private List<OrderCommodity> list;

    /**
     * 收货地址
     */
    @TableField(exist = false)
    private OrderConsignee consignee;

}
