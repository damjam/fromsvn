package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.manage.dao.ParkingInfoDao;
import com.ylink.cim.manage.domain.ParkingInfo;
import com.ylink.cim.manage.service.ParkingInfoService;
import com.ylink.cim.manage.service.ParkingInfoService;
import com.ylink.cim.user.domain.UserInfo;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("parkingInfoService")
public class ParkingInfoServiceImpl implements ParkingInfoService {

	@Autowired
	private ParkingInfoDao parkingInfoDao;
	@Autowired
	private IdFactoryService idFactoryService;
	public void saveOrUpdate(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException{
		parkingInfo.setBranchNo(userInfo.getBranchNo());
		parkingInfoDao.saveOrUpdate(parkingInfo);
	}

	public void save(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException{
		String id = idFactoryService.generateId(Constants.PARKING_INFO_ID);
		parkingInfo.setId(id);
		parkingInfo.setBranchNo(userInfo.getBranchNo());
		parkingInfo.setCreateDate(DateUtil.getCurrent());
		parkingInfo.setCreateUser(userInfo.getUserName());
		parkingInfoDao.save(parkingInfo);
	}

	public void update(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException{
		parkingInfo.setBranchNo(userInfo.getBranchNo());
		parkingInfoDao.update(parkingInfo);
	}

	public void delete(String id, UserInfo sessionUser) throws BizException{
		parkingInfoDao.deleteById(id);
		
	}



}
