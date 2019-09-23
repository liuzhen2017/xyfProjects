DROP TABLE IF EXISTS `ebank_account_extension`;
CREATE TABLE `ebank_account_extension` (
  `id`                  INT UNSIGNED        PRIMARY KEY         AUTO_INCREMENT,
--
  `account_no`          char(20)            NOT NULL  UNIQUE    COMMENT '每一个用户的账号，记录转账，系统生成',
  `user_phone`          char(11)                                COMMENT '当前用户的手机号',
  `super_account_no`    char(20)                                COMMENT '上级用户账号',
  `super_phone`         char(11)                                COMMENT '上级用户手机号'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟账号扩展';