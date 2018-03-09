package com.example.modules.tieba.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modules.create.entity.CreateTableEntity;
import com.example.modules.create.mapper.CreateTableMapper;
import com.example.modules.tieba.entity.Tieba;
import com.example.modules.tieba.mapper.TiebaMapper;
import com.example.modules.tieba.mapper.TiebaTieziMapper;
import com.example.modules.tieba.service.IBaiduTiebaService;
import com.example.modules.tieba.util.TiebaInfoUtil;

@Service
public class BaiduTiebaServiceImpl implements IBaiduTiebaService {
	
	@Autowired
	private TiebaMapper tiebaMapper;
	
	@Autowired
	private TiebaTieziMapper tiebaTieziMapper;
	
	@Autowired
	private CreateTableMapper createTableMapper; 
	
	@Override
	public void doFetchTiezi() {
		List<Tieba> tlist =tiebaMapper.queryAllFetchTieba();

	}

	@Override
	public void doFetchTieBa(String tiebaName,boolean isFetch) {
		if(StringUtils.isBlank(tiebaName)) {
			return;
		}
		Tieba tieba = TiebaInfoUtil.fetchTiebaInfo(tiebaName);
		tieba.setIsFetch(isFetch?"Y":"N");
		tiebaMapper.insertSelective(tieba);
		CreateTableEntity table = new CreateTableEntity();
		table.setTableName("tieba_tiezi_"+tieba.gettId());
		table.setTableComment(tieba.getTiebaName());
		createTableMapper.createTiezi(table);
	}
}
