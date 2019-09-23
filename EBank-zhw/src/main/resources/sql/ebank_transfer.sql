DROP TABLE IF EXISTS `ebank_transfer`;
CREATE TABLE `ebank_transfer`(
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `order_no`              VARCHAR(50)     NOT NULL UNIQUE COMMENT '转账订单编号，其它服务的订单编号',
    `ebank_order_no`        CHAR(32)                        COMMENT '合并后的订单编号，合并订单是的订单编号',
--
    `source_account_no`     CHAR(20)        NOT NULL        COMMENT '资金转出用户',
    `source_account_type`   VARCHAR(10)     NOT NULL        COMMENT '转出用户类型',
    `receive_account_no`    CHAR(20)        NOT NULL        COMMENT '资金转入用户',
    `receive_account_type`  VARCHAR(10)     NOT NULL        COMMENT '转入用户类型',
--
    `person_account_no`     CHAR(20)                        COMMENT '创建转帐时的收款人',
    `person_account_type`   VARCHAR(10)                     COMMENT '创建转帐是的收款人账户类型',
--
    `amount`                DECIMAL(30,2)   NOT NULL        COMMENT '付款金额',
--
    `closed`                BOOLEAN         DEFAULT FALSE   COMMENT '是否已关闭',
    `server_name`           VARCHAR(20)                     COMMENT '发起支付请求的服务',
--
    `create_time`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='向用户转账';

ALTER TABLE `ebank_transfer` ADD `person_account_no` CHAR(20) COMMENT '创建转帐时的收款人' AFTER `receive_account_type`;
ALTER TABLE `ebank_transfer` ADD `person_account_type` VARCHAR(10) COMMENT '创建转帐是的收款人账户类型' AFTER `person_account_no`;
