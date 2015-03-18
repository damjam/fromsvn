package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.OwnerState;
import com.ylink.cim.common.type.AccountChangeType;
import com.ylink.cim.common.type.InoutType;
import com.ylink.cim.common.type.InputTradeType;
import com.ylink.cim.common.type.OutputTradeType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.dao.WaterBillDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.AccountDetail;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.domain.WaterBill;
import com.ylink.cim.manage.service.AccountJournalService;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.user.domain.UserInfo;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.AmountUtils;
import flink.util.DateUtil;

@Component("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private IdFactoryService idFactoryService;
	@Autowired
	private WaterBillDao waterBillDao;
	@Autowired
	private AccountJournalService accountJournalService;
	
	public void add(String ownerId, UserInfo sessionUser) throws BizException {
		Account account = new Account();
		OwnerInfo ownerInfo = ownerInfoDao.findByIdWithLock(ownerId);
		account.setId(ownerInfo.getId());
		account.setOwnerName(ownerInfo.getOwnerName());
		account.setCreditAmt(0d);
		account.setFreezeAmt(0d);
		account.setBalance(0d);
		account.setCreateDate(DateUtil.getCurrent());
		account.setCreateUser(sessionUser.getUserName());
		account.setHouseSn(ownerInfo.getHouseSn());
		account.setState(OwnerState.NORMAL.getValue());
		account.setBranchNo(sessionUser.getBranchNo());
		accountDao.save(account);
		ownerInfo.setHasAcct(Symbol.YES);
		ownerInfoDao.update(ownerInfo);
		
	}

	public String addAccountDetail(Account account, Double amount, String type, String inoutType, String billId, String remark, UserInfo userInfo) throws BizException {
		AccountDetail detail = new AccountDetail();
		String id = idFactoryService.generateId(Constants.ACCOUNT_DETAIL_ID);
		detail.setId(id);
		detail.setBillId(billId);
		detail.setAmount(amount);
		detail.setBalance(account.getBalance());
		detail.setAcctNo(account.getId());
		detail.setOwnerName(account.getOwnerName());
		detail.setCreateDate(DateUtil.getCurrent());
		detail.setCreateUser(userInfo.getUserName());
		detail.setType(type);
		detail.setInoutType(inoutType);
		detail.setRemark(remark);
		detail.setBranchNo(userInfo.getBranchNo());
		accountDao.save(detail);
		return id;
	}
	private Double checkDebt(String no, String houseSn, UserInfo userInfo) throws BizException{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseSn", houseSn);
		params.put("states", new String[] {BillState.PART_PAID.getValue(), BillState.UNPAY.getValue()});
		params.put("branchNo", userInfo.getBranchNo());
		List<WaterBill> bills = waterBillDao.findBills(params);
		Account account = accountDao.findByIdWithLock(no);
		for (int i = 0; i < bills.size(); i++) {
			WaterBill bill = bills.get(i);
			waterBillDao.lock(bill, LockMode.UPGRADE);
			Double debtAmt = bill.getAmount() - bill.getPaidAmt();
			if (account.getBalance() >= debtAmt) {
				bill.setState(BillState.PAID.getValue());
				Double balance = account.getBalance() - debtAmt;
				bill.setPaidAmt(bill.getAmount());
				bill.setState(BillState.PAID.getValue());
				bill.setChargeDate(DateUtil.getCurrent());
				bill.setChargeUser(userInfo.getUserName());
				bill.setRemark("ˮ�Ѵ�Ԥ���˻��п۳�,��ǰ���"+MoneyUtil.getFormatStr2(balance)+"Ԫ");
				waterBillDao.update(bill);
				payBill(no, debtAmt, bill.getId(), "�۳�ˮ��"+MoneyUtil.getFormatStr2(debtAmt)+"Ԫ", userInfo);
				/*
				//�����
				account.setBalance(balance);
				accountDao.update(account);
				
				addAccountDetail(account, debtAmt, AccountChangeType.WATER_FEE.getValue(), InoutType.TYPE_OUT.getValue(), bill.getId(), "�۳�������ˮ��"+debtAmt+"Ԫ", userInfo);
				*/
			}else {
				Double oweAmt = debtAmt - account.getBalance();
				bill.setPaidAmt(bill.getPaidAmt()+account.getBalance());
				bill.setRemark("�貹��"+MoneyUtil.getFormatStr2(oweAmt)+"Ԫ");
				bill.setChargeDate(DateUtil.getCurrent());
				bill.setChargeUser(userInfo.getUserName());
				bill.setState(BillState.PART_PAID.getValue());
				payBill(no, account.getBalance(), bill.getId(), "�۳�ˮ��"+MoneyUtil.getFormatStr2(account.getBalance())+"Ԫ,��ǰǷ��"+MoneyUtil.getFormatStr2(oweAmt)+"Ԫ", userInfo);
				waterBillDao.update(bill);
				/*
				account.setBalance(0d);
				accountDao.update(account);
				
				addAccountDetail(account, debtAmt, AccountChangeType.WATER_FEE.getValue(), InoutType.TYPE_OUT.getValue(), bill.getId(), "�۳�������ˮ��"+account.getBalance()+"Ԫ,��ǰǷ��"+oweAmt+"Ԫ", userInfo);
				*/
			}
		}
		return account.getBalance();
	}
	public Double deposit(String no, Double amount, UserInfo userInfo) throws BizException {
		Account account = accountDao.findByIdWithLock(no);
		account.setBalance(AmountUtils.add(account.getBalance(), amount));
		accountDao.update(account);
		String billId = addAccountDetail(account, amount, AccountChangeType.DEPOSIT.getValue(), InoutType.TYPE_IN.getValue(), "", "", userInfo);
		OwnerInfo ownerInfo = ownerInfoDao.findById(no);
		Assert.notNull(ownerInfo, "�Ҳ���ҵ����Ϣ");
		String houseSn = ownerInfo.getHouseSn();
		accountJournalService.add(InputTradeType.DEPOSIT.getValue(), amount, billId,houseSn+"ҵ��"+ownerInfo.getOwnerName()+"��ֵ", userInfo);
		return checkDebt(no, houseSn, userInfo);
	}
	
	public void payBill(String no, Double amount, String billId, String remark, UserInfo userInfo) throws BizException {
		Account account = accountDao.findByIdWithLock(no);
		Double balance = AmountUtils.subtract(account.getBalance(), amount);
		account.setBalance(balance);
		accountDao.update(account);
		addAccountDetail(account, amount, AccountChangeType.WATER_FEE.getValue(), InoutType.TYPE_OUT.getValue(), billId, remark, userInfo);
		
	}

	public void withdraw(String no, Double amount, UserInfo userInfo) throws BizException {
		Account account = accountDao.findByIdWithLock(no);
		Double balance = AmountUtils.subtract(account.getBalance(), amount);
		Assert.isTrue(balance >= 0, "���ֽ��ܴ������");
		account.setBalance(balance);
		accountDao.update(account);
		String billId = addAccountDetail(account, amount, AccountChangeType.WITHDRAW.getValue(), InoutType.TYPE_OUT.getValue(), "", "", userInfo);
		OwnerInfo ownerInfo = ownerInfoDao.findById(account.getId());
		accountJournalService.deduct(OutputTradeType.WITHDRAW.getValue(), amount, billId, "��"+ownerInfo.getHouseSn()+"ҵ��"+ownerInfo.getOwnerName()+"Ԥ��ˮ��", userInfo);
	}
	
	
}
