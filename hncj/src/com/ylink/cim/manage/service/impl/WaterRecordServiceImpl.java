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
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.dao.WaterRecordDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.domain.WaterBill;
import com.ylink.cim.manage.domain.WaterRecord;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.manage.service.WaterRecordService;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;

@Component("waterRecordService")
public class WaterRecordServiceImpl implements WaterRecordService {
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

	@Override
	public void saveWaterRecord(WaterRecord waterRecord, UserInfo userInfo)
			throws BizException {
		String houseSn = waterRecord.getHouseSn();
		Assert.notNull(waterRecordDao.findById(HouseInfo.class, houseSn),
				"������Ϣ������");
		String recordMonth = waterRecord.getRecordMonth();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseSn", waterRecord.getHouseSn());
		params.put("recordMonth", recordMonth);
		params.put("branchNo", userInfo.getBranchNo());
		Assert.isEmpty(waterRecordDao.findPreRecords(params), "�·�"
				+ recordMonth + "�ļ�¼�Ѵ���");
		waterRecord.setId(idFactoryService
				.generateId(Constants.WATER_RECORD_ID));
		waterRecord.setCreateDate(DateUtil.getCurrent());
		waterRecord.setCreateUser(userInfo.getUserName());
		waterRecord.setState(RecordState.UNCHECK.getValue());
		waterRecord.setBranchNo(userInfo.getBranchNo());
		waterRecordDao.save(waterRecord);
	}

	@Override
	public boolean checkRecord(String id, UserInfo userInfo)
			throws BizException {
		WaterRecord waterRecord = waterRecordDao.findByIdWithLock(id);
		if (waterRecord.getNum() == null || waterRecord.getNum() <= 0) {
			return false;
		}
		waterRecord.setState(RecordState.CHECKED.getValue());
		waterRecord.setCheckDate(DateUtil.getCurrent());
		waterRecord.setCheckUser(userInfo.getUserName());
		waterRecordDao.save(waterRecord);
		// ���˵�
		WaterBill waterBill = new WaterBill();
		String price = ParaManager.getWaterPrice();
		Double amount = Double.parseDouble(price) * waterRecord.getNum();
		// ��ѯδ�ɷѵ��˵�
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
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(waterBill
				.getHouseSn());
		Assert.notNull(ownerInfo, "�Ҳ������ݱ��" + waterBill.getHouseSn()
				+ "��Ӧ��ҵ����Ϣ");
		waterBill.setOwnerName(ownerInfo.getOwnerName());
		if (Symbol.YES.equals(ownerInfo.getHasAcct())) {
			Account account = accountDao.findByIdWithLock(ownerInfo.getId());
			String remark = "";
			if (account != null) {
				if (account.getBalance() > 0) {
					// ����㹻
					if (account.getBalance() >= waterBill.getAmount()) {
						Double balance = account.getBalance()
								- waterBill.getAmount();
						waterBill.setPaidAmt(waterBill.getAmount());
						waterBill.setState(BillState.PAID.getValue());
						waterBill.setChargeDate(DateUtil.getCurrent());
						waterBill.setChargeUser(userInfo.getUserName());
						remark = "�۳�ˮ��"
								+ MoneyUtil
										.getFormatStr2(waterBill.getAmount())
								+ "Ԫ����ǰ���" + MoneyUtil.getFormatStr2(balance)
								+ "Ԫ";
						waterBill.setRemark("ˮ�Ѵ�Ԥ���˻��п۳�����ǰ���"
								+ MoneyUtil.getFormatStr2(balance) + "Ԫ");
						accountService.payBill(account.getId(),
								waterBill.getPaidAmt(), waterBill.getId(),
								remark, userInfo);
						// account.setBalance(account.getBalance() -
						// waterBill.getAmount());
						// accountService.addAccountDetail(account, amount,
						// AccountChangeType.WATER_FEE.getValue(),
						// InoutType.TYPE_OUT.getValue(), waterBill.getId(),
						// remark, userInfo);
					} else {
						// ����
						waterBill.setPaidAmt(account.getBalance());
						waterBill.setState(BillState.PART_PAID.getValue());
						waterBill.setChargeDate(DateUtil.getCurrent());
						waterBill.setChargeUser(userInfo.getUserName());
						waterBill.setRemark("Ƿ��"
								+ MoneyUtil.getFormatStr2(waterBill.getAmount()
										- waterBill.getPaidAmt()));
						remark = "�۳�ˮ��"
								+ MoneyUtil.getFormatStr2(account.getBalance())
								+ "Ԫ����ǰ���Ϊ��";
						accountService.payBill(account.getId(),
								waterBill.getPaidAmt(), waterBill.getId(),
								remark, userInfo);
					}
				}
			}
		}

		waterRecordDao.save(waterBill);
		return true;
	}

	@Override
	public void deleteRecord(String id, UserInfo userInfo) throws BizException {
		waterRecordDao.deleteById(id);
	}

	@Override
	public WaterRecord getPreRecord(String houseSn) throws BizException {
		return waterRecordDao.findPreRecord(houseSn);
	}

	@Override
	public Integer checkAllRecord(UserInfo userInfo) throws BizException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state", RecordState.UNCHECK.getValue());
		params.put("branchNo", userInfo.getBranchNo());
		List<WaterRecord> list = waterRecordDao.findRecords(params);
		int k = 0;
		for (int i = 0; i < list.size(); i++) {
			if (checkRecord(list.get(i).getId(), userInfo)) {
				k++;
			}
		}
		return k;
	}

	@Override
	public void importDeposit(List<WaterRecord> list, UserInfo userInfo)
			throws BizException {
		for (int i = 0; i < list.size(); i++) {
			WaterRecord record = list.get(i);
			record.setCreateDate(DateUtil.getCurrent());
			record.setCreateUser(userInfo.getUserName());
			record.setId(idFactoryService.generateId(Constants.WATER_RECORD_ID));
			record.setState(RecordState.UNCHECK.getValue());
			waterRecordDao.save(record);
			if (i % 100 == 0) {
				waterRecordDao.flush();
			}
		}
	}

}
