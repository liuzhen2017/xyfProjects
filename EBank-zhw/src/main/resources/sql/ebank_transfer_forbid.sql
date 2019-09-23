DROP TABLE IF EXISTS `ebank_transfer_forbid`;
CREATE TABLE `ebank_transfer_forbid`(
  `id`                  INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
  `account_no`          char(20)            NOT NULL  UNIQUE    COMMENT '每一个用户的账号，记录转账，系统生成',
  `user_no`             BIGINT              NOT NULL  UNIQUE    COMMENT '用户编号',
  `user_phone`          CHAR(11)            NOT NULL UNIQUE     COMMENT '手机号'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='禁止合并转账的用户列表';
