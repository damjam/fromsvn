package com.ylink.cim.user.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;
import com.ylink.cim.admin.domain.UserRole;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.type.UserType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.user.domain.UserInfo;
import com.ylink.cim.user.service.UserService;

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
import flink.util.LogUtils;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

 
public class UserInfoAction extends BaseDispatchAction {
	
	private static final Logger logger = Logger.getLogger(UserInfoAction.class);

	private final UserService userService = (UserService) getService("userService");

	//private final SysDictService sysDictService=(SysDictService)getService("sysDictService");
	
	private final UserRoleDao userRoleDao = (UserRoleDao) getService("userRoleDao");
	
	
	
	/**
	 * 角色服务类
	 */
	//private final RoleInfoService roleInfoService = (RoleInfoService) getService("roleInfoService");
	
	 
	/**
	 * 菜单绑定页面
	 */
	private final static String MENU_BIND="menubind";
	
	
	/**
	 * 根据权限点生成树节点
	 * @param privs
	 * @return
	 */
	private List<PrivilegeTreeNode> generateTreeNode(List<IPrivilege> privs ){
		List<PrivilegeTreeNode> result = new ArrayList<PrivilegeTreeNode>();
		for (IPrivilege p : privs) {
			PrivilegeTreeNode node = new PrivilegeTreeNode();
			node.setCode(p.getCode());
			node.setName(p.getName());
			node.setIsMenu(BooleanUtil.booleanToString(p.isMenu()));
			String parent = p.getParent();
			node.setParentCode(parent == null?PrivilegeTreeNode.ROOT_PARENT:parent);
			result.add(node);
		}
		return result;
	}
	
	/**
	 * 到指定用户快捷菜单页面
	 */
	public ActionForward toMenuBind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws BizException {
		DynaActionForm dForm = (DynaActionForm)form;
		//设置用户信息
		UserInfo user = this.getSessionUser(request);
		
		//获取所有的可选的菜单
		List<IPrivilege> allPriv = (List<IPrivilege>)WebUtils.getSessionAttribute(request, Constants.USER_PRIVILEGE);
		List<IPrivilege> menuPri = new ArrayList<IPrivilege>();
		/*for(IPrivilege p:allPriv){
			if(p.isMenu() || p.getCode().equals(rootPrivilege.getSys_admin_id())){//是根节点或者是目录
				menuPri.add(p);
			}
		}*/
		List<PrivilegeTreeNode> avaibleList = this.generateTreeNode(menuPri);
		request.setAttribute("avaibleList", avaibleList);
		
		String msg = LogUtils.r("指定菜单查询成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return mapping.findForward(MENU_BIND);
	}
	
	
	
	/**
	 * 导向至指定权限页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toAssignRole(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
		final HttpServletResponse response) throws Exception {
		// 获取被操作用户；
		DynaActionForm dForm = (DynaActionForm) form;
		String userId = dForm.getString("userId");
		
		//初始化已经选中的权限id
		List<UserRole> roles = userRoleDao.getUserRoleByUser(userId);
		if(roles!=null){
			String[] roleIds = new String[roles.size()];
			for(int i=0;i<roles.size();i++){
				roleIds[i] = roles.get(i).getId().getRoleId();
			}
			dForm.getMap().put("ids", roleIds);
		}
		
		init_assignRoleDate(userId, request);
		request.setAttribute("userId", userId);
		
		return mapping.findForward("assignRole");
	}

	/**
	 * 初始化用户角色分配数据
	 * @param userId
	 * @param request
	 * @throws Exception
	 */
	private void init_assignRoleDate(String userId,HttpServletRequest request) throws Exception {
		
		/*UserInfo user = (UserInfo)this.userService.getUserInfo(userId);
		if(user == null){
			String err = "没有选中用户！";
			setResult(false, err, request);
			return ;
		}
		//获取用户的可选的所有的角色
		List<RoleInfo> roles = roleInfoService.getAvaibleRoles(user.getUserType(), user.getBranchNo(), user.getMerchantNo(), user.getDeptId());
		
		List<String> orgIds = new ArrayList<String>();
		List<String> mrchIds = new ArrayList<String>();
		List<String> chnlIds = new ArrayList<String>();
		for(RoleInfo role:roles){
			String orgId = role.getBranchNo();
			String mrchId = role.getMerchantNo();
		}
		this.setAllOrgAndMrchName(request, orgIds, mrchIds,chnlIds);
		UserDataPriHelper.initSelectDeparts(request);
		
		request.setAttribute("list", roles);*/
	}
	
	public ActionForward toAddPage(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		this.initSelect(request);
		return mapping.findForward(ActionConstant.TO_ADD_PAGE);
	}
	 
	
	public void initSelect(HttpServletRequest request) throws Exception{
		/*
		ParaManager.setDictInReq(request, SysDictType.BranchType, "branchTypes");
		ParaManager.setDictInReq(request, new SysDictType[]{SysDictType.BranchType});*/
		Map<String, String> map = ParaManager.getSysDict(SysDictType.UserType.getValue());
		Map<String, String> userTypes = new HashMap<String, String>();
		userTypes.putAll(map);
		//userTypes.remove(UserType.SUPER_ADMIN.getValue());
		//userTypes.remove(UserType.CUSTOM.getValue());
		request.setAttribute("userTypes", userTypes);
	}
	
	/**
	 * 新增用户信息
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public ActionForward addUserInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{

		try{
			if (!isValidKey(request)) {
				return toAddPage(mapping, form, request, response);
			}
			UserInfoActionForm userInfoActionForm=(UserInfoActionForm)form;
			UserInfo userInfo = this.getUserInfoBy(userInfoActionForm);
			userInfo.setUserId(idFactoryService.generateId(Constants.USER_INFO_ID));
			if (StringUtils.isEmpty(userInfoActionForm.getBranchNo())) {
				userInfo.setBranchNo(getSessionUser(request).getBranchNo());
			}
			if(this.userService.isExistLoginId(userInfo.getLoginId())){
				setResult(false, ActionMessageConstant.OPER_FAIL_HAS_USER, request);
			}else{
				
				this.userService.saveUserInfo(userInfo);
				setResult(true, ActionMessageConstant.OPER_SUCCESS+",初始密码为111111，请立即更改", request);
			}
			
			this.initSelect(request);
			String msg = LogUtils.r("添加用户成功,更新内容为：{?}",FeildUtils.toString(userInfo));
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return mapping.findForward(ActionConstant.TO_ADD_PAGE);
		}catch (Exception e) {
			String msg = LogUtils.r("添加用户失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			ExceptionUtils.logException(UserInfoAction.class, e.getMessage());
			throw e;
		}
		
	}


	/**
	 * 查询用户信息
	 */
	public ActionForward listUserInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
	
		UserInfoActionForm userInfoActionForm=(UserInfoActionForm)form;
		UserInfo userInfo=this.getUserInfoBy(userInfoActionForm);
		userInfo.setBranchNo(getSessionBranch(request).getValue());
		userInfo.setUserId(getSessionUser(request).getUserId());
//		userInfo.setUserType(getSessionUser(request).getUserType());
		Paginater paginater= this.userService.getUserInfoPageList(
				userInfo, super.getPager(request));
		//List<SysDict> userTypes = sysDictService.getSysDictByDictType(SysDictType.UserType.getValue());
		BoUtils.addProperty(paginater.getList(), "userType", "userTypeName", ParaManager.getSysDict(SysDictType.UserType.getValue()), "key", "value");
		//BoUtils.addProperty(paginater.getList(), "branchNo", "branchName", ParaManager.getSysDict(SysDictType.BranchType.getValue()), "key", "value");
		saveQueryResult(request, paginater );
		String msg = LogUtils.r("查询用户信息成功");
		super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		return mapping.findForward(ActionConstant.TO_LIST_PAGE);
	}
	
	
	private UserInfo getUserInfoBy(UserInfoActionForm userInfoActionForm) throws Exception {
		
		UserInfo userInfo=new UserInfo();
		BeanUtils.copyProperties(userInfo, userInfoActionForm);
		userInfo.setCreateTime(new Date());
		userInfo.setUpdateTime(new Date());
		return userInfo;
	}

	public ActionForward queryPopUpUserInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		
			DynaActionForm dynaActionForm=(DynaActionForm)form;
			UserInfo userInfo=new UserInfo();
			userInfo.setUserId(dynaActionForm.getString("userId"));
			userInfo.setUserName(dynaActionForm.getString("userName"));
			userInfo.setUserType(dynaActionForm.getString("userType"));
			BeanUtils.copyProperties(userInfo, form);
			Paginater paginater = this.userService.getPopUpUserInfoPageList(userInfo, getPager(request)); 
			
			request.setAttribute("userInfoList", paginater.getList());
			request.setAttribute(Paginater.PAGINATER, paginater);
			return mapping.findForward("popUp");
	}
	
	public ActionForward deleteUserInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			
			UserInfoActionForm userInfoActionForm=(UserInfoActionForm)form;
			this.userService.deleteUserInfo(userInfoActionForm.getUserId());
			
			setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
			String msg = LogUtils.r("删除用户信息成功,删除内容为：{?}",userInfoActionForm.getUserId());
			super.logSuccess(request, UserLogType.DELETE.getValue(), msg);
			userInfoActionForm.setUserId(null);
			
			return this.listUserInfo(mapping, userInfoActionForm, request, response);
		}catch (Exception e) {
			String msg = LogUtils.r("删除用户信息失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			ExceptionUtils.logException(UserInfoAction.class, e.getMessage());
			throw e;
		}
	}
	
	public ActionForward toUpdatePage(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		this.initSelect(request);
		UserInfoActionForm userInfoActionForm=(UserInfoActionForm)form;
		
		UserInfo userInfo = this.userService.getUserInfo(userInfoActionForm.getUserId());
		BeanUtils.copyProperties(userInfoActionForm, userInfo);
		return mapping.findForward(ActionConstant.TO_UPDATE_PAGE);
	}
	
	public ActionForward updateUserInfo(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try{
			this.initSelect(request);
			UserInfoActionForm userInfoActionForm=(UserInfoActionForm)form;
			if(this.userService.isExistLoginIdExpellUserId(userInfoActionForm.getLoginId(),
					userInfoActionForm.getUserId())){
				setResult(false, ActionMessageConstant.OPER_FAIL_HAS_LOG_IN_ID, request);
				return mapping.findForward(ActionConstant.TO_UPDATE_PAGE);
			}else{
				UserInfo userInfo = this.userService.getUserInfo(userInfoActionForm.getUserId());
				userInfo.setLoginId(userInfoActionForm.getLoginId());
				userInfo.setUserName(userInfoActionForm.getUserName());
				userInfo.setUserType(userInfoActionForm.getUserType());
				userInfo.setUpdateTime(new Date());
				userInfo.setUpdateUser(super.getSessionUser(request).getUserId());
				if (StringUtils.isEmpty(userInfoActionForm.getBranchNo())) {
					userInfo.setBranchNo(getSessionUser(request).getBranchNo());
				}else{
					userInfo.setBranchNo(userInfoActionForm.getBranchNo());
				}
				this.userService.updateUserInfo(userInfo);
				setResult(true, ActionMessageConstant.OPER_SUCCESS, request);
				String msg = LogUtils.r("更新用户信息成功,更新内容为：{?}",FeildUtils.toString(userInfo));
				super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			}
			return mapping.findForward(ActionConstant.TO_UPDATE_PAGE);
		}catch (Exception e) {
			String msg = LogUtils.r("更新用户信息失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			ExceptionUtils.logException(UserInfoAction.class, e.getMessage());
			throw e;
		}
	}
	public ActionForward assignRole(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String[] roleIds = request.getParameterValues("ids");// 选中的角色
		String userId = request.getParameter("userId");
		if (userId == null || userId.trim().equals("")) {
			String errInfo = "没有选中用户！";
			setResult(false, errInfo, request);
			//logError(request, errInfo);
			return mapping.findForward("assignRole");
		}

		try {
			this.userService.assignUserRole(roleIds, userId);

			String sucMessage = "给用户" + userId + "分配角色成功！";
			setResult(true, sucMessage, request);
			//logSuccess(request, sucMessage);
			init_assignRoleDate(userId, request);
			String msg = LogUtils.r("给用户分配角色成功,分配内容为：{?},角色为：{?}",sucMessage,roleIds);
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
		} catch (Exception e) {
			String errInfo = "给用户" + userId + "分配角色失败！";
			setResult(false, errInfo, request);
			//logError(request, errInfo);
			String msg = LogUtils.r(errInfo+",失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			
			logger.error(errInfo, e);
		}

		return mapping.findForward("assignRole");
	}
	
	/**
	 * 导向至修改密码页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toChangePassword(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		return mapping.findForward("toChangePassword");
	}
	
	/**
	 * 修改密码,成功后跳转到主页面。
	 */
	public ActionForward changePassword(final ActionMapping mapping, final ActionForm actionForm,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		UserInfoActionForm form = (UserInfoActionForm)actionForm;
		String oldLoginPwd = form.getOldLoginPwd().trim();
		String loginPwd = form.getLoginPwd().trim();
		String confirmPwd = form.getConfirmPwd().trim();
		try {
			UserInfo user = this.getSessionUser(request);
			user =userService.getUserInfoByLoginId(user.getLoginId());
			if (!user.getLoginPwd().equals(oldLoginPwd)) {
				String errInfo = "原密码输入错误";
				setResult(false, errInfo, request);
				return this.toChangePassword(mapping, form, request, response);
			}
			if (StringUtils.isEmpty(loginPwd)) {
				String errInfo = "新密码不能为空";
				setResult(false, errInfo, request);
				return this.toChangePassword(mapping, form, request, response);
			}
			if (loginPwd.length() < 6) {
				String errInfo = "新密码至少6位";
				setResult(false, errInfo, request);
				return this.toChangePassword(mapping, form, request, response);
			}
			if (loginPwd.equals(oldLoginPwd)) {
				String errInfo = "新密码与原密码不能相同";
				setResult(false, errInfo, request);
				return this.toChangePassword(mapping, form, request, response);
			}
			if (!loginPwd.equals(confirmPwd)) {
				String errInfo = "确认密码与新密码不一致";
				setResult(false, errInfo, request);
				return this.toChangePassword(mapping, form, request, response);
			}
			user.setLoginPwd(loginPwd);
			userService.updateUserInfo(user);
			String sucMessage = "用户" + user.getLoginId() + "修改密码成功！";
			String msg = LogUtils.r("用户修改密码成功,更新内容为：{?}",FeildUtils.toString(user));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			setResult(true, sucMessage, request);
		} catch(Exception e) {
			String msg = LogUtils.r("用户修改密码失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			String errInfo = "用户修改密码失败！" + e.getMessage();
			setResult(false, errInfo, request);
			// 还是主动修改
			form.setOldLoginPwd("");
			form.setLoginId("");
			form.setConfirmPwd("");
			e.printStackTrace();
			return toChangePassword(mapping, form, request, response);
		}
		return success(mapping);
	}
	public ActionForward resetPwd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String userId = request.getParameter("userId");
			UserInfo userInfo = userService.getUserInfo(userId);
			Assert.notNull(userInfo, LogUtils.r("找不到用户{?}", userId));
			if(null != userInfo) {
				if(!StringUtils.equals(userInfo.getUserType(), UserType.CUSTOM.getValue())) {
					//若不是客户,则把密码重置为登录号
					String newPwd = MD5Util.MD5("111111");
					userService.resetCustomPwd(userInfo.getUserId(), newPwd);
					setResult(true, LogUtils.r("用户{?}密码已被重置为{?}",userInfo.getLoginId(), "111111"), request);
				} else {
					//若是客户,则密码重置为手机号后六位
				}
			}  else {
				setResult(false, "密码重置失败", request);
			}
			String msg = LogUtils.r("用户密码重置成功,新内容为：{?}",FeildUtils.toString(userInfo));
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
		} catch (Exception e) {
//			e.printStackTrace();
			String msg = LogUtils.r("用户密码重置失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.DELETE.getValue(), msg);
			setResult(false, "操作失败,"+e.getMessage(), request);
			//保存日志
		}
		return listUserInfo(mapping, form, request, response);
	}
}
