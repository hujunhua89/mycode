package com.example.modules.tieba.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class TiebaUtil {
	
	private static String cooikeStr = "BAIDUID=A26D41955B82C07DB3FF47FFF8D5A95D:FG=1; PSTM=1514961127; BIDUPSID=EE4C9C65B2B3E02D4EC5EF845F44288A; BDUSS=W5CSlo1VW8zNm95d091d2NkdFFBfjZGNGlUVU1ZMGlMQXpOT0wwc1JORDVNblJhQVFBQUFBJCQAAAAAAAAAAAEAAAAdRKIGaHVhMDgwNzE0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPmlTFr5pUxaR; TIEBA_USERTYPE=2da17e7c6b99fa7da68cfcf6; STOKEN=070ecfbafb0fbdf7b933c830fc088411005756f94f7a8928d985e1c024a52218; TIEBAUID=18658700510200dc2ed816bc; bdshare_firstime=1515113270337; td_cookie=1628118910; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=1462_21084; wise_device=0; H_WISE_SIDS=110317_104494_121790_102433_121435_120195_121140_118891_118875_118838_118824_118798_107313_121254_121533_121924_121214_121988_117334_121862_117428_121667_120590_121560_121422_120943_121041_121363_120483_119929_120851_120036_116408_110085; PSINO=7; fixed_bar=1; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; bottleBubble=1; 111297565_FRSVideoUploadTip=1; Hm_lvt_98b9d8c2fd6608d564bf2ac2ae642948=1516697585,1516697632,1516698976,1516755420; Hm_lpvt_98b9d8c2fd6608d564bf2ac2ae642948=1516758608";
	
	public static Map<String, String> cookieMap = new LinkedHashMap<>();
	
	public static Map<String, String> headerMap = new LinkedHashMap<>();
	
	public static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";
	
	static {
		String[] st = cooikeStr.split(";");
		for (String s : st) {
			String[] info = s.trim().split("=");
			if (info.length == 3) {
				cookieMap.put(info[0], info[1] + "=" + info[2]);
			} else {
				cookieMap.put(info[0], info[1]);
			}
		}
		headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headerMap.put("Accept-Encoding", "gzip, deflate");
		headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
		headerMap.put("Cache-Control", "max-age=0");
		headerMap.put("Connection", "keep-alive");
		headerMap.put("Host", "tieba.baidu.com");
		headerMap.put("Upgrade-Insecure-Requests", "1");
	}
	

	public static String parseStr(String source, String charset) throws UnsupportedEncodingException {
		char[] ch = source.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				sb.append(URLEncoder.encode(c + "", charset));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * * 判断字符是否是中文
	 * 
	 * @param c
	 * @return boolean
	 * @author Junhua Hu
	 * @date 2017-7-24
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	public static String parseDate(String date) {
		StringBuffer redate = new StringBuffer();
		if (StringUtils.isNotBlank(date)) {
			if (date.indexOf("-") != -1) {// 不是当天的
				String[] de = date.split("-");
				if (de[0].length() > 2) {// 年
					redate.append(de[0] + "年");
					redate.append(de[1] + "月");
				} else {
					redate.append("2017年");
					redate.append(de[0] + "月");
					redate.append(de[1] + "日");
				}
			} else if (date.indexOf(":") != -1) {
				Calendar ce = Calendar.getInstance();
				redate.append(ce.get(Calendar.YEAR) + "年");
				redate.append((ce.get(Calendar.MONTH) + 1) + "月");
				redate.append(ce.get(Calendar.DAY_OF_MONTH) + "日 ");
				String[] de = date.split(":");
				redate.append(de[0] + "时");
				redate.append(de[1] + "分");
			}
		}
		return redate.toString();
	}
	
	public static Date parseToDate(String date) {
		Calendar cal = Calendar.getInstance();
		if (StringUtils.isNotBlank(date)) {
			if (date.indexOf("-") != -1) {// 不是当天的
				String[] de = date.split("-");
				if (de[0].length() > 2) {// 年
					cal.set(Calendar.YEAR, NumberUtils.toInt(de[0]));
					cal.set(Calendar.MONTH, NumberUtils.toInt(de[1])-1);
				} else {
					cal.set(Calendar.MONTH, NumberUtils.toInt(de[0])-1);
					cal.set(Calendar.DAY_OF_MONTH, NumberUtils.toInt(de[1]));
				}
			} else if (date.indexOf(":") != -1) {
				String[] de = date.split(":");
				cal.set(Calendar.HOUR_OF_DAY, NumberUtils.toInt(de[0]));
				cal.set(Calendar.MINUTE, NumberUtils.toInt(de[1]));
			}
		}
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(DateFormatUtils.format(parseToDate("1989-12"), "yyyy-MM-dd HH:mm:ss"));
	}

	public static String unicode2String(String unicode) {
		if (StringUtils.isBlank(unicode))
			return null;
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;
		while ((i = unicode.indexOf("\\u", pos)) != -1) {
			sb.append(unicode.substring(pos, i));
			if (i + 5 < unicode.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}	
}
