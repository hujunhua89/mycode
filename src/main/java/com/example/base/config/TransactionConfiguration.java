package com.example.base.config;

import java.util.Properties;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.example.base.bean.datasource.DynamicDataSource;



/****
 * 事务配置类
* <p>Title: TransactiConfiguration</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2017-4-21
 */
@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(DataSourceConfiguration.class)
public class TransactionConfiguration {
	/** 
     * 配置事务管理器 
     */  
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource")DynamicDataSource ds) throws Exception { 
        return new DataSourceTransactionManager(ds);  
    }  
    
    @Bean(name="transactionInterceptor")
	public TransactionInterceptor transactionInterceptor(@Qualifier("transactionManager")DataSourceTransactionManager transactionManager) {
		//log.info("TransactionInterceptor 初始化");
		TransactionInterceptor ti = new TransactionInterceptor();
		ti.setTransactionManager(transactionManager);
		Properties properties = new Properties();
		properties.setProperty("*", "readOnly");
	    properties.setProperty("do*", "PROPAGATION_REQUIRED");    
	    properties.setProperty("save*", "PROPAGATION_REQUIRED");
		properties.setProperty("add*", "PROPAGATION_REQUIRED");
		properties.setProperty("remove*", "PROPAGATION_REQUIRED");
		properties.setProperty("modify*", "PROPAGATION_REQUIRED");
		properties.setProperty("update*", "PROPAGATION_REQUIRED");
		properties.setProperty("del*", "PROPAGATION_REQUIRED");
		ti.setTransactionAttributes(properties);
		return ti;
	}

	@Bean
	public BeanNameAutoProxyCreator transactionAutoProxy() {
		BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
		// 使用jdk接口代理，不使用cglib代理
		transactionAutoProxy.setProxyTargetClass(true);
		transactionAutoProxy.setBeanNames("*ServiceImpl");
		transactionAutoProxy.setInterceptorNames("transactionInterceptor");
		return transactionAutoProxy;
	}
}
