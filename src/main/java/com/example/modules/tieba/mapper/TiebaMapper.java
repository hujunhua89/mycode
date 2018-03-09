package com.example.modules.tieba.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.modules.tieba.entity.Tieba;

public interface TiebaMapper {
    @Delete({
        "delete from tieba",
        "where t_id = #{tId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer tId);

    @Insert({
        "insert into tieba (t_id, tieba_id, ",
        "tieba_name, first_class, ",
        "second_class, tieba_url, ",
        "tieba_info, tieba_zt_num, ",
        "tieba_tz_num, tieba_people_num, ",
        "is_fetch, last_fetch_time, ",
        "first_fetch_time, create_time)",
        "values (#{tId,jdbcType=INTEGER}, #{tiebaId,jdbcType=VARCHAR}, ",
        "#{tiebaName,jdbcType=VARCHAR}, #{firstClass,jdbcType=VARCHAR}, ",
        "#{secondClass,jdbcType=VARCHAR}, #{tiebaUrl,jdbcType=VARCHAR}, ",
        "#{tiebaInfo,jdbcType=VARCHAR}, #{tiebaZtNum,jdbcType=VARCHAR}, ",
        "#{tiebaTzNum,jdbcType=VARCHAR}, #{tiebaPeopleNum,jdbcType=VARCHAR}, ",
        "#{isFetch,jdbcType=VARCHAR}, #{lastFetchTime,jdbcType=TIMESTAMP}, ",
        "#{firstFetchTime,jdbcType=TIMESTAMP}, #{createTime,jdbcType=TIMESTAMP})"
    })
    @Options(useGeneratedKeys=true,keyProperty="tId")
    int insert(Tieba record);

    int insertSelective(Tieba record);

    @Select({
        "select",
        "t_id, tieba_id, tieba_name, first_class, second_class, tieba_url, tieba_info, ",
        "tieba_zt_num, tieba_tz_num, tieba_people_num, is_fetch, last_fetch_time, first_fetch_time, ",
        "create_time",
        "from tieba",
        "where t_id = #{tId,jdbcType=INTEGER}"
    })
    @ResultMap("com.example.modules.tieba.mapper.TiebaMapper.BaseResultMap")
    Tieba selectByPrimaryKey(Integer tId);

    int updateByPrimaryKeySelective(Tieba record);

    @Update({
        "update tieba",
        "set tieba_id = #{tiebaId,jdbcType=VARCHAR},",
          "tieba_name = #{tiebaName,jdbcType=VARCHAR},",
          "first_class = #{firstClass,jdbcType=VARCHAR},",
          "second_class = #{secondClass,jdbcType=VARCHAR},",
          "tieba_url = #{tiebaUrl,jdbcType=VARCHAR},",
          "tieba_info = #{tiebaInfo,jdbcType=VARCHAR},",
          "tieba_zt_num = #{tiebaZtNum,jdbcType=VARCHAR},",
          "tieba_tz_num = #{tiebaTzNum,jdbcType=VARCHAR},",
          "tieba_people_num = #{tiebaPeopleNum,jdbcType=VARCHAR},",
          "is_fetch = #{isFetch,jdbcType=VARCHAR},",
          "last_fetch_time = #{lastFetchTime,jdbcType=TIMESTAMP},",
          "first_fetch_time = #{firstFetchTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where t_id = #{tId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Tieba record);
    
    
    @Select({
        "select",
        "t_id, tieba_id, tieba_name, first_class, second_class, tieba_url, tieba_info, ",
        "tieba_zt_num, tieba_tz_num, tieba_people_num, is_fetch, last_fetch_time, first_fetch_time, ",
        "create_time",
        "from tieba",
        "where is_fetch = 'Y'"
    })
    @ResultMap("com.example.modules.tieba.mapper.TiebaMapper.BaseResultMap")
    List<Tieba> queryAllFetchTieba();
}