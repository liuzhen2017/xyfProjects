/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:22:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pay_type
-- ----------------------------
DROP TABLE IF EXISTS `pay_type`;
CREATE TABLE `pay_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '支付类型id',
  `pay_type` varchar(30) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '支付类型,16微信,32支付宝,64快捷支付,128网银支付,拼接字符串',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态,0可以,1禁用,2删除',
  `remarks` varchar(50) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '支付方式文字注释',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COMMENT='支付类型表';
