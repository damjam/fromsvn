package com.ylink.cim.admin.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.PrivilegeHelper;
import com.ylink.cim.admin.service.UserService;
import com.ylink.cim.common.type.UserLogType;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.IPrivilege;
import flink.util.LoggerCacheUtils;
import flink.util.MsgUtils;
import flink.util.WebResource;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class LoginAction extends BaseAction implements ModelDriven<UserInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	/**
	 * ���ص�¼�û�Ȩ�޲˵���, ���ص������˵�.
	 */
	public String loadUserPrivilegeTopButton() throws Exception {
		String parentCode = request.getParameter("parentCode");// ��һ���˵�Ȩ�ޱ��(Ϊ���ڵ���ӽڵ�).
		String privilegeCode = request.getParameter("privilegeCode");// �ڶ����˵�Ȩ�ޱ��.
		Privilege menuTree = (Privilege) WebUtils.getSessionAttribute(request,
				Constants.USER_MENU);// Ŀ¼��

		// ��ѯ�������˵�.
		request.setAttribute("menus",
				getPrivilegeTopButton(menuTree, parentCode, privilegeCode));
		return "topbutton";
	}

	/**
	 * �˳�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returnlo
	 * @throws Exception
	 */
	public String logOff() throws Exception {
		String userId = "";
		try {
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
					Constants.SESSION_USER);
			userId = userInfo.getUserId();
			/*
			 * String tag = getSessionBranchTag(request); if
			 * (StringUtils.isEmpty(tag)) { String branchNo =
			 * CookieDealer.getCookieValue(CookieDealer.FROM_BRANCH, request);
			 * if (StringUtils.isNotEmpty(branchNo)) {
			 * 
			 * } tag = BranchType.valueOf(branchNo).getTag(); }
			 */
			setResult(true, "�û�ǿ�����߳ɹ�", request);
			/*
			 * if (StringUtils.isNotEmpty(tag)) { forward = new
			 * ActionForward("/"+tag); }
			 */
			// if (!branchType.getValue().equals(BranchType.SZGOLD.getValue()))
			// {

			// }
			CPSHttpSessionListener.logOff(userId);
			WebUtils.setSessionAttribute(request, "tag", null);
			request.getSession().invalidate();
			String msg = MsgUtils.r("�û���" + userId + " �ǳ��ɹ�");
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return "index";
			// return mapping.findForward("index");// ������¼ҳ��
		} catch (Exception e) {
			String msg = MsgUtils.r("�û���" + userId + " �ǳ�ʧ��,ʧ��ԭ��:{?}",
					e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return "index";// ������¼ҳ��
		}
	}

	public String login() throws Exception {
		String verifyCode = request.getParameter("verifyCode");
		String randomCode = (String) request.getSession().getAttribute(
				"randomCode");
		if (!StringUtils.equalsIgnoreCase(verifyCode, randomCode)) {
			setResult(false, "��֤�벻��ȷ", request);
			return ActionConstant.TO_LOGIN_PAGE;
		}
		String loginId = request.getParameter("code");
		String password = request.getParameter("password");

		// ��ֹͬһ���������û�û���˳����ж���û�
		UserInfo oldUser = getSessionUser(request);
		if (oldUser != null) {
			HttpSession oldSession = request.getSession();
			oldSession.invalidate();
		}

		request.setAttribute("code", loginId);

		// ����Ϣ���浽cookie��
		String keyIfCookie = "remenber";
		String[] keyToGetCookie = new String[] { "code" };
		String[] values = new String[] { loginId };
		CookieDealer.putAllToCookie(keyIfCookie, values, keyToGetCookie,
				request, response);

		try {

			UserInfo userInfo = this.userService.getUserInfoByLoginId(loginId);

			if (null == userInfo) {
				setResult(false, ActionMessageConstant.OPER_FAIL_NO_USER,
						request);
				return ActionConstant.TO_LOGIN_PAGE;
			}
			if (!password.equals(userInfo.getLoginPwd())) {
				// Integer errorTimes =
				// userService.updateErrorInfo(userInfo.getUserId());
				// if (errorTimes < 3) {
				setResult(false, ActionMessageConstant.OPER_FAIL_ERROR_PWD,
						request);
				// } else {
				// setResult(false, ActionMessageConstant.OPER_FAIL_USER_LOCKED,
				// request);
				// }
				return ActionConstant.TO_LOGIN_PAGE;
			}
			// �˺���Ϣ��ȷ,����Ƿ��ѱ�����
			/*
			 * if(!canLogin(userInfo)){ setResult(false,
			 * ActionMessageConstant.OPER_FAIL_USER_LOCKED, request); return
			 * mapping.findForward(ActionConstant.TO_LOGIN_PAGE); }
			 */
			WebUtils.setSessionAttribute(request, Constants.SESSION_USER,
					userInfo);// ���û����浽session��
			String tag = request.getParameter("tag");
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TAG, tag);

			CookieDealer.saveBranch(userInfo.getBranchNo(), response);
			CPSHttpSessionListener.putSession(userInfo.getUserId(),
					request.getSession());
			initDefaultParam(request);
			// �����û�Ȩ�޺Ͳ˵�.
			loadPrivilege(userInfo, request);
			String msg = MsgUtils.r("�û���" + loginId + " ��¼�ɹ�");
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return ActionConstant.TO_MAIN_PAGE;
		} catch (Throwable e) {
			e.printStackTrace();
			String err = "��¼�쳣��" + e.getMessage();
			LoggerCacheUtils.getLogger(LoginAction.class).debug(err);
			setResult(false, e.getMessage(), request);
			String tag = getSessionBranchTag(request);
			String msg = MsgUtils.r("�û���" + loginId + " �ǳ�ʧ��,ʧ��ԭ��:{?}",
					e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return ActionConstant.TO_LOGIN_PAGE;
		}
	}

	private void initDefaultParam(HttpServletRequest request) {
		//
		WebUtils.setSessionAttribute(
				request,
				"defStartDate",
				DateUtil.getDateYYYYMMDD(DateUtil.addMonths(
						DateUtil.getCurrent(), -3)));
		WebUtils.setSessionAttribute(request, "defEndDate",
				DateUtil.getCurrentDate());
	}

	private boolean canLogin(UserInfo userInfo) throws BizException {
		Integer errorTimes = userInfo.getPwdErrorTimes();
		boolean flag = true;
		if (errorTimes != null && errorTimes >= 3) {
			// �ж��Ƿ��Ѵ��Сʱ
			Date errorTime = userInfo.getErrorTime();
			Date now = new Date();
			if (errorTime == null) {
				errorTime = now;
			}
			if ((now.getTime() - errorTime.getTime()) / (1000 * 60) > 30) {
				flag = true;
			} else {
				flag = false;
			}
		}
		if (flag) {
			userInfo.setErrorTime(null);
			userInfo.setPwdErrorTimes(null);
			userService.updateUserInfo(userInfo);
		}
		return flag;
	}

	/**
	 * ��ѯ�������˵�.
	 * 
	 * @param menuTree
	 *            ���е�Ȩ�޵�
	 * @param firstCode
	 * @param secondCode
	 * @return
	 */
	private Collection<Privilege> getPrivilegeTopButton(
			final Privilege menuTree, final String firstCode,
			final String secondCode) {
		// ѭ����һ���˵�.
		for (Iterator<Privilege> i = menuTree.getChildren().iterator(); i
				.hasNext();) {
			Privilege first = i.next();
			// �ҵ���һ���˵�, ѭ���ӽڵ��ѯ�ڶ���.
			if (first.getCode().equals(firstCode)) {

				// ѭ���ڶ����˵�
				for (Iterator<Privilege> j = first.getChildren().iterator(); j
						.hasNext();) {
					Privilege second = j.next();

					if (second.getCode().equals(secondCode)) {// �������˵�
						Collection<Privilege> result = new ArrayList<Privilege>();
						Collection<Privilege> thirdPriv = second.getChildren();
						if (thirdPriv == null || thirdPriv.size() == 0) {
							return Collections.emptyList();
						}

						// �������������е�Ŀ¼���أ�Ҷ�ӽڵ㲻Ҫ��
						for (Privilege p : thirdPriv) {
							if (p.isMenu()) {
								result.add(p);
							}
						}
						return result;
					}
				}
			}
		}

		return Collections.emptyList();
	}

	/**
	 * ��ȡ���еĲ˵� ��һ���Ͷ����˵���
	 * 
	 * @param allPriv
	 * @return
	 */
	private List<IPrivilege> getMenu(IPrivilege menuTree) {
		List<IPrivilege> result = new ArrayList<IPrivilege>();

		Collection<IPrivilege> firstLev = menuTree.getAllChildren();// һ���˵�
		if (firstLev == null || firstLev.size() == 0) {
			LoggerCacheUtils.getLogger(LoginAction.class).debug("û��һ���˵�");
			return result;
		}

		// ����һ���˵�
		for (IPrivilege firstP : firstLev) {
			if (firstP.isMenu()) {// ֻ����Ŀ¼������Ҷ��
				result.add(firstP);
			}
		}

		Iterator iterator = firstLev.iterator();
		while (iterator.hasNext()) {
			IPrivilege menu1 = (IPrivilege) iterator.next();
			// ���صڶ����˵�
			Collection<IPrivilege> secord = menu1.getAllChildren();
			if (secord != null && secord.size() > 0) {
				for (IPrivilege p : secord) {
					if (p.isMenu()) {
						result.add(p);
					}
				}
			}
		}

		return result;
	}

	/**
	 * ����Ȩ�޺���Դ֮���ӳ���ϵ
	 * 
	 * @param allPriv
	 * @param resources
	 * @return
	 */
	private Map<String, List<PrivilegeResource>> getMapOfPriv(
			List<IPrivilege> allPriv, List<WebResource> resources) {
		Map<String, List<PrivilegeResource>> result = new HashMap<String, List<PrivilegeResource>>();
		for (WebResource r : resources) {
			PrivilegeResource pr = (PrivilegeResource) r;
			List<PrivilegeResource> listOfRes = result.get(pr.getLimitId());
			if (listOfRes == null) {
				listOfRes = new ArrayList<PrivilegeResource>();
				result.put(pr.getLimitId(), listOfRes);
			}
			listOfRes.add(pr);
		}

		return result;
	}

	/**
	 * ��ʼ��Ȩ�޵��url��param
	 * 
	 * @param p
	 * @param res
	 */
	private void setPrivUrlAndParam(Privilege p, List<PrivilegeResource> res) {
		if (res == null || res.size() == 0) {
			return;
		}

		for (PrivilegeResource r : res) {
			if ("Y".equals(r.getIsEntry())) {// ����ڵ�ַ
				p.setEntry(WebResource.getLink(r.getUrl(), r.getParam()));
			}
		}
	}

	/**
	 * Ȩ�޵㵽Ȩ�޵�ӳ���key
	 */
	public static final String ID_TO_PRI = "id_to_pri";

	/**
	 * ��Ȩ�޷��뵽map<Ȩ��id,Ȩ��>��
	 * 
	 * @param allPriv
	 * @param resources
	 * @param request
	 */
	private void mapPriv(HttpServletRequest request) {
		List<IPrivilege> allPriv = (List<IPrivilege>) WebUtils
				.getSessionAttribute(request, Constants.USER_PRIVILEGE);
		Map<String, IPrivilege> mapOfPri = new HashMap<String, IPrivilege>();// Ȩ�޵㵽Ȩ�޵�ӳ��
		for (IPrivilege ip : allPriv) {
			mapOfPri.put(ip.getCode(), ip);
		}
		WebUtils.setSessionAttribute(request, ID_TO_PRI, mapOfPri);// �������е�Ȩ��
	}

	/**
	 * �����û�Ȩ�޺Ͳ˵�, ������request(��session) ��.
	 * 
	 * @param user
	 * @param request
	 */
	private void loadPrivilege(final UserInfo user,
			final HttpServletRequest request) throws Exception {
		// ��ѯ�û��˵�, ���浽session��.
		String userId = null;
		if (null != user) {
			userId = user.getUserId();
		}
		// �������е�Ȩ�޵�
		List<IPrivilege> allPriv = this.userService.getAllPriv(userId);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE, allPriv);// �������е�Ȩ��

		// ��ȡ�û�Ȩ����Դ, ���浽session��.
		List<WebResource> resources = this.userService
				.getPrivilegeResources(allPriv);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE_RES,
				WebResource.map(resources));

		// ����¼��Ȩ����Դ����
		dealWithLogPri(request);

		// ��������Ȩ�޵�·��
		Map<String, List<PrivilegeResource>> map = this.getMapOfPriv(allPriv,
				resources);// Ȩ�޺�Ȩ����Դ֮���ӳ��
		for (IPrivilege ip : allPriv) {
			Privilege p = (Privilege) ip;
			List<PrivilegeResource> res = map.get(p.getLimitId());
			this.setPrivUrlAndParam(p, res);
		}

		// ��Ȩ�޷��뵽������
		mapPriv(request);

		// ���˵�ת��Ϊ����״, ���浽session��.
		IPrivilege root = new Privilege();// ������ڵ�.
		root.setCode(Constants.ROOT_PRIVILEGE_CODE);
		IPrivilege priviligeTree = PrivilegeHelper.getPrivilegeTree(root,
				allPriv, map);
		WebUtils.setSessionAttribute(request, Constants.USER_MENU,
				priviligeTree);// ����Ȩ����
		this.setUserMenu(priviligeTree);
	}

	/**
	 * ����¼��Ȩ�޼��뵽session��
	 * 
	 * @param privileges
	 */
	private void dealWithLogPri(HttpServletRequest request) {

		Map<String, PrivilegeResource> privileges = (Map<String, PrivilegeResource>) WebUtils
				.getSessionAttribute(request, Constants.USER_PRIVILEGE_RES);

		String link = WebResource.getLink(request);

		PrivilegeResource pr = new PrivilegeResource();
		pr.setLimitId("login");

		privileges.put(link, pr);
	}

	public String index() throws Exception {
		UserInfo userInfo = getSessionUser(request);
		String forwardUrl = "/";
		if (userInfo != null) {
			// String tag = getSessionBranchTag(request);
			// forwardUrl+= tag;
		} else {
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TAG, null);
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TYPE, null);
		}
		// response.sendRedirect(forwardUrl);
		return forwardUrl;
	}

	@Override
	public UserInfo getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	private UserInfo model = new UserInfo();

	private IPrivilege userMenu;

	public IPrivilege getUserMenu() {
		return userMenu;
	}

	public void setUserMenu(IPrivilege userMenu) {
		this.userMenu = userMenu;
	}

}
