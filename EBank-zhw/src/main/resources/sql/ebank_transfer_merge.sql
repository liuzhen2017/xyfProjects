DROP TABLE IF EXISTS `ebank_transfer_merge`;
CREATE TABLE `ebank_transfer_merge`(
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `ebank_order_no`        CHAR(32)                        COMMENT '合并后的订单编号',
    `closed`                BOOLEAN         DEFAULT FALSE   COMMENT '是否已关闭',
--
    `receive_account_no`    CHAR(20)        NOT NULL        COMMENT '资金转入用户',
    `receive_account_type`  VARCHAR(10)     NOT NULL        COMMENT '转入用户类型',
--
    `amount`                DECIMAL(30,2)   NOT NULL        COMMENT '付款金额',
    `status`                VARCHAR(20)                     COMMENT '合并订单状态, applying申请支付中，apply_filed申请支付失败，success支付成功，wait_handle等待人工处理',
    `voucher`               VARCHAR(50)                     COMMENT '交易凭证号',
--
    `create_time`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合并后向用户转账';

-- 合并后向普通用户转账，为系统自动转出，除非超过一定额度，否则不需要人工处理
-- 合并后下向推广大使/vip用户转账，为人工处理，本服务只需要提供转账修改接口
