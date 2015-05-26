/*
Navicat MySQL Data Transfer

Source Server         : ess
Source Server Version : 50096
Source Host           : localhost:3307
Source Database       : shm

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2015-05-25 21:15:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `advice`
-- ----------------------------
DROP TABLE IF EXISTS `advice`;
CREATE TABLE `advice` (
  `username` varchar(20) NOT NULL,
  `advice` text NOT NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of advice
-- ----------------------------
INSERT INTO `advice` VALUES ('131110323', '不错的平台', '1');

-- ----------------------------
-- Table structure for `problems`
-- ----------------------------
DROP TABLE IF EXISTS `problems`;
CREATE TABLE `problems` (
  `id` int(11) NOT NULL auto_increment,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `username` varchar(9) NOT NULL,
  `name` varchar(10) NOT NULL,
  `sex` int(1) NOT NULL,
  `givedpoints` int(11) NOT NULL,
  `picid` int(11) NOT NULL,
  `time` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of problems
-- ----------------------------
INSERT INTO `problems` VALUES ('19', '啦啦啦啦啦啦啦啦了', '浓浓', '131110323', '岩志彬', '0', '60', '0', '2015-04-28 17:31:13');
INSERT INTO `problems` VALUES ('23', '啦啦啦啦啦', 'hhhgg', '131110323', '岩志彬', '0', '0', '0', '2015-05-05 16:44:37');
INSERT INTO `problems` VALUES ('27', '我想卖自行车', '一个车子要卖了', '131110109', '李晓颖', '1', '22', '5', '2015-05-05 17:23:54');

-- ----------------------------
-- Table structure for `solve`
-- ----------------------------
DROP TABLE IF EXISTS `solve`;
CREATE TABLE `solve` (
  `id` int(11) NOT NULL auto_increment,
  `solve_username` varchar(9) NOT NULL,
  `solve_name` varchar(10) NOT NULL,
  `problemid` int(11) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `username` varchar(9) NOT NULL,
  `name` varchar(10) NOT NULL,
  `sex` int(1) NOT NULL,
  `givedpoints` int(11) NOT NULL,
  `picid` int(11) NOT NULL,
  `time` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of solve
-- ----------------------------

-- ----------------------------
-- Table structure for `the_deleted_problems`
-- ----------------------------
DROP TABLE IF EXISTS `the_deleted_problems`;
CREATE TABLE `the_deleted_problems` (
  `id` int(11) NOT NULL auto_increment,
  `solve_username` varchar(9) default NULL,
  `solve_name` varchar(10) default NULL,
  `title` text NOT NULL,
  `content` text NOT NULL,
  `username` varchar(9) NOT NULL,
  `name` varchar(10) NOT NULL,
  `sex` int(1) NOT NULL,
  `givedpoints` int(11) NOT NULL,
  `picid` int(11) NOT NULL,
  `time` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of the_deleted_problems
-- ----------------------------
INSERT INTO `the_deleted_problems` VALUES ('1', null, null, '111111', '111111', '131110323', '岩志彬', '1', '0', '1', '2015-04-23 23:21:07');
INSERT INTO `the_deleted_problems` VALUES ('2', '131110323', '岩志彬', '100的积分', '100的积分', '131110323', '岩志彬', '1', '100', '1', '2015-04-24 20:59:23');
INSERT INTO `the_deleted_problems` VALUES ('3', null, null, '500的积分', '500的积分', '131110323', '岩志彬', '1', '500', '1', '2015-04-24 20:59:01');
INSERT INTO `the_deleted_problems` VALUES ('4', '131110323', '岩志彬', '测试', '测试', '131110323', '岩志彬', '1', '0', '1', '2015-04-23 23:20:08');
INSERT INTO `the_deleted_problems` VALUES ('5', '131110323', '岩志彬', '利民', '利民', '131110323', '岩志彬', '1', '0', '1', '2015-05-04 16:52:02');
INSERT INTO `the_deleted_problems` VALUES ('6', null, null, '同学helpme', '还是技术监督局大酒店', '131110109', '李晓颖', '1', '22', '2', '2015-05-05 16:52:47');

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(9) NOT NULL,
  `password` varchar(20) NOT NULL,
  `name` varchar(10) NOT NULL,
  `picid` int(11) NOT NULL,
  `signature` text,
  `academe` text NOT NULL,
  `profession` text NOT NULL,
  `sex` int(1) NOT NULL,
  `qqnumber` varchar(20) default NULL,
  `points` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', '131110323', '123', '岩志彬', '0', '心情不错', '计院', '软件工程', '0', '821845901', '1238');
INSERT INTO `users` VALUES ('2', '131110321', '123', '向往', '1', null, '计院', '软件工程', '0', null, '220');
INSERT INTO `users` VALUES ('3', '131110320', '123', '王喆', '1', null, '计院', '软件工程', '0', null, '260');
INSERT INTO `users` VALUES ('4', '131110322', '1234', '许杨昕', '1', null, '信院', '电气工程及其自动化', '0', null, '210');
INSERT INTO `users` VALUES ('5', '131110109', '123', '李晓颖', '5', null, '计院', '软件工程', '1', null, '404');

-- ----------------------------
-- Table structure for `versionupdate`
-- ----------------------------
DROP TABLE IF EXISTS `versionupdate`;
CREATE TABLE `versionupdate` (
  `url` text,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of versionupdate
-- ----------------------------
INSERT INTO `versionupdate` VALUES ('', '1');
