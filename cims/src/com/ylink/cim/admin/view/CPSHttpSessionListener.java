package com.ylink.cim.admin.view;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.ylink.cim.admin.domain.UserInfo;

import flink.consant.Constants;

/**
 * 
 */
public class CPSHttpSessionListener implements HttpSessionListener {

	static Logger logger = Logger.getLogger(CPSHttpSessionListener.class);

	private static final Map<String, HttpSession> sessionMap = new ConcurrentHashMap<String, HttpSession>();

	/**
	 * 如果对应用户的 Session 存在，尝试销毁之。
	 * 
	 * @param userId
	 *            用户ID
	 */
	public static void logOff(final String userId) {
		HttpSession session = getSession(userId);
		if (session != null) {
			try {
				session.invalidate();
				sessionMap.remove(userId);
			} catch (Exception e) {
				logger.debug("用户"+userId+"离线失败", e);
			}
		}
	}

	/**
	 * 根据用户ID设置对应的 HttpSession
	 * 
	 * @param userId
	 *            用户ID
	 * @param userSession
	 *            用户的HttpSession
	 */
	public static void putSession(final String userId, final HttpSession userSession) {
		logOff(userId);
		sessionMap.put(userId, userSession);
	}

	/**
	 * 根据用户ID获得对应的 HttpSession
	 * 
	 * @param userId
	 *            用户ID
	 * @return 用户ID对应的 {@link HttpSession} 没有返回空。
	 */
	protected static HttpSession getSession(final String userId) {
		return sessionMap.get(userId);
	}

	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(final HttpSessionEvent se) {
	}

	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(final HttpSessionEvent se) {
		UserInfo user = (UserInfo) se.getSession().getAttribute(Constants.SESSION_USER);
		if (user != null) {
			String id = user.getUserId();
			sessionMap.remove(id);
		}
	}

}
