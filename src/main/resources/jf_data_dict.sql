/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : jf_data_dict

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 15/04/2019 22:58:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dict_table
-- ----------------------------
DROP TABLE IF EXISTS `dict_table`;
CREATE TABLE `dict_table`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `table_desc` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict_table
-- ----------------------------
INSERT INTO `dict_table` VALUES (1, 'p_orginfo', '机构表', '3.04');
INSERT INTO `dict_table` VALUES (2, 'p_examinfo', '考试计划表', '3.04');
INSERT INTO `dict_table` VALUES (3, 'p_rel_orgenable', '机构启用表', '3.04');
INSERT INTO `dict_table` VALUES (4, 'p_code', '码表', '3.04');
INSERT INTO `dict_table` VALUES (5, 'p_placeinfo', '场所表', '3.04');
INSERT INTO `dict_table` VALUES (6, 'p_orginfo', '机构表', '3.11');
INSERT INTO `dict_table` VALUES (7, 'p_examinfo', '考试计划表', '3.10');
INSERT INTO `dict_table` VALUES (8, 'p_placeinfo', '场所信息表', '3.11');
INSERT INTO `dict_table` VALUES (9, 'p_orginfo', '机构表', '3.10');

-- ----------------------------
-- Table structure for dict_type
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `db_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '数据源ID',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
  `version` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '版本号',
  `status` int(11) NULL DEFAULT 1 COMMENT '数据状态',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict_type
-- ----------------------------
INSERT INTO `dict_type` VALUES (1, 'jf_dc_db', '码表管理', '3.04', 1);
INSERT INTO `dict_type` VALUES (2, 'jf_dc_db', '用户角色', '3.04', 1);
INSERT INTO `dict_type` VALUES (3, 'jf_dc_db', '考试计划', '3.04', 1);
INSERT INTO `dict_type` VALUES (4, 'jf_dc_db', '组织机构', '3.04', 1);
INSERT INTO `dict_type` VALUES (5, 'jf_dc_db', '场所设备', '3.04', 1);
INSERT INTO `dict_type` VALUES (6, 'jf_dc_db', '后台管理', '3.04', 1);
INSERT INTO `dict_type` VALUES (7, 'jf_dc_db', '统计类', '3.04', 1);
INSERT INTO `dict_type` VALUES (8, 'jf_vis_db', '考试计划', '3.10', 1);
INSERT INTO `dict_type` VALUES (9, 'jf_vis_db', '机构场所设备', '3.10', 1);
INSERT INTO `dict_type` VALUES (10, 'jf_vis_db', '考生信息管理', '3.10', 1);
INSERT INTO `dict_type` VALUES (11, 'jf_vis_db', '巡查人员管理', '3.10', 1);
INSERT INTO `dict_type` VALUES (12, 'jf_vis_db', '考场视频巡查', '3.10', 1);
INSERT INTO `dict_type` VALUES (13, 'jf_vis_db', '码表管理', '3.10', 1);
INSERT INTO `dict_type` VALUES (14, 'jf_vis_db', '统计类', '3.10', 1);
INSERT INTO `dict_type` VALUES (15, 'jf_dc_db', '码表管理', '3.11', 1);
INSERT INTO `dict_type` VALUES (16, 'jf_dc_db', '用户角色', '3.11', 1);
INSERT INTO `dict_type` VALUES (17, 'jf_dc_db', '考试计划', '3.11', 1);
INSERT INTO `dict_type` VALUES (18, 'jf_dc_db', '组织机构', '3.11', 1);
INSERT INTO `dict_type` VALUES (19, 'jf_dc_db', '场所设备', '3.11', 1);
INSERT INTO `dict_type` VALUES (20, 'jf_dc_db', '后台管理', '3.1', 1);
INSERT INTO `dict_type` VALUES (21, 'jf_dc_db', '统计类', '3.11', 1);

-- ----------------------------
-- Table structure for rel_table_type
-- ----------------------------
DROP TABLE IF EXISTS `rel_table_type`;
CREATE TABLE `rel_table_type`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NULL DEFAULT NULL,
  `dt_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rel_table_type
-- ----------------------------
INSERT INTO `rel_table_type` VALUES (1, 1, 4);
INSERT INTO `rel_table_type` VALUES (2, 2, 3);
INSERT INTO `rel_table_type` VALUES (3, 3, 4);
INSERT INTO `rel_table_type` VALUES (4, 4, 1);
INSERT INTO `rel_table_type` VALUES (5, 5, 5);
INSERT INTO `rel_table_type` VALUES (6, 6, 18);
INSERT INTO `rel_table_type` VALUES (7, 7, 8);
INSERT INTO `rel_table_type` VALUES (8, 8, 19);
INSERT INTO `rel_table_type` VALUES (9, 9, 9);

SET FOREIGN_KEY_CHECKS = 1;
