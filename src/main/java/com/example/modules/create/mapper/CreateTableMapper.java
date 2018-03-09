package com.example.modules.create.mapper;

import org.apache.ibatis.annotations.Update;

import com.example.modules.create.entity.CreateTableEntity;
/***
 * 自定义建表
 * @ClassName:CreateTableMapper
 * @Description:(描述)
 * @author:hujunhua
 * @date:2018年1月17日 上午11:05:13
 *
 */
public interface CreateTableMapper {
	
	 @Update({"CREATE TABLE ${tableName} (" + 
	    		"  `tz_id` VARCHAR(20) NOT NULL COMMENT '贴子id'," + 
	    		"  `author_id` VARCHAR(20) DEFAULT NULL COMMENT '作者id'," + 
	    		"  `author_name` VARCHAR(50) DEFAULT NULL COMMENT '作者名'," + 
	    		"  `title` VARCHAR(500) COMMENT '标题'," + 
	    		"  `content` VARCHAR(500) COMMENT '内容'," + 
	    		"  `post_s_date` VARCHAR(20) DEFAULT NULL COMMENT '发贴时间标识'," + 
	    		"  `post_date` DATETIME DEFAULT NULL COMMENT '发贴时间标识'," + 
	    		"  `reply_s_date` VARCHAR(20) DEFAULT NULL COMMENT '回贴时间标识'," + 
	    		"  `reply_date` DATETIME DEFAULT NULL COMMENT '回贴时间标识'," + 
	    		"  `reply_num` VARCHAR(20) DEFAULT NULL COMMENT '回贴数量'," + 
	    		"  `replyer_id` VARCHAR(20) DEFAULT NULL COMMENT '回贴id'," + 
	    		"  `replyer_name` VARCHAR(50) DEFAULT NULL COMMENT '回贴人名'," + 
	    		"  `auditer_id` VARCHAR(20) DEFAULT NULL COMMENT '审核人id'," + 
	    		"  `auditer_name` VARCHAR(50) DEFAULT NULL COMMENT '审核人名'," + 
	    		"  `is_top` VARCHAR(1) DEFAULT 'N' COMMENT '是否置顶'," + 
	    		"  `is_fetch` VARCHAR(1) DEFAULT 'N' COMMENT '是否抓取'," + 
	    		"  `last_fetch_time` DATETIME DEFAULT NULL COMMENT '最后抓取时间'," + 
	    		"  `first_fetch_time` DATETIME DEFAULT NULL COMMENT '首次抓取时间'," + 
	    		"  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间'," + 
	    		"  PRIMARY KEY (`tz_id`)" + 
	    		") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT=#{tableComment,jdbcType=VARCHAR}"})
	int createTiezi(CreateTableEntity table);
	 
	 
	 @Update({"CREATE TABLE ${tableName} (" + 
	 		"  `txt_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自定义id'," + 
	 		"  `txt_url` VARCHAR(100) DEFAULT NULL COMMENT '地址'," + 
	 		"  `txt_class` VARCHAR(50) DEFAULT NULL COMMENT '分类'," + 
	 		"  `author_name` VARCHAR(50) DEFAULT NULL COMMENT '作者'," + 
	 		"  `title` VARCHAR(100) DEFAULT NULL COMMENT '标题'," + 
	 		"  `content` LONGTEXT DEFAULT NULL COMMENT '内容'," + 
	 		"  `date_str` VARCHAR(20) DEFAULT NULL COMMENT '时间标识'," + 
	 		"  `is_fetch` VARCHAR(1) DEFAULT 'N' COMMENT '是否抓取'," + 
	 		"  `last_fetch_time` DATETIME DEFAULT NULL COMMENT '最后抓取时间'," + 
	 		"  `first_fetch_time` DATETIME DEFAULT NULL COMMENT '首次抓取时间'," + 
	 		"  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间'," + 
	 		"  `orderby` INT(11) DEFAULT 0 COMMENT '自定义id'," + 
	 		"  `mark_flag` VARCHAR(50) DEFAULT NULL COMMENT '标记'," + 
	 		"  `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注'," + 
	 		"  PRIMARY KEY (`txt_id`)" + 
	 		") ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT=#{tableComment,jdbcType=VARCHAR}"})
	int createCustomTxt(CreateTableEntity table);

}
