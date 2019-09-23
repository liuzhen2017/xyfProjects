DROP TABLE IF EXISTS `ebank_notice`;
CREATE TABLE `ebank_notice` (
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `user_name`             VARCHAR(20)     NOT NULL        COMMENT '被通知人姓名',
    `phone`                 CHAR(11)        NOT NULL        COMMENT '被通知人手机号',
    `email`                 VARCHAR(50)                     COMMENT '被通知人邮箱',
--
    `phone_enable`          BOOLEAN NOT NULL DEFAULT true COMMENT '是否短信推送',
    `email_enable`          BOOLEAN NOT NULL DEFAULT false COMMENT '是否邮件推送',
--
    `enable`                BOOLEAN NOT NULL DEFAULT true COMMENT '是否推送',
--
    `remark`                VARCHAR(100)                    COMMENT '说明'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每日对账被通知人';

INSERT INTO `ebank_notice` (`user_name`, `phone`)
    VALUES ("姜", "15327876865"),
    ("刘", "15818660647");
