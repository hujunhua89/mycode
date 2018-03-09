package com.example.demo;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.example.base.util.HttpSSLUtil;
import com.example.base.util.RSACryptUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestExl {
	// 默认单元格内容为数字时格式
	private static DecimalFormat df = new DecimalFormat("0");
	// 默认单元格格式化日期字符串
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 格式化数字
	private static DecimalFormat nf = new DecimalFormat("0.00");

	public static Map<String, String> ourMap = new HashMap<>();
	static {
		ourMap.put("婚姻情况-已婚", "A");
		ourMap.put("婚姻情况-单身", "A");
		ourMap.put("子女情况-有", "B");
		ourMap.put("子女情况-没有", "B");
		
		ourMap.put("是否购买保险-社保", "C");
		ourMap.put("是否购买保险-商保", "G");
		ourMap.put("是否购买保险-都没有", "C,G");
		ourMap.put("是否购买保险-社保与商保均有", "C,G");

		ourMap.put("是否关注养老保险-是", "D");
		ourMap.put("是否关注意外-是", "D");
		ourMap.put("是否关注健康-是", "D");
		ourMap.put("商业险意愿投入金额（月）- <300", "E");
		ourMap.put("商业险意愿投入金额（月）-300-500", "E");
		ourMap.put("商业险意愿投入金额（月）-500-1000", "E");
		ourMap.put("商业险意愿投入金额（月）->1000", "E");

		ourMap.put("职业类别-普通职员", "F");
		ourMap.put("职业类别-私营企业主", "F");
		ourMap.put("职业类别-公务员、事业单位职员", "F");
		ourMap.put("职业类别-专业人士（会计师、律师、经济师、教授等）", "F");
		ourMap.put("职业类别-公司高管", "F");
		ourMap.put("职业类别-自由职业", "F");

		ourMap.put("曾购买险种-意外险", "D");
		ourMap.put("曾购买险种-重疾险", "D");
		ourMap.put("曾购买险种-健康险", "D");
		ourMap.put("曾购买险种-养老险", "D");
		ourMap.put("曾购买险种-少儿教育金", "D");
		ourMap.put("曾购买险种-理财险", "D");

		ourMap.put("是否购车-是", "H");
		ourMap.put("是否购车-否", "H");
		ourMap.put("是否购房-是", "I");
		ourMap.put("是否购房-否", "I");

		ourMap.put("出行习惯-自驾车", "H");
	}

	public static void main(String[] args) {
		try {
			Map<String, Integer> qmap = new HashMap<>();
			Map<String, Integer> icmap = new HashMap<>();
			Gson gson = new Gson();
			String reg = "\\[(.*?)\\]";
			Pattern pattern = Pattern.compile(reg);
			int totol = 0;
			int namenum = 0;
			int sexnum = 0;
			int idnum = 0;
			int emailnum = 0;
			int bitrdaynum = 0;
			int mopronum = 0;
			int mocitynum = 0;
			int q1 = 0;
			int q2 = 0;
			int q3 = 0;
			int ictotol = 0;
			ArrayList<ArrayList<Object>> rowList = new ArrayList<ArrayList<Object>>();
			ArrayList<Object> colList;
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("F:\\openvpn\\201801091457.xlsx"));
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			List<DccUseractivInfo> activinfo = new ArrayList<>();
			for (int i = sheet.getFirstRowNum(), rowCount = 0; rowCount < sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				colList = new ArrayList<Object>();
				if (row == null) {
					// 当读取行为空时
					if (i != sheet.getPhysicalNumberOfRows()) {// 判断是否是最后一行
						rowList.add(colList);
					}
					continue;
				} else {
					rowCount++;
				}
				if (i == 0) {
					continue;
				}
				if (i <= 10) {
					continue;
				}
				if (i == 21) {
					break;
				}
				DccUseractivInfo dccUseractivInfo = new DccUseractivInfo();
				dccUseractivInfo.setActivityId(row.getCell(3).toString());
				dccUseractivInfo.setMobile(row.getCell(5).toString());
				String json = row.getCell(7).toString();
				Map<String, Object> dccData = gson.fromJson(json,
						new TypeToken<Map<String, Object>>() {
						}.getType());
				dccUseractivInfo.setInfo(dccData);
				activinfo.add(dccUseractivInfo);
				Map<String, String> dData = gson.fromJson(json,
						new TypeToken<Map<String, Object>>() {
						}.getType());
				
				Map<String, String> taginfo = DccUserDataProcessUtil.parseDccDataToTag(dData);
				System.out.println(dccUseractivInfo.getMobile()+gson.toJson(taginfo));
//				for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
//					cell = row.getCell(j);
//					if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
//						// 当该单元格为空
//						if (j != row.getLastCellNum()) {// 判断是否是该行中最后一个单元格
//							colList.add("");
//						}
//						continue;
//					}
//				} // end for j
				
			} // end for i

			System.out.println();
			String prikey ="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOhFLbePguyMFJOu4MjP29n0EQbqFtUziR4gctmVwM/J0eyR9zaH8AB2LLKEv9LhpAfgZWk58Xw9++IEDEyrDe0Z6tvnD5f0zdmU2GtrqxCpanXSAxBbqMkXEp87jHwLfIrLN0IFKB+8K9rhn5hKPzrzHzoKHktpeBpc3ZBf+RtxAgMBAAECgYANJg1mfMtOlMd9bMLw20N43SYxnALa4uX6PKADzURy/tvRZpn2Dk/51rbFDFrHl1pvz7Yt9MfE3sVpkA0aMfzBubmd0jgSgXFi6tB0UeKfAVJlpFChFfTGNuK/ncXTr/uNWvCcttgkJx8rBY6uRifXVlgy5zwERfS/Gksf2XRoIQJBAP6Lb2ybUh6AvSIlUHaa6egVRR7bmPTlxQjAjDxXx2lW1L1r7LP2Rz3wFAMjKj5Ll6La3Xzu9gFdy/BaE9spCMUCQQDpmSQsw7bCTEIcE1rUkR//M0/6WKM/KfTCw+Td/CkfgjhvXcfmLnb5YZSVxxo4PlV3N3DgnVueaBF94vns/Tq9AkB5ebmXLxiljfK01PHtaEiwhVYTP+FjHjRF1P4X3hxAXTvxmgvAZovhNy2VwbZrPFAFXEXPVYr5Y/XrolFPiemxAkAc9s4TdtjsUoRH8uZqQOxUukoKYn8rT8p3c86m76MxsvXIaTpmAhDibwT7ZUqM+r2iII+wahJ4G7M/hqsyhVuJAkA6w8nNJsw0jYav3bAOaSJsv+2cm3o4ULLmhIk9Y4zno40yXLBXvLsjPs9AssEb/uVEn4SB6MH4Z/7IOu7zz2Dp";
			String data = gson.toJson(activinfo);//转为json
	    	String datasing = RSACryptUtil.encryptByPrivateKey(data, prikey);//私钥加密-传输使base64编码
	    	String hostUrl = "http://localhost:8088/open_api/taikiang/registdata";
	    	String res  = HttpSSLUtil.postJson(hostUrl, datasing);
	    	System.out.println("通知S端返回:"+res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testjs() {
		
		
	}
	
	//Map<String, String> taginfo = DccUserDataProcessUtil.parseDccDataToTag(dccData);
	//System.out.println(taginfo);
//	if (StringUtils.isNotBlank(dccData.get("name"))) {
//		// System.out.print("姓名:"+dccData.get("name"));
//		namenum++;
//	}
//	if (StringUtils.isNotBlank(dccData.get("sex"))) {
//		String sex = "2".equals(dccData.get("sex")) ? "0" : dccData.get("sex");
//		// System.out.print("姓别:"+sex);
//		sexnum++;
//	}
//	if (StringUtils.isNotBlank(dccData.get("birthday"))) {
//		long bday = Long.parseLong(dccData.get("birthday")) * 1000;
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(bday);
//		// System.out.print("生日:"+DateFormatUtils.format(cal, "yyyy-MM-dd"));
//		bitrdaynum++;
//	}
//	if (StringUtils.isNotBlank(dccData.get("idcard"))) {
//		// System.out.print("身份证:"+dccData.get("idcard"));
//		idnum++;
//	}
//	if (StringUtils.isNotBlank(dccData.get("email"))) {
//		// System.out.print("邮箱:"+dccData.get("email"));
//		emailnum++;
//	}
//	if (StringUtils.isNotBlank(dccData.get("mobileProvinceId"))) {
//		// System.out.print("手机省份:"+dccData.get("mobileProvinceId"));
//		mopronum++;
//	}
//	if (StringUtils.isNotBlank(dccData.get("mobileCityId"))) {
//		// System.out.print("手机城市:"+dccData.get("mobileCityId"));
//		mocitynum++;
//	}
//	Set<String> numMap = new HashSet<>();
//	if (StringUtils.isNotBlank(dccData.get("question1"))) {
//		Matcher matcher = pattern.matcher(dccData.get("question1"));
//		String src = "";
//		while (matcher.find()) {
//			src = matcher.group(1);
//			if (ourMap.containsKey(src)) {
//				numMap.add(ourMap.get(src));
//			}
//		}
//		System.out.print("问题1回答:" + dccData.get("question1"));
//		q1++;
//	}
//	if (StringUtils.isNotBlank(dccData.get("question2"))) {
//		// System.out.print("问题2回答:"+dccData.get("question2"));
//		Matcher matcher = pattern.matcher(dccData.get("question2"));
//		String src = "";
//		while (matcher.find()) {
//			src = matcher.group(1);
//			if (ourMap.containsKey(src)) {
//				numMap.add(ourMap.get(src));
//			}
//		}
//		System.out.print("问题2回答:" + dccData.get("question2"));
//		q2++;
//	}
//	if (StringUtils.isNotBlank(dccData.get("question3"))) {
//		// System.out.print("问题3回答:"+dccData.get("question3"));
//		Matcher matcher = pattern.matcher(dccData.get("question3"));
//		String src = "";
//		while (matcher.find()) {
//			src = matcher.group(1);
//			if (ourMap.containsKey(src)) {
//				numMap.add(ourMap.get(src));
//			}
//		}
//		System.out.print("问题3回答:" + dccData.get("question3"));
//		q3++;
//	}
//	if(!numMap.isEmpty()) {
//		for(String key:numMap) {
//			if(qmap.containsKey(key)) {
//				qmap.put(key, qmap.get(key)+1);
//			}else {
//				qmap.put(key, 1);
//			}
//		}
//	}
//	if (StringUtils.isNotBlank(dccData.get("mobile"))) {
//		Set<String> icnumSet = new HashSet<>();
//		List<Map<String, String>> icInfoList =IcUserDataProcessUtil.getIcDataToOur(dccData.get("mobile"));
//		if(icInfoList!=null&&!icInfoList.isEmpty()) {
//			ictotol ++;
//			for(Map<String, String> icinfo : icInfoList) {
//				for(String key : icinfo.keySet()) {
//					if(IcUserDataProcessUtil.icdatanumMap.containsKey(key)) {
//						icnumSet.add(IcUserDataProcessUtil.icdatanumMap.get(key));
//					}
//				}
//				
//			}
//		}
//		if(!icnumSet.isEmpty()) {
//			for(String key:icnumSet) {
//				if(icmap.containsKey(key)) {
//					icmap.put(key, icmap.get(key)+1);
//				}else {
//					icmap.put(key, 1);
//				}
//			}
//		}
//	}
	//System.out.println("");
//	System.out.println("总数:" + totol);
//	System.out.println("姓名数:" + namenum);
//	System.out.println("生日数:" + bitrdaynum);
//	System.out.println("身份证数:" + idnum);
//	System.out.println("邮箱数:" + emailnum);
//	System.out.println("姓别数:" + sexnum);
//	System.out.println("省份数:" + mopronum);
//	System.out.println("城市数:" + mocitynum);
//	for (String key : qmap.keySet()) {
//		if(key.indexOf(",")!=-1) {
//			String[] s = key.split(",");
//			for(String k:s) {
//				if(qmap.containsKey(k)) {
//					qmap.put(k, qmap.get(k)+qmap.get(key));
//				}else {
//					qmap.put(k, qmap.get(key));
//				}
//			}
//			
//		}
//	}
//	System.out.println("婚姻情况:" + qmap.get("A"));
//	System.out.println("子女情况:" + qmap.get("B"));
//	System.out.println("是否有社保:" + qmap.get("C"));
//	System.out.println("是否有商保:" + qmap.get("G"));
//	System.out.println("意向产品:" + qmap.get("D"));
//	System.out.println("月收入:" + qmap.get("E"));
//	System.out.println("职业类别:" + qmap.get("F"));
//	System.out.println("是否有车:" + qmap.get("H"));
//	System.out.println("是否有房:" + qmap.get("I"));
//	
//	System.out.println("IC数据数量:" + ictotol);
//	System.out.println("姓名:" + icmap.get("A"));
//	System.out.println("身份证数:" + icmap.get("B"));
//	System.out.println("姓别数:" + icmap.get("C"));
//	System.out.println("生日数:" + icmap.get("D"));
//	System.out.println("是否有房:" + icmap.get("E"));
//	System.out.println("是否有车:" + icmap.get("F"));
//	System.out.println("城市数:" + icmap.get("G"));
//	System.out.println("省份数:" + icmap.get("H"));
//	System.out.println("子女情况:" + icmap.get("I"));
//	System.out.println("月收入:" + icmap.get("J"));
//	System.out.println("是否有商保:" + icmap.get("K"));
}
