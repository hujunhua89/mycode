package com.example.base.aspect;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.base.util.JsonUtil;
/**
 * @ClassName LogAspect
 * @Description TODO
 * @date 2016年4月14日 
 * @author Jinyi Xian
 */  


@Aspect 
@Component
public class LogAspect {  
	
	private static final Logger logger = LoggerFactory.getLogger("aspectLog");
	
    /** 
     * 添加业务逻辑方法切入点 
     */  
    @Pointcut("execution(* com.example.modules.*.service.*.*(..))")  
    public void serviceCall() { }  
    
    @Pointcut("execution(* com.example.modules.*.controller.*.*(..))")  
    public void controllerCall() { }  
      
    /** 
     * 管理员添加操作日志 
     * @param joinPoint 
     * @throws Throwable 
     */  
    @Around("serviceCall()")  
    public Object  serviceCallCalls(ProceedingJoinPoint joinPoint) throws Throwable{  
          
        //执行前输出信息  
    	StringBuffer message= new StringBuffer();
    	Date startDate = new Date();
        String methodName = joinPoint.getTarget().getClass().getSimpleName()+"."+joinPoint.getSignature().getName();  //获取方法名   

        message.append("调用服务：").append(methodName).append("  参数：").append(JsonUtil.toJson(joinPoint.getArgs()));
        //记录
        Object proceed = joinPoint.proceed();
        message.append("  返回值:").append(JsonUtil.toJson(proceed));
        Date endDate = new Date();
        long temp = endDate.getTime() - startDate.getTime();    //相差毫秒数
        //相差秒数
        message.append("  耗时:").append(temp);
        logger.info(message.toString());
        return proceed;
    }  
    
    /** 
     * 计算controller执行时间
     */  
    @Around("controllerCall()")  
    public Object  controllerCallCalls(ProceedingJoinPoint joinPoint) throws Throwable{  
    	String classSimpleName = joinPoint.getTarget().getClass().getSimpleName();
    	String methodName = joinPoint.getSignature().getName();  //获取方法名
        //记录
        Date startDate = new Date();
        Object proceed = joinPoint.proceed();
        Date endDate = new Date();
        long temp = endDate.getTime() - startDate.getTime();    //相差毫秒数
  //    long second = temp /1000;                    			//相差秒数
        logger.info("类【"+classSimpleName+"】的"+methodName+"方法共执行了"+temp+"毫秒");
        if(temp>1000){
        	// 记录该方法，以备优化
        }
        return proceed;
    }  
    
    
    
    
    
    
    public String adminOptionContent(Object[] args, String mName) throws Exception{  
        if (args == null) {  
            return null;  
        }  
        StringBuffer rs = new StringBuffer();  
        for (Object info : args) {  // 遍历参数对象  
            String entityInfo = getEntityInfo(info); 
            rs.append(entityInfo);
        }  
        return rs.toString();  
    }


	/**
	 * @param info
	 */
	private String getEntityInfo( Object info) {
		if(info==null){
			return "";
		}
		String className=null;
		StringBuffer rs = new StringBuffer();  
		//获取对象类型  
		className = info.getClass().getName();  
		className = className.substring(className.lastIndexOf(".") + 1);  
		rs.append("\r\n");
		rs.append("     类型：" + className + "，值："+JsonUtil.toJson(info));  
		return rs.toString();
	}  
      
}  