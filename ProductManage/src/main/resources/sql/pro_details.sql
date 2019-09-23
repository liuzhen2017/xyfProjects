/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:23:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_details
-- ----------------------------
DROP TABLE IF EXISTS `pro_details`;
CREATE TABLE `pro_details` (
  `pro_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `specs` varchar(2000) DEFAULT NULL COMMENT '商品规格，采用json格式保存',
  `details` varchar(10000) DEFAULT NULL COMMENT '''商品详情，采用html格式保存',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态，启用=0,禁用=1,删除=2',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`pro_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000023 DEFAULT CHARSET=utf8 COMMENT='商品参数详情表';
