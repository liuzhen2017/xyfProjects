/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:23:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_banner
-- ----------------------------
DROP TABLE IF EXISTS `pro_banner`;
CREATE TABLE `pro_banner` (
  `banner_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '广告id',
  `banner_name` varchar(100) NOT NULL DEFAULT '' COMMENT '广告名称',
  `img_url` varchar(150) NOT NULL DEFAULT '' COMMENT '图片路径',
  `pro_id` bigint(20) NOT NULL,
  `link_url` varchar(150) NOT NULL COMMENT '广告链接',
  `color` varchar(20) NOT NULL DEFAULT '' COMMENT '背景颜色',
  `start_date` date NOT NULL COMMENT '开始时间',
  `end_date` date NOT NULL COMMENT '结束时间',
  `sort_number` int(11) NOT NULL COMMENT '排序号',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`banner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='广告表';
