package com.example.base.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.example.base.bean.datasource.DataSourceChange;
import com.example.base.bean.datasource.DynamicDataSource;


/****
 * 根据 注解dataSourceChange注解 做  主从切换
* <p>Title: DataSourceAspect</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2017-3-8
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {
	private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);

    @Around("@annotation(dataSourceChange)")
    public Object doAround(ProceedingJoinPoint pjp, DataSourceChange dataSourceChange) throws Exception {
        Object retVal = null;
        boolean selectedDataSource = false;
        try {
            if (null != dataSourceChange) {
                selectedDataSource = true;
                if (dataSourceChange.slave()) {
                    DynamicDataSource.useSlave();
                } else {
                    DynamicDataSource.useMaster();
                }
            }
            LOGGER.info("切换成功");
            retVal = pjp.proceed();
        } catch (Throwable e) {
            LOGGER.warn("数据源切换错误", e);
            throw new Exception("数据源切换错误", e);
        } finally {
            if (selectedDataSource) {
                DynamicDataSource.reset();
            }
        }
        return retVal;
    }

	@Override
	public int getOrder() {
		return 0;
	}
}
