package com.example.modules.tieba.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.base.util.JsonUtil;
import com.example.modules.tieba.entity.Tieba;
import com.example.modules.tieba.entity.TiebaTiezi;
import com.google.gson.reflect.TypeToken;

public class TiebaTieziUtil {
	public static List<TiebaTiezi> fetchTieziList(String url) {
		Tieba tieba = new Tieba();
		List<TiebaTiezi> tieziList = new LinkedList<>();
		try {
			tieba.setTiebaUrl(url);
			Document doc = Jsoup.connect(url)
					.userAgent(TiebaUtil.userAgent)
					.headers(TiebaUtil.headerMap)
					.cookies(TiebaUtil.cookieMap)
					.get();
			Document docoumet = Jsoup
					.parse(doc.toString().replaceAll("<!--", "<junhua>").replaceAll("-->", "</junhua>"));
			Element eletol = docoumet.getElementById("thread_list");
			// System.out.println(eletol);
			Elements eles = eletol.getElementsByClass("j_thread_list"); // getElementsByTag("li");
			
			TiebaTiezi tiezi = null;
			for (Element ele : eles) {
				tiezi = new TiebaTiezi();
				String backnum = ele.getElementsByClass("threadlist_rep_num").text();// 回复数
				String title = ele.getElementsByClass("j_th_tit").text();// 标题
				//String aut = ele.getElementsByClass("frs-author-name").html();// 作者
				String autall = ele.getElementsByClass("tb_icon_author").attr("title");// 作者
				String autid = ele.getElementsByClass("tb_icon_author").attr("data-field");// 作者id
				String contenthref = ele.getElementsByClass("j_th_tit").attr("href");// 内容链接
				String content = ele.getElementsByClass("threadlist_abs").html();// 内容链接
				String date = ele.getElementsByClass("is_show_create_time").html();// 内容链接
				String lastdate = ele.getElementsByClass("j_reply_data").html();// 内容链接
				String lastauto = ele.getElementsByClass("tb_icon_author_rely").attr("title");// 最后回复人
				String re = ele.getElementsByClass("frs-author-name").attr("data-field");// 最后回复人
				tiezi.setAuthorName(autall.replace("主题作者: ", ""));
				Map<String, String> authorId = JsonUtil.get().fromJson(autid, new TypeToken<Map<String, String>>() {
				}.getType());
				tiezi.setTzId(contenthref.replaceAll("/p/", ""));
				tiezi.setAuthorId(authorId.get("user_id"));
				tiezi.setTitle(title);
				tiezi.setContent(content);
				tiezi.setPostSDate(date);
				tiezi.setPostDate(TiebaUtil.parseToDate(date));
				tiezi.setReplySDate(lastdate);
				tiezi.setReplyDate(TiebaUtil.parseToDate(lastdate));
				tiezi.setReplyNum(backnum);
				tiezi.setReplyerName(lastauto);
//				System.out.println("(" + autall + ")作者:" + aut + "---id:" + autid);
//				System.out.println("链接:" + "http://tieba.baidu.com" + contenthref);
//				System.out.println("标题:" + title);
//				System.out.println("内容:" + content);
//				System.out.println("发贴时间:" + TiebaUtil.parseDate(date));
//				System.out.println(lastauto+" 最后回复时间:" + TiebaUtil.parseDate(lastdate));
//				System.out.println(re+"回复数:" + backnum);
				String info = ele.getElementsByClass("j_thread_list").attr("data-field");// 回复数
				Map<String, String> author = JsonUtil.get().fromJson(info, new TypeToken<Map<String, String>>() {
				}.getType());
				tiezi.setAuditerId(author.get("id"));
				tiezi.setAuditerName(author.get("author_name"));
				tiezi.setIsTop("true".equals(author.get("is_top"))?"Y":"N");
				if("Y".equals(tiezi.getIsTop())) {
					tiezi.setIsFetch("N");
				}else {
					tiezi.setIsFetch("Y");
				}
				tieziList.add(tiezi);
				//System.out.println("审核人:" + JsonUtil.get().toJson(author));
				//System.out.println("-------");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tieziList;
	}
	public static void main(String[] args) {
		List<TiebaTiezi> tzList = fetchTieziList("http://tieba.baidu.com/f?kw=%E6%89%8B%E6%9C%BA");
		for(TiebaTiezi tz :tzList) {
			System.out.println(JsonUtil.toJson(tz));
		}
		
	}
}
