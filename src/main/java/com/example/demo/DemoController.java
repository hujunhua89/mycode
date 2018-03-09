package com.example.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.base.init.InitBeanFactory;
import com.example.base.modules.seq.thread.TestSeqMainThread;
import com.example.modules.create.entity.CreateTableEntity;
import com.example.modules.create.mapper.CreateTableMapper;
import com.example.modules.custom.service.ITxtInfoService;
import com.example.modules.tieba.entity.Tieba;
import com.example.modules.tieba.mapper.TiebaMapper;
import com.example.modules.tieba.util.TiebaInfoUtil;
import com.google.gson.Gson;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

@Controller
public class DemoController {
	private static final Logger log = LoggerFactory.getLogger(DemoController.class);
	
	
	@Autowired
	private JedisConnectionFactory redisConnectionFactory;
	
	@Autowired
	private CreateTableMapper createTableMapper;
	
	@Autowired
	private ITxtInfoService txtInfoService;
	
	@Autowired
	private TiebaMapper tiebaMapper;
	
	@RequestMapping("/")
	public String index(HttpServletRequest req) {
//		Gson gson = new Gson();
//		UserAgent userAgent = UserAgent.parseUserAgentString("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; Tablet PC 2.0)");
//		Browser browser = userAgent.getBrowser();
//		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
//		Version version = userAgent.getBrowserVersion();
//		System.out.println(req.getHeader("User-Agent"));
//		//System.out.println(gson.toJson(userAgent));
//		System.out.println("----------浏览器---------");
//		System.out.println(browser.getName());
//		System.out.println(browser.getBrowserType());
//		System.out.println(browser.getManufacturer());
//		System.out.println(browser.getRenderingEngine());
//		System.out.println("----------版本---------");
//		System.out.println(version.getMajorVersion());
//		System.out.println(version.getMinorVersion());
//		System.out.println(version.getVersion());
//		System.out.println("----------系统---------");
//		System.out.println(operatingSystem.getName());
//		System.out.println(operatingSystem.getDeviceType());
//		System.out.println(operatingSystem.getManufacturer());
//		System.out.println(operatingSystem.getGroup().getName());
		return "index";
	}	
	
	@RequestMapping("/video")
	public String video(HttpServletRequest req) {

		return "video";
	}
	

	@RequestMapping("/demo")
	@ResponseBody
	public String testdemo(HttpServletRequest req) {
		String sid = req.getSession().getId();
		System.out.println("sid:"+ req.getSession().getAttribute("name"));
		return sid;
	}

	@RequestMapping("/create")
	@ResponseBody
	public String testredis(String tiebaName) {
		if(tiebaName==null) {
			return "false";
		}
		Tieba tieba = TiebaInfoUtil.fetchTiebaInfo(tiebaName);
		tieba.setIsFetch("Y");
		tiebaMapper.insertSelective(tieba);
		CreateTableEntity table = new CreateTableEntity();
		table.setTableName("tieba_tiezi_"+tieba.gettId());
		table.setTableComment(tieba.getTiebaName());
		int str = createTableMapper.createTiezi(table);
		return "hello world:" + str;
	}
	
	@RequestMapping("/createtxt")
	@ResponseBody
	public String createtxt() {
		CreateTableEntity table = new CreateTableEntity();
		table.setTableName("custom_txt_b");
		table.setTableComment("小说-s天堂");
		int str = createTableMapper.createCustomTxt(table);
		return "hello world:" + str;
	}
	
	@RequestMapping("/getseq")
	@ResponseBody
	public String getseq(HttpServletRequest req) throws Exception{
		log.info("线程数1：{}",InitBeanFactory.beanTool.threadPool.getActiveCount());
		for(int i=0;i<50;i++) {
			InitBeanFactory.beanTool.threadPool.execute(new TestSeqMainThread(i));
		}
		log.info("线程数2：{}",InitBeanFactory.beanTool.threadPool.getActiveCount());
		return "ok";
	}

	
	public void feibolaqi() {
		int a = 1, b = 1, c;
		// 1235813
		for (int i = 1; i < 200; i++) {
			c = b;
			b = a + b;
			a = c;
			System.out.println(c);
		}
	}

	public void zhishu() {
		for (int i = 2; i < 200; i++) {
			boolean is = true;
			for (int j = 2; j < i; j++) {
				if (i % j == 0) {
					is = false;
				}
			}
			if (is) {
				System.out.println(i);
			}
		}
	}

	public void shuixianhua() {
		for (int i = 100; i <= 999; i++) {
			int g = i / 100;
			int s = (i - 100 * g) / 10;
			int b = (i - 100 * g - 10 * s);
			if (g * g * g + s * s * s + b * b * b == i) {
				System.out.println(i);
			}
		}
	}
	
	public void zhiyingsu() {
		int k = 2;
		int num = 20000;
		System.out.print(num + "=");
		while (num > k) {
			if (num % k == 0) {
				System.out.print(k + "*");
				num = num / k;
			} else {
				k++;
			}
		}
		System.out.println(k);
	}

	
	
	public void gonyunshu() {
		int a = 99;
		int b = 45;
		int first = a;
		int second = b;
		System.out.println("a=" + a + ";b=" + b);
		int temp;

		if (a < b) {
			temp = a;
			a = b;
			b = temp;
		}
		while (b != 0) {
			temp = a % b;
			a = b;
			b = temp;
		}
		System.out.println("最大公约数为" + a);
		System.out.println("最小公倍数为" + first * second / a);
	}
	
	public static void main(String[] args) {
		Gson gson = new Gson();
		UserAgent userAgent = UserAgent.parseUserAgentString("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.2; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; Tablet PC 2.0)");
		Browser browser = userAgent.getBrowser();
		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
		Version version = userAgent.getBrowserVersion();
		//System.out.println(gson.toJson(userAgent));
		System.out.println("----------浏览器---------");
		System.out.println(browser.getName());
		System.out.println(browser.getBrowserType());
		System.out.println(browser.getManufacturer());
		System.out.println(browser.getRenderingEngine());
		System.out.println("----------版本---------");
		System.out.println(version.getMajorVersion());
		System.out.println(version.getMinorVersion());
		System.out.println(version.getVersion());
		System.out.println("----------系统---------");
		System.out.println(operatingSystem.getName());
		System.out.println(operatingSystem.getDeviceType());
		System.out.println(operatingSystem.getManufacturer());
		System.out.println(operatingSystem.getGroup().getName());
		
	}
	
}
