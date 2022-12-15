/*
 Navicat Premium Data Transfer

 Source Server         : yyjblog
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : android

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 15/12/2022 06:40:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for deviceInfo
-- ----------------------------
DROP TABLE IF EXISTS `deviceInfo`;
CREATE TABLE `deviceInfo` (
  `uuid` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `MAC` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `loginDate` datetime DEFAULT NULL,
  `registerDate` datetime DEFAULT NULL,
  `status` enum('0','1') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '0 offline \n1 online',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for MANET
-- ----------------------------
DROP TABLE IF EXISTS `MANET`;
CREATE TABLE `MANET` (
  `uuid` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ownerID` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `number` int DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for MANET_member
-- ----------------------------
DROP TABLE IF EXISTS `MANET_member`;
CREATE TABLE `MANET_member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `MANET_UUID` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7171 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for Message
-- ----------------------------
DROP TABLE IF EXISTS `Message`;
CREATE TABLE `Message` (
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin,
  `sendDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `readDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `isRead` int DEFAULT '0',
  `targetName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `targetMAC` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sourceName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sourceMAC` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `uploadName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `uploadMAC` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `uploadTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `isUpload` int DEFAULT NULL,
  `dataType` int DEFAULT NULL,
  `fileContent` blob,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for neighborGraph
-- ----------------------------
DROP TABLE IF EXISTS `neighborGraph`;
CREATE TABLE `neighborGraph` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sourceName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `sourceMAC` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `desName` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `desMAC` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `MANET_UUID` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `timeRecord` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8783 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for Router
-- ----------------------------
DROP TABLE IF EXISTS `Router`;
CREATE TABLE `Router` (
  `id` int NOT NULL AUTO_INCREMENT,
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `dest` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `hop` int DEFAULT NULL,
  `MANET_UUID` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38392 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

SET FOREIGN_KEY_CHECKS = 1;
