package com.xinyunfu.aop;

import lombok.Getter;
import lombok.Setter;

/**
 * @author XRZ
 * @date 2019/7/31
 * @Description : 异常消息枚举
 */
@Getter
public enum ExecptionEnum {

    THIS_MESSAGE(7051,"X",false,true),
    CHANGE_TIME_OUT_FAIL(7052,"修改时间限制失败！",true,false),
    INCONSISTENT_AMOUNT(7050,"支付金额和订单应付金额不一致！",true,true),
    ADD_CART_GOODS_MAX(7048,"购买数量上限！",false,true),
    BUY_COUNT_OUT_MAX(7047,"亲~ 此商品已在购物车了哟！",false,true),
    ERROR_EXPRESS_IS_NOT(7046,"根据快递公司名称获取公司编号失败！快递公司不存在！"),
    ERROR_REQUSET_VO_FAIL(7045,"根据优惠券的ID获取优惠券信息失败！"),
    ERROR_COUPONS_ORDER_IS_NOT(7044,"优惠券订单不存在！"),
    ERROR_ORDERNO_IS_UNDEFINED(7043,"该订单不存在！"),
    REQUSET_GOODS_SERVICE_RETURN_STOCK_FAIL(7042,"调用商品服务，归还商品库存失败！",true,false),
    CREATE_ORDER_SAVE_MASTER_FAIL(7041,"下单时生成主订单失败！"),
    CREATE_ORDER_SAVE_COMMODITY_FAIL(7040,"下单时保存商品信息失败！"),
    CREATE_ORDER_SAVE_ITEM_FAIL(7039,"下单时生成子订单失败!"),
    CREATE_ORDER_SAVE_CONSIGNEE(7038,"下单时保存收货地址失败！"),
    CHANGE_ORDER_STATUS_FAIL(7037,"修改订单状态失败！"),
    ERROR_DELETE_ORDER(7036,"删除订单失败！"),
    CONFIRM_GOODS_FAIL(7035,"确认收货失败！"),
    CREATE_ORDER_COUPONS_FAIL(7034,"创建优惠券订单失败！"),
    UPDATE_ORDER_COUPONS_FAIL(7033,"修改优惠券订单失败！"),
    BY_INVOICEID_GET_REDIS_CACHE(7032,"从redis中获取发票信息失败！redis中不存在该键！"),
    SAVE_ORDER_INVOICE_INFO_FAIL(7031,"保存发票关联信息失败！"),
    SAVE_INVOICE_INFO_FAIL(7030,"保存发票信息失败！"),
    SAVE_INVOICE_INFO_CACHE_FAIL(7029,"发票信息放入缓存失败！"),
    ERROR_PARAM_EMAIL(7028,"邮箱不能为空！",false,true),
    BY_ORDERNO_GET_COUPONS_INFO_ISNULL(7027,"根据订单编号获取优惠券信息失败！"),
    BY_ORDERNO_GET_ORDERINFO(7026,"付款时根据订单编号获取子订单信息失败！"),
    ORDER_STATUC_ERROR(7025,"订单状态异常，无法提交支付！",true,false),
    GOODS_FREIGHT_CHANGE(7024,"商品运费已更新，请重新下单！"),
    GOODS_PRICE_CHANGE(7023,"商品价格已更新，请重新下单！"),
    COUPONS_STATUS_ERROR(7022,"支付校验时发现优惠券状态异常！"),
    EBANK_RETURN_ORDERNO_ATYPISM(7021,"调用电子银行返回的订单编号不一致！"),
    EBAKN_RETURN_URL_ISNULL(7020,"调用电子银行返回的URL为空！"),
    EBANK_RETURN_OPENTYPE_ISNULL(7019,"调用电子银行返回的支付打开方式为空！"),
    UPDATE_ORDER_ITEM_FAIL(7018,"修改子订单记录失败！"),
    SAVE_CONSIGNEE_FAIL(7017,"下单时保存收货地址信息失败！"),
    ADDRESS_INFO_ERROR(7016,"该地址信息不属于当前用户!"),
    BY_ORDERNO_GET_CONSIGNEE_FAIL(7016,"根据订单编号获取收货地址失败!"),
    VIP_USER_BUY_MAX_MONEY(7015,"单次购买交易金额超出！",false,true),
    COMMON_USER_BUY_COUNT(7014,"很抱歉，普通用户只能购买一件套餐！",false,true),
    BY_ORDERNO_GET_INVOICEINFO(7013,"根据订单编号获取发票信息失败！"),
    BY_INVOICEID_GET_INVOICEINFO(7012,"根据发票ID获取发票信息失败！"),
    ORDER_ITEM_UNDEFINED(7011,"该子订单不存在！"),
    ERROR_UPDATE_INVOICE_STATUS(7010,"修改发票状态失败！"),
    ERROR_DELETE_CART_FAIL(7009,"删除购物车失败！"),
    ERROR_UPDATE_CART_FAIL(7008,"修改购物车失败！"),
    ERROR_INSUFFICIENT_STOCK(7007,"很抱歉，库存不足！",false,true),
    ERROR_ADD_CART_FAIL(7006,"加入购物车失败！"),
    ERROR_UPDATE_INVOICE_STATUC(7005,"修改订单发票状态失败!",false,false),
    ERROR_TIMING_RETURN_VOUCHERS(7004,"定时任务:调用券集市，归还券失败！",true,false),
    ERROR_TIMING_CONFIRM_ORDER(7003,"定时任务:处理自动确认收货的订单,修改订单状态失败！",true,false),
    ERROR_TIMING_DELETE_ORDER(7002,"定时任务:处理已取消超过七天的订单,删除失败！",true,false),
    CLEAR_CACHE_FAIL(7001,"清除缓存失败！"),
    ERROR_PARAM(7000,"请求参数有误！"),
    /**
     * 提供给其他服务用 不需要记录日志，需要展示给前端看
     */
    TYPE_NOTLOG_NOTSHOW(0,"",false,true),
    /**
     * 提供给其他服务用 需要记录日志，需要展示给前端看
     */
    TYPE_LOG_SHOW(0,"",true,true),
    /**
     * 提供给其他服务用 不需要记录日志，需要展示给前端看
     */
    TYPE_NOTLOG_SHOW(0,"",false,true),
    /**
     * 提供给其他服务用 需要记录日志，需要展示给前端看
     */
    TYPE_LOG_NOTSHOW(0,"",true,true);

    /**
     * 错误状态码
     */
    @Setter
    private int code;

    /**
     * 错误消息
     */
    @Setter
    private String message;

    /**
     * 是否需要入库记录日志
     */
    private boolean log;

    /**
     * 是否需要显示给前端看
     */
    private boolean show;

    /**
     * 抛出异常信息，不需要显示给前端看和入库记录
     *
     * @param code    状态码
     * @param message 信息
     */
    ExecptionEnum(int code,String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 抛出异常信息
     *
     * @param code      状态码
     * @param message   消息
     * @param log       是否需要入库记录日志
     * @param show      是否需要展示消息给前端看
     */
    ExecptionEnum(int code,String message, boolean log, boolean show) {
        this.code = code;
        this.message = message;
        this.log = log;
        this.show = show;
    }

    /**
     * 根据 错误状态码获取 错误消息
     * @param code
     * @return
     */
    public static String getMessageByCode(int code) {
        for (ExecptionEnum state : values())
            if (state.getCode() == code)
                return state.getMessage();
        return null;
    }


}
