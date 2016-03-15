package com.ylink.cim.admin.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.service.SysDictService;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.util.ExceptionUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class SysDictAction extends BaseAction implements ModelDriven<SysDict> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8604990429021666731L;
	@Autowired
	private SysDictService sysDictService;

	public String listSysDict() throws Exception {
		Paginater paginater = this.sysDictService.getSysDictPageList(model, super.getPager(request));
		saveQueryResult(request, paginater);

		SysDictType.setInReq(request);
		String msg = MsgUtils.r("字典管理查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return ActionConstant.TO_LIST_PAGE;
	}

	public String toAddPage() {
		SysDictType.setInReq(request);
		return ActionConstant.TO_ADD_PAGE;
	}

	public String addSysDict() throws Exception {
		try {
			if (this.sysDictService.isExist(model.getId())) {
				setResult(false, ActionMessageConstant.OPER_FAIL_EXIST, request);
				return toAddPage();
			} else {
				this.sysDictService.saveSysDict(model);
				setSucResult(ActionMessageConstant.OPER_SUCCESS, request);
			}
			String msg = MsgUtils.r("添加字典成功,添加内容为：{?}", FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return "toMain";
		} catch (Exception e) {
			ExceptionUtils.logBizException(SysDictAction.class, e.getMessage());
			// setResult(false, ActionMessageConstant.OPER_FAIL, request);
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			String msg = MsgUtils.r("添加字典失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return toAddPage();
		} 
	}

	public String deleteSysDict() throws Exception {

		try {
			this.sysDictService.deleteSysDict(model);
			setSucResult(ActionMessageConstant.OPER_SUCCESS, request);
			String msg = MsgUtils.r("删除字典成功,删除内容为：{?}", FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
		} catch (Exception e) {
			String msg = MsgUtils.r("删除字典失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logBizException(this.getClass(), e.getMessage());
			setFailResult(ActionMessageConstant.OPER_FAIL, request);
		}
		return "toMain";
	}

	@Override
	public SysDict getModel() {
		return model;
	}

	private SysDict model = new SysDict();
}
