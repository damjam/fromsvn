package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.AdrentBill;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.DecorateServiceBill;
import com.ylink.cim.manage.domain.DepositBill;
import com.ylink.cim.manage.domain.GeneralBill;
import com.ylink.cim.manage.domain.IcDeposit;
import com.ylink.cim.manage.domain.ParkingBill;

import flink.etc.BizException;

public interface BillService {

	void chargeAdRent(String id, UserInfo userInfo) throws BizException;

	void chargeCommonFee(String id, UserInfo userInfo) throws BizException;

	void chargeDecorateFee(String id, UserInfo userInfo) throws BizException;

	void chargeDepositFee(String id, UserInfo userInfo) throws BizException;

	void chargeGeneralBill(String id, UserInfo userInfo) throws BizException;

	void chargeIcDeposit(String id, UserInfo userInfo) throws BizException;

	void chargeParkingFee(String id, UserInfo userInfo) throws BizException;

	void chargeWaterFee(String id, UserInfo userInfo) throws BizException;

	void deleteBill(Class clazz, String id, UserInfo userInfo) throws BizException;

	void refund(String id, UserInfo sessionUser) throws BizException;

	void saveAdRentBill(AdrentBill adRent, UserInfo sessionUser) throws BizException;

	void saveDecorateServiceBill(DecorateServiceBill decorateServiceBill, UserInfo userInfo) throws BizException;

	void saveDepositBill(DepositBill depositBill, UserInfo sessionUser) throws BizException;

	void saveGeneralBill(GeneralBill generalBill, UserInfo userInfo) throws BizException;

	void saveIcDeposit(IcDeposit icDeposit, UserInfo userInfo) throws BizException;

	void saveParkingBill(ParkingBill parkingBill, UserInfo userInfo) throws BizException;

	void saveServiceBill(CommonServiceBill commomServiceBill, UserInfo userInfo) throws BizException;

	void chargeElecFee(String id, UserInfo userInfo) throws BizException;
}
