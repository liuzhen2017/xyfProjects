DROP TABLE IF EXISTS `ebank_record_result`;
CREATE TABLE `ebank_record_result` (
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `startTime`             CHAR(19)                COMMENT '对账开始时间',
    `endTime`               CHAR(19)                COMMENT '对账结束时间',
--
    `ebank_number`          INT UNSIGNED            COMMENT 'ebank成功订单数量',
    `pay_center_number`     INT UNSIGNED            COMMENT '支付中心成功订单数量',
    `ebank_amount`          DECIMAL(30, 2)          COMMENT 'ebank成功订单总价',
    `pay_center_amount`     DECIMAL(30, 2)          COMMENT '支付中心成功订单总价',
--
    `create_time`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='对账结果';
