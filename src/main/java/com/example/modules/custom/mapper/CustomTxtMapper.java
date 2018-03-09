package com.example.modules.custom.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.example.modules.custom.entity.CustomTxt;
import com.example.modules.custom.entity.extension.CustomTxtExtension;
import com.example.modules.custom.mapper.provider.CustomTxtSqlProvider;

public interface CustomTxtMapper {
    @Delete({
        "delete from ${tableName}",
        "where txt_id = #{txtId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer txtId);

    @Insert({
        "insert into ${tableName} (txt_id, txt_url, ",
        "txt_class, author_name, ",
        "title, content, ",
        "date_str, is_fetch, ",
        "last_fetch_time, first_fetch_time, ",
        "create_time, orderby, ",
        "mark_flag, remark)",
        "values (#{txtId,jdbcType=INTEGER}, #{txtUrl,jdbcType=VARCHAR}, ",
        "#{txtClass,jdbcType=VARCHAR}, #{authorName,jdbcType=VARCHAR}, ",
        "#{title,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, ",
        "#{dateStr,jdbcType=VARCHAR}, #{isFetch,jdbcType=VARCHAR}, ",
        "#{lastFetchTime,jdbcType=TIMESTAMP}, #{firstFetchTime,jdbcType=TIMESTAMP}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{orderby,jdbcType=INTEGER}, ",
        "#{markFlag,jdbcType=VARCHAR}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(CustomTxt record);

    int insertSelective(CustomTxt record);
    
    @Select({
        "select",
        "txt_id, txt_url, txt_class, author_name, title, content, date_str, is_fetch, ",
        "last_fetch_time, first_fetch_time, create_time, orderby, mark_flag, remark",
        "from ${tableName}",
        "where txt_id = #{txtId,jdbcType=INTEGER}"
    })
    @ResultMap("com.example.modules.custom.mapper.CustomTxtMapper.BaseResultMap")
    CustomTxt selectByPrimaryKey(CustomTxt record);

    int updateByPrimaryKeySelective(CustomTxt record);

    @Update({
        "update ${tableName}",
        "set txt_url = #{txtUrl,jdbcType=VARCHAR},",
          "txt_class = #{txtClass,jdbcType=VARCHAR},",
          "author_name = #{authorName,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "date_str = #{dateStr,jdbcType=VARCHAR},",
          "is_fetch = #{isFetch,jdbcType=VARCHAR},",
          "last_fetch_time = #{lastFetchTime,jdbcType=TIMESTAMP},",
          "first_fetch_time = #{firstFetchTime,jdbcType=TIMESTAMP},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "orderby = #{orderby,jdbcType=INTEGER},",
          "mark_flag = #{markFlag,jdbcType=VARCHAR},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where txt_id = #{txtId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CustomTxt record);
    
    @SelectProvider(type=CustomTxtSqlProvider.class,method="queryTitieAll")
    @ResultMap("com.example.modules.custom.mapper.CustomTxtMapper.BaseResultMap")
    List<CustomTxt> queryTitieAll(CustomTxtExtension record);
    
    @SelectProvider(type=CustomTxtSqlProvider.class,method="countTitieAll")
    @ResultType(Integer.class)
    int countTitieAll(CustomTxtExtension record);
    
    
}