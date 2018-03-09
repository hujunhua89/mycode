package com.example.modules.tieba.util;

import java.net.URL;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.base.util.JsonUtil;
import com.example.modules.tieba.entity.Tieba;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.gson.reflect.TypeToken;

public class TiebaInfoUtil {
	

	public static Tieba fetchTiebaInfo(String tiebaName) {
		Tieba tieba = new Tieba();
		try {
			String url = "http://tieba.baidu.com/f?kw=" + TiebaUtil.parseStr(tiebaName, "UTF-8");
			tieba.setTiebaUrl(url);
			Document doc = Jsoup.connect(url)
					.userAgent(TiebaUtil.userAgent)
					.headers(TiebaUtil.headerMap)
					.cookies(TiebaUtil.cookieMap)
					.get();
			Document docoumet = Jsoup
					.parse(doc.toString().replaceAll("<!--", "<junhua>").replaceAll("-->", "</junhua>"));
			Elements eles = docoumet.getElementsByTag("script"); // getElementsByTag("li");
			for (Element ele : eles) {
				if(ele.toString().indexOf("PageData.forum")!=-1) {
					String st = ele.html().replaceAll("\n", "").replaceAll("\r", "").replaceAll("\b", "");
					int s = st.indexOf("PageData.forum");
					String str = st.substring(s).replace("'", "\"").replaceAll(" ", "");
					int i= str.indexOf("}");
					String str1 = str.substring(15,i+1);
					Map<String, String> author = JsonUtil.get().fromJson(str1, new TypeToken<Map<String, String>>() {
					}.getType());
					tieba.setTiebaId(author.get("id"));
					tieba.setTiebaName(author.get("name"));
					tieba.setFirstClass(author.get("first_class"));
					tieba.setSecondClass(author.get("second_class"));
					break;
				}
			}
			tieba.setTiebaInfo(docoumet.getElementsByTag("title").text().replaceAll("\n", "").replaceAll("\r", ""));
			Elements numinfo = docoumet.getElementsByClass("th_footer_l");
			for(Element ele: numinfo) {
				Elements nums = ele.getElementsByTag("span");
				tieba.setTiebaZtNum(nums.get(0).text());
				tieba.setTiebaTzNum(nums.get(1).text());
				tieba.setTiebaPeopleNum(nums.get(2).text());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tieba;
	}

	public static void main(String[] args) throws Exception {
		//System.out.println(TiebaiUtil.cookieMap);

		Tieba tb = fetchTiebaInfo("手机");
		System.out.println(JsonUtil.toJson(tb));
		
	}

	

	public static void test(String tiebaName) {
		try {
			String url = "http://tieba.baidu.com/f?kw=" + TiebaUtil.parseStr(tiebaName, "UTF-8");
			WebClient webClient = new WebClient();
			// 2 启动JS
			webClient.getOptions().setJavaScriptEnabled(true);
			// 3 禁用Css，可避免自动二次請求CSS进行渲染
			webClient.getOptions().setCssEnabled(false);
			// 4 启动客戶端重定向
			webClient.getOptions().setRedirectEnabled(true);
			// 5 js运行错誤時，是否拋出异常
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			// 6 设置超时
			webClient.getOptions().setTimeout(50000);

			webClient.getCookieManager().addCookie(new Cookie("", "BAIDUID", "A26D41955B82C07DB3FF47FFF8D5A95D:FG"));
			URL link = new URL(url);
			WebRequest request = new WebRequest(link);

			//// 设置请求报文头里的User-Agent字段
			request.setAdditionalHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");

			HtmlPage htmlPage = webClient.getPage(request);
			// 等待JS驱动dom完成获得还原后的网页
			webClient.waitForBackgroundJavaScript(10000);
			// 获取网页
			// Document docoumet = Jsoup
			// .parse(htmlPage.asXml());
			System.out.println(htmlPage.asXml());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
