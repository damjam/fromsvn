package com.ylink.cim.manage.view;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
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
import flink.web.BaseAction;
@Scope("prototype")
@Component
public class AccountAction extends BaseAction implements ModelDriven<Account> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private AccountDetailDao accountDetailDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountService accountService;

	public String toDeposit() throws Exception {
		YesNoType.setInReq(request);

		String acctNo = request.getParameter("id");
		Account account = (Account) accountDao.findById(acctNo);
		BeanUtils.copyProperties(model, account);
		// return forward("/pages/manage/account/deposit.jsp");
		return "deposit";
	}

	public String deposit() throws Exception {
		try {
			Double balance = accountService.deposit(model.getId(), model.getAmount(), getSessionUser(request));
			String tip = "�����ɹ�";
			if (balance < model.getAmount()) {
				tip += "���ѿ۳�����ˮ��" + MoneyUtil.getFormatStr2(model.getAmount() - balance) + "Ԫ";
			}
			tip += "����ǰ�˻����" + MoneyUtil.getFormatStr2(balance) + "Ԫ";
			setResult(true, tip, request);
		} catch (BizException e) {
			e.printStackTrace();
			setResult(false, e.getMessage(), request);
			return toDeposit();
		} catch (Exception e) {
			e.printStackTrace();
			setResult(false, "����ʧ��" + e.getMessage(), request);
			return toDeposit();
		}
		return list();
	}

	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();

		map.put("houseSn", model.getHouseSn());
		map.put("state", model.getState());
		map.put("ownerName", model.getOwnerName());
		map.put("year", model.getYear());
		map.put("branchNo", getSessionBranchNo(request));
		Paginater paginater = accountDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		//return forward("/pages/manage/account/accountList.jsp");
		return "list";
	}

	public String toWithdraw() throws Exception {

		String acctNo = request.getParameter("id");
		Account account = (Account) accountDao.findById(acctNo);
		BeanUtils.copyProperties(model, account);
		// return forward("/pages/manage/account/withdraw.jsp");
		return "withdraw";
	}

	public String withdraw() throws Exception {
		try {

			accountService.withdraw(model.getId(), model.getAmount(), getSessionUser(request));
			setResult(true, "�����ɹ�", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toWithdraw();
		} catch (Exception e) {
			setResult(false, "����ʧ��", request);
			e.printStackTrace();
			return toWithdraw();
		}
		return list();
	}

	public String detail() throws Exception {
		AccountChangeType.setInReq(request);
		Map<String, Object> params = getParaMap();
		String acctNo = model.getAcctNo();
		Account account = accountDao.findById(acctNo);
		if (!BranchType.HQ_0000.getValue().equals(getSessionBranchNo(request))
				&& !account.getBranchNo().equals(getSessionBranchNo(request))) {
			setResult(false, "����Ȩ�鿴��ҵ���˻�", request);
			list();
		}
		params.put("acctNo", model.getAcctNo());
		params.put("type", model.getType());
		params.put("startCreateDate", model.getStartCreateDate());
		params.put("endCreateDate", model.getEndCreateDate());
		Paginater paginater = accountDetailDao.findPager(params, getPager(request));
		saveQueryResult(request, paginater);
		// return forward("/pages/manage/account/accountDetailList.jsp");
		return "detail_list";
	}

	public Account getModel() {
		return model;
	}

	private Account model = new Account();

}
