package com.ylink.cim.admin.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.BranchDictDao;
import com.ylink.cim.admin.domain.BranchDict;
import com.ylink.cim.admin.service.BranchDictService;
import com.ylink.cim.common.type.BranchDictType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FieldUtils;
import com.ylink.cim.common.util.ParaManager;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.etc.BizException;
import flink.util.ExceptionUtils;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class BranchDictAction extends BaseAction implements ModelDriven<BranchDict> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8604990429021666731L;
	@Autowired
	private BranchDictService branchDictService;
	@Autowired
	private BranchDictDao branchDictDao;
	public String list() throws Exception {
		Paginater paginater = this.branchDictDao.getDictPageList(model, super.getPager(request));
		for(int i=0; i<paginater.getList().size(); i++){
			BranchDict branchDict = (BranchDict)paginater.getList().get(i);
			branchDict.setBranchName(ParaManager.getSysDictName(SysDictType.BranchType.getValue(), branchDict.getId().getBranchNo()));
		}
		saveQueryResult(request, paginater);
		request.setAttribute("branches", ParaManager.getBranches(false));
		BranchDictType.setInReq(request);
		String msg = MsgUtils.r("字典管理查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return ActionConstant.TO_LIST_PAGE;
	}

	public String toAdd() {
		BranchDictType.setInReq(request);
		request.setAttribute("branches", ParaManager.getBranches(false));
		return ActionConstant.TO_ADD_PAGE;
	}

	public String doAdd() throws Exception {
		try {
			if (this.branchDictService.isExist(model.getId())) {
				throw new BizException(ActionMessageConstant.OPER_FAIL_EXIST);
			} else {
				this.branchDictService.saveDict(model);
				setSucResult(ActionMessageConstant.OPER_SUCCESS, request);
			}
			String msg = MsgUtils.r("添加字典成功,添加内容为：{?}", FieldUtils.toString(model));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return "toMain";
		} catch (BizException e){
			setResult(false, e.getMessage(), request);
			return toAdd();
		} catch (Exception e) {
			e.printStackTrace();
			// setResult(false, ActionMessageConstant.OPER_FAIL, request);
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			String msg = MsgUtils.r("添加字典失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return toAdd();
		}
	}

	public String delete() throws Exception {
		try {
			this.branchDictService.deleteBranchDict(model);
			setSucResult(ActionMessageConstant.OPER_SUCCESS, request);
			String msg = MsgUtils.r("删除字典成功,删除内容为：{?}", FieldUtils.toString(model));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return "toMain";
		} catch (Exception e) {
			e.printStackTrace();
			String msg = MsgUtils.r("删除字典失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logBizException(this.getClass(), e.getMessage());
			setFailResult(ActionMessageConstant.OPER_FAIL, request);
			throw e;
		}
	}

	@Override
	public BranchDict getModel() {
		return model;
	}

	private BranchDict model = new BranchDict();
}
