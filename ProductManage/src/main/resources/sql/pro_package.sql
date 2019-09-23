/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:24:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_package
-- ----------------------------
DROP TABLE IF EXISTS `pro_package`;
CREATE TABLE `pro_package` (
  `package_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '套餐id',
  `pro_id` bigint(20) NOT NULL COMMENT '商品id',
  `package_name` varchar(255) NOT NULL DEFAULT '' COMMENT '套餐名称',
  `package_price` decimal(10,2) NOT NULL COMMENT '套餐价格',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态，启用=0,禁用=1,删除=2',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='套餐信息表';
