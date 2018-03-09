package com.example.demo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class DccUserDataProcessUtil {
	
	private static Map<String, String> ourMap = new HashMap<>();
	private static Pattern pattern = Pattern.compile("\\[(.*?)\\]");
	static {
		ourMap.put("婚姻情况-已婚", "marriage:1");
		ourMap.put("婚姻情况-单身", "marriage:0");
		ourMap.put("子女情况-有", "children:1");
		ourMap.put("子女情况-没有", "children:0");
		
		ourMap.put("是否购买保险-社保", "socialsecurity:1");
		ourMap.put("是否购买保险-商保", "policy:1");
		ourMap.put("是否购买保险-都没有", "socialsecurity:0;policy:0");
		ourMap.put("是否购买保险-社保与商保均有", "socialsecurity:1;policy:1");

		ourMap.put("是否关注养老保险-是", "productidea:3");
		ourMap.put("是否关注意外-是", "productidea:1");
		ourMap.put("是否关注健康-是", "productidea:2");
		ourMap.put("商业险意愿投入金额（月）- <300", "moincome:-3000");
		ourMap.put("商业险意愿投入金额（月）-300-500", "moincome:3000-8000");
		ourMap.put("商业险意愿投入金额（月）-500-1000", "moincome:8000-15000");
		ourMap.put("商业险意愿投入金额（月）->1000", "moincome:15000-30000");

		ourMap.put("职业类别-普通职员", "industry:4");
		ourMap.put("职业类别-私营企业主", "industry:5");
		ourMap.put("职业类别-公务员、事业单位职员", "industry:2");
		ourMap.put("职业类别-专业人士（会计师、律师、经济师、教授等）", "industry:1");
		ourMap.put("职业类别-公司高管", "industry:3");
		ourMap.put("职业类别-自由职业", "industry:6");

		ourMap.put("曾购买险种-意外险", "productidea:1");
		ourMap.put("曾购买险种-重疾险", "productidea:2");
		ourMap.put("曾购买险种-健康险", "productidea:2");
		ourMap.put("曾购买险种-养老险", "productidea:3");
		ourMap.put("曾购买险种-少儿教育金", "productidea:4");
		ourMap.put("曾购买险种-理财险", "productidea:3");

		ourMap.put("是否购车-是", "hadcar:1");
		ourMap.put("是否购车-否", "hadcar:0");
		ourMap.put("是否购房-是", "hadhouse:1");
		ourMap.put("是否购房-否", "hadhouse:0");

		ourMap.put("出行习惯-自驾车", "hadcar:1");
	}
	
	public static Map<String, String> parseDccDataToTag(Map<String, String> dccData){
		Map<String, String> taginfo = new HashMap<>();
		if(StringUtils.isNotBlank(dccData.get("name"))) {
			taginfo.put("name", dccData.get("name"));
		}
		if(StringUtils.isNotBlank(dccData.get("sex"))) {
			String sex = "2".equals(dccData.get("sex"))?"0":dccData.get("sex");
			taginfo.put("sex", sex);
		}
		if(StringUtils.isNotBlank(dccData.get("birthday"))) {
			long bday = Long.parseLong(dccData.get("birthday"))*1000;
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(bday);
			taginfo.put("birthday", DateFormatUtils.format(cal, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotBlank(dccData.get("idcard"))) {
			taginfo.put("idcard", dccData.get("idcard"));
		}
		if(StringUtils.isNotBlank(dccData.get("email"))) {
			taginfo.put("email", dccData.get("email"));
		}
		if (StringUtils.isNotBlank(dccData.get("mobileProvinceId"))) {
			
		}
		if (StringUtils.isNotBlank(dccData.get("mobileCityId"))) {
			
		}
		int s = 0;
		if (StringUtils.isNotBlank(dccData.get("question1"))) {
			s++;
			Matcher matcher = pattern.matcher(dccData.get("question1"));
			String src = "";
			while (matcher.find()) {
				src = matcher.group(1);
				if (ourMap.containsKey(src)) {
					parseQuestion(taginfo,ourMap.get(src));
				}
			}
		}
		if (StringUtils.isNotBlank(dccData.get("question2"))) {
			s++;
			Matcher matcher = pattern.matcher(dccData.get("question2"));
			String src = "";
			while (matcher.find()) {
				src = matcher.group(1);
				if (ourMap.containsKey(src)) {
					parseQuestion(taginfo,ourMap.get(src));
				}
			}
		}
		if (StringUtils.isNotBlank(dccData.get("question3"))) {
			s++;
			Matcher matcher = pattern.matcher(dccData.get("question3"));
			String src = "";
			while (matcher.find()) {
				src = matcher.group(1);
				if (ourMap.containsKey(src)) {
					parseQuestion(taginfo,ourMap.get(src));
				}
			}
		}
		//完成了调查问卷
		if(s==3) {
			taginfo.put("hasquestionnaire", "1");
		}
		return taginfo;
	}
	
	private static void parseQuestion(Map<String, String> dataMap,String matcherData) {
		String[] data = matcherData.split(";");
		for(String dd:data) {
			String[] ds = dd.split(":");
			dataMap.put(ds[0], ds[1]);
		}
	}

}
