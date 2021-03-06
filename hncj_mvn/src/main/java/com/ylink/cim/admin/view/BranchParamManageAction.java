package com.ylink.cim.admin.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.BranchParmDao;
import com.ylink.cim.admin.domain.BranchParam;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.BranchParmService;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FieldUtils;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;
import net.sf.json.JSONObject;

/**
 * 系统参数管理action
 */
@Scope("prototype")
@Component 
public class BranchParamManageAction extends BaseAction implements ModelDriven<BranchParam> {

	private static final long serialVersionUID = 3419813256406264742L;

	private static final Logger logger = Logger.getLogger(BranchParamManageAction.class);

	@Autowired
	BranchParmService branchParmService;
	@Autowired
	BranchParmDao branchParmDao;
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
	public String delete() throws Exception {

		UserInfo sessionUser = getSessionUser(request);
		try {
			branchParmService.delete(model.getCode());
			this.saveUserLog(request, getCurPrivilegeCode(request), Constants.LOG_USER_D, sessionUser.getUserId() + "("
					+ sessionUser.getUserName() + "),删除系统参数" + "code=" + model.getCode() + "成功");
			request.setAttribute(Constants.OPER_INFO, Constants.DELETE_SUCCESS);
			String msg = MsgUtils.r("删除系统参数成功,删除内容为：{?}", request, getCurPrivilegeCode(request), Constants.LOG_USER_D,
					sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),删除系统参数" + "code=" + model.getCode()
							+ "成功");
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			setSucResult("操作成功", request);
			return "toMain";
		} catch (Exception e) {
			String error = "用户" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")删除系统参数 addrId=" + model.getCode() + "失败";
			logger.debug(error + ",失败原因:" + e.getMessage());
			String msg = MsgUtils.r("删除系统参数失败,失败原因:{?}，错误:{?}", e.getMessage(), error);
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			return this.query();
		}
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
	public String query() throws Exception {
		Paginater paginater = branchParmService.findAll(getPager(request), model);
		for(int i=0; i<paginater.getList().size(); i++){
			BranchParam branchParm = (BranchParam)paginater.getList().get(i);
			branchParm.setBranchName(ParaManager.getSysDictName(SysDictType.BranchType.getValue(), branchParm.getBranchNo()));
		}
		saveQueryResult(request, paginater);
		String msg = MsgUtils.r("机构参数查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		request.setAttribute("branches", ParaManager.getBranches(false));
		return "list";
	}

	/**
	 * 重新加载系统参数.
	 */
	public String reload() throws Exception {
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
	public String toAdd() throws Exception {
		request.setAttribute("branches", ParaManager.getBranches(false));
		return "add";
	}
	public String toUpdate() throws Exception {
		request.setAttribute("branches", ParaManager.getBranches(false));
		BranchParam branchParam = branchParmDao.findById(model.getCode());
		BeanUtils.copyProperties(model, branchParam);
		return "modify";
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
			if (StringUtils.isEmpty(model.getBranchNo())) {
				model.setBranchNo(getSessionBranchNo(request));
			}
			branchParmService.save(model);
			this.saveUserLog(
					request,
					getCurPrivilegeCode(request),
					Constants.LOG_USER_A,
					"用户" + sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),新增系统参数 code="
							+ model.getCode() + "成功");
			request.setAttribute(Constants.OPER_INFO, Constants.SAVE_SUCCESS);
			setReturnUrl("/sysParmManage.do?action=query", request);
			String msg = MsgUtils.r("添加系统参数成功,更新内容为：{?}", FieldUtils.toString(model));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			setSucResult("操作成功", request);
			return "toMain";
		} catch (BizException e) {
			// e.printStackTrace();
			String error = "用户" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()

			+ ")新增系统参数 code" + model.getCode() + "失败";
			logger.debug(error + ",失败原因:" + e.getMessage());
			this.logErrorWithReason(request, getCurPrivilegeCode(request), error, e.getMessage());
			setResult(false, e.getMessage(), request);
			String msg = MsgUtils.r("添加系统参数失败,失败原因:{?}", e.getMessage());
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
			BranchParam branchParam = branchParmDao.findById(model.getCode());
			CopyPropertyUtil.copyPropertiesIgnoreNull(model, branchParam);
			branchParmService.update(branchParam);
			this.saveUserLog(
					request,
					getCurPrivilegeCode(request),
					Constants.LOG_USER_U,
					"用户" + sessionUser.getUserId() + "(" + sessionUser.getUserName() + "),修改系统参数 code="
							+ model.getCode() + "成功");
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_SUCCESS);
			String msg = MsgUtils.r("更新系统参数成功,更新内容为：{?}", FieldUtils.toString(model));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			//setSucResult("操作成功", request);
			setResult(true, "操作成功", request);
			return "toMain";
		} catch (Exception e) {
			e.printStackTrace();
			String error = "用户" + getSessionUserCode(request) + "(" + getSessionUser(request).getUserName()
					+ ")修改系统参数 code=" + model.getCode() + "失败";
			logger.debug(error + ",原因：" + e.getMessage());
			this.saveSysLog(request, getCurPrivilegeCode(request), "", Constants.LOG_SYS_ERROR, Constants.LOG_SYS_S,
					error + e.getMessage());
			request.setAttribute(Constants.OPER_INFO, Constants.UPDATE_FAIL);
			String msg = MsgUtils.r("更新系统参数失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return update();
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
	public String update() throws Exception {
		BranchParam parm = branchParmDao.findById(model.getCode());
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
			File oldFile = new File(dir + "\\cims" + yesterday + ".sql");
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

	@Override
	public BranchParam getModel() {
		return model;
	}

	private BranchParam model = new BranchParam();

	public void setModel(BranchParam model) {
		this.model = model;
	}

}
