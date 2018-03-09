package com.example.demo;

import java.util.Map;

/***
 * dcc活动数据
 * @ClassName:DccUseractivInfo
 * @Description:(描述)
 * @author:hujunhua
 * @date:2017年12月27日 下午7:47:31
 *
 */
public class DccUseractivInfo {
	private String activityId;
	
	private String mobile;
	
	private Map<String, Object> info;

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Map<String, Object> getInfo() {
		return info;
	}

	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}
}
