package com.example.demo;

import java.io.File;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TestJsoup {

	public static void main(String[] args) {
		try {
			Gson gson = new Gson();
			Document doc = Jsoup.connect("http://tieba.baidu.com/f?kw=%CA%D6%BB%FA&fr=ala0&tpl=5").userAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
					.header("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
					.header("Accept-Encoding", "gzip, deflate").header("Accept-Language", "zh-CN,zh;q=0.9")
					.header("Connection", "keep-alive").header("Host", "tieba.baidu.com")
					.header("Referer",
							"https://www.baidu.com/link?url=h_MeF1VcofqXJehmCsp25sQ2x9NqdDWezZuRw_1X-29wXefaq2xVmH93jXxh4kdxeAHOJSeWryNEyxUiurSCRq&wd=&eqid=b5229f480000c0e2000000065a4df7dc")
					.header("Upgrade-Insecure-Requests", "1").cookie("BAIDUID", "A26D41955B82C07DB3FF47FFF8D5A95D:FG")
					.cookie("PSTM", "1514961127").cookie("BIDUPSID", "EE4C9C65B2B3E02D4EC5EF845F44288A")
					.cookie("td_cookie", "602494953").cookie("TIEBA_USERTYPE", "2da17e7c6b99fa7da68cfcf6")
					.cookie("TIEBAUID", "18658700510200dc2ed816bc").cookie("H_PS_PSSID", "1462_21084")
					.cookie("BDORZ", "B490B5EBF6F3CD402E515D22BCDA1598").cookie("wise_device", "0")
					.cookie("bottleBubble", "1").cookie("111297565_FRSVideoUploadTip", "1")
					.cookie("Hm_lvt_98b9d8c2fd6608d564bf2ac2ae642948", "1515051570,1515059171,1515059189,1515059753")
					.cookie("Hm_lpvt_98b9d8c2fd6608d564bf2ac2ae642948", "1515059753")
					.cookie("BDRCVFR[feWj1Vr5u3D]", "I67x6TjHwwYf0").cookie("PSINO", "7").get();

			Document docoumet = Jsoup
					.parse(doc.toString().replaceAll("<!--", "<junhua>").replaceAll("-->", "</junhua>"));
			Element eletol = docoumet.getElementById("thread_list");
			// System.out.println(eletol);
			Elements eles = eletol.getElementsByClass("j_thread_list"); // getElementsByTag("li");
			for (Element ele : eles) {
				String backnum = ele.getElementsByClass("threadlist_rep_num").text();// 回复数
				String title = ele.getElementsByClass("j_th_tit").text();// 标题
				String aut = ele.getElementsByClass("frs-author-name").html();// 作者
				String autall = ele.getElementsByClass("tb_icon_author").attr("title");// 作者
				String autid = ele.getElementsByClass("tb_icon_author").attr("data-field");// 作者id
				String contenthref = ele.getElementsByClass("j_th_tit").attr("href");// 内容链接
				String content = ele.getElementsByClass("threadlist_abs").html();// 内容链接
				String date = ele.getElementsByClass("is_show_create_time").html();// 内容链接
				String lastdate = ele.getElementsByClass("j_reply_data").html();// 内容链接
				String lastauto = ele.getElementsByClass("tb_icon_author_rely").attr("title");// 最后回复人
				System.out.println("(" + autall + ")作者:" + aut + "---id:" + autid);
				System.out.println("链接:" + "http://tieba.baidu.com" + contenthref);
				System.out.println("标题:" + title);
				System.out.println("内容:" + content);
				System.out.println("发贴时间:" + parseDate(date));
				System.out.println(lastauto+" 最后回复时间:" + parseDate(lastdate));
				System.out.println("回复数:" + backnum);
				String info = ele.getElementsByClass("j_thread_list").attr("data-field");// 回复数
				Map<String, String> author = gson.fromJson(info, new TypeToken<Map<String, String>>() {
				}.getType());
				System.out.println("审核人:" + gson.toJson(author));
				System.out.println("-------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String parseDate(String date) {
		StringBuffer redate = new StringBuffer();
		if(StringUtils.isNotBlank(date)) {
			if(date.indexOf("-")!=-1) {//不是当天的
				String[] de = date.split("-");
				if(de[0].length()>2) {//年
					redate.append(de[0]+"年");
					redate.append(de[1]+"月");
				}else {
					redate.append("2017年");
					redate.append(de[0]+"月");
					redate.append(de[1]+"日");
				}
			}else if(date.indexOf(":")!=-1) {
				Calendar ce = Calendar.getInstance();
				redate.append(ce.get(Calendar.YEAR)+"年");
				redate.append((ce.get(Calendar.MONTH)+1)+"月");
				redate.append(ce.get(Calendar.DAY_OF_MONTH)+"日 ");
				String[] de = date.split(":");
				redate.append(de[0]+"时");
				redate.append(de[1]+"分");
			}
		}
		return redate.toString();
	}

	 public static String unicode2String(String unicode){  
	        if(StringUtils.isBlank(unicode))return null;
	        StringBuilder sb = new StringBuilder();  
	        int i = -1;  
	        int pos = 0;  
	        while((i=unicode.indexOf("\\u", pos)) != -1){  
	            sb.append(unicode.substring(pos, i));  
	            if(i+5 < unicode.length()){  
	                pos = i+6;  
	                sb.append((char)Integer.parseInt(unicode.substring(i+2, i+6), 16));  
	            }  
	        }  
	          
	        return sb.toString();  
	    }  
	
	@Test
	public void test() {
		File file = new File("I:\\备份\\电影\\另外\\动漫\\魔界天使ジブリール[全系列]\\(18禁アニメ) (無修正) 魔界天使ジブリール2 Vol.3 決戦！ジブリールVSジブリール(DVD 640x480 WMV9)(CRC AA89).zip");
		
		if (file.exists()) {
			System.out.println(file.getPath());
			file.delete();
		}
	}

}
