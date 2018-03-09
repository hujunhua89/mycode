package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.example.base.util.HttpSSLUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/***
 * IC数据处理
 * @ClassName:IcUserDataProcess
 * @Description:(描述)
 * @author:hujunhua
 * @date:2017年12月21日 下午4:14:45
 *
 */
public class IcUserDataProcessUtil {
	//IC数据限制
	private static Map<String, String> ICDataMap = new HashMap<>();
	
	//本地数据限制
	private static Map<String, String> OurDataMap = new HashMap<>();
	
	private static Map<String, String> transitionMap =  new HashMap<>();
	
	public static Map<String, String> icdatanumMap =  new HashMap<>();
	
	static {
		icdatanumMap.put("name", "A");
		icdatanumMap.put("idcard", "B");
		icdatanumMap.put("sex", "C");
		icdatanumMap.put("birthday", "D");
		icdatanumMap.put("hadhouse", "E");
		icdatanumMap.put("hadcar", "F");
		icdatanumMap.put("cityid", "G");
		icdatanumMap.put("provinceid", "H");
		icdatanumMap.put("children", "I");
		icdatanumMap.put("moincome", "J");
		icdatanumMap.put("isbuyinsurance", "K");
		
		//本地标签定义
		OurDataMap.put("all_city", "cityid");
		OurDataMap.put("all_province", "provinceid");
		//OurDataMap.put("name", "username");
		OurDataMap.put("yeskid", "children");
		//OurDataMap.put("hadhouse", "property");
		OurDataMap.put("isbuyinsurance", "insurance");
		OurDataMap.put("insurance", "insurancepro");
		OurDataMap.put("annualincome", "income");
		OurDataMap.put("income", "moincome");
		OurDataMap.put("id", "icactivid");
		
		//个人情况	
		//ICDataMap.put("age", "注册年龄");
		ICDataMap.put("all_city", "城市");
		ICDataMap.put("all_province", "省份");
		ICDataMap.put("available", "是否有效手机号");
		ICDataMap.put("birthday", "生日");
		//ICDataMap.put("career", "注册年龄");
		ICDataMap.put("hadcar", "是否有车");
		ICDataMap.put("createtime", "注册时间");
		ICDataMap.put("credict", "信用卡额度");
		//ICDataMap.put("email", "邮箱");
		ICDataMap.put("idcard", "身份证号");
		ICDataMap.put("income", "月收入");
		ICDataMap.put("industry", "所属行业");
		//ICDataMap.put("mobile", "电话");
		ICDataMap.put("life_stages", "人生阶段");
		ICDataMap.put("name", "姓名");
		ICDataMap.put("sex", "性别");
		ICDataMap.put("workinglife", "工作年限");
		ICDataMap.put("hadhouse", "是否有房");
		ICDataMap.put("yeskid", "是否有小孩");
		ICDataMap.put("annualincome", "年收入（单位：万）");
		ICDataMap.put("terminal_type", "终端类型");
		
		//家庭关注度	
		ICDataMap.put("childBirthday", "孩子生日");
		ICDataMap.put("familyBirthday", "家人生日");
		ICDataMap.put("familyTrial", "为家人试算");

		//保险关注度	
		ICDataMap.put("amount", "保额（单位：万）");
		ICDataMap.put("calTimes", "试算次数");
		ICDataMap.put("insurance", "赠险");
		ICDataMap.put("insurancePeriod", "保障期限");
		ICDataMap.put("applicantAge", "投保人年龄");
		ICDataMap.put("appointment", "预约咨询");
		ICDataMap.put("bookProduct", "预约产品");
		ICDataMap.put("paymentway", "缴费方式");	
		ICDataMap.put("interest", "兴趣产品");
		ICDataMap.put("isbuyinsurance", "是否购买保险");
		ICDataMap.put("isinsterest", "是否对保障又返还保险感兴趣");
		ICDataMap.put("policy", "是否有保单");
		ICDataMap.put("trial", "是否试算");
		
		ICDataMap.put("payment", "缴费");
		ICDataMap.put("paymentperiod", "缴费年限");
		ICDataMap.put("premium", "保费");
		ICDataMap.put("trialResult", "试算结果");
		ICDataMap.put("voice_durationTime", "通话持续时间（秒）");
		
		//贷款、教育关注度	
		ICDataMap.put("corpid", "企业id");
		ICDataMap.put("educationcnt", "注册教育培训类活动次数");	
		ICDataMap.put("loan", "需贷额度");
		ICDataMap.put("level", "卡等级");
		ICDataMap.put("moneyType", "贷款类型");
		//ICDataMap.put("stoptime", "停留时长");
		ICDataMap.put("id", "活动唯一id");
		ICDataMap.put("src", "模板URL");
		
		
	
		
		
		
		ICDataMap.put("question1", "问题1");
		ICDataMap.put("question2", "问题2");
		ICDataMap.put("question3", "问题3");
		
		transitionMap.put("birthday", "BDAY");
		transitionMap.put("sex", "SEX");
		transitionMap.put("available", "ABLE");
		transitionMap.put("email", "NO");
		transitionMap.put("mobile", "NO");
		transitionMap.put("createtime", "NO");
		transitionMap.put("age", "NO");
		transitionMap.put("stoptime", "NO");
		transitionMap.put("calTimes", "ZERO");
		transitionMap.put("stoptime", "ZERO");
		//transitionMap.put("hadhouse", "YESTOZERO");
		//transitionMap.put("hadcar", "YESTOONE");
		transitionMap.put("isbuyinsurance", "YESTOONE");
		transitionMap.put("yeskid", "YESORON");
	}
	
	
	private static List<Map<String, String>> getICData(String mobile){
		Map<String, Object> param = new HashMap<>();
		Gson gson = new Gson();
		String url = "http://14.17.102.61:8100/query_certify_event";
		param.put("corp", "campaign");
		param.put("source", "register");
		param.put("certify", "mobile");
		List<String> mobileList = new ArrayList<>();
		mobileList.add(mobile);
		param.put("data", mobileList);
		String res = HttpSSLUtil.postJson(url, gson.toJson(param));
		System.out.println(res);
		List<Map<String, String>> icInfoList = new ArrayList<>();
		if(StringUtils.isNotBlank(res)) {
			JsonObject obj = gson.fromJson(res, JsonObject.class);
			JsonArray arrObj = obj.getAsJsonArray("data");
			if(arrObj!=null&&arrObj.size()>0) {
				JsonObject evetobj = arrObj.get(0).getAsJsonObject();
				JsonArray arrevetObj =evetobj.getAsJsonArray("event");
				for(int i=0;i<arrevetObj.size();i++) {
					JsonObject infoobj = arrevetObj.get(i).getAsJsonObject();
					Map<String, String> uinfo = gson.fromJson(infoobj,
							new TypeToken<Map<String, String>>() {
							}.getType());
					System.out.println(uinfo);
					icInfoList.add(uinfo);
				}	
			}
		}
		return icInfoList;
	}
	
	public static List<Map<String, String>> getIcDataToOur(String mobile){
		List<Map<String, String>> icInfoList =getICData(mobile);
		List<Map<String, String>> ourInfoList =null;
		if(!icInfoList.isEmpty()) {
			ourInfoList = new ArrayList<>();
			Map<String, String> ourinfo = null;
			for(Map<String, String> icinfo : icInfoList) {
				ourinfo = new HashMap<String, String>();
				for(String key : icinfo.keySet()) {
					if(StringUtils.isBlank(icinfo.get(key))||"\\N".equals(icinfo.get(key))
							||!ICDataMap.containsKey(key)) {
						continue;
					}
					String val = transDataToOur(key, icinfo.get(key));
					if(StringUtils.isBlank(val)) {
						continue;
					}
					String newkey = OurDataMap.get(key)==null?key:OurDataMap.get(key);
					ourinfo.put(newkey, val);
				}
				//完成了调查问卷
				if(ourinfo.containsKey("question1")&&ourinfo.containsKey("question2")
						&&ourinfo.containsKey("question3")) {
					ourinfo.put("hasquestionnaire", "1");
				}
				//完成测保
				if(ourinfo.containsKey("amount")&&ourinfo.containsKey("premium")) {
					ourinfo.put("hasquestionnaire", "1");
				}
				//完成测保
				if(ourinfo.containsKey("src")) {
					if(ourinfo.get("src").startsWith("edm")) {
						ourinfo.put("dasource", "1");
					}else {
						ourinfo.put("dasource", "0");
					}
				}
				
				ourInfoList.add(ourinfo);
			}	
		}
		return ourInfoList;
	}
	
	private static String transDataToOur(String key,String value) {
		String transData = transitionMap.get(key);
		if(transData!=null) {
			switch (transData) {
			case "BDAY":
				value = value.substring(0, 4) + "-" + value.substring(4, 6) + "-"
						+ value.substring(6, 8);
				break;
			case "SEX":
				value ="男".equals(value)?"1":"女".equals(value)?"0":null;
				break;
			case "NO":
				value = null;
				break;
			case "ZERO":
				if("0".equals(value)) {
					value = null;
				}
				break;
			case "ABLE":
				value ="有效".equals(value)?"1":"无效".equals(value)?"0":null;
				break;
			case "YESTOZERO":
				value ="有".equals(value)?"0":null;
				break;
			case "YESTOONE":
				value ="有".equals(value)?"1":null;
				break;
			case "YESORON":
				value ="有".equals(value)?"1":"无".equals(value)?"0":null;
				break;
			default:
				break;
			}
		}	
		return value;
	}
	
	
	public static void main(String[] args) {
		//System.out.println(getIcDataToOur("13424093400"));//13711646852
		List<Map<String, String>> icInfoList =getIcDataToOur("13711646852");
		if(icInfoList!=null&&icInfoList.isEmpty()) {
			for(Map<String, String> icinfo : icInfoList) {
				System.out.println("---------分隔线-----------");
				for(String key : icinfo.keySet()) {
					System.out.println(ICDataMap.get(key)+"("+key+"):"+icinfo.get(key));
				}
				
			}
		}
		
		//toOurDataMap();
		


	}
	
}
