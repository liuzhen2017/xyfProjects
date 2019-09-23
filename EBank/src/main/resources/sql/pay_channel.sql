CREATE TABLE `pay_channel` (
  `id`              int(30)             NOT NULL AUTO_INCREMENT     COMMENT 'ID',
  `pay_key`         int(11)                                         COMMENT 'key',
  `pay_name`        varchar(50)         CHARACTER SET utf8          COMMENT '支护名称',
  `open_name`       varchar(200)        CHARACTER SET utf8          COMMENT '名称',
  `open_key`        int(10)             DEFAULT '0'                 COMMENT 'key',
  `enables`         int(1)              DEFAULT '1'                 COMMENT '是否有效1有效,0无效',
  `order_bys`       int(1)                                          COMMENT '排序',
  `channles`        char(20)                                        COMMENT '渠道',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='交易渠道维护'