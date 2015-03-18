package com.ylink.cim.admin.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * cookie������
 * 
 * @
 * 
 */
public class CookieDealer {
	private static Log log = LogFactory.getLog(CookieDealer.class);
	/**
	 * ����cookie
	 */
	public static final String COOKIE_YES = "1";

	public static final String FROM_BRANCH = "fromBranch";
	/**
	 * ��ȡ��ǰ�����д������е�cookie
	 * @param keyToGetCookie  ��ȡcookie��name�б�
	 * @param keyForRemenberNames  cookie���浽request�е�key�б�
	 * @param request
	 * @param response
	 */
	public static Map<String, String> branchMap = new HashMap<String, String>();
	static{
		branchMap.put("848348906ed5cadbd217473007ca5e71", "1");
		branchMap.put("6d76641d5910cee05f05c770fd6cbe17", "2");
	}
	public static void dealAllCookieInCurrentReq(String[] keyToGetCookie,String[] keyForRemenberNames,
			HttpServletRequest request) {
		if(request.getAttribute(keyForRemenberNames[0])!=null){//˵�������Ѿ���ֵ�ˣ�����Ҫ��ʹ��cookie�е�ֵ
			return;
		}
		if (keyToGetCookie == null || keyToGetCookie.length == 0) {
			return;
		}
		for (int i = 0; i < keyToGetCookie.length; i++) {
			String keyOfCookie = keyToGetCookie[i];
			String keyOfReq = keyForRemenberNames[i];
			CookieDealer.putCookieToReqAttr(keyOfCookie, keyOfReq, request);// ��ȡ��ǰ�����д���cookie
		}
	}

	/**
	 * ��ȡcookie�е�ֵ
	 * 
	 * @param keyOfCookie
	 * @param keyOfAttr
	 * @param request
	 */
	public static String getCookieValue(String keyOfCookie, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "";
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (keyOfCookie.equals(cookie.getName())) {
				String value = cookie.getValue();
				return value;
			}
		}
		return "";
	}

	/**
	 * ��������Ϣ���浽cookie
	 * 
	 * @param keyIfCookie
	 * @param keyForRemenberNames
	 * @param keyToGetCookie
	 * @param request
	 * @param response
	 */
	public static void putAllToCookie(String keyIfCookie, List<String> keyForRemenberNames,
			List<String> keyToGetCookie, HttpServletRequest request, HttpServletResponse response) {
		if (keyForRemenberNames == null || keyToGetCookie == null) {
			log.error("����ļ�ֵΪ�գ�");
			return;
		}
		CookieDealer.putAllToCookie(keyIfCookie, (String[]) keyForRemenberNames.toArray(),
				(String[]) keyToGetCookie.toArray(), request, response);
	}

	/**
	 * ��������Ϣ���浽cookie
	 * 
	 * @param keyIfCookie  �Ƿ񱣴�cookie
	 * @param values       Ҫ���浽cookie�е�ֵ  
	 * @param keyToGetCookie
	 * @param request
	 * @param response
	 */
	public static void putAllToCookie(String keyIfCookie, String[] values,
			String[] keyToGetCookie, HttpServletRequest request, HttpServletResponse response) {
		if(values == null || values.length==0 || keyToGetCookie ==null || keyToGetCookie.length==0){
			log.error("����Ĳ���Ϊ�գ����߼�ֵ��������һ��");
			return ;
		}
		for (int i = 0; i < values.length; i++) {
			saveCookie(keyIfCookie, keyToGetCookie[i], values[i], request, response);// ����ǰ��Ϣ���浽cookie��
		}
	}

	/**
	 * ���ͻ���cookie��������ݷ��뵽request��attr��
	 * 
	 * @param keyOfCookie
	 * @param keyOfAttrInReq
	 * @param request
	 */
	private static void putCookieToReqAttr(String keyOfCookie, String keyOfAttrInReq,
			HttpServletRequest request) {
		String value = getCookieValue(keyOfCookie, request);
		request.setAttribute(keyOfAttrInReq, value);
	}

	
	public static void saveBranch(String fromBranch, HttpServletResponse response) {
		Cookie cookie = new Cookie(FROM_BRANCH, fromBranch);
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}

	/**
	 * ����cookie
	 * 
	 * @param keyIfCookie
	 * @param key
	 * @param value
	 * @param request
	 * @param response
	 */
	public static void saveCookie(String keyIfCookie, String key, String value,
			HttpServletRequest request, HttpServletResponse response) {
		String ischeck = request.getParameter(keyIfCookie);
		if (COOKIE_YES.equals(ischeck)) {// ��ʾ�û�����ˡ���ס���롿��ť
			// ����cookie
			Cookie user = new Cookie(key, value);
			user.setMaxAge(60 * 60 * 24);
			user.setPath("/");
			response.addCookie(user);
		} else {// ɾ����Cookie
			Cookie deleteNewCookie = new Cookie(key, null);
			deleteNewCookie.setMaxAge(0);
			deleteNewCookie.setPath("/");
			response.addCookie(deleteNewCookie);
		}
	}
	
}
