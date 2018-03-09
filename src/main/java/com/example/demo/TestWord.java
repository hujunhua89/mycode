package com.example.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class TestWord {
	/**
	 * 读取word文件内容
	 * 
	 * @param path
	 * @return buffer
	 */

	public static String readWord(String path) {
		String buffer = "";
		InputStream is = null;
		try {
			if (path.endsWith(".doc")) {
				is = new FileInputStream(new File(path));
				WordExtractor ex = new WordExtractor(is);
				buffer = ex.getText();
				ex.close();
			} else if (path.endsWith("docx")) {
				OPCPackage opcPackage = POIXMLDocument.openPackage(path);
				POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
				buffer = extractor.getText();
				extractor.close();
			} else {
				System.out.println("此文件不是word文件！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}

		return buffer;
	}

	public static void main(String[] args) {
		// List<Word> words = WordSegmenter.segWithStopWords("我爱北京天安门，天安门上太阳升");
		// System.out.println("词："+words);
		// PartOfSpeechTagging.process(words);
		// System.out.println("标注词性："+words);
		//String txt = readWord("G:\\data\\5635----被我养在房间的小萝莉1-32 (1).doc");
		File file = new File("G:\\data");
		BufferedWriter out = null;
		try {
			for(File doc:file.listFiles()) {
				if(doc.isFile()) {
					String name  =doc.getName();
					name = name.substring(name.lastIndexOf("----")+4, name.indexOf(".")).replaceAll(" ", "");
					File writename = new File("G:\\data\\"+name+".txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
					writename.createNewFile(); // 创建新文件
					out = new BufferedWriter(new FileWriter(writename));
					String txt = readWord(doc.getAbsolutePath());
					String info = parseCNUrlCode(txt);
					out.write(info.replaceAll("　　", "\r\n")); // \r\n即为换行
					out.flush(); // 把缓存区内容压入文件
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * * 判断字符是否是中文
	 * 
	 * @param c
	 * @return boolean
	 * @author Junhua Hu
	 * @date 2017-7-24
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	public static String parseCNUrlCode(String source) {
		char[] ch = source.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				sb.append(c);
			} else {
				// sb.append(c) ;
			}
		}
		return sb.toString();
	}

	/****
	 * URLEncoder编码中的中文字符（非中文不编码）
	 * 
	 * @param source
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 *             String
	 * @author Junhua Hu
	 * @date 2017-7-24
	 */
	public static String parseCNUrlCode(String source, String charset) throws UnsupportedEncodingException {
		char[] ch = source.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				sb.append(URLEncoder.encode(c + "", charset));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
