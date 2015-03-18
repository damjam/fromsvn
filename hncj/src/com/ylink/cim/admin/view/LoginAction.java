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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.service.PrivilegeHelper;
import com.ylink.cim.busioper.dao.NoticeMsgRecordDao;
import com.ylink.cim.busioper.domain.NoticeMsgRecord;
import com.ylink.cim.common.state.SignState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.type.UserType;
import com.ylink.cim.cust.domain.CustInfo;
import com.ylink.cim.cust.service.CustInfoService;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.user.domain.UserInfo;
import com.ylink.cim.user.service.UserService;

import flink.consant.ActionConstant;
import flink.consant.ActionMessageConstant;
import flink.consant.Constants;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;
import flink.util.IPrivilege;
import flink.util.LogUtils;
import flink.util.LoggerCacheUtils;
import flink.util.WebResource;
import flink.web.BaseDispatchAction;


public class LoginAction extends BaseDispatchAction {

	private final UserService userService = (UserService) getService("userService");
	private final CustInfoService custInfoService = (CustInfoService) getService("custInfoService");
	private NoticeMsgRecordDao noticeMsgRecordDao = (NoticeMsgRecordDao)getService("noticeMsgRecordDao");
	private SignContractDao signContractDao = (SignContractDao)getService("signContractDao");
	/**
	 * ���ص�¼�û�Ȩ�޲˵���, ���ص������˵�.
	 */
	public ActionForward loadUserPrivilegeTopButton(final ActionMapping mapping,
		  ActionForm form, final HttpServletRequest request,
		 HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");// ��һ���˵�Ȩ�ޱ��(Ϊ���ڵ���ӽڵ�).
		String privilegeCode = request.getParameter("privilegeCode");// �ڶ����˵�Ȩ�ޱ��.
		Privilege menuTree = (Privilege) WebUtils.getSessionAttribute(request, Constants.USER_MENU);//Ŀ¼��

		// ��ѯ�������˵�.
		request.setAttribute("menus", getPrivilegeTopButton(menuTree, parentCode, privilegeCode));
		return forward("/pages/layout/topbutton.jsp");
	}
	
	/**
	 * �˳�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward logOff(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId="";
		try {
			UserInfo userInfo = (UserInfo)request.getSession().getAttribute(Constants.SESSION_USER);
			userId = userInfo.getUserId();
			/*
			String tag = getSessionBranchTag(request);
			if (StringUtils.isEmpty(tag)) {
				String branchNo = CookieDealer.getCookieValue(CookieDealer.FROM_BRANCH, request);
				if (StringUtils.isNotEmpty(branchNo)) {
					
				}
				tag = BranchType.valueOf(branchNo).getTag();
			}*/
			setResult(true, "�û�ǿ�����߳ɹ�", request);
			ActionForward forward = new ActionForward("/");
			/*
			if (StringUtils.isNotEmpty(tag)) {
				forward = new ActionForward("/"+tag);
			}*/
			//if (!branchType.getValue().equals(BranchType.HQ_0000.getValue())) {
				
			//}
			CPSHttpSessionListener.logOff(userId);
			WebUtils.setSessionAttribute(request, "tag", null);
			request.getSession().invalidate();
			forward.setRedirect(true);
			String msg = LogUtils.r("�û���"+userId+" �ǳ��ɹ�");
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return forward;
			//return mapping.findForward("index");// ������½ҳ��
		} catch (Exception e) {
			String msg = LogUtils.r("�û���"+userId+" �ǳ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return mapping.findForward("index");// ������½ҳ��
		}
	}
	

	public ActionForward login(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String verifyCode = request.getParameter("verifyCode");
		String randomCode = (String) request.getSession().getAttribute("randomCode");
		if (!StringUtils.equalsIgnoreCase(verifyCode, randomCode)) {
			 setResult(false, "��֤�벻��ȷ", request);
			 return forward("/index_old.jsp");
		}
		String loginId = request.getParameter("code");
		String password = request.getParameter("password");
		
		//��ֹͬһ���������û�û���˳����ж���û�
		UserInfo oldUser = getSessionUser(request);
		if(oldUser!=null){
			HttpSession oldSession = request.getSession();
			oldSession.invalidate();
		}
		
		request.setAttribute("code", loginId);
		
		//����Ϣ���浽cookie��
		String keyIfCookie = "remenber";
		String[] keyToGetCookie = new String[]{"code"};
		String[] values = new String[]{loginId};
		CookieDealer.putAllToCookie(keyIfCookie, values, keyToGetCookie, request, response);
		
		try {

			UserInfo userInfo = this.userService.getUserInfoByLoginId(loginId);
			
			if(null == userInfo){
				setResult(false, ActionMessageConstant.OPER_FAIL_NO_USER, request);
				return mapping.findForward(ActionConstant.TO_LOGIN_PAGE);
			}
			if(!password.equals(userInfo.getLoginPwd())){
				//Integer errorTimes = userService.updateErrorInfo(userInfo.getUserId());
				//if (errorTimes < 3) {
				setResult(false, ActionMessageConstant.OPER_FAIL_ERROR_PWD, request);
				//} else {
				//	setResult(false, ActionMessageConstant.OPER_FAIL_USER_LOCKED, request);
				//}
				return mapping.findForward(ActionConstant.TO_LOGIN_PAGE);
			}
			//�˺���Ϣ��ȷ,����Ƿ��ѱ�����
			/*
			if(!canLogin(userInfo)){
				setResult(false, ActionMessageConstant.OPER_FAIL_USER_LOCKED, request);
				return mapping.findForward(ActionConstant.TO_LOGIN_PAGE);
			}*/
			WebUtils.setSessionAttribute(request, Constants.SESSION_USER, userInfo);//���û����浽session��
			String tag = request.getParameter("tag");
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TYPE, BranchType.getByTag(tag));
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TAG, tag);
			if(StringUtils.equals(userInfo.getUserType(), UserType.CUSTOM.getValue())) {
				//�ͻ��û�
				CustInfo custInfo = custInfoService.getCustInfoByUserId(userInfo.getUserId());//�û�������û���ж�
				if(null != custInfo) {
					WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM_ID, custInfo.getId());//���ͻ�Id���浽session��
					WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM, custInfo);//���ͻ���Ϣ���浽session��
					Map<String, Object> map = getParaMap();
					map.put("custId", custInfo.getId());
					map.put("branchNo", getSessionBranch(request).getValue());
					map.put("state", SignState.SIGNED.getValue());
					String[] investAcctNo = signContractDao.findAcctByCust(map);
					WebUtils.setSessionAttribute(request, Constants.INVEST_ACCT_NOS, investAcctNo);
				}
				Map<String, Object> map = getParaMap();
				map.put("custId", custInfo.getId());
				map.put("read", Symbol.NO);
				List<NoticeMsgRecord> list = noticeMsgRecordDao.findByParams(map);
				request.setAttribute("msgCnt", list.size());
				//request.setAttribute("msgCnt", list.size());
				WebUtils.setSessionAttribute(request, CookieDealer.FROM_BRANCH, custInfo.getBranchNo());
			}
			CookieDealer.saveBranch(userInfo.getBranchNo(), response);
			CPSHttpSessionListener.putSession(userInfo.getUserId(), request.getSession());
			initDefaultParam(request);
			// �����û�Ȩ�޺Ͳ˵�.
			loadPrivilege(userInfo, request);
			String msg = LogUtils.r("�û���"+loginId+" ��½�ɹ�");
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return mapping.findForward(ActionConstant.TO_MAIN_PAGE);
		} catch (Throwable e) {
			e.printStackTrace();
			String err = "��¼�쳣��" + e.getMessage();
			LoggerCacheUtils.getLogger(LoginAction.class).debug(err);
			setResult(false, e.getMessage(), request);
			String tag = getSessionBranchTag(request);
			String msg = LogUtils.r("�û���"+loginId+" �ǳ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return forward("/"+tag);
		}
	}
	
	private void initDefaultParam(HttpServletRequest request) {
		WebUtils.setSessionAttribute(request, "defStartDate", DateUtil.getDateYYYYMMDD(DateUtil.addMonths(DateUtil.getCurrent(), -3)));
		WebUtils.setSessionAttribute(request, "defEndDate", DateUtil.getCurrentDate());
	}

	private boolean canLogin(UserInfo userInfo) throws BizException{
		Integer errorTimes = userInfo.getPwdErrorTimes();
		boolean flag = true;
		if(errorTimes != null && errorTimes >= 3){
			//�ж��Ƿ��Ѵ��Сʱ
			Date errorTime = userInfo.getErrorTime();
			Date now = new Date();
			if (errorTime == null) {
				errorTime = now;
			}
			if ((now.getTime() - errorTime.getTime())/(1000*60) > 30) {
				flag = true;
			} else {
				flag = false;
			}
		} 
		if(flag){
			userInfo.setErrorTime(null);
			userInfo.setPwdErrorTimes(null);
			userService.updateUserInfo(userInfo);
		}
		return flag;
	}

	/**
	 * ��ѯ�������˵�.
	 * 
	 * @param menuTree ���е�Ȩ�޵�
	 * @param firstCode
	 * @param secondCode
	 * @return
	 */
	private Collection<Privilege> getPrivilegeTopButton(final Privilege menuTree, final String firstCode,
		final String secondCode) {
		// ѭ����һ���˵�.
		for (Iterator<Privilege> i = menuTree.getChildren().iterator(); i.hasNext();) {
			Privilege first = i.next();
			// �ҵ���һ���˵�, ѭ���ӽڵ��ѯ�ڶ���.
			if (first.getCode().equals(firstCode)) {
				
				//ѭ���ڶ����˵�
				for (Iterator<Privilege> j = first.getChildren().iterator(); j.hasNext();) {
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
	 * @param allPriv
	 * @return
	 */
	private List<IPrivilege> getMenu(IPrivilege menuTree){
		List<IPrivilege> result = new ArrayList<IPrivilege>();
		
		Collection<IPrivilege> firstLev = menuTree.getAllChildren();//һ���˵�
		if(firstLev == null || firstLev.size() ==0){
			LoggerCacheUtils.getLogger(LoginAction.class).debug("û��һ���˵�");
			return result;
		}
		
		//����һ���˵�
		for(IPrivilege firstP:firstLev){
			if (firstP.isMenu()) {//ֻ����Ŀ¼������Ҷ��
				result.add(firstP);
			}
		}
		
		Iterator iterator = firstLev.iterator();
		while(iterator.hasNext()){
			IPrivilege menu1 = (IPrivilege)iterator.next();
			//���صڶ����˵�
			Collection<IPrivilege> secord = menu1.getAllChildren();
			if(secord!=null && secord.size()>0){
				for(IPrivilege p: secord){
					if(p.isMenu()){
						result.add(p);
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * ����Ȩ�޺���Դ֮���ӳ���ϵ
	 * @param allPriv
	 * @param resources
	 * @return
	 */
	private Map<String,List<PrivilegeResource>> getMapOfPriv(List<IPrivilege> allPriv,List<WebResource> resources){
		Map<String,List<PrivilegeResource>> result = new HashMap<String, List<PrivilegeResource>>();
		for(WebResource r: resources){
			PrivilegeResource pr = (PrivilegeResource)r;
			List<PrivilegeResource> listOfRes = result.get(pr.getLimitId());
			if(listOfRes == null){
				listOfRes = new ArrayList<PrivilegeResource>();
				result.put(pr.getLimitId(), listOfRes);
			}
			listOfRes.add(pr);
		}
		
		return result;
	}

	/**
	 * ��ʼ��Ȩ�޵��url��param
	 * @param p
	 * @param res
	 */
	private void setPrivUrlAndParam(Privilege p,List<PrivilegeResource> res){
		if(res == null || res.size()==0){
			return ;
		}
		
		for(PrivilegeResource r:res){
			if("Y".equals(r.getIsEntry())){//����ڵ�ַ
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
		List<IPrivilege> allPriv = (List<IPrivilege>) WebUtils.getSessionAttribute(request,	Constants.USER_PRIVILEGE);
		Map<String, IPrivilege> mapOfPri = new HashMap<String, IPrivilege>();// Ȩ�޵㵽Ȩ�޵�ӳ��
		for (IPrivilege ip : allPriv) {
			mapOfPri.put(ip.getCode(), ip);
		}
		WebUtils.setSessionAttribute(request, ID_TO_PRI, mapOfPri);//�������е�Ȩ��
	}
	
	/**
	 * �����û�Ȩ�޺Ͳ˵�, ������request(��session) ��.
	 * 
	 * @param user
	 * @param request
	 */
	private void loadPrivilege(final UserInfo user, final HttpServletRequest request) throws Exception {
		// ��ѯ�û��˵�, ���浽session��.
		String userId=null;	
		if(null!=user){
			userId = user.getUserId();
		}
		//�������е�Ȩ�޵�
		List<IPrivilege> allPriv = this.userService.getAllPriv(userId);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE, allPriv);//�������е�Ȩ��
		
		// ��ȡ�û�Ȩ����Դ, ���浽session��.
		List<WebResource> resources = this.userService.getPrivilegeResources(allPriv);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE_RES, WebResource.map(resources));
		
		//����½��Ȩ����Դ����
		dealWithLogPri(request);
		
		//��������Ȩ�޵�·��
		Map<String,List<PrivilegeResource>> map = this.getMapOfPriv(allPriv, resources);//Ȩ�޺�Ȩ����Դ֮���ӳ��
		for(IPrivilege ip:allPriv){
			Privilege p = (Privilege)ip;
			List<PrivilegeResource> res = map.get(p.getLimitId());
			this.setPrivUrlAndParam(p, res);
		}
		
		//��Ȩ�޷��뵽������
		mapPriv(request);
		
		// ���˵�ת��Ϊ����״, ���浽session��.
		IPrivilege root = new Privilege();// ������ڵ�.
		root.setCode(Constants.ROOT_PRIVILEGE_CODE);
		IPrivilege priviligeTree = PrivilegeHelper.getPrivilegeTree(root, allPriv,map);
		WebUtils.setSessionAttribute(request, Constants.USER_MENU, priviligeTree);//����Ȩ����
		
	}
	
	/**
	 * ����½��Ȩ�޼��뵽session��
	 * @param privileges
	 */
	private void dealWithLogPri(HttpServletRequest request) {
		
		Map<String, PrivilegeResource> privileges =
			(Map<String, PrivilegeResource>) WebUtils.getSessionAttribute(request, Constants.USER_PRIVILEGE_RES);
		
		String link = WebResource.getLink(request);
		
		PrivilegeResource pr = new PrivilegeResource();
		pr.setLimitId("login");
		
		privileges.put(link, pr);
	}
	public ActionForward index(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) {
		UserInfo userInfo = getSessionUser(request);
		String forwardUrl = "/";
		if (userInfo != null) {
			//String tag = getSessionBranchTag(request);
			//forwardUrl+= tag;
		} else {
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TAG, null);
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TYPE, null);
		}
		ActionForward forward = new ActionForward(forwardUrl);
		forward.setRedirect(true);
		return forward; 
	}
	
}
