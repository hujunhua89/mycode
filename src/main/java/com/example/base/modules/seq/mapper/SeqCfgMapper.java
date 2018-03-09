package com.example.base.modules.seq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.base.modules.seq.entity.SeqCfg;

public interface SeqCfgMapper {
	@Delete({ "delete from seq_cfg", "where seq_name = #{seqName,jdbcType=VARCHAR}" })
	int deleteByPrimaryKey(String seqName);

	@Insert({ "insert into seq_cfg (seq_name, init_val, ", "stock_val, current_val, ", "increment_val, status, ",
			"init_time)", "values (#{seqName,jdbcType=VARCHAR}, #{initVal,jdbcType=INTEGER}, ",
			"#{stockVal,jdbcType=INTEGER}, #{currentVal,jdbcType=VARCHAR}, ",
			"#{incrementVal,jdbcType=INTEGER}, #{status,jdbcType=INTEGER}, ", "#{initTime,jdbcType=TIMESTAMP})" })
	int insert(SeqCfg record);

	int insertSelective(SeqCfg record);

	@Select({ "select", "seq_name, init_val, stock_val, current_val, increment_val, status, init_time", "from seq_cfg",
			"where seq_name = #{seqName,jdbcType=VARCHAR}" })
	@ResultMap("com.example.base.modules.seq.mapper.SeqCfgMapper.BaseResultMap")
	SeqCfg selectByPrimaryKey(String seqName);

	int updateByPrimaryKeySelective(SeqCfg record);

	@Update({ "update seq_cfg", "set init_val = #{initVal,jdbcType=INTEGER},",
			"stock_val = #{stockVal,jdbcType=INTEGER},", "current_val = #{currentVal,jdbcType=VARCHAR},",
			"increment_val = #{incrementVal,jdbcType=INTEGER},", "status = #{status,jdbcType=INTEGER},",
			"init_time = #{initTime,jdbcType=TIMESTAMP}", "where seq_name = #{seqName,jdbcType=VARCHAR}" })
	int updateByPrimaryKey(SeqCfg record);

	@Select({ "select", "*", "from seq_cfg", "where status = 0 and DATE_FORMAT(init_time,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d')" })
	@ResultMap("com.example.base.modules.seq.mapper.SeqCfgMapper.BaseResultMap")
	List<SeqCfg> queryAllUse();

	@Update({ "update seq_cfg", "set  current_val= init_val,init_time=now() where DATE_FORMAT(init_time,'%Y-%m-%d') != DATE_FORMAT(now(),'%Y-%m-%d')" })
	int init();
}