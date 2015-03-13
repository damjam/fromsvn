package com.ylink.cim.admin.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.domain.SysParm;
import com.ylink.cim.admin.service.SysParmService;
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
 * ϵͳ��������action
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
	 * ҳ��ɾ��
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
					+ sessionUser.getUserName() + "),ɾ��ϵͳ����" + "code=" + sysParmManageActionForm.getCode() + "�ɹ�");
			request.setAttribute(Constants.OPER_INFO, Constants.DELETE_SUCCESS);
			this.clearData(sysParmManageActionForm);
			String msg = LogUtils.r("ɾ��ϵͳ�����ɹ�,ɾ������Ϊ��{?}", request, getCurPrivilegeCode(request), Constants.LOG_USER_D,
					sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),ɾ��ϵͳ����" + "code="
							+ sysParmManageActionForm.getCode() + "�ɹ�");
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return this.query(mapping, sysParmManageActionForm, request, response);
		} catch (Exception e) {
			String error = "�û�" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")ɾ��ϵͳ���� addrId=" + sysParmManageActionForm.getCode() + "ʧ��";
			logger.debug(error + ",ʧ��ԭ��:" + e.getMessage());
			String msg = LogUtils.r("ɾ��ϵͳ����ʧ��,ʧ��ԭ��:{?}������:{?}", e.getMessage(), error);
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			return this.query(mapping, sysParmManageActionForm, request, response);
		}
	}

	/**
	 * struts actionForm ת�� po
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
	 * hibernate Po ת�� struts
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
	 * ҳ���ѯ
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
		String msg = LogUtils.r("ϵͳ������ѯ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return forward("/pages/admin/sysRunManager/sysParmManager.jsp");
	}

	/**
	 * ���¼���ϵͳ����.
	 */
	public ActionForward reload(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			ParaManager.init();
			respond(response, "���¼���ϵͳ�������");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e, e);
			respond(response, "���¼���ϵͳ����ʧ��");
		}

		return null;
	}

	/**
	 * ����ϵͳ����
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
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_A, "�û�"
					+ sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),����ϵͳ���� code="
					+ sysParmManageActionForm.getCode() + "�ɹ�");
			this.clearData(sysParmManageActionForm);
			request.setAttribute(Constants.OPER_INFO, Constants.SAVE_SUCCESS);
			setReturnUrl("/sysParmManage.do?action=query", request);
			String msg = LogUtils.r("���ϵͳ�����ɹ�,��������Ϊ��{?}", FeildUtils.toString(this.getSysParmFromActionForm(request,
					sysParmManageActionForm)));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			// e.printStackTrace();
			String error = "�û�" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()

			+ ")����ϵͳ���� code" + sysParmManageActionForm.getCode() + "ʧ��";
			logger.debug(error + ",ʧ��ԭ��:" + e.getMessage());
			this.logErrorWithReason(request, getCurPrivilegeCode(request), error, e.getMessage());
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("���ϵͳ����ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return mapping.findForward("save");
		}

	}

	/**
	 * ����ϵͳ����
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
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_U, "�û�"
					+ sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),�޸�ϵͳ���� code="
					+ actionForm.getCode() + "�ɹ�");
			this.clearData(actionForm);
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_SUCCESS);
			String msg = LogUtils.r("����ϵͳ�����ɹ�,��������Ϊ��{?}", FeildUtils.toString(this.getSysParmFromActionForm(request,
					actionForm)));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return query(mapping, actionForm, request, response);
		} catch (Exception e) {
			String error = "�û�" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")�޸�ϵͳ���� code=" + actionForm.getCode() + "ʧ��";
			logger.debug(error + ",ԭ��" + e.getMessage());
			this.saveSysLog(request, getCurPrivilegeCode(request), "", Constants.LOG_SYS_S, Constants.LOG_SYS_ERROR,
					error + e.getMessage());
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_FAIL);
			String msg = LogUtils.r("����ϵͳ����ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return mapping.findForward("modify");
		}

	}

	/**
	 * ҳ���޸�
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
			String cmd = "mysqldump -h localhost -ucims -pcims cims> d:\\data_back\\cims" + today + ".sql"; // һ��Ҫ��
			// -h
			// localhost(���Ƿ�����IP��ַ)
			Process process = rt.exec("cmd /c " + cmd);
			InputStreamReader isr = new InputStreamReader(process.getErrorStream());
			LineNumberReader input = new LineNumberReader(isr);
			String line;
			while ((line = input.readLine()) != null) {
				System.out.println("������Ϣ" + line + "~~~~~~~~~~");
			}
			System.out.println("���ݳɹ�!");
			// ɾ�������ļ�
			String yesterday = DateUtil.getDateYYYYMMDD(DateUtil.addDays(DateUtil.getCurrent(), -1));
			File oldFile = new File(dir+"\\cims" + yesterday + ".sql");
			oldFile.deleteOnExit();
			jsonObject.put("tip", "���ݳɹ�");
			respond(response, jsonObject.toString());
			return null;
		} catch (IOException e) {
			System.out.println("����ʧ��!");
			e.printStackTrace();
		}

		return null;
	}
}
