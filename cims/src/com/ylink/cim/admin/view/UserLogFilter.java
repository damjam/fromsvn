package com.ylink.cim.admin.view;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.domain.UserLog;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.admin.service.UserLogService;
import com.ylink.cim.user.domain.UserInfo;

import flink.consant.Constants;
import flink.util.SpringContext;
import flink.util.WebResource;


/**
 * �û�������־
 * 
 *
 */
public class UserLogFilter extends HttpServlet implements Filter {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(UserLogFilter.class);
	private IdFactoryService idFactoryService = (IdFactoryService)SpringContext.getService("idFactoryService");
	private FilterConfig filterConfig;
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			String uri = request.getRequestURI();			
			if (uri.endsWith(".do")) {
				String ip = getIpAddr(request);
				UserLog userLog = new UserLog();
				userLog.setId(idFactoryService.generateId(Constants.USER_LOG_ID));
				String limitId=getCurPrivilegeCode(request);
				if (limitId == null) {
					limitId = "";
				}// ��ģ���ţ��յĻ��޸�Ϊ�� ��
	
				UserInfo sessionUser =(UserInfo)WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
				if (sessionUser == null) {// û�е�½�ɹ����龰
					userLog.setUserId("anony");
				} else {
					userLog.setUserId(sessionUser.getUserId());
//					userLog.setLoginType(sessionUser.getUserType());
				}
				userLog.setLimitId(limitId);
				userLog.setLoginIp(ip);
				userLog.setLogType(getLogType(request,uri));
				userLog.setContent(StringUtils.abbreviate(getLogContent(request,uri), 100));
				userLog.setCreateTime(new java.util.Date());
				UserLogService userLogService = (UserLogService)SpringContext.getService("userLogService");
				userLogService.saveUserLog(userLog);
			}
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (Exception e) {
			filterConfig.getServletContext().log(e.getMessage());
		}
	}

	private String getLogType(HttpServletRequest request,String uri){
		String logType=Constants.LOG_USER_O;//����
		if (uri.endsWith(".do")) {
			String action = request.getParameter("action");
			if(action.startsWith("query")
					||action.startsWith("search")
					||action.startsWith("find")
					||action.startsWith("get")
					||action.startsWith("view")
					||action.startsWith("is")
					||action.startsWith("list")
					||action.startsWith("toQuery")
					||action.startsWith("toSearch")
					||action.startsWith("toFind")
					||action.startsWith("toGet")
					||action.startsWith("toView")
					||action.startsWith("toIs")
					||action.startsWith("toList")
					){
				
				 logType=Constants.LOG_USER_S;//��ѯ
			}
			if(action.startsWith("insert")
					||action.startsWith("save")
					||action.startsWith("add")
					||action.startsWith("toInsert")
					||action.startsWith("toSave")
					||action.startsWith("toAdd")
					){
				 logType=Constants.LOG_USER_A;//����
			}
			if(action.startsWith("update")
					||action.startsWith("modify")
					||action.startsWith("toUpdate")
					||action.startsWith("toModify")
					||action.startsWith("edit")
					||action.startsWith("toEdit")
					){
				 logType=Constants.LOG_USER_U;//�޸�
			}
			if(action.startsWith("delete")
					||action.startsWith("toDelete")
					||action.startsWith("del")
					||action.startsWith("toDel")
					){
				 logType=Constants.LOG_USER_D;//�޸�
			}
			if(action.startsWith("check")
					||action.startsWith("toCheck")
					){
				 logType=Constants.LOG_USER_C;//���
			}
		}
		return logType;	
	}
	
	private String getLogContent(HttpServletRequest request,String uri) {
		Enumeration en = request.getParameterNames();
        StringBuffer url = new StringBuffer();
        String paramName = "";
        String paramValue = "";
        url.append(uri);
        url.append("?");
        String action = request.getParameter("action");
        if(StringUtils.isNotEmpty(action)){
	        url.append("action").append("=").append(action);
	        url.append("&");
        }
        while(en.hasMoreElements()){        //�����������ԣ�����ֵ�Ľ�����װ
        	 	
                paramName = en.nextElement().toString();
                paramValue = request.getParameter(paramName);
                if(!paramName.equalsIgnoreCase("action")){
	                url.append(paramName).append("=").append(paramValue);
	                url.append("&");
                }
               
        }
        int len=url.toString().length();
        String logContent=url.toString().substring(0, len-1);
		return logContent;
	}
	
	private String getCurPrivilegeCode(final HttpServletRequest request) {
		Map<String, PrivilegeResource> privileges =
			(Map<String, PrivilegeResource>) WebUtils.getSessionAttribute(request, Constants.USER_PRIVILEGE_RES);
		if (privileges == null) {
			return null;
		}
		String link = WebResource.getLink(request);
		PrivilegeResource res = privileges.get(link);
		return res == null ? null : res.getLimitId();
	}

	private  String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
	public void init(FilterConfig filterConfig) throws ServletException {
			this.filterConfig = filterConfig;
	}
	
	public void destroy() {
		this.filterConfig = null;
	}
}
