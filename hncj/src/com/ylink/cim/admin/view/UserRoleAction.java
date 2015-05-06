package com.ylink.cim.admin.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.LimitGroupInfoDao;
import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.UserRole;
import com.ylink.cim.admin.service.UserRoleService;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.util.BoUtils;
import flink.util.ExceptionUtils;
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class UserRoleAction extends BaseAction implements ModelDriven<UserRole> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4605507332845959645L;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private LimitGroupInfoDao limitGroupInfoDao;

	public String listUserRole() throws Exception {

		Paginater list = this.userRoleService.getUserRolePageList(model, super.getPager(request));

		saveQueryResult(request, list);
		String msg = LogUtils.r("用户角色查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return ActionConstant.TO_LIST_PAGE;
	}

	public String deleteUserRole() throws Exception {

		try {
			this.userRoleService.deleteUserRole(model.getId());
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = LogUtils.r("删除用户角色成功,删除内容为：{?}", FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return ActionConstant.TO_LIST_PAGE;
		} catch (Exception e) {
			String msg = LogUtils.r("删除用户角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logException(UserRoleAction.class, e.getMessage());
			throw e;
		}
	}

	public String toAssignRole() throws Exception {
		String userId = model.getUserId();
		List<UserRole> roles = userRoleDao.getUserRoleByUser(userId);
		if (roles.size() > 0) {
			String[] roleIds = new String[roles.size()];
			for (int i = 0; i < roles.size(); i++) {
				roleIds[i] = roles.get(i).getId().getRoleId();
			}
			model.setRoleIds(roleIds);
		}
		List<LimitGroupInfo> list = limitGroupInfoDao.findAll();
		Paginater paginater = userRoleDao.getAvailableRoles(userId, getPager(request));
		BoUtils.addProperty(paginater.getList(), "limitGroupId", "limitGroupName", list, "limitGroupId",
				"limitGroupName");
		saveQueryResult(request, paginater);
		return ActionConstant.TO_ASSIGN_ROLE_PAGE;
	}

	public String assignRole() throws Exception {
		try {
			if (!isValidKey(request)) {
				return toAssignRole();
			}
			this.userRoleService.saveUserRole(model.getUserId(), model.getRoleIds());
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = LogUtils.r("添加用户角色成功,添加内容为：{?}",
					"[UserId=" + model.getUserId() + ",RoleIds=" + FeildUtils.arrayToString(model.getRoleIds()));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return this.toAssignRole();
		} catch (Exception e) {
			String msg = LogUtils.r("添加用户角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			throw e;
		}
	}

	public UserRole getModel() {
		return model;
	}

	private UserRole model = new UserRole();

}
