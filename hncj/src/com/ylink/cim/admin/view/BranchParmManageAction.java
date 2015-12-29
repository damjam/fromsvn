package com.ylink.cim.admin.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.BranchParmDao;
import com.ylink.cim.admin.domain.BranchParm;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.BranchParmService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

/**
 * ϵͳ��������action
 */
@Scope("prototype")
@Component 
public class BranchParmManageAction extends BaseAction implements ModelDriven<BranchParm> {

	private static final long serialVersionUID = 3419813256406264742L;

	private static final Logger logger = Logger.getLogger(BranchParmManageAction.class);

	@Autowired
	BranchParmService branchParmService;
	@Autowired
	BranchParmDao branchParmDao;
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
	public String delete() throws Exception {

		UserInfo sessionUser = getSessionUser(request);
		try {
			branchParmService.delete(model.getCode());
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_D, sessionUser.getUserId() + "("
					+ sessionUser.getUserName() + "),ɾ��ϵͳ����" + "code=" + model.getCode() + "�ɹ�");
			request.setAttribute(Constants.OPER_INFO, Constants.DELETE_SUCCESS);
			String msg = MsgUtils.r("ɾ��ϵͳ�����ɹ�,ɾ������Ϊ��{?}", request, getCurPrivilegeCode(request), Constants.LOG_USER_D,
					sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),ɾ��ϵͳ����" + "code=" + model.getCode()
							+ "�ɹ�");
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			model.setCode(null);
			return this.query();
		} catch (Exception e) {
			String error = "�û�" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")ɾ��ϵͳ���� addrId=" + model.getCode() + "ʧ��";
			logger.debug(error + ",ʧ��ԭ��:" + e.getMessage());
			String msg = MsgUtils.r("ɾ��ϵͳ����ʧ��,ʧ��ԭ��:{?}������:{?}", e.getMessage(), error);
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			return this.query();
		}
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
	public String query() throws Exception {

		Paginater paginater = branchParmService.findAll(getPager(request), model);
		saveQueryResult(request, paginater);
		String msg = MsgUtils.r("ϵͳ������ѯ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return "list";
		// "/pages/admin/sysRunManager/sysParmManager.jsp"
	}

	/**
	 * ���¼���ϵͳ����.
	 */
	public String reload() throws Exception {
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
	public String toAdd() throws Exception {
		return "add";
		// "/pages/admin/sysRunManager/sysParmAdd.jsp";
	}

	public String save() throws Exception {
		String userCode = this.getSessionUserCode(request);
		if (null == userCode) {
			userCode = "";
		}
		UserInfo sessionUser = getSessionUser(request);
		try {
			if (!isValidToken()) {
				return toAdd();
			}
			branchParmService.save(model);
			this.saveUserLog(
					request,
					getCurPrivilegeCode(request),
					Constants.LOG_USER_A,
					"�û�" + sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),����ϵͳ���� code="
							+ model.getCode() + "�ɹ�");
			request.setAttribute(Constants.OPER_INFO, Constants.SAVE_SUCCESS);
			setReturnUrl("/sysParmManage.do?action=query", request);
			String msg = MsgUtils.r("���ϵͳ�����ɹ�,��������Ϊ��{?}", FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			setSucResult("�����ɹ�", request);
			return "toMain";
		} catch (BizException e) {
			// e.printStackTrace();
			String error = "�û�" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()

			+ ")����ϵͳ���� code" + model.getCode() + "ʧ��";
			logger.debug(error + ",ʧ��ԭ��:" + e.getMessage());
			this.logErrorWithReason(request, getCurPrivilegeCode(request), error, e.getMessage());
			setResult(false, e.getMessage(), request);
			String msg = MsgUtils.r("���ϵͳ����ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			setResult(false, e.getMessage(), request);
			return toAdd();
		}

	}

	
	public String saveUpdate() throws Exception {
		String userCode = this.getSessionUserCode(request);
		UserInfo sessionUser = getSessionUser(request);
		if (null == userCode) {
			userCode = "";
		}
		try {
			branchParmService.update(model);
			this.saveUserLog(
					request,
					getCurPrivilegeCode(request),
					Constants.LOG_USER_U,
					"�û�" + sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),�޸�ϵͳ���� code="
							+ model.getCode() + "�ɹ�");
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_SUCCESS);
			String msg = MsgUtils.r("����ϵͳ�����ɹ�,��������Ϊ��{?}", FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			setSucResult("�����ɹ�", request);
			return "toMain";
		} catch (Exception e) {
			String error = "�û�" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")�޸�ϵͳ���� code=" + model.getCode() + "ʧ��";
			logger.debug(error + ",ԭ��" + e.getMessage());
			this.saveSysLog(request, getCurPrivilegeCode(request), "", Constants.LOG_SYS_S, Constants.LOG_SYS_ERROR,
					error + e.getMessage());
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_FAIL);
			String msg = MsgUtils.r("����ϵͳ����ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return update();
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
	public String update() throws Exception {
		BranchParm parm = branchParmDao.findById(model.getCode());
		BeanUtils.copyProperties(model, parm);
		return "modify";
	}

	public String backupData() throws Exception {
		try {
			JSONObject jsonObject = new JSONObject();
			String today = DateUtil.getCurrentDate();
			Runtime rt = Runtime.getRuntime();
			String dir = "D:\\data_back";
			File file = new File(dir);
			// file.make
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
			File oldFile = new File(dir + "\\cims" + yesterday + ".sql");
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

	@Override
	public BranchParm getModel() {
		return model;
	}

	private BranchParm model = new BranchParm();

	public void setModel(BranchParm model) {
		this.model = model;
	}

}
