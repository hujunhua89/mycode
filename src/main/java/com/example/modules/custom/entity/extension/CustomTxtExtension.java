package com.example.modules.custom.entity.extension;

import com.example.modules.custom.entity.CustomTxt;

public class CustomTxtExtension extends CustomTxt {
	 private Integer limitStart;
	 private Integer limitLen;
	 private String  likeTitle;
	 private String orderbycol;
	public String getOrderbycol() {
		return orderbycol;
	}
	public void setOrderbycol(String orderbycol) {
		this.orderbycol = orderbycol;
	}
	public Integer getLimitStart() {
		return limitStart;
	}
	public void setLimitStart(Integer limitStart) {
		this.limitStart = limitStart;
	}
	public Integer getLimitLen() {
		return limitLen;
	}
	public void setLimitLen(Integer limitLen) {
		this.limitLen = limitLen;
	}
	public String getLikeTitle() {
		return likeTitle;
	}
	public void setLikeTitle(String likeTitle) {
		this.likeTitle = likeTitle==null?likeTitle:("%"+likeTitle+"%");
	} 
	 
	
}
