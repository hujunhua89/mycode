package com.example.modules.custom.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

import com.example.modules.custom.entity.extension.CustomTxtExtension;

public class CustomTxtSqlProvider {
	public String queryTitieAll(CustomTxtExtension record){
		SQL sql = new SQL();
		sql.SELECT("txt_id,txt_url,title,remark");
		sql.FROM(record.getTableName());
		if(record.getTxtClass()!=null) {
			sql.WHERE("txt_class = #{txtClass,jdbcType=VARCHAR}");
		}
		if(record.getLikeTitle()!=null) {
			sql.WHERE("title like #{likeTitle,jdbcType=VARCHAR}");
		}
		if(record.getIsFetch()!=null) {
			sql.WHERE("is_fetch = #{isFetch,jdbcType=VARCHAR}");
		}
		if(record.getOrderbycol()!=null) {
			sql.ORDER_BY(record.getOrderbycol());
		}
		
		if(record.getLimitStart()!=null&&record.getLimitLen()!=null) {
			return sql.toString()+" limit #{limitStart,jdbcType=INTEGER},#{limitLen,jdbcType=INTEGER}";
		}
		return sql.toString();
	}
	public static void main(String[] args) {
		CustomTxtSqlProvider sql = new CustomTxtSqlProvider();
		CustomTxtExtension record = new CustomTxtExtension();
		record.setLikeTitle("斗破");
		record.setRuleFlag("a");
		System.out.println(sql.queryTitieAll(record));
	}
	
	public String countTitieAll(CustomTxtExtension record){
		SQL sql = new SQL();
		sql.SELECT("count(*)");
		sql.FROM(record.getTableName());
		if(record.getTxtClass()!=null) {
			sql.WHERE("txt_class = #{txtClass,jdbcType=VARCHAR}");
		}
		if(record.getLikeTitle()!=null) {
			sql.WHERE("title like #{likeTitle,jdbcType=VARCHAR}");
		}
		if(record.getIsFetch()!=null) {
			sql.WHERE("is_fetch = #{isFetch,jdbcType=VARCHAR}");
		}
		return sql.toString();
	}
}
