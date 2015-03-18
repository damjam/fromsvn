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
	 * 加载登录用户权限菜单中, 加载第三级菜单.
	 */
	public ActionForward loadUserPrivilegeTopButton(final ActionMapping mapping,
		  ActionForm form, final HttpServletRequest request,
		 HttpServletResponse response) throws Exception {
		String parentCode = request.getParameter("parentCode");// 第一级菜单权限编号(为根节点的子节点).
		String privilegeCode = request.getParameter("privilegeCode");// 第二级菜单权限编号.
		Privilege menuTree = (Privilege) WebUtils.getSessionAttribute(request, Constants.USER_MENU);//目录树

		// 查询第三级菜单.
		request.setAttribute("menus", getPrivilegeTopButton(menuTree, parentCode, privilegeCode));
		return forward("/pages/layout/topbutton.jsp");
	}
	
	/**
	 * 退出
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
			setResult(true, "用户强制离线成功", request);
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
			String msg = LogUtils.r("用户："+userId+" 登出成功");
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return forward;
			//return mapping.findForward("index");// 跳到登陆页面
		} catch (Exception e) {
			String msg = LogUtils.r("用户："+userId+" 登出失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return mapping.findForward("index");// 跳到登陆页面
		}
	}
	

	public ActionForward login(final ActionMapping mapping, final ActionForm form,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String verifyCode = request.getParameter("verifyCode");
		String randomCode = (String) request.getSession().getAttribute("randomCode");
		if (!StringUtils.equalsIgnoreCase(verifyCode, randomCode)) {
			 setResult(false, "验证码不正确", request);
			 return forward("/index_old.jsp");
		}
		String loginId = request.getParameter("code");
		String password = request.getParameter("password");
		
		//禁止同一个机器上用户没有退出就有多个用户
		UserInfo oldUser = getSessionUser(request);
		if(oldUser!=null){
			HttpSession oldSession = request.getSession();
			oldSession.invalidate();
		}
		
		request.setAttribute("code", loginId);
		
		//将信息保存到cookie中
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
			//账号信息正确,检查是否已被锁定
			/*
			if(!canLogin(userInfo)){
				setResult(false, ActionMessageConstant.OPER_FAIL_USER_LOCKED, request);
				return mapping.findForward(ActionConstant.TO_LOGIN_PAGE);
			}*/
			WebUtils.setSessionAttribute(request, Constants.SESSION_USER, userInfo);//将用户保存到session中
			String tag = request.getParameter("tag");
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TYPE, BranchType.getByTag(tag));
			WebUtils.setSessionAttribute(request, Constants.BRANCH_TAG, tag);
			if(StringUtils.equals(userInfo.getUserType(), UserType.CUSTOM.getValue())) {
				//客户用户
				CustInfo custInfo = custInfoService.getCustInfoByUserId(userInfo.getUserId());//用户不存在没有判断
				if(null != custInfo) {
					WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM_ID, custInfo.getId());//将客户Id保存到session中
					WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM, custInfo);//将客户信息保存到session中
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
			// 加载用户权限和菜单.
			loadPrivilege(userInfo, request);
			String msg = LogUtils.r("用户："+loginId+" 登陆成功");
			super.logSuccess(request, UserLogType.OTHER.getValue(), msg);
			return mapping.findForward(ActionConstant.TO_MAIN_PAGE);
		} catch (Throwable e) {
			e.printStackTrace();
			String err = "登录异常：" + e.getMessage();
			LoggerCacheUtils.getLogger(LoginAction.class).debug(err);
			setResult(false, e.getMessage(), request);
			String tag = getSessionBranchTag(request);
			String msg = LogUtils.r("用户："+loginId+" 登出失败,失败原因:{?}", e.getMessage());
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
			//判断是否已达半小时
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
	 * 查询第三级菜单.
	 * 
	 * @param menuTree 所有的权限点
	 * @param firstCode
	 * @param secondCode
	 * @return
	 */
	private Collection<Privilege> getPrivilegeTopButton(final Privilege menuTree, final String firstCode,
		final String secondCode) {
		// 循环第一级菜单.
		for (Iterator<Privilege> i = menuTree.getChildren().iterator(); i.hasNext();) {
			Privilege first = i.next();
			// 找到第一级菜单, 循环子节点查询第二级.
			if (first.getCode().equals(firstCode)) {
				
				//循环第二级菜单
				for (Iterator<Privilege> j = first.getChildren().iterator(); j.hasNext();) {
					Privilege second = j.next();

					if (second.getCode().equals(secondCode)) {// 第三级菜单
						Collection<Privilege> result = new ArrayList<Privilege>();
						Collection<Privilege> thirdPriv = second.getChildren();
						if (thirdPriv == null || thirdPriv.size() == 0) {
							return Collections.emptyList();
						}

						// 将第三级别所有的目录加载，叶子节点不要。
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
	 * 获取所有的菜单 （一级和二级菜单）
	 * @param allPriv
	 * @return
	 */
	private List<IPrivilege> getMenu(IPrivilege menuTree){
		List<IPrivilege> result = new ArrayList<IPrivilege>();
		
		Collection<IPrivilege> firstLev = menuTree.getAllChildren();//一级菜单
		if(firstLev == null || firstLev.size() ==0){
			LoggerCacheUtils.getLogger(LoginAction.class).debug("没有一级菜单");
			return result;
		}
		
		//加载一级菜单
		for(IPrivilege firstP:firstLev){
			if (firstP.isMenu()) {//只加载目录不加在叶子
				result.add(firstP);
			}
		}
		
		Iterator iterator = firstLev.iterator();
		while(iterator.hasNext()){
			IPrivilege menu1 = (IPrivilege)iterator.next();
			//加载第二级菜单
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
	 * 建立权限和资源之间的映射关系
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
	 * 初始化权限点的url和param
	 * @param p
	 * @param res
	 */
	private void setPrivUrlAndParam(Privilege p,List<PrivilegeResource> res){
		if(res == null || res.size()==0){
			return ;
		}
		
		for(PrivilegeResource r:res){
			if("Y".equals(r.getIsEntry())){//是入口地址
				p.setEntry(WebResource.getLink(r.getUrl(), r.getParam()));
			}
		}
	}
	

	/**
	 * 权限点到权限的映射的key
	 */
	public static final String ID_TO_PRI = "id_to_pri";

	/**
	 * 将权限放入到map<权限id,权限>中
	 * 
	 * @param allPriv
	 * @param resources
	 * @param request
	 */
	private void mapPriv(HttpServletRequest request) {
		List<IPrivilege> allPriv = (List<IPrivilege>) WebUtils.getSessionAttribute(request,	Constants.USER_PRIVILEGE);
		Map<String, IPrivilege> mapOfPri = new HashMap<String, IPrivilege>();// 权限点到权限的映射
		for (IPrivilege ip : allPriv) {
			mapOfPri.put(ip.getCode(), ip);
		}
		WebUtils.setSessionAttribute(request, ID_TO_PRI, mapOfPri);//保存所有的权限
	}
	
	/**
	 * 加载用户权限和菜单, 保存在request(或session) 中.
	 * 
	 * @param user
	 * @param request
	 */
	private void loadPrivilege(final UserInfo user, final HttpServletRequest request) throws Exception {
		// 查询用户菜单, 保存到session中.
		String userId=null;	
		if(null!=user){
			userId = user.getUserId();
		}
		//加载所有的权限点
		List<IPrivilege> allPriv = this.userService.getAllPriv(userId);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE, allPriv);//保存所有的权限
		
		// 获取用户权限资源, 保存到session中.
		List<WebResource> resources = this.userService.getPrivilegeResources(allPriv);
		WebUtils.setSessionAttribute(request, Constants.USER_PRIVILEGE_RES, WebResource.map(resources));
		
		//将登陆的权限资源加入
		dealWithLogPri(request);
		
		//设置所有权限的路径
		Map<String,List<PrivilegeResource>> map = this.getMapOfPriv(allPriv, resources);//权限和权限资源之间的映射
		for(IPrivilege ip:allPriv){
			Privilege p = (Privilege)ip;
			List<PrivilegeResource> res = map.get(p.getLimitId());
			this.setPrivUrlAndParam(p, res);
		}
		
		//将权限放入到请求中
		mapPriv(request);
		
		// 将菜单转换为树形状, 保存到session中.
		IPrivilege root = new Privilege();// 构造根节点.
		root.setCode(Constants.ROOT_PRIVILEGE_CODE);
		IPrivilege priviligeTree = PrivilegeHelper.getPrivilegeTree(root, allPriv,map);
		WebUtils.setSessionAttribute(request, Constants.USER_MENU, priviligeTree);//保存权限树
		
	}
	
	/**
	 * 将登陆的权限加入到session中
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
