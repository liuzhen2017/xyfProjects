DROP TABLE IF EXISTS `cust_client_info`;
CREATE TABLE `cust_client_info`(
    `id`                    INT UNSIGNED        PRIMARY KEY         AUTO_INCREMENT,
--
    `user_no`                BIGINT                                  COMMENT '用户id',
    `client_version`         VARCHAR(20)                             COMMENT '客户端版本',
    `client_system`          VARCHAR(50)                             COMMENT '客户端系统版本',
    `source`                 TINYINT                                 COMMENT 'APP来源',
    `server_version`         VARCHAR(10)                             COMMENT '服务器系统版本',
--
    `create_time`            TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登陆设备信息表';