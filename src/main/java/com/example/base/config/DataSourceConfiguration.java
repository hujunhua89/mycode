package com.example.base.config;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.example.base.bean.datasource.DynamicDataSource;

/***
 * 数据源主从配置
 * <p>
 * Title: DataSourceConfiguration
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: infinite
 * </p>
 * 
 * @author Junhua Hu
 * @date 2017-4-20
 */
@Configuration
@MapperScan(basePackages = {"com.example.modules.*.mapper","com.example.base.modules.*.mapper"})
public class DataSourceConfiguration {
	
	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;
	
	@Value("${datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "datasource.master")
	@Primary
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "datasource.slave")
	public DataSource slaveDataSource() {
		return DataSourceBuilder.create().type(dataSourceType).build();
	}
	
	 /** 
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例） 
     */  
    @Bean(name="dynamicDataSource")
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource,  
            @Qualifier("slaveDataSource") DataSource slaveDataSource) {  
    	DynamicDataSource proxy = new DynamicDataSource();
		proxy.setMaster(masterDataSource);
		List<DataSource> slaveDataSourceList = new LinkedList<DataSource>();
		slaveDataSourceList.add(slaveDataSource);
		proxy.setSlaves(slaveDataSourceList);
	    return proxy;
    }
    /** 
     * 根据数据源创建SqlSessionFactory 
     */  
    @Bean(name="sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource")DynamicDataSource ds) throws Exception { 
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean(); 
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        fb.setMapperLocations(resolver
                .getResources(mapperLocations));
        fb.setDataSource(ds);// 指定数据源(这个必须有，否则报错)
        return fb.getObject();  
    }
    
    /** 
     * 根据数据源创建SqlSessionFactory 
     */  
    @Bean(name="sqlSessionTemplate ")
    @Primary
    public SqlSessionTemplate  sqlSessionTemplate(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory) { 
    	 return new SqlSessionTemplate(sqlSessionFactory);
    }
        
}
