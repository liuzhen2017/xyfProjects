DROP TABLE IF EXISTS `cust_log`;
CREATE TABLE `cust_log`(
    `id`            INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `user_No`       BIGINT                          COMMENT '日志所有人',
--
    `log_type`      VARCHAR(20)                     COMMENT '日志类型',
    `user_ip`       VARCHAR(20)                     COMMENT '登陆人ip',
    `details`       VARCHAR(200)                    COMMENT '日志内容',
--
    `create_time`   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户管理日志表';