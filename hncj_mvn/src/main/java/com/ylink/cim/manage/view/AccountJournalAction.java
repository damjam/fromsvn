package com.ylink.cim.manage.view;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ModelDriven;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.InoutType;
import com.ylink.cim.common.type.InputTradeType;
import com.ylink.cim.common.type.OutputTradeType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.common.type.TradeType4Reverse;
import com.ylink.cim.manage.dao.AccountJournalDao;
import com.ylink.cim.manage.domain.AccountJournal;
import com.ylink.cim.manage.domain.InnerAcct;
import com.ylink.cim.manage.service.AccountJournalService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.Paginater;
import flink.web.BaseAction;

@Scope("prototype")
@Component
public class AccountJournalAction extends BaseAction implements
		ModelDriven<AccountJournal> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private AccountJournalDao accountJournalDao;
	@Autowired
	private AccountJournalService accountJournalService;

	public String list() throws Exception {
		BillState.setInReq(request);
		InoutType.setInReq(request);
		TradeType.setInReq(request);
		Map<String, Object> map = getParaMap();

		map.put("tradeType", model.getTradeType());
		map.put("inoutType", model.getInoutType());
		map.put("startCreateDate", model.getStartCreateDate());
		map.put("endCreateDate", model.getEndCreateDate());
		map.put("year", model.getYear());
		if (isHQ()) {//总部
			map.put("branchNo", model.getBranchNo());
		}else {//机构
			map.put("branchNo", getSessionBranchNo(request));
		}
		Paginater paginater = accountJournalDao.findPager(map,
				getPager(request));
		saveQueryResult(request, paginater);
		Map<String, Object> sumInfo = accountJournalDao.findSumInfo(map);
		request.setAttribute("sumInfo", sumInfo);
		// return forward("/pages/manage/actJournal/accountJournal.jsp");
		return "account_journal";
	}

	public String toWithdraw() throws Exception {
		Map<String, OutputTradeType> map = new LinkedHashMap<String, OutputTradeType>();
		map.putAll(OutputTradeType.ALL);
		map.remove("10");
		map.remove("16");
		request.setAttribute("tradeTypes", map.values());

		InnerAcct innerAcct = accountJournalDao.findById(InnerAcct.class,
				Constants.INNER_ACCTID);
		BeanUtils.copyProperties(model, innerAcct);
		// return forward("/pages/manage/actJournal/outputFund.jsp");
		return "output_fund";
	}

	public String toDeposit() throws Exception {
		InputTradeType.setInReq(request);
		// return forward("/pages/manage/actJournal/inputFund.jsp");
		return "intput_fund";
	}

	public String toReverse() throws Exception {
		TradeType4Reverse.setInReq(request);
		// return forward("/pages/manage/actJournal/reverse.jsp");
		return "reverse";
	}

	public String toGather() throws Exception {
		request.setAttribute("today", DateUtil.getCurrentDate());
		// return forward("/pages/manage/actJournal/accountGather.jsp");
		return "account_gather";
	}

	public String toReport() throws Exception {
		request.setAttribute("today", DateUtil.getCurrentDate());
		// return forward("/pages/manage/actJournal/tradeReport.jsp");
		return "trade_report";
	}

	public String reverse() throws Exception {
		try {

			accountJournalService.reverse(model.getTradeType(),
					model.getBillId(), model.getRemark(),
					getSessionUser(request));
			model.setTradeType("");
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toReverse();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toReverse();
		}
		return this.list();
	}

	public String withdraw() throws Exception {
		try {

			accountJournalService.deduct(model.getTradeType(),
					model.getAmount(), "", model.getRemark(),
					getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toWithdraw();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toWithdraw();
		}
		return list();
	}

	public String deposit() throws Exception {
		try {

			accountJournalService.add(model.getTradeType(), model.getAmount(),
					"", model.getRemark(), getSessionUser(request));
			setResult(true, "操作成功", request);
		} catch (BizException e) {
			setResult(false, e.getMessage(), request);
			return toWithdraw();
		} catch (Exception e) {
			setResult(false, "操作失败", request);
			e.printStackTrace();
			return toWithdraw();
		}
		return list();
	}

	@Override
	public AccountJournal getModel() {
		return model;
	}

	private AccountJournal model = new AccountJournal();
}
