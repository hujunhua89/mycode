package com.example.modules.custom.util;

import java.io.File;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.base.util.JsonUtil;
import com.example.modules.custom.entity.CustomTxt;

public class TxtHtmlFetchUtil {
	public static Map<String, String> headerMap = new LinkedHashMap<>();
	
	public static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36";
	
	public static List<String> info = new LinkedList<>();
	static {
		headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headerMap.put("Accept-Encoding", "gzip, deflate");
		headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
		headerMap.put("Cache-Control", "max-age=0");
		headerMap.put("Connection", "keep-alive");
		headerMap.put("Upgrade-Insecure-Requests", "1");
	}
	
	
	public static List<CustomTxt> fetchTxtInfo(String flag,String url,String classname){
		List<CustomTxt> txtlist = null;
		switch (flag) {
		case "a":
			txtlist = fetchTxtByAInfo(url);
			break;
		case "b":
			txtlist = fetchTxtByBInfo(url,classname);
			break;
		case "c":
			txtlist = fetchTxtByCInfo(url,classname);
			break;
		default:
			break;
		}
		return txtlist;
	}
	
	public static String fetchTxtContent(String flag,String url) {
		String content = null;
		switch (flag) {
		case "a":
			content = fetchTxtContentByA(url);
			break;
		case "b":
			content = fetchTxtContentByB(url);
			break;
		case "c":
			content = fetchTxtContentByC(url);
			break;
			
		default:
			break;
		}
		return content;
	}
	
	private static List<CustomTxt> fetchTxtByAInfo(String url) {
		List<CustomTxt> txtlist= new LinkedList<>();
		CustomTxt txtinfo = null;
		try {
			Document docoumet = Jsoup.connect(url)
					.userAgent(userAgent)
					.headers(headerMap)	
					.get();
			//Document docoumet = Jsoup.parse(new File("F:\\logs\\tieba.html"), "utf-8");
			Elements eles = docoumet.getElementsByTag("li");
			for (Element ele : eles) {
				txtinfo = new CustomTxt();
				String hrefurl = ele.getElementsByTag("a").attr("href");
				String[] titles = ele.getElementsByTag("a").text().split("作者");
				String title = titles[0].substring(titles[0].indexOf("【"), titles[0].length());
				String className = title.substring(title.indexOf("【")+1,title.indexOf("】"));
				String auther = "";
				if(titles.length>1) {
					auther = titles[1];
				}
				String ourid =hrefurl.substring(hrefurl.lastIndexOf("/")+1, hrefurl.indexOf("."));
				System.out.println(ourid);
				String datestr = ele.getElementsByTag("span").text();
				txtinfo.setAuthorName(auther);
				txtinfo.setTitle(title);
				txtinfo.setCreateTime(new Date());
				txtinfo.setTxtUrl(hrefurl);
				txtinfo.setRuleFlag("a");
				txtinfo.setRemark(ourid);
				txtinfo.setTxtClass(className);
				txtinfo.setDateStr(datestr);
				txtlist.add(txtinfo);
			}		
		} catch (Exception e) {
			//e.printStackTrace();
			info.add(url+""+e.getMessage());
		}
		return txtlist;
	}
	
	private static String fetchTxtContentByA(String url) {
		String txtinfo = null;
		try {
			Document docoumet = Jsoup.connect(url)
					.userAgent(userAgent)
					.headers(headerMap)	
					.get();
			//Document docoumet = Jsoup.parse(new File("F:\\logs\\tieba.html"), "utf-8");
			Elements eles = docoumet.getElementsByClass("content");
			for(Element ele : eles) {
				txtinfo = ele.getElementsByTag("font").html();
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			info.add(url+""+e.getMessage());
		}
		return txtinfo;
	}
	
	public static int fetchTxtByBpageNum(String url) {
		int num = 0 ;
		try {
			Document docoumet = Jsoup.connect(url)
					.userAgent(userAgent)
					.headers(headerMap)	
					.get();
			//Document docoumet = Jsoup.parse(new File("F:\\logs\\baidu.html"), "utf-8");
			Elements eles = docoumet.getElementsByClass("page");
			Pattern pattern = Pattern.compile("当前:1/(.*?)页");
			for (Element ele : eles) {
				Matcher matcher = pattern.matcher(ele.text());
				String src = "";
				while (matcher.find()) {
					System.out.println(matcher.group(1));
					src = matcher.group(1);
				}
				num = NumberUtils.toInt(src,0);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			info.add(url+""+e.getMessage());
		}
		return num;
	}
	private static List<CustomTxt> fetchTxtByBInfo(String url,String className) {
		List<CustomTxt> txtlist= new LinkedList<>();
		CustomTxt txtinfo = null;
		try {
			Document docoumet = Jsoup.connect(url)
					.userAgent(userAgent)
					.headers(headerMap)	
					.get();
			//Document docoumet = Jsoup.parse(new File("F:\\logs\\baidu.html"), "utf-8");
			Elements eles = docoumet.getElementsByTag("li");
			for (Element ele : eles) {
				//System.out.println(ele.toString());
				txtinfo = new CustomTxt();
				String hrefurl = ele.getElementsByTag("a").attr("href");
				String[] titles = StringEscapeUtils.unescapeHtml(ele.getElementsByTag("a").text().
						replaceAll(" ", "").replaceAll("【", "--").replaceAll("】", "")).split("--");
				String auther = "";
				String title = "";
				String flag = "";
				if(titles.length>0) {
					if(StringUtils.isBlank(titles[0])) {
						title=titles[1];
					}else {
						title = titles[0];
					}
					for(String str :titles) {
						if(str.indexOf("作者")!=-1) {
							auther = str.split("：").length>1?str.split("：")[1]:"";
						}else if(!str.equals(title)&&StringUtils.isNotBlank(str)&&!StringUtils.endsWith(str, "转帖")) {
							flag +="【"+str+"】";
						}
					}
				}else {
					title = titles[0];
				}
				String ourid =hrefurl.split("/")[2];
				String datestr = ele.getElementsByTag("span").text();
				txtinfo.setAuthorName(auther);
				txtinfo.setTitle(title+flag);
				txtinfo.setCreateTime(new Date());
				txtinfo.setTxtUrl(hrefurl);
				txtinfo.setRuleFlag("b");
				txtinfo.setRemark(ourid);
				txtinfo.setTxtClass(className);
				txtinfo.setDateStr(datestr);
				txtlist.add(txtinfo);
			}		
		} catch (Exception e) {
			e.printStackTrace();
			info.add(url+""+e.getMessage());
		}
		return txtlist;
	}
	
	private static List<CustomTxt> fetchTxtByCInfo(String url,String className) {
		List<CustomTxt> txtlist= new LinkedList<>();
		CustomTxt txtinfo = null;
		try {
			Document docoumet = Jsoup.connect(url)
					.userAgent(userAgent)
					.headers(headerMap)	
					.get();
			//Document docoumet = Jsoup.parse(new File("F:\\logs\\baidu.html"), "utf-8");
			Elements eles = docoumet.getElementsByClass("typelist").get(0).getElementsByTag("ul");
			boolean res = false;
			for (Element ele : eles) {
				//System.out.println(ele.toString());
				txtinfo = new CustomTxt();
				String hrefurl = ele.getElementsByTag("a").attr("href");
				String[] titles = StringEscapeUtils.unescapeHtml(ele.getElementsByTag("a").text().
						replaceAll(" ", "")).split("作者");
				String auther = "";
				String title = "";
				if(titles.length>1) {
					title= titles[0];
					auther = titles[1];
				}else {
					title = titles[0];
				}
				if(hrefurl.indexOf(".html")==-1) {
					res=true;
					continue;
				}
				String ourid =hrefurl.substring(hrefurl.lastIndexOf("/")+1, hrefurl.indexOf("."));
				String datestr = ele.getElementsByTag("font").text();
				txtinfo.setAuthorName(auther.replaceAll("【|】", ""));
				txtinfo.setTitle(title);
				txtinfo.setCreateTime(new Date());
				txtinfo.setTxtUrl(hrefurl);
				txtinfo.setRuleFlag("c");
				txtinfo.setRemark(ourid);
				txtinfo.setTxtClass(className);
				txtinfo.setDateStr(datestr);
				if(res) {
					txtlist.add(txtinfo);
				}
				//System.out.println(JsonUtil.toJson(txtinfo));
				
			}		
		} catch (Exception e) {
			e.printStackTrace();
			info.add(url+""+e.getMessage());
		}
		return txtlist;
	}
	
	public static int fetchTxtByCpageNum(String url) {
		int num = 0 ;
		try {
			Document docoumet = Jsoup.connect(url)
					.userAgent(userAgent)
					.headers(headerMap)	
					.get();
			//Document docoumet = Jsoup.parse(new File("F:\\logs\\baidu.html"), "utf-8");
			Elements eles = docoumet.getElementById("page").getElementsByTag("font");
			for (Element ele : eles) {
				num = NumberUtils.toInt(ele.text(),0);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			info.add(url+""+e.getMessage());
		}
		return num;
	}
	
	private static String fetchTxtContentByB(String url) {
		String txtinfo = null;
		try {
			Document docoumet = Jsoup.connect(url)
					.userAgent(userAgent)
					.headers(headerMap)	
					.get();
			//Document docoumet = Jsoup.parse(new File("F:\\logs\\tieba.html"), "utf-8");
			Elements eles = docoumet.getElementsByClass("content");
			for(Element ele : eles) {
				txtinfo = ele.html();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			info.add(url+""+e.getMessage());
		}
		return txtinfo;
	}
	
	private static String fetchTxtContentByC(String url) {
		String txtinfo = null;
		try {
			Document docoumet = Jsoup.connect(url)
					.userAgent(userAgent)
					.headers(headerMap)	
					.get();
			//Document docoumet = Jsoup.parse(new File("F:\\logs\\tieba.html"), "gbk");
			Element ele = docoumet.getElementById("view2");
			txtinfo = ele.html();			
		} catch (Exception e) {
			e.printStackTrace();
			info.add(url+""+e.getMessage());
		}
		return txtinfo;
	}
	
	public static String fetchTxtContentByLocal() {
		String txtinfo = null;
		try {
			Document docoumet = Jsoup.parse(new File("I:\\备份\\电影\\另外\\小说\\34本萝莉H小说\\34本萝莉H小说\\征服.html"), "gbk");
			Element ele = docoumet.getElementById("content");
			txtinfo = ele.html().replaceAll("<br>", "\r").replaceAll("&nbsp;", " ");			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return txtinfo;
	}
	public static void main(String[] args) {

		
		System.out.println(fetchTxtContentByLocal());
	}
}
