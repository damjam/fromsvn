package com.ylink.cim.invest.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.state.PlanState;
import com.ylink.cim.common.state.SignState;
import com.ylink.cim.common.type.AipMode;
import com.ylink.cim.common.type.AipPeriod;
import com.ylink.cim.common.type.AipType;
import com.ylink.cim.common.type.BSFlag;
import com.ylink.cim.common.type.BusiType;
import com.ylink.cim.common.type.DeductType;
import com.ylink.cim.common.type.PayChnlType;
import com.ylink.cim.common.type.UserLogType;
import com.ylink.cim.common.util.DoubleUtil;
import com.ylink.cim.invest.dao.SignContractDao;
import com.ylink.cim.invest.domain.SignContract;
import com.ylink.cim.invest.service.FundAcctService;
import com.ylink.cim.invest.service.InvestPlanService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.LogUtils;
import flink.util.SpringContext;
import flink.web.BaseDispatchAction;

/**
 * 资金账户管理
 * 
 * @date 2013-5-21
 */
public class InvestFundAction extends BaseDispatchAction {

	private InvestPlanService investPlanService = (InvestPlanService) SpringContext.getService("investPlanService");
	private SignContractDao signContractDao = (SignContractDao) SpringContext.getService("signContractDao");
	private FundAcctService fundAcctService = (FundAcctService) SpringContext.getService("fundAcctService");

	// 查询出入金账户
	public ActionForward showFundAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/*
		 * WebUtils.setSessionAttribute(request, "form", null);
		 * 
		 * 
		 * List<Map<String, Object>> list = investPlanService.queryPlan(null);
		 * saveQueryResult(request, list); initData(request); List<SignContract>
		 * contracts = (List<SignContract>)request.getAttribute("payChnls");
		 * SignContract contract = contracts.get(0); for (int i = 0; i <
		 * list.size(); i++) { Map<String, Object> plan = list.get(i);
		 * plan.put("payChnlAlias",
		 * PayChnlType.valueOf(contract.getPayChnlType()
		 * ).getName()+contract.getPayChnlNo()); }
		 */
		return forward("/pages/invest/fundacct/fundacctDetail.jsp");
	}

	// 转向计划添加页面
	public ActionForward toWithdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		if (Symbol.YES.equals(request.getParameter("updateSymbol"))) {
			InvestInfoActionForm sessionForm = (InvestInfoActionForm) WebUtils.getSessionAttribute(request, "form");
			if (sessionForm != null) {
				BeanUtils.copyProperties(form, sessionForm);
				request.setAttribute("msg", "");
			}
		}
		Map<String, String> map = getReqMap(request);
		String[] investAccts = getSessionInvestAcctNos(request);
		Map<String, String> aipAccts = fundAcctService.queryFundAccts(map, investAccts);
		request.getSession().setAttribute("aipAccts", aipAccts);
		request.setAttribute("aipAccts", aipAccts);
		return forward("/pages/invest/fundacct/withdraw.jsp");
	}

	public ActionForward toVerifyWithdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 验证
		
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		try {
			String acctNo = actionForm.getAcctNo();
			Map<String, String> aipAcctMap = (Map<String, String>) request.getSession().getAttribute("aipAccts");
			String balance = aipAcctMap.get(acctNo);
			Assert.isTrue(Double.parseDouble(aipAcctMap.get(acctNo)) >= Double.parseDouble(actionForm.getAmount()),
					"出金金额不能大于可账户余额");
			actionForm.setBalance(balance.toString());
			String signContractId = actionForm.getSignContractId();
			SignContract signContract = signContractDao.findById(signContractId);
			actionForm.setPayChnlNo(signContract.getPayChnlNo());
			actionForm.setPayChnlAlias(PayChnlType.valueOf(signContract.getPayChnlType()).getName()
					+ signContract.getPayChnlNo());
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/fundacct/withdrawVerify.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			initData(request);
			//request.setAttribute("aipAccts", aipAccts);
			return toWithdraw(mapping, actionForm, request, response);
			//return forward("/pages/invest/fundacct/withdraw.jsp");
		}
	}

	public ActionForward doWithdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm) WebUtils.getSessionAttribute(request, "form");
		try {
			Map<String, String> map = getReqMap(request);
			map.put("aip_no", actionForm.getAcctNo());
			map.put("exch_bal", DoubleUtil.doubleToString(Double.parseDouble(actionForm.getAmount()), 18, 2));
			map.put("deduct_type", DeductType.DEDUCT_TYPE_25.getValue());
			map.put("account_no", actionForm.getPayChnlNo());
			String orderNo = fundAcctService.addWithdraw(map);
			String msg = LogUtils.r("出金已受理,流水号{?},出金金额{?},定投账号{?},收款账户{?}", orderNo, actionForm.getAmount(), actionForm
					.getAcctNo(), actionForm.getPayChnlNo());
			setResult(true, "出金已受理,流水号" + orderNo, request);
			setReturnUrl("/investFundAction.do?action=toWithdraw", request);
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			String msg = LogUtils.r("出金失败,失败原因:{?}", e.getMessage());
			setResult(false, msg, request);
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return toWithdraw(mapping, actionForm, request, response);
		}
	}

	//
	public ActionForward toDeposit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		if (Symbol.YES.equals(request.getParameter("updateSymbol"))) {
			BeanUtils.copyProperties(form, WebUtils.getSessionAttribute(request, "form"));
			request.setAttribute("msg", "");
		}

		Map<String, String> map = getReqMap(request);
		String[] investAccts = getSessionInvestAcctNos(request);
		map.put(MsgField.h_exch_code.getFieldCode(), "A3002");
		Map<String, String> aipAccts = fundAcctService.queryFundAccts(map, investAccts);
		request.getSession().setAttribute("aipAccts", aipAccts);
		request.setAttribute("aipAccts", aipAccts);
		return forward("/pages/invest/fundacct/deposit.jsp");
	}

	public ActionForward toVerifyDeposit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
			Map<String, String> aipAcctMap = (Map<String, String>) request.getSession().getAttribute("aipAccts");
			String balance = aipAcctMap.get(actionForm.getAcctNo());
			actionForm.setBalance(balance.toString());
			String signContractId = actionForm.getSignContractId();
			SignContract signContract = signContractDao.findById(signContractId);
			actionForm.setPayChnlAlias(PayChnlType.valueOf(signContract.getPayChnlType()).getName()
					+ signContract.getPayChnlNo());
			actionForm.setPayChnlNo(signContract.getPayChnlNo());
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/fundacct/depositVerify.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toDeposit(mapping, form, request, response);
		}

	}

	// 入金
	public ActionForward doDeposit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm) WebUtils.getSessionAttribute(request, "form");
		try {
			Map<String, String> map = getReqMap(request);
			map.put("aip_no", actionForm.getAcctNo());
			map.put("exch_bal", DoubleUtil.doubleToString(Double.parseDouble(actionForm.getAmount()), 18, 2));
			map.put("deduct_type", DeductType.DEDUCT_TYPE_24.getValue());
			map.put("account_no", actionForm.getPayChnlNo());
			String orderNo = fundAcctService.addDeposit(map);
			request.getSession().setAttribute("fomr", null);
			setResult(true, "入金已受理,申请流水号" + orderNo, request);
			setReturnUrl("/investFundAction.do?action=toDeposit", request);

			String msg = LogUtils.r("入金已受理,流水号{?},定投账号{?},入金金额{?},付款账户{?}", orderNo, actionForm.getAcctNo(), actionForm
					.getAmount(), actionForm.getPayChnlNo());
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("入金失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return toDeposit(mapping, actionForm, request, response);
		}
	}

	/**
	 * 显示资金账户购买黄金的交易记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listFundTrade(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		String planNo = actionForm.getPlanNo();
		return forward("/pages/invest/plan/planUpdate.jsp");
	}

	// 主动定投
	public ActionForward toAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		if (Symbol.YES.equals(request.getParameter("updateSymbol"))) {
			InvestInfoActionForm sessionForm = (InvestInfoActionForm) WebUtils.getSessionAttribute(request, "form");
			if (sessionForm != null) {
				BeanUtils.copyProperties(form, sessionForm);
				request.setAttribute("msg", "");
			}
		}
		// 查询资金账户余额
		String[] aipNos = getSessionInvestAcctNos(request);
		Map<String, String> map = getReqMap(request);
		Map<String, String> aipAccts = fundAcctService.queryFundAccts(map, aipNos);
		request.getSession().setAttribute("aipAccts", aipAccts);
		request.setAttribute("aipAccts", aipAccts);
		return forward("/pages/invest/fundacct/buyGold.jsp");
	}

	public ActionForward toVerify(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		try {
			String acctNo = actionForm.getAcctNo();
			Map<String, String> aipAcctMap = (Map<String, String>) request.getSession().getAttribute("aipAccts");
			String balance = aipAcctMap.get(acctNo);
			Assert.isTrue(Double.parseDouble(aipAcctMap.get(acctNo)) >= Double.parseDouble(actionForm.getAmount()),
					"购买金额不能大于可账户余额");
			actionForm.setBalance(balance.toString());
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/fundacct/buyGoldVerify.jsp");
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toAdd(mapping, actionForm, request, response);
		}

	}

	public ActionForward doAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		InvestInfoActionForm actionForm = (InvestInfoActionForm) WebUtils.getSessionAttribute(request, "form");
		try {
			Map<String, String> map = getReqMap(request);
			map.put("aip_no", actionForm.getAcctNo());
			map.put("bs_flag", BSFlag.BUY.getValue());
			map.put("aip_type", BusiType.GOLD.getCode());
			map.put("aip_mode", AipMode.MODE_1.getValue());
			map.put("aip_amount", DoubleUtil.doubleToString(Double.parseDouble(actionForm.getAmount()), 18, 4));
			String orderNo = fundAcctService.doOneDeal(map);
			setResult(true, "操作成功,申请流水号" + orderNo, request);
			setReturnUrl("/investFundAction.do?action=toAdd", request);
			String msg = LogUtils.r("主动定投已受理,流水号{?},定投账号{?},购买金额{?}", orderNo, actionForm.getAcctNo(), actionForm
					.getAmount());
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("主动定投失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return failure(mapping);
		}
	}

	// 进入补缴金额页面
	public ActionForward toPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		initData(request);
		setBackUrl(request);
		if (Symbol.YES.equals(request.getParameter("updateSymbol"))) {
			InvestInfoActionForm sessionForm = (InvestInfoActionForm) WebUtils.getSessionAttribute(request, "form");
			if (sessionForm != null) {
				BeanUtils.copyProperties(form, sessionForm);
				request.setAttribute("msg", "");
			}
		}
		Map<String, String> map = getReqMap(request);
		String[] investAccts = getSessionInvestAcctNos(request);
		Map<String, Map<String, MsgField>> aipAccts = fundAcctService.queryAcctsNeedPay(map, investAccts);
		request.getSession().setAttribute("aipAccts", aipAccts);
		request.setAttribute("aipAccts", aipAccts);
		return forward("/pages/invest/fundacct/toPay.jsp");
	}

	// 进入补缴金额确认页面
	public ActionForward verifyPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 验证
		// initData(request);
		InvestInfoActionForm actionForm = (InvestInfoActionForm) form;
		try {
			String acctNo = actionForm.getAcctNo();
			Map<String, String> map = getReqMap(request);
			Map<String, MsgField> aipAcctMap = fundAcctService.queryOneAcctNeedPay(map, acctNo);
			String balanceStr = aipAcctMap.get(MsgField.curr_can_use.getFieldCode()).getValue();
			String debtStr = aipAcctMap.get(MsgField.debt_bal.getFieldCode()).getValue();
			Double debt = Double.parseDouble(debtStr);
			Assert.isTrue(Double.parseDouble(balanceStr) >= debt, "可用金额小于欠款金额");
			actionForm.setAcctNo(acctNo);
			actionForm.setBalance(balanceStr);
			actionForm.setDebt(debt + "");
			WebUtils.setSessionAttribute(request, "form", form);
			return forward("/pages/invest/fundacct/payVerify.jsp");
		} catch (Exception e) {
			setResult(false, e.getMessage(), request);
			return toPay(mapping, actionForm, request, response);
		}
	}

	// 执行补缴欠费操作
	public ActionForward doPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvestInfoActionForm actionForm = (InvestInfoActionForm) WebUtils.getSessionAttribute(request, "form");
		try {
			String acctNo = actionForm.getAcctNo();
			Map<String, String> map = getReqMap(request);
			Map<String, MsgField> aipAcctMap = fundAcctService.queryOneAcctNeedPay(map, acctNo);
			String balanceStr = aipAcctMap.get(MsgField.curr_can_use.getFieldCode()).getValue();
			String debtStr = aipAcctMap.get(MsgField.debt_bal.getFieldCode()).getValue();
			Double debt = Double.parseDouble(debtStr);
			Assert.isTrue(Double.parseDouble(balanceStr) >= debt, "可用金额小于欠款金额");
			actionForm.setAcctNo(acctNo);
			actionForm.setBalance(balanceStr);
			actionForm.setDebt(debt + "");
			Map<String, String> doMap = getReqMap(request);
			doMap.put(MsgField.h_bank_no.getFieldCode(), getSessionBranch(request).getValue());
			doMap.put(MsgField.aip_no.getFieldCode(), acctNo);
			// doMap.put(MsgField.account_no.getFieldCode(),
			// actionForm.getPayChnlNo());
			doMap.put(MsgField.deduct_type.getFieldCode(), DeductType.DEDUCT_TYPE_28.getValue());
			doMap.put(MsgField.exch_bal.getFieldCode(), debt + "");
			String orderNo = fundAcctService.payDebt(doMap);
			setResult(true, "补缴已受理,流水号" + orderNo, request);
			request.getSession().setAttribute("aipAccts", null);
			setReturnUrl("/investFundAction.do?action=toPay", request);
			String msg = LogUtils.r("补缴已受理,流水号{?},机构为{?},定投账号为{?},补缴金额{?}", orderNo, getSessionBranch(request)
					.getValue(), acctNo, debt + "");
			super.logSuccess(request, UserLogType.ADD.getValue(), msg);
			return success(mapping);
		} catch (Exception e) {
			setResult(false, e.getMessage(), request);
			String msg = LogUtils.r("补缴失败,失败原因:{?}", e.getMessage());
			super.logError(request, UserLogType.ADD.getValue(), msg);
			return toPay(mapping, actionForm, request, response);
		}
	}

	public ActionForward getBankAcct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String acctNo = request.getParameter("acctNo");
		SignContract contract = signContractDao.getUniqueResult(SignContract.class, "investAcctNo", acctNo);
		JSONObject object = new JSONObject();
		contract.setPayChnlTypeName(PayChnlType.valueOf(contract.getPayChnlType()).getName());
		object.put("id", contract.getId());
		object.put("bankAcctInfo", PayChnlType.valueOf(contract.getPayChnlType()).getName()+" "+contract.getPayChnlNo());
		respond(response, object.toString());
		return null;
	}

	private void initData(HttpServletRequest request) throws Exception {
		Map<String, Object> map = getParaMap();
		map.put("custId", getSessionCustId(request));
		map.put("busiCode", BusiType.GOLD.getValue());
		map.put("state", SignState.SIGNED.getValue());
		map.put("branchNo", getSessionBranch(request).getValue());
		List<SignContract> list = signContractDao.findByParams(map);
		for (int i = 0; i < list.size(); i++) {
			SignContract contract = list.get(i);
			contract.setPayChnlTypeName(PayChnlType.valueOf(contract.getPayChnlType()).getName());
		}
		request.setAttribute("payChnls", list);
		AipMode.setInReq(request);
		AipPeriod.setInReq(request);
		PlanState.setInReq(request);
		AipType.setInReq(request);
	}
}
