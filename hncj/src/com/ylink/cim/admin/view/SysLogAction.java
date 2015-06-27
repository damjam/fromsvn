package com.ylink.cim.admin.view;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.SysLog;
import com.ylink.cim.admin.service.SysLogService;
import com.ylink.cim.common.type.LogClassType;
import com.ylink.cim.common.type.UserLogType;

import flink.util.BoUtils;
import flink.util.MsgUtils;
import flink.util.Pager;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class SysLogAction extends BaseAction implements ModelDriven<SysLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -138026494547713801L;
	private static Logger logger = Logger.getLogger(SysLogAction.class);
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private PrivilegeDao privilegeDao;

	public String querySysLog() throws Exception {
		Pager pager = new Pager(getPageNumber(request), getPageSize(request));
		Paginater paginater = this.sysLogService
				.getSysLogPageList(model, pager);
		List<Privilege> list = privilegeDao.findAll();
		BoUtils.addProperty(paginater.getList(), "limitId", "limitName", list,
				"limitId", "limitName");
		saveQueryResult(request, paginater);
		LogClassType.setInReq(request);
		String msg = MsgUtils.r("系统日志查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return "success";
	}

	public String querySysLogContent() throws Exception {

		// 如果是第一次查看。修改状态
		SysLog sysLog = this.sysLogService.getSysLogById(model.getId());
		if (null == sysLog.getState() || "11".equals(sysLog.getState())) {
			sysLog.setViewDate(new java.util.Date());
			sysLog.setViewUser(getSessionUserCode(request));
			sysLog.setState("00");
			this.sysLogService.updateSysLog(sysLog);
		}
		Pager pager = new Pager(getPageNumber(request), getPageSize(request));
		Paginater paginater = this.sysLogService
				.getSysLogPageList(model, pager);
		request.setAttribute(Paginater.PAGINATER, paginater);
		String msg = MsgUtils.r("系统日志明细查看成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return "detail";
	}

	@Override
	public SysLog getModel() {
		return model;
	}

	private SysLog model = new SysLog();
}
