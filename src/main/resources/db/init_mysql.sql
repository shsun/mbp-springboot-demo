-- ----------------------------
-- create db instance
-- ----------------------------
DROP DATABASE IF EXISTS cyems;
CREATE DATABASE cyems;
USE cyems;
--
--
--
--
--
--
--
--
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
	test_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	tenant_id BIGINT(20) NOT NULL COMMENT '租户ID',
	role_key varchar(30) NOT NULL COMMENT '角色',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '名称',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	test_type INT(11) NULL DEFAULT NULL COMMENT '测试下划线字段命名类型',
	test_date DATETIME NULL DEFAULT NULL COMMENT '日期',
	phone VARCHAR(11) NULL DEFAULT NULL COMMENT '手机号码',
	PRIMARY KEY (test_id)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='人员表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO user (tenant_id, role_key, name, age, test_type, test_date, phone) VALUES
(1, '4', '张一', 1, 1, '2017-1-1 1:1:1', '10010'),
(1, '1', '张二', 2, 1, '2017-2-2 1:1:1', '10086'),
(1, '2', '张三', 1, 1, '2017-3-3 1:1:1', '10000'),
(2, '1', '张四', 1, 1, '2017-3-3 1:1:1', '10000'),
(2, '1', '张五', 2, 1, '2017-3-3 1:1:1', '10086'),
(1, '2', '张六', 2, 1, '2017-3-3 1:1:1', '10010'),
(2, '1', '李一', 2, 1, '2017-3-3 1:1:1', '10086'),
(2, '1', '李二', 1, 1, '2017-3-3 1:1:1', '10086'),
(2, '3', '李三', 2, 1, '2017-3-3 1:1:1', '10086');
--
--
--
--
--
--
--
--
-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS role;
CREATE TABLE role (
  role_id int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  role_key varchar(30) DEFAULT NULL COMMENT '角色编码',
  create_time varchar(30) DEFAULT NULL COMMENT '创建时间',
  description varchar(200) DEFAULT NULL COMMENT '描述',
  role_value varchar(40) NOT NULL COMMENT '角色名称',
  company_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (role_id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO role VALUES ('1', '开发工程师', null, null, '', null);
INSERT INTO role VALUES ('2', '测试工程师', null, null, '', null);
INSERT INTO role VALUES ('3', '产品经理', null, null, '', null);
INSERT INTO role VALUES ('4', '经理', null, null, '', null);
--
--
--
--
--
--
--
--