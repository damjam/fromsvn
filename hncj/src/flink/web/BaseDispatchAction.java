package flink.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.domain.SysLog;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.admin.service.SysLogService;
import com.ylink.cim.admin.service.UserLogService;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.cust.domain.CustInfo;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.RuntimeBizException;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.WebResource;

/**
 * Title: BaseDispatchAction
 *
 * @description: ����dispatch action
 * @copyright: (c) 2008 ylink inc.
 * 
 * @version 1.0
 * @since 1.0 2008-07-02
 */
public abstract class BaseDispatchAction extends RootDispatchAction {

	private static final Logger logger = Logger.getLogger(BaseDispatchAction.class);
 
	protected static Object getService(final String serviceName) {
		return SpringContext.getService(serviceName);
	}
	private SysLogService sysLogService = (SysLogService)getService("sysLogService");
	private UserLogService userLogService = (UserLogService)getService("userLogService");
	protected IdFactoryService idFactoryService = (IdFactoryService)getService("idFactoryService");


	protected static final String GBK_TEXT_HTML = "text/html;charset=GBK";

	protected static final String GBK_TEXT_XML = "text/xml;charset=GBK";

	/** ��ֹ�ظ��ύ�����ƻ� */
	private static final String TOKEN_YLINK_KEY = "YLINK-KEY";

	private static final String LOG = "BIZ_LOG";

	private static final String FORWARD = "FORWARD_URL";

	/**
	 * ��ȡϵͳ��־����
	 *
	 * @param request
	 * @param limitId
	 *           ģ����� ����Ϊ��
	 * @param errorCode
	 *           �������
	 * @param logType
	 *           ��־���� Constants ȡ ����Ϊ��
	 * @param logClass
	 *           ��־��� Constants ȡ ����Ϊ��
	 * @param approach
	 *           ���鴦��ʽ
	 * @param logContent
	 *           ��־���� ����Ϊ��
	 * @return
	 * @throws BizException
	 */
	

	/*
	 * (non-Javadoc)
	 * @see com.flink.struts.action.RootDispatchAction#dealException(org.apache.struts.action.ActionMapping,
	 * javax.servlet.http.HttpServletRequest, java.lang.Exception, java.lang.String)
	 */
	
	protected ActionForward dealException(final ActionMapping mapping, final ActionForm form,
		final HttpServletRequest request, final HttpServletResponse response, final Exception e, final String methodName) {
		e.printStackTrace();
		try {
			String limitId = getCurPrivilegeCode(request);
			String forward = (String) request.getAttribute(FORWARD);
			String error = "";
			if (e instanceof BizException || e instanceof RuntimeBizException) {
				String msg = e.getMessage();
				error = ObjectUtils.toString(getLog(request));
				setResult(false, msg, request);
			} else if(e instanceof DataAccessResourceFailureException){
				error = "���ݿ������쳣��������������";
				setResult(false, error, request);
			} else {
				error = "ϵͳ���󣬲���ʧ�ܣ�";
				setResult(false, error, request);
				logErrorWithReason(request, limitId, error, error);
				logger.debug(error, e);
			}
			logErrorWithReason(request, limitId, error, error);
			if (StringUtils.isNotEmpty(forward)) {
				return forward(forward);
			}
		} catch (Exception ex) {
			logger.debug(ex, ex);
		}

		return failure(mapping);
	}

	protected ActionForward forward(final HttpServletRequest request) {
		return new ActionForward((String) request.getAttribute(FORWARD));
	}
	protected ActionForward forward(final String url) {
		return new ActionForward(url);
	}
	/**
	 * ��session�б�����û�Ȩ���л�ȡ��ǰ������Ȩ�ޱ��.
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String getCurPrivilegeCode(final HttpServletRequest request) {
		Map<String, PrivilegeResource> privileges =
			(Map<String, PrivilegeResource>) WebUtils.getSessionAttribute(request, Constants.USER_PRIVILEGE_RES);

		if (privileges == null) {
			return null;
		}

		String link = WebResource.getLink(request);
		PrivilegeResource res = privileges.get(link);

		return res == null ? null : res.getLimitId();
	}
	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	protected String getLog(final HttpServletRequest request) {
		return (String) request.getAttribute(LOG);
	}
	
	/**
	 * ȡҳ��.
	 *
	 * @param request
	 * @return
	 */
	protected long getPageNumber(final HttpServletRequest request) {
		String pageNumber = request.getParameter(Paginater.PAGE_NUMBER);

		return NumberUtils.isDigits(pageNumber) ? Long.parseLong(pageNumber) : 1;
	}
	/**
	 * ��ȡ��ҳ����
	 *
	 * @param request
	 * @return
	 */
	protected Pager getPager(final HttpServletRequest request) {
		return getPager(request, getPageSize(request));
	}
	/**
	 * ��ȡ��ҳ����
	 *
	 * @param request
	 * @return
	 */
	protected Pager getPager(final HttpServletRequest request, final int pageSize) {
		return new Pager(getPageNumber(request), pageSize);
	}

	/**
	 * ��ȡÿҳ����
	 *
	 * @param request
	 * @return
	 */
	protected int getPageSize(final HttpServletRequest request) {
		String pageSize = request.getParameter(Constants.PAGE_SIZE);

		return NumberUtils.isDigits(pageSize) ? Integer.parseInt(pageSize) : Constants.DEFAULT_PAGE_SIZE;
	}

	protected Map<String, Object> getParaMap() {
		return new HashMap<String, Object>();
	}

	public Map<String, String> getReqMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(MsgField.h_bank_no.getFieldCode(), getSessionBranch(request).getValue());
		return map;
	}

	protected BranchType getSessionBranch(HttpServletRequest request) {
		String tag = getSessionBranchTag(request);
		return BranchType.getByTag(tag);
	}

	protected String getSessionBranchNo(HttpServletRequest request) {
		return getSessionUser(request).getBranchNo();
	}

	protected String getSessionBranchTag(HttpServletRequest request) {
		String tag = (String)WebUtils.getSessionAttribute(request, Constants.BRANCH_TAG);
		if(tag == null){
			tag = "";
		}
		return tag;
	}

	protected CustInfo getSessionCust(HttpServletRequest request) {
		return (CustInfo)WebUtils.getSessionAttribute(request, Constants.SESSION_CUSTOM);
	}

	protected String getSessionCustId(HttpServletRequest request) {
		return (String)WebUtils.getSessionAttribute(request, Constants.SESSION_CUSTOM_ID);
	}
	
	protected String[] getSessionInvestAcctNos(HttpServletRequest request) {
		return (String[])WebUtils.getSessionAttribute(request, Constants.INVEST_ACCT_NOS);
	}

	/**
	 * ��ȡ��ǰ�û�.
	 *
	 * @param request
	 * @return
	 */
	protected UserInfo getSessionUser(final HttpServletRequest request) {
		return (UserInfo) WebUtils.getSessionAttribute(request, Constants.SESSION_USER);
	}

	/**
	 * ��ȡ��ǰ�û����.
	 *
	 * @param request
	 * @return
	 */
	protected String getSessionUserCode(final HttpServletRequest request) {
		return getSessionUser(request).getUserId();
	}

	private SysLog getSysLog(final HttpServletRequest request, final String limitId, final String errorCode,
			final String logType, final String logClass, final String logContent)
			throws BizException {

		UserInfo sessionUser = getSessionUser(request);
		SysLog sysLog = new SysLog();

		// ���ݼ��
		Assert.notNull(limitId, "ģ���Ų���Ϊ��");
	    Assert.notNull(logContent, "��־���ݲ���Ϊ��");

		Assert.notNull(logType, "��־���Ͳ���Ϊ��");
		Assert.notNull(logClass, "��־�����Ϊ��");

		if (Constants.LOG_SYS_TYPE.indexOf(logType) < 0) {
			throw new BizException("��־���ͷǷ�");
		}

		if (Constants.LOG_SYS_CLASS.indexOf(logClass) < 0) {
			throw new BizException("��־���Ƿ�");
		}

		sysLog.setUserId(sessionUser.getUserId());
		sysLog.setLimitId(limitId);
		sysLog.setErrorCode(errorCode);
		sysLog.setLogType(logType);
		sysLog.setLogClass(logClass);
		String content = sysLog.getContent();
		if (content != null) {
			content = content.replaceAll("\\s*|\t|\r|\n", "");
		}
		content = StringUtils.abbreviate(content, 250);
		sysLog.setContent(logContent);
		sysLog.setState("11");
		sysLog.setCreateTime(new Date());
		return sysLog;
	}
 
	private UserLog getUserLog(final HttpServletRequest request, String limitId, final String logType,
			final String logContent) throws BizException {

		String ip = request.getRemoteAddr();
		UserLog userLog = new UserLog();
		userLog.setId(idFactoryService.generateId(Constants.USER_LOG_ID));
		// ���ݼ��
		// Assert.notNull(sessionUser.getBranchNo(), "������������Ϊ��");
		// Assert.notNull(limitId, "ģ���Ų���Ϊ��");
		if (limitId == null) {
			limitId = "";
		}// ��ģ���ţ��յĻ��޸�Ϊ�� ��
		Assert.notNull(logType, "��־���Ͳ���Ϊ��");
		Assert.notNull(logContent, "��־���ݲ���Ϊ��");
		if (Constants.LOG_USER_TYPE.indexOf(logType) < 0) {
			throw new BizException("��־���ͷǷ�");
		}

		UserInfo sessionUser = getSessionUser(request);
		if (sessionUser == null) {// û�е�½�ɹ����龰
			userLog.setUserId("anony");
		} else {
			userLog.setUserId(sessionUser.getUserId());
		}
		userLog.setLimitId(limitId);
		userLog.setLoginIp(ip);
		userLog.setLogType(logType);
		userLog.setContent(logContent);
		if(getSessionUser(request)!=null){
			userLog.setBranchNo(getSessionUser(request).getBranchNo());
		}
		userLog.setCreateTime(new java.util.Date());
		return userLog;
	}

	/**
	 * ��֤�����Ƿ�Ϸ�
	 *
	 * @param request
	 * @return
	 */
	protected boolean isValidKey(final HttpServletRequest request) {
		String key = request.getParameter(TOKEN_YLINK_KEY);
		String oldToken = (String) request.getSession().getAttribute(TOKEN_YLINK_KEY);

		if (StringUtils.equals(key, oldToken)) {
			setResult(false, "�Ƿ����ظ��Ĳ���", request);

			return false;
		}

		// ����ǺϷ�key�򱣴���session
		saveTokenKey(request);
		return true;
	}

	protected void logError(final HttpServletRequest request, String operType, final String content) throws BizException {
		String limitId = getCurPrivilegeCode(request);

		// ��Ȩ�޵㲻��¼���ݿ���־.
		if (StringUtils.isEmpty(limitId)) {
			//return;
		}

		// ��־�����ݲ���¼���ݿ���־.
		if (StringUtils.isEmpty(content)) {
			return;
		}

		saveUserLog(request, limitId, operType, content);
	}
	protected void logErrorWithReason(final HttpServletRequest request, final String limitId, final String content,
			final String errorReason) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append(content).append(";ʧ��ԭ��:").append(errorReason);
		saveUserLog(request, limitId, Constants.LOG_USER_O, sb.toString());
	}
	protected void logSuccess(final HttpServletRequest request, String operType, final String content) throws BizException {
		logUserOperate(request, operType, content);
	}
	protected void logUserOperate(HttpServletRequest request, String operType, String logContent) throws BizException {
		saveUserLog(request, getCurPrivilegeCode(request), operType, logContent);
	}
	/**
	 * response �����Ϣ.
	 *
	 * @param response
	 * @param msg
	 * @return
	 */
	protected ActionForward respond(final HttpServletResponse response, final String msg) {
		PrintWriter writer = null;

		try {
			response.setContentType(GBK_TEXT_HTML);
			writer = response.getWriter();
			writer.write(msg == null ? "" : msg);
		} catch (IOException e) {
			logger.debug(e, e);
		} finally {
			IOUtils.closeQuietly(writer);
		}

		return null;
	}
	protected void saveSysLog(final HttpServletRequest request, final String limitId, final String errorCode,
			final String logType, final String logClass, final String logContent)
			throws BizException {

		SysLog sysLog = getSysLog(request, limitId, errorCode, logType, logClass, logContent);
		this.sysLogService.saveSysLog(sysLog);
	}
	/**
	 * �������Ƶ�session
	 *
	 * @param request
	 * @return
	 */
	protected void saveTokenKey(final HttpServletRequest request) {
		String key = request.getParameter(TOKEN_YLINK_KEY);
		request.getSession().setAttribute(TOKEN_YLINK_KEY, key);
	}
	protected void saveUserLog(final HttpServletRequest request, final String limitId, final String logType,
			final String logContent) throws BizException {
		UserLog userLog = getUserLog(request, limitId, logType, StringUtils.abbreviate(logContent, 250));
		this.userLogService.saveUserLog(userLog);
	}
	/*protected ActionForm getSessionForm(HttpServletRequest request, String formName) {
		Map<String, ActionForm> map = (Map<String, ActionForm>)WebUtils.getSessionAttribute(request, "formMap");
		if (map == null) {
			return null;
		}
		return map.get("formName");
	}
	protected void setSessionForm(HttpServletRequest request, String formName, ActionForm form) {
		Map<String, ActionForm> map = (Map<String, ActionForm>)WebUtils.getSessionAttribute(request, "formMap");
		if (map == null) {
			map = new HashMap<String, ActionForm>();
		}
		map.put("formName", form);
	}*/
	@SuppressWarnings("unchecked")
	protected void setBackUrl(HttpServletRequest request) {
		String url = request.getRequestURI();
		url = url.substring(request.getContextPath().length());
		if (StringUtils.isEmpty(url)) {
			return;
		}
		request.getSession().setAttribute("backUrl", url);
		Map paraMap = request.getParameterMap();
		JSONObject json = new JSONObject();
		for (Iterator iterator = paraMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			if (StringUtils.isEmpty(key) || "updateSymbol".equals(key) || "msg".equals(key)) {
				continue;
			}
			Object value = entry.getValue();
			String values[];
			if (value instanceof String[]) {
				values = (String[]) value;
			} else {
				String[] valueHolder = new String[1];
				values = valueHolder;
			}
			if (ArrayUtils.isEmpty(values)) {
				continue;
			}
			if (values.length == 1) {
				if (!StringUtils.isEmpty(values[0])) {
					json.put(key, values[0]);
				}
			} else {
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < values.length; i++) {
					if (StringUtils.isEmpty(values[i])) {
						continue;
					}
					jsonArray.add(values[i]);
				}
				json.put(key, values);
			}
		}
		System.out.println(json.toString());
		request.getSession().setAttribute("params", json.toString());
	}
	protected void setForward(final HttpServletRequest request, final String forward) {
		request.setAttribute(FORWARD, forward);
	}
	protected void setLog(final HttpServletRequest request, final String log) {
		request.setAttribute(LOG, log);
	}
	/**
	 * ����ҵ����Ľ����ǰ̨����ȡ��
	 *
	 * @param success
	 * @param message
	 * @param request
	 */
	protected void setResult(final boolean success, final String message, final HttpServletRequest request) {
		request.setAttribute("result", Boolean.valueOf(success));
		request.setAttribute("msg", message);
	}
	/**
	 * ����ҵ����Ľ����ǰ̨����ȡ��
	 *
	 * @param success
	 * @param message
	 * @param request
	 */
	public void setResult(final String result, final String message, final HttpServletRequest request) {
		request.setAttribute("result", result);
		request.setAttribute("msg", message);
	}

	protected void setReturnUrl(String url, HttpServletRequest request){
		request.setAttribute("url", url);
	}
}
