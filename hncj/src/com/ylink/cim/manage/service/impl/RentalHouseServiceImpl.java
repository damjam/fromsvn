package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.RentState;
import com.ylink.cim.common.state.RenterState;
import com.ylink.cim.manage.dao.RentalHouseDao;
import com.ylink.cim.manage.dao.RenterInfoDao;
import com.ylink.cim.manage.domain.RentalHouse;
import com.ylink.cim.manage.domain.RenterInfo;
import com.ylink.cim.manage.service.RentalHouseService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.DateUtil;
import flink.util.LogUtils;
@Component("rentalHouseService")
public class RentalHouseServiceImpl implements RentalHouseService {

	@Autowired
	private RentalHouseDao rentalHouseDao;
	@Autowired
	private RenterInfoDao renterInfoDao;
	
	@Override
	public void save(RentalHouse rentalHouse, UserInfo sessionUser) {
		rentalHouse.setCreateDate(DateUtil.getCurrent());
		rentalHouse.setCreateUser(sessionUser.getUserName());
		rentalHouse.setBranchNo(sessionUser.getBranchNo());
		rentalHouse.setState(RentState.STATE_00.getValue());
		rentalHouseDao.save(rentalHouse);
	}

	@Override
	public void update(RentalHouse rentalHouse) {
		rentalHouseDao.update(rentalHouse);
	}

	@Override
	public void release(String houseSn) throws BizException {
		RentalHouse rentalHouse = rentalHouseDao.findByIdWithLock(houseSn);
		Assert.notNull(rentalHouse, LogUtils.r("�����ڷ��ݱ��Ϊ{?}�ķ�����Ϣ", houseSn));
		rentalHouse.setState(RentState.STATE_00.getValue());
		rentalHouseDao.update(rentalHouse);
	}

	@Override
	public void occupy(String houseSn) throws BizException {
		RentalHouse rentalHouse = rentalHouseDao.findByIdWithLock(houseSn);
		Assert.notNull(rentalHouse, LogUtils.r("�����ڷ��ݱ��Ϊ{?}�ķ�����Ϣ", houseSn));
		Assert.equals(rentalHouse.getState(), RentState.STATE_00.getValue(), LogUtils.r("����{?}�ѳ��⣬�޷��ٴγ���", houseSn));
		rentalHouse.setState(RentState.STATE_01.getValue());
		rentalHouseDao.update(rentalHouse);
	}

	@Override
	public void delete(String houseSn) throws BizException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("houseSn", houseSn);
		map.put("state", RenterState.LIVEIN.getValue());
		List<RenterInfo> list = renterInfoDao.findList(map);
		Assert.isEmpty(list, LogUtils.r("���ݱ��{?}���ڶ�Ӧ�������Ϣ���޷�ɾ��", houseSn));
		rentalHouseDao.deleteById(houseSn);
	}
	
}
