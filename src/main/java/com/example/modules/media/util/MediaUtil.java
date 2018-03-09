package com.example.modules.media.util;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;

import com.example.modules.media.bean.MediaObject;
import com.example.modules.media.constant.MediaType;
import com.example.modules.media.entity.LocalVideo;

public class MediaUtil {

	private static Map<String, String> mimeTypes = new HashMap<>();

	private static Map<String, String> fileTypes = new HashMap<>();

	private static Map<String, MediaType> fileAttrs = new HashMap<>();

	static {
		mimeTypes.put("abs", "audio/x-mpeg");
		mimeTypes.put("ai", "application/postscript");
		mimeTypes.put("aif", "audio/x-aiff");
		mimeTypes.put("aifc", "audio/x-aiff");
		mimeTypes.put("aiff", "audio/x-aiff");
		mimeTypes.put("aim", "application/x-aim");
		mimeTypes.put("art", "image/x-jg");
		mimeTypes.put("asf", "video/x-ms-asf");
		mimeTypes.put("asx", "video/x-ms-asf");
		mimeTypes.put("au", "audio/basic");
		mimeTypes.put("avi", "video/x-msvideo");
		mimeTypes.put("avx", "video/x-rad-screenplay");
		mimeTypes.put("bcpio", "application/x-bcpio");
		mimeTypes.put("bin", "application/octet-stream");
		mimeTypes.put("bmp", "image/bmp");
		mimeTypes.put("body", "text/html");
		mimeTypes.put("cdf", "application/x-cdf");
		mimeTypes.put("cer", "application/pkix-cert");
		mimeTypes.put("class", "application/java");
		mimeTypes.put("cpio", "application/x-cpio");
		mimeTypes.put("csh", "application/x-csh");
		mimeTypes.put("css", "text/css");
		mimeTypes.put("dib", "image/bmp");
		mimeTypes.put("doc", "application/msword");
		mimeTypes.put("dtd", "application/xml-dtd");
		mimeTypes.put("dv", "video/x-dv");
		mimeTypes.put("dvi", "application/x-dvi");
		mimeTypes.put("eot", "application/vnd.ms-fontobject");
		mimeTypes.put("eps", "application/postscript");
		mimeTypes.put("etx", "text/x-setext");
		mimeTypes.put("exe", "application/octet-stream");
		mimeTypes.put("gif", "image/gif");
		mimeTypes.put("gtar", "application/x-gtar");
		mimeTypes.put("gz", "application/x-gzip");
		mimeTypes.put("hdf", "application/x-hdf");
		mimeTypes.put("hqx", "application/mac-binhex40");
		mimeTypes.put("htc", "text/x-component");
		mimeTypes.put("htm", "text/html");
		mimeTypes.put("html", "text/html");
		mimeTypes.put("ief", "image/ief");
		mimeTypes.put("jad", "text/vnd.sun.j2me.app-descriptor");
		mimeTypes.put("jar", "application/java-archive");
		mimeTypes.put("java", "text/x-java-source");
		mimeTypes.put("jnlp", "application/x-java-jnlp-file");
		mimeTypes.put("jpe", "image/jpeg");
		mimeTypes.put("jpeg", "image/jpeg");
		mimeTypes.put("jpg", "image/jpeg");
		mimeTypes.put("js", "application/javascript");
		mimeTypes.put("jsf", "text/plain");
		mimeTypes.put("json", "application/json");
		mimeTypes.put("jspf", "text/plain");
		mimeTypes.put("kar", "audio/midi");
		mimeTypes.put("latex", "application/x-latex");
		mimeTypes.put("m3u", "audio/x-mpegurl");
		mimeTypes.put("mac", "image/x-macpaint");
		mimeTypes.put("man", "text/troff");
		mimeTypes.put("mathml", "application/mathml+xml");
		mimeTypes.put("me", "text/troff");
		mimeTypes.put("mid", "audio/midi");
		mimeTypes.put("midi", "audio/midi");
		mimeTypes.put("mif", "application/x-mif");
		mimeTypes.put("mov", "video/quicktime");
		mimeTypes.put("movie", "video/x-sgi-movie");
		mimeTypes.put("mp1", "audio/mpeg");
		mimeTypes.put("mp2", "audio/mpeg");
		mimeTypes.put("mp3", "audio/mpeg");
		mimeTypes.put("mp4", "video/mp4");
		mimeTypes.put("mpa", "audio/mpeg");
		mimeTypes.put("mpe", "video/mpeg");
		mimeTypes.put("mpeg", "video/mpeg");
		mimeTypes.put("mpega", "audio/x-mpeg");
		mimeTypes.put("mpg", "video/mpeg");
		mimeTypes.put("mpv2", "video/mpeg2");
		mimeTypes.put("ms", "application/x-wais-source");
		mimeTypes.put("nc", "application/x-netcdf");
		mimeTypes.put("oda", "application/oda");
		mimeTypes.put("odb", "application/vnd.oasis.opendocument.database");
		mimeTypes.put("odc", "application/vnd.oasis.opendocument.chart");
		mimeTypes.put("odf", "application/vnd.oasis.opendocument.formula");
		mimeTypes.put("odg", "application/vnd.oasis.opendocument.graphics");
		mimeTypes.put("odi", "application/vnd.oasis.opendocument.image");
		mimeTypes.put("odm", "application/vnd.oasis.opendocument.text-master");
		mimeTypes.put("odp", "application/vnd.oasis.opendocument.presentation");
		mimeTypes.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
		mimeTypes.put("odt", "application/vnd.oasis.opendocument.text");
		mimeTypes.put("otg", "application/vnd.oasis.opendocument.graphics-template");
		mimeTypes.put("oth", "application/vnd.oasis.opendocument.text-web");
		mimeTypes.put("otp", "application/vnd.oasis.opendocument.presentation-template");
		mimeTypes.put("ots", "application/vnd.oasis.opendocument.spreadsheet-template ");
		mimeTypes.put("ott", "application/vnd.oasis.opendocument.text-template");
		mimeTypes.put("ogx", "application/ogg");
		mimeTypes.put("ogv", "video/ogg");
		mimeTypes.put("oga", "audio/ogg");
		mimeTypes.put("ogg", "audio/ogg");
		mimeTypes.put("otf", "application/x-font-opentype");
		mimeTypes.put("spx", "audio/ogg");
		mimeTypes.put("flac", "audio/flac");
		mimeTypes.put("anx", "application/annodex");
		mimeTypes.put("axa", "audio/annodex");
		mimeTypes.put("axv", "video/annodex");
		mimeTypes.put("xspf", "application/xspf+xml");
		mimeTypes.put("pbm", "image/x-portable-bitmap");
		mimeTypes.put("pct", "image/pict");
		mimeTypes.put("pdf", "application/pdf");
		mimeTypes.put("pgm", "image/x-portable-graymap");
		mimeTypes.put("pic", "image/pict");
		mimeTypes.put("pict", "image/pict");
		mimeTypes.put("pls", "audio/x-scpls");
		mimeTypes.put("png", "image/png");
		mimeTypes.put("pnm", "image/x-portable-anymap");
		mimeTypes.put("pnt", "image/x-macpaint");
		mimeTypes.put("ppm", "image/x-portable-pixmap");
		mimeTypes.put("ppt", "application/vnd.ms-powerpoint");
		mimeTypes.put("pps", "application/vnd.ms-powerpoint");
		mimeTypes.put("ps", "application/postscript");
		mimeTypes.put("psd", "image/vnd.adobe.photoshop");
		mimeTypes.put("qt", "video/quicktime");
		mimeTypes.put("qti", "image/x-quicktime");
		mimeTypes.put("qtif", "image/x-quicktime");
		mimeTypes.put("ras", "image/x-cmu-raster");
		mimeTypes.put("rdf", "application/rdf+xml");
		mimeTypes.put("rgb", "image/x-rgb");
		mimeTypes.put("rm", "application/vnd.rn-realmedia");
		mimeTypes.put("roff", "text/troff");
		mimeTypes.put("rtf", "application/rtf");
		mimeTypes.put("rtx", "text/richtext");
		mimeTypes.put("sfnt", "application/font-sfnt");
		mimeTypes.put("sh", "application/x-sh");
		mimeTypes.put("shar", "application/x-shar");
		mimeTypes.put("sit", "application/x-stuffit");
		mimeTypes.put("snd", "audio/basic");
		mimeTypes.put("src", "application/x-wais-source");
		mimeTypes.put("sv4cpio", "application/x-sv4cpio");
		mimeTypes.put("sv4crc", "application/x-sv4crc");
		mimeTypes.put("svg", "image/svg+xml");
		mimeTypes.put("svgz", "image/svg+xml");
		mimeTypes.put("swf", "application/x-shockwave-flash");
		mimeTypes.put("t", "text/troff");
		mimeTypes.put("tar", "application/x-tar");
		mimeTypes.put("tcl", "application/x-tcl");
		mimeTypes.put("tex", "application/x-tex");
		mimeTypes.put("texi", "application/x-texinfo");
		mimeTypes.put("texinfo", "application/x-texinfo");
		mimeTypes.put("tif", "image/tiff");
		mimeTypes.put("tiff", "image/tiff");
		mimeTypes.put("tr", "text/troff");
		mimeTypes.put("tsv", "text/tab-separated-values");
		mimeTypes.put("ttf", "application/x-font-ttf");
		mimeTypes.put("txt", "text/plain");
		mimeTypes.put("ulw", "audio/basic");
		mimeTypes.put("ustar", "application/x-ustar");
		mimeTypes.put("vxml", "application/voicexml+xml");
		mimeTypes.put("xbm", "image/x-xbitmap");
		mimeTypes.put("xht", "application/xhtml+xml");
		mimeTypes.put("xhtml", "application/xhtml+xml");
		mimeTypes.put("xls", "application/vnd.ms-excel");
		mimeTypes.put("xml", "application/xml");
		mimeTypes.put("xpm", "image/x-xpixmap");
		mimeTypes.put("xsl", "application/xml");
		mimeTypes.put("xslt", "application/xslt+xml");
		mimeTypes.put("xul", "application/vnd.mozilla.xul+xml");
		mimeTypes.put("xwd", "image/x-xwindowdump");
		mimeTypes.put("vsd", "application/vnd.visio");
		mimeTypes.put("wav", "audio/x-wav");
		mimeTypes.put("wbmp", "image/vnd.wap.wbmp");
		mimeTypes.put("wml", "text/vnd.wap.wml");
		mimeTypes.put("wmlc", "application/vnd.wap.wmlc");
		mimeTypes.put("wmls", "text/vnd.wap.wmlsc");
		mimeTypes.put("wmlscriptc", "application/vnd.wap.wmlscriptc");
		mimeTypes.put("wmv", "video/x-ms-wmv");
		mimeTypes.put("woff", "application/font-woff");
		mimeTypes.put("woff2", "application/font-woff2");
		mimeTypes.put("wrl", "model/vrml");
		mimeTypes.put("wspolicy", "application/wspolicy+xml");
		mimeTypes.put("z", "application/x-compress");
		mimeTypes.put("zip", "application/zip");
		for (String key : mimeTypes.keySet()) {
			fileTypes.put(mimeTypes.get(key), key);
		}
		fileAttrs.put("pict", MediaType.image);
		fileAttrs.put("ras", MediaType.image);
		fileAttrs.put("png", MediaType.image);
		fileAttrs.put("pnm", MediaType.image);
		fileAttrs.put("pnt", MediaType.image);
		fileAttrs.put("xwd", MediaType.image);
		fileAttrs.put("mac", MediaType.image);
		fileAttrs.put("pgm", MediaType.image);
		fileAttrs.put("jpeg", MediaType.image);
		fileAttrs.put("art", MediaType.image);
		fileAttrs.put("svg", MediaType.image);
		fileAttrs.put("ppm", MediaType.image);
		fileAttrs.put("xpm", MediaType.image);
		fileAttrs.put("dib", MediaType.image);
		fileAttrs.put("bmp", MediaType.image);
		fileAttrs.put("pic", MediaType.image);
		fileAttrs.put("tif", MediaType.image);
		fileAttrs.put("wbmp", MediaType.image);
		fileAttrs.put("svgz", MediaType.image);
		fileAttrs.put("qti", MediaType.image);
		fileAttrs.put("qtif", MediaType.image);
		fileAttrs.put("pbm", MediaType.image);
		fileAttrs.put("psd", MediaType.image);
		fileAttrs.put("xbm", MediaType.image);
		fileAttrs.put("tiff", MediaType.image);
		fileAttrs.put("gif", MediaType.image);
		fileAttrs.put("ief", MediaType.image);
		fileAttrs.put("rgb", MediaType.image);
		fileAttrs.put("jpe", MediaType.image);
		fileAttrs.put("jpg", MediaType.image);
		fileAttrs.put("pct", MediaType.image);
		fileAttrs.put("movie", MediaType.video);
		fileAttrs.put("mov", MediaType.video);
		fileAttrs.put("axv", MediaType.video);
		fileAttrs.put("mpe", MediaType.video);
		fileAttrs.put("qt", MediaType.video);
		fileAttrs.put("mpg", MediaType.video);
		fileAttrs.put("wmv", MediaType.video);
		fileAttrs.put("mpv2", MediaType.video);
		fileAttrs.put("asf", MediaType.video);
		fileAttrs.put("asx", MediaType.video);
		fileAttrs.put("dv", MediaType.video);
		fileAttrs.put("ogv", MediaType.video);
		fileAttrs.put("mpeg", MediaType.video);
		fileAttrs.put("avi", MediaType.video);
		fileAttrs.put("avx", MediaType.video);
		fileAttrs.put("mp4", MediaType.video);
		fileAttrs.put("axa", MediaType.audio);
		fileAttrs.put("midi", MediaType.audio);
		fileAttrs.put("mpa", MediaType.audio);
		fileAttrs.put("au", MediaType.audio);
		fileAttrs.put("aif", MediaType.audio);
		fileAttrs.put("flac", MediaType.audio);
		fileAttrs.put("mpega", MediaType.audio);
		fileAttrs.put("mid", MediaType.audio);
		fileAttrs.put("snd", MediaType.audio);
		fileAttrs.put("abs", MediaType.audio);
		fileAttrs.put("oga", MediaType.audio);
		fileAttrs.put("ogg", MediaType.audio);
		fileAttrs.put("spx", MediaType.audio);
		fileAttrs.put("ulw", MediaType.audio);
		fileAttrs.put("aiff", MediaType.audio);
		fileAttrs.put("aifc", MediaType.audio);
		fileAttrs.put("m3u", MediaType.audio);
		fileAttrs.put("kar", MediaType.audio);
		fileAttrs.put("wav", MediaType.audio);
		fileAttrs.put("mp2", MediaType.audio);
		fileAttrs.put("mp1", MediaType.audio);
		fileAttrs.put("mp3", MediaType.audio);
		fileAttrs.put("pls", MediaType.audio);

	}
	/***
	 * 根据文件名后缀获取http响应头类型
	* @Description: (描述) 
	* @param @param filename
	* @param @return
	* @return String 
	* @throws
	 */
	public static String getMimeType(String filename) {
		if (filename == null)
			return (null);
		int period = filename.lastIndexOf('.');
		if (period < 0)
			return (null);
		String extension = filename.substring(period + 1);
		if (extension.length() < 1)
			return (null);
		return (mimeTypes.get(extension));
	}
	/***
	 * 根据http请求响应头类型获取文件类型
	* @Description: (描述) 
	* @param @param mimeType
	* @param @return
	* @return String 
	* @throws
	 */
	public static String getFileType(String mimeType) {
		if (mimeType == null)
			return (null);
		return (fileTypes.get(mimeType));
	}
	/**
	 * 根据文件类型获取媒体类型
	* @Description: (描述) 
	* @param @param fileType
	* @param @return
	* @return MediaType 
	* @throws
	 */
	public static MediaType getMediaType(String fileType) {
		if (fileType == null)
			return (null);
		return (fileAttrs.get(fileType));
	}
	/***
	 * 获取完全路径
	* @Description: (描述) 
	* @param @return
	* @return String 
	* @throws
	 */
	public static String getCompletePath(MediaObject obj) {
		StringBuffer completePath = new StringBuffer();
		if(StringUtils.isNotBlank(obj.getRootPath())) {
			completePath.append(obj.getRootPath());
		}
		if(obj.getMediaType()!=null) {
			completePath.append(File.separatorChar);
			completePath.append(obj.getMediaType().toString());
		}
		if(StringUtils.isNotBlank(obj.getRelativePath())) {
			completePath.append(File.separatorChar);
			completePath.append(obj.getRelativePath());
		}
		if(StringUtils.isNotBlank(obj.getFileName())) {
			completePath.append(File.separatorChar);
			completePath.append(obj.getFileName());
		}
		if(StringUtils.isNotBlank(obj.getFileType())) {
			completePath.append(".");
			completePath.append(obj.getFileType());
		}
		return completePath.toString();
	}
	
	/***
	 * 获取完全路径
	* @Description: (描述) 
	* @param @return
	* @return String 
	* @throws
	 */
	public static String getCompletePath(LocalVideo obj) {
		StringBuffer completePath = new StringBuffer();
		if(StringUtils.isNotBlank(obj.getRootPath())) {
			completePath.append(obj.getRootPath());
		}
		if(StringUtils.isNotBlank(obj.getRelativePath())) {
			if(!obj.getRelativePath().startsWith("\\")) {
				completePath.append(File.separatorChar);
			}
			completePath.append(obj.getRelativePath());
		}
		if(StringUtils.isNotBlank(obj.getFileName())) {
			completePath.append(File.separatorChar);
			completePath.append(obj.getFileName());
		}
		if(StringUtils.isNotBlank(obj.getFileType())) {
			completePath.append(".");
			completePath.append(obj.getFileType());
		}
		return completePath.toString();
	}
	public static String formatFileSize(long filesize) {
		String fz = null;
		BigDecimal b = new BigDecimal(filesize);
		BigDecimal c = new BigDecimal(1);
		if(filesize>=0&&filesize<1024) {
			fz = "b";	
		}else if(filesize>=1024&&filesize<(1024*1024)) {
			fz = "KB";
			c = new BigDecimal(1024);
		}else if(filesize>=(1024*1024)&&filesize<(1024*1024*1024)) {
			fz = "M";
			c = new BigDecimal(1024*1024);
		}else if(filesize>=(1024*1024*1024)) {
			fz = "G";
			c = new BigDecimal(1024*1024*1024);
		}
		return b.divide(c).setScale(1, BigDecimal.ROUND_UP)+fz;
	}
	

	public static String getFileMD5(String filepath) {
		String md5 = null;
		File file = new File(filepath);
		if(file.exists()) {
			FileInputStream bis = null;
			try {
				bis = new FileInputStream(file);
				md5 = DigestUtils.md5Hex(bis);
				System.out.println("wtf:"+md5);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				IOUtils.closeQuietly(bis);
			}
		}else {
			System.out.println("MD5文件不存在:"+filepath);	
		}
		return md5;
	}
	public static void main(String[] args) {
		String filepath = "I:\\备份\\电影\\另外\\小说\\mx\\出 .avi";
		File file = new File(filepath);
		System.out.println(file.length());
	}
	
}
