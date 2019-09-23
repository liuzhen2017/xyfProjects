/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:24:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_sku
-- ----------------------------
DROP TABLE IF EXISTS `pro_sku`;
CREATE TABLE `pro_sku` (
  `sku_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sku_no` varchar(50) DEFAULT ' ' COMMENT 'sku唯一编码',
  `group_no` varchar(50) NOT NULL DEFAULT ' ' COMMENT '分组编码，如：属性：属性值,属性：属性值',
  `pro_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品编号',
  `stock` int(10) NOT NULL DEFAULT '0' COMMENT '当前库存',
  `sell_stock` int(10) NOT NULL DEFAULT '0' COMMENT '已出售量',
  `img_url` varchar(100) NOT NULL COMMENT 'sku图片,如果为null,显示商品默认图',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '出售价格（单位：元）',
  `integral_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '抵扣积分',
  `market_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '市场价格，单位：元',
  `min_sell_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '最低销售价格,高于成本价',
  `cost_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '成本价格，单位：元',
  `pro_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品类型,0普通商品,1秒杀商品,2套餐商品',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态，启用=0,禁用=1,删除=2',
  `data_version` varchar(50) NOT NULL DEFAULT '0' COMMENT '数据版本，采用时间戳',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT ' 创建时间',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=274 DEFAULT CHARSET=utf8 COMMENT='sku——商品库存量单位信息表';
