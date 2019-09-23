DROP TABLE IF EXISTS `ebank_product_record`;
CREATE TABLE `ebank_product_record`(
    `id`                    INT UNSIGNED    PRIMARY KEY     AUTO_INCREMENT,
--
    `ebank_order_no`        CHAR(32)        NOT NULL UNIQUE     COMMENT 'ebank支付订单号，购买订单号可能存在多个支付订单号',
    `order_no`              VARCHAR(30)     NOT NULL            COMMENT '购买订单编号，其它服务的订单编号',
    `sys_order_no`          VARCHAR(50)                         COMMENT '支付中心单号',
    `account_no`            CHAR(32)        NOT NULL            COMMENT '每一个用户的账号，记录转账，系统生成',
    `product_type`          VARCHAR(10)     NOT NULL            COMMENT '产品类型，00商品，01套餐',
--
    `amount`                DECIMAL(30,2)   NOT NULL            COMMENT '付款金额',
    `pay_type`              INT(3)          NOT NULL            COMMENT '支付类型, 2云闪付，16微信支付，32支付宝，64快捷支付，128网银支付',
    `trade_type`            INT(3)          NOT NULL            COMMENT '付款方式，1二维码，2jsapi，4h5，8app，16付款码',
    `return_url`            VARCHAR(200)                        COMMENT '调用端url，只记录，不处理',
--
    `server_name`           VARCHAR(20)                         COMMENT '发起支付请求的服务',
    `status`                VARCHAR(20)                         COMMENT '支付状态，apply_process申请支付中，apply_success申请支付成功，apply_failed申请支付失败, pay_failed支付失败，pay_success支付成功',
--
    `order_type`            INT(3)                              COMMENT '订单模块请求参数，原样返回',
    `client_ip`             VARCHAR(50)                         COMMENT '客户端IP',
    `subject`               VARCHAR(50)                         COMMENT '交易主体',
--
    `create_time`           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `last_modify_time`      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品购买付款记录，产品包括商品/套餐';
