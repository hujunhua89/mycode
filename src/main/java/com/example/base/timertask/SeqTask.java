package com.example.base.timertask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.base.init.InitBaseData;
import com.example.base.modules.seq.mapper.SeqCfgMapper;

/**
 * Created by Administrator on 2017/9/13.
 */
@Component
public class SeqTask {
	private static final Logger log = LoggerFactory.getLogger(SeqTask.class);

    @Autowired
    public SeqCfgMapper seqCfgMapper;
    
    @Autowired
    public InitBaseData initBaseData;
//    @Scheduled(cron="30 0/30 * * * ?")
//    @Scheduled(cron="0 0 7 ? * MON")
	@Scheduled(cron="0 0 1 * * ?")
    public void testPutseq(){
		log.info("每天初始化序列");
    	seqCfgMapper.init();
    	log.info("生成当天序列");
    	initBaseData.initSeq();
    }
}
