package com.example.base.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class HttpSSLUtil {

	public static final String get(final String url, final Map<String, String> params) {
		long s = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		if (null != params && !params.isEmpty()) {
			int i = 0;
			for (String key : params.keySet()) {
				if (i == 0) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(key).append("=").append(params.get(key));
				i++;
			}
		}
		CloseableHttpClient httpClient = createSSLClientDefault();
		CloseableHttpResponse response = null;
		System.out.println(url + sb.toString());

		HttpGet get = new HttpGet(url + sb.toString());

		String result = "";

		try {
			response = httpClient.execute(get);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			}
			System.out.println("Get:" + url + ";请求用时:" + (System.currentTimeMillis() - s));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != response) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	public static final String get(final String url, final Map<String, String> params, Map<String, String> cookie) {
		long s = System.currentTimeMillis();
		StringBuilder sb = new StringBuilder();
		if (null != params && !params.isEmpty()) {
			int i = 0;
			for (String key : params.keySet()) {
				if (i == 0) {
					sb.append("?");
				} else {
					sb.append("&");
				}
				sb.append(key).append("=").append(params.get(key));
				i++;
			}
		}
		CookieStore cookieStore = new BasicCookieStore();
		for (String key : cookie.keySet()) {
			BasicClientCookie coo = new BasicClientCookie(key, cookie.get(key));
			coo.setDomain("");
			cookieStore.addCookie(coo);
		}
		CloseableHttpClient httpClient = createSSLClientDefault(cookieStore);
		CloseableHttpResponse response = null;

		HttpGet get = new HttpGet(url + sb.toString());

		String result = "";

		try {
			response = httpClient.execute(get);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			}
			System.out.println("Get:" + url + ";请求用时:" + (System.currentTimeMillis() - s));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != response) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	public static final String post(final String url, final Map<String, Object> params) {
		long s = System.currentTimeMillis();
		CloseableHttpClient httpClient = createSSLClientDefault();
		HttpPost post = new HttpPost(url);

		CloseableHttpResponse response = null;

		if (null != params && !params.isEmpty()) {
			List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
				nvpList.add(nvp);
			}
			post.setEntity(new UrlEncodedFormEntity(nvpList, Charset.forName("UTF-8")));
		}

		String result = "";

		try {
			response = httpClient.execute(post);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			}
			System.out.println("Post:" + url + ";请求用时:" + (System.currentTimeMillis() - s));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != response) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param url
	 * @param jsonStr
	 * @return
	 */
	public static String postJson(final String url, final String jsonStr) {
		CloseableHttpClient httpClient = createSSLClientDefault();

		// log.info("create httppost:" + url);
		// HttpPost post = postJsonForm(url, jsonStr, sign);
		HttpPost httpost = new HttpPost(url);
		httpost.addHeader("Content-type", "application/json; charset=utf-8");
		httpost.setHeader("Accept", "application/json");
		if (StringUtils.isNotBlank(jsonStr)) {
			httpost.setEntity(new StringEntity(jsonStr, "utf-8"));
		}
		CloseableHttpResponse response = null;
		String result = "";
		try {
			response = httpClient.execute(httpost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return "";
		} finally {
			if (null != response) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;

	}

	public static final String doGet(final String url, final String cookie) {

		CloseableHttpClient httpClient = createSSLClientDefault();
		CloseableHttpResponse response = null;
		// System.out.println(url + url.toString());
		URI uri = null;
		try {
			URL url1 = new URL(url);
			uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		HttpGet get = new HttpGet(uri);

		if (null != cookie) {
			// System.out.println(cookie);
			get.addHeader("Cookie", cookie);
		}

		String result = "";

		try {
			response = httpClient.execute(get);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != response) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	public static CloseableHttpClient createSSLClientDefault() {

		SSLContext sslContext;
		try {

			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				@Override
				public boolean isTrusted(X509Certificate[] xcs, String string) {
					return true;
				}
			}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyStoreException ex) {
			ex.printStackTrace();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (KeyManagementException ex) {
			ex.printStackTrace();
		}

		return HttpClients.createDefault();
	}

	

	public static CloseableHttpClient createSSLClientDefault(CookieStore cookieStore) {

		SSLContext sslContext;
		try {

			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				@Override
				public boolean isTrusted(X509Certificate[] xcs, String string) {
					return true;
				}
			}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

			return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(cookieStore).build();
		} catch (KeyStoreException ex) {
			ex.printStackTrace();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (KeyManagementException ex) {
			ex.printStackTrace();
		}

		return HttpClients.createDefault();
	}
}
