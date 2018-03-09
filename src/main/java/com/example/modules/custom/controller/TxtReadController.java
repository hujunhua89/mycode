package com.example.modules.custom.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.base.util.ChineseBig5TransUtil;
import com.example.base.util.JsonUtil;
import com.example.base.util.RSACryptUtil;
import com.example.modules.create.entity.CreateTableEntity;
import com.example.modules.create.mapper.CreateTableMapper;
import com.example.modules.custom.bean.TxtChapters;
import com.example.modules.custom.bean.TxtJsonInfo;
import com.example.modules.custom.entity.CustomTxt;
import com.example.modules.custom.entity.extension.CustomTxtExtension;
import com.example.modules.custom.mapper.CustomTxtMapper;
import com.example.modules.custom.service.ITxtInfoService;
import com.example.modules.custom.util.TxtHtmlFetchUtil;

@Controller
@RequestMapping("/txt")
public class TxtReadController {
	

	@Autowired
	private CreateTableMapper createTableMapper;
	
	@Autowired
	private ITxtInfoService txtInfoService;
	
	@Autowired
	private CustomTxtMapper customTxtMapper;

	@RequestMapping("/totxt")
	public String totxt(ModelMap model) {
		
		return "txt/conetent";
	}
	@RequestMapping("/init/c")
	@ResponseBody
	public String initc(ModelMap model) {
		CreateTableEntity table = new CreateTableEntity();
		table.setTableName("custom_txt_c");
		table.setTableComment("小说-优优");
		createTableMapper.createCustomTxt(table);
		Map<String, String> infom = new LinkedHashMap<>();
		infom.put("武侠", "http://www.gaoshiyspx.com/t04/");
		infom.put("乱伦", "http://www.gaoshiyspx.com/t02/");
		infom.put("都市", "http://www.gaoshiyspx.com/t01/");
		infom.put("另类", "http://www.gaoshiyspx.com/t06/");
		infom.put("笑话", "http://www.gaoshiyspx.com/t07/");
		infom.put("人妻", "http://www.gaoshiyspx.com/t03/");
		infom.put("校园", "http://www.gaoshiyspx.com/t05/");
		infom.put("技巧", "http://www.gaoshiyspx.com/t08/");
		for(String key:infom.keySet()) {
			int index = TxtHtmlFetchUtil.fetchTxtByCpageNum(infom.get(key)+"list_1.html");
			System.out.println(key+":"+index);
			txtInfoService.doFetchTxt("c", index, infom.get(key), key);
		}
		
		return "OK";
	}
	@RequestMapping("/inittxt/c")
	@ResponseBody
	public String inittxtc(ModelMap model) {
		CustomTxtExtension record = new CustomTxtExtension();
		record.setRuleFlag("c");
		record.setIsFetch("N");
		int s = customTxtMapper.countTitieAll(record);
		System.out.println("数量:"+s);
		int length = s/30;
		for (int i=0;i<=length;i++){
			int index =i*30;
			txtInfoService.doFetchTxtContent("c", "http://www.gaoshiyspx.com", index);
			try {
				System.out.println("休息5秒");
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "OK";
	}

	
	@RequestMapping("/query/{flag}")
	@SuppressWarnings(value="all")
	public String queryTxtList(@PathVariable String flag,String className,String titlelike,String orderby,ModelMap model,HttpSession session) {
		CustomTxtExtension record = new CustomTxtExtension();
		record.setTxtClass(StringUtils.isBlank(className)?null:className);
		record.setLikeTitle(StringUtils.isBlank(titlelike)?null:titlelike);
		if(StringUtils.isNotBlank(orderby)) {
			String ordercol = null;
			switch (orderby) {
			case "1":
				ordercol = " title";
				break;
			case "2":
				ordercol = " title desc";
				break;
			case "3":
				ordercol = " date_str";
				break;
			case "4":
				ordercol = " date_str desc";
				break;

			default:
				break;
			}
			record.setOrderbycol(ordercol);
		}
		record.setRuleFlag(flag);
		List<CustomTxt> txtlist = txtInfoService.queryTxtChapters(record);
		if(txtlist!= null &&!txtlist.isEmpty()) {
			for(CustomTxt txt:txtlist) {
				txt.setTitle((ChineseBig5TransUtil.trans(txt.getTitle(), ChineseBig5TransUtil.Target.chinese)));
			}
		}
		model.put("txtlist", txtlist);
		model.put("className", className);
		model.put("titlelike", titlelike);
		model.put("orderby", orderby);
		HashMap<Integer, String> readinfo = null;
		if(session.getAttribute("readinfo_"+flag)!=null) {
			readinfo = (HashMap<Integer, String>)session.getAttribute("readinfo_"+flag);
		}else {
			readinfo = new HashMap<>();	
		}
		model.put("readinfo", readinfo);
		return "txt/index";
	}
	
	@RequestMapping("/get/{flag}/{txtId}")
	@ResponseBody
	@SuppressWarnings(value="all")
	public CustomTxt getbyid(@PathVariable Integer txtId,@PathVariable String flag,HttpSession session) throws UnsupportedEncodingException {
		CustomTxt txt = txtInfoService.getTxtById(flag,txtId);
		if(txt!=null&&txt.getContent()!=null) {
			Pattern p_img = Pattern.compile("<(font|FONT)(.*?)(/>|></font>|>)");
			Matcher m_img = p_img.matcher(txt.getContent());
			while (m_img.find()) {
				// 获取到匹配的<img />标签中的内容
				String str_img = m_img.group(0);//获取整个
				txt.setContent(txt.getContent().replaceAll(str_img, "<font>"));
			}
			txt.setContent(ChineseBig5TransUtil.trans(txt.getContent(), ChineseBig5TransUtil.Target.chinese));
		}
		HashMap<Integer, String> readinfo = null;
		if(session.getAttribute("readinfo_"+flag)!=null) {
			readinfo = (HashMap<Integer, String>)session.getAttribute("readinfo_"+flag);
		}else {
			readinfo = new HashMap<>();	
		}
		readinfo.put(txt.getTxtId(), "Y");
		session.setAttribute("readinfo_"+flag, readinfo);
		return txt;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public TxtJsonInfo getTxtList(String className,String title) {
		TxtJsonInfo jsoninfo = new TxtJsonInfo();
		jsoninfo.setTitle(StringUtils.isBlank(className)?"我爱":className+"小说");
		CustomTxtExtension record = new CustomTxtExtension();
		record.setTxtClass(className);
		record.setLikeTitle(title);
		record.setRuleFlag("a");
		List<CustomTxt> txtlist = txtInfoService.queryTxtChapters(record);
		List<TxtChapters> chaList = new LinkedList<>();
		if(txtlist!=null&&!txtlist.isEmpty()) {
			TxtChapters cha = null;
			for(CustomTxt txt:txtlist) {
				cha = new TxtChapters();
				cha.setChapter_id(txt.getTxtId());
				cha.setFree(true);
				cha.setPrice(0f);
				cha.setTitle(txt.getTitle());
				cha.setWord_count(NumberUtils.toInt(txt.getMarkFlag(),0));
				chaList.add(cha);
			}
		}
		jsoninfo.setChapters(chaList);
		return jsoninfo;
	}
	@RequestMapping("/preread/{txtId}")
	@ResponseBody
	public TxtJsonInfo preread(@PathVariable Integer txtId,ModelMap model) {
		TxtJsonInfo jsoninfo = new TxtJsonInfo();
		jsoninfo.setJsonp("/txt/read/"+txtId);
		return jsoninfo;
	}
	@RequestMapping("/read/{txtId}")
	@ResponseBody
	public String readbyid(@PathVariable Integer txtId) throws UnsupportedEncodingException {
		String str ="duokan_fiction_chapter('";
		CustomTxt txt = txtInfoService.getTxtById("a",txtId);
		Map<String, Object> info = new HashMap<>();
		info.put("t", txt.getTitle());
		List<String> contlist = new ArrayList<>();
		contlist.add(txt.getContent());
		info.put("p", contlist);
		str+= RSACryptUtil.encryptBASE64(JsonUtil.toJson(info).getBytes("UTF-8"));
		str += "')";
		return str;
	}
	
	
}
