DROP TABLE IF EXISTS `cust_shipping_address`;
CREATE TABLE `cust_shipping_address`(
    `id`                    INT UNSIGNED        PRIMARY KEY         AUTO_INCREMENT,
--
    `user_no`               BIGINT              NOT NULL            COMMENT '收货地址所有人',
    `default_address`       BOOLEAN             DEFAULT false       COMMENT '是否是默认地址',
--
    `phone`                 CHAR(11)                                COMMENT '收件人手机号',
    `name`                  VARCHAR(20)                             COMMENT '收件人手机号',
    `region_id`             INT UNSIGNED                            COMMENT '区id',
    `address`               VARCHAR(50)                             COMMENT '详细地址',
    `post_code`             VARCHAR(20)                             COMMENT '邮政编码',
--
    `create_time`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货地址表';