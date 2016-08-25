package flink.web;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import flink.consant.ActionConstant;
import flink.util.Paginater;


public abstract class RootAction extends ActionSupport implements SessionAware,ServletRequestAware,ServletResponseAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2736861230307224572L;
	private static Log logger = LogFactory.getLog(RootAction.class);
	public static final String RESULT_ERROR = "failure";
	private String successFlag;
	
	
	
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	protected String action;
	private String uri;
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * 处理异常, for override.
	 * 
	 * @param mapping
	 * @param request
	 * @param e
	 * @return
	 */
	protected String dealException(HttpServletRequest request, 
			HttpServletResponse response, Exception e, String methodName) {
		if (logger.isErrorEnabled()) {
			logger.error(e.getMessage(), e);
		}
		return ActionConstant.FAILURE;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DispatchAction#dispatchMethod(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	/*
	protected String dispatchMethod(HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		String forward = null;
		
		try {
			forward = super.execute();
		} 
		catch (Exception e) {
			return dealException(request, response, e, name);
		}
		
		return forward;
	}*/
	@Override
	public String execute() {
		try {
			uri = request.getRequestURI();
			super.execute();
			successFlag = executeMethod(getAction()); 
			return successFlag;
		}
		catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			//this.addActionError(this.getText("error.msg"));
			request.setAttribute("msg", e.getMessage());
			return RESULT_ERROR;
		}
	}
	
	protected String executeMethod(String method) throws Exception {
		if(StringUtils.isEmpty(method)){
			return "index";
		}
		Class[] c = null;
		Method m = this.getClass().getMethod(method, c);
		Object[] o = null;
		String result = (String) m.invoke(this, o);
		return result;
	}
	
	public String getAction() {
		return action;
	}
	
	public String getSuccessFlag() {
		return successFlag;
	}
	
	/**
	 * 保存查询结果.
	 * 
	 * @param request
	 * @param paginater 分页器.
	 * @param list
	 */
	protected void saveQueryResult(HttpServletRequest request, List list) {
		this.saveQueryResult(request, list, "list");
	}
	

	/**
	 * 保存查询结果.
	 * 
	 * @param request
	 * @param paginater 分页器.
	 * @param list
	 */
	protected void saveQueryResult(HttpServletRequest request, List list, String listName) {
			request.setAttribute(listName, list);
	}

	/**
	 * 保存查询结果.
	 * 
	 * @param request
	 * @param paginater 分页器.
	 * @param list
	 */
	protected void saveQueryResult(HttpServletRequest request, Paginater paginater) {
		this.saveQueryResult(request, paginater, "list", Paginater.PAGINATER);
	}

	/**
	 * 保存查询结果.
	 * 
	 * @param request
	 * @param paginater 分页器.
	 * @param list
	 */
	protected void saveQueryResult(HttpServletRequest request, Paginater paginater, String listName) {
		this.saveQueryResult(request, paginater, listName, Paginater.PAGINATER);
	}
	/**
	 * 保存查询结果.
	 * 
	 * @param request
	 * @param paginater 分页器.
	 * @param list
	 */
	protected void saveQueryResult(HttpServletRequest request, Paginater paginater, String listName, String paginaterName) {
		if (paginater == null) {
			return;
		}
		
		if (StringUtils.isNotEmpty(paginaterName)) {
			request.setAttribute(paginaterName, paginater);
		}

		if (CollectionUtils.isNotEmpty(paginater.getData())) {
			request.setAttribute(listName, paginater.getList());
		}
	}
	public void setAction(String action) {
		this.action = action;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}



	@Override
	public void setSession(Map<String, Object> map) {
		this.session = map;
	}
	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}
	
}
