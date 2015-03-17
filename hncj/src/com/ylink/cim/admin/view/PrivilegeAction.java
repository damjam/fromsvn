package com.ylink.cim.admin.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.service.PrivilegeResourceService;
import com.ylink.cim.admin.service.PrivilegeService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.Symbol;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class PrivilegeAction extends BaseDispatchAction {

	private PrivilegeService privilegeService = (PrivilegeService) getService("privilegeService");
	private PrivilegeDao privilegeDao = (PrivilegeDao) getService("privilegeDao");
	private PrivilegeResourceService privilegeResourceService = (PrivilegeResourceService)getService("privilegeResourceService");
	private List<PrivilegeActionForm> convert(Paginater paginater) throws Exception {
		List<PrivilegeActionForm> list = new ArrayList<PrivilegeActionForm>();
		if (null != paginater && null != paginater.getList()) {
			for (int i = 0; i < paginater.getList().size(); i++) {
				Object object = paginater.getList().get(i);
				list.add(this.getPrivilegeActionForm((Privilege) object));
			}
		}

		return list;
	}

	private PrivilegeActionForm getPrivilegeActionForm(Privilege privilege) throws Exception {
		PrivilegeActionForm privilegeActionForm = new PrivilegeActionForm();
		BeanUtils.copyProperties(privilegeActionForm, privilege);
		return privilegeActionForm;
	}

	private Privilege getPrivilegeByActionForm(PrivilegeActionForm privilegeActionForm) throws Exception {
		Privilege privilege = new Privilege();
		BeanUtils.copyProperties(privilege, privilegeActionForm);
		return privilege;
	}

	public ActionForward queryPopUpPrivilege(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm) form;
		Privilege privilege = this.getPrivilegeByActionForm(privilegeActionForm);
		Paginater paginater = this.privilegeService.getPrivilegePageList(privilege, getPager(request));
		List<PrivilegeActionForm> list = convert(paginater);
		request.setAttribute(Paginater.PAGINATER, paginater);
		request.setAttribute("privilegeList", list);
		String msg = LogUtils.r("权限管理查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return mapping.findForward("popUp");
	}
	//权限管理查询
	public ActionForward listPrivs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm)form;
		Paginater paginater = privilegeDao.findPrivs(privilegeActionForm, getPager(request));
		setHasSubPri(paginater);
		saveQueryResult(request, paginater);
		String msg = LogUtils.r("权限管理查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return forward("/pages/admin/privilege/privList.jsp");
	}
	private void setHasSubPri(Paginater paginater) {
		for (int i = 0; i < paginater.getList().size(); i++) {
			Privilege privilege = (Privilege)paginater.getList().get(i);
			boolean hasSubPris = privilegeDao.hasSubPris(privilege.getLimitId());
			if (hasSubPris) {
				privilege.setHasSubPris(Symbol.YES);
			}
		}
		
	}
	
	//子权限查询
	public ActionForward listPrivRes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm)form;
		Paginater paginater = privilegeDao.findPrivRes(privilegeActionForm, getPager(request));
		saveQueryResult(request, paginater);
		String msg = LogUtils.r("子权限查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return forward("/pages/admin/privilege/privResList.jsp");
	}
	
	//添加子权限
	public ActionForward toEditPrivilege(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm)form;
		if (StringUtils.isNotEmpty(privilegeActionForm.getLimitId())) {
			Privilege privilege = privilegeDao.findById(Privilege.class, privilegeActionForm.getLimitId());
			BeanUtils.copyProperties(privilegeActionForm, privilege);
		}
		return forward("/pages/admin/privilege/privEdit.jsp");
	}
	
	//添加子权限
	public ActionForward editPrivilege(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm)form;
			Privilege p = privilegeDao.findById(Privilege.class, privilegeActionForm.getLimitId());
			Assert.isNull(p, "权限ID已存在");
			p = new Privilege();
			BeanUtils.copyProperties(p, privilegeActionForm);
			Integer menuOrder = privilegeDao.getNextIndex(privilegeActionForm.getParent());
			p.setMenuOrder(menuOrder);
			p.setIfAudit(Symbol.NO);
			privilegeService.savePrivilege(p);
			setResult(true, "操作成功", request);
			privilegeActionForm.setLimitId(null);
			privilegeActionForm.setLimitName(null);
			privilegeActionForm.setParent(null);
			String msg = LogUtils.r("添加子权限成功,添加内容为：{?}",FeildUtils.toString(p));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "操作失败:"+e.getMessage(), request);
			String msg = LogUtils.r("添加子权限失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return toEditPrivilege(mapping, form, request, response);
		}
		return listPrivs(mapping, form, request, response);
	}
	
	//修改权限资源
	public ActionForward toEditPriRes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm)form;
		Long id = privilegeActionForm.getId();
		if (id != null && id != 0) {
			PrivilegeResource privilegeResource = privilegeDao.findById(PrivilegeResource.class, id);
			BeanUtils.copyProperties(privilegeActionForm, privilegeResource);
		}
		return forward("/pages/admin/privilege/privResEdit.jsp");
	}
	
	//添加权限资源
	public ActionForward editPrivRes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm)form;
			if (Symbol.YES.equals(privilegeActionForm.getIsEntry())) {
				boolean exist = privilegeDao.existPriRes(privilegeActionForm.getLimitId(), privilegeActionForm.getId());
				Assert.notTrue(exist, "入口已存在");
			}
			PrivilegeResource p = null;
			if (privilegeActionForm.getId() != null) {
				 p = privilegeDao.findById(PrivilegeResource.class, privilegeActionForm.getId());
				 BeanUtils.copyProperties(p, privilegeActionForm);
			} else {
				p = new PrivilegeResource();
				BeanUtils.copyProperties(p, privilegeActionForm);
				String id = idFactoryService.generateId(Constants.PRIVILEGE_RESOURCE_ID);
				p.setId(Long.parseLong(id));
			}
			privilegeResourceService.savePrivilegeResource(p);
			setResult(true, "操作成功", request);
			String msg = LogUtils.r("修改子权限成功,修改内容为：{?}",FeildUtils.toString(p));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "操作失败:"+e.getMessage(), request);
			e.printStackTrace();
			String msg = LogUtils.r("修改子权限失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
		}
		return toEditPriRes(mapping, form, request, response);
	}
	//删除子权限
	public ActionForward delPri(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm)form;
			Privilege p = privilegeDao.findById(Privilege.class, privilegeActionForm.getLimitId());
			privilegeDao.delete(p);
			setResult(true, "操作成功", request);
			String msg = LogUtils.r("删除子权限成功,删除的内容为：{?}",FeildUtils.toString(p));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "操作失败:"+e.getMessage(), request);
//			e.printStackTrace();
			String msg = LogUtils.r("删除子权限失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
		}
		return listPrivRes(mapping, form, request, response);
	}
	//删除子权限
	public ActionForward delPriRes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			PrivilegeActionForm privilegeActionForm = (PrivilegeActionForm)form;
			PrivilegeResource p = privilegeDao.findById(PrivilegeResource.class, privilegeActionForm.getId());
			privilegeDao.delete(p);
			setResult(true, "操作成功", request);
			String msg = LogUtils.r("删除子权限成功,删除的内容为：{?}",FeildUtils.toString(p));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
		} catch (Exception e) {
			setResult(false, "操作失败:"+e.getMessage(), request);
//			e.printStackTrace();
			String msg = LogUtils.r("删除子权限失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
		}
		return listPrivRes(mapping, form, request, response);
	}
	
}
