package com.example.modules.tieba.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.modules.tieba.entity.TiebaTiezi;

public interface TiebaTieziMapper {
	@Delete({ "delete from ${tableName}", "where tz_id = #{tzId,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String tzId);

	@Insert({ "insert into ${tableName} (tz_id, author_id, ", "author_name, title, ", "content, post_s_date, ",
			"post_date, reply_s_date, ", "reply_date, reply_num, ", "replyer_id, replyer_name, ",
			"auditer_id, auditer_name, ", "is_top, is_fetch, ", "last_fetch_time, first_fetch_time, ", "create_time)",
			"values (#{tzId,jdbcType=VARCHAR}, #{authorId,jdbcType=VARCHAR}, ",
			"#{authorName,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR}, ",
			"#{content,jdbcType=VARCHAR}, #{postSDate,jdbcType=VARCHAR}, ",
			"#{postDate,jdbcType=TIMESTAMP}, #{replySDate,jdbcType=VARCHAR}, ",
			"#{replyDate,jdbcType=TIMESTAMP}, #{replyNum,jdbcType=VARCHAR}, ",
			"#{replyerId,jdbcType=VARCHAR}, #{replyerName,jdbcType=VARCHAR}, ",
			"#{auditerId,jdbcType=VARCHAR}, #{auditerName,jdbcType=VARCHAR}, ",
			"#{isTop,jdbcType=VARCHAR}, #{isFetch,jdbcType=VARCHAR}, ",
			"#{lastFetchTime,jdbcType=TIMESTAMP}, #{firstFetchTime,jdbcType=TIMESTAMP}, ",
			"#{createTime,jdbcType=TIMESTAMP})" })
	int insert(TiebaTiezi record);

	int insertSelective(TiebaTiezi record);

	@Select({ "select", "tz_id, author_id, author_name, title, content, post_s_date, post_date, reply_s_date, ",
			"reply_date, reply_num, replyer_id, replyer_name, auditer_id, auditer_name, is_top, ",
			"is_fetch, last_fetch_time, first_fetch_time, create_time", "from ${tableName}",
			"where tz_id = #{tzId,jdbcType=VARCHAR}" })
	@ResultMap("com.example.modules.tieba.mapper.TiebaTieziMapper.BaseResultMap")
	TiebaTiezi selectByPrimaryKey(String tzId);

	int updateByPrimaryKeySelective(TiebaTiezi record);

	@Update({ "update ${tableName}", "set author_id = #{authorId,jdbcType=VARCHAR},",
			"author_name = #{authorName,jdbcType=VARCHAR},", "title = #{title,jdbcType=VARCHAR},",
			"content = #{content,jdbcType=VARCHAR},", "post_s_date = #{postSDate,jdbcType=VARCHAR},",
			"post_date = #{postDate,jdbcType=TIMESTAMP},", "reply_s_date = #{replySDate,jdbcType=VARCHAR},",
			"reply_date = #{replyDate,jdbcType=TIMESTAMP},", "reply_num = #{replyNum,jdbcType=VARCHAR},",
			"replyer_id = #{replyerId,jdbcType=VARCHAR},", "replyer_name = #{replyerName,jdbcType=VARCHAR},",
			"auditer_id = #{auditerId,jdbcType=VARCHAR},", "auditer_name = #{auditerName,jdbcType=VARCHAR},",
			"is_top = #{isTop,jdbcType=VARCHAR},", "is_fetch = #{isFetch,jdbcType=VARCHAR},",
			"last_fetch_time = #{lastFetchTime,jdbcType=TIMESTAMP},",
			"first_fetch_time = #{firstFetchTime,jdbcType=TIMESTAMP},",
			"create_time = #{createTime,jdbcType=TIMESTAMP}", "where tz_id = #{tzId,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(TiebaTiezi record);
}