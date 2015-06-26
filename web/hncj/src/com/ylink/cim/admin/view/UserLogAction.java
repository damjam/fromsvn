package com.ylink.cim.admin.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.admin.service.UserLogService;
import com.ylink.cim.common.type.UserLogType;

import flink.util.BoUtils;
import flink.util.LogUtils;
import flink.util.Pager;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class UserLogAction extends BaseAction implements ModelDriven<UserLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7458093271781199657L;
	@Autowired
	private UserLogService userLogService;
	@Autowired
	private PrivilegeDao privilegeDao;
	@Autowired
	private IdFactoryService idFactoryService;

	private UserLog model = new UserLog();

	public UserLog getModel() {
		return model;
	}

	public String queryUserLog() throws Exception {
		Pager pager = getPager(request);
		Paginater paginater = this.userLogService.getUserLogPageList(model, pager, this.getSessionUser(request));
		List<Privilege> list = privilegeDao.findAll();
		BoUtils.addProperty(paginater.getList(), "limitId", "limitName", list, "limitId", "limitName");
		List<UserInfo> userList = privilegeDao.findAll(UserInfo.class);
		BoUtils.addProperty(paginater.getList(), "userId", "userName", userList, "userId", "userName");
		saveQueryResult(request, paginater);
		UserLogType.setInReq(request);
		String msg = LogUtils.r("用户日志查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return "success";
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
	public String queryUserLogContent() throws Exception {
		UserLog userLog = userLogService.getUserLogDtl(model.getId());
		request.setAttribute("userLog", userLog);
		String msg = LogUtils.r("查看用户日志成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return "detail";
	}

}
