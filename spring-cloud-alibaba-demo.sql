/*
	请先建库，再导入
	库名：spring-cloud-alibaba-demo
	字符集：utf8
	排序规则：utf8_general_ci
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品名称',
  `quantity` int(11) NULL DEFAULT NULL COMMENT '库存数',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `product` VALUES (1, '苹果', 7, '2021-09-15 22:47:36');
INSERT INTO `product` VALUES (2, '香蕉', 10, '2021-09-15 22:09:52');

SET FOREIGN_KEY_CHECKS = 1;
