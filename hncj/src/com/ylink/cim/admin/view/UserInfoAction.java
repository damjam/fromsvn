package com.ylink.cim.admin.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserRole;
import com.ylink.cim.admin.service.UserService;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.type.UserType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.util.CopyPropertyUtil;

import flink.MD5Util;
import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.BoUtils;
import flink.util.BooleanUtil;
import flink.util.ExceptionUtils;
import flink.util.IPrivilege;
import flink.util.MsgUtils;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class UserInfoAction extends BaseAction implements ModelDriven<UserInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3719985713417266784L;

	private static final Logger logger = Logger.getLogger(UserInfoAction.class);

	@Autowired
	private UserService userService;

	// private final SysDictService
	// sysDictService=(SysDictService)getService("sysDictService");
	@Autowired
	private UserRoleDao userRoleDao;

	/**
	 * ��ɫ������
	 */
	// private final RoleInfoService roleInfoService = (RoleInfoService)
	// getService("roleInfoService");

	/**
	 * �˵���ҳ��
	 */
	private final static String MENU_BIND = "menubind";

	/**
	 * ����Ȩ�޵��������ڵ�
	 * 
	 * @param privs
	 * @return
	 */
	private List<PrivilegeTreeNode> generateTreeNode(List<IPrivilege> privs) {
		List<PrivilegeTreeNode> result = new ArrayList<PrivilegeTreeNode>();
		for (IPrivilege p : privs) {
			PrivilegeTreeNode node = new PrivilegeTreeNode();
			node.setCode(p.getCode());
			node.setName(p.getName());
			node.setIsMenu(BooleanUtil.booleanToString(p.isMenu()));
			String parent = p.getParent();
			node.setParentCode(parent == null ? PrivilegeTreeNode.ROOT_PARENT
					: parent);
			result.add(node);
		}
		return result;
	}

	/**
	 * ��ָ���û���ݲ˵�ҳ��
	 */
	public String toMenuBind() throws BizException {
		// �����û���Ϣ
		UserInfo user = this.getSessionUser(request);

		// ��ȡ���еĿ�ѡ�Ĳ˵�
		List<IPrivilege> allPriv = (List<IPrivilege>) WebUtils
				.getSessionAttribute(request, Constants.USER_PRIVILEGE);
		List<IPrivilege> menuPri = new ArrayList<IPrivilege>();
		/*
		 * for(IPrivilege p:allPriv){ if(p.isMenu() ||
		 * p.getCode().equals(rootPrivilege.getSys_admin_id())){//�Ǹ��ڵ������Ŀ¼
		 * menuPri.add(p); } }
		 */
		List<PrivilegeTreeNode> avaibleList = this.generateTreeNode(menuPri);
		request.setAttribute("avaibleList", avaibleList);

		String msg = MsgUtils.r("ָ���˵���ѯ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return MENU_BIND;
	}

	/**
	 * ������ָ��Ȩ��ҳ��
	 * 
	 * @param mapping
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String toAssignRole() throws Exception {
		// ��ȡ�������û���
		String userId = model.getUserId();

		// ��ʼ���Ѿ�ѡ�е�Ȩ��id
		List<UserRole> roles = userRoleDao.getUserRoleByUser(userId);
		if (roles != null) {
			String[] roleIds = new String[roles.size()];
			for (int i = 0; i < roles.size(); i++) {
				roleIds[i] = roles.get(i).getId().getRoleId();
			}
			model.setIds(roleIds);
		}

		init_assignRoleDate(userId, request);
		request.setAttribute("userId", userId);

		return "assignRole";
	}

	/**
	 * ��ʼ���û���ɫ��������
	 * 
	 * @param userId
	 * @param request
	 * @throws Exception
	 */
	private void init_assignRoleDate(String userId, HttpServletRequest request)
			throws Exception {

		/*
		 * UserInfo user = (UserInfo)this.userService.getUserInfo(userId);
		 * if(user == null){ String err = "û��ѡ���û���"; setResult(false, err,
		 * request); return ; } //��ȡ�û��Ŀ�ѡ�����еĽ�ɫ List<RoleInfo> roles =
		 * roleInfoService.getAvaibleRoles(user.getUserType(),
		 * user.getBranchNo(), user.getMerchantNo(), user.getDeptId());
		 * 
		 * List<String> orgIds = new ArrayList<String>(); List<String> mrchIds =
		 * new ArrayList<String>(); List<String> chnlIds = new
		 * ArrayList<String>(); for(RoleInfo role:roles){ String orgId =
		 * role.getBranchNo(); String mrchId = role.getMerchantNo(); }
		 * this.setAllOrgAndMrchName(request, orgIds, mrchIds,chnlIds);
		 * UserDataPriHelper.initSelectDeparts(request);
		 * 
		 * request.setAttribute("list", roles);
		 */
	}

	public String toAddPage() throws Exception {

		this.initSelect(request);
		return ActionConstant.TO_ADD_PAGE;
	}

	public void initSelect(HttpServletRequest request) throws Exception {
		/*
		 * ParaManager.setDictInReq(request, SysDictType.BranchType,
		 * "branchTypes"); ParaManager.setDictInReq(request, new
		 * SysDictType[]{SysDictType.BranchType});
		 */
		Map<String, String> map = ParaManager.getSysDict(SysDictType.UserType
				.getValue());
		Map<String, String> userTypes = new HashMap<String, String>();
		if (map != null) {
			userTypes.putAll(map);
		}
		// userTypes.remove(UserType.SUPER_ADMIN.getValue());
		// userTypes.remove(UserType.CUSTOM.getValue());
		request.setAttribute("userTypes", userTypes);
		request.setAttribute("branches", ParaManager.getBranches(true));
	}

	/**
	 * �����û���Ϣ
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String addUserInfo() throws Exception {

		try {
			if (!isValidKey(request)) {
				return toAddPage();
			}
			model.setUserId(idFactoryService.generateId(Constants.USER_INFO_ID));
			if (StringUtils.isEmpty(model.getBranchNo())) {
				model.setBranchNo(getSessionUser(request).getBranchNo());
			}
			if (this.userService.isExistLoginId(model.getLoginId())) {
				setResult(false, ActionMessageConstant.OPER_FAIL_HAS_USER,
						request);
			} else {

				this.userService.saveUserInfo(model);
				setResult(true, ActionMessageConstant.OPER_SUCCESS
						+ ",��ʼ����Ϊ111111������������", request);
			}

			this.initSelect(request);
			String msg = MsgUtils.r("����û��ɹ�,��������Ϊ��{?}",
					FeildUtils.toString(model));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return ActionConstant.TO_ADD_PAGE;
		} catch (Exception e) {
			String msg = MsgUtils.r("����û�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			ExceptionUtils.logException(UserInfoAction.class, e.getMessage());
			throw e;
		}

	}

	/**
	 * ��ѯ�û���Ϣ
	 */
	public String listUserInfo() throws Exception {

		model.setUserId(getSessionUser(request).getUserId());
		// userInfo.setUserType(getSessionUser(request).getUserType());
		Paginater paginater = this.userService.getUserInfoPageList(model,
				super.getPager(request));
		// List<SysDict> userTypes =
		// sysDictService.getSysDictByDictType(SysDictType.UserType.getValue());
		BoUtils.addProperty(paginater.getList(), "userType", "userTypeName",
				ParaManager.getSysDict(SysDictType.UserType.getValue()), "key",
				"value");
		BoUtils.addProperty(paginater.getList(), "branchNo", "branchName", ParaManager.getBranches(true), "key", "value");
		// BoUtils.addProperty(paginater.getList(), "branchNo", "branchName",
		// ParaManager.getSysDict(SysDictType.BranchType.getValue()), "key",
		// "value");
		saveQueryResult(request, paginater);
		String msg = MsgUtils.r("��ѯ�û���Ϣ�ɹ�");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return ActionConstant.TO_LIST_PAGE;
	}

	public String queryPopUpUserInfo() throws Exception {

		Paginater paginater = this.userService.getPopUpUserInfoPageList(model,
				getPager(request));

		request.setAttribute("userInfoList", paginater.getList());
		request.setAttribute(Paginater.PAGINATER, paginater);
		return "popUp";
	}

	public String deleteUserInfo() throws Exception {

		try {

			this.userService.deleteUserInfo(model.getUserId());

			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = MsgUtils.r("ɾ���û���Ϣ�ɹ�,ɾ������Ϊ��{?}", model.getUserId());
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			model.setUserId(null);

			return this.listUserInfo();
		} catch (Exception e) {
			String msg = MsgUtils.r("ɾ���û���Ϣʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logException(UserInfoAction.class, e.getMessage());
			throw e;
		}
	}

	public String toUpdatePage() throws Exception {

		this.initSelect(request);

		UserInfo userInfo = this.userService.getUserInfo(model.getUserId());
		BeanUtils.copyProperties(model, userInfo);
		return ActionConstant.TO_UPDATE_PAGE;
	}

	public String updateUserInfo() throws Exception {

		try {
			this.initSelect(request);
			if (this.userService.isExistLoginIdExpellUserId(model.getLoginId(),
					model.getUserId())) {
				setResult(false, ActionMessageConstant.OPER_FAIL_HAS_LOG_IN_ID,
						request);
				return ActionConstant.TO_UPDATE_PAGE;
			} else {
				UserInfo userInfo = this.userService.getUserInfo(model
						.getUserId());
				CopyPropertyUtil.copyPropertiesIgnoreNull(model, userInfo);
				userInfo.setUpdateTime(new Date());
				userInfo.setUpdateUser(super.getSessionUser(request)
						.getUserId());
				if (StringUtils.isEmpty(model.getBranchNo())) {
					userInfo.setBranchNo(getSessionUser(request).getBranchNo());
				} else {
					userInfo.setBranchNo(model.getBranchNo());
				}
				this.userService.updateUserInfo(userInfo);
				setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
				String msg = MsgUtils.r("�����û���Ϣ�ɹ�,��������Ϊ��{?}",
						FeildUtils.toString(userInfo));
				super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			}
			return ActionConstant.TO_UPDATE_PAGE;
		} catch (Exception e) {
			String msg = MsgUtils.r("�����û���Ϣʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			ExceptionUtils.logException(UserInfoAction.class, e.getMessage());
			throw e;
		}
	}

	public String assignRole() throws Exception {
		String[] roleIds = request.getParameterValues("ids");// ѡ�еĽ�ɫ
		String userId = request.getParameter("userId");
		if (userId == null || userId.trim().equals("")) {
			String errInfo = "û��ѡ���û���";
			setResult(false, errInfo, request);
			// logError(request, errInfo);
			return "assignRole";
		}

		try {
			this.userService.assignUserRole(roleIds, userId);

			String sucMessage = "���û�" + userId + "�����ɫ�ɹ���";
			setResult(true, sucMessage, request);
			// logSuccess(request, sucMessage);
			init_assignRoleDate(userId, request);
			String msg = MsgUtils.r("���û������ɫ�ɹ�,��������Ϊ��{?},��ɫΪ��{?}", sucMessage,
					roleIds);
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
		} catch (Exception e) {
			String errInfo = "���û�" + userId + "�����ɫʧ�ܣ�";
			setResult(false, errInfo, request);
			// logError(request, errInfo);
			String msg = MsgUtils.r(errInfo + ",ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);

			logger.error(errInfo, e);
		}

		return "assignRole";
	}

	/**
	 * �������޸�����ҳ��
	 * 
	 * @param mapping
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String toChangePassword() throws Exception {
		return "toChangePassword";
	}

	/**
	 * �޸�����,�ɹ�����ת����ҳ�档
	 */
	public String changePassword() throws Exception {
		String oldLoginPwd = model.getOldLoginPwd().trim();
		String loginPwd = model.getLoginPwd().trim();
		String confirmPwd = model.getConfirmPwd().trim();
		try {
			UserInfo user = this.getSessionUser(request);
			user = userService.getUserInfoByLoginId(user.getLoginId());
			if (!user.getLoginPwd().equals(oldLoginPwd)) {
				String errInfo = "ԭ�����������";
				setResult(false, errInfo, request);
				return this.toChangePassword();
			}
			if (StringUtils.isEmpty(loginPwd)) {
				String errInfo = "�����벻��Ϊ��";
				setResult(false, errInfo, request);
				return this.toChangePassword();
			}
			if (loginPwd.length() < 6) {
				String errInfo = "����������6λ";
				setResult(false, errInfo, request);
				return this.toChangePassword();
			}
			if (loginPwd.equals(oldLoginPwd)) {
				String errInfo = "��������ԭ���벻����ͬ";
				setResult(false, errInfo, request);
				return this.toChangePassword();
			}
			if (!loginPwd.equals(confirmPwd)) {
				String errInfo = "ȷ�������������벻һ��";
				setResult(false, errInfo, request);
				return this.toChangePassword();
			}
			user.setLoginPwd(loginPwd);
			userService.updateUserInfo(user);
			String sucMessage = "�û�" + user.getLoginId() + "�޸�����ɹ���";
			String msg = MsgUtils.r("�û��޸�����ɹ�,��������Ϊ��{?}",
					FeildUtils.toString(user));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			setResult(true, sucMessage, request);
		} catch (Exception e) {
			String msg = MsgUtils.r("�û��޸�����ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			String errInfo = "�û��޸�����ʧ�ܣ�" + e.getMessage();
			setResult(false, errInfo, request);
			// ���������޸�
			model.setOldLoginPwd("");
			model.setLoginId("");
			model.setConfirmPwd("");
			e.printStackTrace();
			return toChangePassword();
		}
		return "success";
	}

	public String resetPwd() throws Exception {
		try {
			String userId = request.getParameter("userId");
			UserInfo userInfo = userService.getUserInfo(userId);
			Assert.notNull(userInfo, MsgUtils.r("�Ҳ����û�{?}", userId));
			if (null != userInfo) {
				if (StringUtils.equals(userInfo.getUserType(),
						UserType.SYS_ADMIN.getValue())) {
					// �����ǿͻ�,�����������Ϊ��¼��
					String newPwd = MD5Util.MD5("111111");
					userService.resetCustomPwd(userInfo.getUserId(), newPwd);
					setResult(true, MsgUtils.r("�û�{?}�����ѱ�����Ϊ{?}",
							userInfo.getLoginId(), "111111"), request);
				} else {
					// ���ǿͻ�,����������Ϊ�ֻ��ź���λ
				}
			} else {
				setResult(false, "��������ʧ��", request);
			}
			String msg = MsgUtils.r("�û��������óɹ�,������Ϊ��{?}",
					FeildUtils.toString(userInfo));
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
		} catch (Exception e) {
			// e.printStackTrace();
			String msg = MsgUtils.r("�û���������ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			setResult(false, "����ʧ��," + e.getMessage(), request);
			// ������־
		}
		return listUserInfo();
	}

	@Override
	public UserInfo getModel() {
		return model;
	}

	private UserInfo model = new UserInfo();

}
