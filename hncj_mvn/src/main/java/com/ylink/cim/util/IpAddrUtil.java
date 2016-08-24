package com.ylink.cim.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class IpAddrUtil {

	private static Logger logger = Logger.getLogger(IpAddrUtil.class);

	public static String getAddresses(String ip, String encodingString)
			throws Exception {
		String url = "http://ip.taobao.com/service/getIpInfo.php";
		// 从http://whois.pconline.com.cn取得IP所在的省市区信息
		// String returnStr = getResult(urlStr, content, encodingString);
		String returnStr = getRes(url, ip, encodingString);
		String country = "";
		String province = "";
		String city = "";
		// String isp = "";
		// System.out.println(returnStr);
		if (returnStr != null) {
			// {"code":0,"data":{"country":"\u4e2d\u56fd","country_id":"CN","area":"\u534e\u5357","area_id":"800000","region":"\u5e7f\u4e1c\u7701","region_id":"440000","city":"\u6df1\u5733\u5e02","city_id":"440300","county":"","county_id":"-1","isp":"\u7535\u4fe1","isp_id":"100017","ip":"14.155.30.79"}}
			JSONObject object = JSONObject.fromObject(returnStr);
			JSONObject data = (JSONObject) object.get("data");
			country = decodeUnicode(data.getString("country"));
			province = decodeUnicode(data.getString("region"));
			city = decodeUnicode(data.getString("city"));
			// isp = decodeUnicode(data.getString("isp"));
			return country + province + city;
		}
		return null;
	}

	public static String getRes(String urlStr, String ip, String encoding)
			throws Exception {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(urlStr);
		post.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=" + encoding);
		ip = "14.155.30.79";
		NameValuePair[] data = { new NameValuePair("ip", ip) };// 设置短信内容
		post.setRequestBody(data);
		client.executeMethod(post);
		// Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		logger.debug("返回状态码:" + statusCode);
		/*
		 * for (Header h : headers) { System.out.println(h.toString()); }
		 */
		String result = new String(post.getResponseBodyAsString().getBytes(
				encoding));
		return result;
	}

	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	// 测试
	public static void main(String[] args) throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
		ip = "14.155.30.79";
		String address = "";
		try {
			address = IpAddrUtil.getAddresses(ip, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(address);
	}
}