/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:24:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_pay_type
-- ----------------------------
DROP TABLE IF EXISTS `pro_pay_type`;
CREATE TABLE `pro_pay_type` (
  `pro_id` bigint(20) NOT NULL COMMENT '商品id',
  `pay_type_id` bigint(20) NOT NULL COMMENT '支付类型id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `remarks` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`pro_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品支付类型关联表';
