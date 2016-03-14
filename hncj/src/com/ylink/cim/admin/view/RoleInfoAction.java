package com.ylink.cim.admin.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.LimitGroupInfoDao;
import com.ylink.cim.admin.dao.PrivilegeDao;
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
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;
import flink.web.tag.DTreeObj;

@Scope("prototype")
@Component
public class RoleInfoAction extends BaseAction implements ModelDriven<RoleInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private LimitGroupInfoService limitGroupInfoService;
	@Autowired
	private LimitGroupService limitGroupService;
	@Autowired
	private RolePrivilegeService rolePrivilegeService;
	@Autowired
	private LimitGroupInfoDao limitGroupInfoDao;
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;
	@Autowired
	private RoleInfoDao roleInfoDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private PrivilegeDao privilegeDao;

	public String listRoleInfo() throws Exception {

		Paginater paginater = this.roleInfoService.getRoleInfoPageList(model,
				super.getPager(request));

		saveQueryResult(request, paginater);
		String msg = MsgUtils.r("角色管理查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);

		return ActionConstant.TO_LIST_PAGE;
	}

	public String toAddPage() throws Exception {

		this.initLimitGroupInfo(request);
		this.initLimitGroup(request, model.getLimitGroupId());
		initDtreeSelect(model.getRoleId(), request);
		return ActionConstant.TO_ADD_PAGE;
	}

	public String addRoleInfo() throws Exception {
		try {
			if (!isValidKey(request)) {
				return this.toAddPage();
			}
			// 未选中任何一条
			Assert.notEmpty(model.getLimitIds(), "权限点不能为空");
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("roleName", model.getRoleName());
			Long count = roleInfoDao.findCountByParam(RoleInfo.class, params,
					null, null);
			Assert.isTrue(count == 0,
					MsgUtils.r("已存在角色名称[{?}]", model.getRoleName()));
			model.setRoleId(idFactoryService.generateId(Constants.ROLE_INFO_ID));
			if (this.roleInfoService.isExistRole(model)) {
				setResult(false, ActionMessageConstant.OPER_FAIL_EXIST, request);
				return this.toAddPage();
			}

			this.roleInfoService.saveRoleAndLimits(model);
			setSucResult(ActionMessageConstant.OPER_SUCCESS, request);
			String msg = MsgUtils.r("添加角色成功,添加内容为：{?}",
					FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
		} catch (BizException e) {
			String msg = MsgUtils.r("添加角色失败,失败原因:{?}", e.getMessage());
			setResult(false, msg, request);
			super.logError(request, UserLogType.ADD.getValue(), msg);
		} catch (Exception e) {
			setResult(false, ActionMessageConstant.OPER_FAIL, request);
			String msg = MsgUtils.r("添加角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
		}
		return "toMain";
	}

	public String toUpdatePage() throws Exception {
		RoleInfo roleInfo = this.roleInfoService.getRoleInfoByRoleId(model
				.getRoleId());
		BeanUtils.copyProperties(model, roleInfo);
		this.initLimitGroupInfo(request);
		this.initLimitGroup(request, model.getLimitGroupId());
		String limitGroupId = model.getLimitGroupId();
		LimitGroupInfo lgi = limitGroupInfoDao.findById(limitGroupId);
		request.setAttribute("limitGroupName", lgi.getLimitGroupName());
		List<PrivilegeTreeNode> list = privilegeDao.getRoleTree(model
				.getLimitGroupId());

		// 取得角色权限信息
		List<RolePrivilege> rolePrivilegeList = rolePrivilegeDao
				.getRolePrivilegeByRoleId(model.getRoleId());

		saveQueryResult(request, list);
		request.setAttribute("rolePrivilegeList", rolePrivilegeList);

		initDtreeSelect(model.getRoleId(), request);

		return ActionConstant.TO_UPDATE_PAGE;
	}

	public String updateRoleInfo() throws Exception {

		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("roleName", model.getRoleName());
			Long count = roleInfoDao.findCountByParam(RoleInfo.class, params,
					"roleId", model.getRoleId());
			Assert.isTrue(count == 0,
					MsgUtils.r("已存在角色名称[{?}]", model.getRoleName()));
			this.roleInfoService.updateRoleAndLimits(model);
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = MsgUtils.r("更新角色成功,更新内容为：{?}",
					FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return this.toUpdatePage();
		} catch (BizException e) {
			setResult(true, MsgUtils.r("已存在角色名称[{?}]", model.getRoleName()),
					request);
			String msg = MsgUtils.r("更新角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return this.toUpdatePage();
		} catch (Exception e) {
			// e.printStackTrace();
			setResult(true, ActionMessageConstant.OPER_FAIL + e.getMessage(),
					request);
			String msg = MsgUtils.r("更新角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return this.toUpdatePage();
		}
	}

	private void initDtreeSelect(String roleId, HttpServletRequest request) {
		if (null == roleId) {
			return;
		}

		List<RolePrivilege> list = this.rolePrivilegeService
				.getRolePrivilegeByRoleId(roleId);
		if (null == list || list.size() == 0) {
			return;
		}

		StringBuffer initCheck = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			RolePrivilege rolePrivilege = list.get(i);
			initCheck.append("$" + rolePrivilege.getId().getLimitId());
		}

		request.setAttribute("initCheck", initCheck.deleteCharAt(0).toString());
	}

	private void initLimitGroup(HttpServletRequest request, String limitGroupId)
			throws Exception {

		if (null == limitGroupId) {
			return;
		}

		LimitGroupId id = new LimitGroupId();
		id.setLimitGroupId(limitGroupId);
		LimitGroup limitGroup = new LimitGroup();
		limitGroup.setId(id);

		List<LimitGroup> list = this.limitGroupService
				.getLimitGroup(limitGroup);
		convertToTree(list, request);
	}

	private void convertToTree(List<LimitGroup> list, HttpServletRequest request) {

		if (null == list || list.size() == 0) {
			return;
		}

		List<DTreeObj> dTreeObjs = new ArrayList<DTreeObj>();
		for (LimitGroup limitGroup : list) {
			DTreeObj dTreeObj = new DTreeObj();
			dTreeObj.setNodeId(limitGroup.getId().getLimitId());
			dTreeObj.setNodePid(limitGroup.getPid());
			dTreeObj.setNodeLabel(limitGroup.getLimitName());
			dTreeObj.setNodeName("limitIds");
			dTreeObj.setNodeValue(limitGroup.getId().getLimitId());

			dTreeObjs.add(dTreeObj);
		}

		request.setAttribute("dTree", dTreeObjs);
	}

	public void initLimitGroupInfo(HttpServletRequest request) throws Exception {

		List<LimitGroupInfo> list = this.limitGroupInfoService.getAll();
		saveQueryResult(request, list, "limitGroupInfoCollections");
	}

	public String deleteRoleInfo() throws Exception {

		try {

			if (userRoleDao.getUserRoleByRole(model.getRoleId()).size() > 0) {
				setResult(false, "角色已被用户绑定，不能删除", request);
				return this.listRoleInfo();
			}
			RoleInfo roleInfo = roleInfoDao.findById(model.getRoleId());
			this.roleInfoService.deleteRoleInfo(roleInfo);
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = MsgUtils.r("删除角色成功,删除内容为：{?}",
					FeildUtils.toString(roleInfo));
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			return this.listRoleInfo();

		} catch (Exception e) {
			String msg = MsgUtils.r("删除角色失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logException(RoleInfo.class, e.getMessage());
			setResult(false, ActionMessageConstant.OPER_FAIL, request);

			return this.listRoleInfo();
		}

	}

	public void clearForm(RoleInfo roleInfo) {

		roleInfo.setRoleId(null);
		roleInfo.setRoleName(null);
		roleInfo.setLimitGroupId(null);
		roleInfo.setLimitGroupName(null);
		roleInfo.setLimitIds(null);
		roleInfo.setLimitGroupId(null);

	}

	public String loadTree() throws BizException {
		// 取得页面权限组，如果为空则取登录用户权限组

		String limitGroupId = request.getParameter("limitGroupId");
		if (StringUtils.isEmpty(limitGroupId)) {
			saveQueryResult(request, new ArrayList());
			return "loadTree";
		}
		// 获取权限组
		LimitGroupInfo lg = (LimitGroupInfo) limitGroupInfoDao
				.findById(limitGroupId);
		if (limitGroupId == null || limitGroupId.trim().equals("")) {
			saveQueryResult(request, new ArrayList());
			return "loadTree";
		}
		// 取得权限树
		List<PrivilegeTreeNode> list = privilegeDao.getRoleTree(lg
				.getLimitGroupId());
		request.setAttribute("limitGroupId", lg.getLimitGroupId());
		saveQueryResult(request, list);
		return "loadTree";
	}

	@Override
	public RoleInfo getModel() {
		return model;
	}

	RoleInfo model = new RoleInfo();

}
