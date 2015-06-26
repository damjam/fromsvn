package com.ylink.cim.invest.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.state.SignState;
import com.ylink.cim.common.type.BusiType;
import com.ylink.cim.common.type.CustType;
import com.ylink.cim.common.type.PayChnlType;
import com.ylink.cim.common.type.SymbolType;
import com.ylink.cim.common.type.SysDictType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.common.util.SendMobilMsgUtil;
import com.ylink.cim.cust.domain.CustInfo;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.SignContract;
import com.ylink.cim.invest.service.FundAcctService;
import com.ylink.cim.invest.service.SignContractService;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.LogUtils;
import flink.util.SpringContext;
import flink.web.BaseDispatchAction;

public class InvestAcctAction extends BaseDispatchAction {

	private SignContractDao signContractDao = (SignContractDao)SpringContext.getService("signContractDao");
	private SignContractService signContractService = (SignContractService)SpringContext.getService("signContractService");
	private FundAcctService fundAcctService = (FundAcctService)getService("fundAcctService");
	public void initData(HttpServletRequest request) throws Exception{
		Map<String, Object> map = getParaMap();
		map.put("custId", getSessionCustId(request));
		map.put("busiCode", BusiType.GOLD.getValue());
		map.put("state", SignState.SIGNED.getValue());
		List<SignContract> list = signContractDao.findByParams(map);
		for (int i = 0; i < list.size(); i++) {
			SignContract contract = list.get(i);
			contract.setPayChnlTypeName(PayChnlType.valueOf(contract.getPayChnlType()).getName());
		}
		request.setAttribute("signContracts", list);
	}
	/**
	 * ��ѯ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
		Map<String, String> msgMap = getReqMap(request);
//		CustInfo custInfo=getSessionCust(request);
//		Assert.notNull(custInfo, "�Ҳ����ͻ���Ϣ");
		msgMap.put(MsgField.aip_no.getFieldCode(),  actionForm.getAcctNo());
//		msgMap.put(MsgField.cert_type.getFieldCode(), custInfo.getCardType());
//		msgMap.put(MsgField.cert_num.getFieldCode(), custInfo.getIdCard());
		try {
			List<Map<String, String>> list = signContractService.queryInvestAcctList(msgMap, getSessionInvestAcctNos(request));
			saveQueryResult(request, list);
			String msg = LogUtils.r("��Ͷ�˻������ѯ�ɹ���");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (BizException e) {
			setResult(false, "��ѯʧ��,"+e.getMessage(), request);
			String msg = LogUtils.r("��Ͷ�˻������ѯʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		initData(request);
		return forward("/pages/invest/account/list.jsp");
	}
	
	/**
	 * �����ѯҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		return forward("/pages/invest/account/list.jsp");
	}
	
	
	public ActionForward toAddSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CustInfo custInfo = getSessionCust(request);
		Assert.notNull(custInfo, "�Ҳ����ͻ���Ϣ");
		String u = request.getParameter("u");
		//�ж��Ƿ���ȷ��ҳ�淵���޸���Ϣ
		if (Symbol.YES.equals(u)) {
			ActionForm sessionForm = (ActionForm)WebUtils.getSessionAttribute(request, "form");
			if (sessionForm != null) {
				BeanUtils.copyProperties(form, sessionForm);
			}
		}
		ParaManager.setDictInReq(request, SysDictType.valueOf("SignChnl"+getSessionBranch(request).getValue().substring(2, 4)), "payChnlTypes");
		SymbolType.setInReq(request);
		if (StringUtils.equals(CustType.TYPE_MEMBER.getValue(), custInfo.getCustType())) {
			//ֱ����д�б�Ϳ���
			return forward("/pages/invest/account/addAcct.jsp");
		}else {
			return forward("/pages/invest/account/openFirst.jsp");
		}
	}
	//ȷ��
	public ActionForward toVerify(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			Map<String, Object> map = getParaMap();
			map.put(MsgField.h_bank_no.getFieldCode(), getSessionBranch(request).getValue());
			map.put("payChnlNo", actionForm.getPayChnlNo());
			map.put("busiCode", BusiType.GOLD.getValue());
			List<SignContract> list = signContractDao.findByParams(map);
			Assert.isEmpty(list, "�����п���ǩԼ�������ٴ�ǩԼ");
			WebUtils.setSessionAttribute(request, "form", form);
			
			actionForm.setBusiCode(BusiType.GOLD.getValue());
			actionForm.setPayChnlTypeName(PayChnlType.valueOf(actionForm.getPayChnlType()).getName());
			return forward("/pages/invest/account/verifySignInfo.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAddSign(mapping, form, request, response);
		}
		
	}
	//ת������ǩԼҳ��
	@Deprecated
	public ActionForward toBankSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return forward("/pages/invest/account/bankSign.jsp");
	}
	
	
	public ActionForward doSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			SignContract contract = new SignContract();
			InvestInfoActionForm actionForm = (InvestInfoActionForm)WebUtils.getSessionAttribute(request, "form");
			BeanUtils.copyProperties(contract, actionForm);
			contract.setCustId(getSessionCustId(request));
			contract.setBranchNo(getSessionBranch(request).getValue());
			//����ǩԼ��Ϣ
			String accreditId = signContractService.addSignContract(contract, request, response);
			actionForm.setAccreditId(accreditId);
			String msg = LogUtils.r("�ͻ�ǩԼ�����п��󶨳ɹ�����ˮ��Ϊ{?}",accreditId);
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
			return null;
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("�ͻ�ǩԼ�����п���ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			return failure(mapping);
		}
	}
	
	/**
	 * ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cancelInvestAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			request.setAttribute("form", actionForm);
			String acctNo = actionForm.getAcctNo();
			Assert.notNull(acctNo, "��Ͷ�˺�Ϊ��");
			//����ǰ�ȼ��ƽ���ʽ��˻�
			checkAcctForCancel(acctNo, request);
			
			/*msgMap.put(MsgField.h_bank_no.getFieldCode(), getSessionBranch(request).getValue());
			msgMap.put(MsgField.aip_no.getFieldCode(),  actionForm.getAcctNo());
			msgMap.put(MsgField.account_no.getFieldCode(), actionForm.getPayChnlNo());
			
			String accreditId = actionForm.getAccreditId();*/
			
			//��ǩԼ���е�״̬��Ϊ������Լ��
			signContractService.cancelAcct(request, response, acctNo);
			//δ��Լ����ִ�н�Լ
			if (SignState.SIGNED.getValue().equals(actionForm.getState())) {
				return null;
			}
			//CcbSignUtil.cancelSign(request, response, actionForm.getAccreditId());
			//��ʱ����ȷ���Ƿ������ɹ����ݲ��ǳɹ���־
			String msg = LogUtils.r("ִ����������,��Ͷ�˺�{?}", acctNo);
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			super.setReturnUrl("/investAccAction.do?action=toList", request);
			return success(mapping);
		} catch (BizException e) {
			setResult(false,"����ʧ��,"+e.getMessage(), request);
			String msg = LogUtils.r("�ͻ����������п������ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			return toList(mapping, form, request, response);
		}
		
	}
	//����˻��Ƿ�߱���������
	private void checkAcctForCancel(String acctNo, HttpServletRequest request) throws BizException{
		Map<String, String> map3002 = super.getReqMap(request);
		Map<String, MsgField> resMap = fundAcctService.queryFundAcct(map3002, acctNo);
		Assert.isTrue(Double.parseDouble(resMap.get(MsgField.curr_bal.getFieldCode()).getValue()) == 0, "��ǰ��Ϊ0");
		Assert.isTrue(Double.parseDouble(resMap.get(MsgField.frozen_fund.getFieldCode()).getValue()) == 0, "�����Ϊ0");
		Assert.isTrue(Double.parseDouble(resMap.get(MsgField.debt_bal.getFieldCode()).getValue()) == 0, "Ƿ�Ϊ0");
		
		Map<String, String> map2044 = super.getReqMap(request);
		map2044.put(MsgField.aip_no.getFieldCode(), acctNo);
		Map<String, MsgField> stockMap = signContractService.queryInvestAcctStock(map2044);
		Assert.isTrue(Double.parseDouble(stockMap.get(MsgField.curr_amt.getFieldCode()).getValue()) == 0, "������");
		Assert.isTrue(Double.parseDouble(stockMap.get(MsgField.sell_froz_amt.getFieldCode()).getValue()) == 0, "�ֽ���ض�����");
		Assert.isTrue(Double.parseDouble(stockMap.get(MsgField.trade_app_froz.getFieldCode()).getValue()) == 0, "ʵ����ض�����");
	}
	//��Լ
	public ActionForward cancelSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			String acctNo = actionForm.getAcctNo();
			Assert.notNull(acctNo, "��Ͷ�˺�Ϊ��");
			signContractService.cancelSign(request, response, acctNo);
			String msg = LogUtils.r("ִ�н�Լ��������Ͷ�˺�{?}", acctNo);
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			return null;
		} catch (BizException e) {
			// TODO: handle exception
			return toList(mapping, form, request, response);
		}
		
	}
	
	
	public ActionForward feedbackSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ACCREDIT_FLAG = request.getParameter("ACCREDIT_FLAG");
		String ACCREDIT_ID = request.getParameter("ACCREDIT_ID");
		//ί����Ȩ
		if ("0".equals(ACCREDIT_FLAG)) {
			String TX_QUOTA = request.getParameter("TX_QUOTA");
			String TX_DATE = request.getParameter("TX_DATE");
			Map<String, String> map = new HashMap<String, String>();
			map.put("ACCREDIT_ID", ACCREDIT_ID);
			map.put("TX_QUOTA", TX_QUOTA);
			map.put("TX_DATE", TX_DATE);
			boolean firstSign = false;
			if (getSessionCust(request) == null) {
				firstSign = true;
			}
			String aipNo = signContractService.signSuccess(map);
			String msg = "";
			if(firstSign){
				msg = "ҵ��ͨ�ɹ�,���¼";
				setResult(true, msg, request);
				setReturnUrl("/"+getSessionBranchTag(request), request);
				String mobileMsg=LogUtils.r(Constants.OPEN_ACC_MOBILE_MSG,aipNo );
				String sessMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
				if(ParaManager.isProductMode()){
					SendMobilMsgUtil.sendMsgFw(sessMobile,mobileMsg);//������Ϣ
				}
				String msgs = LogUtils.r("�ͻ���Ͷ�˺ſ�ͨ���˺�Ϊ{?}",aipNo);
				super.logSuccess(request, UserLogType.ADD.getValue(), msgs);
				return success(mapping);
			} else {
				msg = LogUtils.r("��Ȩ�ɹ�,��Ͷ�˺�{?},��Ȩ��{?},�����޶�{?},ÿ���޶�{?}",aipNo, ACCREDIT_ID, TX_QUOTA, TX_DATE);
				setResult(true, msg, request);
				setReturnUrl("/investAcctAction.do?action=toList", request);
				super.logSuccess(request, UserLogType.CHECK.getValue(), msg);
				return success(mapping);
			}
		}
		//������Ȩ
		else if ("1".equals(ACCREDIT_FLAG)) {
			signContractService.cancelSuccess(ACCREDIT_ID);
			setResult(true, "������Ȩ�ɹ�", request);
			setReturnUrl("/investAcctAction.do?action=toList", request);
			String mobileMsg=LogUtils.r(Constants.CANCEL_ACC_MOBILE_MSG );
			String sessMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
			if(ParaManager.isProductMode()){
				SendMobilMsgUtil.sendMsgFw(sessMobile,mobileMsg);//������Ϣ
			}
			String msgs = LogUtils.r("�ͻ�������Ȩ�ɹ����˺�Ϊ{?}",ACCREDIT_ID);
			super.logSuccess(request, UserLogType.DELETE.getValue(), msgs);
			return success(mapping);
		}
		setReturnUrl("/", request);
		return success(mapping);
	}
	
	/**
	 * 
	 * ת���޸��˺Ű�ҳ��
	 */
	public ActionForward toUpdateInvestAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setBackUrl(request);
			//�ж��Ƿ���ȷ��ҳ�淵���޸���Ϣ
			String updateSymbol = request.getParameter("updateSymbol");
			if (Symbol.YES.equals(updateSymbol)) {
				ActionForm sessionForm = (ActionForm)WebUtils.getSessionAttribute(request, "form");
				if (sessionForm != null) {
					BeanUtils.copyProperties(form, sessionForm);
				}
			}
			ParaManager.setDictInReq(request, SysDictType.valueOf("SignChnl"+getSessionBranch(request).getValue().substring(2, 4)), "payChnlTypes");
			SymbolType.setInReq(request);
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			String acctNo = actionForm.getAcctNo();
			Assert.notNull(acctNo, "��Ͷ�˺�Ϊ��");
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/account/modifyInvestAcct.jsp");
		} catch (BizException e) {
			setResult(false,"ԭ��Ͷ��Ϣ��ѯʧ��,"+e.getMessage(), request);
			return toList(mapping, form, request, response);
		}
	}
	
	/**
	 * 
	 * ת���޸��˺Ű󶨵�ȷ��ҳ��
	 */
	public ActionForward verifyInvestAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
		try {
			
			String acctNo = actionForm.getAcctNo();
			Assert.notNull(acctNo, "��Ͷ�˺�Ϊ��");
			String payChnlNo = actionForm.getPayChnlNo();
			Assert.notNull(payChnlNo, "֧���˺�Ϊ��");
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/account/verifyInvestAcct.jsp");
		} catch (BizException e) {
			setResult(false,e.getMessage(), request);
			return toUpdateInvestAcct(mapping, form, request, response);
		}
	}
	
	/**
	 * 
	 * ִ�п����޸�
	 */
	public ActionForward doModifyInvestAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			String acctNo = actionForm.getAcctNo();
			Assert.notNull(acctNo, "��Ͷ�˺�Ϊ��");
			String payChnlNo = actionForm.getPayChnlNo();
			Assert.notNull(payChnlNo, "����Ϊ��");
			//����ǩԼ����
			String authId = signContractService.updateSignContract(acctNo, payChnlNo, request, response);
			actionForm.setAccreditId(authId);
			WebUtils.setSessionAttribute(request, "form", actionForm);
			/*
			Map<String, String> msgMap=new HashMap<String, String>();
			msgMap.put(MsgField.h_bank_no.getFieldCode(), getSessionBranch(request).getValue());
			msgMap.put(MsgField.aip_no.getFieldCode(),  acctNo);
			List<Map<String, MsgField>> list = signContractService.queryInvestAcctList(msgMap, getSessionInvestAcctNos(request));
			Map<String, MsgField> sourceMap=list.get(0);
			Map<String, String> modifyMsgMap=new HashMap<String, String>();
			modifyMsgMap.put(MsgField.h_bank_no.getFieldCode(), getSessionBranch(request).getValue());
			modifyMsgMap.put(MsgField.aip_no.getFieldCode(),  acctNo);
			modifyMsgMap.put(MsgField.old_account_no.getFieldCode(),  sourceMap.get(MsgField.account_no.getFieldCode()).getValue());
			modifyMsgMap.put(MsgField.new_account_no.getFieldCode(),  payChnlNo);
			String serialNo=signContractService.modifyInvestAcct(modifyMsgMap);
			setResult(true, "�����ɹ�,������ˮ��"+serialNo, request);
			setReturnUrl("/investAcctAction.do?action=toList", request);*/
			/*String msgs = LogUtils.r("�ͻ��޸İ����п��ţ���Ͷ�˺�Ϊ{?}",acctNo);
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
			return success(mapping);*/
			return null;
		} catch (BizException e) {
			String msg = LogUtils.r("�ͻ��޸İ����п���ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			setResult(false, e.getMessage(), request);
			return failure(mapping);
		}
		
	}
	//����
	public ActionForward checkSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			//���ǩԼʧ�ܣ�ɾ��ǩԼ��¼
			InvestInfoActionForm actionForm = (InvestInfoActionForm)request.getSession().getAttribute("form");
			String accreditId = actionForm.getAccreditId();
			boolean signResult = signContractService.checkSign(accreditId);
			Assert.isTrue(signResult, "ǩԼʧ��");
			setResult(true, LogUtils.r("ǩԼ�ɹ�,��Ȩ��{?}", accreditId), request);
			
			String msgs = LogUtils.r("�ͻ�ǩԼ��֤�ɹ�����Ȩ��{?}",accreditId);
			super.logSuccess(request, UserLogType.CHECK.getValue(), msgs);
			setReturnUrl("/investAcctAction.do?action=toList", request);
			return success(mapping);
		} catch (Exception e) {
			setReturnUrl("/investAcctAction.do?action=toList", request);
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("�ͻ�ǩԼ��֤ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.CHECK.getValue(), msg);
			return failure(mapping);
		}
	}
	//��֤��Լ(����״̬�жϽ�Լ��������)
	public ActionForward checkCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			//�����Լʧ��
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			String acctNo = actionForm.getAcctNo();
			boolean cancelResult = signContractService.checkCancel(acctNo);
			Assert.isTrue(cancelResult, "��Լʧ��");
			setResult(true, "��Լ�ɹ�", request);
			
			setReturnUrl("/investAcctAction.do?action=toList", request);
			
			String msgs = LogUtils.r("�ͻ���Լ�ɹ�����Ͷ�˺�{?}",acctNo);
			super.logSuccess(request, UserLogType.OTHER.getValue(), msgs);
			return success(mapping);
		} catch (Exception e) {
			e.getMessage();
			setReturnUrl("/investAcctAction.do?action=toList", request);
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("�ͻ���Լ��֤ʧ��,ʧ��ԭ��:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return failure(mapping);
		}
	}
}
