package com.ylink.cim.manage.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.CheckinState;
import com.ylink.cim.common.type.BillType;
import com.ylink.cim.common.type.InputTradeType;
import com.ylink.cim.common.type.OutputTradeType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.dao.DecorateServiceBillDao;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.dao.ParkingBillDao;
import com.ylink.cim.manage.dao.WaterBillDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.DecorateServiceBill;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.domain.ParkingBill;
import com.ylink.cim.manage.domain.WaterBill;
import com.ylink.cim.manage.service.AccountJournalService;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.manage.service.BillService;
import com.ylink.cim.manage.service.BillTrackService;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.etc.Symbol;
import flink.util.DateUtil;
@Component("billService")
public class BillServiceImpl implements BillService {
	@Autowired
	private WaterBillDao waterBillDao;
	@Autowired
	private ParkingBillDao parkingBillDao;
	@Autowired
	private DepositBillDao depositBillDao;
	@Autowired
	private CommonServiceBillDao commonServiceBillDao;
	@Autowired
	private DecorateServiceBillDao decorateServiceBillDao;
	@Autowired
	private IdFactoryService idFactoryService;
	@Autowired
	private OwnerInfoDao ownerInfoDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountJournalService accountJournalService;
	@Autowired
	private BillTrackService billTrackService;
	public void chargeWaterFee(String id, UserInfo userInfo) throws BizException {
		WaterBill bill = waterBillDao.findByIdWithLock(id);
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(bill.getHouseSn());
		String remark = "";
		Double acctPayAmt = 0d;
		boolean flag = false;
		if (Symbol.YES.equals(ownerInfo.getHasAcct())) {
			Account account = accountDao.findByIdWithLock(ownerInfo.getId());
			if(account.getBalance() > 0){
				if (account.getBalance() > bill.getAmount()) {
					acctPayAmt = bill.getAmount();
					Double balance = account.getBalance()-bill.getAmount();
					remark = "费用从预存账户中扣除，当前余额"+MoneyUtil.getFormatStr2(balance)+"元";
				}else {
					acctPayAmt = account.getBalance();
				}
				flag = true;
			}
		}
		bill.setPaidAmt(bill.getAmount());
		bill.setState(BillState.PAID.getValue());
		bill.setChargeDate(DateUtil.getCurrent());
		bill.setChargeUser(userInfo.getUserName());
		bill.setRemark(remark);
		waterBillDao.save(bill);
		if (flag) {
			accountService.payBill(ownerInfo.getId(), acctPayAmt, bill.getId(), "扣除水费"+MoneyUtil.getFormatStr2(acctPayAmt)+"元", userInfo);
		}
		accountJournalService.add(InputTradeType.WATER.getValue(), bill.getAmount(), id, "收"+bill.getHouseSn()+"业主"+ownerInfo.getOwnerName()+"水费", userInfo);
		// 废弃以前的提醒,不创建新提醒
		billTrackService.expirePreTracks(bill.getHouseSn(), BillType.WATER.getValue(), null);
	}
	
	public void saveParkingBill(ParkingBill parkingBill, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		parkingBill.setId(id);
		parkingBill.setCreateDate(DateUtil.getCurrent());
		parkingBill.setCreateUser(userInfo.getUserName());
		parkingBill.setState(BillState.UNPAY.getValue());
		parkingBillDao.save(parkingBill);
	}
	public void saveServiceBill(CommonServiceBill commonServiceBill, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		commonServiceBill.setId(id);
		commonServiceBill.setCreateDate(DateUtil.getCurrent());
		commonServiceBill.setCreateUser(userInfo.getUserName());
		commonServiceBill.setState(BillState.UNPAY.getValue());
		commonServiceBillDao.save(commonServiceBill);
	}
	public void chargeCommonFee(String id, UserInfo userInfo) throws BizException {
		CommonServiceBill bill = commonServiceBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), bill.getState(), "账单不是待缴费状态");
		bill.setState(BillState.PAID.getValue());
		bill.setChargeDate(DateUtil.getCurrent());
		bill.setChargeUser(userInfo.getUserName());
		commonServiceBillDao.save(bill);
		accountJournalService.add(InputTradeType.SERVICE.getValue(), bill.getTotalAmount(), id, "收"+bill.getHouseSn()+"业主"+bill.getOwnerName()+"物业费(含公共能源费)", userInfo);
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(bill.getHouseSn());
		if (bill.getTotalAmount() == bill.getServiceAmount()+bill.getLightAmount()) {
			// houseInfo = commonServiceBillDao.findById(commonServiceBill.getHouseSn());
			if (ownerInfo != null && CheckinState.CHECKEDIN.getValue().equals(ownerInfo.getCheckinState())) {
				ownerInfo.setCheckinState(CheckinState.CHECKEDIN.getValue());
				ownerInfoDao.update(ownerInfo);
			}
		}
		String mobile = "";
		if (ownerInfo != null) {
			mobile = ownerInfo.getMobile();
		}
		// 废弃以前的提醒
		billTrackService.expirePreTracks(bill.getHouseSn(), BillType.SERVICE.getValue(), null);
		//添加提醒
		billTrackService.addBillTrack(bill.getHouseSn(), BillType.SERVICE.getValue(), bill.getId(), bill.getEndDate(), bill.getOwnerName(), mobile, "", bill.getTotalAmount());
	}
	public void chargeDecorateFee(String id, UserInfo userInfo) throws BizException {
		DecorateServiceBill decorateServiceBill = decorateServiceBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), decorateServiceBill.getState(), "账单不是待缴费状态");
		decorateServiceBill.setState(BillState.PAID.getValue());
		decorateServiceBill.setChargeDate(DateUtil.getCurrent());
		decorateServiceBill.setChargeUser(userInfo.getUserName());
		commonServiceBillDao.update(decorateServiceBill);
		//添加
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(decorateServiceBill.getHouseSn());
		ownerInfo.setCheckinState(CheckinState.CHECKEDIN.getValue());
		if (StringUtils.isEmpty(ownerInfo.getCheckinDate())) {
			ownerInfo.setCheckinDate(DateUtil.getCurrentDate());
		}
		ownerInfoDao.update(ownerInfo);
		accountJournalService.add(InputTradeType.DECORATE.getValue(), decorateServiceBill.getAmount(), id, "收"+decorateServiceBill.getHouseSn()+"业主"+decorateServiceBill.getOwnerName()+"装修服务费", userInfo);
	}
	public void chargeParkingFee(String id, UserInfo userInfo) throws BizException {
		ParkingBill bill = parkingBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), bill.getState(), "账单不是待缴费状态");
		bill.setState(BillState.PAID.getValue());
		bill.setChargeDate(DateUtil.getCurrent());
		bill.setChargeUser(userInfo.getUserName());
		parkingBillDao.update(bill);
		String houseSn = bill.getHouseSn();
		if (houseSn == null) {
			houseSn = "";
		}
		accountJournalService.add(InputTradeType.PARKING.getValue(), bill.getAmount(), id, "收"+houseSn+""+bill.getOwnerName()+"停车费", userInfo);
		//废弃以前的提醒
		billTrackService.expirePreTracks(houseSn, BillType.PARKING.getValue(), null);
		//添加提醒
		billTrackService.expirePreTracks(houseSn, BillType.PARKING.getValue(), null);
		billTrackService.addBillTrack(bill.getHouseSn(), BillType.PARKING.getValue(), bill.getId(), bill.getEndDate(), bill.getOwnerName(), bill.getMobile(), "", bill.getAmount());
	}

	public void deleteBill(Class clazz, String id, UserInfo userInfo) throws BizException {
		Object object = waterBillDao.findById(clazz, id);
		if (object != null) {
			waterBillDao.delete(object);
		}
	}

	public void chargeDepositFee(String id, UserInfo userInfo) throws BizException {
		DepositBill depositBill = depositBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), depositBill.getState(), "状态已变更");
		depositBill.setDepositDate(DateUtil.getCurrent());
		depositBill.setDepositUser(userInfo.getUserName());
		depositBill.setState(BillState.PAID.getValue());
		depositBillDao.update(depositBill);
		accountJournalService.add(InputTradeType.SECURITY.getValue(), depositBill.getAmount(), id, "收"+depositBill.getHouseSn()+"业主"+depositBill.getPayerName()+"押金", userInfo);
	}

	public void refund(String id, UserInfo userInfo) throws BizException{
		DepositBill depositBill = depositBillDao.findByIdWithLock(id);
		Assert.equals(BillState.PAID.getValue(), depositBill.getState(), "状态已变更");
		depositBill.setState(BillState.REFUND.getValue());
		depositBill.setRefundDate(DateUtil.getCurrent());
		depositBill.setRefundUser(userInfo.getUserName());
		depositBillDao.update(depositBill);
		accountJournalService.deduct(OutputTradeType.REFUND.getValue(), depositBill.getAmount(), id, "退"+depositBill.getHouseSn()+"业主"+depositBill.getPayerName()+"押金", userInfo);
	}

	public void saveDepositBill(DepositBill depositBill, UserInfo userInfo) throws BizException{
		String id = idFactoryService.generateId(Constants.BILL_ID);
		depositBill.setId(id);
		depositBill.setState(BillState.UNPAY.getValue());
		depositBillDao.save(depositBill);
	}

	public void saveDecorateServiceBill(DecorateServiceBill bill, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		bill.setId(id);
		bill.setCreateDate(DateUtil.getCurrent());
		bill.setCreateUser(userInfo.getUserName());
		bill.setState(BillState.UNPAY.getValue());
		decorateServiceBillDao.save(bill);
		if (StringUtils.isNotBlank(bill.getCsBillId())) {
			CommonServiceBill csBill = commonServiceBillDao.findById(bill.getCsBillId());
			CommonServiceBill newCsBill = new CommonServiceBill();
			try {
				BeanUtils.copyProperties(newCsBill, csBill);
				newCsBill.setId(null);
				newCsBill.setChargeDate(null);
				newCsBill.setChargeUser(null);
				newCsBill.setRemark("补缴20%已减免物业费");
				newCsBill.setTotalAmount(csBill.getTotalAmount()*0.25);
				saveServiceBill(newCsBill, userInfo);
			} catch (Exception e) {
				throw new BizException("保存补缴物业费单据失败!");
			}
		}
	}

}
