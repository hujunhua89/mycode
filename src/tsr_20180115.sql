SET NAMES utf8;

UPDATE tsr_taglib_cfg SET tag_sub_fmt='1,2,3,4',tag_view_fmt='1:意外险;2:健康险;3:理财险;4:少儿险' WHERE tag_var_name='productidea';


DROP TABLE IF EXISTS `tsr_operate_record`;

CREATE TABLE `tsr_operate_record` (
  `rid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `comid` int(11) DEFAULT NULL COMMENT '公司id',
  `custid` int(11) DEFAULT NULL COMMENT '客服id',
  `op_type` varchar(20) DEFAULT NULL COMMENT '操作类型。serach：搜索 ；browse：浏览(足迹)；favorite：收藏',
  `op_object` varchar(20) DEFAULT NULL COMMENT '操作对象',
  `op_object_id` int(11) DEFAULT NULL COMMENT '操作对象id',
  `op_object_content` varchar(200) DEFAULT NULL COMMENT '操作对象内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='客服操作记录表';


INSERT INTO sysparameter VALUES(
'TSR_CONTENT_GRADE',
'tsr文章技能等级',
'[ { "0": "其他" }, { "1": "倔强青铜" }, { "2": "秩序白银" }, { "3": "荣耀黄金" }, { "4": "尊贵铂金" }, { "5": "永恒钻石" }, { "6": "最强王者" } ]',
NULL,NULL
);


INSERT INTO sysparameter VALUES(
'TSR_CONTENT_ANSWER',
'tsr文章技能答案',
'{ "1": "A", "2": "C", "3": "D", "4": "C", "5": "B", "6": "A", "7": "B", "8": "C", "9": "A", "10": "C", "11": "B", "12": "D", "13": "D", "14": "B", "15": "C", "16": "C", "17": "C", "18": "B", "19": "A", "20": "D", "21": "D", "22": "D", "23": "A", "24": "A", "25": "B" }',
NULL,NULL
);


ALTER TABLE Content ADD tsr_content_grade int(11) COMMENT '文章技能等级';

DROP TABLE IF EXISTS `content_relation`;

CREATE TABLE `content_relation` (
  `cr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `comid` int(11) DEFAULT NULL COMMENT '公司id',
  `mainid` int(11) DEFAULT NULL COMMENT '主id（文章id，栏目id）',
  `relaid` int(11) DEFAULT NULL COMMENT '关联的文章id',
  `rela_type` varchar(20) DEFAULT NULL COMMENT '关联类型 hot：热门  spcolumn：专栏  content：文章',
  `orderby` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`cr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章关联表';


DROP TABLE IF EXISTS `column_configue`;

CREATE TABLE `column_configue` (
  `cc_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `comid` int(11) DEFAULT NULL COMMENT '公司id',
  `cc_type` varchar(20) DEFAULT NULL COMMENT '栏目类型 adCloumn：广告栏目  conCloumn：文章栏目',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `link` varchar(200) DEFAULT NULL COMMENT '链接',
  `icon` varchar(50) DEFAULT NULL COMMENT '图片',
  `orderby` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`cc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='栏目配置表';


ALTER TABLE tsr_score_log ADD (
rela_id int(11) COMMENT '关联id', 
remark VARCHAR(200) COMMENT '备注'
);


DROP TABLE IF EXISTS `tsr_cust_login_log`;
CREATE TABLE `tsr_cust_login_log` (
  `log_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `comid` INT(11) NOT NULL COMMENT '公司id',
  `custid` INT(11) NOT NULL COMMENT '客服id',
  `ip` VARCHAR(50) DEFAULT NULL COMMENT 'ip',
  `device` VARCHAR(20) DEFAULT NULL COMMENT '设备',
  `device_type` VARCHAR(20) DEFAULT NULL COMMENT '设备类型',
  `system_info` VARCHAR(20) DEFAULT NULL COMMENT '系统',
  `browser` VARCHAR(20) DEFAULT NULL COMMENT '浏览器',
  `browser_type` VARCHAR(20) DEFAULT NULL COMMENT '浏览器类型',
  `browser_version` VARCHAR(20) DEFAULT NULL COMMENT '浏览器版本',
  `user_agent` VARCHAR(500) DEFAULT NULL COMMENT '用户标识详情',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='客服登录日志表';

DROP TABLE IF EXISTS tsr_customer_task;
CREATE TABLE `tsr_customer_task` (
  `cust_task_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comid` INT(11) DEFAULT NULL COMMENT '公司ID',
  `base_type` VARCHAR(2) DEFAULT NULL COMMENT '类型,0:固定任务;1:自定义任务',
  `task_name` VARCHAR(20) DEFAULT NULL COMMENT '任务名称',
  `target` VARCHAR(40) DEFAULT NULL COMMENT '目标',
  `task_type_code` VARCHAR(20) DEFAULT NULL COMMENT '任务类型code',
  `task_type` VARCHAR(20) DEFAULT NULL COMMENT '任务类型,college:学堂;getClient:获客;market:营销;birthMarket:生日营销;greetCard:贺卡;poll:调查问卷;otherOne:其他(一次性);otherDate:其他（按任务日期长期提醒）businCard:名片;signIn:签到;skillTest:技能体检;circle:朋友圈助手',
  `task_finish_sign` VARCHAR(2) DEFAULT NULL COMMENT '任务完成标志,10:一次性;11:按任务执行日期;12:按任务触发提示',
  `task_link` VARCHAR(200) DEFAULT NULL COMMENT '任务链接',
  `rule_id` INT(11) DEFAULT NULL COMMENT '积分规则',
  `has_pop_up` VARCHAR(2) DEFAULT NULL COMMENT '是否首页弹窗提示,0:是;1:否',
  `task_describe`  VARCHAR(400) DEFAULT NULL COMMENT '任务描述',
  `status` INT(11) DEFAULT NULL COMMENT '状态',
  `start_time` DATETIME DEFAULT NULL COMMENT '任务开始时间',
  `end_time` DATETIME DEFAULT NULL COMMENT '任务结束时间',
  `createtime` DATETIME DEFAULT NULL COMMENT '创建时间',
  `updatetime` DATETIME DEFAULT NULL COMMENT '更改时间',
  `get_point` INT(11) DEFAULT NULL COMMENT '任务指定的内容id',
  `icon` VARCHAR(80) DEFAULT NULL COMMENT '图片',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`cust_task_id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='客服任务表';


alter table tsr_score_rule add `cycle_intervals` varchar(50) DEFAULT NULL COMMENT '周期间隔[单位:分钟](周期次数大于1时使用)';


INSERT INTO `tsr_score_rule` 
(`id`,`com_id`,`name`,`code`,`cycle`,`rewardnum`,`single_score`,`cycle_intervals`) VALUES 
(3,19,'完善名片','businCard','3','1','5',null),(4,36,'完善名片','businCard','3','1','5',null),
(5,19,'技能体检','skillTest','0','1','5',null),(6,36,'技能体检','skillTest','0','1','5',null),
(7,19,'生日送贺卡','birthMarket','3','1','0',null),(8,36,'生日送贺卡','birthMarket','3','1','0',null);

update tsr_score_rule set cycle_intervals=86400 where code='signIn';


update  customer set score = 0 where score is null;

ALTER TABLE `tsr_content_share_log` 
ADD COLUMN `user_type` int(2) AFTER `update_time`,
ADD COLUMN `openid` varchar(50) AFTER `user_type`,
ADD COLUMN `nick_name` varchar(50) AFTER `openid`,
ADD COLUMN `head_url` varchar(200) AFTER `nick_name`;

update tsr_content_share_log set user_type = 0;

ALTER TABLE `user_content_read_log` 
ADD COLUMN `openid` varchar(50) AFTER `update_time`,
ADD COLUMN `nick_name` varchar(50) AFTER `openid`,
ADD COLUMN `head_url` varchar(200) AFTER `nick_name`;



ALTER TABLE `user_content_read_log`  ADD INDEX `idx_user_content_read_openid`(`openid`);

ALTER TABLE `tsr_content_share_log` ADD INDEX `idx_content_share_openid`(`openid`);

ALTER TABLE `tsr_user_send_record` ADD INDEX `idx_record_cust_ext`(`custid`, `ext_id`);


ALTER TABLE `poll_result` 
ADD COLUMN `head_url` varchar(200) AFTER `result`,
ADD COLUMN `nick_name` varchar(50) AFTER `head_url`,
ADD COLUMN `openid` varchar(50) AFTER `nick_name`;

ALTER TABLE `poll_result` ADD INDEX `idx_poll_res_openid`(`openid`);






