package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.RecordState;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.common.util.ParaManager;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.dao.WaterRecordDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.domain.WaterBill;
import com.ylink.cim.manage.domain.WaterRecord;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.manage.service.WaterRecordService;
import com.ylink.cim.user.domain.UserInfo;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;
@Component("waterRecordService")
public class WaterRecordServiceImpl implements WaterRecordService{
	@Autowired
	private WaterRecordDao waterRecordDao;
	@Autowired
	private IdFactoryService idFactoryService;
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountDao accountDao;
	public void saveWaterRecord(WaterRecord waterRecord, UserInfo userInfo) throws BizException{
		String houseSn = waterRecord.getHouseSn();
		Assert.notNull(waterRecordDao.findById(HouseInfo.class, houseSn), "房屋信息不存在");
		String recordMonth = waterRecord.getRecordMonth();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseSn", waterRecord.getHouseSn());
		params.put("recordMonth", recordMonth);
		Assert.isEmpty(waterRecordDao.findPreRecords(params), "月份"+recordMonth+"的记录已存在");
		waterRecord.setId(idFactoryService.generateId(Constants.WATER_RECORD_ID));
		waterRecord.setCreateDate(DateUtil.getCurrent());
		waterRecord.setCreateUser(userInfo.getUserName());
		waterRecord.setState(RecordState.UNCHECK.getValue());
		waterRecordDao.save(waterRecord);
		
	}
	public boolean checkRecord(String id, UserInfo userInfo) throws BizException {
		WaterRecord waterRecord = waterRecordDao.findByIdWithLock(id);
		if (waterRecord.getNum() == null || waterRecord.getNum() <= 0) {
			return false;
		}
		waterRecord.setState(RecordState.CHECKED.getValue());
		waterRecord.setCheckDate(DateUtil.getCurrent());
		waterRecord.setCheckUser(userInfo.getUserName());
		waterRecordDao.save(waterRecord);
		//填账单
		WaterBill waterBill = new WaterBill();
		String price = ParaManager.getWaterPrice();
		Double amount = Double.parseDouble(price)*waterRecord.getNum();
		//查询未缴费的账单
		waterBill.setAmount(amount);
		waterBill.setPreRecordDate(waterRecord.getPreRecordDate());
		waterBill.setCurRecordDate(waterRecord.getCurRecordDate());
		waterBill.setId(idFactoryService.generateId(Constants.BILL_ID));
		waterBill.setHouseSn(waterRecord.getHouseSn());
		waterBill.setPrenum(waterRecord.getPrenum());
		waterBill.setCurnum(waterRecord.getCurnum());
		waterBill.setNum(waterRecord.getNum());
		waterBill.setState(BillState.UNPAY.getValue());
		waterBill.setCreateDate(DateUtil.getCurrent());
		waterBill.setCreateUser(userInfo.getUserName());
		waterBill.setAmount(amount);
		waterBill.setRecordMonth(waterRecord.getRecordMonth());
		waterBill.setPrice(Double.parseDouble(ParaManager.getWaterPrice()));
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(waterBill.getHouseSn());
		Assert.notNull(ownerInfo, "找不到房屋编号"+waterBill.getHouseSn()+"对应的业主信息");
		waterBill.setOwnerName(ownerInfo.getOwnerName());
		if (Symbol.YES.equals(ownerInfo.getHasAcct())) {
			Account account = accountDao.findByIdWithLock(ownerInfo.getId());
			String remark = "";
			if (account != null) {
				if(account.getBalance() > 0){
					//余额足够
					if (account.getBalance() >= waterBill.getAmount()) {
						Double balance = account.getBalance()-waterBill.getAmount();
						waterBill.setPaidAmt(waterBill.getAmount());
						waterBill.setState(BillState.PAID.getValue());
						waterBill.setChargeDate(DateUtil.getCurrent());
						waterBill.setChargeUser(userInfo.getUserName());
						remark = "扣除水费"+MoneyUtil.getFormatStr2(waterBill.getAmount())+"元，当前余额"+MoneyUtil.getFormatStr2(balance)+"元";
						waterBill.setRemark("水费从预存账户中扣除，当前余额"+MoneyUtil.getFormatStr2(balance)+"元");
						accountService.payBill(account.getId(), waterBill.getPaidAmt(),  waterBill.getId(), remark, userInfo);
						//account.setBalance(account.getBalance() - waterBill.getAmount());
						//accountService.addAccountDetail(account, amount, AccountChangeType.WATER_FEE.getValue(), InoutType.TYPE_OUT.getValue(), waterBill.getId(), remark, userInfo);
					}else {
						//余额不够
						waterBill.setPaidAmt(account.getBalance());
						waterBill.setState(BillState.PART_PAID.getValue());
						waterBill.setChargeDate(DateUtil.getCurrent());
						waterBill.setChargeUser(userInfo.getUserName());
						waterBill.setRemark("欠费"+MoneyUtil.getFormatStr2(waterBill.getAmount()-waterBill.getPaidAmt()));
						remark = "扣除水费"+MoneyUtil.getFormatStr2(account.getBalance())+"元，当前余额为零";
						accountService.payBill(account.getId(), waterBill.getPaidAmt(),  waterBill.getId(), remark, userInfo);
					}
				}
			}
		}
		
		waterRecordDao.save(waterBill);
		return true;
	}
	public void deleteRecord(String id, UserInfo userInfo) throws BizException {
		waterRecordDao.deleteById(id);
	}
	public WaterRecord getPreRecord(String houseSn) throws BizException {
		return waterRecordDao.findPreRecord(houseSn);
	}
	public Integer checkAllRecord(UserInfo userInfo) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", RecordState.UNCHECK.getValue());
		List<WaterRecord> list = waterRecordDao.findRecords(params);
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			if (checkRecord(list.get(i).getId(), userInfo)) {
				k++;
			}
		}
		return k;
	}
	public void importDeposit(List<WaterRecord> list, UserInfo userInfo) throws BizException {
		for (int i = 0; i < list.size(); i++) {
			WaterRecord record = list.get(i);
			record.setCreateDate(DateUtil.getCurrent());
			record.setCreateUser(userInfo.getUserName());
			record.setId(idFactoryService.generateId(Constants.WATER_RECORD_ID));
			record.setState(RecordState.UNCHECK.getValue());
			waterRecordDao.save(record);
			if (i%100 == 0) {
				waterRecordDao.flush();
			}
		}
	}

}
