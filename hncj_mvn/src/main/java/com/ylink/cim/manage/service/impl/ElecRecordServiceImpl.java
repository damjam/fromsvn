package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.RecordState;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.ElecRecordDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.ElecBill;
import com.ylink.cim.manage.domain.ElecRecord;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.manage.service.ElecRecordService;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;

@Component("elecRecordService")
public class ElecRecordServiceImpl implements ElecRecordService {
	@Autowired
	private ElecRecordDao elecRecordDao;
	@Autowired
	private IdFactoryService idFactoryService;
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountDao accountDao;

	@Override
	public void saveElecRecord(ElecRecord elecRecord, UserInfo userInfo)
			throws BizException {
		String houseSn = elecRecord.getHouseSn();
		Assert.notNull(elecRecordDao.findById(HouseInfo.class, houseSn),
				"房屋信息不存在");
		String recordMonth = elecRecord.getRecordMonth();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseSn", elecRecord.getHouseSn());
		params.put("recordMonth", recordMonth);
		Assert.isEmpty(elecRecordDao.findPreRecords(params), "月份" + recordMonth
				+ "的记录已存在");
		elecRecord
				.setId(idFactoryService.generateId(Constants.WATER_RECORD_ID));
		elecRecord.setCreateDate(DateUtil.getCurrent());
		elecRecord.setCreateUser(userInfo.getUserName());
		elecRecord.setState(RecordState.UNCHECK.getValue());
		elecRecordDao.save(elecRecord);

	}

	@Override
	public boolean checkRecord(String id, UserInfo userInfo)
			throws BizException {
		ElecRecord elecRecord = elecRecordDao.findByIdWithLock(id);
		if (elecRecord.getNum() == null || elecRecord.getNum() <= 0) {
			return false;
		}
		elecRecord.setState(RecordState.CHECKED.getValue());
		elecRecord.setCheckDate(DateUtil.getCurrent());
		elecRecord.setCheckUser(userInfo.getUserName());
		elecRecordDao.save(elecRecord);
		// 填账单
		ElecBill elecBill = new ElecBill();
		String price = ParaManager.getWaterPrice();
		Double amount = Double.parseDouble(price) * elecRecord.getNum();
		// 查询未缴费的账单
		elecBill.setAmount(amount);
		elecBill.setPreRecordDate(elecRecord.getPreRecordDate());
		elecBill.setCurRecordDate(elecRecord.getCurRecordDate());
		elecBill.setId(idFactoryService.generateId(Constants.BILL_ID));
		elecBill.setHouseSn(elecRecord.getHouseSn());
		elecBill.setPrenum(elecRecord.getPrenum());
		elecBill.setCurnum(elecRecord.getCurnum());
		elecBill.setNum(elecRecord.getNum());
		elecBill.setState(BillState.UNPAY.getValue());
		elecBill.setCreateDate(DateUtil.getCurrent());
		elecBill.setCreateUser(userInfo.getUserName());
		elecBill.setAmount(amount);
		elecBill.setRecordMonth(elecRecord.getRecordMonth());
		elecBill.setPrice(Double.parseDouble(ParaManager.getWaterPrice()));
		OwnerInfo ownerInfo = ownerInfoDao
				.getNormalOwner(elecBill.getHouseSn(), elecBill.getBranchNo());
		Assert.notNull(ownerInfo, "找不到房屋编号" + elecBill.getHouseSn() + "对应的业主信息");
		elecBill.setOwnerName(ownerInfo.getOwnerName());
		if (Symbol.YES.equals(ownerInfo.getHasAcct())) {
			Account account = accountDao.findByIdWithLock(ownerInfo.getId());
			String remark = "";
			if (account != null) {
				if (account.getBalance() > 0) {
					// 余额足够
					if (account.getBalance() >= elecBill.getAmount()) {
						Double balance = account.getBalance()
								- elecBill.getAmount();
						elecBill.setPaidAmt(elecBill.getAmount());
						elecBill.setState(BillState.PAID.getValue());
						elecBill.setChargeDate(DateUtil.getCurrent());
						elecBill.setChargeUser(userInfo.getUserName());
						remark = "扣除水费"
								+ MoneyUtil.getFormatStr2(elecBill.getAmount())
								+ "元，当前余额" + MoneyUtil.getFormatStr2(balance)
								+ "元";
						elecBill.setRemark("水费从预存账户中扣除，当前余额"
								+ MoneyUtil.getFormatStr2(balance) + "元");
						accountService.payBill(account.getId(),
								elecBill.getPaidAmt(), elecBill.getId(),
								remark, userInfo);
						// account.setBalance(account.getBalance() -
						// elecBill.getAmount());
						// accountService.addAccountDetail(account, amount,
						// AccountChangeType.WATER_FEE.getValue(),
						// InoutType.TYPE_OUT.getValue(), elecBill.getId(),
						// remark, userInfo);
					} else {
						// 余额不够
						elecBill.setPaidAmt(account.getBalance());
						elecBill.setState(BillState.PART_PAID.getValue());
						elecBill.setChargeDate(DateUtil.getCurrent());
						elecBill.setChargeUser(userInfo.getUserName());
						elecBill.setRemark("欠费"
								+ MoneyUtil.getFormatStr2(elecBill.getAmount()
										- elecBill.getPaidAmt()));
						remark = "扣除水费"
								+ MoneyUtil.getFormatStr2(account.getBalance())
								+ "元，当前余额为零";
						accountService.payBill(account.getId(),
								elecBill.getPaidAmt(), elecBill.getId(),
								remark, userInfo);
					}
				}
			}
		}

		elecRecordDao.save(elecBill);
		return true;
	}

	@Override
	public void deleteRecord(String id, UserInfo userInfo) throws BizException {
		elecRecordDao.deleteById(id);
	}

	@Override
	public ElecRecord getPreRecord(String houseSn) throws BizException {
		return elecRecordDao.findPreRecord(houseSn);
	}

	@Override
	public Integer checkAllRecord(UserInfo userInfo) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", RecordState.UNCHECK.getValue());
		params.put("branchNo", userInfo.getBranchNo());
		List<ElecRecord> list = elecRecordDao.findRecords(params);
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			if (checkRecord(list.get(i).getId(), userInfo)) {
				k++;
			}
		}
		return k;
	}

	@Override
	public void importDeposit(List<ElecRecord> list, UserInfo userInfo)
			throws BizException {
		for (int i = 0; i < list.size(); i++) {
			ElecRecord record = list.get(i);
			record.setCreateDate(DateUtil.getCurrent());
			record.setCreateUser(userInfo.getUserName());
			record.setId(idFactoryService.generateId(Constants.WATER_RECORD_ID));
			record.setState(RecordState.UNCHECK.getValue());
			elecRecordDao.save(record);
			if (i % 100 == 0) {
				elecRecordDao.flush();
			}
		}
	}

}
