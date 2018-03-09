SET NAMES utf8;

ALTER TABLE poll_info ADD poll_flag VARCHAR(10);

ALTER TABLE poll_info ADD poll_pri_info VARCHAR(300);

ALTER TABLE poll_result ADD attr1 VARCHAR(100);

ALTER TABLE poll_result ADD attr2 VARCHAR(100);

ALTER TABLE poll_result ADD attr3 VARCHAR(100);

ALTER TABLE poll_result ADD attr4 VARCHAR(100);

ALTER TABLE poll_result ADD attr5 VARCHAR(100);


update tsr_userinfo set usertype = '1' where usertype is null;

ALTER TABLE  `tsr_user_send_record` 
ADD INDEX `idx_record_type`(`custid`, `record_type`, `create_time`);

ALTER TABLE  `tsr_userinfo` 
ADD INDEX `idx_userinfo_type`(`custid`, `usertype`);

ALTER TABLE `tsr_score_log` 
ADD INDEX `idx_score_type`(`cust_id`, `type`);

ALTER TABLE `tsr_user_send_record` 
MODIFY COLUMN `record_info` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '记录详情' AFTER `record_intro`;

INSERT INTO `sysparameter` VALUES ('COM_HOT_COLUMN_ID', '重点推荐课程栏目id', '[{\"19\":\"3\"},{\"33\":\"6\"}]', NULL, NULL);





