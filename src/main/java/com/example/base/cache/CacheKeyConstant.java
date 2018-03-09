package com.example.base.cache;

/***
 * 缓存常量配置
* <p>Title: CacheConstant</p>
* <p>Description: </p>
* <p>Company: infinite </p>
* @author Junhua Hu
* @date 2017-5-10
 */
public class CacheKeyConstant {
	
	/***
	 * 缓存前缀
	 */
	public static final String CACHE_PREFIX = "hcs_";
	
	/***
	 * 单条公共配置信息 key由 CacheKey+配置key 值为 配置值字符串
	 */
	public static final String SYS_PARAMETER_KEY = "sys_parameter_key_";
	
	
	/***
	 * 单条企业配置信息 key由 CacheKey+企业id+"_"+配置key 值为 配置值字符串
	 */
	public static final String COM_PARAMETER_KEY = "com_parameter_key_";
	
	/***
	 * 企业域名  key由 CacheKey+企业域名  值为企业id的json字符串
	 */
	public static final String COM_HOST ="com_host_";
	
	/***
	 * 企业域名  key由 CacheKey+企业id  值为企业域名的json字符串
	 */
	public static final String COM_HOST_ID = "com_host_id_";
	
	/***
	 * 登录错误次数  key由 CacheKey+（session id or 登录账号）  值为次数
	 */
	public static final String LOGIN_ERR_COUNT = "login_err_count_";
	
	/***
	 * 企业TSR端域名 key由 CacheKey  值为<K,V><企业域名,企业id>的Map对象
	 */
	public static final String COM_TSR_HOST = "com_tsr_host_";
	
	/***
	 * 企业TSR端域名   key由 CacheKey  值为<K,V><企业id,企业域名>的Map对象
	 */
	public static final String COM_TSR_HOST_ID = "com_tsr_host_id_";
	
	
	/***
	 * 验证码KEY+ sessionID
	 */
	public static final String AUTHCODE = "csa_authcode_";
	
	/***
	 * 微信_accessToken key
	 */
	public static final String WEIXIN_ACCESS_TOKEN = "wechat_accessToken";
	
	/***
	 * 微信_jsapitichet key
	 */
	public static String WEIXIN_JSAPI_TICHET = "wechat_JsapiTicket";


	/**
	 * tsr 端缓存产品内容
	 */
	public static final String TSR_PRODUCT_CONTENT = "tsr_product_content_";


	/***
	 * 投票ip缓存记录
	 */
	public static final String COMMIT_POLL_IP = "commit_poll_ip_";

	public static final String T_TEMPPLANID="T_TEMPPLANID_";

	public static final String T_ACTIVID="T_ACTIVID_";

	public static final String T_IDEAID="T_IDEAID_";
	
	//客服变更通知
	public static final String CUST_MODIFY ="cust_modify_";
	
	/***
	 * 平安_accessToken key
	 */
	public static final String PINGAN_ACCESS_TOKEN = "pingan_accessToken";
	
	
	/***
	 * 微信模板消息队列
	 */
	public static final String WEIXIN_TEMPLATE_MSG = "weixin_template_msg";
	
	/***
	 * 微信模板id与数据库配置
	 */
	public static final String WEIXIN_TEMPLATE_CFG = "WX_TEMP_CFG_";
	
	
	/***
	 * 活动attr字段配置
	 */
	public static final String ACTIV_ATTR_CFG = "acitv_attr_cfg_";
	
	
	/***
	 * 短信验证码前缀
	 */
	public static final String SMS_AUTHCODE = "sms_authcode_";
	
	/***
	 * 用户标签配置前缀
	 */
	public static final String USER_TAG_CFG = "user_tag_cfg_";
	
	/***
	 * 用户标签配置前缀
	 */
	public static final String USER_TAG_CFG_MAP = "user_tag_cfg_map";


	/**
	 * 企业积分规则配置前缀
	 */
	public static final String COM_SCORE_RULE = "com_score_rule_";

	/**
	 * 坐席积分规则项缓存前缀
	 */
	public static final String CUST_SCORE_RULE = "cust_score_rule_";

	/**
	 * 积分间隔
	 */
	public static final String CUST_SCORE_TIMEINTERVAL = "cust_score_timeinterval_";
	
	/***
	 * 用户行为配置前缀
	 */
	public static final String USER_ACTIV_CFG = "user_activ_cfg_";

	/**
	 * 客服获取icid用户标签
	 */
	public static final String CUST_QUERY_ICID_TAGS = "cust_query_icid_tags_";
	
	/**
	 * 客服获取icid用户标签
	 */
	public static final String REGION_ID = "region_id_";

	/*
	任务每天添加3个客户前缀
	 */
	public static final String ADD_CLIENT = "add_client_";
	//不允许new
	private CacheKeyConstant(){
		
	}
}
