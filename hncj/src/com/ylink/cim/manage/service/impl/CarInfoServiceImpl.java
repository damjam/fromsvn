package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.CarInfoDao;
import com.ylink.cim.manage.domain.CarInfo;
import com.ylink.cim.manage.service.CarInfoService;
import com.ylink.cim.user.domain.UserInfo;

import flink.etc.BizException;
import flink.util.DateUtil;

@Component("carInfoService")
public class CarInfoServiceImpl implements CarInfoService {

	@Autowired
	private CarInfoDao carInfoDao;

	public void saveOrUpdate(CarInfo carInfo, UserInfo userInfo) throws BizException{
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfoDao.saveOrUpdate(carInfo);
	}

	public void save(CarInfo carInfo, UserInfo userInfo) throws BizException{
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfo.setCreateDate(DateUtil.getCurrent());
		carInfo.setCreateUser(userInfo.getUserName());
		carInfoDao.save(carInfo);
	}

	public void update(CarInfo carInfo, UserInfo userInfo) throws BizException{
		carInfo.setBranchNo(userInfo.getBranchNo());
		carInfoDao.update(carInfo);
	}

	public void delete(String id, UserInfo sessionUser) throws BizException{
		carInfoDao.deleteById(id);
		
	}



}
