package com.example.modules.custom.bean;

import java.util.List;

public class TxtJsonInfo {
	private String msg = "成功";
	private Integer result = 0;
	private List<TxtChapters> chapters;
	private String jsonp;
	private String title;
	
	public String getJsonp() {
		return jsonp;
	}
	public void setJsonp(String jsonp) {
		this.jsonp = jsonp;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public List<TxtChapters> getChapters() {
		return chapters;
	}
	public void setChapters(List<TxtChapters> chapters) {
		this.chapters = chapters;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
