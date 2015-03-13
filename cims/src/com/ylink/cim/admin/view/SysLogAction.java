package com.ylink.cim.admin.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.SysLog;
import com.ylink.cim.admin.service.SysLogService;
import com.ylink.cim.common.type.LogClassType;
import com.ylink.cim.common.type.UserLogType;

import flink.util.BoUtils;
import flink.util.LogUtils;
import flink.util.Pager;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class SysLogAction extends BaseDispatchAction {

	private static Logger logger = Logger.getLogger(SysLogAction.class);
	SysLogService sysLogService = (SysLogService) getService("sysLogService");
	private PrivilegeDao privilegeDao = (PrivilegeDao)getService("privilegeDao");
	public SysLog getSysLog(SysLogActionForm sysLogActionForm) throws Exception {
		SysLog sysLog = new SysLog();
		BeanUtils.copyProperties(sysLog, sysLogActionForm);
		return sysLog;
	}

	public ActionForward querySysLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Pager pager = new Pager(getPageNumber(request), getPageSize(request));
		Paginater paginater = this.sysLogService.getSysLogPageList(this.getSysLog((SysLogActionForm) (form)), pager);
		List<Privilege> list = privilegeDao.findAll();
		BoUtils.addProperty(paginater.getList(), "limitId", "limitName", list, "limitId", "limitName");
		saveQueryResult(request, paginater);
		LogClassType.setInReq(request);
		String msg = LogUtils.r("系统日志查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return mapping.findForward("success");
	}

	public ActionForward querySysLogContent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysLogActionForm sysLogActionForm = (SysLogActionForm) form;

		// 如果是第一次查看。修改状态
		SysLog sysLog = this.sysLogService.getSysLogById(sysLogActionForm.getId());
		if (null == sysLog.getState() || "11".equals(sysLog.getState())) {
			sysLog.setViewDate(new java.util.Date());
			sysLog.setViewUser(getSessionUserCode(request));
			sysLog.setState("00");
			this.sysLogService.updateSysLog(sysLog);
		}
		Pager pager = new Pager(getPageNumber(request), getPageSize(request));
		Paginater paginater = this.sysLogService.getSysLogPageList(this.getSysLog(sysLogActionForm), pager);
		request.setAttribute(Paginater.PAGINATER, paginater);
		String msg = LogUtils.r("系统日志明细查看成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return mapping.findForward("detail");
	}
}
