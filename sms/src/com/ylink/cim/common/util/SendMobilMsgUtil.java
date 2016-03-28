package com.ylink.cim.common.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import flink.util.SpringContext;

public class SendMobilMsgUtil {
	public static Logger logger = Logger.getLogger(SendMobilMsgUtil.class);
	public static final String KEY_LX = "LX";
	public static final String KEY_DLZH = "DLZH";
	public static final String KEY_DLMM = "DLMM";
	public static final String KEY_SJHM = "SJHM";
	public static final String KEY_DXNR = "DXNR";
	public static final String KEY_KZHM = "KZHM";
	public static final String KEY_FHLS = "FHLS";

	/*
	 * public static void sendMsg(String mobile, String content) throws
	 * Exception { HttpClient client = new HttpClient(); PostMethod post = new
	 * PostMethod("http://sms.webchinese.cn/web_api/");
	 * post.addRequestHeader("Content-Type",
	 * "application/x-www-form-urlencoded;charset=gbk");// ��ͷ�ļ�������ת��
	 * NameValuePair[] data = { new NameValuePair("Uid", "damjam"), // ע����û���
	 * new NameValuePair("Key", "3b6f85e76945d1577e48"), new
	 * NameValuePair("smsMob", mobile), // �ֻ����� new NameValuePair("smsText",
	 * content) };// ���ö������� post.setRequestBody(data);
	 * client.executeMethod(post); Header[] headers = post.getResponseHeaders();
	 * int statusCode = post.getStatusCode(); System.out.println("statusCode:" +
	 * statusCode); for (Header h : headers) { System.out.println(h.toString());
	 * } String result = new
	 * String(post.getResponseBodyAsString().getBytes("gbk"));
	 * System.out.println(result); post.releaseConnection(); }
	 */
	public static void main(String[] args) throws Exception {
		// sendMsg("18520827190", "���Զ���");
	}

	/**
	 * 
	 * @param mobile
	 * @param content
	 */
	public static void sendMsgFw(String mobile, String content) {
		try {
			MobileMsgConfig config = (MobileMsgConfig) SpringContext
					.getService("mobileMsgConfig");
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(config.getPostUrl());
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=gbk");
			NameValuePair[] data = {
					new NameValuePair(KEY_LX, "0"),
					new NameValuePair(KEY_DLZH, config.getAccount()), // ע����û���
					new NameValuePair(KEY_DLMM, config.getPassword()),
					new NameValuePair(KEY_SJHM, mobile), // �ֻ�����
					new NameValuePair(KEY_DXNR, content),
					new NameValuePair(KEY_KZHM, "005"),
					new NameValuePair(KEY_FHLS, "1") };// ���ö�������
			post.setRequestBody(data);
			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			logger.debug("����״̬��:" + statusCode);
			for (Header h : headers) {
				System.out.println(h.toString());
			}
			String result = new String(post.getResponseBodyAsString().getBytes(
					"gbk"));
			logger.debug(result);
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendMsgFw(String[] mobiles, String content) {
		try {
			if (ArrayUtils.isEmpty(mobiles)) {
				logger.warn("�ֻ���Ϊ��");
				return;
			}
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < mobiles.length; i++) {
				if (builder.length() > 0) {
					builder.append(";");
				}
				builder.append(mobiles[i]);
			}
			MobileMsgConfig config = (MobileMsgConfig) SpringContext
					.getService("mobileMsgConfig");
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(config.getPostUrl());
			post.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=gbk");
			NameValuePair[] data = {
					new NameValuePair(KEY_LX, "0"),
					new NameValuePair(KEY_DLZH, config.getAccount()), // ע����û���
					new NameValuePair(KEY_DLMM, config.getPassword()),
					new NameValuePair(KEY_SJHM, builder.toString()), // �ֻ�����
					new NameValuePair(KEY_DXNR, content),
					new NameValuePair(KEY_KZHM, "005"),
					new NameValuePair(KEY_FHLS, "1") };// ���ö�������
			post.setRequestBody(data);
			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			logger.debug("����״̬��:" + statusCode);
			for (Header h : headers) {
				System.out.println(h.toString());
			}
			String result = new String(post.getResponseBodyAsString().getBytes(
					"gbk"));
			logger.debug(result);
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
