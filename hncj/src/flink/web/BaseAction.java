package flink.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.domain.SysLog;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.admin.service.SysLogService;
import com.ylink.cim.admin.service.UserLogService;

import flink.consant.ActionConstant;
import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.RuntimeBizException;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.SpringContext;
import flink.util.WebResource;

public abstract class BaseAction extends RootAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3797956804611323349L;

	private static final Logger logger = Logger.getLogger(BaseAction.class);

	private SysLogService sysLogService = (SysLogService) getService("sysLogService");
	private UserLogService userLogService = (UserLogService) getService("userLogService");
	protected IdFactoryService idFactoryService = (IdFactoryService) getService("idFactoryService");

	protected static Object getService(final String serviceName) {
		return SpringContext.getService(serviceName);
	}

	protected static final String GBK_TEXT_HTML = "text/html;charset=GBK";

	protected static final String GBK_TEXT_XML = "text/xml;charset=GBK";

	/** 防止重复提交的令牌环 */
	private static final String TOKEN_YLINK_KEY = "YLINK-KEY";

	private static final String TOKEN = "token";

	private static final String LOG = "BIZ_LOG";

	private static final String FORWARD = "FORWARD_URL";

	protected boolean result;
	protected String msg;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 保存业务处理的结果，前台可以取得
	 * 
	 * @param success
	 * @param message
	 * @param request
	 */
	public void setResult(final String result, final String message,
			final HttpServletRequest request) {
		request.setAttribute("result", result);
		request.setAttribute("msg", message);
	}

	/**
	 * 获取系统日志对象
	 * 
	 * @param request
	 * @param limitId
	 *            模块代码 不能为空
	 * @param errorCode
	 *            错误编码
	 * @param logType
	 *            日志类型 Constants 取 不能为空
	 * @param logClass
	 *            日志类别 Constants 取 不能为空
	 * @param approach
	 *            建议处理方式
	 * @param logContent
	 *            日志内容 不能为空
	 * @return
	 * @throws BizException
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flink.struts.action.RootDispatchAction#dealException(org.apache.struts
	 * .action.ActionMapping, javax.servlet.http.HttpServletRequest,
	 * java.lang.Exception, java.lang.String)
	 */

	@Override
	protected String dealException(final HttpServletRequest request,
			final HttpServletResponse response, final Exception e,
			final String methodName) {
		e.printStackTrace();
		try {
			String limitId = getCurPrivilegeCode(request);
			String forward = (String) request.getAttribute(FORWARD);
			String error = "";
			if (e instanceof BizException || e instanceof RuntimeBizException) {
				String msg = e.getMessage();
				error = ObjectUtils.toString(getLog(request));
				setResult(false, msg, request);
			} else if (e instanceof DataAccessResourceFailureException) {
				error = "数据库连接异常，请检查网络连接";
				setResult(false, error, request);
			} else {
				error = "系统错误，操作失败！";
				setResult(false, error, request);
				logErrorWithReason(request, limitId, error, error);
				logger.debug(error, e);
			}
			logErrorWithReason(request, limitId, error, error);
			if (StringUtils.isNotEmpty(forward)) {
				return forward;
			}
		} catch (Exception ex) {
			logger.debug(ex, ex);
		}

		return ActionConstant.FAILURE;
	}

	protected void logErrorWithReason(final HttpServletRequest request,
			final String limitId, final String content, final String errorReason)
			throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append(content).append(";失败原因:").append(errorReason);
		saveUserLog(request, limitId, Constants.LOG_USER_O, sb.toString());
	}

	protected void saveSysLog(final HttpServletRequest request,
			final String limitId, final String errorCode, final String logType,
			final String logClass, final String logContent) throws BizException {

		SysLog sysLog = getSysLog(request, limitId, errorCode, logType,
				logClass, logContent);
		this.sysLogService.saveSysLog(sysLog);
	}

	private SysLog getSysLog(final HttpServletRequest request,
			final String limitId, final String errorCode, final String logType,
			final String logClass, final String logContent) throws BizException {

		UserInfo sessionUser = getSessionUser(request);
		SysLog sysLog = new SysLog();

		// 数据检查
		Assert.notNull(limitId, "模块编号不能为空");
		Assert.notNull(logContent, "日志内容不能为空");

		Assert.notNull(logType, "日志类型不能为空");
		Assert.notNull(logClass, "日志类别不能为空");

		if (Constants.LOG_SYS_TYPE.indexOf(logType) < 0) {
			throw new BizException("日志类型非法");
		}

		if (Constants.LOG_SYS_CLASS.indexOf(logClass) < 0) {
			throw new BizException("日志类别非法");
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

	protected void saveUserLog(final HttpServletRequest request,
			final String limitId, final String logType, final String logContent)
			throws BizException {
		UserLog userLog = getUserLog(request, limitId, logType,
				StringUtils.abbreviate(logContent, 250));
		this.userLogService.saveUserLog(userLog);
	}

	private UserLog getUserLog(final HttpServletRequest request,
			String limitId, final String logType, final String logContent)
			throws BizException {

		String ip = request.getRemoteAddr();
		UserLog userLog = new UserLog();
		userLog.setId(idFactoryService.generateId(Constants.USER_LOG_ID));
		// 数据检查
		// Assert.notNull(sessionUser.getBranchNo(), "所属机构不能为空");
		// Assert.notNull(limitId, "模块编号不能为空");
		if (limitId == null) {
			limitId = "";
		}// 将模块编号：空的话修改为“ ”
		Assert.notNull(logType, "日志类型不能为空");
		Assert.notNull(logContent, "日志内容不能为空");
		if (Constants.LOG_USER_TYPE.indexOf(logType) < 0) {
			throw new BizException("日志类型非法");
		}

		UserInfo sessionUser = getSessionUser(request);
		if (sessionUser == null) {// 没有登录成功的情景
			userLog.setUserId("anony");
		} else {
			userLog.setUserId(sessionUser.getUserId());
		}
		userLog.setLimitId(limitId);
		userLog.setLoginIp(ip);
		userLog.setLogType(logType);
		userLog.setContent(logContent);
		if (getSessionUser(request) != null) {
			userLog.setBranchNo(getSessionUser(request).getBranchNo());
		}
		userLog.setCreateTime(new java.util.Date());
		return userLog;
	}

	protected void logError(final HttpServletRequest request, String operType,
			final String content) throws BizException {
		String limitId = getCurPrivilegeCode(request);

		// 无权限点不记录数据库日志.
		if (StringUtils.isEmpty(limitId)) {
			// return;
		}

		// 日志无内容不记录数据库日志.
		if (StringUtils.isEmpty(content)) {
			return;
		}

		saveUserLog(request, limitId, operType, content);
	}

	/**
	 * 从session中保存的用户权限中获取当前操作的权限编号.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected String getCurPrivilegeCode(final HttpServletRequest request) {
		Map<String, PrivilegeResource> privileges = (Map<String, PrivilegeResource>) WebUtils
				.getSessionAttribute(request, Constants.USER_PRIVILEGE_RES);

		if (privileges == null) {
			return null;
		}

		String link = WebResource.getLink(request);
		PrivilegeResource res = privileges.get(link);

		return res == null ? null : res.getLimitId();
	}

	protected String getLog(final HttpServletRequest request) {
		return (String) request.getAttribute(LOG);
	}

	/**
	 * 取页数.
	 * 
	 * @param request
	 * @return
	 */
	protected long getPageNumber(final HttpServletRequest request) {
		String pageNumber = request.getParameter(Paginater.PAGE_NUMBER);

		return NumberUtils.isDigits(pageNumber) ? Long.parseLong(pageNumber)
				: 1;
	}

	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	protected Pager getPager(final HttpServletRequest request) {
		return getPager(request, getPageSize(request));
	}

	/**
	 * 获取分页参数
	 * 
	 * @param request
	 * @return
	 */
	protected Pager getPager(final HttpServletRequest request,
			final int pageSize) {
		return new Pager(getPageNumber(request), pageSize);
	}

	/**
	 * 获取每页笔数
	 * 
	 * @param request
	 * @return
	 */
	protected int getPageSize(final HttpServletRequest request) {
		String pageSize = request.getParameter(Constants.PAGE_SIZE);

		return NumberUtils.isDigits(pageSize) ? Integer.parseInt(pageSize)
				: Constants.DEFAULT_PAGE_SIZE;
	}

	/**
	 * 获取当前用户.
	 * 
	 * @param request
	 * @return
	 */
	protected UserInfo getSessionUser(final HttpServletRequest request) {
		return (UserInfo) WebUtils.getSessionAttribute(request,
				Constants.SESSION_USER);
	}

	/**
	 * 获取当前用户编号.
	 * 
	 * @param request
	 * @return
	 */
	protected String getSessionUserCode(final HttpServletRequest request) {
		return getSessionUser(request).getUserId();
	}

	/**
	 * 验证令牌是否合法
	 * 
	 * @param request
	 * @return
	 */
	protected boolean isValidKey(final HttpServletRequest request) {
		String key = request.getParameter(TOKEN_YLINK_KEY);
		String oldToken = (String) request.getSession().getAttribute(
				TOKEN_YLINK_KEY);

		if (StringUtils.equals(key, oldToken)) {
			setResult(false, "非法或重复的操作", request);

			return false;
		}

		// 如果是合法key则保存在session
		saveTokenKey(request);
		return true;
	}

	protected boolean isValidToken() {
		String tokenVal = this.token;
		String oldToken = MapUtils.getString(session, TOKEN);
		if (StringUtils.isEmpty(tokenVal)) {
			return true;
		}
		if (StringUtils.equals(tokenVal, oldToken)) {
			setResult(false, "非法或重复的操作", request);
			this.msg = "非法或重复的操作";
			return false;
		}

		// 如果是合法key则保存在session
		session.put(TOKEN, tokenVal);
		return true;
	}

	/**
	 * response 输出消息.
	 * 
	 * @param response
	 * @param msg
	 * @return
	 */
	protected String respond(final HttpServletResponse response,
			final String msg) {
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

	/**
	 * 保存令牌到session
	 * 
	 * @param request
	 * @return
	 */
	protected void saveTokenKey(final HttpServletRequest request) {
		String key = request.getParameter(TOKEN_YLINK_KEY);
		request.getSession().setAttribute(TOKEN_YLINK_KEY, key);
	}

	protected void setForward(final HttpServletRequest request,
			final String forward) {
		request.setAttribute(FORWARD, forward);
	}

	protected void setLog(final HttpServletRequest request, final String log) {
		request.setAttribute(LOG, log);
	}

	/**
	 * 保存业务处理的结果，前台可以取得
	 * 
	 * @param success
	 * @param message
	 * @param request
	 */
	protected void setResult(final boolean success, final String message,
			final HttpServletRequest request) {
		request.setAttribute("result", Boolean.valueOf(success));
		this.result = success;
		this.msg = message;
		request.setAttribute("msg", message);
		request.setAttribute("result", success);
	}

	protected void setSucResult(HttpServletRequest request) {
		setSucResult("操作成功 ", request);
	}

	protected void setSucResult(String message, HttpServletRequest request) {
		String s = "";
		try {
			s = URLEncoder.encode(message, "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
		}
		request.setAttribute("msg", s);
		this.msg = s;
		this.result = true;
	}

	protected void setReturnUrl(String url, HttpServletRequest request) {
		request.setAttribute("url", url);
	}

	protected Map<String, Object> getParaMap() {
		return new HashMap<String, Object>();
	}

	protected String getSessionCustId(HttpServletRequest request) {
		return (String) WebUtils.getSessionAttribute(request,
				Constants.SESSION_CUSTOM_ID);
	}

	protected String[] getSessionInvestAcctNos(HttpServletRequest request) {
		return (String[]) WebUtils.getSessionAttribute(request,
				Constants.INVEST_ACCT_NOS);
	}

	protected String getSessionBranchTag(HttpServletRequest request) {
		String tag = (String) WebUtils.getSessionAttribute(request,
				Constants.BRANCH_TAG);
		if (tag == null) {
			tag = "";
		}
		return tag;
	}

	protected String getSessionBranchNo(HttpServletRequest request) {
		return getSessionUser(request).getBranchNo();
	}

	protected void logSuccess(final HttpServletRequest request,
			String operType, final String content) throws BizException {
		logUserOperate(request, operType, content);
	}

	protected void logUserOperate(HttpServletRequest request, String operType,
			String logContent) throws BizException {
		saveUserLog(request, getCurPrivilegeCode(request), operType, logContent);
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

	/*
	 * protected ActionForm getSessionForm(HttpServletRequest request, String
	 * formName) { Map<String, ActionForm> map = (Map<String,
	 * ActionForm>)WebUtils.getSessionAttribute(request, "formMap"); if (map ==
	 * null) { return null; } return map.get("formName"); } protected void
	 * setSessionForm(HttpServletRequest request, String formName, ActionForm
	 * form) { Map<String, ActionForm> map = (Map<String,
	 * ActionForm>)WebUtils.getSessionAttribute(request, "formMap"); if (map ==
	 * null) { map = new HashMap<String, ActionForm>(); } map.put("formName",
	 * form); }
	 */
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
		for (Iterator iterator = paraMap.entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry entry = (Map.Entry) iterator.next();
			String key = (String) entry.getKey();
			if (StringUtils.isEmpty(key) || "updateSymbol".equals(key)
					|| "msg".equals(key)) {
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

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
