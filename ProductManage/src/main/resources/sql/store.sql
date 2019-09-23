/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:26:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `store_id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '商家id',
  `store_name` varchar(50) NOT NULL DEFAULT '' COMMENT '商家名称',
  `owner_id` int(10) NOT NULL DEFAULT '0' COMMENT '所有者,关联user表的id',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态，启用=0,禁用=1,删除=2',
  `tel` varchar(20) DEFAULT NULL COMMENT '客服电话',
  `store_type` int(10) NOT NULL DEFAULT '0' COMMENT '''商户类型（商城商户=1,联盟商户=2,系统商户=100）',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='商家信息表';
