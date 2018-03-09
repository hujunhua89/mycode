package com.example.modules.custom.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.base.util.JsonUtil;
import com.example.modules.custom.entity.CustomTxt;
import com.example.modules.custom.entity.extension.CustomTxtExtension;
import com.example.modules.custom.mapper.CustomTxtMapper;
import com.example.modules.custom.service.ITxtInfoService;
import com.example.modules.custom.util.TxtHtmlFetchUtil;

@Service
public class TxtInfoServiceImpl implements ITxtInfoService{

	@Autowired
	private CustomTxtMapper customTxtMapper;
	
	
	@Override
	public int doFetchTxt(String flag,int index,String url,String classname) {
		int count =0;
		switch (flag) {
		case "a":
			count= doFetchTxtByA(url, index);
			break;
		case "b":
			
			break;
		case "c":
			count = doFetchTxtByC(url, index,classname);
			break;
		default:
			break;
		}
		return count;
	}
	
	@Override
	public int doFetchTxtByA(String url,int index) {	
		int count = 0;
		for(int i=1;i<=index; i++ ) {
			if(i>1) {
				url += "-"+i+".html";
			}else {
				url += ".html";
			}
			List<CustomTxt> txtlist = TxtHtmlFetchUtil.fetchTxtInfo("a",url,null);
			if(txtlist!=null&&!txtlist.isEmpty()) {
				for(CustomTxt txt: txtlist) {
					customTxtMapper.insertSelective(txt);
				}
				
			}
			count+=txtlist.size();
		}		
		System.out.println(JsonUtil.toJson(TxtHtmlFetchUtil.info));
		return count;
	}
	
	private int doFetchTxtByC(String url,int index,String classname) {	
		int count = 0;
		for(int i=1;i<=index; i++ ) {
			List<CustomTxt> txtlist = TxtHtmlFetchUtil.fetchTxtInfo("c",url+"list_"+i+".html",classname);
			if(txtlist!=null&&!txtlist.isEmpty()) {
				for(CustomTxt txt: txtlist) {
					customTxtMapper.insertSelective(txt);
				}
				
			}
			count+=txtlist.size();
		}		
		System.out.println(JsonUtil.toJson(TxtHtmlFetchUtil.info));
		return count;
	}
		

	@Override
	public List<CustomTxt> queryTxtChapters(CustomTxtExtension record) {
		return customTxtMapper.queryTitieAll(record);
	}



	@Override
	public void doFetchTxtContentByA() {
		CustomTxtExtension record = new CustomTxtExtension();
		record.setRuleFlag("a");
		record.setIsFetch("N");
		int s = customTxtMapper.countTitieAll(record);
		System.out.println("数量:"+s);
		int length = s/100;
		for (int i=0;i<=length;i++){
			record.setLimitStart(i*100);
			record.setLimitLen(100);
			List<CustomTxt> dataList = customTxtMapper.queryTitieAll(record);
			if(dataList!=null&&!dataList.isEmpty()) {
				for(CustomTxt txtinfo: dataList) {
					System.out.println("处理："+JsonUtil.get().toJson(txtinfo));
					String content = TxtHtmlFetchUtil.fetchTxtContent("a","https://www.1120y.com"+txtinfo.getTxtUrl());
					if(StringUtils.isNotBlank(content)) {
						txtinfo.setMarkFlag(content.replaceAll("<br>", "").replaceAll(" ", "").length()+"");
						txtinfo.setContent(content);
						txtinfo.setRuleFlag("a");
						txtinfo.setFirstFetchTime(new Date());
						txtinfo.setLastFetchTime(new Date());
						txtinfo.setIsFetch("Y");
						customTxtMapper.updateByPrimaryKeySelective(txtinfo);
					}	
				}
			}
		}
		System.out.println("错误:"+TxtHtmlFetchUtil.info);
		
	}



	@Override
	public CustomTxt getTxtById(String flag,Integer txtId) {
		CustomTxt record = new CustomTxt();
		record.setRuleFlag(flag);
		record.setTxtId(txtId);
		return customTxtMapper.selectByPrimaryKey(record);
	}



	@Override
	public int doFetchTxtByB() {
		Map<String, String> infom = new LinkedHashMap<>();
		infom.put("武侠", "http://www.778dd.com/list/wxgd/");
		infom.put("乱伦", "http://www.778dd.com/list/jtll/");
		infom.put("都市", "http://www.778dd.com/list/dsjq/");
		infom.put("另类", "http://www.778dd.com/list/llxs/");
		infom.put("笑话", "http://www.778dd.com/list/qsxh/");
		infom.put("人妻", "http://www.778dd.com/list/rq/");
		infom.put("校园", "http://www.778dd.com/list/xiaoyou/");
		infom.put("技巧", "http://www.778dd.com/list/xaijq/");
		int count = 0;
		for(String key: infom.keySet()) {
			int	index= TxtHtmlFetchUtil.fetchTxtByBpageNum(infom.get(key));
			System.out.println(key+":"+index);
			for(int i=1;i<=index; i++ ) {
				String url = infom.get(key);
				if(i>1) {
					url += "index-"+i+".html";
				}
				List<CustomTxt> txtlist = TxtHtmlFetchUtil.fetchTxtInfo("b",url,key);
				if(txtlist!=null&&!txtlist.isEmpty()) {
					for(CustomTxt txt: txtlist) {
						customTxtMapper.insertSelective(txt);
					}
					
				}
				count+=txtlist.size();
			}		
		}
		System.out.println(JsonUtil.toJson(TxtHtmlFetchUtil.info));
		return count;
	}

	@Override
	public void doFetchTxtContentByB() {
		CustomTxtExtension record = new CustomTxtExtension();
		record.setRuleFlag("b");
		record.setIsFetch("N");
		int s = customTxtMapper.countTitieAll(record);
		System.out.println("数量:"+s);
		int length = s/100;
		for (int i=0;i<=length;i++){
			record.setLimitStart(i*100);
			record.setLimitLen(100);
			List<CustomTxt> dataList = customTxtMapper.queryTitieAll(record);
			if(dataList!=null&&!dataList.isEmpty()) {
				for(CustomTxt txtinfo: dataList) {
					System.out.println("处理："+JsonUtil.get().toJson(txtinfo));
					String content = TxtHtmlFetchUtil.fetchTxtContent("b","http://www.778dd.com"+txtinfo.getTxtUrl());
					if(StringUtils.isNotBlank(content)) {
						System.out.println("超长："+content.length());
						txtinfo.setMarkFlag(content.replaceAll("<br>", "").replaceAll(" ", "").length()+"");
						txtinfo.setContent(content);
						txtinfo.setRuleFlag("b");
						txtinfo.setFirstFetchTime(new Date());
						txtinfo.setLastFetchTime(new Date());
						txtinfo.setIsFetch("Y");
						try {
							customTxtMapper.updateByPrimaryKeySelective(txtinfo);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
						
					}	
				}
			}
		}
	}

	
	public void doFetchTxtContent(String flag,String urlfix,int starIndex) {
		CustomTxtExtension record = new CustomTxtExtension();
		record.setRuleFlag(flag);
		record.setIsFetch("N");
		record.setLimitStart(starIndex);
		record.setLimitLen(30);
		List<CustomTxt> dataList = customTxtMapper.queryTitieAll(record);
		if(dataList!=null&&!dataList.isEmpty()) {
			for(CustomTxt txtinfo: dataList) {
				System.out.println(starIndex+"处理："+JsonUtil.get().toJson(txtinfo));
				String content = TxtHtmlFetchUtil.fetchTxtContent(flag,urlfix+txtinfo.getTxtUrl());
				if(StringUtils.isNotBlank(content)) {
					txtinfo.setMarkFlag(content.replaceAll("<br>", "").replaceAll(" ", "").length()+"");
					txtinfo.setContent(content);
					txtinfo.setRuleFlag(flag);
					txtinfo.setFirstFetchTime(new Date());
					txtinfo.setLastFetchTime(new Date());
					txtinfo.setIsFetch("Y");
					try {
						customTxtMapper.updateByPrimaryKeySelective(txtinfo);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}	
			}
		}
	}
}
