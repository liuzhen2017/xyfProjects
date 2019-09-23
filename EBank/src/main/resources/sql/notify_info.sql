CREATE TABLE `notify_info` (
  `id`                  int(11)             NOT NULL AUTO_INCREMENT,
  `notify_object`       varchar(100)                        COMMENT '发送对象',
  `notify_class`        varchar(500)                        COMMENT '通知类',
  `notify_method`       varchar(100)                        COMMENT '通知方法',
  `notify_parmat`       varchar(500)                        COMMENT '发送参数',
  `notify_status`       char(4)                             COMMENT '通知状态',
  `reply_count`         int(11)             DEFAULT '0'     COMMENT '重发次数',
  `err_msg`             varchar(200)                        COMMENT '错误信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8 COMMENT='通知信息->定时任务扫描该表'