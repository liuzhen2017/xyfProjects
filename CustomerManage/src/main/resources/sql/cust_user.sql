DROP TABLE IF EXISTS `cust_user`;
CREATE TABLE `cust_user` (
    `id`                INT UNSIGNED        PRIMARY KEY         AUTO_INCREMENT,
--
    `user_no`           BIGINT              NOT NULL UNIQUE     COMMENT '用户编号',
    `phone`             CHAR(11)            NOT NULL UNIQUE     COMMENT '手机号',
    `password`          CHAR(32)                                COMMENT '密码',
    `pay_password`      CHAR(32)                                COMMENT '支付密码',
    `user_code`         VARCHAR(6)          NOT NULL UNIQUE     COMMENT '当前用户的推荐码，用于分享给别人，忽略大小写',
--
    `nick_name`         VARCHAR(20)         NOT NULL            COMMENT '昵称',
    `name`              VARCHAR(20)                             COMMENT '真实姓名',
    `sex`               TINYINT                                 COMMENT '性别, 0女，1男',
    `photo_url`         VARCHAR(200)                            COMMENT '用户头像',
--
    `status`            TINYINT                                 COMMENT '用户状态，0未激活，1已激活，2已注销，3已封锁',
    `level`             TINYINT                                 COMMENT '用户级别',
--
    `referrer_no`       BIGINT                                  COMMENT '推荐人id',
    `referral_code`     VARCHAR(20)                             COMMENT '推荐人的推荐码',
--
    `card_no`           VARCHAR(20)                             COMMENT '证件号',
    `card_type`         VARCHAR(10)                             COMMENT '证件类型',
    `auth_status`       TINYINT              DEFAULT '0'        COMMENT '证件状态',
    `auth_time`         TIMESTAMP                               COMMENT '认证成功时间',
--
    `wechat`            VARCHAR(50)                             COMMENT '微信号码',
    `open_id`           VARCHAR(50)                             COMMENT '对接微信等第三方登陆',
    `union_id`          VARCHAR(50)                             COMMENT '对接微信等第三方登陆',
    `data_source`       VARCHAR(10)                             COMMENT '用户来源',
--
    `remark`            VARCHAR(200)                            COMMENT '备注信息',
--
    `create_time`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
    INDEX (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

alter table cust_user modify column password char(32) comment '登陆密码';