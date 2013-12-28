DROP DATABASE IF EXISTS cost_on;

CREATE DATABASE cost_on DEFAULT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

USE cost_on;

DROP TABLE IF EXISTS cost_user;

CREATE TABLE cost_user(
user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_name VARCHAR(20) NOT NULL,
password VARCHAR(20) NOT NULL,
login_name VARCHAR(20) NOT NULL,
user_age TINYINT(3) NOT NULL,
user_address VARCHAR(150) NOT NULL,
user_email VARCHAR(50) NOT NULL,
user_image VARCHAR(100),
user_status TINYINT(1) NOT NULL DEFAULT 2 COMMENT "1：不可用；2：可用",
is_admin TINYINT(1) NOT NULL DEFAULT 1 COMMENT "1：普通用户；2：管理员",
login_time TIMESTAMP,
create_user VARCHAR(20),
create_time TIMESTAMP,
modify_user VARCHAR(20),
modify_time TIMESTAMP,
user_remark VARCHAR(200)
)engine=InnoDB DEFAULT CHARSET=UTF8 AUTO_INCREMENT=1;

DROP TABLE IF EXISTS cost_account;

CREATE TABLE cost_account(
account_id BIGINT PRIMARY KEY AUTO_INCREMENT,
user_id BIGINT NOT NULL,
account_money DOUBLE NOT NULL,
account_time DATE NOT NULL,
account_type TINYINT(1) NOT NULL,
approve_time TIMESTAMP,
is_approve TINYINT(1) NOT NULL DEFAULT 1 COMMENT "1：未审批；2：已审批",
account_partner VARCHAR(100),
account_accessory VARCHAR(100),
create_user VARCHAR(20),
create_time TIMESTAMP,
modify_user VARCHAR(20),
modify_time TIMESTAMP,
account_remark VARCHAR(200),
FOREIGN KEY(user_id) REFERENCES cost_user(user_id)
)engine=InnoDB DEFAULT CHARSET=UTF8 AUTO_INCREMENT=1;