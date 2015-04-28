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

import com.ylink.cim.admin.domain.BranchParm;
import com.ylink.cim.admin.service.BranchParmService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.user.domain.UserInfo;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

/**
 * 系统参数管理action
 */
public class BranchParmManageAction extends BaseDispatchAction {

	private static final Logger logger = Logger.getLogger(LoginAction.class);

	BranchParmService branchParmService = (BranchParmService) getService("branchParmService");

	public void clearData(BranchParmManageActionForm branchParmManageActionForm) {
		branchParmManageActionForm.setCode("");
		branchParmManageActionForm.setParname("");
		branchParmManageActionForm.setParvalue("");
		branchParmManageActionForm.setIfmodify("");
		branchParmManageActionForm.setUsercode("");
		branchParmManageActionForm.setUpdatedate("");
		branchParmManageActionForm.setRemark("");
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

		BranchParmManageActionForm branchParmManageActionForm = (BranchParmManageActionForm) form;
		UserInfo sessionUser = getSessionUser(request);
		try {
			branchParmService.delete(branchParmManageActionForm.getCode());
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_D, sessionUser.getUserId() + "("
					+ sessionUser.getUserName() + "),删除系统参数" + "code=" + branchParmManageActionForm.getCode() + "成功");
			request.setAttribute(Constants.OPER_INFO, Constants.DELETE_SUCCESS);
			this.clearData(branchParmManageActionForm);
			String msg = LogUtils.r("删除系统参数成功,删除内容为：{?}", request, getCurPrivilegeCode(request), Constants.LOG_USER_D,
					sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),删除系统参数" + "code="
							+ branchParmManageActionForm.getCode() + "成功");
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return this.query(mapping, branchParmManageActionForm, request, response);
		} catch (Exception e) {
			String error = "用户" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")删除系统参数 addrId=" + branchParmManageActionForm.getCode() + "失败";
			logger.debug(error + ",失败原因:" + e.getMessage());
			String msg = LogUtils.r("删除系统参数失败,失败原因:{?}，错误:{?}", e.getMessage(), error);
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			return this.query(mapping, branchParmManageActionForm, request, response);
		}
	}

	/**
	 * struts actionForm 转化 po
	 * 
	 * @param branchParmManageActionForm
	 */
	public BranchParm getBranchParmFromActionForm(HttpServletRequest request, BranchParmManageActionForm branchParmManageActionForm) {

		BranchParm branchParm = new BranchParm();

		branchParm.setCode(branchParmManageActionForm.getCode());
		branchParm.setParname(branchParmManageActionForm.getParname());
		branchParm.setParvalue(branchParmManageActionForm.getParvalue());
		branchParm.setRemark(branchParmManageActionForm.getRemark());
		branchParm.setBranchNo(getSessionBranchNo(request));
		if (null == branchParm.getCode()) {
			branchParm.setCode("");
		}

		if (null == branchParm.getParname()) {
			branchParm.setParname("");
		}

		return branchParm;

	}

	/**
	 * hibernate Po 转化 struts
	 * 
	 * @param branchParm
	 * @return
	 */
	public BranchParmManageActionForm getBranchParmManageActionFormFromBranchParm(BranchParm branchParm) {

		BranchParmManageActionForm branchParmManageActionForm = new BranchParmManageActionForm();

		branchParmManageActionForm.setCode(branchParm.getCode());
		branchParmManageActionForm.setParname(branchParm.getParname());
		branchParmManageActionForm.setParvalue(branchParm.getParvalue());
		branchParmManageActionForm.setRemark(branchParm.getRemark());

		return branchParmManageActionForm;
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

		Paginater paginater = branchParmService.findAll(getPager(request), getBranchParmFromActionForm(request,
				(BranchParmManageActionForm) form));
		request.setAttribute("branchParmList", paginater.getList());
		request.setAttribute(Paginater.PAGINATER, paginater);
		String msg = LogUtils.r("系统参数查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return forward("/pages/admin/branchParam/branchParmManager.jsp");
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

		return forward("/pages/admin/branchParam/branchParmAdd.jsp");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BranchParmManageActionForm branchParmManageActionForm = (BranchParmManageActionForm) form;
		String userCode = this.getSessionUserCode(request);
		if (null == userCode) {
			userCode = "";
		}

		UserInfo sessionUser = getSessionUser(request);
		try {
			branchParmManageActionForm.setUsercode(userCode);
			branchParmService.save(this.getBranchParmFromActionForm(request, branchParmManageActionForm));
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_A, "用户"
					+ sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),新增系统参数 code="
					+ branchParmManageActionForm.getCode() + "成功");
			this.clearData(branchParmManageActionForm);
			request.setAttribute(Constants.OPER_INFO, Constants.SAVE_SUCCESS);
			setReturnUrl("/branchParmManage.do?action=query", request);
			String msg = LogUtils.r("添加系统参数成功,更新内容为：{?}", FeildUtils.toString(this.getBranchParmFromActionForm(request,
					branchParmManageActionForm)));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			// e.printStackTrace();
			String error = "用户" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()

			+ ")新增系统参数 code" + branchParmManageActionForm.getCode() + "失败";
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

		BranchParmManageActionForm actionForm = (BranchParmManageActionForm) form;
		String userCode = this.getSessionUserCode(request);
		UserInfo sessionUser = getSessionUser(request);
		if (null == userCode) {
			userCode = "";
		}

		try {
			actionForm.setUsercode(userCode);
			branchParmService.update(this.getBranchParmFromActionForm(request, actionForm));
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_U, "用户"
					+ sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),修改系统参数 code="
					+ actionForm.getCode() + "成功");
			this.clearData(actionForm);
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_SUCCESS);
			String msg = LogUtils.r("更新系统参数成功,更新内容为：{?}", FeildUtils.toString(this.getBranchParmFromActionForm(request,
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

		BranchParm branchParm = branchParmService.findById(((BranchParmManageActionForm) form).getCode());
		request.setAttribute("branchParm", branchParm);
		// BranchParmManageActionForm branchParmManageActionForm =
		// getBranchParmManageActionFormFromBranchParm(branchParm);
		// BeanUtils.copyProperties(form, branchParmManageActionForm);

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
