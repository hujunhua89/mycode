package com.example.base.init;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.base.modules.seq.entity.SeqCfg;
import com.example.base.modules.seq.mapper.SeqCfgMapper;
import com.example.base.modules.seq.util.SeqManage;

/***
 * 初始化数据
* <p>Title: InitBaseData</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2017-6-19
 */
@Component
public class InitBaseData {

private static final Logger log = LoggerFactory.getLogger(InitBaseData.class);
	
	@Autowired
	private SeqCfgMapper seqCfgMapper;

	public void initSeq() {
		log.info("生成序列");
		seqCfgMapper.init();
		List<SeqCfg> seqlist =seqCfgMapper.queryAllUse();
		for(SeqCfg seq:seqlist) {
			List<String> datalist = new ArrayList<>();
			BigDecimal b = new BigDecimal(seq.getCurrentVal());
			for(int i=1;i<=seq.getStockVal();i++) {
				b =  b.add(new BigDecimal(seq.getIncrementVal()));
				datalist.add(b.toString());
			}
			log.info("生成序列:{},长度:{}",seq.getSeqName(),datalist.size());
			SeqManage.putSeq(seq.getSeqName(), datalist);
			seq.setCurrentVal(b.toString());
			seqCfgMapper.updateByPrimaryKeySelective(seq);
		}
		log.info("生成序列完成");
	}
}
