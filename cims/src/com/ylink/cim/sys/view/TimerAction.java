package com.ylink.cim.sys.view;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.sys.dao.TimerDao;
import com.ylink.cim.sys.domain.Timer;
import com.ylink.cim.sys.service.TimerService;

import flink.util.Paginater;
import flink.web.BaseDispatchAction;

/**
 * 
 * 
 */
public class TimerAction extends BaseDispatchAction {

	TimerService timerService = (TimerService) getService("timerService");
	TimerDao timerDao = (TimerDao)getService("timerDao");
	public void clearData(TimerActionForm timerActionForm) {
		timerActionForm.setId(null);
		timerActionForm.setBeanName("");
		timerActionForm.setBeanNameCh("");
		timerActionForm.setRemark("");
		timerActionForm.setState("");
		timerActionForm.setPara1(null);
		timerActionForm.setPara2(null);
		timerActionForm.setTriggertime(null);
		timerActionForm.setGroupno("");
		timerActionForm.setCreateTime(null);
		timerActionForm.setCreateUser("");
		timerActionForm.setAuditTime(null);
		timerActionForm.setAuditUser("");
		timerActionForm.setNoPass("");

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			TimerActionForm actionForm = (TimerActionForm) form;
			timerService.delete(actionForm.getId());
			setResult(true, "操作成功", request);
		}catch (Exception e) {
			setResult(false, e.getMessage(), request);
		}
		return query(mapping, form, request, response);
	}

	
	/**
	 * 查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimerActionForm actionForm = (TimerActionForm)form;
		Map<String, Object> params = getParaMap();
		params.put("beanNameCh", actionForm.getBeanNameCh());
		Paginater paginater = timerDao.getTimerList(getPager(request), params);
		saveQueryResult(request, paginater);
		return forward("/pages/admin/sysRunManager/timerList.jsp");
	}
	

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimerActionForm actionForm = (TimerActionForm) form;
		if (!StringUtils.isEmpty(actionForm.getId())) {
			Timer timer = timerDao.findById(actionForm.getId());
			BeanUtils.copyProperties(actionForm, timer);
		}
		return forward("/pages/admin/sysRunManager/timerEdit.jsp");
	}
	public ActionForward toTimerMng(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return forward("/pages/admin/sysRunManager/sysTimerManager.jsp");
	}
	
	public ActionForward doEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Timer timer = null;
			TimerActionForm actionForm = (TimerActionForm) form;
			if (StringUtils.isEmpty(actionForm.getId())) {
				timer = new Timer();
				BeanUtils.copyProperties(timer, actionForm);
				timerService.save(timer, getSessionUser(request));
			}else {
				timer = timerDao.findById(actionForm.getId());
				String createUser = timer.getCreateUser();
				Date createTime = timer.getCreateTime();
				BeanUtils.copyProperties(timer, actionForm);
				timer.setCreateTime(createTime);
				timer.setCreateUser(createUser);
				timerService.update(timer, getSessionUser(request));
			}
			setResult(true, "操作成功", request);
			clearData(actionForm);
			return query(mapping, actionForm, request, response);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return forward("/pages/admin/sysRunManager/timerEdit.jsp");
		}
	}
	
}
