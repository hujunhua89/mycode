package com.example.base.modules.seq.thread;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.base.modules.seq.util.SeqManage;
import com.example.demo.DemoController;

/***
 * 短信任务主线程
 * 
 * @ClassName:SendTaskMainThread
 * @Description:(描述)
 * @author:hujunhua
 * @date:2017年12月14日 下午3:11:00
 *
 */
public class TestSeqMainThread implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(TestSeqMainThread.class);

	private Integer taskId;

	public TestSeqMainThread(Integer taskId) {
		this.taskId = taskId;
	}

	@Override
	public void run() {
		try {
			String s = "0";
			int i =0;
			while (true) {
				s = SeqManage.getSeqVal("test_seq");
				if(StringUtils.isBlank(s)) {
					break;
				}
				if(SeqManage.seqMap.containsKey(s)) {
					log.info("{}:重复信息:{}",taskId,s);
					SeqManage.seqinfo.add(taskId+":"+s);
				}
				SeqManage.seqMap.put(s, "");
				i++;
				Thread.sleep(200);
			}
			log.info("{}号线程完成数{};重复信息:{}",taskId,i,SeqManage.seqinfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
