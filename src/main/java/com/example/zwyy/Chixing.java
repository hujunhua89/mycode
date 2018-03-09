package com.example.zwyy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;

public class Chixing {
	private static String orgnpath = "F:\\文档\\4.6.1等22个文件";
	private static String outpath = "F:\\文档\\test";
	private static int i = 0;
	private static Map<String, List<String>> classMap = new LinkedHashMap<String, List<String>>();
	private static Map<String, List<String>> mgMap = new LinkedHashMap<String, List<String>>();
	private static List<String> noclassMap = new ArrayList<>();
	static {
		mgMap.put("后台", new ArrayList<>());
		mgMap.put("管理端", new ArrayList<>());
		classMap.put("C端", new ArrayList<>());
		classMap.put("分享监控", new ArrayList<>());
		classMap.put("行为监控", new ArrayList<>());
		classMap.put("首页", new ArrayList<>());
		classMap.put("投票", new ArrayList<>());
		classMap.put("文章", new ArrayList<>());
		classMap.put("朋友圈", new ArrayList<>());
		classMap.put("新手指南", new ArrayList<>());
		classMap.put("营销指引", new ArrayList<>());
		classMap.put("名片", new ArrayList<>());
		classMap.put("活动", new ArrayList<>());
		classMap.put("微信", new ArrayList<>());
		classMap.put("登录", new ArrayList<>());
		classMap.put("学堂", new ArrayList<>());
		classMap.put("计划书", new ArrayList<>());
		classMap.put("客户", new ArrayList<>());		
		classMap.put("价值用户", new ArrayList<>());
		classMap.put("公司", new ArrayList<>());
		classMap.put("短信模版", new ArrayList<>());
		classMap.put("轮播广告", new ArrayList<>());
		classMap.put("联系我们", new ArrayList<>());
		classMap.put("功能介绍", new ArrayList<>());
		classMap.put("意见反馈", new ArrayList<>());
		classMap.put("专题", new ArrayList<>());
		classMap.put("本周生日", new ArrayList<>());
		classMap.put("签到", new ArrayList<>());
		classMap.put("支付demo", new ArrayList<>());
		classMap.put("保单账户", new ArrayList<>());
		classMap.put("生日", new ArrayList<>());
		classMap.put("贺卡", new ArrayList<>());
		classMap.put("任务提醒功能", new ArrayList<>());
	}

	public static void main(String[] args) {
		//move(new File(orgnpath),null,1);
		//test();
		test1(new File("F:\\文档\\api"),-1);
		
	}
	public static void test() {
		//move(new File(orgnpath));
		File file = new File(outpath);
		for(File f : file.listFiles()) {
			String name = f.getName();
			boolean res = true;
			for(String key:classMap.keySet()) {
				if(name.indexOf(key)!=-1) {
					classMap.get(key).add(name);
					res  = false;
					break;
				}
			}
			if(res) {
				noclassMap.add(name);
			}
		}
		FileInputStream in = null;
		FileOutputStream out = null;
		for(String key:classMap.keySet()) {
			File f = new File(outpath + "\\" + key);
			if(!f.exists()) {
				f.mkdirs();
			}
			System.out.println(key+":"+classMap.get(key).size());
			System.out.println(classMap.get(key));
			for(String fl:classMap.get(key)) {
				String outpaths = outpath + "\\"+ key+"\\";
				if(fl.indexOf("后台")!=-1||fl.indexOf("管理端")!=-1) {			
					File ff = new File(outpaths+"\\后台");
					if(!ff.exists()) {
						ff.mkdirs();
					}
					outpaths += "\\后台";
				}
				outpaths += "\\"+fl;
				try {
					in = new FileInputStream(new File(outpath + "\\" + fl));
					out = new FileOutputStream(new File(outpaths));
					IOUtils.copy(in, out);
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					IOUtils.closeQuietly(in);
					IOUtils.closeQuietly(out);
				}
			}	
		}
		File f = new File(outpath + "\\" + "未分类");
		if(!f.exists()) {
			f.mkdirs();
		}
		for(String fl:noclassMap) {
			try {
				in = new FileInputStream(new File(outpath + "\\" + fl));
				out = new FileOutputStream(new File(outpath + "\\未分类\\"+ fl));
				IOUtils.copy(in, out);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				IOUtils.closeQuietly(in);
				IOUtils.closeQuietly(out);
			}
		}	
		System.out.println("公共："+noclassMap.size());
		System.out.println(noclassMap);
	}
	
	public static void test1(File file,int level) {
			String s = "";
			for(int i=0;i<level;i++) {
				s +="\t";
			}
			if (file.isFile()) {// 是文件就COPY
				String name = file.getName();
				String[] ns = name.split("----");
				String fn = ns[ns.length-1];
				StringBuffer sb = new StringBuffer();
				for(String st : ns) {
					sb.append("/"+st);
				}
				System.out.println(s+fn.substring(0, fn.indexOf("."))+"("+sb.toString().substring(1)+")");
			}else {
				level++;
				System.out.println(s+file.getName());
				File[] fs = file.listFiles();
				for(File f:fs) {
					test1(f,level);
				}
			}
	}

	public static void move(File file,String fileName,int level) {
		FileInputStream in = null;
		FileOutputStream out = null;
		try {
			if (file.isFile()) {// 是文件就COPY
				in = new FileInputStream(file);
				out = new FileOutputStream(new File(outpath + "\\" +fileName+"----"+ file.getName()));
				IOUtils.copy(in, out);
				i++;
			}else {
				if(level==1) {
					fileName = fileName==null?null:file.getName();
				}else {
					fileName = fileName==null?file.getName():(fileName+"----"+file.getName());
				}
				File[] fs = file.listFiles();
				for(File f:fs) {	
					move(f,fileName,2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		}

	}
	
	
	
	
}
