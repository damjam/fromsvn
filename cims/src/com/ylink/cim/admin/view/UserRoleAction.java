package com.ylink.cim.admin.view;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.admin.dao.LimitGroupInfoDao;
import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.RoleInfo;
import com.ylink.cim.admin.domain.UserRole;
import com.ylink.cim.admin.domain.UserRoleId;
import com.ylink.cim.admin.service.UserRoleService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.util.BoUtils;
import flink.util.ExceptionUtils;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class UserRoleAction extends BaseDispatchAction {

	private UserRoleService userRoleService=(UserRoleService)getService("userRoleService");
	private UserRoleDao userRoleDao = (UserRoleDao)getService("userRoleDao");
	private LimitGroupInfoDao limitGroupInfoDao = (LimitGroupInfoDao)getService("limitGroupInfoDao");
	public ActionForward listUserRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		UserRoleActionForm userRoleActionForm=(UserRoleActionForm)form;
		UserRole userRole=this.getUserRoleBy(userRoleActionForm);
		Paginater list = this.userRoleService.getUserRolePageList(userRole, super.getPager(request));
		
		saveQueryResult(request, list);
		String msg = LogUtils.r("用户角色查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return mapping.findForward(ActionConstant.TO_LIST_PAGE);
	}

	private UserRole getUserRoleBy(UserRoleActionForm userRoleActionForm) {
		
		UserRoleId id=new UserRoleId();
		id.setUserId(userRoleActionForm.getUserId());
		id.setRoleId(userRoleActionForm.getRoleId());
		
		UserRole userRole=new UserRole();
		userRole.setId(id);
		userRole.setUserName(userRoleActionForm.getUserName());
		userRole.setUserType(userRoleActionForm.getUserType());
		userRole.setLoginId(userRoleActionForm.getLoginId());
		userRole.setRoleName(userRoleActionForm.getRoleName());
		
		return userRole;
	}
	
	public ActionForward deleteUserRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			UserRoleActionForm userRoleActionForm=(UserRoleActionForm)form;
			UserRole userRole = this.getUserRoleBy(userRoleActionForm);
			
			this.userRoleService.deleteUserRole(userRole.getId());
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = LogUtils.r("删除用户角色成功,删除内容为：{?}",FeildUtils.toString(userRole));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return mapping.findForward(ActionConstant.TO_LIST_PAGE);
		}catch (Exception e) {
			String msg = LogUtils.r("删除用户角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logException(UserRoleAction.class, e.getMessage());
			throw e;
		}
	}
	
	public ActionForward toAssignRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		UserRoleActionForm userInfoActionForm =(UserRoleActionForm)form;
		
		String userId = userInfoActionForm.getUserId();
		List<UserRole> roles = userRoleDao.getUserRoleByUser(userId);
		if (roles.size() > 0) {
			String[] roleIds = new String[roles.size()];
			for (int i = 0; i < roles.size(); i++) {
				roleIds[i] = roles.get(i).getId().getRoleId();
			}
			userInfoActionForm.setRoleIds(roleIds);
		}
		List<LimitGroupInfo> list = limitGroupInfoDao.findAll();
		Paginater paginater = userRoleDao.getAvailableRoles(userId, getPager(request));
		BoUtils.addProperty(paginater.getList(), "limitGroupId", "limitGroupName", list, "limitGroupId", "limitGroupName");
		saveQueryResult(request,paginater);
		return mapping.findForward(ActionConstant.TO_ASSIGN_ROLE_PAGE);
	}
	
	public ActionForward assignRole(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			if (!isValidKey(request)) {
				return toAssignRole(mapping, form, request, response);
			}
			UserRoleActionForm userRoleActionForm=(UserRoleActionForm)form;
			this.userRoleService.saveUserRole(userRoleActionForm.getUserId(), userRoleActionForm.getRoleIds());
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = LogUtils.r("添加用户角色成功,添加内容为：{?}","[UserId="+userRoleActionForm.getUserId()+",RoleIds="+ FeildUtils.arrayToString(userRoleActionForm.getRoleIds()));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return this.toAssignRole(mapping, userRoleActionForm, request, response);
		}catch (Exception e) {
			String msg = LogUtils.r("添加用户角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			throw e;
		}
	}
}
