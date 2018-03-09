package com.example.modules.tieba.util;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.base.util.HttpSSLUtil;
import com.example.base.util.JsonUtil;
import com.example.modules.tieba.entity.Tieba;
import com.example.modules.tieba.entity.TiebaTiezi;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class TiebaTieziContentUtil {

	public static void fetchTiebaInfo(String url) {
		try {
			Document doc = Jsoup.connect(url)
					.userAgent(TiebaUtil.userAgent)
					.headers(TiebaUtil.headerMap)
					.cookies(TiebaUtil.cookieMap)
					.get();
			Document docoumet = Jsoup
					.parse(doc.toString().replaceAll("<!--", "<junhua>").replaceAll("-->", "</junhua>"));
			//System.out.println(docoumet);
			Elements eles = docoumet.getElementsByClass("l_post"); // getElementsByTag("li");
			for (Element ele : eles) {
				//System.out.println(ele);
//				String backnum = ele.getElementsByClass("threadlist_rep_num").text();// 回复数
//				String title = ele.getElementsByClass("j_th_tit").text();// 标题
//				//String aut = ele.getElementsByClass("frs-author-name").html();// 作者
//				String autall = ele.getElementsByClass("tb_icon_author").attr("title");// 作者
//				String autid = ele.getElementsByClass("j_icon_slot").attr("data-field");// 作者id
//				String contenthref = ele.getElementsByClass("j_th_tit").attr("href");// 内容链接
				
//				String date = ele.getElementsByClass("is_show_create_time").html();// 内容链接
//				String lastdate = ele.getElementsByClass("j_reply_data").html();// 内容链接
//				String lastauto = ele.getElementsByClass("tb_icon_author_rely").attr("title");// 最后回复人
//				String re = ele.getElementsByClass("frs-author-name").attr("data-field");// 最后回复人
//				
//				Map<String, String> authorId = JsonUtil.get().fromJson(autid, new TypeToken<Map<String, Object>>() {
//				}.getType());
//				System.out.println(authorId);
//				System.out.println("链接:" + "http://tieba.baidu.com" + contenthref);
//				System.out.println("标题:" + title);
				
//				System.out.println("发贴时间:" + TiebaUtil.parseDate(date));
//				System.out.println(lastauto+" 最后回复时间:" + TiebaUtil.parseDate(lastdate));
//				System.out.println(re+"回复数:" + backnum);
				String info = ele.getElementsByClass("l_post_bright").attr("data-field");// 回复数
				if(StringUtils.isBlank(info)) {
					continue;
				}
				String contentHtml = ele.getElementsByClass("d_post_content").html();// 内容链接
				System.out.println("内容:" + contentHtml);
				System.out.println(info);
				JsonObject obj =JsonUtil.get().fromJson(info, JsonObject.class);		
				JsonObject author = obj.getAsJsonObject("author");
				JsonObject content = obj.getAsJsonObject("content");
//				Map<String, Object> author = JsonUtil.get().fromJson(info, new TypeToken<Map<String, Object>>() {
//				}.getType());
				System.out.println(JsonUtil.toJson(author));
				System.out.println(JsonUtil.toJson(content));
				System.out.println("------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		//System.out.println(TiebaiUtil.cookieMap);
		//""
		//https://tieba.baidu.com/p/totalComment?tid=5340963279&fid=52
		fetchTiebaInfo("http://tieba.baidu.com/p/5340963279");
		
		//String res = HttpSSLUtil.get("https://tieba.baidu.com/p/totalComment?tid=5340963279&fid=52", null, TiebaUtil.cookieMap);
		//System.out.println(res);
		
		
	}

	

	public static void test(String url) {
		try {
			WebClient webClient = new WebClient();
			// 2 启动JS
			webClient.getOptions().setJavaScriptEnabled(true);
			// 3 禁用Css，可避免自动二次請求CSS进行渲染
			//webClient.getOptions().setCssEnabled(false);
			// 4 启动客戶端重定向
			webClient.getOptions().setRedirectEnabled(true);
			// 5 js运行错誤時，是否拋出异常
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			// 6 设置超时
			webClient.getOptions().setTimeout(50000);
			
			for(String key:TiebaUtil.cookieMap.keySet()) {
				webClient.getCookieManager().addCookie(new Cookie("", key, TiebaUtil.cookieMap.get(key)));
			}
			
			URL link = new URL(url);
			WebRequest request = new WebRequest(link);
			//// 设置请求报文头里的User-Agent字段
			request.setAdditionalHeader("User-Agent",
					TiebaUtil.userAgent);
//			for(String key:TiebaUtil.headerMap.keySet()) {
//				request.setAdditionalHeader(key,
//						TiebaUtil.headerMap.get(key));
//			}
			HtmlPage htmlPage = webClient.getPage(request);
			// 等待JS驱动dom完成获得还原后的网页
			webClient.waitForBackgroundJavaScript(8000); 
			// 获取网页
			// Document docoumet = Jsoup
			// .parse(htmlPage.asXml());
			System.out.println(htmlPage.asXml());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
