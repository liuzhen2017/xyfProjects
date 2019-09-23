DROP TABLE IF EXISTS `ebank_account`;
CREATE TABLE `ebank_account` (
  `id`                  INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
  `account_no`          char(20)            NOT NULL  UNIQUE    COMMENT '每一个用户的账号，记录转账，系统生成',
  `user_no`             BIGINT              NOT NULL  UNIQUE    COMMENT '用户编号',
  `user_name`           varchar(200)                            COMMENT '用户姓名',
  `phone`               char(11)            NOT NULL            COMMENT '手机号',
  `id_card_no`          varchar(20)                             COMMENT '身份证号码',
  `account_name`        varchar(50)         NOT NULL            COMMENT '账号名',
  `account_type`        varchar(10)                             COMMENT '账户类型',
--
  `bank_card_no`        varchar(20)                             COMMENT '银行卡号',
  `bank_name`           varchar(20)                             COMMENT '银行名称',
  `bank_no`             varchar(20)                             COMMENT '银行编号',
  `branch_name`         varchar(20)                             COMMENT '分行名称',
  `branch_no`           varchar(20)                             COMMENT '分行行号',
--
  `province_name`       varchar(50)                             COMMENT '省份',
  `city_name`           varchar(50)                             COMMENT '城市',
--
  `balance`             decimal(30,2)       DEFAULT '0.00'      COMMENT '余额',
  `frozen_balance`      decimal(30,2)       DEFAULT '0.00'      COMMENT '冻结余额',
  `available_balance`   decimal(30,2)       DEFAULT '0.00'      COMMENT '可用余额',
--
  `business_status`     varchar(10)                             COMMENT '业务状态',
  `enable`              boolean           DEFAULT false         COMMENT '是否可用（可用=true，禁用=false）',
--
  `create_time`        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modify_time`        timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个转账账户的虚拟账号';
