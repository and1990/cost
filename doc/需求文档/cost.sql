/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : cost_shasha

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2014-05-05 10:28:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cost_account
-- ----------------------------
DROP TABLE IF EXISTS `cost_account`;
CREATE TABLE `cost_account` (
  `account_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `account_money` decimal(10,2) NOT NULL,
  `account_time` date NOT NULL,
  `account_type` int(1) NOT NULL DEFAULT '1' COMMENT '1：食物；2：娱乐：3：交通',
  `account_status` int(1) NOT NULL DEFAULT '1' COMMENT '1：未审批；2：已审批',
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `modify_user` varchar(20) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `account_remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cost_account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `cost_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for cost_user
-- ----------------------------
DROP TABLE IF EXISTS `cost_user`;
CREATE TABLE `cost_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(64) NOT NULL,
  `login_name` varchar(20) NOT NULL,
  `user_age` int(3) NOT NULL,
  `user_address` varchar(150) DEFAULT NULL,
  `user_email` varchar(50) DEFAULT NULL,
  `user_status` int(1) NOT NULL DEFAULT '2' COMMENT '1：不可用；2：可用',
  `user_type` int(1) NOT NULL DEFAULT '1' COMMENT '1：普通用户；2：管理员',
  `login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL,
  `user_remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
