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
import com.ylink.cim.common.type.IcCardType;
import com.ylink.cim.common.type.InputTradeType;
import com.ylink.cim.common.type.OutputTradeType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.AdrentBillDao;
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.dao.DecorateServiceBillDao;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.dao.ElecBillDao;
import com.ylink.cim.manage.dao.GeneralBillDao;
import com.ylink.cim.manage.dao.HouseRentBillDao;
import com.ylink.cim.manage.dao.IcDepositDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.dao.ParkingBillDao;
import com.ylink.cim.manage.dao.WaterBillDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.AdrentBill;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.DecorateServiceBill;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.domain.ElecBill;
import com.ylink.cim.manage.domain.GeneralBill;
import com.ylink.cim.manage.domain.HouseRentBill;
import com.ylink.cim.manage.domain.IcDeposit;
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
	private GeneralBillDao generalBillDao;
	@Autowired
	private IcDepositDao icDepositDao;
	@Autowired
	private AdrentBillDao adRentBillDao;
	@Autowired
	private BillTrackService trackBillService;
	@Autowired
	private ElecBillDao elecBillDao;
	@Autowired
	private HouseRentBillDao houseRentBillDao;
	@Override
	public void chargeAdRent(String id, UserInfo userInfo) throws BizException {
		AdrentBill bill = adRentBillDao.findByIdWithLock(id);
		bill.setState(BillState.PAID.getValue());
		bill.setChargeUser(userInfo.getUserName());
		bill.setChargeDate(DateUtil.getCurrent());
		adRentBillDao.update(bill);
		accountJournalService.add(TradeType.AD_RENT.getValue(),
				bill.getPaidAmt(), id, "收" + bill.getPayerName() + "广告位租赁费",
				userInfo);
	}

	@Override
	public void chargeCommonFee(String id, UserInfo userInfo)
			throws BizException {
		CommonServiceBill bill = commonServiceBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), bill.getState(), "账单不是待缴费状态");
		bill.setState(BillState.PAID.getValue());
		bill.setChargeDate(DateUtil.getCurrent());
		bill.setChargeUser(userInfo.getUserName());
		parkingBillDao.save(bill);
		accountJournalService.add(InputTradeType.SERVICE.getValue(),
				bill.getTotalAmount(), id, "收" + bill.getHouseSn() + "业主"
						+ bill.getOwnerName() + "物业费(含公共照明费)", userInfo);
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(bill.getHouseSn(), userInfo.getBranchNo());
		if (bill.getTotalAmount() == bill.getServiceAmount()
				+ bill.getLightAmount()) {
			// houseInfo = parkingBillDao.findById(bill.getHouseSn());

			if (ownerInfo != null
					&& CheckinState.CHECKEDIN.getValue().equals(
							ownerInfo.getCheckinState())) {
				ownerInfo.setCheckinState(CheckinState.CHECKEDIN.getValue());
				ownerInfoDao.update(ownerInfo);
			}
		}
		// 废弃以前的提醒
		trackBillService.expirePreTracks(bill.getHouseSn(),
				BillType.SERVICE.getValue(), bill.getBranchNo());
		// 加入新提醒
		trackBillService.addBillTrack(bill.getHouseSn(),
				BillType.SERVICE.getValue(), bill.getId(), bill.getEndDate(),
				ownerInfo.getOwnerName(), ownerInfo.getMobile(),
				userInfo.getBranchNo());
	}

	@Override
	public void chargeDecorateFee(String id, UserInfo userInfo)
			throws BizException {
		DecorateServiceBill decorateServiceBill = decorateServiceBillDao
				.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(),
				decorateServiceBill.getState(), "账单不是待缴费状态");
		decorateServiceBill.setState(BillState.PAID.getValue());
		decorateServiceBill.setChargeDate(DateUtil.getCurrent());
		decorateServiceBill.setChargeUser(userInfo.getUserName());
		parkingBillDao.update(decorateServiceBill);
		// 添加
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(decorateServiceBill
				.getHouseSn(), userInfo.getBranchNo());
		ownerInfo.setCheckinState(CheckinState.CHECKEDIN.getValue());
		if (StringUtils.isEmpty(ownerInfo.getCheckinDate())) {
			ownerInfo.setCheckinDate(DateUtil.getCurrentDate());
		}
		ownerInfoDao.update(ownerInfo);
		accountJournalService.add(InputTradeType.DECORATE.getValue(),
				decorateServiceBill.getAmount(), id,
				"收" + decorateServiceBill.getHouseSn() + "业主"
						+ decorateServiceBill.getOwnerName() + "装修服务费",
				userInfo);
	}

	@Override
	public void chargeDepositFee(String id, UserInfo userInfo)
			throws BizException {
		DepositBill bill = depositBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), bill.getState(), "状态已变更");
		bill.setDepositDate(DateUtil.getCurrent());
		bill.setDepositUser(userInfo.getUserName());
		bill.setState(BillState.PAID.getValue());
		depositBillDao.update(bill);
		accountJournalService.add(InputTradeType.SECURITY.getValue(),
				bill.getAmount(), id,
				"收" + bill.getHouseSn() + "业主" + bill.getPayerName() + "押金",
				userInfo);
	}

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
				"收" + bill.getPayerName()
						+ TradeType.valueOf(bill.getTradeType()).getName(),
				userInfo);
	}

	@Override
	public void chargeIcDeposit(String id, UserInfo userInfo)
			throws BizException {
		IcDeposit bill = icDepositDao.findByIdWithLock(id);
		bill.setState(BillState.PAID.getValue());
		bill.setChargeUser(userInfo.getUserName());
		bill.setChargeDate(DateUtil.getCurrent());
		icDepositDao.update(bill);
		accountJournalService.add(TradeType.IC_DEPOSIT.getValue(),
				bill.getAmount(), bill.getId(), "收" + bill.getPayerName()
						+ IcCardType.valueOf(bill.getCardType()).getName()
						+ "充值款", userInfo);
	}

	@Override
	public void chargeParkingFee(String id, UserInfo userInfo)
			throws BizException {
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
		OwnerInfo ownerInfo = ownerInfoDao.findById(bill.getOwnerId());
		String ownerName = "";
		String ownerCel = "";
		if (ownerInfo != null) {
			ownerName = ownerInfo.getOwnerName();
			ownerCel = ownerInfo.getMobile();
		}
		accountJournalService.add(InputTradeType.PARKING.getValue(),
				bill.getAmount(), id, "收" + houseSn + "" + ownerName + "停车费",
				userInfo);
		// 废弃以前的提醒
		trackBillService.expirePreTracks(bill.getHouseSn(),
				BillType.PARKING.getValue(), bill.getBranchNo());
		// 加入新提醒

		trackBillService.addBillTrack(bill.getHouseSn(),
				BillType.PARKING.getValue(), bill.getId(), bill.getEndDate(),
				ownerName, ownerCel, userInfo.getBranchNo());
	}

	@Override
	public void chargeWaterFee(String id, UserInfo userInfo)
			throws BizException {
		WaterBill bill = waterBillDao.findByIdWithLock(id);
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(bill.getHouseSn(), bill.getBranchNo());
		String remark = "";
		Double acctPayAmt = 0d;
		boolean flag = false;
		if (Symbol.YES.equals(ownerInfo.getHasAcct())) {
			Account account = accountDao.findByIdWithLock(ownerInfo.getId());
			if (account.getBalance() > 0) {
				if (account.getBalance() > bill.getAmount()) {
					acctPayAmt = bill.getAmount();
					Double balance = account.getBalance() - bill.getAmount();
					remark = "费用从预存账户中扣除，当前余额"
							+ MoneyUtil.getFormatStr2(balance) + "元";
				} else {
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
		bill.setBranchNo(userInfo.getBranchNo());
		waterBillDao.save(bill);
		if (flag) {
			accountService.payBill(ownerInfo.getId(), acctPayAmt, bill.getId(),
					"扣除水费" + MoneyUtil.getFormatStr2(acctPayAmt) + "元",
					userInfo);
		}
		accountJournalService.add(InputTradeType.WATER.getValue(),
				bill.getAmount(), id, "收" + bill.getHouseSn() + "业主"
						+ ownerInfo.getOwnerName() + "水费", userInfo);
	}

	@Override
	public void deleteBill(Class clazz, String id, UserInfo userInfo)
			throws BizException {
		Object object = generalBillDao.findById(clazz, id);
		if (object != null) {
			generalBillDao.delete(object);
		}
	}

	@Override
	public void refund(String id, UserInfo userInfo) throws BizException {
		DepositBill depositBill = depositBillDao.findByIdWithLock(id);
		Assert.equals(BillState.PAID.getValue(), depositBill.getState(),
				"状态已变更");
		depositBill.setState(BillState.REFUND.getValue());
		depositBill.setRefundDate(DateUtil.getCurrent());
		depositBill.setRefundUser(userInfo.getUserName());
		depositBillDao.update(depositBill);
		accountJournalService.deduct(OutputTradeType.REFUND.getValue(),
				depositBill.getAmount(), id, "退" + depositBill.getHouseSn()
						+ "业主" + depositBill.getPayerName() + "押金", userInfo);
	}

	@Override
	public void saveAdRentBill(AdrentBill adRent, UserInfo sessionUser)
			throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		adRent.setId(id);
		adRent.setState(BillState.UNPAY.getValue());
		adRent.setBranchNo(sessionUser.getBranchNo());
		adRent.setCreateDate(DateUtil.getCurrent());
		adRent.setCreateUser(sessionUser.getUserName());
		adRentBillDao.save(adRent);
	}

	@Override
	public void saveDecorateServiceBill(DecorateServiceBill bill,
			UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		bill.setId(id);
		bill.setCreateDate(DateUtil.getCurrent());
		bill.setCreateUser(userInfo.getUserName());
		bill.setState(BillState.UNPAY.getValue());
		bill.setBranchNo(userInfo.getBranchNo());
		decorateServiceBillDao.save(bill);
		if (StringUtils.isNotBlank(bill.getCsBillId())) {
			CommonServiceBill csBill = parkingBillDao.findById(bill
					.getCsBillId());
			CommonServiceBill newCsBill = new CommonServiceBill();
			try {
				BeanUtils.copyProperties(newCsBill, csBill);
				newCsBill.setId(null);
				newCsBill.setChargeDate(null);
				newCsBill.setChargeUser(null);
				newCsBill.setRemark("补缴20%已减免物业费");
				newCsBill.setTotalAmount(csBill.getTotalAmount() * 0.25);
				newCsBill.setBranchNo(userInfo.getBranchNo());
				saveServiceBill(newCsBill, userInfo);
			} catch (Exception e) {
				throw new BizException("保存补缴物业费单据失败!");
			}
		}
	}

	@Override
	public void saveDepositBill(DepositBill depositBill, UserInfo userInfo)
			throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		depositBill.setId(id);
		depositBill.setState(BillState.UNPAY.getValue());
		depositBill.setBranchNo(userInfo.getBranchNo());
		depositBillDao.save(depositBill);
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

	@Override
	public void saveIcDeposit(IcDeposit icDeposit, UserInfo userInfo)
			throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		icDeposit.setId(id);
		icDeposit.setState(BillState.UNPAY.getValue());
		icDeposit.setBranchNo(userInfo.getBranchNo());
		icDeposit.setCreateDate(DateUtil.getCurrent());
		icDeposit.setCreateUser(userInfo.getUserName());
		generalBillDao.save(icDeposit);
	}

	@Override
	public void saveParkingBill(ParkingBill bill, UserInfo userInfo)
			throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		bill.setId(id);
		bill.setCreateDate(DateUtil.getCurrent());
		bill.setCreateUser(userInfo.getUserName());
		bill.setState(BillState.UNPAY.getValue());
		bill.setBranchNo(userInfo.getBranchNo());
		parkingBillDao.save(bill);
	}

	@Override
	public void saveServiceBill(CommonServiceBill bill, UserInfo userInfo)
			throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		bill.setId(id);
		bill.setCreateDate(DateUtil.getCurrent());
		bill.setCreateUser(userInfo.getUserName());
		bill.setState(BillState.UNPAY.getValue());
		bill.setBranchNo(userInfo.getBranchNo());
		parkingBillDao.save(bill);
	}

	@Override
	public void chargeElecFee(String id, UserInfo userInfo) throws BizException {
		ElecBill bill = elecBillDao.findByIdWithLock(id);
		bill.setState(BillState.PAID.getValue());
		bill.setChargeUser(userInfo.getUserName());
		bill.setChargeDate(DateUtil.getCurrent());
		elecBillDao.update(bill);
	}

	@Override
	public void saveHouseRentBill(HouseRentBill bill, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		bill.setId(id);
		bill.setCreateDate(DateUtil.getCurrent());
		bill.setCreateUser(userInfo.getUserName());
		bill.setState(BillState.UNPAY.getValue());
		bill.setBranchNo(userInfo.getBranchNo());
		houseRentBillDao.save(bill);
	}
	@Override
	public void chargeHouseRent(String id, UserInfo userInfo) throws BizException {
		HouseRentBill bill = houseRentBillDao.findByIdWithLock(id);
		bill.setState(BillState.PAID.getValue());
		bill.setChargeUser(userInfo.getUserName());
		bill.setChargeDate(DateUtil.getCurrent());
		houseRentBillDao.update(bill);
	}

}
