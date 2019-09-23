DROP TABLE IF EXISTS `ebank_log`;
CREATE TABLE `ebank_log`(
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `log_key`               VARCHAR(50)     NOT NULL        COMMENT '日志内容对应的key，与日志类型相关',
    `log_type`              VARCHAR(20)     NOT NULL        COMMENT '日志类型',
    `content`               TEXT            NOT NULL        COMMENT '日志内容',
--
    `create_time`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录系统日志';
