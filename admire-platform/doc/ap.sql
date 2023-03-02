/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.40 : Database - admire-platform
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`admire-platform` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_german2_ci */;

USE `admire-platform`;

/*Table structure for table `ap_commodity` */

DROP TABLE IF EXISTS `ap_commodity`;

CREATE TABLE `ap_commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '名称',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `type` int(1) NOT NULL COMMENT '类型（1：收费，2：免费）',
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  `description_url` varchar(500) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '描述链接',
  `content` text COLLATE utf8mb4_german2_ci NOT NULL COMMENT '付费内容',
  `buy_number` int(4) DEFAULT '0' COMMENT '购买人数',
  `look_number` int(4) DEFAULT '0' COMMENT '查看人数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `updater` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

/*Data for the table `ap_commodity` */

/*Table structure for table `ap_order` */

DROP TABLE IF EXISTS `ap_order`;

CREATE TABLE `ap_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `commodity_id` bigint(20) NOT NULL COMMENT '商品id',
  `order_number` varchar(32) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '订单编号',
  `callback_url` varchar(500) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '回调url',
  `money` decimal(10,2) NOT NULL COMMENT '金额',
  `pay_type` int(1) NOT NULL COMMENT '支付渠道（1：支付宝、2：微信）',
  `pay_status` int(1) NOT NULL COMMENT '支付状态（0：未付款，1：已付款）',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `qr_code` varchar(200) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '支付二维码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `updater` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk` (`user_id`,`commodity_id`),
  UNIQUE KEY `uk-order-number` (`order_number`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

/*Data for the table `ap_order` */

/*Table structure for table `ap_pay_log` */

DROP TABLE IF EXISTS `ap_pay_log`;

CREATE TABLE `ap_pay_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `change_money` decimal(10,2) NOT NULL COMMENT '变动金额',
  `type` int(1) NOT NULL COMMENT '类型：（赞赏、注册）',
  `create_time` datetime DEFAULT NULL,
  `creator` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

/*Data for the table `ap_pay_log` */


/*Table structure for table `ap_tag` */

DROP TABLE IF EXISTS `ap_tag`;

CREATE TABLE `ap_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` int(1) NOT NULL COMMENT '类型（1:商品、2:用户）',
  `name` varchar(15) COLLATE utf8mb4_german2_ci NOT NULL COMMENT '名称',
  `sort` int(1) NOT NULL COMMENT '标签序号',
  `description` varchar(100) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '序号说明',
  `heat` int(4) DEFAULT NULL COMMENT '热度（标签对应的商品数量，标签对应的用户数量）',
  `create_time` datetime DEFAULT NULL,
  `creator` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk` (`type`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

/*Data for the table `ap_tag` */


/*Table structure for table `ap_user` */

DROP TABLE IF EXISTS `ap_user`;

CREATE TABLE `ap_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '账号',
  `password` varchar(150) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '密码',
  `order_number` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '订单编号（付款注册用户，可通过交易商户订单号直接登录，商户订单号可在支付宝交易记录中查看）',
  `nickname` varchar(20) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '昵称',
  `head_picture` varchar(500) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '头像:url',
  `sex` tinyint(1) DEFAULT '1' COMMENT '性别',
  `signature` varchar(500) COLLATE utf8mb4_german2_ci DEFAULT NULL COMMENT '个性签名',
  `register_type` int(1) NOT NULL COMMENT '注册类型（1：公众号/链接，2：付款注册）',
  `tag_id` bigint(20) NOT NULL COMMENT '标签id',
  `create_time` datetime DEFAULT NULL,
  `creator` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

/*Data for the table `ap_user` */

/*Table structure for table `ap_user_commodity` */

DROP TABLE IF EXISTS `ap_user_commodity`;

CREATE TABLE `ap_user_commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `commodity_id` bigint(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `creator` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `updater` varchar(32) COLLATE utf8mb4_german2_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_german2_ci;

/*Data for the table `ap_user_commodity` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
