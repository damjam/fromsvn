package com.ylink.cim.admin.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.domain.SysDictId;
import com.ylink.cim.admin.service.SysDictService;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.util.ExceptionUtils;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class SysDictAction extends BaseDispatchAction {

	//int i=0;
	private SysDictService sysDictService=(SysDictService)getService("sysDictService");;
	
	
	public ActionForward listSysDict(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		SysDictActionForm sysDictActionForm =(SysDictActionForm)form;
		SysDict sysDict=this.getSysDictBy(sysDictActionForm);
		Paginater paginater = this.sysDictService.getSysDictPageList(sysDict, super.getPager(request));
		saveQueryResult(request, paginater);
		
		SysDictType.setInReq(request);
		String msg = LogUtils.r("字典管理查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return mapping.findForward(ActionConstant.TO_LIST_PAGE);
	}


	private SysDict getSysDictBy(SysDictActionForm sysDictActionForm) {
		
		SysDictId id=new SysDictId();
		id.setDictType(sysDictActionForm.getDictType());
		id.setDictValue(sysDictActionForm.getDictValue());
		
		SysDict sysDict=new SysDict();
		sysDict.setDictName(sysDictActionForm.getDictName());
		sysDict.setRemark(sysDictActionForm.getRemark());
		
		sysDict.setId(id);
		
		return sysDict;
	}
	
	public ActionForward toAddPage(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response){
		SysDictType.setInReq(request);
		return mapping.findForward(ActionConstant.TO_ADD_PAGE);
	}
	
	public ActionForward addSysDict(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			
			SysDictActionForm sysDictActionForm=(SysDictActionForm)form;
			SysDict sysDict = this.getSysDictBy(sysDictActionForm);
			
			if(this.sysDictService.isExist(sysDict.getId())){
				setResult(false, ActionMessageConstant.OPER_FAIL_EXIST, request);
			}else{
				this.sysDictService.saveSysDict(sysDict);
				setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			}
			String msg = LogUtils.r("添加字典成功,添加内容为：{?}",FeildUtils.toString(sysDict));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return this.toAddPage(mapping, sysDictActionForm, request, response);
		}catch (Exception e) {
			ExceptionUtils.logBizException(SysDictAction.class, e.getMessage());
//			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			String msg = LogUtils.r("添加字典失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			throw e;
		}
	}
	
	public ActionForward deleteSysDict(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			
			SysDictActionForm sysDictActionForm=(SysDictActionForm)form;
			SysDict sysDict = this.getSysDictBy(sysDictActionForm);
			this.sysDictService.deleteSysDict(sysDict);
			
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			
			sysDictActionForm.setDictValue(null);
			sysDictActionForm.setDictName(null);
			String msg = LogUtils.r("删除字典成功,删除内容为：{?}",FeildUtils.toString(sysDict));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return this.listSysDict(mapping, sysDictActionForm, request, response);
		}catch (Exception e) {
			String msg = LogUtils.r("删除字典失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logBizException(this.getClass(), e.getMessage());
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			throw e;

		}
	}
	
	
}
