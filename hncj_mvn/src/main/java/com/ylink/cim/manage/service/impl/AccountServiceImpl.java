package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
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

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.AmountUtils;
import flink.util.DateUtil;
import flink.util.LogUtils;
import flink.util.MsgUtils;

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
	
	@Override
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

	@Override
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
			waterBillDao.lock(bill, LockMode.PESSIMISTIC_WRITE);
			Double debtAmt = bill.getAmount() - bill.getPaidAmt();
			if (account.getBalance() >= debtAmt) {
				bill.setState(BillState.PAID.getValue());
				Double balance = account.getBalance() - debtAmt;
				bill.setPaidAmt(bill.getAmount());
				bill.setState(BillState.PAID.getValue());
				bill.setChargeDate(DateUtil.getCurrent());
				bill.setChargeUser(userInfo.getUserName());
				bill.setRemark("水费从预存账户中扣除,当前余额"+MoneyUtil.getFormatStr2(balance)+"元");
				waterBillDao.update(bill);
				payBill(no, debtAmt, bill.getId(), "扣除水费"+MoneyUtil.getFormatStr2(debtAmt)+"元", userInfo);
				/*
				//新余额
				account.setBalance(balance);
				accountDao.update(account);
				
				addAccountDetail(account, debtAmt, AccountChangeType.WATER_FEE.getValue(), InoutType.TYPE_OUT.getValue(), bill.getId(), "扣除待补缴水费"+debtAmt+"元", userInfo);
				*/
			}else {
				Double oweAmt = debtAmt - account.getBalance();
				bill.setPaidAmt(bill.getPaidAmt()+account.getBalance());
				bill.setRemark("需补缴"+MoneyUtil.getFormatStr2(oweAmt)+"元");
				bill.setChargeDate(DateUtil.getCurrent());
				bill.setChargeUser(userInfo.getUserName());
				bill.setState(BillState.PART_PAID.getValue());
				payBill(no, account.getBalance(), bill.getId(), "扣除水费"+MoneyUtil.getFormatStr2(account.getBalance())+"元,当前欠费"+MoneyUtil.getFormatStr2(oweAmt)+"元", userInfo);
				waterBillDao.update(bill);
				/*
				account.setBalance(0d);
				accountDao.update(account);
				
				addAccountDetail(account, debtAmt, AccountChangeType.WATER_FEE.getValue(), InoutType.TYPE_OUT.getValue(), bill.getId(), "扣除待补缴水费"+account.getBalance()+"元,当前欠费"+oweAmt+"元", userInfo);
				*/
			}
		}
		return account.getBalance();
	}
	@Override
	public Double deposit(String no, Double amount, UserInfo userInfo) throws BizException {
		Account account = accountDao.findByIdWithLock(no);
		account.setBalance(AmountUtils.add(account.getBalance(), amount));
		account.setLastChangeDate(DateUtil.getCurrent());
		account.setLastTradeType(AccountChangeType.DEPOSIT.getValue());
		account.setLastTradeAmt(amount);
		accountDao.update(account);
		String billId = addAccountDetail(account, amount, AccountChangeType.DEPOSIT.getValue(), InoutType.TYPE_IN.getValue(), "", "", userInfo);
		OwnerInfo ownerInfo = ownerInfoDao.findById(no);
		Assert.notNull(ownerInfo, "找不到业主信息");
		String houseSn = ownerInfo.getHouseSn();
		accountJournalService.add(InputTradeType.DEPOSIT.getValue(), amount, billId,houseSn+"业主"+ownerInfo.getOwnerName()+"充值", userInfo);
		return checkDebt(no, houseSn, userInfo);
	}
	
	@Override
	public void payBill(String no, Double amount, String billId, String remark, UserInfo userInfo) throws BizException {
		Account account = accountDao.findByIdWithLock(no);
		Double balance = AmountUtils.subtract(account.getBalance(), amount);
		account.setBalance(balance);
		account.setLastChangeDate(DateUtil.getCurrent());
		account.setLastTradeType(AccountChangeType.WATER_FEE.getValue());
		account.setLastTradeAmt(amount);
		accountDao.update(account);
		addAccountDetail(account, amount, AccountChangeType.WATER_FEE.getValue(), InoutType.TYPE_OUT.getValue(), billId, remark, userInfo);
		
	}

	@Override
	public void withdraw(String no, Double amount, UserInfo userInfo) throws BizException {
		Account account = accountDao.findByIdWithLock(no);
		Double balance = AmountUtils.subtract(account.getBalance(), amount);
		Assert.isTrue(balance >= 0, "提现金额不能大于余额");
		account.setBalance(balance);
		account.setLastChangeDate(DateUtil.getCurrent());
		account.setLastTradeType(AccountChangeType.WITHDRAW.getValue());
		account.setLastTradeAmt(amount);
		accountDao.update(account);
		String billId = addAccountDetail(account, amount, AccountChangeType.WITHDRAW.getValue(), InoutType.TYPE_OUT.getValue(), "", "", userInfo);
		OwnerInfo ownerInfo = ownerInfoDao.findById(account.getId());
		accountJournalService.deduct(OutputTradeType.WITHDRAW.getValue(), amount, billId, "退"+ownerInfo.getHouseSn()+"业主"+ownerInfo.getOwnerName()+"预存水费", userInfo);
	}

	@Override
	public int addFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException{
		Integer totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				Account info = new Account();
				String houseSn = MapUtils.getString(map, "houseSn");
				Map<String, Object> params = new HashMap<>();
				params.put("houseSn", houseSn);
				params.put("branchNo", sessionUser.getBranchNo());
				OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn, sessionUser.getBranchNo());
				Assert.notNull(ownerInfo, MsgUtils.r("房屋编号为{?}的业主信息不存在", houseSn));
				Assert.isEmpty(accountDao.findList(params), "房屋编号为"+houseSn+"的账户信息已存在");
				try{
					BeanUtils.populate(info, map);
				}catch (Exception e){
					throw new BizException("程序出现异常:"+e.getMessage());
				}
				info.setOwnerName(ownerInfo.getOwnerName());
				info.setOwnerId(ownerInfo.getId());
				info.setCreateDate(DateUtil.getCurrent());
				info.setBranchNo(sessionUser.getBranchNo());
				info.setId(ownerInfo.getId());
				info.setFreezeAmt(0d);
				info.setCreditAmt(0d);
				info.setState(OwnerState.NORMAL.getValue());
				accountDao.save(info);
				totalCnt++;
			}
		}
		return totalCnt;
	}

	@Override
	public void openAcct(String houseSn, Double amount, UserInfo userInfo) throws BizException {
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(houseSn, userInfo.getBranchNo());
		Assert.notNull(ownerInfo, MsgUtils.r("找不到房屋编号为{?}的业主信息", houseSn));
		Assert.isNull(accountDao.findById(ownerInfo.getId()), MsgUtils.r("房屋编号为{?}的业主已开通账户,不可重新开户", houseSn));
		Account account = new Account();
		account.setId(ownerInfo.getId());
		account.setOwnerName(ownerInfo.getOwnerName());
		account.setCreditAmt(0d);
		account.setFreezeAmt(0d);
		account.setBalance(amount);
		account.setCreateDate(DateUtil.getCurrent());
		account.setCreateUser(userInfo.getUserName());
		account.setHouseSn(ownerInfo.getHouseSn());
		account.setState(OwnerState.NORMAL.getValue());
		account.setBranchNo(userInfo.getBranchNo());
		
		account.setLastChangeDate(DateUtil.getCurrent());
		account.setLastTradeType(AccountChangeType.DEPOSIT.getValue());
		account.setLastTradeAmt(amount);
		accountDao.save(account);
		ownerInfo.setHasAcct(Symbol.YES);
		ownerInfoDao.update(ownerInfo);
		String billId = addAccountDetail(account, amount, AccountChangeType.DEPOSIT.getValue(), InoutType.TYPE_IN.getValue(), "", "", userInfo);
		accountJournalService.add(InputTradeType.DEPOSIT.getValue(), amount, billId,houseSn+"业主"+ownerInfo.getOwnerName()+"充值", userInfo);
	}
}
