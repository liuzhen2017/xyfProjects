DROP TABLE IF EXISTS `infocenter_push_template`;
CREATE TABLE `infocenter_push_template`(
    `id`                    INT UNSIGNED        PRIMARY KEY             AUTO_INCREMENT,
--
    `template_name`         CHAR(3)             NOT NULL    UNIQUE      COMMENT '模板名称唯一标识',
    `template_type`         VARCHAR(20)         NOT NULL                COMMENT '推送类型，短信shortMessage/极光pushMessage/微信wechatMessage',
--
    `title`                 VARCHAR(200)        NOT NULL                COMMENT '推送标题',
    `content`               VARCHAR(200)        NOT NULL                COMMENT '推送内容模板',
    `enable`                BOOLEAN             NOT NULL DEFAULT TRUE   COMMENT '是否可用',
--
    `remark`                VARCHAR(200)                                COMMENT '模板说明',
    `create_time`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
    `last_modify_time`  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='推送模板';

INSERT INTO `infocenter_push_template` (`template_name`, `template_type`, `title`, `content`, `enable`)
    VALUES ("001", "pushMessage", "转让成功", "您成功转让%s张优惠券，转让金额%s元, 已进入银行待结算，请您注意查收", true),
    ("002", "pushMessage", "飞    单", "您的粉丝%s成功转让了%s张券，转让收入%s元 ，您已错过了好几个小目标~", true),
    ("003", "pushMessage", "订单发货", "订单[%s]运送中，正在快马加鞭赶完下一站请您耐心等待", true),
    ("004", "pushMessage", "订单支付成功", "您的订单支付成功，商家已开始处理.", true),
    ("005", "pushMessage", "欢迎加入星云福大家庭", "欢迎加入星云福大家庭，星仔将引领你玩转淘金圈，共创知识和财富~", true),
    ("006", "pushMessage", "转让成功", "您成功转让%s张优惠券，转让金额%s元, 已进入银行待结算，请您注意查收", true),
    ("007", "pushMessage", "订单支付成功", "订单支付成功：您的订单支付成功，厂家已开始出库中.请您耐心等待～", true),
    ("008", "pushMessage", "优惠券已自动退回", "亲，您于%s提交的订单因长时间未完成支付，系统已自动取消，订单所使用的优惠券已自动退回您的卡券里面哟～", true);

DELETE FROM `infocenter_push_template` where `template_name`='009';
INSERT INTO `infocenter_push_template` (`template_name`, `template_type`, `title`, `content`, `remark`)
    VALUES ("009", "others", "对账结果通知",
        "%s至%s，代付中心订单处理，共%s单，成功%s单，失败%s单，待人工处理%s单（单笔超额），人工已处理%s单，支出总金额%s元。APP数据中心订单处理，成功%s单，支出总金额%s元。", "短信推送内容");
INSERT INTO `infocenter_push_template` (`template_name`, `template_type`, `title`, `content`, `remark`)
    VALUES ("010", "others", "供应链注册通知",
        "恭喜您:%s供应链申请完毕,请访问%s登录星云福供应链平台,用户名为%s,密码%s", "短信推送内容");
