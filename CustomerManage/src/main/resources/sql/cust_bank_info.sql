DROP TABLE IF EXISTS `cust_bank_info`;
CREATE TABLE `cust_bank_info`(
    `id`                    INT UNSIGNED        PRIMARY KEY         AUTO_INCREMENT,
--
    `cname`                 VARCHAR(20)         NOT NULL            COMMENT '银行中文名',
    `ename`                 VARCHAR(50)         NOT NULL            COMMENT '银行英文名',
    `bank_code`             VARCHAR(20)         NOT NULL            COMMENT '银行机构代码',
    `create_time`           VARCHAR(24)                             COMMENT '创建时间',
--
    `url`                   VARCHAR(200)                            COMMENT '银行主页',
    `logo`                  VARCHAR(200)                            COMMENT '银行logo',
    `status`                TINYINT                                 COMMENT '状态',
--
    `remark`                VARCHAR(200)                            COMMENT '备注',
    `rank`                  TINYINT                                 COMMENT '排序权重'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='银行信息表';
INSERT INTO cust_bank_info(cname, ename, bank_code, status, rank)
			values ('邮政储蓄银行', '邮政储蓄银行', 'PSBC', 1, 1),
			('中国银行', '中国银行', 'BOC', 1, 1),
			('工商银行', '工商银行', 'ICBC', 1, 1),
			('农业银行', '农业银行', 'ABC', 1, 1),
			('建设银行', '建设银行', 'CCB', 1, 1),
			('招商银行', '招商银行', 'CMB', 1, 1),
			('交通银行', '交通银行', 'COMM', 1, 1),
			('广发银行', '广发银行', 'CGB', 1, 1),
			('民生银行', '民生银行', 'CMBC', 1, 1),
			('浦发银行', '浦发银行', 'SPDB', 1, 1),
			('华夏银行', '华夏银行', 'HXB', 1, 1),
			('兴业银行', '兴业银行', 'CIB', 1, 1),
			('光大银行', '光大银行', 'CEB', 1, 1),
			('平安银行', '平安银行', 'PINGANBK', 1, 1),
			('中信银行', '中信银行', 'CITIC', 1, 1),
			('北京银行', '北京银行', 'BOBJ', 1, 1),
			('南京银行', '南京银行', 'NJCB', 1, 1),
			('杭州银行', '杭州银行', 'HZCB', 1, 1),
			('浙商银行', '浙商银行', 'ZHESHANGB', 1, 1),
			('渤海银行', '渤海银行', 'CBHB', 1, 1),
			('上海银行', '上海银行', 'BKSH', 1, 1),
			('宁波银行', '宁波银行', 'NBB', 1, 1),
			('东莞银行', '东莞银行', 'DGB', 1, 1);