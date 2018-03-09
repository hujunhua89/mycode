SET NAMES utf8;

ALTER TABLE activity_info ADD activ_title VARCHAR(100) DEFAULT NULL COMMENT '活动标题' AFTER activ_name;

ALTER TABLE activity_info ADD contentid INT DEFAULT NULL COMMENT '关联文章id' AFTER activ_pri_info;

DROP TABLE IF EXISTS `activity_guide`;

CREATE TABLE `activity_guide` (
  `guide_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '指引id',
  `activ_id` INT(11) NOT NULL COMMENT '活动id',
  `comid` INT(11) DEFAULT NULL COMMENT '企业id',
  `branchid` INT(11) DEFAULT NULL COMMENT '分公司id',
  `guide_name` VARCHAR(100) DEFAULT NULL COMMENT '指引名称',
  `guide_title` VARCHAR(400) DEFAULT NULL COMMENT '指引亮点',
  `head_pic` VARCHAR(100) DEFAULT NULL COMMENT '头部图片',
  `prod_pic` VARCHAR(100) DEFAULT NULL COMMENT '产品图片',
  `prod_link` VARCHAR(100) DEFAULT NULL COMMENT '产品链接',
  `createtime` DATETIME DEFAULT NULL COMMENT '创建时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`guide_id`),
  UNIQUE KEY `AK_activgud_UNI` (`activ_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='活动指引表';


DROP TABLE IF EXISTS `activity_guide_gift`;

CREATE TABLE `activity_guide_gift` (
  `gift_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '指引id',
  `guide_id` INT(11) NOT NULL COMMENT '活动指引id',
  `gift_name` VARCHAR(100) DEFAULT NULL COMMENT '礼品名称',
  `gift_info` VARCHAR(500) DEFAULT NULL COMMENT '礼品说明',
  `gift_pic` VARCHAR(100) DEFAULT NULL COMMENT '礼品图片',
  `orderby` INT(11) DEFAULT 0 COMMENT '排序',
  `createtime` DATETIME DEFAULT NULL COMMENT '创建时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`gift_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='活动指引礼品表';

DROP TABLE IF EXISTS `activity_guide_proc`;

CREATE TABLE `activity_guide_proc` (
  `proc_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '操作流id',
  `guide_id` INT(11) NOT NULL COMMENT '活动指引id',
  `proc_title` VARCHAR(100) DEFAULT NULL COMMENT '操作标题',
  `proc_describe` VARCHAR(200) DEFAULT NULL COMMENT '操作描述',
  `proc_target` VARCHAR(100) DEFAULT NULL COMMENT '操作目标',
  `proc_info` VARCHAR(100) DEFAULT NULL COMMENT '操作目标',
  `proc_link` VARCHAR(100) DEFAULT NULL COMMENT '操作链接',
  `orderby` INT(11) DEFAULT 0 COMMENT '排序',
  `createtime` DATETIME DEFAULT NULL COMMENT '创建时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`proc_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='活动指引操作流程表';

DROP TABLE IF EXISTS `activity_poster`;

CREATE TABLE `activity_poster` (
  `poster_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '海报id',
  `activ_id` INT(11) NOT NULL COMMENT '活动id',
  `comid` INT(11) DEFAULT NULL COMMENT '企业id',
  `branchid` INT(11) DEFAULT NULL COMMENT '分公司id',
  `poster_name` VARCHAR(100) DEFAULT NULL COMMENT '海报名称',
  `poster_title` VARCHAR(200) DEFAULT NULL COMMENT '海报亮点',
  `poster_content` TEXT DEFAULT NULL COMMENT '海报内容',
  `createtime` DATETIME DEFAULT NULL COMMENT '创建时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`poster_id`),
  UNIQUE KEY `AK_activpost_UNI` (`activ_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='活动海报表';


UPDATE tsr_customer_task tct SET  tct.get_point=tct.cust_task_id WHERE tct.task_type_code IN ('greetCard','otherDate');

INSERT INTO `tsr_score_rule` (`com_id`,`name`,`code`,`cycle`,`rewardnum`,`single_score`) VALUES (33,'添加客户','addClient','1','1','3'),(36,'添加客户','addClient','1','1','3'),(19,'添加客户','addClient','1','1','3');

update tsr_score_log set rela_id=16 where create_time BETWEEN '2018-01-24 00:00:00' AND '2018-01-24 23:59:59' AND TYPE='greetCard' and rela_id is null; 

update tsr_score_log set rela_id=15 where create_time BETWEEN '2018-01-19 00:00:00' AND '2018-01-20 23:59:59' AND TYPE='greetCard' and rela_id is null;

update tsr_score_log set rela_id=15 where create_time BETWEEN '2018-01-21 00:00:00' AND '2018-01-30 23:59:59' AND TYPE='greetCard' and rela_id is null;

update tsr_score_log set rela_id=18 where create_time BETWEEN '2018-02-04 00:00:00' AND '2018-02-04 23:59:59' AND TYPE='greetCard' and rela_id is null;

update tsr_score_log set rela_id=21 where create_time BETWEEN '2018-02-08 00:00:00' AND '2018-02-08 23:59:59' AND TYPE='greetCard' and rela_id is null;

update tsr_score_log set rela_id=15 where create_time BETWEEN '2018-02-05 00:00:00' AND '2018-02-09 23:59:59' AND TYPE='greetCard' and rela_id is null;


