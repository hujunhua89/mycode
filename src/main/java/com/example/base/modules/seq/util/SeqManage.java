package com.example.base.modules.seq.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SeqManage {
	private static Map<String, ConcurrentLinkedQueue<String>> queueMap = Collections.synchronizedMap(new HashMap<>());

	public static Map<String,String> seqMap = Collections.synchronizedMap(new HashMap<>());
	public static List<String> seqinfo = Collections.synchronizedList(new ArrayList<>());
	public static List<String> seqsecinfo = Collections.synchronizedList(new ArrayList<>());

	public static String getSeqVal(String seqName) {
		String val = null;
		if(queueMap.containsKey(seqName)) {
			try {
				val = queueMap.get(seqName).poll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return val;
	}
	
	public static void putSeq(String seqName,List<String> valList) {
		if(queueMap.containsKey(seqName)) {
			queueMap.get(seqName).addAll(valList);
		}else {
			ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
			queue.addAll(valList);
			queueMap.put(seqName, queue);
		}
	}
	
}
