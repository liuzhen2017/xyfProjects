DROP TABLE IF EXISTS `cust_referral_code_special`;
CREATE TABLE `cust_referral_code_special`(
    `id`                    INT UNSIGNED        PRIMARY KEY         AUTO_INCREMENT,
--
    `referral_code`         CHAR(6)                                 COMMENT '内部保留的推荐码',
    `used`                  BOOLEAN             DEFAULT false       COMMENT '是否已被使用',
--
    `remark`                VARCHAR(200)                            COMMENT '说明'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行卡表'; 

-- 由于是分布式服务，生成推荐码时需要保证唯一，服务端使用分布式锁+N进制计算的方式保证唯一
-- 所以该表中有一行数据保存N进制的下表