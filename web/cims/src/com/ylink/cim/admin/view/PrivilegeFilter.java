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
 * 用户登录过滤
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
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			HttpServletResponse response = (HttpServletResponse) servletResponse;

			String uri = getUrl(request);
			System.out.println(uri);
			// 公共资源.
			if (isPublicUri(uri)) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}
			
			// 是否已登录.
			if (!isLogin(request)) {
				response.sendRedirect(request.getContextPath() + OVERTIME_URL);
				return;
			}
		
			// 是否具有访问权限.
     		if (hasPrivilege(request, uri)) {
				filterChain.doFilter(servletRequest, servletResponse);			
			}
			else {
				request.setAttribute("msg", "没有该项权限");
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

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		
		//初始化登陆后的可访问资源
		ApplicationContext context = new ClassPathXmlApplicationContext("filter.xml");
		LoginedResource loginedResource = (LoginedResource) context.getBean("LoginedResource");
		LOGINED_PUBLIC_URLS = loginedResource.getAllResource();
	}
	
	/**
	 * 是否有权限访问.
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
		
		// 有权限则取出权限路径.
		if (hasPrivilege) {
			// 权限路径.
			List privileges = (List) request.getSession().getAttribute(Constants.USER_PRIVILEGE);
			request.setAttribute("PRIVILEGE_PATH", getPrivilegePath(privileges, res.getLimitId()));
			
			// 当前动作.
			request.setAttribute("ACT", (Privilege) PrivilegeHelper.getPrivilege(res.getLimitId(), privileges));
		}
		else {
			logger.debug("用户[" + ((UserInfo)request.getSession().getAttribute(Constants.SESSION_USER)).getUserId() + "]没有权限, " + link);
		}
		
		long next = System.currentTimeMillis();
		//logger.debug("filter权限树构造时间："+(next-first));
			
		return hasPrivilege;
	}
	
	/**
	 * 获取权限的路径.
	 * @param allPriv  有的所有权限
	 * @param limitId 权限编号
	 * @return
	 */
	private List getPrivilegePath(List allPriv, String limitId) {
		List paths = new ArrayList();
		Privilege curPrivilege = (Privilege) PrivilegeHelper.getPrivilege(limitId, allPriv);
		if (curPrivilege == null) {
			return Collections.EMPTY_LIST;
		}

		//添加当前节点
		paths.add(curPrivilege);
		
		// 查找父节点.
		String limitid = curPrivilege.getCode();
		String parentCode =null;
		while (!Constants.ROOT_PRIVILEGE_CODE.equals(limitid)) {//当前节点不是根节点，则添加父节点。
			//添加父节点
			parentCode = curPrivilege.getParent();
			Privilege parentPrivilege = (Privilege) PrivilegeHelper.getPrivilege(parentCode, allPriv);
			paths.add(0, parentPrivilege);
			
			//进行下次循环
			curPrivilege = parentPrivilege;
			limitid = curPrivilege.getCode();
		}

		return paths;
	}
	
	/**
	 * 登陆后可访问的公共url.
	 * 
	 * @param uri
	 * @return
	 */
	private boolean isLoginedPublicUri(String uri, String contextPath) {
		String subUri = uri.substring(contextPath.length());
		
		return PatternMatchUtils.simpleMatch(LOGINED_PUBLIC_URLS, subUri);	
	}

	/**
	 * 是否已登录.
	 * 
	 * @param session
	 * @return
	 */
	private boolean isLogin(HttpServletRequest request) {
		return request.getSession().getAttribute(Constants.SESSION_USER) != null;
	}
	
	/**
	 * 未登录也允许访问的url.
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
		"/navi.jsp",
		"/index_navi.jsp",
		"/login.do?action=login",
		"/login.do?action=index",
		"/login.do?action=navi",
		"/userInfoAction.do?action=toChangePassord",//修改密码权限
		"/userInfoAction.do?action=changePassord",//修改密码权限
		"/login.jsp",
		"/overtime.jsp",
		"/error.jsp",
		"/authorityAlert.jsp",
		"/WebRequesServlet",
		"/login.do?action=logOff",
		
		"/custInfoAction.do?action=toRegister",
		"/custInfoAction.do?action=doRegister",
		"/custInfoAction.do?action=toSign",
		"/custInfoAction.do?action=preSign",
		"/custInfoAction.do?action=verifySignInfo",
		"/custInfoAction.do?action=doSign",
		"/custInfoAction.do?action=checkDate",
		"/custInfoAction.do?action=sendDymCode",
		"/custInfoAction.do?action=toResetPwd",
		"/custInfoAction.do?action=sendDymCodeForResetPwd",
		"/custInfoAction.do?action=verifyDymCodeForRestPwd",
		"/custInfoAction.do?action=doResetPwd",
		"/custInfoAction.do?action=verifyEmail",
		"/custInfoAction.do?action=cancelEmailSubs",
		"/custInfoAction.do?action=addSignInfo",
		"/investAcctAction.do?action=feedbackSign",
		"/custInfoAction.do?action=checkSign"
	};
	/**
	 * 登陆后的公用资源
	 */
	private static String[] LOGINED_PUBLIC_URLS ;
	
	// Clean up resources
	public void destroy() {
		this.filterConfig = null;
	}
}
