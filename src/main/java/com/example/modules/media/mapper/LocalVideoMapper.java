package com.example.modules.media.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.modules.media.entity.LocalVideo;

public interface LocalVideoMapper {
    @Delete({
        "delete from local_video",
        "where video_id = #{videoId,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String videoId);

    @Insert({
        "insert into local_video (video_id, root_path, ",
        "relative_path, file_name, ",
        "file_type, file_size, ",
        "sub_title, backup_path, ",
        "backup_subtitle_path, create_time, ",
        "md5_code, status, ",
        "remark)",
        "values (#{videoId,jdbcType=VARCHAR}, #{rootPath,jdbcType=VARCHAR}, ",
        "#{relativePath,jdbcType=VARCHAR}, #{fileName,jdbcType=VARCHAR}, ",
        "#{fileType,jdbcType=VARCHAR}, #{fileSize,jdbcType=VARCHAR}, ",
        "#{subTitle,jdbcType=VARCHAR}, #{backupPath,jdbcType=VARCHAR}, ",
        "#{backupSubtitlePath,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{md5Code,jdbcType=VARCHAR}, #{status,jdbcType=INTEGER}, ",
        "#{remark,jdbcType=VARCHAR})"
    })
    int insert(LocalVideo record);

    int insertSelective(LocalVideo record);

    @Select({
        "select",
        "video_id, root_path, relative_path, file_name, file_type, file_size, sub_title, ",
        "backup_path, backup_subtitle_path, create_time, md5_code, status, remark",
        "from local_video",
        "where video_id = #{videoId,jdbcType=VARCHAR}"
    })
    @ResultMap("com.example.modules.media.mapper.LocalVideoMapper.BaseResultMap")
    LocalVideo selectByPrimaryKey(String videoId);

    int updateByPrimaryKeySelective(LocalVideo record);

    @Update({
        "update local_video",
        "set root_path = #{rootPath,jdbcType=VARCHAR},",
          "relative_path = #{relativePath,jdbcType=VARCHAR},",
          "file_name = #{fileName,jdbcType=VARCHAR},",
          "file_type = #{fileType,jdbcType=VARCHAR},",
          "file_size = #{fileSize,jdbcType=VARCHAR},",
          "sub_title = #{subTitle,jdbcType=VARCHAR},",
          "backup_path = #{backupPath,jdbcType=VARCHAR},",
          "backup_subtitle_path = #{backupSubtitlePath,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "md5_code = #{md5Code,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=INTEGER},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where video_id = #{videoId,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(LocalVideo record);
    
    @Select({
        "select",
        "video_id, root_path, relative_path, file_name, file_type, file_size, sub_title, ",
        "backup_path, backup_subtitle_path, create_time, md5_code, status, remark",
        "from local_video"
    })
    @ResultMap("com.example.modules.media.mapper.LocalVideoMapper.BaseResultMap")
    List<LocalVideo> queryAll();
}