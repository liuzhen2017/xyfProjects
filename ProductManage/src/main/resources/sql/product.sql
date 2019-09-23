/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.56
Source Server Version : 50726
Source Host           : 192.168.1.56:3306
Source Database       : ProductManage

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-07-30 21:26:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pro_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `pro_no` varchar(50) NOT NULL DEFAULT '' COMMENT '商品编码',
  `pro_name` varchar(150) NOT NULL DEFAULT '' COMMENT '商品名称',
  `pro_title` varchar(150) CHARACTER SET utf32 DEFAULT NULL COMMENT '商品标题',
  `buyer_reading` varchar(500) DEFAULT NULL COMMENT '购物须知',
  `freight_id` int(11) NOT NULL DEFAULT '1' COMMENT '邮费模板 指定店铺之后可以选择店铺下的邮费模板',
  `store_id` int(10) NOT NULL DEFAULT '0' COMMENT '店铺id，关联store.STORE_ID',
  `kill_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '标识秒杀状态,0未开始秒杀,1开始秒杀,为1时允许在秒杀模块购买',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态，上架=0,下架=1,删除=2,待审核99',
  `unit` varchar(10) NOT NULL DEFAULT '0' COMMENT '商品单位',
  `weight` double NOT NULL DEFAULT '0' COMMENT '重量,千克',
  `source` int(10) NOT NULL DEFAULT '0' COMMENT '商品来源,0其他,1京东,2怡亚通',
  `coupon_type` varchar(50) NOT NULL DEFAULT '0' COMMENT '支持使用的券,拼接券的id,0不支持使用券',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` varchar(100) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`pro_id`),
  UNIQUE KEY `pro_no` (`pro_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1004326 DEFAULT CHARSET=utf8 COMMENT='商品信息表';
