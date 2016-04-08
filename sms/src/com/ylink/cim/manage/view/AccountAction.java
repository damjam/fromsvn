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
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.AccountDetailDao;
import com.ylink.cim.manage.domain.Account;

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

	public String toDeposit() throws Exception {
		YesNoType.setInReq(request);

		String acctNo = request.getParameter("id");
		Account account = (Account) accountDao.findById(acctNo);
		BeanUtils.copyProperties(model, account);
		return "deposit";
	}


	public String list() throws Exception {
		BillState.setInReq(request);
		Map<String, Object> map = getParaMap();

		map.put("houseSn", model.getHouseSn());
		map.put("state", model.getState());
		map.put("ownerName", model.getOwnerName());
		map.put("year", model.getYear());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = accountDao.findPager(map, getPager(request));
		saveQueryResult(request, paginater);
		return "list";
	}

	public String toWithdraw() throws Exception {

		String acctNo = request.getParameter("id");
		Account account = (Account) accountDao.findById(acctNo);
		BeanUtils.copyProperties(model, account);
		// return forward("/pages/manage/account/withdraw.jsp");
		return "withdraw";
	}


	public String detail() throws Exception {
		AccountChangeType.setInReq(request);
		Map<String, Object> params = getParaMap();
		String acctNo = model.getAcctNo();
		Account account = accountDao.findById(acctNo);
		if (!BranchType.HQ_0000.getValue().equals(getSessionBranchNo(request))
				&& !account.getBranchNo().equals(getSessionBranchNo(request))) {
			setResult(false, "你无权查看该业主账户", request);
			list();
		}
		params.put("acctNo", model.getAcctNo());
		params.put("type", model.getType());
		params.put("startCreateDate", model.getStartCreateDate());
		params.put("endCreateDate", model.getEndCreateDate());
		Paginater paginater = accountDetailDao.findPager(params,
				getPager(request));
		saveQueryResult(request, paginater);
		// return forward("/pages/manage/account/accountDetailList.jsp");
		return "detail_list";
	}

	@Override
	public Account getModel() {
		return model;
	}

	private Account model = new Account();

}
