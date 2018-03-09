package com.example.base.cache;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.base.init.InitBeanFactory;

import redis.clients.jedis.Jedis;

/****
 * 封装缓存管理工具类
* <p>Title: InitBeanFactory.beanTool</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2017-5-9
 */
@Component
public class CacheManage {
	private static final Logger log = LoggerFactory.getLogger(CacheManage.class);
	/***
	 * 缓存key加前缀防止冲突覆盖
	 * @param key
	 * @return String
	 * @author Junhua Hu
	 * @date 2017-5-10
	 */
	private static String parseKey(String key){
		return CacheKeyConstant.CACHE_PREFIX+key;
	}
	private static String[] parseKey(String... keys){
		List<String> strL = new LinkedList<>();
		String[] str = {};
		for(String key:keys){
			strL.add(parseKey(key));
		}
		return strL.toArray(str);
	}
	/***
	 * 获取缓存中数据
	 * @param key 键
	 * @return String
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static String getCache(String key){
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			return jedis.get(parseKey(key));
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return null;
	}
	/***
	 * 添加缓存
	 * @param key
	 * @param value void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void setCache(String key,String value){
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			 jedis.set(parseKey(key), value);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
	/***
	 * 集中添加缓存
	 * @param param void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void set(Map<String, String> param) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			for (String key : param.keySet()) {
				jedis.set(parseKey(key), param.get(key));
			}
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
	
	/***
	 * 添加map类型缓存
	 * @param param void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void addMap(String key,Map<String, String> param) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			jedis.hmset(parseKey(key), param);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
	
	/****
	 * 获取map类型缓存
	 * @param key 缓存键
	 * @param dataKey map键
	 * @return List<String>
	 * @author Junhua Hu
	 * @date 2017-5-10
	 */
	public static String getMapKey(String key,String dataKey) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		String st = null ;
		try {
			st = jedis.hget(parseKey(key), dataKey);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return st;
	}
	/****
	 * 删除map指定key缓存
	 * @param key 缓存键
	 * @param dataKey map键
	 * @return List<String>
	 * @author Junhua Hu
	 * @date 2017-5-10
	 */
	public static void delMapKey(String key,String dataKey) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			 jedis.hdel(parseKey(key), dataKey);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
	/****
	 * 获取map类型缓存
	 * @param key 缓存键
	 * @param dataKey map键
	 * @return List<String>
	 * @author Junhua Hu
	 * @date 2017-5-10
	 */
	public static List<String> getMapKey(String key,String... dataKey) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		List<String> data = null ;
		try {
			data = jedis.hmget(parseKey(key), dataKey);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return data;
	}
	/****
	 * 获取map类型缓存
	 * @param key 缓存键
	 * @param dataKey map键
	 * @return List<String>
	 * @author Junhua Hu
	 * @date 2017-5-10
	 */
	public static Map<String, String> getMap(String key) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		Map<String, String> data = null ;
		try {
			data = jedis.hgetAll(parseKey(key));
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return data;
	}
	
	/***
	 * 移除map缓存
	 * @param param void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void removeMap(String key) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
	/***
	 * 往set集合添加数据
	 * @param param void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void addSet(String key,String value) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			 jedis.sadd(parseKey(key), value);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
	/***
	 * 获取往set集合数据
	 * @param param void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static Set<String> getSet(String key) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		Set<String> dataSet = null;
		try {
			dataSet = jedis.smembers(parseKey(key));
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return dataSet;
	}
	
	/***
	 * 判断set中是否存在该数据
	 * @param param void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static boolean existsSet(String key,String value) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			return jedis.sismember(parseKey(key),value);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return false;
	}
	/***
	 * 移除set指定数据
	 * @param param void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void removeSet(String key,String value) {
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			 jedis.srem(parseKey(key), value);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
	/****
	 * 添加缓存多少秒后过期
	 * @param key
	 * @param value
	 * @param sec void 秒
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void setCache(String key,String value,int sec){
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			 jedis.setex(parseKey(key),sec, value);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
	
	/****
	 * 往缓存队列添加数据
	 * @param key
	 * @param value void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void pushQueue(String key,String... value){
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			 jedis.rpush(parseKey(key), value);//从队列右边push
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}

	
	/****
	 * 往缓存队列取数据-并销除(先进先出)
	 * @param key
	 * @param value void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public synchronized static List<String> popQueueTrim(String key){
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		List<String> dataList = null;
		key = parseKey(key);
		try {
			if(jedis.exists(key)){
				long len = jedis.llen(key);
				if(len>0){
					dataList = jedis.lrange(key, 0, len);//从左边取
					jedis.ltrim(key, len, -1);//清除己取
				}
			}		
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return dataList;
	}
	
	/****
	 * 往缓存队列取数据-并销除(先进先出)
	 * @param key
	 * @param value void
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static String popQueueOneTrim(String key){
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		String data = null;
		key = parseKey(key);
		try {
			if(jedis.exists(key)){
				long len = jedis.llen(key);
				if(len>0){
					data = jedis.lpop(key);//从左边取
					jedis.ltrim(key, 0, 1);//清除己取
				}
			}		
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return data;
	}
	
	/***
	 * 缓存是否存在
	 * @param key
	 * @return boolean
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static boolean exists(String key){
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			return jedis.exists(key);
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
		return false;
	}
	
	/***
	 * 清除指定缓存
	 * @param key
	 * @return boolean
	 * @author Junhua Hu
	 * @date 2017-5-9
	 */
	public static void del(String... key){
		Jedis jedis =InitBeanFactory.beanTool.jedisPool.getResource();
		try {
			jedis.del(parseKey(key));
		} catch (Exception e) {
			log.info("缓存异常:{}",e.getMessage());
		} finally{
			jedis.close();
		}
	}
}
