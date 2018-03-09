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

public class MediaFetchImageUtil {
	public static Map<String, String> headerMap = new LinkedHashMap<>();

	public static boolean isDebug = true;

	public static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";

	public static String muserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1";

	public static List<String> info = new LinkedList<>();
	static {
		headerMap.put("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headerMap.put("Accept-Encoding", "gzip, deflate");
		headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
		headerMap.put("Cache-Control", "no-cache");
		headerMap.put("Connection", "keep-alive");
		headerMap.put("Upgrade-Insecure-Requests", "1");
	}

	public static void fetchImageListHref(String flag, String url) {
		switch (flag) {
		case "a":
			fetchImageListHrefBy4Hu(url);
			break;
		default:

		}
	}

	public static void fetchImageListSrc(String flag, String url) {
		switch (flag) {
		case "a":
			fetchImageListSrcfBy4Hu(url);
			break;
		default:

		}
	}

	public static void fetchImageListHrefBy4Hu(String url) {
		try {
			Document docoumet = null;
			if (isDebug) {
				docoumet = Jsoup.parse(new File("F:\\logs\\4hu_img_list.html"), "utf-8");
			} else {
				docoumet = Jsoup.connect(url).userAgent(userAgent).headers(headerMap).get();
			}
			Elements eles = docoumet.getElementsByTag("li");
			for (Element ele : eles) {
				System.out.println(ele.toString());
				String hrefurl = ele.getElementsByTag("a").attr("href");
				
				String ourid = hrefurl.substring(hrefurl.lastIndexOf("/") + 1, hrefurl.indexOf("."));
				System.out.println(ourid);
				String datestr = ele.getElementsByTag("span").text();
				String title = ele.getElementsByTag("a").text().replaceAll(datestr, "");
				System.out.println("链接：" + hrefurl);
				System.out.println("标题：" + title);
				System.out.println("id：" + ourid);
				System.out.println("时间：" + datestr);
				// System.out.println(JsonUtil.toJson(txtinfo));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void fetchImageListHrefByLifang(String url) {
		try {
			Document docoumet = null;
			docoumet = Jsoup.connect(url).userAgent(muserAgent).headers(headerMap).get();
			Elements eles = docoumet.getElementById("main").getElementsByClass("abc1").first().getElementsByTag("li");
			for (Element ele : eles) {
				System.out.println(ele.toString());
				String hrefurl = ele.getElementsByTag("a").first().attr("href");				
				String ourid = hrefurl.substring(hrefurl.lastIndexOf("/") + 1, hrefurl.indexOf("."));
				String cover = ele.getElementsByTag("img").first().attr("src");
//				String datestr = ele.getElementsByTag("span").text();
				String title = ele.getElementsByTag("a").last().text();
				System.out.println("链接：" + hrefurl);
				System.out.println("标题：" + title);
				System.out.println("封面：" + cover);
				System.out.println("id：" + ourid);
				System.out.println("==============");
				// System.out.println(JsonUtil.toJson(txtinfo));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void fetchRealImageListHrefByLifang(String url) {
		try {
			Document docoumet = null;
			docoumet = Jsoup.connect(url).userAgent(muserAgent).headers(headerMap).get();
			Elements eles = docoumet.getElementById("dedepagetitles").getElementsByTag("option");
			String urlfix = url.substring(0,url.lastIndexOf("/")+1);
			for(Element ele:eles) {
				String hrefurl = ele.attr("value");
				String src = fetchImageSrcfByLifang(urlfix+hrefurl);
				System.out.println(urlfix+hrefurl+":真实图地址："+src);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String fetchImageSrcfByLifang(String url) {
		String src = null;
		try {
			Document docoumet = Jsoup.connect(url).userAgent(muserAgent).headers(headerMap).get();
			
			Element ele = docoumet.getElementById("nr234img");
			Element eles = ele.getElementsByTag("img").first();
			src = eles.attr("src");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return src;
	}
	public static void fetchImageListSrcfBy4Hu(String url) {

		try {
			Document docoumet = null;
			if (isDebug) {
				docoumet = Jsoup.parse(new File("F:\\logs\\4hu_img_info.html"), "gbk");
			} else {
				docoumet = Jsoup.connect(url).userAgent(userAgent).headers(headerMap).get();
			}
			Element ele = docoumet.getElementsByClass("content").first();
			Elements eles = ele.getElementsByTag("img");
			for (Element img : eles) {
				System.out.println(img.attr("src"));
				// System.out.println(JsonUtil.toJson(txtinfo));

			}
			System.out.println(ele.text());
			System.out.println(ele.html());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int fetchImageListPagesizeBy4Hu(String url) {
		int size = 0;
		try {
			Document docoumet = null;
			if (isDebug) {
				docoumet = Jsoup.parse(new File("F:\\logs\\4hu_img_list.html"), "utf-8");
			} else {
				docoumet = Jsoup.connect(url).userAgent(userAgent).headers(headerMap).get();
			}
			Elements eles = docoumet.getElementsByClass("pagination");
			Pattern pattern = Pattern.compile("当前:1/(.*?)页");
			for (Element ele : eles) {
				Matcher matcher = pattern.matcher(ele.text());
				String src = "";
				while (matcher.find()) {
					System.out.println(matcher.group(1));
					src = matcher.group(1);
				}
				size = NumberUtils.toInt(src, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}
	
	public static int fetchImageListPagesizeByLifang(String url) {
		int size = 0;
		try {
			//http://m.lifanacg.com/manhua/list_1_{index}.html
			Document docoumet = Jsoup.connect(url).userAgent(muserAgent).headers(headerMap).get();
			Element ele = docoumet.getElementsByClass("pageinfo").first().getElementsByTag("strong").first();
			size = NumberUtils.toInt(ele.text(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	public static void main(String[] args) {
		fetchRealImageListHrefByLifang("http://m.369107.com/manhua/2018/0129/5152.html");
	}

}
