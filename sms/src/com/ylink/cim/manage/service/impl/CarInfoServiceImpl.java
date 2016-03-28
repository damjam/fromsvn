package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.manage.dao.CarInfoDao;
import com.ylink.cim.manage.domain.CarInfo;
import com.ylink.cim.manage.service.CarInfoService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("carInfoService")
public class CarInfoServiceImpl implements CarInfoService {

	@Autowired
	private CarInfoDao carInfoDao;
	@Autowired
	private IdFactoryService idFactoryService;

	@Override
	public void saveOrUpdate(CarInfo carInfo, UserInfo userInfo) throws BizException {
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfoDao.saveOrUpdate(carInfo);
	}

	@Override
	public void save(CarInfo carInfo, UserInfo userInfo) throws BizException {
		String id = idFactoryService.generateId(Constants.CAR_INFO_ID);
		carInfo.setId(id);
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfo.setCreateDate(DateUtil.getCurrent());
		carInfo.setCreateUser(userInfo.getUserName());
		carInfoDao.save(carInfo);
	}

	@Override
	public void update(CarInfo carInfo, UserInfo userInfo) throws BizException {
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfoDao.update(carInfo);
	}

	@Override
	public void delete(String id, UserInfo sessionUser) throws BizException {
		carInfoDao.deleteById(id);

	}

}
