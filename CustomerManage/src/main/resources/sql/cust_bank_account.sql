DROP TABLE IF EXISTS `cust_bank_account`;
CREATE TABLE `cust_bank_account`(
    `id`                    INT UNSIGNED    PRIMARY KEY         AUTO_INCREMENT,
--
    `user_no`               BIGINT          NOT NULL            COMMENT '银行卡所有人',
    `bank_id`               INT UNSIGNED    NOT NULL            COMMENT '银行卡所属银行',
--
    `account_name`          VARCHAR(20)     NOT NULL            COMMENT '开户人姓名',
    `account_no`            VARCHAR(20)     NOT NULL            COMMENT '银行卡号',
    `card_no`               VARCHAR(20)     NOT NULL            COMMENT '开户人身份证号',
    `default_card`          BOOLEAN         DEFAULT false       COMMENT '是否是默认卡',
    `phone`                 CHAR(11)        NOT NULL            COMMENT '手机号',
--
    `bank_name`             VARCHAR(20)                         COMMENT '银行名称',
    `bank_code`             VARCHAR(20)                         COMMENT '银行机构代码',
--
    `province_id`           INT UNSIGNED                        COMMENT '开户省份id',
    `city_id`               INT UNSIGNED                        COMMENT '开户城市id',
    `region_id`             INT UNSIGNED                        COMMENT '开户区id',
    `province_name`         VARCHAR(50)                         COMMENT '开户省份',
    `city_name`             VARCHAR(50)                         COMMENT '城市名称',
    `region_name`           VARCHAR(50)                         COMMENT '开户区名称',
    `address`               VARCHAR(100)                        COMMENT '具体地址',
--
    `account_type`          TINYINT         DEFAULT '1'         COMMENT '帐号类型,个人账号=1，企业账号=2"',
    `card_type`             VARCHAR(20)                         COMMENT '银行卡类型',
    `card_name`             VARCHAR(20)                         COMMENT '银行卡名称',
    `status`                TINYINT                             COMMENT '银行卡状态,删除=0，审核通过=1',
--
    `branch_no`             VARCHAR(20)                         COMMENT '银行联行号',
    `branch_name`           VARCHAR(20)                         COMMENT '银行联行名',
    `branch_bank`           VARCHAR(50)                         COMMENT '支行信息',
    `image_fullpath`        VARCHAR(200)                        COMMENT '手持银行卡图像的完整路径',
--
    `remark`                VARCHAR(200)                        COMMENT '备注',
    `create_time`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行卡表'; 