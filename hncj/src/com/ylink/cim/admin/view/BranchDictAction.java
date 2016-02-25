package com.ylink.cim.admin.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.BranchDictDao;
import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.service.SysDictService;
import com.ylink.cim.common.type.BranchDictType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.util.ExceptionUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class BranchDictAction extends BaseAction implements ModelDriven<SysDict> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8604990429021666731L;
	@Autowired
	private SysDictService sysDictService;
	@Autowired
	private BranchDictDao branchDictDao;
	public String listSysDict() throws Exception {
		Paginater paginater = this.sysDictService.getSysDictPageList(model, super.getPager(request));
		saveQueryResult(request, paginater);

		SysDictType.setInReq(request);
		String msg = MsgUtils.r("�ֵ�����ѯ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return ActionConstant.TO_LIST_PAGE;
	}

	public String toAddPage() {
		BranchDictType.setInReq(request);
		request.setAttribute("branches", ParaManager.getSysDict(SysDictType.BranchType.getValue()));
		return ActionConstant.TO_ADD_PAGE;
	}

	public String addSysDict() throws Exception {
		try {
			if (this.sysDictService.isExist(model.getId())) {
				setResult(false, ActionMessageConstant.OPER_FAIL_EXIST, request);
			} else {
				this.sysDictService.saveSysDict(model);
				setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			}
			String msg = MsgUtils.r("����ֵ�ɹ�,�������Ϊ��{?}", FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return this.toAddPage();
		} catch (Exception e) {
			ExceptionUtils.logBizException(BranchDictAction.class, e.getMessage());
			// setResult(false, ActionMessageConstant.OPER_FAIL, request);
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			String msg = MsgUtils.r("����ֵ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			throw e;
		}
	}

	public String delete() throws Exception {

		try {
			this.sysDictService.deleteSysDict(model);

			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			model.getId().setDictValue(null);
			model.setDictName(null);
			String msg = MsgUtils.r("ɾ���ֵ�ɹ�,ɾ������Ϊ��{?}", FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return this.listSysDict();
		} catch (Exception e) {
			String msg = MsgUtils.r("ɾ���ֵ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logBizException(this.getClass(), e.getMessage());
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			throw e;

		}
	}

	@Override
	public SysDict getModel() {
		return model;
	}

	private SysDict model = new SysDict();
}
