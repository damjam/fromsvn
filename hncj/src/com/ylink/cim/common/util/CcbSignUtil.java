package com.ylink.cim.common.util;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ylink.cim.common.type.IdCardType;

import flink.MD5Util;

public class CcbSignUtil {

	public static String sign(HttpServletRequest request, HttpServletResponse response, Map<String, String> map) throws Exception { 
		StringBuilder url = new StringBuilder();
		url.append("https://ibsbjstar.ccb.com.cn/app/ccbMain?CCB_IBSVersion=V5");
		appendParam(url, "MERCHANTID", CcbConstant.MERCHANTID);
		appendParam(url, "POSID", CcbConstant.POSID);
		appendParam(url, "BRANCHID", CcbConstant.BRANCHID);
		appendParam(url, "AUTHID", map.get("AUTHID"));
		appendParam(url, "PAYMENT", map.get("PAYMENT"));
		appendParam(url, "LIMIT", map.get("LIMIT"));
		appendParam(url, "CURCODE", CcbConstant.CURCODE);
		appendParam(url, "TXCODE", CcbConstant.TXCODE_SIGN);
		String UName = URLEncoder.encode(map.get("UName"), "GBK");
		appendParam(url, "UName", UName);
		appendParam(url, "IdType", IdCardType.valueOf(map.get("IdType")).getCcbCardType());
		appendParam(url, "IdNumber", map.get("IdNumber"));
		appendParam(url, "EPAYNO", map.get("EPAYNO"));
		appendParam(url, "OTHER1", "0");
		appendParam(url, "REMARK1", "");
		appendParam(url, "REMARK2", "");
		StringBuilder strToMD5 = new StringBuilder(url);
		appendParam(strToMD5, "PUB32", CcbConstant.PUB32);
		String mac = MD5Util.MD5(strToMD5.toString());
		appendParam(url, "MAC", mac);
		System.out.println(url.toString());
		response.sendRedirect(url.toString());
		return null;
	}
	
	public static String updateSign() {
		
		return null;
	}
	public static String cancelSign(HttpServletRequest request, HttpServletResponse response, String accreditId) throws Exception {
		StringBuilder url = new StringBuilder();
		url.append("http://127.0.0.1:8080/auth/sign");
		url.append("?bankType=105");
		appendParam(url, "operType", "cancel");
		appendParam(url, "ACCREDIT_ID", accreditId);
		response.sendRedirect(url.toString());
		return null;
	}
	private static StringBuilder appendParam(StringBuilder builder, String key, String value){
		builder.append("&");
		builder.append(key);
		builder.append("=");
		builder.append(value);
		return builder;
	}
	
}
