package com.example.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/****
 * springMVC配置
 * <p>
 * Title: InterceptorConfiguration
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: infinite
 * </p>
 * 
 * @author Junhua Hu
 * @date 2017-2-16
 */
@Configuration
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/test2").setViewName("test2");
		registry.addViewController("/test3").setViewName("test3");
	}

	/***
	 * 跨域配置
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").
		allowCredentials(true).allowedMethods("GET", "POST", "OPTIONS");
	}
	
	

}
