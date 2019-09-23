DROP TABLE IF EXISTS `cust_user_extension`;
CREATE TABLE `cust_user_extension`(
    `id`                INT UNSIGNED        PRIMARY KEY         AUTO_INCREMENT,
--
    `user_no`           BIGINT              NOT NULL UNIQUE     COMMENT '用户编号',
    `meal_nu`           INT(7)                                  COMMENT '用户购买套餐数量--数据来自订单模块',
    `super_user_no`     BIGINT                                  COMMENT '上级用户编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户扩展表';

ALTER TABLE `cust_user_extension` ADD `super_user_no` BIGINT COMMENT '上级用户编号';
