package com.example.modules.media.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MediaFetchVideoUtil {
	public static Map<String, String> headerMap = new LinkedHashMap<>();

	public static boolean isDebug = true;

	public static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";

	public static List<String> info = new LinkedList<>();
	static {
		headerMap.put("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,Video/webp,Video/apng,*/*;q=0.8");
		headerMap.put("Accept-Encoding", "gzip, deflate");
		headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
		headerMap.put("Cache-Control", "max-age=0");
		headerMap.put("Connection", "keep-alive");
		headerMap.put("Upgrade-Insecure-Requests", "1");
	}

	public static void fetchVideoListHref(String flag, String url) {
		switch (flag) {
		case "a":
			fetchVideoListHrefBy4Hu(url);
			break;
		default:

		}
	}

	public static void fetchVideoHref(String flag, String url) {
		switch (flag) {
		case "a":
			fetchVideoHrefBy4Hu(url);
			break;
		default:

		}
	}

	public static void fetchVideoListHrefBy4Hu(String url) {
		try {
			Document docoumet = null;
			if (isDebug) {
				docoumet = Jsoup.parse(new File("F:\\logs\\4hu_video_list.html"), "utf-8");
			} else {
				docoumet = Jsoup.connect(url).userAgent(userAgent).headers(headerMap).get();
			}
			Elements eles = docoumet.getElementsByTag("li");
			for (Element ele : eles) {
				//System.out.println(ele.toString());
				String hrefurl = ele.getElementsByTag("a").attr("href");	
				String ourid = hrefurl.substring(hrefurl.lastIndexOf("/") + 1, hrefurl.indexOf("."));
				Element dates = ele.getElementsByClass("movie_date").first();
				String datestr = dates.text();
				String title = ele.getElementsByTag("a").text().replaceAll(datestr, "").trim();
				String surface = ele.getElementsByTag("img").attr("src");
				System.out.println("封面：" + surface);
				System.out.println("链接：" + hrefurl);
				System.out.println("标题：" + title);
				System.out.println("id：" + ourid);
				System.out.println("时间：" + datestr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fetchVideoHrefBy4Hu(String url) {

		try {
			Document docoumet = null;
			if (isDebug) {
				docoumet = Jsoup.parse(new File("F:\\logs\\4hu_video_info.html"), "gbk");
			} else {
				docoumet = Jsoup.connect(url).userAgent(userAgent).headers(headerMap).get();
			}
			Element ele = docoumet.getElementsByClass("downurl").first().getElementsByTag("a").first();
			System.out.println(ele.text());
			System.out.println(ele.attr("href"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int fetchVideoListPagesizeBy4Hu(String url) {
		int size = 0;
		try {
			Document docoumet = null;
			if (isDebug) {
				docoumet = Jsoup.parse(new File("F:\\logs\\4hu_video_list.html"), "utf-8");
			} else {
				docoumet = Jsoup.connect(url).userAgent(userAgent).headers(headerMap).get();
			}
			Element ele = docoumet.getElementsByClass("pagination").first();
			Pattern pattern = Pattern.compile("部 1/(.*?)首页");
			Matcher matcher = pattern.matcher(ele.text());
			String src = "";
			while (matcher.find()) {
				System.out.println(matcher.group(1));
				src = matcher.group(1);
			}
			size = NumberUtils.toInt(src, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public static void main(String[] args) {
		fetchVideoHrefBy4Hu("");
	}

}
