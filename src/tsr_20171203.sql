set names utf8;
DROP TABLE IF EXISTS tsr_taglib_cfg;

CREATE TABLE `tsr_taglib_cfg` (
  `tag_cfg_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '标签配置id',
  `tag_cfg_type` VARCHAR(10) DEFAULT NULL COMMENT '标签配置类型U:用户标签',
  `tag_var_name` VARCHAR(50) DEFAULT NULL COMMENT '标签代码',
  `tag_name` VARCHAR(50) DEFAULT NULL COMMENT '标签名',
  `tag_view_name` VARCHAR(50) DEFAULT NULL COMMENT '显示标签名',
  `tag_source` VARCHAR(50) DEFAULT NULL COMMENT '标签来源',
  `is_base` VARCHAR(1) DEFAULT NULL COMMENT '是否基础字段Y:N',
  `base_type` VARCHAR(10) DEFAULT NULL COMMENT '基础配置类型 K:标签名匹配,KV:标签名值匹配,RK:关联标签匹配',
  `base_limit` VARCHAR(200) DEFAULT NULL COMMENT '基础配置;分隔',
  `is_etd_view` VARCHAR(1) DEFAULT NULL COMMENT '是否编缉显示Y:N',
  `is_que_view` VARCHAR(1) DEFAULT NULL COMMENT '是否查询显示Y:N',
  `tag_fmt_type` VARCHAR(10) DEFAULT NULL COMMENT '标签数据格式化类型',
  `tag_validate_type` VARCHAR(10) DEFAULT NULL COMMENT '标签校验类型',
  `tag_sub_fmt` VARCHAR(500) DEFAULT NULL COMMENT '格式化提交信息',
  `tag_view_fmt` VARCHAR(500) DEFAULT NULL COMMENT '格式化显示信息',
  `val_score_rule` VARCHAR(500) DEFAULT NULL COMMENT '值计分规则:值|分值 ;分隔',
  `relevant_tag` VARCHAR(50) DEFAULT NULL COMMENT '关联标签|处理类型 ;分隔',
  `mark_num` INT(11) DEFAULT 1 COMMENT '可标记次数',
  `view_orderby` INT(11) DEFAULT NULL COMMENT '显示排序',
  `create_date` DATETIME DEFAULT NULL COMMENT '创建时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`tag_cfg_id`),
  UNIQUE KEY `AK_TSR_TAGCFG` (`tag_var_name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='标签库配置表';

insert  into `tsr_taglib_cfg`(`tag_cfg_id`,`tag_cfg_type`,`tag_var_name`,`tag_name`,`tag_view_name`,`tag_source`,`is_base`,`base_type`,`base_limit`,`is_etd_view`,`is_que_view`,`tag_fmt_type`,`tag_validate_type`,`tag_sub_fmt`,`tag_view_fmt`,`val_score_rule`,`relevant_tag`,`mark_num`,`view_orderby`,`create_date`,`remark`) values (1,'U','age','年龄','年龄','our','N','',NULL,'Y','N','INT',NULL,NULL,NULL,NULL,'ageinterval|STR',1,0,'2017-11-15 14:50:51',NULL),(2,'U','birthday','生日','出生日期','our','Y','K',NULL,'Y','N','DATE',NULL,NULL,NULL,NULL,'age|BIRTHDAY',1,0,'2017-11-15 14:52:00',NULL),(3,'U','ageinterval','年龄区间','年龄段','our','N','',NULL,'N','Y','STR','in','-20,20-25,25-30,30-40,50-','-20:20岁以下;20-25:20-25岁;25-30:25-30岁;30-40:30-40岁;50-:50岁以上','-20|0;20-25|5;25-30|8;30-40|10;40-50|8;50-|5',NULL,1,0,'2017-11-15 14:52:00',NULL),(4,'U','sex','性别','性别','our','Y','K',NULL,'Y','N','STR','eq','0,1,2','0:女;1:男;2:未知',NULL,NULL,1,0,'2017-11-15 14:52:00',NULL),(5,'U','marriage','婚姻状况','婚否','our','Y','K',NULL,'Y','Y','STR','eq','0,1','0:未婚;1:己婚','0|5;1|10',NULL,1,0,'2017-11-15 14:52:00',NULL),(6,'U','children','子女状况','子女','our','Y','K',NULL,'Y','Y','STR','eq','0,1','0:无孩;1:有孩','1|15',NULL,1,0,'2017-11-15 14:52:00',NULL),(7,'U','property','资产状况','资产','our','Y','KV','0;1','Y','Y','STR','and','0,1','0:有房;1:有车',NULL,NULL,2,0,'2017-11-15 14:52:00',NULL),(9,'U','insurance','保险状况','保险','our','Y','KV','0;1','Y','N','STR','and','0,1','0:社保;1:商保','0|5',NULL,2,0,'2017-11-15 15:33:10',NULL),(10,'U','income','年收入','年收入','our','N',NULL,NULL,'Y','Y','STR','in','-10,10-30,30-50,50-','-10:10W以下;10-30:10-30W;30-50:30-50W;50-:50W以上','-10|5;10-30|10;30-50|15;50-|20',NULL,1,0,'2017-11-15 15:34:13',NULL),(11,'U','mackdeal','成交客户','是否成交','our','Y',NULL,NULL,'Y','N','STR','eq','0,1','0:否;1:是','1|10',NULL,1,0,'2017-11-15 15:34:13',NULL),(12,'U','username','用户姓名','姓名','our','Y','K',NULL,'Y','N','STR',NULL,NULL,NULL,NULL,NULL,1,0,'2017-11-15 15:34:13',NULL),(13,'U','provinceid','省份代码','省份','our','Y','RK','cityid','Y','N','ADDRESS',NULL,NULL,NULL,NULL,NULL,1,0,'2017-11-15 15:34:13',NULL),(14,'U','cityid','市代码','市','our','N','','','Y','N','ADDRESS',NULL,NULL,NULL,NULL,NULL,1,0,'2017-11-15 15:34:13',NULL),(15,'U','name','姓名','姓名','our','N','',NULL,'N','N','STR',NULL,NULL,NULL,NULL,'username|STR',1,0,NULL,NULL),(16,'U','email','邮箱','邮箱','our','N',NULL,NULL,'Y','N','STR',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL),(17,'U','weixin','微信号','微信','our','N',NULL,NULL,'Y','N','STR',NULL,NULL,NULL,NULL,NULL,1,0,NULL,NULL),(18,'U','address','详细地址','地址','our','N',NULL,NULL,'Y','N','STR',NULL,NULL,NULL,NULL,NULL,1,0,'2017-11-15 15:34:13',NULL);


DROP TABLE IF EXISTS tsr_union_user;

CREATE TABLE `tsr_union_user` (
  `union_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户联合id',
  `comid` INT(11) NOT NULL COMMENT '公司id',
  `mobile` VARCHAR(20) NOT NULL COMMENT '手机号',
  `base_score` FLOAT DEFAULT 0 COMMENT '基础分',
  `activity` FLOAT DEFAULT 0 COMMENT '活跃度',
  `username` VARCHAR(20) DEFAULT NULL COMMENT '用户姓名',
  `sex` VARCHAR(1) DEFAULT NULL COMMENT '性别',
  `age` VARCHAR(5) DEFAULT NULL COMMENT '年龄',
  `ageInterval` VARCHAR(50) DEFAULT NULL COMMENT '年龄区间',
  `birthday` VARCHAR(10) DEFAULT NULL COMMENT '生日',
  `countryid` VARCHAR(30) DEFAULT NULL COMMENT '国家编码',
  `provinceid` VARCHAR(30) DEFAULT NULL COMMENT '省编码',
  `cityid` VARCHAR(30) DEFAULT NULL COMMENT '市编码',
  `countyid` VARCHAR(30) DEFAULT NULL COMMENT '县(区)编码',
  `address` VARCHAR(200) DEFAULT NULL COMMENT '详细地址',
  `qq` VARCHAR(20) DEFAULT NULL COMMENT 'QQ号',
  `winxin` VARCHAR(50) DEFAULT NULL COMMENT '微信号',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `photo` VARCHAR(100) DEFAULT NULL COMMENT '头像',
  `integrity` VARCHAR(100) DEFAULT NULL COMMENT '完整度',
  `product` VARCHAR(100) DEFAULT NULL COMMENT '产品',
  `last_activ_time` DATETIME DEFAULT NULL COMMENT '最后活跃时间',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`union_id`),
  UNIQUE KEY `AK_TSR_UNI` (`mobile`,`comid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='T端联合用户表';

INSERT INTO tsr_union_user(comid,mobile,create_time,last_activ_time,username,sex,birthday) 
SELECT tu.comid,tu.mobile,NOW(),NOW(),tu.username,tu.sex,tu.birthday FROM tsr_userinfo tu,(SELECT a.comid,a.mobile,a.num,a.userid FROM (SELECT comid,mobile,COUNT(*) AS num,MAX(userid) userid FROM tsr_userinfo GROUP BY comid,mobile)a)b WHERE tu.userid=b.userid;

DROP TABLE IF EXISTS tsr_union_user_tag;

CREATE TABLE `tsr_union_user_tag` (
 `record_id`INT(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `union_id` INT(11) NOT NULL COMMENT '用户联合id',
  `comid` INT(11) DEFAULT NULL COMMENT '公司id',
  `mobile` VARCHAR(20) NOT NULL COMMENT '手机号',
  `tag_var_name` VARCHAR(50) DEFAULT NULL COMMENT '标签名',
  `input_value` VARCHAR(50) NOT NULL COMMENT '值',
  `input_source` VARCHAR(2) DEFAULT NULL COMMENT '输入来源客服:C;用户:U',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
   PRIMARY KEY (`record_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='T端联合用户标签表';

DROP TABLE IF EXISTS tsr_union_user_tag_log;

CREATE TABLE `tsr_union_user_tag_log` (
  `log_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `union_id` INT(11) NOT NULL COMMENT '用户联合id',
  `tag_var_name` VARCHAR(50) DEFAULT NULL COMMENT '标签名',
  `input_value` VARCHAR(50) NOT NULL COMMENT '值',
  `weight` INT(11) DEFAULT 10 COMMENT '权重',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`log_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='T端联合用户标签日志表';

DROP TABLE IF EXISTS tsr_union_active_log;
CREATE TABLE `tsr_union_active_log` (
  `log_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `union_id` INT(11) NOT NULL COMMENT '用户联合id',
  `active_type` VARCHAR(20) DEFAULT NULL COMMENT '活跃类型',
  `active_level` VARCHAR(2) NOT NULL COMMENT '活跃级别1,2,3',
  `score` INT(11) DEFAULT 0 COMMENT '分值',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `update_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`log_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='T端联合用户活跃日志表';

DROP TABLE IF EXISTS tsr_union_active_cfg;

CREATE TABLE `tsr_union_active_cfg` (
  `active_cfg_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '标签配置id',
  `active_type` VARCHAR(20) DEFAULT NULL COMMENT '活跃类型',
  `active_level` VARCHAR(2) DEFAULT NULL COMMENT '活跃级别1,2,3',
  `weight` INT(11) DEFAULT 0 COMMENT '权重',
  `score` INT(11) DEFAULT 0 COMMENT '分值',
  `multiplying` INT(11) DEFAULT 1 COMMENT '倍率',
  `frequency` INT(11) DEFAULT 1 COMMENT '频率',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`active_cfg_id`),
  UNIQUE KEY `AK_TSR_ACTIVE_CFG` (`active_type`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='联合用户活跃类型表';

insert  into `tsr_union_active_cfg`(`active_cfg_id`,`active_type`,`active_level`,`weight`,`score`,`multiplying`,`frequency`,`remark`) values (1,'busin_card_r','1',0,15,1,1,'名片阅读提醒'),(2,'busin_card_x','1',0,15,1,1,'名片评价提醒'),(3,'plan_r','3',0,40,1,1,'计划阅读提醒'),(4,'zx','2',0,25,1,1,'获客通知（参加赠险）'),(5,'yqh','3',0,40,1,1,'邀请函填写(营销活动)'),(6,'bd_x','2',0,25,1,1,'保单帐户填写'),(7,'wz_r','1',0,15,1,1,'文章阅读'),(8,'wjdc','1',0,15,1,1,'问卷调查');

ALTER TABLE content MODIFY COLUMN cont_link VARCHAR(200);


drop table if exists tsr_relation_us;
CREATE TABLE `tsr_relation_us` (
  `us_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(20) DEFAULT NULL COMMENT '标题',
  `taginfo` varchar(1000) DEFAULT NULL COMMENT '标签信息',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `content` text COMMENT '内容',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更改时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `view_count` int(11) DEFAULT NULL COMMENT '浏览次数',
  PRIMARY KEY (`us_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='联系我们内容表';


drop table if exists tsr_function_introduce;
CREATE TABLE `tsr_function_introduce` (
  `introduce_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comid` int(11) DEFAULT NULL COMMENT '公司id;type为1才有公司id',
  `type` varchar(2) DEFAULT NULL COMMENT '类型;0:功能介绍;1:营销指引',
  `title` varchar(20) DEFAULT NULL COMMENT '标题',
  `taginfo` varchar(1000) DEFAULT NULL COMMENT '标签信息',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `content` mediumtext COMMENT '内容',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更改时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `view_count` int(11) DEFAULT NULL COMMENT '浏览次数',
  PRIMARY KEY (`introduce_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='功能介绍内容和营销指引表';

drop table if exists tsr_relation_us_feedback;
CREATE TABLE `tsr_relation_us_feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈id',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `content` text COMMENT '内容',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `feedback_ip` varchar(50) DEFAULT NULL COMMENT 'ip',
  `feedback_time` datetime DEFAULT NULL COMMENT '参与时间',
  `feedback_unit` varchar(100) DEFAULT NULL COMMENT 'Ip物理地址',
  `feedback_browser` varchar(500) DEFAULT NULL COMMENT '参与浏览器',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='联系我们反馈表';


alter table adconfig add `ad_type` varchar(2) DEFAULT NULL COMMENT '类型,0:旧的;1:新的';
update adconfig  set ad_type=0;

alter table adconfig add `status` int(11) DEFAULT NULL COMMENT '状态,0:正常;9:删除';
update adconfig  set status=0;

drop table if exists tsr_special_topic;
CREATE TABLE `tsr_special_topic` (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '专题内容id',
  `comid` int(11) DEFAULT NULL COMMENT '公司id',
  `topic_type` varchar(2) DEFAULT NULL COMMENT '类型 0:专题的标题内容，1：专题的文章内容',
  `topic_title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content_id` int(11) DEFAULT NULL COMMENT '文章id',
  `content_title` varchar(100) DEFAULT NULL COMMENT '文章标题',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更改时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='专题内容表';

ALTER TABLE `customer` ADD COLUMN `score` int(10) COMMENT '客服金豆' AFTER `band_date`;



-- 积分规则表
DROP TABLE IF EXISTS `tsr_score_rule`;
CREATE TABLE `tsr_score_rule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `com_id` int(11) DEFAULT NULL COMMENT '企业Id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '积分策略名称',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '积分策略编码',
  `cycle` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '周期(0一次1天2月3不限)',
  `rewardnum` int(10) DEFAULT NULL COMMENT '周期内奖励次数',
  `single_score` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '单次积分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


INSERT INTO `sysparameter` VALUES ('YBJ_CRAWLER_CONTENT_CAT', '赢百家内容爬虫文章类型', '20170427AC-6XBIYWU1,20170427AC-MN1MRL3H,20170427AC-UTZFRYI1,20170427AC-X7MF7UUK', NULL, NULL);
INSERT INTO `sysparameter` VALUES ('YBJ_CONTENT_PUSH_COMID', '赢百家内容推送到哪些企业', '19', NULL, NULL);
INSERT INTO `sysparameter` VALUES ('YBJ_CONTENT_PUSH_CAT', '赢百家内容推送到哪些栏目', '342', NULL, NULL);
INSERT INTO `sysparameter` VALUES ('CUST_SCORE_RANKING_ROLE_FILTER', '坐席积分排名角色过滤', '13', NULL, NULL);

DROP TABLE IF EXISTS `tsr_score_log`;
CREATE TABLE `tsr_score_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cust_id` int(11) DEFAULT NULL COMMENT '客服id',
  `com_id` int(11) DEFAULT NULL COMMENT '企业id',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '获取积分方式',
  `score` int(4) DEFAULT NULL COMMENT '积分',
  `create_time` datetime(0) DEFAULT NULL COMMENT '获取时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

update roleaccorelation set roleid = 13 where accoid in (select accoid from account where custid in(693,689,688,687,685,683,392,506,394,492,691,690) );
