package com.example.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/****
 * 异步任务线程池配置
* <p>Title: ThreadPoolConfiguration</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2017-2-27
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ThreadPoolConfiguration {
	@Bean
	@Primary
	public ThreadPoolTaskExecutor ThreadPoolFactory() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(100);
		taskExecutor.setMaxPoolSize(150);
		taskExecutor.setKeepAliveSeconds(30);
		taskExecutor.setBeanName("executor");
		return taskExecutor;
	}
	
	@Bean
	@Primary
	public ThreadPoolTaskScheduler TaskSchedulerPoolFactory(){
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();		
		taskScheduler.setPoolSize(100);
		taskScheduler.setBeanName("scheduler");
		return taskScheduler;
	}
}
