 
package com.ylink.cim.admin.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.dao.LimitGroupInfoDao;
import com.ylink.cim.admin.dao.RoleInfoDao;
import com.ylink.cim.admin.dao.RolePrivilegeDao;
import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.domain.LimitGroup;
import com.ylink.cim.admin.domain.LimitGroupId;
import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;
import com.ylink.cim.admin.domain.RoleInfo;
import com.ylink.cim.admin.domain.RolePrivilege;
import com.ylink.cim.admin.service.LimitGroupInfoService;
import com.ylink.cim.admin.service.LimitGroupService;
import com.ylink.cim.admin.service.PrivilegeService;
import com.ylink.cim.admin.service.RoleInfoService;
import com.ylink.cim.admin.service.RolePrivilegeService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.ExceptionUtils;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;
import flink.web.tag.DTreeObj;

public class RoleInfoAction extends BaseDispatchAction{
	
	private RoleInfoService roleInfoService=(RoleInfoService)getService("roleInfoService");
	private LimitGroupInfoService limitGroupInfoService=(LimitGroupInfoService)getService("limitGroupInfoService");
	private LimitGroupService limitGroupService=(LimitGroupService)getService("limitGroupService");
	private RolePrivilegeService rolePrivilegeService=(RolePrivilegeService)getService("rolePrivilegeService");
	private LimitGroupInfoDao limitGroupInfoDao = (LimitGroupInfoDao)getService("limitGroupInfoDao");
	private PrivilegeService privilegeService = (PrivilegeService)getService("privilegeService");
	private RolePrivilegeDao rolePrivilegeDao = (RolePrivilegeDao)getService("rolePrivilegeDao");
	private RoleInfoDao roleInfoDao = (RoleInfoDao)getService("roleInfoDao");
	private UserRoleDao userRoleDao = (UserRoleDao)getService("userRoleDao");
	
	public ActionForward listRoleInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		RoleInfoActionForm roleInfoActionForm=(RoleInfoActionForm)form;
		
		RoleInfo roleInfo=this.getRoleInfoBy(roleInfoActionForm);
		Paginater paginater = this.roleInfoService.getRoleInfoPageList(roleInfo, super.getPager(request));

		saveQueryResult(request, paginater);
		String msg = LogUtils.r("角色管理查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		
		return mapping.findForward(ActionConstant.TO_LIST_PAGE);
	}

	private RoleInfo getRoleInfoBy(RoleInfoActionForm roleInfoActionForm) throws Exception {
		
		RoleInfo roleInfo=new RoleInfo();
		BeanUtils.copyProperties(roleInfo, roleInfoActionForm);
		if (StringUtils.isEmpty(roleInfo.getRoleId())) {
			roleInfo.setRoleId(idFactoryService.generateId(Constants.ROLE_INFO_ID));
		}
		return roleInfo;
	}
	
	public ActionForward toAddPage(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		RoleInfoActionForm roleInfoActionForm=(RoleInfoActionForm)form;
		
		this.initLimitGroupInfo(request);
		this.initLimitGroup(request,roleInfoActionForm.getLimitGroupId());
		initDtreeSelect(roleInfoActionForm.getRoleId(),request);
		
		return mapping.findForward(ActionConstant.TO_ADD_PAGE);
	}
	
	public ActionForward addRoleInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		RoleInfoActionForm roleInfoActionForm=(RoleInfoActionForm)form;
		try{
			if (!isValidKey(request)) {
				return this.toAddPage(mapping, form, request, response);
			}
			//未选中任何一条
			Assert.notEmpty(roleInfoActionForm.getLimitIds(), "权限点不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("roleName", roleInfoActionForm.getRoleName());
			Long count = roleInfoDao.findCountByParam(RoleInfo.class, params, null, null);
			Assert.isTrue(count == 0, LogUtils.r("已存在角色名称[{?}]", roleInfoActionForm.getRoleName()));
			RoleInfo roleInfo = this.getRoleInfoBy(roleInfoActionForm);
			
			if(this.roleInfoService.isExistRole(roleInfo)){
				setResult(false, ActionMessageConstant.OPER_FAIL_EXIST, request);
				return this.toAddPage(mapping, roleInfoActionForm, request, response);
			}
			
			this.roleInfoService.saveRoleAndLimits(roleInfo);
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = LogUtils.r("添加角色成功,添加内容为：{?}",FeildUtils.toString(roleInfo));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
		}
		catch(BizException e){
			String msg = LogUtils.r("添加角色失败,失败原因:{?}", e.getMessage());
			setResult(false, msg, request);
			super.logError(request, UserLogType.ADD.getValue(), msg);
		}
		catch (Exception e) {
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			String msg = LogUtils.r("添加角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
		}
		roleInfoActionForm.setLimitGroupId(null);
		roleInfoActionForm.setRoleName("");
		roleInfoActionForm.setLimitGroupName("");
		//return this.toAddPage(mapping, form, request, response);
		return listRoleInfo(mapping, roleInfoActionForm, request, response);
	} 
	
	public ActionForward toUpdatePage(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		RoleInfoActionForm roleInfoActionForm=(RoleInfoActionForm)form;
		RoleInfo roleInfo = this.roleInfoService.getRoleInfoByRoleId(roleInfoActionForm.getRoleId());
		roleInfoActionForm.setRoleName(roleInfo.getRoleName());
		roleInfoActionForm.setLimitGroupId(roleInfo.getLimitGroupId());
		roleInfoActionForm.setRemark(roleInfo.getRemark());
		this.initLimitGroupInfo(request);
		this.initLimitGroup(request,roleInfoActionForm.getLimitGroupId());
		String limitGroupId = roleInfo.getLimitGroupId();
		LimitGroupInfo lgi = limitGroupInfoDao.findById(limitGroupId);
		request.setAttribute("limitGroupName", lgi.getLimitGroupName());
		List list = privilegeService.getRoleTree(roleInfo.getLimitGroupId());

		// 取得角色权限信息
		List rolePrivilegeList = rolePrivilegeDao.getRolePrivilegeByRoleId(roleInfo.getRoleId());

		saveQueryResult(request, list);
		request.setAttribute("rolePrivilegeList", rolePrivilegeList);
		
		initDtreeSelect(roleInfoActionForm.getRoleId(),request);
		
		return mapping.findForward(ActionConstant.TO_UPDATE_PAGE);
	}

	public ActionForward updateRoleInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		RoleInfoActionForm roleInfoActionForm=(RoleInfoActionForm)form;
		try{
			RoleInfo roleInfo = this.getRoleInfoBy(roleInfoActionForm); 
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleName", roleInfoActionForm.getRoleName());
			Long count = roleInfoDao.findCountByParam(RoleInfo.class, params, "roleId", roleInfoActionForm.getRoleId());
			Assert.isTrue(count == 0, LogUtils.r("已存在角色名称[{?}]", roleInfoActionForm.getRoleName()));
			this.roleInfoService.updateRoleAndLimits(roleInfo);
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = LogUtils.r("更新角色成功,更新内容为：{?}",FeildUtils.toString(roleInfo));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return this.toUpdatePage(mapping, roleInfoActionForm, request, response);
		}
		catch(BizException e){
			setResult(true, LogUtils.r("已存在角色名称[{?}]", roleInfoActionForm.getRoleName()), request);
			String msg = LogUtils.r("更新角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return this.toUpdatePage(mapping, roleInfoActionForm, request, response);
		}
		catch (Exception e) {
//			e.printStackTrace();
			setResult(true, ActionMessageConstant.OPER_FAIL+e.getMessage(), request);
			String msg = LogUtils.r("更新角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return this.toUpdatePage(mapping, roleInfoActionForm, request, response);
		}
	}
	
	private void initDtreeSelect(String roleId, HttpServletRequest request) {
		if(null==roleId){
			return ;
		}
		
		List<RolePrivilege> list = this.rolePrivilegeService.getRolePrivilegeByRoleId(roleId);
		if(null==list || list.size()==0){
			return;
		}
		
		StringBuffer initCheck=new StringBuffer();
		for(int i=0;i<list.size();i++){
			RolePrivilege rolePrivilege = list.get(i);
			initCheck.append("$"+rolePrivilege.getId().getLimitId());
		}

		request.setAttribute("initCheck", initCheck.deleteCharAt(0).toString());
	}

	private void initLimitGroup(HttpServletRequest request,String limitGroupId) throws Exception {
		
		if(null==limitGroupId){ 
			return;
		}
		
		LimitGroupId id=new LimitGroupId();
		id.setLimitGroupId(limitGroupId);
		LimitGroup limitGroup=new LimitGroup();
		limitGroup.setId(id);
		
		List<LimitGroup> list = this.limitGroupService.getLimitGroup(limitGroup);
		convertToTree(list,request);
	}

	
	private void convertToTree(List<LimitGroup> list, HttpServletRequest request) {
		
		if(null==list || list.size()==0){
			return;
		}
		
		List<DTreeObj> dTreeObjs=new ArrayList<DTreeObj>();
		for(LimitGroup limitGroup:list){
			DTreeObj dTreeObj=new DTreeObj();
			dTreeObj.setNodeId(limitGroup.getId().getLimitId());
			dTreeObj.setNodePid(limitGroup.getPid());
			dTreeObj.setNodeLabel(limitGroup.getLimitName());
			dTreeObj.setNodeName("limitIds");
			dTreeObj.setNodeValue(limitGroup.getId().getLimitId());
			
			dTreeObjs.add(dTreeObj);
		}
		
		request.setAttribute("dTree", dTreeObjs);
	}

	public void initLimitGroupInfo(HttpServletRequest request) throws Exception{
		
		List<LimitGroupInfo> list = this.limitGroupInfoService.getAll();
		saveQueryResult(request, list, "limitGroupInfoCollections");
	}
	
	public ActionForward deleteRoleInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{

		RoleInfoActionForm roleInfoActionForm=(RoleInfoActionForm)form;
		try{
			
			if(userRoleDao.getUserRoleByRole(roleInfoActionForm.getRoleId()).size() > 0){
				setResult(false, "角色已被用户绑定，不能删除", request);
				return this.listRoleInfo(mapping, roleInfoActionForm, request, response);
			}
			RoleInfo roleInfo = roleInfoDao.findById(roleInfoActionForm.getRoleId());
			this.roleInfoService.deleteRoleInfo(roleInfo);
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			this.clearForm(roleInfoActionForm);
			String msg = LogUtils.r("删除角色成功,删除内容为：{?}",FeildUtils.toString(roleInfo));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return this.listRoleInfo(mapping, form, request, response);
		
		}catch (Exception e) {
			String msg = LogUtils.r("删除角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logException(RoleInfo.class, e.getMessage());
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			this.clearForm(roleInfoActionForm);
			
			return this.listRoleInfo(mapping, form, request, response);
		}
		
	}
	
	public void clearForm(RoleInfoActionForm roleInfoActionForm){
		
		roleInfoActionForm.setRoleId(null);
		roleInfoActionForm.setRoleName(null);
		roleInfoActionForm.setLimitGroupId(null);
		roleInfoActionForm.setLimitGroupName(null);
		roleInfoActionForm.setLimitIds(null);
		roleInfoActionForm.setLimitGroupId(null);
	
	}
	
	public ActionForward loadTree(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws BizException {

		

		// 取得页面权限组，如果为空则取登陆用户权限组
		
		String limitGroupId = (String) request.getParameter("limitGroupId");
		if (StringUtils.isEmpty(limitGroupId)) {
			saveQueryResult(request, new ArrayList());
			return mapping.findForward("loadTree");
		}
		// 获取权限组
		LimitGroupInfo lg = (LimitGroupInfo) limitGroupInfoDao.findById(limitGroupId);
		if (limitGroupId == null || limitGroupId.trim().equals("")) {
			saveQueryResult(request, new ArrayList());
			return mapping.findForward("loadTree");
		}
		// 取得权限树
		List<PrivilegeTreeNode> list = privilegeService.getRoleTree(lg.getLimitGroupId());
		request.setAttribute("limitGroupId", lg.getLimitGroupId());
		saveQueryResult(request, list);
		return mapping.findForward("loadTree");
	}
}




