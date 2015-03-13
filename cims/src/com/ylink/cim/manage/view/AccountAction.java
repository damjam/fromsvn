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
			String tip = "�����ɹ�";
			if (balance < actionForm.getAmount()) {
				tip += "���ѿ۳�����ˮ��"+MoneyUtil.getFormatStr2(actionForm.getAmount()-balance)+"Ԫ";
			}
			tip += "����ǰ�˻����"+MoneyUtil.getFormatStr2(balance)+"Ԫ";
			setResult(true, tip, request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toDeposit(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��"+e.getMessage(), request);
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
			setResult(true, "�����ɹ�", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toWithdraw(mapping, form, request, response);
		} catch (Exception e) {
			setResult(false, "����ʧ��", request);
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
			params.put("acctNo", actionForm.getAcctNo());
			params.put("type", actionForm.getType());
			params.put("startCreateDate", actionForm.getStartCreateDate());
			params.put("endCreateDate", actionForm.getEndCreateDate());
			Paginater paginater = accountDetailDao.findPager(params, getPager(request));
			saveQueryResult(request, paginater);
			return forward("/pages/manage/account/accountDetailList.jsp"); 
	}
	
}
