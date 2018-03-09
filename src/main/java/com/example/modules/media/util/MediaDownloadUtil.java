package com.example.modules.media.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.base.util.HttpSSLUtil;
import com.example.base.util.JsonUtil;
import com.example.modules.media.bean.MediaObject;
import com.example.modules.media.constant.MediaType;

public class MediaDownloadUtil {

	private static final Logger log = LoggerFactory.getLogger(MediaDownloadUtil.class);
	
	public static final String MEDIA_ROOT_PATH = "G:\\data\\media";

	/***
	 * 把外部图片转成base64
	 * 
	 * @param url
	 * @return
	 */
	public static MediaObject downloadImage(final String url,String path,String filename) {
		CloseableHttpClient httpClient = HttpSSLUtil.createSSLClientDefault();
		CloseableHttpResponse response = null;
		HttpGet get = new HttpGet(url);
		get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.119 Safari/537.36");
		MediaObject mediaObj = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		long s = System.currentTimeMillis();
		log.info("开始下载：{}",url);
		try {
			response = httpClient.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					String fileType = MediaUtil.getFileType(entity.getContentType().getValue());
					if(StringUtils.isBlank(fileType)) {
						//无配置类型-直接从请求url取
						fileType = url.substring(url.lastIndexOf('.') + 1);
					}
					MediaType mediaType = MediaUtil.getMediaType(fileType);
					log.info("响应类型:{};文件类型:{};媒体类型:{}",entity.getContentType().getValue(),fileType,mediaType);
					long size = entity.getContentLength();
					log.info("文件大小:{}",MediaUtil.formatFileSize(size));
					if(mediaType!=null) {
						mediaObj = new MediaObject();
						mediaObj.setRootPath(MEDIA_ROOT_PATH);
						mediaObj.setRelativePath(path);
						mediaObj.setFileName(filename);
						mediaObj.setFileType(fileType);
						mediaObj.setMediaType(mediaType);
						mediaObj.setFileSize(size);
						String outfilepath = MediaUtil.getCompletePath(mediaObj);
						File outfile = new File(outfilepath);
						if(!outfile.getParentFile().exists()) {
							outfile.getParentFile().mkdirs();
						}
						bis = new BufferedInputStream(entity.getContent());
						bos = new BufferedOutputStream(new FileOutputStream(outfile));
						IOUtils.copy(bis, bos);
						mediaObj.setDownload(true);
						log.info("文件下载完成:{}",outfilepath);;
					}else {
						log.info("非支持下载文件");
					}	
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != response) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(bis);
		}
		log.info("下载{}:耗时{}",url,(System.currentTimeMillis()-s));
		return mediaObj;
	}
	
	public static void addheader() {
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Accept", "*/*");
		headerMap.put("Accept-Encoding", "gzip, deflate, br");
		headerMap.put("Accept-Language", "zh-Hans-CN, zh-Hans; q=0.8, en-US; q=0.5, en; q=0.3");
		headerMap.put("Cache-Control", "no-cache");
		headerMap.put("Connection", "keep-alive");
		headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 Edge/16.16299");
		
	}
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		String date = DateFormatUtils.format(cal, "yyyy-MM-dd");
		String filename = DateFormatUtils.format(cal, "yyyyMMddHHmmssS");
		MediaObject obj = MediaDownloadUtil.downloadImage("", date, filename);		
		if(obj.isDownload()) {
			//61761525c0d6302eeeabd7cdfd7c9022
			long s = System.currentTimeMillis();
			String md5 =  MediaUtil.getFileMD5(MediaUtil.getCompletePath(obj));
			obj.setMd5Code(md5);
			log.info("MD5耗时：{}",(System.currentTimeMillis()-s));
		}
		log.info("完成：{}",JsonUtil.toJson(obj));
	}
}
