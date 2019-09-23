/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:23:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_freight
-- ----------------------------
DROP TABLE IF EXISTS `pro_freight`;
CREATE TABLE `pro_freight` (
  `freight_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '邮费模板id',
  `store_id` bigint(20) NOT NULL COMMENT '店铺id',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否包邮,0包邮,1不包邮,2按地区包邮,3按金额包邮,4按件数包邮,',
  `conditions` varchar(100) DEFAULT NULL COMMENT '包邮条件',
  `modes` varchar(300) DEFAULT NULL COMMENT '计算邮费,json格式保存',
  `default_postage` decimal(10,0) NOT NULL DEFAULT '0' COMMENT '默认邮费',
  `remarks` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态,0启用,1禁用,2删除',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`freight_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='店铺邮费模板表';
