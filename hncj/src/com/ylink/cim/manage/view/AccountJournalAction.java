package com.ylink.cim.manage.view;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.InoutType;
import com.ylink.cim.common.type.InputTradeType;
import com.ylink.cim.common.type.OutputTradeType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.manage.dao.AccountJournalDao;
import com.ylink.cim.manage.domain.InnerAcct;
import com.ylink.cim.manage.service.AccountJournalService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class AccountJournalAction extends BaseDispatchAction {
	private AccountJournalDao accountJournalDao = (AccountJournalDao) getService("accountJournalDao");
	private AccountJournalService accountJournalService = (AccountJournalService) getService("accountJournalService");

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BillState.setInReq(request);
		InoutType.setInReq(request);
		TradeType.setInReq(request);
		Map<String, Object> map = getParaMap();
		AccountJournalActionForm actionForm = (AccountJournalActionForm) form;
		map.put("tradeType", actionForm.getTradeType());
		map.put("inoutType", actionForm.getInoutType());
		map.put("startCreateDate", actionForm.getStartCreateDate());
		map.put("endCreateDate", actionForm.getEndCreateDate());
		map.put("year", actionForm.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = accountJournalDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = accountJournalDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		return forward("/pages/manage/actJournal/accountJournal.jsp");
	}

	public ActionForward toWithdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, OutputTradeType> map = new LinkedHashMap<String, OutputTradeType>();
		map.putAll(OutputTradeType.ALL);
		map.remove("10");
		map.remove("16");
		request.setAttribute("tradeTypes", map.values());
		AccountJournalActionForm actionForm = (AccountJournalActionForm) form;
		InnerAcct innerAcct = accountJournalDao.findById(InnerAcct.class, Constants.INNER_ACCTID);
		BeanUtils.copyProperties(actionForm, innerAcct);
		return forward("/pages/manage/actJournal/outputFund.jsp");
	}

	public ActionForward toDeposit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InputTradeType.setInReq(request);
		return forward("/pages/manage/actJournal/inputFund.jsp");
	}
	public ActionForward toReverse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InputTradeType.setInReq(request);
		return forward("/pages/manage/actJournal/reverse.jsp");
	}
	public ActionForward toGather(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("today", DateUtil.getCurrentDate());
		return forward("/pages/manage/actJournal/accountGather.jsp");
	}
	public ActionForward toReport(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("today", DateUtil.getCurrentDate());
		return forward("/pages/manage/actJournal/tradeReport.jsp");
	}
	public ActionForward reverse(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			AccountJournalActionForm actionForm = (AccountJournalActionForm) form;
			accountJournalService.reverse(actionForm.getTradeType(), actionForm.getBillId(), actionForm.getRemark(), getSessionUser(request));
			actionForm.setTradeType("");
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toReverse(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toReverse(mapping, form, request, response);
		}
		return this.list(mapping, form, request, response);
	}
	public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			AccountJournalActionForm actionForm = (AccountJournalActionForm) form;
			accountJournalService.deduct(actionForm.getTradeType(), actionForm.getAmount(), "", actionForm.getRemark(),
					getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toWithdraw(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toWithdraw(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}

	public ActionForward deposit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			AccountJournalActionForm actionForm = (AccountJournalActionForm) form;
			accountJournalService.add(actionForm.getTradeType(), actionForm.getAmount(), "", actionForm.getRemark(),
					getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toWithdraw(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toWithdraw(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}

}
