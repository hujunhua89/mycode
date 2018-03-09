package com.example.base.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;

/****
 * 初始化 需要注入的bean供静态调用
* <p>Title: InitBeanTool</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2017-6-14
 */
@Component
public class InitBeanFactory {
    public static InitBeanFactory beanTool;
    
    @Autowired
    public JedisPool jedisPool;
    
    @Autowired
    public InitBaseData initBaseData;
    
    @Autowired
	public ThreadPoolTaskExecutor threadPool;
    
    
	@PostConstruct
	public void init() {
		beanTool = this;
		beanTool.jedisPool = this.jedisPool;
		beanTool.threadPool = this.threadPool;
		initBaseData.initSeq();
	
	}
}
