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
import com.ylink.cim.user.domain.UserInfo;
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
	 * ע��Ϊһ��ͻ�
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
			Assert.notEmpty(actionForm.getMobile(), "�ֻ��Ų���Ϊ��");
			Assert.notEmpty(actionForm.getLoginPwd(), "��¼���벻��Ϊ��");
			Assert.notEmpty(actionForm.getConfirmPwd(), "ȷ�����벻��Ϊ��");
			CustInfo existCustInfo = custInfoDao.getUniqueCustInfo("mobile", actionForm.getMobile());
			Assert.isNull(existCustInfo, LogUtils.r("����{?}�ѱ�ʹ�ã�����������ֻ�����", actionForm.getMobile()));
			String dymCode = actionForm.getDymCode();
			String realCode = (String)request.getSession().getAttribute("dymCode");
			Assert.notEmpty(realCode, "���ȡ����д��̬��");
			Assert.equals(dymCode, realCode, "��̬�����");
			Assert.equals(actionForm.getLoginPwd(), actionForm.getConfirmPwd(), "�����������벻һ��");
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			custInfo.setCustType(CustType.TYPE_NORMAL.getValue());
			custInfo.setFromType(CustFromType.TYPE_0.getValue());
			//������Ѷ
			
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
			//ִ�б���
			String id = custInfoService.addCustBasicInfo(custInfo);
			WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM_ID, id);
			if ("A".equals(actionForm.getSubsEmail()) && StringUtils.isNotEmpty(actionForm.getEmail())) {
				String contextPath = request.getContextPath();
				String receiver = actionForm.getEmail();
				SendMailUtil.sendActiveMail(contextPath, receiver, id, v);
				
				setResult(true, "ע��ɹ����������������䶩�Ĺ���", request);
			} else {
				setResult(true, "ע��ɹ�", request);
			}
			
			setReturnUrl("/", request);
			String msgs = LogUtils.r("�ͻ�ע��Ϊһ��ͻ��ɹ���ע����ϢΪ���ֻ�{?},����{?},�Ƿ��ֻ�����{?}���Ƿ����䶩��{?}", 
					actionForm.getMobile(),actionForm.getEmail(),actionForm.getSubsPhone(),actionForm.getSubsEmail());
			super.logSuccess(request, UserLogType.ADD.getValue(), msgs);
		} catch (BizException e) {
			actionForm.setLoginPwd("");
			actionForm.setConfirmPwd("");
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("�ͻ�ע��Ϊһ��ͻ�ʧ��,ʧ��ԭ��:{?}", e.getMessage());
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
			return BranchType.SZGOLD.getValue();
		}

	}
	/**
	 * ת���޸Ŀͻ���Ϣҳ��(�ͻ��ѵ�¼)
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
		Assert.notNull(custId, "�Ҳ����ͻ���");
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
	 * �޸Ŀͻ���Ϣ
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
			Assert.isTrue(isValidKey(request), "�����ظ��ύ");
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			String mobile = actionForm.getMobile();
			Assert.notEmpty(mobile, "�ֻ���Ϊ��");
			String oriMobile = actionForm.getOriMobile();
			//�ֻ��ű�����
			if (!oriMobile.equals(mobile)) {
				String dymCode = actionForm.getDymCode();
				String realCode = (String)request.getSession().getAttribute("dymCode");
				Assert.notEmpty(realCode, "���ȡ����д��̬��");
				Date sendCodeTime = (Date)WebUtils.getSessionAttribute(request, "sendTime");
				//�ѳ���10����
				if (DateUtil.addMins(sendCodeTime, 10).before(DateUtil.getCurrent())) {
					throw new BizException("��̬����ʧЧ�������»�ȡ");
				}
				Assert.equals(dymCode, realCode, "��̬�����");
				String sessMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
				//��ֹ���Ͷ�̬����ָ�Ϊ��һ���ֻ���
				Assert.equals(sessMobile, mobile, LogUtils.r("���Ͷ�̬������ֻ��Ų���{?}", mobile));
			} 
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			//custInfo.setvMailStr(vMailStr);
			
			custInfo.setId(getSessionCustId(request));
			
			setReturnUrl("/custInfoAction.do?action=toUpdate&id="+actionForm.getId(), request);
			String log = "�޸ĳɹ�";
			//�����Ҹ���������
			if (StringUtils.isEmpty(actionForm.getEmail())) {
				actionForm.setSubsEmail(Symbol.NO);
			}
			if ("A".equals(actionForm.getSubsEmail())) {
				String v = MD5Util.MD5(String.valueOf(new Date().getTime()));
				String contextPath = request.getContextPath();
				String receiver = actionForm.getEmail();
				SendMailUtil.sendActiveMail(contextPath, receiver, getSessionCustId(request), v);
				log+=",��������¼���伤��Ĺ���";
				custInfo.setvMailStr(v);
				custInfo.setvSendTime(DateUtil.getCurrent());
			}
			custInfoService.updateCustInfo(custInfo);
			logSuccess(request, UserLogType.UPDATE.getValue(), log);
			setResult(true, log, request);
			String msgs = LogUtils.r("�ͻ��޸ĸ������ϳɹ����޸ĺ���ϢΪ��{?}", FeildUtils.toString(custInfo));
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
//			e.printStackTrace();
			String msg = LogUtils.r("�ͻ��޸ĸ�������ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			return toUpdate(mapping, form, request, response);
		} 
	}
	/**
	 * �޸�����
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
				setResult(false, LogUtils.r("֤������{?}�ѱ�ʹ��", idCard), request);
				return toAddSignInfo(mapping, actionForm, request, response);
			}
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			String id = getSessionCustId(request);
			custInfo.setId(id);
			custInfoService.addMemberInfo(custInfo);
			//���ʶ�Ͷϵͳ,����֧������ǩԼҳ��
			//
			setResult(true, "������Ϣ�ɹ�������ת��ǩԼҳ��", request);
			return success(mapping, "/custInfoAction.do?action=doSign");
		} catch (Exception e) {
			e.printStackTrace();
			return forward("/pages/cust/signContract.jsp");
		}
	}*/
	//δ��¼
	public ActionForward verifySignInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			initData(request);
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCard", actionForm.getIdCard());
			List<CustInfo> list = custInfoDao.findByParams(map);
			Assert.isEmpty(list, LogUtils.r("֤������{?}�ѱ�ʹ��", actionForm.getIdCard()));
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/cust/verifySignInfo.jsp");
			
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			initData(request);
			return forward("/pages/cust/signContract.jsp");
		}
	}
	/**
	 * �ѵ�¼��ͨ�û���ͨҵ��ʱ��֤
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
			Assert.isEmpty(list, LogUtils.r("֤������{?}�ѱ�ʹ��", actionForm.getIdCard()));
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/cust/openAcctVerify.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			initData(request);
			return forward("/pages/cust/openAcct.jsp");
		}
	}
	
	//�����Ա��session��ת����Ϣȷ��ҳ��
	public ActionForward addSignInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			String idCard = actionForm.getIdCard().trim();
			//���֤�Ÿ�ʽ����
			if(StringUtils.equals(IdCardType.CARD_0.getValue(), actionForm.getCardType())) {
				if(!DataValidator.isIDNumber(idCard)) {
					setResult(false, LogUtils.r("֤������{?}��ʽ����ȷ", idCard), request);
					initData(request);
					return forward("/pages/cust/signContract.jsp");
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCard", actionForm.getIdCard());
			List<CustInfo> list = custInfoDao.findByParams(map);
			if (list.size() > 0) {
				setResult(false, LogUtils.r("֤������{?}�ѱ�ʹ��", idCard), request);
				initData(request);
				return forward("/pages/cust/signContract.jsp");
			}
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			String id = getSessionCustId(request);
			custInfo.setId(id);
			custInfoService.addMemberInfo(custInfo, getSessionBranch(request).getValue(), request, response);
			//���ʶ�Ͷϵͳ,����֧������ǩԼҳ��
			//
			setResult(true, "�����ɹ�", request);
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
				setResult(false, LogUtils.r("֤������{?}�ѱ�ʹ��", idCard), request);
				return toAddSignInfo(mapping, actionForm, request, response);
			}
			CustInfo custInfo = new CustInfo();
			BeanUtils.copyProperties(custInfo, actionForm);
			String id = getSessionCustId(request);
			custInfo.setId(id);
			custInfoService.addMemberInfo(custInfo, getSessionBranch(request).getValue(), request, response);
			//���ʶ�Ͷϵͳ,����֧������ǩԼҳ��
			//
			setResult(true, "�����ɹ�", request);
			setReturnUrl("/custInfoAction.do?action=viewCustInfo", request);
			return success(mapping);
		} catch (BizException e) {
			e.printStackTrace();
			initData(request);
			setResult(false, e.getMessage(), request);
			return forward("/pages/cust/openAcct.jsp");
		}
	}
	//���������Ϣ��������д��ϸ��Ϣҳ��
	public ActionForward toSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			initData(request);
			//�ж��Ƿ���ȷ��ҳ�淵��
			String u = request.getParameter("u");
			if (StringUtils.isNotEmpty(u)) {
				BeanUtils.copyProperties(form, WebUtils.getSessionAttribute(request, "form"));
				initData(request);
				return forward("/pages/cust/signContract.jsp");
			}
			CustInfoActionForm actionForm = (CustInfoActionForm)form;
			//String custId = getSessionCustId(request);
			//�¿ͻ�ע��
			if (getSessionCust(request) == null) {
				String dymCode = actionForm.getDymCode();
				String realCode = (String)request.getSession().getAttribute("dymCode");
				Assert.notEmpty(realCode, "���ȡ����д��̬��");
				Date sendCodeTime = (Date)WebUtils.getSessionAttribute(request, "sendTime");
				//�ѳ���10����
				if (DateUtil.addMins(sendCodeTime, 10).before(DateUtil.getCurrent())) {
					throw new BizException("��̬����ʧЧ�������»�ȡ");
				}
				if (!StringUtils.equals(dymCode, realCode)) {
					throw new BizException("��̬�����");
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
				//��ͨ�ͻ�����Ϊ��Ա
				CustInfo custInfo = custInfoDao.findById(getSessionCustId(request));
				if (custInfo != null && CustType.TYPE_MEMBER.getValue().equals(custInfo.getCustType())) {
					setResult(false, "����ǰ���ǻ�Ա�ͻ�!", request);
					return viewCustInfo(mapping, actionForm, request, response);
				}
			}
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toRegister(mapping, form, request, response);
		}
		
		initData(request);
		setResult(true, "����ע��ɹ�����¼�˺�Ϊ�����ֻ���", request);
		return forward("/pages/cust/signContract.jsp");
	}
	//��ͨ�ͻ����ǩԼ��Ϣ��Ϊ��Ա(�ѵ�¼)
	public ActionForward toAddSignInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		String u = request.getParameter("u");
		if (Symbol.YES.equals(u)) {
			BeanUtils.copyProperties(form, (ActionForm)WebUtils.getSessionAttribute(request, "form"));
		}
		return forward("/pages/cust/openAcct.jsp");
	}
	
	//ǩԼ
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
			
			String msgs = LogUtils.r("�ͻ�ǩԼΪ��Ա�ͻ��ɹ����ͻ���ϢΪ��", FeildUtils.toString(custInfo));
			super.logSuccess(request, UserLogType.ADD.getValue(), msgs);
			
			return null;

		} catch (BizException e) {
			setResult(false, "ǩԼʧ��", request);
//			e.printStackTrace();
			String msg = LogUtils.r("�ͻ�ǩԼʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return failure(mapping);
		}
		//ǩԼ�ɹ�
	}
	/**
	 * ע����޸Ŀͻ���Ϣʱ��ȡ�ֻ���̬��
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
			
			Assert.notNull(mobile, "�ֻ��Ų���Ϊ��");
			Map<String, Object> params = getParaMap();
			params.put("mobile", mobile);
			CustInfo custInfo = custInfoDao.getUniqueCustInfo("mobile", mobile);
			if (custInfo != null) {
				object.put("result", false);
				if (UserLogType.UPDATE.getValue().equals(operType)) {
					object.put("msg", LogUtils.r("����{?}�ѱ������ͻ�ע�ᣬ����ʹ��", mobile));
				} else {
					if (CustFromType.TYPE_0.getValue().equals(custInfo.getFromType()) || StringUtils.isEmpty(custInfo.getFromType())) {
						object.put("msg", "���ֻ�����ע�ᣬ��ֱ�ӵ�¼");
					} else {
						object.put("msg", "ϵͳ��⵽����ͨ����������ע��\n�����ٴ�ע�ᣬ��ֱ�ӵ�¼");
					}
				}
				respond(response, object.toString());
				return null;
			}
			//�����ڼ�,��̬������Ϊ111111
			String randomCode = RandomUtils.getRandomNumber(6);
			if(ParaManager.isProductMode()){
				SendMobilMsgUtil.sendMsgFw(mobile, "��̬��Ϊ:"+randomCode);
			}else {
				randomCode = UserInfo.INIT_PWD;
			}
			WebUtils.setSessionAttribute(request, "dymCode", randomCode);
			WebUtils.setSessionAttribute(request, "mobile", mobile);
			WebUtils.setSessionAttribute(request, "sendTime", DateUtil.getCurrent());
			object.put("result", true);
			object.put("msg", "��̬���ѷ���");
			respond(response, object.toString());
		} catch (BizException e) {
			object = new JSONObject();
			object.put("result", false);
			object.put("msg", "��ȡ��̬��ʧ��,���Ժ�����");
			respond(response, object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//�һ�����ʱ��ȡ�ֻ���̬��
	public ActionForward sendDymCodeForResetPwd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String mobile = request.getParameter("mobile");
		CustInfo custInfo = (CustInfo)custInfoDao.getUniqueResult(CustInfo.class, "mobile", mobile);
		
		JSONObject object = new JSONObject();
		if (custInfo != null) {
			object.put("result", true);
			object.put("msg", "��̬���ѷ���");
			WebUtils.setSessionAttribute(request, Constants.SESSION_CUSTOM_ID, custInfo.getId());
			String randomCode = RandomUtils.getRandomNumber(6);
			if(ParaManager.isProductMode()){
				SendMobilMsgUtil.sendMsgFw(mobile, "��̬��Ϊ:"+randomCode);
			}else {
				randomCode = UserInfo.INIT_PWD;
			}
			WebUtils.setSessionAttribute(request, "dymCode", randomCode);
			WebUtils.setSessionAttribute(request, "mobile", mobile);
			WebUtils.setSessionAttribute(request, "sendTime", DateUtil.getCurrent());
		} else {
			object.put("result", false);
			object.put("msg", "�ú���δ�ڱ�ϵͳע��");
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
			setResult(false, "��̬�����", request);
			return toResetPwd(mapping, actionForm, request, response);
		}
		if (!StringUtils.equals(mobile, realMobile)) {
			setResult(false, LogUtils.r("�ֻ���{?}�����뷢�Ͷ�̬����벻һ��", mobile), request);
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
			setResult(false, "�����������벻һ��", request);
			return forward("/pages/cust/inputNewPwd.jsp");
		} 
		String custId = (String)WebUtils.getSessionAttribute(request, Constants.SESSION_CUSTOM_ID);
		custInfoService.resetCustPwd(custId, loginPwd);
		setReturnUrl("/", request);
		setResult(true, "���óɹ������μ���������", request);
		String msgs = LogUtils.r("�ͻ�{?}�һ�����ɹ�",custId);
		super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
		return success(mapping);
	}
	public ActionForward viewCustInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		/String id = getSessionCustId(request);
		String custId = getSessionCustId(request);
		Assert.notNull(custId, "�Ҳ����ͻ���");
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
	 * ��֤����
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
		//������֤����ʱ��
		Date vSendTime = custInfo.getvSendTime();
		Date now = new Date();
		//������Сʱ
		if((now.getTime() - vSendTime.getTime())/(1000*60) > 30){
			setResult(false, "������֤�ѹ��ڣ������¼���", request);
			return failure(mapping);
		}
		if (!StringUtils.equals(v, custInfo.getvMailStr())) {
			setResult(false, "��֤ʧ�ܣ������¼���", request);
			return failure(mapping);
		}
		if (Symbol.YES.equals(custInfo.getSubsEmail())) {
			setResult(false, "�����Ѽ�������ظ�����", request);
			return failure(mapping);
		}
		//��֤ͨ��
		custInfo.setActiveMailTime(new Date());
		custInfo.setSubsEmail(Symbol.YES);
		custInfoService.updateCustInfo(custInfo);
		setResult(true, "�����Ѽ���Ĺ���", request);
		setReturnUrl("/", request);
		String msgs = LogUtils.r("�ͻ�{?}���伤��Ĺ��ܳɹ�",custInfo.getId());
		super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
		return success(mapping);
	}
	//ȡ�����䶩��
	public ActionForward cancelEmailSubs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String c = request.getParameter("c");
		String v = request.getParameter("v");
		CustInfo custInfo = custInfoDao.findById(c);
		if (!StringUtils.equals(v, custInfo.getvMailStr())) {
			setResult(false, "������Ч", request);
			return failure(mapping);
		}
		custInfo.setSubsEmail(Symbol.NO);
		custInfoService.updateCustInfo(custInfo);
		setResult(true, "ȡ���ɹ�", request);
		setReturnUrl("/", request);
		String msgs = LogUtils.r("�ͻ�{?}ȡ�����䶩�Ĺ��ܳɹ�",custInfo.getId());
		super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
		return success(mapping);
	}
	public ActionForward toChangePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Assert.notNull(getSessionCust(request), "�Ҳ����ͻ���");
		return forward("/pages/admin/privilege/changePassword.jsp");
	}
	
	
	public ActionForward checkSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			//���ǩԼʧ�ܣ�ɾ��ǩԼ��¼
			CustInfoActionForm actionForm = (CustInfoActionForm)request.getSession().getAttribute("form");
			String accreditId = actionForm.getAccreditId();
			boolean signResult = signContractService.checkSign(accreditId);
			Assert.isTrue(signResult, "ǩԼʧ��");
			if (getSessionCust(request) == null) {
				setResult(true, "ҵ��ͨ�ɹ�,���¼", request);
				String msgs = LogUtils.r("�ͻ�ǩԼ��֤�ɹ�����Ȩ��{?}",accreditId);
				super.logSuccess(request, UserLogType.CHECK.getValue(), msgs);
			}else {
				setResult(true, "����Ϊ��Ա�ͻ�,�����µ�¼", request);
			}
			setReturnUrl("/"+getSessionBranchTag(request), request);
			return success(mapping);
		} catch (Exception e) {
			String msg = LogUtils.r("�ͻ�ǩԼ��֤ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.CHECK.getValue(), msg);
			setReturnUrl("/"+getSessionBranchTag(request), request);
			setResult(false, e.getMessage(), request);
			return failure(mapping);
		}
	}
}
