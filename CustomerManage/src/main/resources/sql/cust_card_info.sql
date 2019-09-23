DROP TABLE IF EXISTS `cust_card_info`;
CREATE TABLE `cust_card_info`(
    `id`            INT UNSIGNED        PRIMARY KEY             AUTO_INCREMENT,
--
    `name`          VARCHAR(20)                                 COMMENT '证件人姓名',
    `card_no`       VARCHAR(20)         UNIQUE                  COMMENT '证件号',
    `birthday`      VARCHAR(30)                                 COMMENT '出生日期',
    `sex`           TINYINT                                     COMMENT '性别',
    `nation`        VARCHAR(20)                                 COMMENT '民族',
--
    `begin_time`    CHAR(10)                                    COMMENT '有效期开始时间',
    `end_time`      CHAR(10)                                    COMMENT '有效期结束时间',
    `issuing`       VARCHAR(50)                                 COMMENT '签发机关',
    `status`        TINYINT             DEFAULT '1'             COMMENT '状态，0禁用，1正常，2删除',
--
    `province`      VARCHAR(50)                                 COMMENT '省',
    `city`          VARCHAR(50)                                 COMMENT '市',
    `prefecture`    VARCHAR(50)                                 COMMENT '区',
    `address`       VARCHAR(50)                                 COMMENT '详细地址',
    `front_photo`   VARCHAR(200)                                COMMENT '身份证正面',
    `reverse_photo` VARCHAR(200)                                COMMENT '身份证反面',
--
    `remark`        VARCHAR(200)                                COMMENT '备注',
    `create_time`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='证件信息表';