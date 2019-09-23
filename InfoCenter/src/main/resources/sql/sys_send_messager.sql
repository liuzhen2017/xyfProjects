DROP TABLE IF EXISTS `sys_send_messager`;
CREATE TABLE `sys_send_messager` (
  `id`                  int(11)             NOT NULL PRIMARY KEY    AUTO_INCREMENT,
  `recv_object`         varchar(200)                                COMMENT '接受者',
  `send_content`        varchar(200)                                COMMENT '接收内容',
  `send_type`           int(3)                                      COMMENT '发送类型,1短信2微信3APP站内信',
  `send_status`         int(3),
  `template_no`         varchar(50)                                 COMMENT '模板ID',
  `client_version`      varchar(20)                                 COMMENT '客户端版本',
  `client_system`       varchar(20)                                 COMMENT '客户端系统',
  `client_source`       varchar(100)                                COMMENT '客户端来源',
  `device_id`           varchar(100)                                COMMENT '设备ID',
  `token`               varchar(200)                                COMMENT 'token',
  `created_date`        datetime,
  `created_by`          varchar(50),
  `updated_date`        datetime,
  `updated_by`          varchar(50),
  `err_msg`             varchar(200)                                COMMENT '错误信息',
  `user_no`             varchar(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
