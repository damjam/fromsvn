package com.ylink.cim.admin.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.service.PrivilegeResourceService;
import com.ylink.cim.admin.service.PrivilegeService;

import flink.consant.ActionConstant;
import flink.util.ExceptionUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class PrivilegeResourceAction extends BaseDispatchAction{

	private PrivilegeResourceService privilegeResourceService=(PrivilegeResourceService)getService("privilegeResourceService");
	private PrivilegeService privilegeService=(PrivilegeService)getService("privilegeService");
	
	public ActionForward listPrivilegeResource(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		PrivilegeResourceActionForm privilegeResourceActionForm=(PrivilegeResourceActionForm)form;
		
		PrivilegeResource privilegeResource=this.getPrivilegeResourceBy(privilegeResourceActionForm);
		Paginater paginater = this.privilegeResourceService.getPrivilegeResourcePageList(privilegeResource,super.getPager(request));
		saveQueryResult(request, paginater);
		
		//获取权限名称
		Privilege privilege = this.privilegeService.getPrivilege(privilegeResourceActionForm.getLimitId());
		if(null!=privilege){
			privilegeResourceActionForm.setLimitName(privilege.getLimitName());
		}
		
		return mapping.findForward(ActionConstant.TO_LIST_PAGE);
	}

	private PrivilegeResource getPrivilegeResourceBy(
			PrivilegeResourceActionForm privilegeResourceActionForm) throws Exception {
		
		PrivilegeResource privilegeResource=new PrivilegeResource();
		BeanUtils.copyProperties(privilegeResource, privilegeResourceActionForm);
		
		if(null==privilegeResourceActionForm.getId()){
			privilegeResource.setId(null);
		}
		
		return privilegeResource;
	}
	
	public ActionForward toAddPage(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		PrivilegeResourceActionForm privilegeResourceActionForm=(PrivilegeResourceActionForm)form;
		//Privilege privilege = this.privilegeService.getPrivilege(privilegeResourceActionForm.getLimitId());
		//privilegeResourceActionForm.setLimitName(privilege.getLimitName());
		
		return mapping.findForward(ActionConstant.TO_ADD_PAGE);
		
	}
	
	
	public ActionForward addPrivilegeResource(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
		
			PrivilegeResource privilegeResource = this.getPrivilegeResourceBy((PrivilegeResourceActionForm)form);
			//privilegeResource.setId(Long.valueOf(IdFactoryHelper.getId(IdFactoryConstant.PRIVILEG_ERESOURCE_ID.getValue())));
			this.privilegeResourceService.savePrivilegeResource(privilegeResource);
			setResult(true, "新增成功", request);
			return mapping.findForward(ActionConstant.TO_ADD_PAGE);
		}catch (Exception e) {
			ExceptionUtils.logException(PrivilegeResource.class, e.getMessage());
			throw e;
		}
		
	}
	
	public ActionForward deletePrivilegeResource(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		PrivilegeResourceActionForm privilegeResourceActionForm=(PrivilegeResourceActionForm)form;
		
		PrivilegeResource privilegeResource = this.privilegeResourceService.getPrivilegeResource(privilegeResourceActionForm.getId());
		Privilege privilege = this.privilegeService.getPrivilege(privilegeResource.getLimitId());
		
		privilegeResourceActionForm.setLimitId(privilege.getLimitId());
		privilegeResourceActionForm.setLimitName(privilege.getLimitName());
		privilegeResourceActionForm.setId(null);
		
		this.privilegeResourceService.deletePrivilegeResource(privilegeResource);
		
		
		return this.listPrivilegeResource(mapping, privilegeResourceActionForm, request, response);
	}
}
