/*
 Navicat MySQL Data Transfer

 Source Server         : java
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : localhost:3306
 Source Schema         : railway_ticket_system

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001

 Date: 22/12/2023 15:45:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hsw
-- ----------------------------
DROP TABLE IF EXISTS `hsw`;
CREATE TABLE `hsw`  (
  `id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `start_station` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `end_station` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `mileages` float NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `end_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `platforms` int(11) NULL DEFAULT NULL,
  `business_blocks` int(11) NULL DEFAULT NULL,
  `first_seats` int(11) NULL DEFAULT NULL,
  `second_seats` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hsw
-- ----------------------------
INSERT INTO `hsw` VALUES ('1', '2', '2', 2, '2', '2', '2', 2, 2, 2, 2);
INSERT INTO `hsw` VALUES ('12', '12', '12', 12, 'Stopped', '12', '12', 12, 12, 12, 12);
INSERT INTO `hsw` VALUES ('HSW006', 'Harbin', 'Dalian', 904, 'Running', '05:30', '11:30', 5, 14, 55, 105);
INSERT INTO `hsw` VALUES ('HSW007', 'Qingdao', 'Nanjing', 512, 'Stopped', '07:45', '12:45', 4, 9, 45, 95);
INSERT INTO `hsw` VALUES ('HSW008', 'Shenyang', 'Xi\'an', 1260, 'Running', '08:20', '16:20', 6, 12, 60, 120);
INSERT INTO `hsw` VALUES ('HSW009', 'Kunming', 'Guiyang', 658, 'Running', '06:50', '11:50', 5, 7, 39, 80);
INSERT INTO `hsw` VALUES ('HSW010', 'Chengdu', 'Lanzhou', 738, 'Running', '09:30', '15:30', 6, 12, 60, 120);
INSERT INTO `hsw` VALUES ('HSW011', 'Urumqi', 'Xi\'an', 2154, 'Running', '07:00', '19:00', 7, 14, 70, 140);
INSERT INTO `hsw` VALUES ('HSW012', 'Hefei', 'Wuhan', 356, 'Running', '10:15', '13:15', 4, 9, 45, 89);
INSERT INTO `hsw` VALUES ('HSW013', 'Nanning', 'Guangzhou', 578, 'Running', '06:30', '12:30', 5, 11, 55, 110);
INSERT INTO `hsw` VALUES ('HSW014', 'Hangzhou', 'Fuzhou', 806, 'Running', '08:00', '14:00', 5, 10, 50, 100);
INSERT INTO `hsw` VALUES ('HSW015', 'Tianjin', 'Jinan', 375, 'Running', '07:55', '10:55', 3, 7, 35, 70);

-- ----------------------------
-- Table structure for passenger
-- ----------------------------
DROP TABLE IF EXISTS `passenger`;
CREATE TABLE `passenger`  (
  `id_card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `paper_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `self_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `emergency_contacter` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `emergency_contacter_phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id_card`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of passenger
-- ----------------------------
INSERT INTO `passenger` VALUES ('110101199003076317', '21', '12', '12', '12', '12');
INSERT INTO `passenger` VALUES ('220202199004086428', '王五', '身份证', '13800238001', '赵六', '13900239001');
INSERT INTO `passenger` VALUES ('330303199005096539', '李华', '身份证', '13800338002', '刘易', '13900339002');
INSERT INTO `passenger` VALUES ('440404199006106640', '陈东', '身份证', '13800438003', '周娜', '13900439003');
INSERT INTO `passenger` VALUES ('550505199007116751', '林峰', '身份证', '13800538004', '吴凯', '13900539004');

-- ----------------------------
-- Table structure for platforms
-- ----------------------------
DROP TABLE IF EXISTS `platforms`;
CREATE TABLE `platforms`  (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `startTime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `endTime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `mileages` float NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of platforms
-- ----------------------------

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `date` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `car_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `start_station` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `end_station` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `paper_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `id_card` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `end_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `seat_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `seat_no` int(11) NULL DEFAULT NULL,
  `price` float NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id_card`(`id_card`) USING BTREE,
  INDEX `car_id`(`car_id`) USING BTREE,
  CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`id_card`) REFERENCES `passenger` (`id_card`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `hsw` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ticket
-- ----------------------------
INSERT INTO `ticket` VALUES ('TKT1703252632803', '05:30', 'HSW006', 'Harbin', 'Dalian', '身份证', '110101199003076317', '张三', '', '', 'Business', 21, 23);
INSERT INTO `ticket` VALUES ('TKT1703252643260', '06:50', 'HSW009', 'Kunming', 'Guiyang', '身份证', '440404199006106640', '陈东', '', '', 'Business', 23, 2323);
INSERT INTO `ticket` VALUES ('TKT1703252656947', '10:15', 'HSW012', 'Hefei', 'Wuhan', '身份证', '330303199005096539', '李华', '', '', 'Second', 11, 45);
INSERT INTO `ticket` VALUES ('TKT1703252671344', '06:50', 'HSW009', 'Kunming', 'Guiyang', '身份证', '440404199006106640', '陈东', '', '', 'First', 9, 478);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'wyb', '0000');
INSERT INTO `users` VALUES (2, '', '');
INSERT INTO `users` VALUES (3, '111', '2323');
INSERT INTO `users` VALUES (4, '121', '222');

SET FOREIGN_KEY_CHECKS = 1;
