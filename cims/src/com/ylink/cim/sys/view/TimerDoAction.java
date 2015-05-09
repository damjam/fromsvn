package com.ylink.cim.sys.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.sys.dao.TimerDoDao;
import com.ylink.cim.sys.domain.TimerDo;
import com.ylink.cim.sys.service.TimerDoService;

import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class TimerDoAction extends BaseDispatchAction {
	TimerDoService timerDoService = (TimerDoService) getService("timerDoService");

	TimerDoDao timerDoDao = (TimerDoDao) getService("timerDoDao");

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimerDoActionForm actionForm = (TimerDoActionForm) form;
		TimerDo timerDo = new TimerDo();
		BeanUtils.copyProperties(timerDo, actionForm);
		Paginater paginater = this.timerDoDao.getPagerList(timerDo, getPager(request));
		saveQueryResult(request, paginater);
		return forward("/pages/admin/sysRunManager/timerDoList.jsp");
	}

}
