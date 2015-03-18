package com.ylink.cim.manage.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.state.CheckinState;
import com.ylink.cim.common.type.IcCardType;
import com.ylink.cim.common.type.InputTradeType;
import com.ylink.cim.common.type.OutputTradeType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.common.util.MoneyUtil;
import com.ylink.cim.manage.dao.AccountDao;
import com.ylink.cim.manage.dao.AdRentBillDao;
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.dao.DecorateServiceBillDao;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.dao.GeneralBillDao;
import com.ylink.cim.manage.dao.IcDepositDao;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.dao.ParkingBillDao;
import com.ylink.cim.manage.dao.WaterBillDao;
import com.ylink.cim.manage.domain.Account;
import com.ylink.cim.manage.domain.AdRent;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.DecorateServiceBill;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.domain.GeneralBill;
import com.ylink.cim.manage.domain.IcDeposit;
import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.manage.domain.ParkingBill;
import com.ylink.cim.manage.domain.WaterBill;
import com.ylink.cim.manage.service.AccountJournalService;
import com.ylink.cim.manage.service.AccountService;
import com.ylink.cim.manage.service.BillService;
import com.ylink.cim.user.domain.UserInfo;

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
	private AdRentBillDao adRentBillDao;
	public void chargeAdRent(String id, UserInfo userInfo) throws BizException {
		AdRent bill = adRentBillDao.findByIdWithLock(id);
		bill.setState(BillState.PAID.getValue());
		bill.setChargeUser(userInfo.getUserName());
		bill.setChargeDate(DateUtil.getCurrent());
		adRentBillDao.update(bill);
		accountJournalService.add(TradeType.AD_RENT.getValue(), bill.getAmount(), id, "��"+bill.getPayerName()+"���λ���޷�", userInfo);
	}
	
	public void chargeCommonFee(String id, UserInfo userInfo) throws BizException {
		CommonServiceBill commonServiceBill = commonServiceBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), commonServiceBill.getState(), "�˵����Ǵ��ɷ�״̬");
		commonServiceBill.setState(BillState.PAID.getValue());
		commonServiceBill.setChargeDate(DateUtil.getCurrent());
		commonServiceBill.setChargeUser(userInfo.getUserName());
		commonServiceBillDao.save(commonServiceBill);
		accountJournalService.add(InputTradeType.SERVICE.getValue(), commonServiceBill.getTotalAmount(), id, "��"+commonServiceBill.getHouseSn()+"ҵ��"+commonServiceBill.getOwnerName()+"��ҵ��(������������)", userInfo);
		if (commonServiceBill.getTotalAmount() == commonServiceBill.getServiceAmount()+commonServiceBill.getLightAmount()) {
			// houseInfo = commonServiceBillDao.findById(commonServiceBill.getHouseSn());
			OwnerInfo ownerInfo	= ownerInfoDao.getNormalOwner(commonServiceBill.getHouseSn());
			if (ownerInfo != null && CheckinState.CHECKEDIN.getValue().equals(ownerInfo.getCheckinState())) {
				ownerInfo.setCheckinState(CheckinState.CHECKEDIN.getValue());
				ownerInfoDao.update(ownerInfo);
			}
		}
	}
	public void chargeDecorateFee(String id, UserInfo userInfo) throws BizException {
		DecorateServiceBill decorateServiceBill = decorateServiceBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), decorateServiceBill.getState(), "�˵����Ǵ��ɷ�״̬");
		decorateServiceBill.setState(BillState.PAID.getValue());
		decorateServiceBill.setChargeDate(DateUtil.getCurrent());
		decorateServiceBill.setChargeUser(userInfo.getUserName());
		commonServiceBillDao.update(decorateServiceBill);
		//���
		OwnerInfo ownerInfo = ownerInfoDao.getNormalOwner(decorateServiceBill.getHouseSn());
		ownerInfo.setCheckinState(CheckinState.CHECKEDIN.getValue());
		if (StringUtils.isEmpty(ownerInfo.getCheckinDate())) {
			ownerInfo.setCheckinDate(DateUtil.getCurrentDate());
		}
		ownerInfoDao.update(ownerInfo);
		accountJournalService.add(InputTradeType.DECORATE.getValue(), decorateServiceBill.getAmount(), id, "��"+decorateServiceBill.getHouseSn()+"ҵ��"+decorateServiceBill.getOwnerName()+"װ�޷����", userInfo);
	}
	public void chargeDepositFee(String id, UserInfo userInfo) throws BizException {
		DepositBill depositBill = depositBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), depositBill.getState(), "״̬�ѱ��");
		depositBill.setDepositDate(DateUtil.getCurrent());
		depositBill.setDepositUser(userInfo.getUserName());
		depositBill.setState(BillState.PAID.getValue());
		depositBillDao.update(depositBill);
		accountJournalService.add(InputTradeType.SECURITY.getValue(), depositBill.getAmount(), id, "��"+depositBill.getHouseSn()+"ҵ��"+depositBill.getPayerName()+"Ѻ��", userInfo);
	}
	public void chargeGeneralBill(String id, UserInfo userInfo) throws BizException {
		GeneralBill bill = generalBillDao.findByIdWithLock(id);
		bill.setState(BillState.PAID.getValue());
		bill.setChargeUser(userInfo.getUserName());
		bill.setChargeDate(DateUtil.getCurrent());
		generalBillDao.update(bill);
		accountJournalService.add(bill.getTradeType(), bill.getPaidAmt(), id, "��"+bill.getPayerName()+TradeType.valueOf(bill.getTradeType()).getName(), userInfo);
	}
	public void chargeIcDeposit(String id, UserInfo userInfo) throws BizException {
		IcDeposit bill = icDepositDao.findByIdWithLock(id);
		bill.setState(BillState.PAID.getValue());
		bill.setChargeUser(userInfo.getUserName());
		bill.setChargeDate(DateUtil.getCurrent());
		icDepositDao.update(bill);
		accountJournalService.add(TradeType.IC_DEPOSIT.getValue(), bill.getAmount(), bill.getId(), "��"+bill.getPayerName()+IcCardType.valueOf(bill.getCardType()).getName()+"��ֵ��", userInfo);
	}

	public void chargeParkingFee(String id, UserInfo userInfo) throws BizException {
		ParkingBill bill = parkingBillDao.findByIdWithLock(id);
		Assert.equals(BillState.UNPAY.getValue(), bill.getState(), "�˵����Ǵ��ɷ�״̬");
		bill.setState(BillState.PAID.getValue());
		bill.setChargeDate(DateUtil.getCurrent());
		bill.setChargeUser(userInfo.getUserName());
		parkingBillDao.update(bill);
		String houseSn = bill.getHouseSn();
		if (houseSn == null) {
			houseSn = "";
		}
		accountJournalService.add(InputTradeType.PARKING.getValue(), bill.getAmount(), id, "��"+houseSn+""+bill.getOwnerName()+"ͣ����", userInfo);
	}

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
					remark = "���ô�Ԥ���˻��п۳�����ǰ���"+MoneyUtil.getFormatStr2(balance)+"Ԫ";
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
		bill.setBranchNo(userInfo.getBranchNo());
		waterBillDao.save(bill);
		if (flag) {
			accountService.payBill(ownerInfo.getId(), acctPayAmt, bill.getId(), "�۳�ˮ��"+MoneyUtil.getFormatStr2(acctPayAmt)+"Ԫ", userInfo);
		}
		accountJournalService.add(InputTradeType.WATER.getValue(), bill.getAmount(), id, "��"+bill.getHouseSn()+"ҵ��"+ownerInfo.getOwnerName()+"ˮ��", userInfo);
	}

	public void deleteBill(Class clazz, String id, UserInfo userInfo) throws BizException {
		Object object = waterBillDao.findById(clazz, id);
		if (object != null) {
			waterBillDao.delete(object);
		}
	}

	public void refund(String id, UserInfo userInfo) throws BizException{
		DepositBill depositBill = depositBillDao.findByIdWithLock(id);
		Assert.equals(BillState.PAID.getValue(), depositBill.getState(), "״̬�ѱ��");
		depositBill.setState(BillState.REFUND.getValue());
		depositBill.setRefundDate(DateUtil.getCurrent());
		depositBill.setRefundUser(userInfo.getUserName());
		depositBillDao.update(depositBill);
		accountJournalService.deduct(OutputTradeType.REFUND.getValue(), depositBill.getAmount(), id, "��"+depositBill.getHouseSn()+"ҵ��"+depositBill.getPayerName()+"Ѻ��", userInfo);
	}

	public void saveAdRentBill(AdRent adRent, UserInfo sessionUser) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		adRent.setId(id);
		adRent.setState(BillState.UNPAY.getValue());
		adRent.setBranchNo(sessionUser.getBranchNo());
		adRent.setCreateDate(DateUtil.getCurrent());
		adRent.setCreateUser(sessionUser.getUserName());
		adRentBillDao.save(adRent);
	}

	public void saveDecorateServiceBill(DecorateServiceBill bill, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		bill.setId(id);
		bill.setCreateDate(DateUtil.getCurrent());
		bill.setCreateUser(userInfo.getUserName());
		bill.setState(BillState.UNPAY.getValue());
		bill.setBranchNo(userInfo.getBranchNo());
		decorateServiceBillDao.save(bill);
		if (StringUtils.isNotBlank(bill.getCsBillId())) {
			CommonServiceBill csBill = commonServiceBillDao.findById(bill.getCsBillId());
			CommonServiceBill newCsBill = new CommonServiceBill();
			try {
				BeanUtils.copyProperties(newCsBill, csBill);
				newCsBill.setId(null);
				newCsBill.setChargeDate(null);
				newCsBill.setChargeUser(null);
				newCsBill.setRemark("����20%�Ѽ�����ҵ��");
				newCsBill.setTotalAmount(csBill.getTotalAmount()*0.25);
				newCsBill.setBranchNo(userInfo.getBranchNo());
				saveServiceBill(newCsBill, userInfo);
			} catch (Exception e) {
				throw new BizException("���油����ҵ�ѵ���ʧ��!");
			}
		}
	}


	public void saveDepositBill(DepositBill depositBill, UserInfo userInfo) throws BizException{
		String id = idFactoryService.generateId(Constants.BILL_ID);
		depositBill.setId(id);
		depositBill.setState(BillState.UNPAY.getValue());
		depositBill.setBranchNo(userInfo.getBranchNo());
		depositBillDao.save(depositBill);
	}

	public void saveGeneralBill(GeneralBill generalBill, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		generalBill.setId(id);
		generalBill.setState(BillState.UNPAY.getValue());
		generalBill.setBranchNo(userInfo.getBranchNo());
		generalBill.setCreateDate(DateUtil.getCurrent());
		generalBill.setCreateUser(userInfo.getUserName());
		generalBillDao.save(generalBill);
	}

	public void saveIcDeposit(IcDeposit icDeposit, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		icDeposit.setId(id);
		icDeposit.setState(BillState.UNPAY.getValue());
		icDeposit.setBranchNo(userInfo.getBranchName());
		icDeposit.setCreateDate(DateUtil.getCurrentDate());
		icDeposit.setCreateUser(userInfo.getUserName());
		generalBillDao.save(icDeposit);
	}
	
	public void saveParkingBill(ParkingBill parkingBill, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		parkingBill.setId(id);
		parkingBill.setCreateDate(DateUtil.getCurrent());
		parkingBill.setCreateUser(userInfo.getUserName());
		parkingBill.setState(BillState.UNPAY.getValue());
		parkingBill.setBranchNo(userInfo.getBranchNo());
		parkingBillDao.save(parkingBill);
	}


	public void saveServiceBill(CommonServiceBill commonServiceBill, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.BILL_ID);
		commonServiceBill.setId(id);
		commonServiceBill.setCreateDate(DateUtil.getCurrent());
		commonServiceBill.setCreateUser(userInfo.getUserName());
		commonServiceBill.setState(BillState.UNPAY.getValue());
		commonServiceBill.setBranchNo(userInfo.getBranchNo());
		commonServiceBillDao.save(commonServiceBill);
	}
}
