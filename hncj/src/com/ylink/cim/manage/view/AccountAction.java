package com.ylink.cim.manage.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.AccountChangeType;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.YesNoType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.AccountDetailDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.service.AccountService;

import flink.etc.BizException;
import flink.util.Paginater;
import flink.web.BaseDispatchAction;

public class AccountAction extends BaseDispatchAction {
	private AccountDetailDao accountDetailDao = (AccountDetailDao)getService("accountDetailDao");
	private AccountDao accountDao = (AccountDao)getService("accountDao");
	private AccountService accountService = (AccountService)getService("accountService");
	public ActionForward toDeposit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		YesNoType.setInReq(request);
		AccountActionForm actionForm = (AccountActionForm)form;
		String acctNo = request.getParameter("id");
		Account account = (Account)accountDao.findById(acctNo);
		BeanUtils.copyProperties(actionForm, account);
		return forward("/pages/manage/account/deposit.jsp");
	}
	public ActionForward deposit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			AccountActionForm actionForm = (AccountActionForm)form;
			Double balance = accountService.deposit(actionForm.getId(), actionForm.getAmount(), getSessionUser(request));
			String tip = "操作成功";
			if (balance < actionForm.getAmount()) {
				tip += "，已扣除待缴水费"+MoneyUtil.getFormatStr2(actionForm.getAmount()-balance)+"元";
			}
			tip += "，当前账户余额"+MoneyUtil.getFormatStr2(balance)+"元";
			setResult(true, tip, request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toDeposit(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "操作失败"+e.getMessage(), request);
			return toDeposit(mapping, form, request, response);
		}
		return list(mapping, form, request, response);
	}
	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();
		AccountActionForm actionForm = (AccountActionForm)form;
		map.put("houseSn", actionForm.getHouseSn());
		map.put("state", actionForm.getState());
		map.put("ownerName", actionForm.getOwnerName());
		map.put("year", actionForm.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = accountDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return forward("/pages/manage/account/accountList.jsp");
	}
	public ActionForward toWithdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		AccountActionForm actionForm = (AccountActionForm)form;
		String acctNo = request.getParameter("id");
		Account account = (Account)accountDao.findById(acctNo);
		BeanUtils.copyProperties(actionForm, account);
		return forward("/pages/manage/account/withdraw.jsp");
	}
	public ActionForward withdraw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			AccountActionForm actionForm = (AccountActionForm)form;
			accountService.withdraw(actionForm.getId(), actionForm.getAmount(), getSessionUser(request));
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
	public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			AccountActionForm actionForm = (AccountActionForm)form;
			AccountChangeType.setInReq(request);
			Map<String, Object> params = getParaMap();
			String acctNo = actionForm.getAcctNo();
			Account account = accountDao.findById(acctNo);
			if (!BranchType.HQ_0000.getValue().equals(getSessionBranchNo(request)) && !account.getBranchNo().equals(getSessionBranchNo(request))) {
				setResult(false, "你无权查看该业主账户", request);
				list(mapping, actionForm, request, response);
			}
			params.put("acctNo", actionForm.getAcctNo());
			params.put("type", actionForm.getType());
			params.put("startCreateDate", actionForm.getStartCreateDate());
			params.put("endCreateDate", actionForm.getEndCreateDate());
			Paginater paginater = accountDetailDao.findPager(params, getPager(request));
			saveQueryResult(request, paginater);
			return forward("/pages/manage/account/accountDetailList.jsp"); 
	}
	
}
