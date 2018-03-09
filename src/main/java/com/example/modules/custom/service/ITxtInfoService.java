package com.example.modules.custom.service;

import java.util.List;

import com.example.modules.custom.entity.CustomTxt;
import com.example.modules.custom.entity.extension.CustomTxtExtension;

public interface ITxtInfoService {

	
	int doFetchTxt(String flag,int index,String url,String classname);
	
	int doFetchTxtByA(String url,int index);
	
	void doFetchTxtContentByA();
	
	List<CustomTxt> queryTxtChapters(CustomTxtExtension record);
	
	CustomTxt getTxtById(String flag,Integer txtId);
	
	int doFetchTxtByB();
	
	void doFetchTxtContentByB();
	
	void doFetchTxtContent(String flag,String urlfix,int starIndex);
	
}
