package com.example.modules.media.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.example.base.util.JsonUtil;
import com.example.modules.media.entity.LocalVideo;

public class MediaLocalFileUtil {

	
	public static String rootPath = "I:\\备份\\电影\\另外";
	
	private static int filecount = 1;
	
	private static Map<String, Integer>  typeCount = new HashMap<>();
	
	private static Map<String, Integer>  fileCount = new HashMap<>();

	private static Map<String, List<String>> filelist = new HashMap<>();
	

	private static Set<String>  videotype = new HashSet<>();
	
	private static String fileWeight = "";
	
	private static Map<String, List<String>>  notype = new HashMap<>();
	
	private static Set<String>  deltype = new HashSet<>();
	
	private static List<String> repetitiondir = new ArrayList<>();
	
	private static Map<String, List<String>>  subTitle = new HashMap<>();
	
	private static boolean isdel = true;
	
	public static List<LocalVideo> videoList = new LinkedList<>();
	
	static {
		//notype.put("85或以上版本____", new ArrayList<>());
		//notype.put("torrent", new ArrayList<>());
		//notype.put("qdl2", new ArrayList<>());
		//notype.put("db", new ArrayList<>());
		//notype.put("srt", new ArrayList<>());
		//notype.put("td", new ArrayList<>());
		//notype.put("7z", new ArrayList<>());
//		notype.put("txt", new ArrayList<>());
//		notype.put("torrent", new ArrayList<>());
//		deltype.add("txt");
//		deltype.add("torrent");
		//repetitiontype.add("rmvb");
		//repetitiontype.add("avi");
		//repetitiontype.add("mkv");
		//视频类型
		videotype.add("wmv");
		videotype.add("mpg");
		videotype.add("mp4");
		videotype.add("flv");
		videotype.add("mpeg");
		videotype.add("wm");
		videotype.add("rm");
		videotype.add("rmvb");
		videotype.add("3gp");
		videotype.add("mkv");
		videotype.add("amv");
		videotype.add("avi");
		//字幕
		subTitle.put("ssa", new ArrayList<>());
		subTitle.put("ass", new ArrayList<>());
		subTitle.put("smi", new ArrayList<>());
		subTitle.put("srt", new ArrayList<>());	
	}
	
	
	public static void conunt(File file) {
		if (file.isFile()) {// 是文件就记录
			filecount++;
			String name = file.getName();
			String fileType = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
			String filename = name.substring(0,name.lastIndexOf('.')).toLowerCase();
			if(typeCount.containsKey(fileType)) {
				typeCount.put(fileType, typeCount.get(fileType)+1);
			}else {
				typeCount.put(fileType, 1);
			}
			if(notype.containsKey(fileType)) {
				notype.get(fileType).add(file.getAbsolutePath());
			}
			if(deltype.contains(fileType)) {
				if(isdel) {
					file.delete();
				}else {
					FileInputStream in = null;
					FileOutputStream out = null;
					try {
						in = new FileInputStream(file);
						out = new FileOutputStream(new File("G:\\data\\" +filecount+"----"+ file.getName()));
						IOUtils.copy(in, out);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						IOUtils.closeQuietly(in);
						IOUtils.closeQuietly(out);
					}
				}
			}
		}else {
			if(fileWeight.split("\\\\").length<file.getPath().split("\\\\").length) {
				fileWeight = file.getPath();
			}
			for(File f:file.listFiles()) {
				conunt(f);
			}
		}
	}
	
	public static void searceVideo(File file){
		if (file.isFile()) {// 是文件就记录
			String name = file.getName();
			String fileType = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
			String filename = name.substring(0,name.lastIndexOf('.')).toLowerCase();
			if(videotype.contains(fileType)) {
				if (filename.endsWith(" ")) {
					File newfile = new File(file.getParent()+"\\"+filename.trim()+"."+fileType);
					if(!newfile.exists()) {
						file.renameTo(newfile);
					}
					System.out.println(file.getParent()+"\\"+filename+"."+fileType);
				}
				repetitiondir.add(name);
				LocalVideo video = new LocalVideo();
				video.setRootPath(rootPath);
				video.setRelativePath(file.getParent().replace(rootPath, ""));
				video.setCreateTime(new Date());
				video.setFileName(filename);
				video.setFileType(fileType);
				video.setFileSize(file.length()+"");
				videoList.add(video);
			}
			
		}else {
			for(File f:file.listFiles()) {
				searceVideo(f);
			}
		}
	}
	public static void main(String[] args) throws IOException {
		long s = System.currentTimeMillis();
		searceVideo(new File(rootPath));
		for(LocalVideo video : videoList) {
			//System.out.println(MediaUtil.getCompletePath(video)+JsonUtil.toJson(video));
		}
//		conunt(new File(rootPath));
//		System.out.println("文件数量："+filecount);
//		System.out.println("文件统计："+JsonUtil.toJson(typeCount));
////		for(String type : typeCount.keySet()) {
////			if(!videotype.contains(type)) {
////				System.out.println(type+"："+typeCount.get(type));
////			}
////		}
//		//System.out.println("最深文件夹："+fileWeight);
//		System.out.println("最深文件夹级："+(fileWeight.split("\\\\").length-rootPath.split("\\\\").length));
//		System.out.println("奇怪文件统计："+JsonUtil.toJson(notype));
//		
//		System.out.println("=============================="+repetitiondir.size());
//		for(String type:subTitle.keySet()) {
//			System.out.println(type+"："+subTitle.get(type));
//		}
//		for(String key :fileCount.keySet()) {
//			int i = fileCount.get(key);
//			if(i>1) {
//				System.out.println(key+"：");
//				for(String st:filelist.get(key)) {
//					String md5str = MediaUtil.getFileMD5(st);
//					System.out.println(md5str+":"+st);
//				}
//				
//				System.out.println("----------------------");
//			}
//		}
		System.out.println("统计耗时："+(System.currentTimeMillis()-s));
	}
}
