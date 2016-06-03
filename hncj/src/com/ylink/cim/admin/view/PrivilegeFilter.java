package com.ylink.cim.admin.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.PatternMatchUtils;

import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.PrivilegeHelper;

import flink.consant.Constants;
import flink.util.WebResource;

/**
 * �û���¼����
 * 
 * 
 * 
 */
public class PrivilegeFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PrivilegeFilter.class);

	private FilterConfig filterConfig;
	private static final String OVERTIME_URL = "/pages/common/overtime.jsp";
	private static final String INVALID_URL = "/error.jsp";

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;

			String uri = getUrl(request);
			System.out.println(uri);
			// ������Դ.
			if (isPublicUri(uri)) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}

			// �Ƿ��ѵ�¼.
			if (!isLogin(request)) {
				response.sendRedirect(request.getContextPath() + OVERTIME_URL);
				return;
			}

			// �Ƿ���з���Ȩ��.
			if (hasPrivilege(request, uri)) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				request.setAttribute("msg", "û�и���Ȩ��");
				request.getSession().getServletContext().getRequestDispatcher(INVALID_URL).forward(request, response);
			}
		} catch (ServletException sx) {
			filterConfig.getServletContext().log(sx.getMessage());
		} catch (IOException iox) {
			filterConfig.getServletContext().log(iox.getMessage());
		}
	}

	private String getUrl(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (uri.endsWith(".jsp")) {
			return uri;
		}

		if (uri.endsWith(".do")) {
			String action = request.getParameter("action");

			if (StringUtils.isNotEmpty(action)) {
				return uri + "?action=" + action;
			}
		}

		return uri;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

		// ��ʼ����¼��Ŀɷ�����Դ
		ApplicationContext context = new ClassPathXmlApplicationContext("filter.xml");
		LoginedResource loginedResource = (LoginedResource) context.getBean("LoginedResource");
		LOGINED_PUBLIC_URLS = loginedResource.getAllResource();
	}

	/**
	 * �Ƿ���Ȩ�޷���.
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private boolean hasPrivilege(HttpServletRequest request, String uri) {

		long first = System.currentTimeMillis();

		if (isLoginedPublicUri(uri, request.getContextPath())) {
			return true;
		}

		Map resMap = (Map) request.getSession().getAttribute(Constants.USER_PRIVILEGE_RES);

		if (resMap == null) {
			return false;
		}

		String link = WebResource.getLink(request);
		PrivilegeResource res = (PrivilegeResource) resMap.get(link);
		boolean hasPrivilege = res != null;

		// ��Ȩ����ȡ��Ȩ��·��.
		if (hasPrivilege) {
			// Ȩ��·��.
			List privileges = (List) request.getSession().getAttribute(Constants.USER_PRIVILEGE);
			request.setAttribute("PRIVILEGE_PATH", getPrivilegePath(privileges, res.getLimitId()));

			// ��ǰ����.
			request.setAttribute("ACT", PrivilegeHelper.getPrivilege(res.getLimitId(), privileges));
		} else {
			logger.debug("�û�[" + ((UserInfo) request.getSession().getAttribute(Constants.SESSION_USER)).getUserId()
					+ "]û��Ȩ��, " + link);
		}

		long next = System.currentTimeMillis();
		// logger.debug("filterȨ��������ʱ�䣺"+(next-first));

		return hasPrivilege;
	}

	/**
	 * ��ȡȨ�޵�·��.
	 * 
	 * @param allPriv
	 *            �е�����Ȩ��
	 * @param limitId
	 *            Ȩ�ޱ��
	 * @return
	 */
	private List getPrivilegePath(List allPriv, String limitId) {
		List paths = new ArrayList();
		Privilege curPrivilege = (Privilege) PrivilegeHelper.getPrivilege(limitId, allPriv);
		if (curPrivilege == null) {
			return Collections.EMPTY_LIST;
		}

		// ��ӵ�ǰ�ڵ�
		paths.add(curPrivilege);

		// ���Ҹ��ڵ�.
		String limitid = curPrivilege.getCode();
		String parentCode = null;
		while (!Constants.ROOT_PRIVILEGE_CODE.equals(limitid)) {// ��ǰ�ڵ㲻�Ǹ��ڵ㣬����Ӹ��ڵ㡣
			// ��Ӹ��ڵ�
			parentCode = curPrivilege.getParent();
			Privilege parentPrivilege = (Privilege) PrivilegeHelper.getPrivilege(parentCode, allPriv);
			// ����Ӷ���ڵ�
			if (!StringUtils.isEmpty(parentPrivilege.getParent())) {
				paths.add(0, parentPrivilege);
			}
			// �����´�ѭ��
			curPrivilege = parentPrivilege;
			limitid = curPrivilege.getCode();
		}
		return paths;
	}

	/**
	 * ��¼��ɷ��ʵĹ���url.
	 * 
	 * @param uri
	 * @return
	 */
	private boolean isLoginedPublicUri(String uri, String contextPath) {
		String subUri = uri.substring(contextPath.length());

		return PatternMatchUtils.simpleMatch(LOGINED_PUBLIC_URLS, subUri);
	}

	/**
	 * �Ƿ��ѵ�¼.
	 * 
	 * @param session
	 * @return
	 */
	private boolean isLogin(HttpServletRequest request) {
		return request.getSession().getAttribute(Constants.SESSION_USER) != null;
	}

	/**
	 * δ��¼Ҳ������ʵ�url.
	 * 
	 * @param subUrl
	 * @return
	 */
	private boolean isPublicUri(String uri) {
		String subUri = uri.substring(uri.lastIndexOf("/"), uri.length());
		return PatternMatchUtils.simpleMatch(PUBLIC_URLS, subUri);
	}

	private static final String[] PUBLIC_URLS = new String[] {
			"/",
			"/index.jsp",
			"/index_old.jsp",
			"/index_new.jsp",
			"/navi.jsp",
			"/index_navi.jsp",
			"/login.do?action=login",
			"/login.do?action=index",
			"/login.do?action=navi",
			"/userInfoAction.do?action=toChangePassord",// �޸�����Ȩ��
			"/userInfoAction.do?action=changePassord",// �޸�����Ȩ��
			"/login.jsp", "/overtime.jsp", "/error.jsp", "/authorityAlert.jsp", "/WebRequesServlet",
			"/login.do?action=logOff",

			"/custInfoAction.do?action=toRegister", "/custInfoAction.do?action=doRegister",
			"/custInfoAction.do?action=toSign", "/custInfoAction.do?action=preSign",
			"/custInfoAction.do?action=verifySignInfo", "/custInfoAction.do?action=doSign",
			"/custInfoAction.do?action=checkDate", "/custInfoAction.do?action=sendDymCode",
			"/custInfoAction.do?action=toResetPwd", "/custInfoAction.do?action=sendDymCodeForResetPwd",
			"/custInfoAction.do?action=verifyDymCodeForRestPwd", "/custInfoAction.do?action=doResetPwd",
			"/custInfoAction.do?action=verifyEmail", "/custInfoAction.do?action=cancelEmailSubs",
			"/custInfoAction.do?action=addSignInfo", "/investAcctAction.do?action=feedbackSign",
			"/custInfoAction.do?action=checkSign" };
	/**
	 * ��¼��Ĺ�����Դ
	 */
	private static String[] LOGINED_PUBLIC_URLS;

	// Clean up resources
	@Override
	public void destroy() {
		this.filterConfig = null;
	}
}
