package com.xingyunfu.ebank.constant;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 订单转账处理
 */
public interface TransferConstant {

    /**
     * 合并订单状态，
     * applying             申请支付中，
     * apply_filed          申请支付失败，
     * success              支付成功，
     * wait_handle          等待人工处理
     * handle_success       手工处理完成
     */
    String status_applying = "applying";
    String status_apply_failed = "apply_failed";
    String status_success = "success";
    String status_wait_handle = "wait_handle";
    String status_handle_success = "handle_success";

    Function<Map<Integer, Integer>, String> timeFun = map -> map.entrySet().stream()
            .map(entry -> entry.getKey() + ":" + (entry.getValue()==0? "00":entry.getValue()))
            .collect(Collectors.joining(", "));
}
