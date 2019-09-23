DROP TABLE IF EXISTS `ebank_flow`;
CREATE TABLE `ebank_flow`(
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `ebank_order_no`        CHAR(32)        NOT NULL                COMMENT 'ebank支付订单编号',
    `sys_order_no`          VARCHAR(50)                             COMMENT '支付中心订单号',
    `voucher`               VARCHAR(50)                             COMMENT '交易凭证号',
    `account_no`            CHAR(32)        NOT NULL                COMMENT '账号，系统生成',
    `account_type`          VARCHAR(10)                             COMMENT '账户类型',
--
    `flow_type`             VARCHAR(5)      NOT NULL                COMMENT '流水类型，in转入，out转出',
    `flow_source`           VARCHAR(10)     NOT NULL                COMMENT '流水来源，商品/套餐购买 product, 用户转账 transfer',
--
    `amount`                DECIMAL(30,2)   NOT NULL                COMMENT '流水金额',
    `status`                VARCHAR(10)     NOT NULL                COMMENT '流水状态,wait等待支付，success支付成功，failed支付失败',
--
    `create_time`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每笔转账的转入转出记录，不包括合并转账';

