SET NAMES utf8;
DROP TABLE IF EXISTS tsr_impress_tag;

CREATE TABLE `tsr_impress_tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签配置id',
  `tag_type` varchar(10) DEFAULT NULL COMMENT '标签配置类型U:用户标签',
  `tag_name` varchar(50) DEFAULT NULL COMMENT '标签名',
  `tag_view_name` varchar(50) DEFAULT NULL COMMENT '显示标签名',
  `is_etd_view` varchar(1) DEFAULT NULL COMMENT '是否编缉显示Y:N',
  `is_auto` varchar(1) DEFAULT NULL COMMENT '是否自动触发Y:N',
  `relevant_tag` varchar(50) DEFAULT NULL COMMENT '关联标签配置表多个以，|分隔',
  `view_orderby` int(11) DEFAULT NULL COMMENT '显示排序',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='TSR印象标签配置表';

DROP TABLE IF EXISTS tsr_impress_tag_content;
CREATE TABLE `tsr_impress_tag_content` (
  `tag_id` INT(11) NOT NULL COMMENT '标签id',
  `contentid` INT(11) NOT NULL COMMENT '文章id',
  `comid` INT(11) NOT NULL COMMENT '企业id',
  `view_orderby` INT(11) DEFAULT NULL COMMENT '显示排序',
  `create_date` DATETIME DEFAULT NULL COMMENT '创建时间',
  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  UNIQUE KEY `AK_TSR_IMP_TAG_CON` (`tag_id`,`contentid`,`comid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='TSR印象标签文章关联表';

DROP TABLE IF EXISTS tsr_impress_tag_user;
CREATE TABLE `tsr_impress_tag_user` (
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  `union_id` int(11) NOT NULL COMMENT '用户联合id',
  `comid` int(11) NOT NULL COMMENT '企业id',
  `is_auto` varchar(1) DEFAULT NULL COMMENT '是否自动触发Y:N',
  `view_orderby` int(11) DEFAULT NULL COMMENT '显示排序',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  UNIQUE KEY `AK_TSR_IMP_TAG_USER` (`tag_id`,`union_id`,`comid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='TSR印象标签用户关联表';


ALTER TABLE `customer` ADD COLUMN `work_month` varchar(10) COMMENT '司龄(月)' AFTER `photo`;

UPDATE tsr_taglib_cfg SET is_etd_view='N' WHERE tag_var_name='age';



