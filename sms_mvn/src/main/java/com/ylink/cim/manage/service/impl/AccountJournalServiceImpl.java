package com.ylink.cim.manage.service.impl;

import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.InoutType;
import com.ylink.cim.common.type.InputTradeType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.manage.dao.AccountJournalDao;
import com.ylink.cim.manage.domain.AccountJournal;
import com.ylink.cim.manage.domain.InnerAcct;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.service.AccountJournalService;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.AmountUtils;
import flink.util.DateUtil;

@Component("accountJournalService")
public class AccountJournalServiceImpl implements AccountJournalService {

	@Autowired
	private AccountJournalDao accountJournalDao;
	@Autowired
	private IdFactoryService idFactoryService;

	@Override
	public void add(String tradeType, Double amount, String billId,
			String remark, UserInfo userInfo) throws BizException {
		InnerAcct innerAcct = accountJournalDao.findById(InnerAcct.class,
				Constants.INNER_ACCTID);
		accountJournalDao.lock(innerAcct, LockMode.PESSIMISTIC_WRITE);
		Double prebalance = innerAcct.getBalance();
		innerAcct.setUpdateDate(DateUtil.getCurrent());
		innerAcct.setBalance(AmountUtils.add(innerAcct.getBalance(), amount));
		innerAcct.setInTimes(innerAcct.getInTimes() + 1);
		innerAcct.setTimes(innerAcct.getInTimes() + innerAcct.getOutTimes());
		accountJournalDao.update(innerAcct);
		addAcctJur(InoutType.TYPE_IN.getValue(), tradeType, prebalance,
				innerAcct.getBalance(), amount, billId, remark, userInfo);
	}

	@Override
	public void deduct(String tradeType, Double amount, String billId,
			String remark, UserInfo userInfo) throws BizException {
		InnerAcct innerAcct = accountJournalDao.findById(InnerAcct.class,
				Constants.INNER_ACCTID);
		accountJournalDao.lock(innerAcct, LockMode.PESSIMISTIC_WRITE);
		Double prebalance = innerAcct.getBalance();
		if (prebalance < amount) {
			throw new BizException("����");
		}
		innerAcct.setUpdateDate(DateUtil.getCurrent());
		innerAcct.setBalance(AmountUtils.add(innerAcct.getBalance(), -amount));
		innerAcct.setOutTimes(innerAcct.getOutTimes() + 1);
		innerAcct.setTimes(innerAcct.getInTimes() + innerAcct.getOutTimes());
		accountJournalDao.update(innerAcct);
		addAcctJur(InoutType.TYPE_OUT.getValue(), tradeType, prebalance,
				innerAcct.getBalance(), amount, billId, remark, userInfo);
	}

	private void addAcctJur(String inoutType, String tradeType,
			Double prebalance, Double balance, Double amount, String billId,
			String remark, UserInfo userInfo) throws BizException {
		AccountJournal accountJournal = new AccountJournal();
		accountJournal.setId(idFactoryService
				.generateId(Constants.ACCOUNT_JOURNAL_ID));
		accountJournal.setPrebalance(prebalance);
		accountJournal.setBalance(balance);
		accountJournal.setAmount(amount);
		accountJournal.setRemark(remark);
		accountJournal.setCreateDate(DateUtil.getCurrent());
		accountJournal.setCreateUser(userInfo.getUserName());
		accountJournal.setBillId(billId);
		accountJournal.setInoutType(inoutType);
		accountJournal.setTradeType(tradeType);
		accountJournal.setBranchNo(userInfo.getBranchNo());
		accountJournalDao.save(accountJournal);
	}

	@Override
	public void reverse(String tradeType, String billId, String remark, UserInfo userInfo) throws BizException {
		Double amount = 0d;
		if (InputTradeType.ORDER_INCOME.getValue().equals(tradeType)) {
			OrderRecord bill = accountJournalDao.findById(
					OrderRecord.class, billId);
			Assert.notNull(bill, "�Ҳ����˵�");
			accountJournalDao.lock(bill, LockMode.PESSIMISTIC_WRITE);
			Assert.isTrue(BillState.PAID.getValue().equals(bill.getState()),
					"ֻ���ѽ�״̬���˵����ܳ���!");
			amount = bill.getAmount();
			bill.setState(BillState.REVERSE.getValue());
			accountJournalDao.update(bill);
		} else {
			throw new BizException("�޷��Ե�ǰ���׽��г���!");
		}
		deduct(TradeType.IN_REVERSE.getValue(), amount, billId, remark,
				userInfo);
		
	}

	
}
