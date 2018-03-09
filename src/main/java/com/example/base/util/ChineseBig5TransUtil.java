package com.example.base.util;

import java.util.ResourceBundle;

public class ChineseBig5TransUtil {
	public enum Target {
		chinese, big5
	}

	private ResourceBundle transTable = null;
	private final static ChineseBig5TransUtil Chinesetrans = new ChineseBig5TransUtil();
	private final static ChineseBig5TransUtil Big5trans = new ChineseBig5TransUtil();

	public static ChineseBig5TransUtil getInstance(Target target) {
		if (target.equals(Target.big5)) {
			if (Big5trans.transTable == null) {
				Big5trans.transTable = ResourceBundle.getBundle("ChinesetoBig5");
			}
			return Big5trans;
		} else {
			if (Chinesetrans.transTable == null) {
				Chinesetrans.transTable = ResourceBundle.getBundle("Big5toChinese");
			}
			return Chinesetrans;
		}
	}

	private ChineseBig5TransUtil() {
	}

	/**
	 * 不需自行创建转换器即可转换. 内部调用{@link #转换(String) 转换}方法.
	 * 
	 * @param 文本
	 *            任意长度
	 * @param 简繁
	 *            目标格式
	 * @return 转换为目标格式的文本
	 * @throws IllegalArgumentException
	 *             文本为null时
	 */
	public static String trans(String text, Target target) {
		return getInstance(target).trans(text);
	}

	/**
	 * 不进行分词. 如果长度大于1, 寻找匹配的短语. 如没有, 按字寻找对应字后组合.
	 * 
	 * @param 输入文本
	 *            任意长度
	 * @return 转换后的文本
	 * @throws IllegalArgumentException
	 *             文本为null时
	 */
	public String trans(String text) {
		if (text == null) {
			throw new IllegalArgumentException("字符串为null");
		}

		StringBuilder sb = new StringBuilder();

		if (text.length() > 1 && transTable.containsKey(text)) {
			return transTable.getString(text);
		}

		for (char cr : text.toCharArray()) {
			String str = String.valueOf(cr);

			// 如有多个对应字符, 暂时用第一个; 如果没有对应字符, 保留原字符
			sb.append(transTable.containsKey(str) ? transTable.getString(str).charAt(0) : str);
		}
		return sb.toString();
	}
}
