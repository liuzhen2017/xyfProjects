/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.31 : Database - otree
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`otree` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `otree`;

/*Table structure for table `order_tree` */

DROP TABLE IF EXISTS `order_tree`;

CREATE TABLE `order_tree` (
  `main_order_no` VARCHAR(50) DEFAULT NULL COMMENT '主订单编号',
  `node_no` VARCHAR(50) NOT NULL COMMENT '订单树节点',
  `user_no` VARCHAR(50) DEFAULT NULL COMMENT '当前节点的用户编号',
  `updated_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `created_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `enables` TINYINT(4) DEFAULT NULL COMMENT '节点是否仍有效，1有效，0无效',
  PRIMARY KEY (`node_no`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `pay_log` */

DROP TABLE IF EXISTS `pay_log`;

CREATE TABLE `pay_log` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_no` VARCHAR(50) DEFAULT NULL COMMENT '提现人的编号',
  `pay_no` VARCHAR(50) DEFAULT NULL COMMENT '订单树自定义订单号，节点编号_第几次提现',
  `result_json` VARCHAR(110) DEFAULT NULL COMMENT '返回结果',
  `main_order_no` VARCHAR(50) DEFAULT NULL COMMENT '主订单编号',
  `is_submit` TINYINT(4) DEFAULT NULL COMMENT '是否提交转让，推广大使默认是提交',
  `source_user` VARCHAR(50) DEFAULT NULL COMMENT '收益来源人-即购买套餐人',
  `updated_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `created_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `tree_no` */

DROP TABLE IF EXISTS `tree_no`;

CREATE TABLE `tree_no` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `node_no` VARCHAR(50) NOT NULL COMMENT '节点编号',
  `right_leaf_no` VARCHAR(50) DEFAULT NULL COMMENT '左子节点编号',
  `left_leaf_no` VARCHAR(50) DEFAULT NULL COMMENT '右子节点编号',
  `parent_leaf_no` VARCHAR(50) DEFAULT NULL COMMENT '父子节点编号',
  `valid_leaf_count` INT(11) NOT NULL DEFAULT '6' COMMENT '剩余可用节点数',
  `submit_date` VARCHAR(50) DEFAULT NULL COMMENT '生成节点的时间戳',
  `updated_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `created_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `enables` TINYINT(4) DEFAULT NULL COMMENT '节点是否有效',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user_package_order` */

DROP TABLE IF EXISTS `user_package_order`;

CREATE TABLE `user_package_order` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '表id',
  `user_no` VARCHAR(50) NOT NULL COMMENT '用户id',
  `main_order_no` VARCHAR(50) DEFAULT NULL COMMENT '主订单号：推广大使会有多个',
  `count` INT(11) NOT NULL DEFAULT '1' COMMENT '订单数量',
  `vaild_count` INT(11) DEFAULT NULL COMMENT '剩余有效数量',
  `user_type` VARCHAR(10) DEFAULT NULL COMMENT '用户类型',
  `enables` TINYINT(4) DEFAULT NULL COMMENT '数据是否启用，1启用，0是禁用',
  `is_submit` TINYINT(4) DEFAULT NULL COMMENT '是否提交转让，0已提交，1未提交',
  `updated_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `created_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user_push` */

DROP TABLE IF EXISTS `user_push`;

CREATE TABLE `user_push` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增',
  `user_no` VARCHAR(60) DEFAULT NULL COMMENT '用户id，唯一，用户管理模块指定',
  `pusher_no_linked` VARCHAR(120) DEFAULT NULL COMMENT '推荐关系链，存一个推荐关系数组',
  `user_type` VARCHAR(10) DEFAULT NULL COMMENT '用户权限级别，普通vip',
  `created_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `rule_config`;

CREATE TABLE `rule_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bean_name` varchar(30) DEFAULT NULL,
  `enables` tinyint(4) DEFAULT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `updated_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `created_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `rule_config` */

insert  into `rule_config`(`id`,`bean_name`,`enables`,`remark`) values (1,'lessAllRuleImpl',1,'6缺6'),(2,'lessOneRuleImpl',0,'6缺1,没有的情况下找进行6缺6'),(3,'xyfRootRule',0,'平台优先规则，没有推荐人直接挂到平台');

