package com.example.modules.media.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.DemoController;
import com.example.modules.media.entity.LocalVideo;
import com.example.modules.media.service.ILocalFileService;
import com.example.modules.media.util.MediaUtil;

@Controller
@RequestMapping("/media/video")
public class VideoController {
	
	private static final Logger log = LoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	private ILocalFileService localFileService;
	
	@RequestMapping("/localvideo")
	public String initLocalVideo(String titlelike,String orderby,ModelMap model) {
		LocalVideo video = new LocalVideo();
		video.setFileName(titlelike);
		List<LocalVideo> videolist =  localFileService.queryLocalVideo(video);
		model.addAttribute("videolist",videolist);
		model.addAttribute("titlelike",titlelike);
		return "video/local_list";
	}

	@RequestMapping("/mp4")
	public void mp4(HttpServletRequest request,HttpServletResponse response) {
        File downloadFile = new File("G:\\data\\media\\video\\2018-02-24\\20180224174900739.mp4"); // 要下载的文件
        long fileLength = downloadFile.length();// 记录文件大小
        long pastLength = 0;// 记录已下载文件大小
        long toLength = 0;// 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
        long contentLength = 0;// 客户端请求的字节总量
        String rangeBytes = "";// 记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容
        // ETag header
        // The ETag is contentLength + lastModified
        response.setHeader("ETag",
                "W/\"" + fileLength + "-" + downloadFile.lastModified() + "\"");
        // Last-Modified header
        response.setHeader("Last-Modified",
                new Date(downloadFile.lastModified()).toString());
        log.info("user-aget"
                + request.getHeader("User-Agent"));
        if (request.getHeader("Range") != null) {// 客户端请求的下载的文件块的开始字节
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            log.info("request.getHeader(\"Range\")="
                    + request.getHeader("Range"));
            rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
            if (rangeBytes.indexOf('-') == rangeBytes.length() - 1) {// bytes=969998336-
                rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                pastLength = Long.parseLong(rangeBytes.trim());
                toLength = fileLength - 1;
            } else {// bytes=1275856879-1275877358
                String temp0 = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                String temp2 = rangeBytes.substring(
                        rangeBytes.indexOf('-') + 1, rangeBytes.length());
                // bytes=1275856879-1275877358，从第 1275856879个字节开始下载
                pastLength = Long.parseLong(temp0.trim());
                // bytes=1275856879-1275877358，到第 1275877358 个字节结束
                toLength = Long.parseLong(temp2);
            }
        } else {// 从开始进行下载
            toLength = fileLength - 1;
        }
        // 客户端请求的是1275856879-1275877358 之间的字节
        contentLength = toLength - pastLength + 1;
        if (contentLength < Integer.MAX_VALUE) {
            response.setContentLength((int) contentLength);
        } else {
            // Set the content-length as String to be able to use a long
            response.setHeader("content-length", "" + contentLength);
        }
       
        // 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
        response.setHeader("Accept-Ranges", "bytes");
        // 必须先设置content-length再设置header
        response.addHeader("Content-Range", "bytes " + pastLength + "-"
                + toLength + "/" + fileLength);

        response.setBufferSize(2048);
        InputStream istream = null;
        OutputStream os = null;
        try {
        	 String contentType = MediaUtil.getMimeType(downloadFile.getName());
             if (null != contentType) {
                 response.setContentType(contentType);
                 response.setHeader("Content-Disposition", "attachment;filename="+ parseCNUrlCode(downloadFile.getName(), "UTF-8"));
             }
            os = response.getOutputStream();
            istream = new BufferedInputStream(
                    new FileInputStream(downloadFile), 2048);
            try {
                copyRange(istream, os, pastLength, toLength);
            } catch (IOException ie) {
                /**
                 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
                 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。
                 * 尤其是对于迅雷这种吸血的客户端软件， 明明已经有一个线程在读取 bytes=1275856879-1275877358，
                 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
                 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
                 * 所以，我们忽略这种异常
                 */
                // ignore
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
        	IOUtils.closeQuietly(istream);
        }
	}
	
	protected void copyRange(InputStream istream, OutputStream ostream,
            long start, long end) throws IOException {

        long skipped = 0;
        skipped = istream.skip(start);

        if (skipped < start) {
            throw new IOException("skip fail: skipped=" + Long.valueOf(skipped)
                    + ", start=" + Long.valueOf(start));
        }

        long bytesToRead = end - start + 1;

        byte buffer[] = new byte[2048];
        int len = buffer.length;
        while ((bytesToRead > 0) && (len >= buffer.length)) {
            try {
                len = istream.read(buffer);
                if (bytesToRead >= len) {
                    ostream.write(buffer, 0, len);
                    bytesToRead -= len;
                } else {
                    ostream.write(buffer, 0, (int) bytesToRead);
                    bytesToRead = 0;
                }
            } catch (IOException e) {
                len = -1;
                throw e;
            }
            if (len < buffer.length)
                break;
        }
	}
	/** * 
	 * 判断字符是否是中文
	 * @param c
	 * @return boolean
	 * @author Junhua Hu
	 * @date 2017-7-24
	 */
	public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
	/****
	 * URLEncoder编码中的中文字符（非中文不编码）
	 * @param source
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException String
	 * @author Junhua Hu
	 * @date 2017-7-24
	 */
	public static String parseCNUrlCode(String source,String charset) throws UnsupportedEncodingException{
		char[] ch = source.toCharArray();
		StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
            	sb.append(URLEncoder.encode(c+"", charset)) ;
            }else{
            	sb.append(c) ;
            }
        }
        return sb.toString();
	}
}
