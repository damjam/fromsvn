package flink.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


import flink.consant.ActionConstant;
import flink.util.Paginater;

/**
 * Title: RootDispatchAction
 * 
 * @description: 
 * 
 * @copyright: (c) 2008 ylink inc
 * 
 * @version 1.0
 * @since 1.0 2008-07-01
 */
public abstract class RootDispatchAction extends DispatchAction {
	private static Log log = LogFactory.getLog(RootDispatchAction.class);
	
	
	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DispatchAction#dispatchMethod(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	protected ActionForward dispatchMethod(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
		ActionForward forward = null;
		
		try {
			forward = super.dispatchMethod(mapping, form, request, response, name);
		} 
		catch (Exception e) {
			return dealException(mapping, form, request, response, e, name);
		}
		
		return forward;
	}
	
	/**
	 * 处理异常, for override.
	 * 
	 * @param mapping
	 * @param request
	 * @param e
	 * @return
	 */
	protected ActionForward dealException(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response, Exception e, String methodName) {
		if (log.isErrorEnabled()) {
			log.error(e.getMessage(), e);
		}
		return failure(mapping);
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
	 * @param mapping
	 * @return
	 */
	protected ActionForward success(ActionMapping mapping) {
		return mapping.findForward(ActionConstant.SUCCESS);
	}
	
	/**
	 * @param mapping
	 * @return
	 */
	protected ActionForward failure(ActionMapping mapping) {
		return mapping.findForward(ActionConstant.FAILURE);
	}

}
