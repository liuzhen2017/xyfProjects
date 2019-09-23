/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:23:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pro_channel
-- ----------------------------
DROP TABLE IF EXISTS `pro_channel`;
CREATE TABLE `pro_channel` (
  `channel_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类目id',
  `channel_name` varchar(50) NOT NULL DEFAULT '' COMMENT '类目名称',
  `channel_type_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '类目类型',
  `father_id` bigint(20) DEFAULT '0' COMMENT '父类目id',
  `sort_number` bigint(20) NOT NULL DEFAULT '10000' COMMENT '排序号',
  `banner_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '广告id',
  `image_url` varchar(300) DEFAULT NULL COMMENT '图片地址，保存绝对路径',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态，启用=0,禁用=1,删除=2',
  `remarks` varchar(50) DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=111129 DEFAULT CHARSET=utf8 COMMENT='类目信息表';
