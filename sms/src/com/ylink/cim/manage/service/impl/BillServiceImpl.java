package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.GeneralBillDao;
import com.ylink.cim.manage.domain.GeneralBill;
import com.ylink.cim.manage.service.AccountJournalService;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.manage.service.BillService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("billService")
public class BillServiceImpl implements BillService {
	@Autowired
	private IdFactoryService idFactoryService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountJournalService accountJournalService;
	@Autowired
	private GeneralBillDao generalBillDao;


	@Override
	public void chargeGeneralBill(String id, UserInfo userInfo)
			throws BizException {
		GeneralBill bill = generalBillDao.findByIdWithLock(id);
		bill.setState(BillState.PAID.getValue());
		bill.setChargeUser(userInfo.getUserName());
		bill.setChargeDate(DateUtil.getCurrent());
		generalBillDao.update(bill);
		accountJournalService.add(
				bill.getTradeType(),
				bill.getPaidAmt(),
				id,
				"สี" + bill.getPayerName()
						+ TradeType.valueOf(bill.getTradeType()).getName(),
				userInfo);
	}


	@Override
	public void deleteBill(Class clazz, String id, UserInfo userInfo) throws BizException {
		Object object = generalBillDao.findById(clazz, id);
		if (object != null) {
			generalBillDao.delete(object);
		}
	}
	@Override
	public void saveGeneralBill(GeneralBill generalBill, UserInfo userInfo)
			throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		generalBill.setId(id);
		generalBill.setState(BillState.UNPAY.getValue());
		generalBill.setBranchNo(userInfo.getBranchNo());
		generalBill.setCreateDate(DateUtil.getCurrent());
		generalBill.setCreateUser(userInfo.getUserName());
		generalBillDao.save(generalBill);
	}

}
