DROP TABLE IF EXISTS `ebank_festival`;
CREATE TABLE `ebank_festival`(
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `years`                 INT(4)                          COMMENT '年',
    `months`                 INT(2)                          COMMENT '月',
    `days`                  INT(2)                          COMMENT '日',
--
    `remark`                VARCHAR(50)                     COMMENT '说明',
    UNIQUE (`years`, `months`, `days`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='节假日';

INSERT INTO `ebank_festival` (`years`, `months`, `days`, `remark`)
    values (2019, 9, 13, '中秋'),(2019, 9, 14, '中秋'),(2019, 9, 15, '中秋'),
    (2019, 10, 1, '国庆'),(2019, 10, 2, '国庆'),(2019, 10, 3, '国庆'),(2019, 10, 4, '国庆'),
    (2019, 10, 5, '国庆'),(2019, 10, 6, '国庆'),(2019, 10, 7, '国庆');
