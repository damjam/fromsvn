package com.ylink.cim.admin.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.domain.SysParm;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.SysParmService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

/**
 * 系统参数管理action
 */
public class SysParmManageAction extends BaseDispatchAction {

	private static final Logger logger = Logger.getLogger(LoginAction.class);

	SysParmService sysParmService = (SysParmService) getService("sysParmService");

	public void clearData(SysParmManageActionForm sysParmManageActionForm) {
		sysParmManageActionForm.setCode("");
		sysParmManageActionForm.setParname("");
		sysParmManageActionForm.setParvalue("");
		sysParmManageActionForm.setIfmodify("");
		sysParmManageActionForm.setUsercode("");
		sysParmManageActionForm.setUpdatedate("");
		sysParmManageActionForm.setRemark("");
	}

	/**
	 * 页面删除
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

		SysParmManageActionForm sysParmManageActionForm = (SysParmManageActionForm) form;
		UserInfo sessionUser = getSessionUser(request);
		try {
			sysParmService.delete(sysParmManageActionForm.getCode());
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_D, sessionUser.getUserId() + "("
					+ sessionUser.getUserName() + "),删除系统参数" + "code=" + sysParmManageActionForm.getCode() + "成功");
			request.setAttribute(Constants.OPER_INFO, Constants.DELETE_SUCCESS);
			this.clearData(sysParmManageActionForm);
			String msg = LogUtils.r("删除系统参数成功,删除内容为：{?}", request, getCurPrivilegeCode(request), Constants.LOG_USER_D,
					sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),删除系统参数" + "code="
							+ sysParmManageActionForm.getCode() + "成功");
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return this.query(mapping, sysParmManageActionForm, request, response);
		} catch (Exception e) {
			String error = "用户" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")删除系统参数 addrId=" + sysParmManageActionForm.getCode() + "失败";
			logger.debug(error + ",失败原因:" + e.getMessage());
			String msg = LogUtils.r("删除系统参数失败,失败原因:{?}，错误:{?}", e.getMessage(), error);
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			return this.query(mapping, sysParmManageActionForm, request, response);
		}
	}

	/**
	 * struts actionForm 转化 po
	 * 
	 * @param sysParmManageActionForm
	 */
	public SysParm getSysParmFromActionForm(HttpServletRequest request, SysParmManageActionForm sysParmManageActionForm) {

		SysParm sysParm = new SysParm();

		sysParm.setCode(sysParmManageActionForm.getCode());
		sysParm.setParname(sysParmManageActionForm.getParname());
		sysParm.setParvalue(sysParmManageActionForm.getParvalue());
		sysParm.setRemark(sysParmManageActionForm.getRemark());

		if (null == sysParm.getCode()) {
			sysParm.setCode("");
		}

		if (null == sysParm.getParname()) {
			sysParm.setParname("");
		}

		return sysParm;

	}

	/**
	 * hibernate Po 转化 struts
	 * 
	 * @param sysParm
	 * @return
	 */
	public SysParmManageActionForm getSysParmManageActionFormFromSysParm(SysParm sysParm) {

		SysParmManageActionForm sysParmManageActionForm = new SysParmManageActionForm();

		sysParmManageActionForm.setCode(sysParm.getCode());
		sysParmManageActionForm.setParname(sysParm.getParname());
		sysParmManageActionForm.setParvalue(sysParm.getParvalue());
		sysParmManageActionForm.setRemark(sysParm.getRemark());

		return sysParmManageActionForm;
	}

	/**
	 * 页面查询
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

		Paginater paginater = sysParmService.findAll(getPager(request), getSysParmFromActionForm(request,
				(SysParmManageActionForm) form));
		request.setAttribute("sysParmList", paginater.getList());
		request.setAttribute(Paginater.PAGINATER, paginater);
		String msg = LogUtils.r("系统参数查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return forward("/pages/admin/sysRunManager/sysParmManager.jsp");
	}

	/**
	 * 重新加载系统参数.
	 */
	public ActionForward reload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ParaManager.init();
			respond(response, "重新加载系统参数完成");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			respond(response, "重新加载系统参数失败");
		}

		return null;
	}

	/**
	 * 新增系统参数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return forward("/pages/admin/sysRunManager/sysParmAdd.jsp");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SysParmManageActionForm sysParmManageActionForm = (SysParmManageActionForm) form;
		String userCode = this.getSessionUserCode(request);
		if (null == userCode) {
			userCode = "";
		}

		UserInfo sessionUser = getSessionUser(request);
		try {
			sysParmManageActionForm.setUsercode(userCode);
			sysParmService.save(this.getSysParmFromActionForm(request, sysParmManageActionForm));
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_A, "用户"
					+ sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),新增系统参数 code="
					+ sysParmManageActionForm.getCode() + "成功");
			this.clearData(sysParmManageActionForm);
			request.setAttribute(Constants.OPER_INFO, Constants.SAVE_SUCCESS);
			setReturnUrl("/sysParmManage.do?action=query", request);
			String msg = LogUtils.r("添加系统参数成功,更新内容为：{?}", FeildUtils.toString(this.getSysParmFromActionForm(request,
					sysParmManageActionForm)));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			// e.printStackTrace();
			String error = "用户" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()

			+ ")新增系统参数 code" + sysParmManageActionForm.getCode() + "失败";
			logger.debug(error + ",失败原因:" + e.getMessage());
			this.logErrorWithReason(request, getCurPrivilegeCode(request), error, e.getMessage());
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("添加系统参数失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return mapping.findForward("save");
		}

	}

	/**
	 * 新增系统参数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SysParmManageActionForm actionForm = (SysParmManageActionForm) form;
		String userCode = this.getSessionUserCode(request);
		UserInfo sessionUser = getSessionUser(request);
		if (null == userCode) {
			userCode = "";
		}

		try {
			actionForm.setUsercode(userCode);
			sysParmService.update(this.getSysParmFromActionForm(request, actionForm));
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_U, "用户"
					+ sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),修改系统参数 code="
					+ actionForm.getCode() + "成功");
			this.clearData(actionForm);
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_SUCCESS);
			String msg = LogUtils.r("更新系统参数成功,更新内容为：{?}", FeildUtils.toString(this.getSysParmFromActionForm(request,
					actionForm)));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return query(mapping, actionForm, request, response);
		} catch (Exception e) {
			String error = "用户" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")修改系统参数 code=" + actionForm.getCode() + "失败";
			logger.debug(error + ",原因：" + e.getMessage());
			this.saveSysLog(request, getCurPrivilegeCode(request), "", Constants.LOG_SYS_S, Constants.LOG_SYS_ERROR,
					error + e.getMessage());
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_FAIL);
			String msg = LogUtils.r("更新系统参数失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return mapping.findForward("modify");
		}

	}

	/**
	 * 页面修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		SysParm sysParm = sysParmService.findById(((SysParmManageActionForm) form).getCode());
		request.setAttribute("sysParm", sysParm);
		// SysParmManageActionForm sysParmManageActionForm =
		// getSysParmManageActionFormFromSysParm(sysParm);
		// BeanUtils.copyProperties(form, sysParmManageActionForm);

		return mapping.findForward("modify");
	}

	public ActionForward backupData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			JSONObject jsonObject = new JSONObject();
			String today = DateUtil.getCurrentDate();
			Runtime rt = Runtime.getRuntime();
			String dir = "D:\\data_back";
			File file = new File(dir);
			//file.make
			if (!file.exists()) {
				file.mkdir();
			}
			String cmd = "mysqldump -h localhost -ucims -pcims cims> d:\\data_back\\cims" + today + ".sql"; // 一定要加
			// -h
			// localhost(或是服务器IP地址)
			Process process = rt.exec("cmd /c " + cmd);
			InputStreamReader isr = new InputStreamReader(process.getErrorStream());
			LineNumberReader input = new LineNumberReader(isr);
			String line;
			while ((line = input.readLine()) != null) {
				System.out.println("错误信息" + line + "~~~~~~~~~~");
			}
			System.out.println("备份成功!");
			// 删除以往文件
			String yesterday = DateUtil.getDateYYYYMMDD(DateUtil.addDays(DateUtil.getCurrent(), -1));
			File oldFile = new File(dir+"\\cims" + yesterday + ".sql");
			oldFile.deleteOnExit();
			jsonObject.put("tip", "备份成功");
			respond(response, jsonObject.toString());
			return null;
		} catch (IOException e) {
			System.out.println("备份失败!");
			e.printStackTrace();
		}

		return null;
	}
}
