DROP TABLE IF EXISTS `ebank_channel`;
CREATE TABLE `ebank_channel` (
	`id`	INT UNSIGNED		PRIMARY KEY 		AUTO_INCREMENT,
--
	`ebank_pay_type`	INT(3)			NOT NULL 		COMMENT 'ebank支付方式(尽可能和支付中心保持一致)',
	`client_type`		VARCHAR(20)		NOT NULL        COMMENT '客户端类型，app, h5, map-weixin',
	`ebank_pay_name`	VARCHAR(50)		NOT NULL 		COMMENT 'ebank支付方式名称',
--
	`pay_type`			INT(3)			NOT NULL        COMMENT '支付方式--支付中心, 2, 16, 32, 64, 128',
	`trade_type`		INT(3)          NOT NULL 		COMMENT '付款方式，1，2，4，8，16',
	`pay_name`			VARCHAR(10)     NOT NULL		COMMENT '支付方式名称, 云闪付，微信支付，支付宝，快捷支付，网银支付，',
	`trade_name`		VARCHAR(10)     NOT NULL		COMMENT '付款方式名称，二维码，jsapi, h5, app, 付款码',
--
	`enable`			BOOLEAN 		NOT NULL        COMMENT '是否有效',
	UNIQUE (`ebank_pay_type`, `client_type`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易渠道';

INSERT INTO ebank_channel (ebank_pay_type, client_type, ebank_pay_name, pay_type, trade_type, pay_name, trade_name, enable)
	values (2, 'CODE', '云闪付二维码支付', 2, 1, '云闪付', '二维码', true),
			(2, 'MP-WEIXIN', '云闪付JSAPI支付', 2, 2, '云闪付', 'JSAPI', true),
			(2, 'H5', '云闪付H5支付', 2, 4, '云闪付', 'H5', true),
			(2, 'APP', '云闪付APP支付', 2, 8, '云闪付', 'APP', true),
			(2, 'PAYMENT', '云闪付PAYMENT支付', 2, 16, '云闪付', '付款码', false),

			(16, 'CODE', '微信支付二维码支付', 16, 1, '微信支付', '二维码', true),
			(16, 'MP-WEIXIN', '微信支付JSAPI支付', 16, 2, '微信支付', 'JSAPI', true),
			(16, 'H5', '微信支付H5支付', 16, 4, '微信支付', 'H5', true),
			(16, 'APP', '微信支付APP支付', 16, 8, '微信支付', 'APP', true),
			(16, 'PAYMENT', '微信支付PAYMENT支付', 16, 16, '微信支付', '付款码', false),

			(32, 'CODE', '支付宝支付二维码支付', 32, 1, '支付宝支付', '二维码', true),
			(32, 'MP-WEIXIN', '支付宝支付JSAPI支付', 32, 2, '支付宝支付', 'JSAPI', true),
			(32, 'H5', '支付宝支付H5支付', 32, 4, '支付宝支付', 'H5', true),
			(32, 'APP', '支付宝支付APP支付', 32, 8, '支付宝支付', 'APP', true),
			(32, 'PAYMENT', '支付宝支付PAYMENT支付', 32, 16, '支付宝支付', '付款码', false),

			(64, 'CODE', '快捷支付二维码支付', 64, 1, '快捷支付', '二维码', true),
			(64, 'MP-WEIXIN', '快捷支付JSAPI支付', 64, 2, '快捷支付', 'JSAPI', true),
			(64, 'H5', '快捷支付H5支付', 64, 4, '快捷支付', 'H5', true),
			(64, 'APP', '快捷支付APP支付', 64, 8, '快捷支付', 'APP', true),
			(64, 'PAYMENT', '快捷支付PAYMENT支付', 64, 16, '快捷支付', '付款码', false),

			(128, 'CODE', '网银支付二维码支付', 128, 1, '网银支付', '二维码', true),
			(128, 'MP-WEIXIN', '网银支付JSAPI支付', 128, 2, '网银支付', 'JSAPI', true),
			(128, 'H5', '网银支付H5支付', 128, 4, '网银支付', 'H5', true),
			(128, 'APP', '网银支付APP支付', 128, 8, '网银支付', 'APP', true),
			(128, 'PAYMENT', '网银支付PAYMENT支付', 128, 16, '网银支付', '付款码', false),

			(256, 'CODE', '代付二维码支付', 16, 1, '微信支付', '二维码', true),
			(256, 'MP-WEIXIN', '代付JSAPI支付', 16, 1, '微信支付', '二维码', true),
			(256, 'H5', '代付H5支付', 16, 1, '微信支付', '二维码', true),
			(256, 'APP', '代付APP5支付', 16, 1, '微信支付', '二维码', true),
			(256, 'PAYMENT', '代付PAYMENT支付', 16, 1, '微信支付', '二维码', true),

			(512, 'CODE', '钱包支付二维码支付', -1, 1, '钱包支付', '二维码', true),
			(512, 'MP-WEIXIN', '钱包支付JSAPI支付', -1, 2, '钱包支付', 'JSAPI', true),
			(512, 'H5', '钱包支付H5', -1, 4, '钱包支付', 'H5', true),
			(512, 'APP', '钱包支付APP', -1, 8, '钱包支付', 'APP', true),
			(512, 'PAYMENT', '钱包支付PAYMENT', -1, 16, '钱包支付', '付款码', true),

			(550, 'CODE', '代付支付宝支付', 32, 1, '支付宝', '二维码', true),
			(550, 'MP-WEIXIN', '代付支付宝支付', 32, 1, '支付宝', '二维码', true),
			(550, 'H5', '代付支付宝支付', 32, 1, '支付宝', '二维码', true),
			(550, 'APP', '代付支付宝支付', 32, 1, '支付宝', '二维码', true),
			(550, 'PAYMENT', '代付支付宝支付', 32, 1, '支付宝', '二维码', true);
