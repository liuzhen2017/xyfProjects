DROP TABLE IF EXISTS `cust_feedback`;
CREATE TABLE `cust_feedback`(
    `id`                INT UNSIGNED        PRIMARY KEY         AUTO_INCREMENT,
--
    `user_no`           BIGINT              NOT NULL            COMMENT '反馈用户',
    `content`           TEXT                NOT NULL            COMMENT '反馈内容',
    `content_type`      VARCHAR(20)         NOT NULL            COMMENT '反馈类型',
    `contact`           VARCHAR(50)                             COMMENT '联系方式',
    `contact_type`      VARCHAR(10)                             COMMENT '联系方式类型',
--
    `first_image`       VARCHAR(200)                            COMMENT '第一张图片',
    `second_image`      VARCHAR(200)                            COMMENT '第二张图片',
    `third_image`       VARCHAR(200)                            COMMENT '第三张图片',
    `fourth_image`      VARCHAR(200)                            COMMENT '第四张图片',
    `fifth_image`       VARCHAR(200)                            COMMENT '第五张图片',
    `sixth_image`       VARCHAR(200)                            COMMENT '第六张图片',
--
    `create_time`       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户反馈';
