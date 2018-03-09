SET NAMES utf8;
DROP TABLE IF EXISTS tsr_send_task_cfg;
/**短信定时任务配置表**/
CREATE TABLE `tsr_send_task_cfg` (
  `task_cfg_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `comid` INT(11) NOT NULL COMMENT '企业id',
  `task_class` VARCHAR(10) DEFAULT 'BADY' COMMENT '任务分类BDAY:生日营销',
  `task_type` VARCHAR(10) DEFAULT NULL COMMENT '任务类型:T-7,T-1',
  `task_name` VARCHAR(100) DEFAULT NULL COMMENT '任务名称',
  `timer_type` VARCHAR(10) DEFAULT 'day' COMMENT '定时类型：每天：day',
  `start_day` DATE DEFAULT NULL COMMENT '开始日期',
  `end_day` DATE DEFAULT NULL COMMENT '结束日期',
  `exec_time` VARCHAR(10) DEFAULT NULL COMMENT '执行时间，结合定时类型使用',
  `status` INT(11) DEFAULT 0 COMMENT '状态 0:正常 1:暂停 2:完成;3：执行中',
  `send_temp_id` INT(11) DEFAULT NULL COMMENT '短信模板id',
  `view_orderby` INT(11) DEFAULT NULL COMMENT '显示排序',
  `create_date` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_date` DATETIME DEFAULT NULL COMMENT '修改时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`task_cfg_id`)
) ENGINE=INNODB AUTO_INCREMENT=100001 DEFAULT CHARSET=utf8 COMMENT='短信定时任务表';

DROP TABLE IF EXISTS tsr_send_task_ds;
/**短信定时任务数据源配置表**/
CREATE TABLE `tsr_send_task_ds` (
  `ds_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '数据源id',
  `task_cfg_id` INT(11) DEFAULT NULL COMMENT '任务配置id',
  `ds_type` VARCHAR(100) DEFAULT 'IC' COMMENT '数据源类型:IC:万丈IC',
  `ds_name` VARCHAR(100) DEFAULT '万丈IC数据' COMMENT '数据源名称',
  `relevant_task` VARCHAR(100) DEFAULT NULL COMMENT '关联任务：任务配置id|(AND,OR)|(OPEN,NOOPEN,SENDSUCC)',
  `recipients` VARCHAR(100) DEFAULT NULL COMMENT '收件人：bday|(AF-后,BF-前)|7',
  `recipients_area` VARCHAR(300) DEFAULT NULL COMMENT '收件人地址：地区id ,分隔',
  `inds_date` VARCHAR(100) DEFAULT NULL COMMENT '入库时间：开始日期|结束日期',
  `create_date` DATETIME DEFAULT NULL COMMENT '创建时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ds_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='短信任务数据源配置表';


DROP TABLE IF EXISTS tsr_send_task;
/**短信定时任务表**/
CREATE TABLE `tsr_send_task` (
  `task_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '任务列表id',
  `comid` INT(11) DEFAULT NULL COMMENT '企业id',
  `task_cfg_id` INT(11) DEFAULT NULL COMMENT '任务配置id',
  `task_type` VARCHAR(10) DEFAULT NULL COMMENT '任务类型:T-7,T-1',
  `send_day` DATE DEFAULT NULL COMMENT '发送日期',
  `send_time` VARCHAR(10) DEFAULT NULL COMMENT '发送时间',
  `send_total_num` INT(11) DEFAULT 0 COMMENT '发送总数',
  `send_yet_num` INT(11) DEFAULT 0 COMMENT '己发送数',
  `send_succ_num` INT(11) DEFAULT 0 COMMENT '发送成功数',
  `send_open_num` INT(11) DEFAULT 0 COMMENT '打开数',
  `send_click_num` INT(11) DEFAULT 0 COMMENT '点击数',
  `status` INT(11) DEFAULT 0 COMMENT '状态 0:待发送 1:暂停 2:完成;3：执行中',
  `send_temp_id` INT(11) DEFAULT NULL COMMENT '短信模板id',
  `create_date` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_date` DATETIME DEFAULT NULL COMMENT '修改时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`task_id`)
) ENGINE=INNODB AUTO_INCREMENT=20171211 DEFAULT CHARSET=utf8 COMMENT='短信任务表';


DROP TABLE IF EXISTS tsr_sms_record;
/**短信定时任务表**/
CREATE TABLE `tsr_sms_record` (
  `sms_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '发送信息id',
  `comid` INT(11) DEFAULT NULL,
  `branchid` INT(11) DEFAULT NULL COMMENT '分公司id',
  `custid` INT(11) DEFAULT NULL COMMENT '客服id',
  `userid` INT(11) DEFAULT NULL COMMENT '用户id',
  `task_id` INT(11) DEFAULT NULL COMMENT '任务id',
  `task_cfg_id` INT(11) DEFAULT NULL COMMENT '任务配置id',
  `temp_id` INT(11) DEFAULT NULL COMMENT '短信模板id',
  `sendtype` VARCHAR(4) DEFAULT NULL COMMENT '发送类型- 生日营销:BDAY',
  `mobile` VARCHAR(100) DEFAULT NULL COMMENT '手机号',
  `sendtext` TEXT COMMENT '发送内容',
  `sendtime` DATETIME DEFAULT NULL COMMENT '发送时间',
  `status` INT(11) DEFAULT NULL COMMENT '发送状态0：未发送,1：发送失败，2：发送成功',
  `receipt_status` VARCHAR(2) DEFAULT NULL COMMENT  '回执状态',
  `receipt_id` VARCHAR(30) DEFAULT NULL COMMENT '回执id',
  `receipt_info` VARCHAR(50) DEFAULT NULL COMMENT '回执信息',
  `click_count` INT(11) DEFAULT 0 COMMENT '点击次数',
  `stay_len` INT(11) DEFAULT 0 COMMENT '停留时长',
  `first_click_date` DATETIME DEFAULT NULL COMMENT '第一次点击时间',
  `last_click_date` DATETIME DEFAULT NULL COMMENT '最后点击时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sms_id`)
) ENGINE=INNODB AUTO_INCREMENT=201712 DEFAULT CHARSET=utf8 COMMENT='短信记录表';

DROP TABLE IF EXISTS tsr_push_sms;
CREATE TABLE `tsr_push_sms` (
  `psid` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comid` INT(11) DEFAULT NULL COMMENT '公司id',
  `branchid` INT(11) DEFAULT NULL COMMENT '分公司id',
  `title` VARCHAR(50) DEFAULT NULL COMMENT '名称',
  `content` VARCHAR(500) DEFAULT NULL COMMENT '内容',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '更新时间',
  `STATUS` INT(11) DEFAULT NULL COMMENT '状态',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`psid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='tsr短信模版表';

DROP TABLE IF EXISTS tsr_birthday_wish;
CREATE TABLE `tsr_birthday_wish` (
  `wishid` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comid` INT(11) DEFAULT NULL COMMENT '公司id',
  `branchid` INT(11) DEFAULT NULL COMMENT '分公司id',
  `sms_id` INT(11) DEFAULT NULL COMMENT '短信id',
  `union_id` INT(11) DEFAULT NULL COMMENT '用户联合id',
  `userid` INT(11) DEFAULT NULL COMMENT '用户id',
  `content` VARCHAR(500) DEFAULT NULL COMMENT '内容',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `STATUS` INT(11) DEFAULT NULL COMMENT '状态',
  `orderby` INT(11) DEFAULT NULL COMMENT '排序',
  `remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`wishid`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='tsr生日愿望表';

DROP TABLE IF EXISTS `celebrity_birthday_dict`;
CREATE TABLE `celebrity_birthday_dict`  (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `person_name` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `brithday` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `person_desc` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `area` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` DATETIME(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 5603 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '明星生日字典表' ROW_FORMAT = COMPACT;

DROP TABLE IF EXISTS `festival_dict`;
CREATE TABLE `festival_dict`  (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `time` VARCHAR(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `name` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_time` DATETIME(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = INNODB AUTO_INCREMENT = 180 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '2018年节日字典表' ROW_FORMAT = COMPACT;

INSERT INTO `sysparameter` VALUES ('SMS_RECEIPT_CRON', '短信回执获取调度配置', '0 0/5 * * * ?', NULL, NULL);
INSERT INTO `sysparameter` VALUES ('TELSEY_CONTENT_PUSH', '平安telsey文章采集推送配置', '20044|382', NULL, NULL);
INSERT INTO `sysparameter` VALUES ('IC_SMS_DATA_CRON', 'ic生日短信数据获取调度配置', '0 0 1 * * ?', NULL, NULL);

INSERT INTO tsr_union_active_cfg VALUES(
9,'srhk',2,0,25,1,1,'生日贺卡H5'
);
ALTER TABLE circle MODIFY url VARCHAR(200) COMMENT '外部文章链接';
ALTER TABLE content MODIFY COLUMN cont_link VARCHAR(200);

INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','insurancepro','赠险','赠险','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','insurancePeriod','保障期限','保障期限','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','paymentway','缴费方式','缴费方式','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','workinglife','工作年限','工作年限','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','icactivid','活动唯一id','活动唯一id','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','amount','保额（单位：万）','保额（单位：万）','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','calTimes','试算次数','试算次数','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','level','卡等级','卡等级','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','applicantAge','投保人年龄','投保人年龄','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','hadhouse','是否有房','是否有房','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','interest','兴趣产品','兴趣产品','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','moincome','月收入','月收入','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','educationcnt','注册教育培训类活动次数','注册教育培训类活动次数','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','src','模板URL','模板URL','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','industry','所属行业','所属行业','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','trial','是否试算','是否试算','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','childBirthday','孩子生日','孩子生日','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','bookProduct','预约产品','预约产品','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','familyBirthday','家人生日','家人生日','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','life_stages','人生阶段','人生阶段','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','corpid','企业id','企业id','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','isinsterest','是否对保障又返还保险感兴趣','是否对保障又返还保险感兴趣','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','familyTrial','为家人试算','为家人试算','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','idcard','身份证号','身份证号','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','available','是否有效手机号','是否有效手机号','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','terminal_type','终端类型','终端类型','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','loan','需贷额度','需贷额度','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','appointment','预约咨询','预约咨询','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','credict','信用卡额度','信用卡额度','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','hadcar','是否有车','是否有车','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','policy','是否有保单','是否有保单','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
INSERT INTO `tsr_taglib_cfg` (`tag_cfg_type`, `tag_var_name`, `tag_name`, `tag_view_name`, `tag_source`, `is_base`, `base_type`, `base_limit`, `is_etd_view`, `is_que_view`, `tag_fmt_type`, `tag_validate_type`, `tag_sub_fmt`, `tag_view_fmt`, `val_score_rule`, `relevant_tag`, `mark_num`, `view_orderby`, `create_date`, `remark`) VALUES('U','moneyType','贷款类型','贷款类型','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,NULL,'1','0',NOW(),NULL);
