DROP TABLE IF EXISTS `infocenter_push_device`;
CREATE TABLE `infocenter_push_device`(
    `id`            INT UNSIGNED        PRIMARY KEY             AUTO_INCREMENT,
--
    `user_no`       BIGINT              NOT NULL UNIQUE         COMMENT '用户编号',
    `phone`         CHAR(11)                                    COMMENT '手机号，用于短信推送',
    `token`         CHAR(32)                                    COMMENT 'token, 推送时使用',
    `client_id`     CHAR(32)                                    COMMENT 'clientId, 推送时使用',
    `phone_type`    VARCHAR(10)                                 COMMENT '手机类型，ios/android',
--
    `create_time`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='可推送设备信息表';
