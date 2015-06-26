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
	 * 查询
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
//		Assert.notNull(custInfo, "找不到客户信息");
		msgMap.put(MsgField.aip_no.getFieldCode(),  actionForm.getAcctNo());
//		msgMap.put(MsgField.cert_type.getFieldCode(), custInfo.getCardType());
//		msgMap.put(MsgField.cert_num.getFieldCode(), custInfo.getIdCard());
		try {
			List<Map<String, String>> list = signContractService.queryInvestAcctList(msgMap, getSessionInvestAcctNos(request));
			saveQueryResult(request, list);
			String msg = LogUtils.r("定投账户管理查询成功！");
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
		} catch (BizException e) {
			setResult(false, "查询失败,"+e.getMessage(), request);
			String msg = LogUtils.r("定投账户管理查询失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
		}
		initData(request);
		return forward("/pages/invest/account/list.jsp");
	}
	
	/**
	 * 进入查询页面
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
		Assert.notNull(custInfo, "找不到客户信息");
		String u = request.getParameter("u");
		//判断是否由确认页面返回修改信息
		if (Symbol.YES.equals(u)) {
			ActionForm sessionForm = (ActionForm)WebUtils.getSessionAttribute(request, "form");
			if (sessionForm != null) {
				BeanUtils.copyProperties(form, sessionForm);
			}
		}
		ParaManager.setDictInReq(request, SysDictType.valueOf("SignChnl"+getSessionBranch(request).getValue().substring(2, 4)), "payChnlTypes");
		SymbolType.setInReq(request);
		if (StringUtils.equals(CustType.TYPE_MEMBER.getValue(), custInfo.getCustType())) {
			//直接填写行别和卡号
			return forward("/pages/invest/account/addAcct.jsp");
		}else {
			return forward("/pages/invest/account/openFirst.jsp");
		}
	}
	//确认
	public ActionForward toVerify(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			Map<String, Object> map = getParaMap();
			map.put(MsgField.h_bank_no.getFieldCode(), getSessionBranch(request).getValue());
			map.put("payChnlNo", actionForm.getPayChnlNo());
			map.put("busiCode", BusiType.GOLD.getValue());
			List<SignContract> list = signContractDao.findByParams(map);
			Assert.isEmpty(list, "该银行卡已签约，无需再次签约");
			WebUtils.setSessionAttribute(request, "form", form);
			
			actionForm.setBusiCode(BusiType.GOLD.getValue());
			actionForm.setPayChnlTypeName(PayChnlType.valueOf(actionForm.getPayChnlType()).getName());
			return forward("/pages/invest/account/verifySignInfo.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAddSign(mapping, form, request, response);
		}
		
	}
	//转向银行签约页面
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
			//更新签约信息
			String accreditId = signContractService.addSignContract(contract, request, response);
			actionForm.setAccreditId(accreditId);
			String msg = LogUtils.r("客户签约，银行卡绑定成功！流水号为{?}",accreditId);
			super.logSuccess(request, UserLogType.SEARCH.getValue(), msg);
			return null;
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("客户签约，银行卡绑定失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			return failure(mapping);
		}
	}
	
	/**
	 * 销户
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
			Assert.notNull(acctNo, "定投账号为空");
			//销户前先检查黄金和资金账户
			checkAcctForCancel(acctNo, request);
			
			/*msgMap.put(MsgField.h_bank_no.getFieldCode(), getSessionBranch(request).getValue());
			msgMap.put(MsgField.aip_no.getFieldCode(),  actionForm.getAcctNo());
			msgMap.put(MsgField.account_no.getFieldCode(), actionForm.getPayChnlNo());
			
			String accreditId = actionForm.getAccreditId();*/
			
			//把签约表中的状态改为销户解约中
			signContractService.cancelAcct(request, response, acctNo);
			//未解约，先执行解约
			if (SignState.SIGNED.getValue().equals(actionForm.getState())) {
				return null;
			}
			//CcbSignUtil.cancelSign(request, response, actionForm.getAccreditId());
			//此时不能确定是否销户成功，暂不记成功日志
			String msg = LogUtils.r("执行销户操作,定投账号{?}", acctNo);
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msg);
			super.setReturnUrl("/investAccAction.do?action=toList", request);
			return success(mapping);
		} catch (BizException e) {
			setResult(false,"销户失败,"+e.getMessage(), request);
			String msg = LogUtils.r("客户销户，银行卡解除绑定失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.SEARCH.getValue(), msg);
			return toList(mapping, form, request, response);
		}
		
	}
	//检查账户是否具备销户条件
	private void checkAcctForCancel(String acctNo, HttpServletRequest request) throws BizException{
		Map<String, String> map3002 = super.getReqMap(request);
		Map<String, MsgField> resMap = fundAcctService.queryFundAcct(map3002, acctNo);
		Assert.isTrue(Double.parseDouble(resMap.get(MsgField.curr_bal.getFieldCode()).getValue()) == 0, "当前余额不为0");
		Assert.isTrue(Double.parseDouble(resMap.get(MsgField.frozen_fund.getFieldCode()).getValue()) == 0, "冻结金额不为0");
		Assert.isTrue(Double.parseDouble(resMap.get(MsgField.debt_bal.getFieldCode()).getValue()) == 0, "欠款不为0");
		
		Map<String, String> map2044 = super.getReqMap(request);
		map2044.put(MsgField.aip_no.getFieldCode(), acctNo);
		Map<String, MsgField> stockMap = signContractService.queryInvestAcctStock(map2044);
		Assert.isTrue(Double.parseDouble(stockMap.get(MsgField.curr_amt.getFieldCode()).getValue()) == 0, "库存余额");
		Assert.isTrue(Double.parseDouble(stockMap.get(MsgField.sell_froz_amt.getFieldCode()).getValue()) == 0, "现金赎回冻结库存");
		Assert.isTrue(Double.parseDouble(stockMap.get(MsgField.trade_app_froz.getFieldCode()).getValue()) == 0, "实物赎回冻结库存");
	}
	//解约
	public ActionForward cancelSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			String acctNo = actionForm.getAcctNo();
			Assert.notNull(acctNo, "定投账号为空");
			signContractService.cancelSign(request, response, acctNo);
			String msg = LogUtils.r("执行解约操作，定投账号{?}", acctNo);
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
		//委托授权
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
				msg = "业务开通成功,请登录";
				setResult(true, msg, request);
				setReturnUrl("/"+getSessionBranchTag(request), request);
				String mobileMsg=LogUtils.r(Constants.OPEN_ACC_MOBILE_MSG,aipNo );
				String sessMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
				if(ParaManager.isProductMode()){
					SendMobilMsgUtil.sendMsgFw(sessMobile,mobileMsg);//发送信息
				}
				String msgs = LogUtils.r("客户定投账号开通，账号为{?}",aipNo);
				super.logSuccess(request, UserLogType.ADD.getValue(), msgs);
				return success(mapping);
			} else {
				msg = LogUtils.r("授权成功,定投账号{?},授权号{?},单笔限额{?},每日限额{?}",aipNo, ACCREDIT_ID, TX_QUOTA, TX_DATE);
				setResult(true, msg, request);
				setReturnUrl("/investAcctAction.do?action=toList", request);
				super.logSuccess(request, UserLogType.CHECK.getValue(), msg);
				return success(mapping);
			}
		}
		//撤销授权
		else if ("1".equals(ACCREDIT_FLAG)) {
			signContractService.cancelSuccess(ACCREDIT_ID);
			setResult(true, "撤销授权成功", request);
			setReturnUrl("/investAcctAction.do?action=toList", request);
			String mobileMsg=LogUtils.r(Constants.CANCEL_ACC_MOBILE_MSG );
			String sessMobile = (String)WebUtils.getSessionAttribute(request, "mobile");
			if(ParaManager.isProductMode()){
				SendMobilMsgUtil.sendMsgFw(sessMobile,mobileMsg);//发送信息
			}
			String msgs = LogUtils.r("客户撤销授权成功，账号为{?}",ACCREDIT_ID);
			super.logSuccess(request, UserLogType.DELETE.getValue(), msgs);
			return success(mapping);
		}
		setReturnUrl("/", request);
		return success(mapping);
	}
	
	/**
	 * 
	 * 转向修改账号绑定页面
	 */
	public ActionForward toUpdateInvestAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setBackUrl(request);
			//判断是否由确认页面返回修改信息
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
			Assert.notNull(acctNo, "定投账号为空");
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/account/modifyInvestAcct.jsp");
		} catch (BizException e) {
			setResult(false,"原定投信息查询失败,"+e.getMessage(), request);
			return toList(mapping, form, request, response);
		}
	}
	
	/**
	 * 
	 * 转向修改账号绑定的确认页面
	 */
	public ActionForward verifyInvestAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
		try {
			
			String acctNo = actionForm.getAcctNo();
			Assert.notNull(acctNo, "定投账号为空");
			String payChnlNo = actionForm.getPayChnlNo();
			Assert.notNull(payChnlNo, "支付账号为空");
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/account/verifyInvestAcct.jsp");
		} catch (BizException e) {
			setResult(false,e.getMessage(), request);
			return toUpdateInvestAcct(mapping, form, request, response);
		}
	}
	
	/**
	 * 
	 * 执行卡号修改
	 */
	public ActionForward doModifyInvestAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			String acctNo = actionForm.getAcctNo();
			Assert.notNull(acctNo, "定投账号为空");
			String payChnlNo = actionForm.getPayChnlNo();
			Assert.notNull(payChnlNo, "卡号为空");
			//更改签约卡号
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
			setResult(true, "操作成功,申请流水号"+serialNo, request);
			setReturnUrl("/investAcctAction.do?action=toList", request);*/
			/*String msgs = LogUtils.r("客户修改绑定银行卡号，定投账号为{?}",acctNo);
			super.logSuccess(request, UserLogType.UPDATE.getValue(), msgs);
			return success(mapping);*/
			return null;
		} catch (BizException e) {
			String msg = LogUtils.r("客户修改绑定银行卡号失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.UPDATE.getValue(), msg);
			setResult(false, e.getMessage(), request);
			return failure(mapping);
		}
		
	}
	//包括
	public ActionForward checkSign(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			//如果签约失败，删除签约记录
			InvestInfoActionForm actionForm = (InvestInfoActionForm)request.getSession().getAttribute("form");
			String accreditId = actionForm.getAccreditId();
			boolean signResult = signContractService.checkSign(accreditId);
			Assert.isTrue(signResult, "签约失败");
			setResult(true, LogUtils.r("签约成功,授权号{?}", accreditId), request);
			
			String msgs = LogUtils.r("客户签约认证成功，授权号{?}",accreditId);
			super.logSuccess(request, UserLogType.CHECK.getValue(), msgs);
			setReturnUrl("/investAcctAction.do?action=toList", request);
			return success(mapping);
		} catch (Exception e) {
			setReturnUrl("/investAcctAction.do?action=toList", request);
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("客户签约认证失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.CHECK.getValue(), msg);
			return failure(mapping);
		}
	}
	//验证解约(根据状态判断解约还是销户)
	public ActionForward checkCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			//如果解约失败
			InvestInfoActionForm actionForm = (InvestInfoActionForm)form;
			String acctNo = actionForm.getAcctNo();
			boolean cancelResult = signContractService.checkCancel(acctNo);
			Assert.isTrue(cancelResult, "解约失败");
			setResult(true, "解约成功", request);
			
			setReturnUrl("/investAcctAction.do?action=toList", request);
			
			String msgs = LogUtils.r("客户解约成功，定投账号{?}",acctNo);
			super.logSuccess(request, UserLogType.OTHER.getValue(), msgs);
			return success(mapping);
		} catch (Exception e) {
			e.getMessage();
			setReturnUrl("/investAcctAction.do?action=toList", request);
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("客户解约认证失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.OTHER.getValue(), msg);
			return failure(mapping);
		}
	}
}
