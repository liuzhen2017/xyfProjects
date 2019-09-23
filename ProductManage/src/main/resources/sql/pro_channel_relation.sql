/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:23:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_channel_relation
-- ----------------------------
DROP TABLE IF EXISTS `pro_channel_relation`;
CREATE TABLE `pro_channel_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pro_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品id',
  `channel_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '类目id',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态，启用=0,禁用=1,删除=2',
  `remarks` varchar(50) DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4375 DEFAULT CHARSET=utf8 COMMENT='商品分类关系表';
