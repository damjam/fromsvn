package com.ylink.cim.admin.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.admin.service.UserLogService;
import com.ylink.cim.common.type.UserLogType;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.BoUtils;
import flink.util.LogUtils;
import flink.util.Pager;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class UserLogAction extends BaseDispatchAction {

	private UserLogService userLogService = (UserLogService) getService("userLogService");
	private PrivilegeDao privilegeDao = (PrivilegeDao)getService("privilegeDao");
	private IdFactoryService idFactoryService = (IdFactoryService)getService("idFactoryService");
	public UserLog getUserLog(UserLogActionForm userActionForm) throws BizException {
		UserLog userLog = new UserLog();
		userLog.setId(idFactoryService.generateId(Constants.USER_LOG_ID));
		userLog.setUserId(userActionForm.getUserId());
		userLog.setLimitId(userActionForm.getLimitId());
		userLog.setLogType(userActionForm.getLogType());
		return userLog;
	}
	

	public ActionForward queryUserLog(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserLogActionForm userLogActionForm = (UserLogActionForm) form;
		Pager pager = getPager(request);
		UserLog userLog = new UserLog();
		BeanUtils.copyProperties(userLog, userLogActionForm);
		Paginater paginater = this.userLogService.getUserLogPageList(userLog, pager, this
				.getSessionUser(request));
		List<Privilege> list = privilegeDao.findAll();
		BoUtils.addProperty(paginater.getList(), "limitId", "limitName", list, "limitId", "limitName");
		List<UserInfo> userList = privilegeDao.findAll(UserInfo.class);
		BoUtils.addProperty(paginater.getList(), "userId", "userName", userList, "userId", "userName");
		saveQueryResult(request, paginater);
		UserLogType.setInReq(request);
		String msg = LogUtils.r("用户日志查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return mapping.findForward("success");
	}

	/**
	 * 查看当前日志内容
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryUserLogContent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserLogActionForm userLogActionForm = (UserLogActionForm) form;
//		Pager pager = new Pager(getPageNumber(request), getPageSize(request));
//		Paginater paginater = this.userLogService.getUserLogPageList(this.getUserLog(userLogActionForm), pager, this
//				.getSessionUser(request));
//		saveQueryResult(request, paginater);
//		UserLogActionForm userLogActionForm = (UserLogActionForm) form;
		UserLog userLog=userLogService.getUserLogDtl(userLogActionForm.getUserLogId());
		request.setAttribute("userLog", userLog);
		String msg = LogUtils.r("查看用户日志成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
//		userLogActionForm.setUserLogId(null);
		return mapping.findForward("detail");
	}

//	public ActionForward queryUserLogDtl(ActionMapping mapping, ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		UserLogActionForm userLogActionForm = (UserLogActionForm) form;
//		UserLog userLog=userLogService.getUserLogDtl(userLogActionForm.getUserId());
//		request.setAttribute("userLog", userLog);
//		return mapping.findForward("success");
//	}
	
}
