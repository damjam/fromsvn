package com.ylink.cim.cust.view;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.admin.dao.SysDictDao;
import com.ylink.cim.admin.domain.SysDict;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.view.CookieDealer;
import com.ylink.cim.common.type.AreaCodeType;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.CustFromType;
import com.ylink.cim.common.type.CustType;
import com.ylink.cim.common.type.IdCardType;
import com.ylink.cim.common.type.SexType;
import com.ylink.cim.common.type.SymbolType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.FeildUtils;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.common.util.RandomUtils;
import com.ylink.cim.common.util.SendMailUtil;
import com.ylink.cim.common.util.SendMobilMsgUtil;
import com.ylink.cim.cust.dao.CustInfoDao;
import com.ylink.cim.cust.domain.CustInfo;
import com.ylink.cim.cust.service.CustInfoService;
import com.ylink.cim.invest.service.SignContractService;
import com.ylink.cim.util.DataValidator;

import flink.MD5Util;
import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.web.BaseDispatchAction;

public class CustInfoAction extends BaseDispatchAction {

	private CustInfoService custInfoService = (CustInfoService)getService("custInfoService");
	private CustInfoDao custInfoDao = (CustInfoDao)getService("custInfoDao");
	//private SignContractDao signContractDao = (SignContractDao)getService("signContractDao");
	private SignContractService signContractService = (SignContractService)getService("signContractService");
	private SysDictDao sysDictDao = (SysDictDao)getService("sysDictDao");
	public ActionForward toRegister(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		initData(request);
		WebUtils.setSessionAttribute(request, Constants.SESSION_USER, null);
		WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM, null);
		WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM_ID, null);
		String value = CookieDealer.getCookieValue(CookieDealer.FROM_BRANCH, request);
		BranchType branchType = BranchType.getByTag(value);
		request.setAttribute("branchType", branchType);
		return forward("/pages/cust/custRegister.jsp");
	}
	/**
	 * 注册为一般客户
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doRegister(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CustInfoActionForm actionForm = (CustInfoActionForm)form;
		try {
			Assert.notEmpty(actionForm.getMobile(), "手机号不能为空");
			Assert.notEmpty(actionForm.getLoginPwd(), "登录密码不能为空");
			Assert.notEmpty(actionForm.getConfirmPwd(), "确认密码不能为空");
			CustInfo existCustInfo = custInfoDao.getUniqueCustInfo("mobile", actionForm.getMobile());
			Assert.isNull(existCustInfo, LogUtils.r("号码{?}已被使用，请更改其他手机号码", actionForm.getMobile()));
			String dymCode = actionForm.getDymCode();
			String realCode = (String)request.getSession().getAttribute("dymCode");
			Assert.notEmpty(realCode, "请获取并填写动态码");
			Assert.equals(dymCode, realCode, "动态码错误");
			Assert.equals(actionForm.getLoginPwd(), actionForm.getConfirmPwd(), "两次密码输入不一致");
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			custInfo.setCustType(CustType.TYPE_NORMAL.getValue());
			custInfo.setFromType(CustFromType.TYPE_0.getValue());
			//订阅资讯
			
			String v = MD5Util.MD5(String.valueOf(new Date().getTime()));
			if (StringUtils.isEmpty(actionForm.getEmail())) {
				actionForm.setSubsEmail(Symbol.NO);
			} else {
				if ("A".equals(actionForm.getSubsEmail())) {
					custInfo.setvMailStr(v);
					custInfo.setvSendTime(DateUtil.getCurrent());
				}
			}
			String branchNo = getAgencyBranchNo(request);
			custInfo.setBranchNo(branchNo);
			//执行保存
			String id = custInfoService.addCustBasicInfo(custInfo);
			WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM_ID, id);
			if ("A".equals(actionForm.getSubsEmail()) && StringUtils.isNotEmpty(actionForm.getEmail())) {
				String contextPath = request.getContextPath();
				String receiver = actionForm.getEmail();
				SendMailUtil.sendActiveMail(contextPath, receiver, id, v);
				
				setResult(true, "注册成功，请立即激活邮箱订阅功能", request);
			} else {
				setResult(true, "注册成功", request);
			}
			
			setReturnUrl("/", request);
			String msgs = LogUtils.r("客户注册为一般客户成功，注册信息为：手机{?},邮箱{?},是否手机订阅{?}，是否邮箱订阅{?}", 
					actionForm.getMobile(),actionForm.getEmail(),actionForm.getSubsPhone(),actionForm.getSubsEmail());
			super.logSuccess(request, UserLogType.ADD.getValue(), msgs);
		} catch (BizException e) {
			actionForm.setLoginPwd("");
			actionForm.setConfirmPwd("");
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("客户注册为一般客户失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return toRegister(mapping, actionForm, request, response);
		}
		return success(mapping);
	}
	
	
	private String getAgencyBranchNo(HttpServletRequest request) {
		String linkCode = CookieDealer.getCookieValue(CookieDealer.FROM_BRANCH, request);
		String branchNo = null;
		try {
			SysDict dict = (SysDict)sysDictDao.getUniqueResult(SysDict.class, "remark", linkCode);
			if (dict != null) {
				branchNo = dict.getId().getDictValue();
			}
			if (StringUtils.isEmpty(branchNo)) {
				branchNo = BranchType.getByTag(linkCode).getValue();
			}
			return branchNo;
		} catch (Exception e) {
			return BranchType.HQ_0000.getValue();
		}

	}
	/**
	 * 转向修改客户信息页面(客户已登录)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustInfoActionForm actionForm = (CustInfoActionForm)form;
		String custId = getSessionCustId(request);
		Assert.notNull(custId, "找不到客户号");
		CustInfo custInfo = custInfoDao.findById(custId);
		BeanUtils.copyProperties(actionForm, custInfo);
		actionForm.setOriMobile(custInfo.getMobile());
		actionForm.setOriEmail(custInfo.getEmail());
		if ("A".equals(custInfo.getSubsEmail())) {
			actionForm.setSubsEmail(Symbol.YES);
		}
		initData(request);
		if (CustType.TYPE_NORMAL.getValue().equals(custInfo.getCustType())) {
			return forward("/pages/cust/basicInfoUpdate.jsp");
		} else if(CustType.TYPE_MEMBER.getValue().equals(custInfo.getCustType())){
			return forward("/pages/cust/merberInfoUpdate.jsp");
		}
		return null;
	}
	/**
	 * 修改客户信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Assert.isTrue(isValidKey(request), "请勿重复提交");
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			String mobile = actionForm.getMobile();
			Assert.notEmpty(mobile, "手机号为空");
			String oriMobile = actionForm.getOriMobile();
			//手机号被更改
			if (!oriMobile.equals(mobile)) {
				String dymCode = actionForm.getDymCode();
				String realCode = (String)request.getSession().getAttribute("dymCode");
				Assert.notEmpty(realCode, "请获取并填写动态码");
				Date sendCodeTime = (Date)WebUtils.getSessionAttribute(request, "sendTime");
				//已超过10分钟
				if (DateUtil.addMins(sendCodeTime, 10).before(DateUtil.getCurrent())) {
					throw new BizException("动态码已失效，请重新获取");
				}
				Assert.equals(dymCode, realCode, "动态码错误");
				String sessMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
				//防止发送动态码后又改为另一个手机号
				Assert.equals(sessMobile, mobile, LogUtils.r("发送动态的码的手机号不是{?}", mobile));
			} 
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			//custInfo.setvMailStr(vMailStr);
			
			custInfo.setId(getSessionCustId(request));
			
			setReturnUrl("/custInfoAction.do?action=toUpdate&id="+actionForm.getId(), request);
			String log = "修改成功";
			//订阅且更换了邮箱
			if (StringUtils.isEmpty(actionForm.getEmail())) {
				actionForm.setSubsEmail(Symbol.NO);
			}
			if ("A".equals(actionForm.getSubsEmail())) {
				String v = MD5Util.MD5(String.valueOf(new Date().getTime()));
				String contextPath = request.getContextPath();
				String receiver = actionForm.getEmail();
				SendMailUtil.sendActiveMail(contextPath, receiver, getSessionCustId(request), v);
				log+=",请立即登录邮箱激活订阅功能";
				custInfo.setvMailStr(v);
				custInfo.setvSendTime(DateUtil.getCurrent());
			}
			custInfoService.updateCustInfo(custInfo);
			logSuccess(request, UserLogType.UPDATE.getValue(), log);
			setResult(true, log, request);
			String msgs = LogUtils.r("客户修改个人资料成功，修改后信息为：{?}", FeildUtils.toString(custInfo));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
//			e.printStackTrace();
			String msg = LogUtils.r("客户修改个人资料失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return toUpdate(mapping, form, request, response);
		} 
	}
	/**
	 * 修改密码
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toResetPwd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return forward("/pages/cust/custPwdReset.jsp");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward toAddInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			String idCard = actionForm.getIdCard();
			CustInfo existCust = custInfoDao.getUniqueCustInfo("idCard", idCard);
			if (existCust != null) {
				setResult(false, LogUtils.r("证件号码{?}已被使用", idCard), request);
				return toAddSignInfo(mapping, actionForm, request, response);
			}
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			String id = getSessionCustId(request);
			custInfo.setId(id);
			custInfoService.addMemberInfo(custInfo);
			//访问定投系统,返回支付渠道签约页面
			//
			setResult(true, "保存信息成功，即将转向签约页面", request);
			return success(mapping, "/custInfoAction.do?action=doSign");
		} catch (Exception e) {
			e.printStackTrace();
			return forward("/pages/cust/signContract.jsp");
		}
	}*/
	//未登录
	public ActionForward verifySignInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			initData(request);
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCard", actionForm.getIdCard());
			List<CustInfo> list = custInfoDao.findByParams(map);
			Assert.isEmpty(list, LogUtils.r("证件号码{?}已被使用", actionForm.getIdCard()));
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/cust/verifySignInfo.jsp");
			
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			initData(request);
			return forward("/pages/cust/signContract.jsp");
		}
	}
	/**
	 * 已登录普通用户开通业务时验证
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verifyMemberInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCard", actionForm.getIdCard());
			List<CustInfo> list = custInfoDao.findByParams(map);
			Assert.isEmpty(list, LogUtils.r("证件号码{?}已被使用", actionForm.getIdCard()));
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/cust/openAcctVerify.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			initData(request);
			return forward("/pages/cust/openAcct.jsp");
		}
	}
	
	//保存会员到session，转向信息确认页面
	public ActionForward addSignInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			String idCard = actionForm.getIdCard().trim();
			//身份证号格式检验
			if(StringUtils.equals(IdCardType.CARD_0.getValue(), actionForm.getCardType())) {
				if(!DataValidator.isIDNumber(idCard)) {
					setResult(false, LogUtils.r("证件号码{?}格式不正确", idCard), request);
					initData(request);
					return forward("/pages/cust/signContract.jsp");
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCard", actionForm.getIdCard());
			List<CustInfo> list = custInfoDao.findByParams(map);
			if (list.size() > 0) {
				setResult(false, LogUtils.r("证件号码{?}已被使用", idCard), request);
				initData(request);
				return forward("/pages/cust/signContract.jsp");
			}
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			String id = getSessionCustId(request);
			custInfo.setId(id);
			custInfoService.addMemberInfo(custInfo, getSessionBranch(request).getValue(), request, response);
			//访问定投系统,返回支付渠道签约页面
			//
			setResult(true, "操作成功", request);
			setReturnUrl("/custInfoAction.do?action=doSign", request);
			return success(mapping);
		} catch (Exception e) {
			e.printStackTrace();
			return forward("/pages/cust/signContract.jsp");
		}
	}
	@Deprecated
	public ActionForward preSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			String idCard = actionForm.getIdCard().trim();
			Map<String, Object> map = getParaMap();
			map.put("idCard", idCard);
			List<CustInfo> list = custInfoDao.findByParams(map);
			if (list.size() > 0) {
				setResult(false, LogUtils.r("证件号码{?}已被使用", idCard), request);
				return toAddSignInfo(mapping, actionForm, request, response);
			}
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			String id = getSessionCustId(request);
			custInfo.setId(id);
			custInfoService.addMemberInfo(custInfo, getSessionBranch(request).getValue(), request, response);
			//访问定投系统,返回支付渠道签约页面
			//
			setResult(true, "操作成功", request);
			setReturnUrl("/custInfoAction.do?action=viewCustInfo", request);
			return success(mapping);
		} catch (BizException e) {
			e.printStackTrace();
			initData(request);
			setResult(false, e.getMessage(), request);
			return forward("/pages/cust/openAcct.jsp");
		}
	}
	//保存基本信息并进入填写详细信息页面
	public ActionForward toSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			initData(request);
			//判断是否由确认页面返回
			String u = request.getParameter("u");
			if (StringUtils.isNotEmpty(u)) {
				BeanUtils.copyProperties(form, WebUtils.getSessionAttribute(request, "form"));
				initData(request);
				return forward("/pages/cust/signContract.jsp");
			}
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			//String custId = getSessionCustId(request);
			//新客户注册
			if (getSessionCust(request) == null) {
				String dymCode = actionForm.getDymCode();
				String realCode = (String)request.getSession().getAttribute("dymCode");
				Assert.notEmpty(realCode, "请获取并填写动态码");
				Date sendCodeTime = (Date)WebUtils.getSessionAttribute(request, "sendTime");
				//已超过10分钟
				if (DateUtil.addMins(sendCodeTime, 10).before(DateUtil.getCurrent())) {
					throw new BizException("动态码已失效，请重新获取");
				}
				if (!StringUtils.equals(dymCode, realCode)) {
					throw new BizException("动态码错误");
				}
				CustInfo custInfo = new CustInfo();
				BeanUtils.copyProperties(custInfo, actionForm);
				custInfo.setCustType(CustType.TYPE_NORMAL.getValue());
				String branchNo = getAgencyBranchNo(request);
				custInfo.setBranchNo(branchNo);
				String id = custInfoService.addCustBasicInfo(custInfo);
				WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM_ID, id);
				//custInfoService.updateCustInfo(custInfo);
			} else {
				//普通客户升级为会员
				CustInfo custInfo = custInfoDao.findById(getSessionCustId(request));
				if (custInfo != null && CustType.TYPE_MEMBER.getValue().equals(custInfo.getCustType())) {
					setResult(false, "您当前已是会员客户!", request);
					return viewCustInfo(mapping, actionForm, request, response);
				}
			}
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toRegister(mapping, form, request, response);
		}
		
		initData(request);
		setResult(true, "您已注册成功，登录账号为您的手机号", request);
		return forward("/pages/cust/signContract.jsp");
	}
	//普通客户添加签约信息成为会员(已登录)
	public ActionForward toAddSignInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		String u = request.getParameter("u");
		if (Symbol.YES.equals(u)) {
			BeanUtils.copyProperties(form, (ActionForm)WebUtils.getSessionAttribute(request, "form"));
		}
		return forward("/pages/cust/openAcct.jsp");
	}
	
	//签约
	public ActionForward doSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String custId = getSessionCustId(request);
			//super.get
			CustInfoActionForm actionForm = (CustInfoActionForm)WebUtils.getSessionAttribute(request, "form");
			CustInfo custInfo = new CustInfo();;
			BeanUtils.copyProperties(custInfo, actionForm);
			custInfo.setId(custId);
			custInfo.setBusiType(actionForm.getBusiType());
			String accreditId = custInfoService.addMemberInfo(custInfo, getSessionBranch(request).getValue(), request, response);
			actionForm.setAccreditId(accreditId);
			
			String msgs = LogUtils.r("客户签约为会员客户成功，客户信息为：", FeildUtils.toString(custInfo));
			super.logSuccess(request, UserLogType.ADD.getValue(), msgs);
			
			return null;

		} catch (BizException e) {
			setResult(false, "签约失败", request);
//			e.printStackTrace();
			String msg = LogUtils.r("客户签约失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return failure(mapping);
		}
		//签约成功
	}
	/**
	 * 注册或修改客户信息时获取手机动态码
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendDymCode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject object = new JSONObject();
		try {
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			String mobile = actionForm.getMobile().trim();
			String operType = request.getParameter("operType");
			
			Assert.notNull(mobile, "手机号不能为空");
			Map<String, Object> params = getParaMap();
			params.put("mobile", mobile);
			CustInfo custInfo = custInfoDao.getUniqueCustInfo("mobile", mobile);
			if (custInfo != null) {
				object.put("result", false);
				if (UserLogType.UPDATE.getValue().equals(operType)) {
					object.put("msg", LogUtils.r("号码{?}已被其他客户注册，不可使用", mobile));
				} else {
					if (CustFromType.TYPE_0.getValue().equals(custInfo.getFromType()) || StringUtils.isEmpty(custInfo.getFromType())) {
						object.put("msg", "该手机号已注册，请直接登录");
					} else {
						object.put("msg", "系统检测到您已通过其他渠道注册\n无需再次注册，请直接登录");
					}
				}
				respond(response, object.toString());
				return null;
			}
			//测试期间,动态码暂设为111111
			String randomCode = RandomUtils.getRandomNumber(6);
			if(ParaManager.isProductMode()){
				SendMobilMsgUtil.sendMsgFw(mobile, "动态码为:"+randomCode);
			}else {
				randomCode = UserInfo.INIT_PWD;
			}
			WebUtils.setSessionAttribute(request, "dymCode", randomCode);
			WebUtils.setSessionAttribute(request, "mobile", mobile);
			WebUtils.setSessionAttribute(request, "sendTime", DateUtil.getCurrent());
			object.put("result", true);
			object.put("msg", "动态码已发送");
			respond(response, object.toString());
		} catch (BizException e) {
			object = new JSONObject();
			object.put("result", false);
			object.put("msg", "获取动态码失败,请稍候重试");
			respond(response, object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//找回密码时获取手机动态码
	public ActionForward sendDymCodeForResetPwd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String mobile = request.getParameter("mobile");
		CustInfo custInfo = (CustInfo)custInfoDao.getUniqueResult(CustInfo.class, "mobile", mobile);
		
		JSONObject object = new JSONObject();
		if (custInfo != null) {
			object.put("result", true);
			object.put("msg", "动态码已发送");
			WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM_ID, custInfo.getId());
			String randomCode = RandomUtils.getRandomNumber(6);
			if(ParaManager.isProductMode()){
				SendMobilMsgUtil.sendMsgFw(mobile, "动态码为:"+randomCode);
			}else {
				randomCode = UserInfo.INIT_PWD;
			}
			WebUtils.setSessionAttribute(request, "dymCode", randomCode);
			WebUtils.setSessionAttribute(request, "mobile", mobile);
			WebUtils.setSessionAttribute(request, "sendTime", DateUtil.getCurrent());
		} else {
			object.put("result", false);
			object.put("msg", "该号码未在本系统注册");
		}
		respond(response, object.toString());
		return null;
	}
	public ActionForward verifyDymCodeForRestPwd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustInfoActionForm actionForm = (CustInfoActionForm)form;
		String dymCode = actionForm.getDymCode();
		String mobile = actionForm.getMobile();
		String realCode = (String)WebUtils.getSessionAttribute(request, "dymCode");
		String realMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
		if (!StringUtils.equals(dymCode, realCode)) {
			setResult(false, "动态码错误", request);
			return toResetPwd(mapping, actionForm, request, response);
		}
		if (!StringUtils.equals(mobile, realMobile)) {
			setResult(false, LogUtils.r("手机号{?}不是与发送动态码号码不一致", mobile), request);
			return toResetPwd(mapping, actionForm, request, response);
		}
		
		return forward("/pages/cust/inputNewPwd.jsp");
	}
	public ActionForward doResetPwd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustInfoActionForm actionForm = (CustInfoActionForm)form;
		String loginPwd = actionForm.getLoginPwd();
		String confirmPwd = actionForm.getConfirmPwd();
		if (!StringUtils.equals(loginPwd, confirmPwd)) {
			setResult(false, "两次密码输入不一致", request);
			return forward("/pages/cust/inputNewPwd.jsp");
		} 
		String custId = (String)WebUtils.getSessionAttribute(request, Constants.SESSION_CUSTOM_ID);
		custInfoService.resetCustPwd(custId, loginPwd);
		setReturnUrl("/", request);
		setResult(true, "重置成功，请牢记您的密码", request);
		String msgs = LogUtils.r("客户{?}找回密码成功",custId);
		super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
		return success(mapping);
	}
	public ActionForward viewCustInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		/String id = getSessionCustId(request);
		String custId = getSessionCustId(request);
		Assert.notNull(custId, "找不到客户号");
		CustInfo custInfo = custInfoDao.findById(custId);
		if ("A".equals(custInfo.getSubsEmail())) {
			custInfo.setSubsEmail(Symbol.YES);
		}
		/*if (CustType.TYPE_MEMBER.getValue().equals(custInfo.getCustType())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("custId", id);
			List<SignContract> list = (List<SignContract>)signContractDao.findByParams(params);
			SignContract sign = list.get(0);
			request.setAttribute("sign", sign);
		}*/
		request.setAttribute("cust", custInfo);
		return forward("/pages/cust/custInfo.jsp");
	}
	public ActionForward checkDate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String date = request.getParameter("date");
		JSONObject object = new JSONObject();
		try {
			DateUtil.string2Date(date, "yyyyMMdd");
			object.put("isDate", true);
		} catch (Exception e) {
			object.put("isDate", false);
		}
		respond(response, object.toString());
		return null;
	}
	private void initData(HttpServletRequest request) throws Exception{
		request.setAttribute("idCardTypes", IdCardType.ALL.values());
		ParaManager.setDictInReq(request, SysDictType.valueOf("SignChnl"+getSessionBranch(request).getValue().substring(2, 4)), "payChnlTypes");
		SexType.setInReq(request);
		SymbolType.setInReq(request);
		AreaCodeType.setInReq(request);
		ParaManager.setDictInReq(request, SysDictType.valueOf("BusiType"+getSessionBranch(request).getValue().substring(2, 4)), "busiTypes");
	}
	
	/**
	 * 验证邮箱
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verifyEmail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String c = request.getParameter("c");
		String v = request.getParameter("v");
		CustInfo custInfo = custInfoDao.findById(c);
		//邮箱验证发送时间
		Date vSendTime = custInfo.getvSendTime();
		Date now = new Date();
		//超过半小时
		if((now.getTime() - vSendTime.getTime())/(1000*60) > 30){
			setResult(false, "邮箱验证已过期，请重新激活", request);
			return failure(mapping);
		}
		if (!StringUtils.equals(v, custInfo.getvMailStr())) {
			setResult(false, "验证失败，请重新激活", request);
			return failure(mapping);
		}
		if (Symbol.YES.equals(custInfo.getSubsEmail())) {
			setResult(false, "邮箱已激活，请勿重复激活", request);
			return failure(mapping);
		}
		//验证通过
		custInfo.setActiveMailTime(new Date());
		custInfo.setSubsEmail(Symbol.YES);
		custInfoService.updateCustInfo(custInfo);
		setResult(true, "邮箱已激活订阅功能", request);
		setReturnUrl("/", request);
		String msgs = LogUtils.r("客户{?}邮箱激活订阅功能成功",custInfo.getId());
		super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
		return success(mapping);
	}
	//取消邮箱订阅
	public ActionForward cancelEmailSubs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String c = request.getParameter("c");
		String v = request.getParameter("v");
		CustInfo custInfo = custInfoDao.findById(c);
		if (!StringUtils.equals(v, custInfo.getvMailStr())) {
			setResult(false, "操作无效", request);
			return failure(mapping);
		}
		custInfo.setSubsEmail(Symbol.NO);
		custInfoService.updateCustInfo(custInfo);
		setResult(true, "取消成功", request);
		setReturnUrl("/", request);
		String msgs = LogUtils.r("客户{?}取消邮箱订阅功能成功",custInfo.getId());
		super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
		return success(mapping);
	}
	public ActionForward toChangePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Assert.notNull(getSessionCust(request), "找不到客户号");
		return forward("/pages/admin/privilege/changePassword.jsp");
	}
	
	
	public ActionForward checkSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			//如果签约失败，删除签约记录
			CustInfoActionForm actionForm = (CustInfoActionForm)request.getSession().getAttribute("form");
			String accreditId = actionForm.getAccreditId();
			boolean signResult = signContractService.checkSign(accreditId);
			Assert.isTrue(signResult, "签约失败");
			if (getSessionCust(request) == null) {
				setResult(true, "业务开通成功,请登录", request);
				String msgs = LogUtils.r("客户签约认证成功，授权号{?}",accreditId);
				super.logSuccess(request, UserLogType.CHECK.getValue(), msgs);
			}else {
				setResult(true, "你已为会员客户,请重新登录", request);
			}
			setReturnUrl("/"+getSessionBranchTag(request), request);
			return success(mapping);
		} catch (Exception e) {
			String msg = LogUtils.r("客户签约认证失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.CHECK.getValue(), msg);
			setReturnUrl("/"+getSessionBranchTag(request), request);
			setResult(false, e.getMessage(), request);
			return failure(mapping);
		}
	}
}
